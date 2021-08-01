package alphavantage.model;

import alphavantage.company.Company;
import alphavantage.company.CompanyBuilder;
import alphavantage.company.CompanyBuilderImpl;
import alphavantage.message.Message;
import alphavantage.message.MessageSender;
import alphavantage.query.CompanyDatabase;
import alphavantage.query.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlphaVantageFacadeImpl implements AlphaVantageFacade{

    /* Input API's mode */
    private String inType;

    /* Output API's mode */
    private String outType;

    /* Current Company */
    private Company com;

    /* Company Builder */
    private CompanyBuilder builder;

    /* Message Sender */
    private Message messageSender;

    /* Company Database */
    private Database db;

    /* Company split year */
    private int splitYear;

    /**
     * AlphavanatgeFacade Constructor
     *
     * @param inType
     * @param outType
     */
    public AlphaVantageFacadeImpl(String inType, String outType){
        this.inType = inType;
        this.outType = outType;
        this.builder = new CompanyBuilderImpl();
        this.messageSender = new MessageSender();
        this.db = new CompanyDatabase();
    }

    @Override
    public Company getCompany(){
        return this.com;
    }

    @Override
    public void setSplitYear(String year) throws IllegalArgumentException, IllegalStateException {
        if(year == null || year.equals("")){
            throw new IllegalArgumentException();
        }

        try {
            splitYear = Integer.parseInt(year);

        }catch (Exception e){
            throw new IllegalArgumentException();
        }

        if(splitYear < 1900 || splitYear > 2021){
            throw new IllegalStateException();
        }
    }

    //0: exists in database
    //1: new data
    @Override
    public int receive(String symbol) throws IllegalArgumentException, IllegalStateException, SQLException {

        if(symbol == null || symbol.equals("")){
            throw new IllegalArgumentException();
        }

        if(inType.equals("offline")){
            symbol += "DUMMY";
        }

        db.connect();
        int n = db.checkIfExists(symbol);
        db.disconnect();
        if(n > 0){
            return 0;
        }

        this.com = builder.generateData(inType, symbol);
        if(com == null){
            throw new IllegalStateException();
        }

        insertToDatabase();

        return 1;
    }

    @Override
    public void receiveApi(String symbol) throws IllegalStateException, SQLException {
        if(inType.equals("offline")){
            symbol += "DUMMY";
        }

        this.com = builder.generateData(inType, symbol);
        if(com == null){
            throw new IllegalStateException();
        }
        updateDatabase();
    }

    @Override
    public void clearData(){
        this.com = null;
    }

    @Override
    public String getData() {

        String data = "";

        if(this.com.getOverView().getElement().size() != this.com.getOverView().getValue().size()){
            return data;
        }

        for(int i = 0; i < this.com.getOverView().getElement().size(); i++){

            if(this.com.getOverView().getElement().get(i).equals("Last Split Date")){
                if(!this.com.getOverView().getValue().get(i).equals("None")){
                    String[] date = this.com.getOverView().getValue().get(i).split("-");
                    int year = Integer.parseInt(date[0]);
                    if(year > splitYear){
                        com.setRecentlySplit(true);
                    }
                }
            }

            String combine = this.com.getOverView().getElement().get(i) +
                    " :      " + this.com.getOverView().getValue().get(i);
            data += combine;
            data += '\n';
            data += '\n';
        }

        for(int i = 0; i < this.com.getCashFlow().getReports().get(0).getElement().size(); i++){
            String combine = this.com.getCashFlow().getReports().get(0).getElement().get(i) +
                    " :      " + this.com.getCashFlow().getReports().get(0).getValue().get(i);
            data += combine;
            data += '\n';
            data += '\n';
        }

        if(com.isRecentlySplit()){
            String split = "Recently Split";
            split += '\n';
            split += data;
            data = split;
        }

        return data;
    }

    @Override
    public String receiveDB(String symbol) throws SQLException {

        if(inType.equals("offline")){
            symbol += "DUMMY";
        }

        db.connect();
        List<String> result = db.selectData(symbol);
        db.disconnect();

        this.com = builder.buildFromDB(result);

        return getData();
    }

    @Override
    public void sendData() throws IllegalStateException {

        try {
            messageSender.setDetails();
            messageSender.configureMessage(com);
            messageSender.sendMessage(outType);

        }catch (Exception e){
            throw new IllegalStateException();
        }

    }

    @Override
    public void setCompanyBuilder(CompanyBuilder cb) {
        builder = cb;
    }

    @Override
    public void setDatabase(Database d) {
        db = d;
    }

    @Override
    public void setMessager(Message m) {
        messageSender = m;
    }

    /**
     * Can only be invoked by receive() method
     *
     * Handles when to insert
     * information to the database
     *
     * @throws SQLException Database error
     */
    private void insertToDatabase() throws SQLException {

        List<String> data = new ArrayList<>();
        data.addAll(com.getOverView().getValue());
        data.addAll(com.getCashFlow().getReports().get(0).getValue());

        db.connect();
        db.insertData(data);
        db.disconnect();
    }

    /**
     * Can only be invoked by receiveApi() method
     *
     * Handles when to update
     * information in the database
     *
     * @throws SQLException Database error
     */
    private void updateDatabase() throws SQLException {

        List<String> data = new ArrayList<>();
        data.addAll(com.getOverView().getValue());
        data.addAll(com.getCashFlow().getReports().get(0).getValue());

        db.connect();
        db.updateData(data);
        db.disconnect();
    }

}

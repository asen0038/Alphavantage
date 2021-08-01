package alphavantage.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyDatabase implements Database{

    /* Company overview element fields */
    private final static List<String> GENERAL_INFO = Arrays.asList("Symbol", "AssetType", "Name", "Description", "CIK",
            "Exchange", "Currency", "Country", "Sector", "Industry", "Address", "FullTimeEmployees",
            "FiscalYearEnd", "LatestQuarter", "MarketCapitalization", "EBITDA", "PERatio", "PEGRatio",
            "BookValue", "DividendPerShare", "DividendYield", "EPS", "RevenuePerShareTTM", "ProfitMargin",
            "OperatingMarginTTM", "ReturnOnAssetsTTM", "ReturnOnEquityTTM", "RevenueTTM", "GrossProfitTTM",
            "DilutedEPSTTM", "QuarterlyEarningsGrowthYOY", "QuarterlyRevenueGrowthYOY", "AnalystTargetPrice",
            "TrailingPE", "ForwardPE", "PriceToSalesRatioTTM", "PriceToBookRatio", "EVToRevenue", "EVToEBITDA",
            "Beta", "52WeekHigh", "52WeekLow", "50DayMovingAverage", "200DayMovingAverage", "SharesOutstanding",
            "SharesFloat", "SharesShort", "SharesShortPriorMonth", "ShortRatio", "ShortPercentOutstanding",
            "ShortPercentFloat", "PercentInsiders", "PercentInstitutions", "ForwardAnnualDividendRate",
            "ForwardAnnualDividendYield", "PayoutRatio", "DividendDate", "ExDividendDate", "LastSplitFactor",
            "LastSplitDate");

    /* Company cashflow element fields */
    private final static List<String> CASHFLOW_ANNUAL_REPORT = Arrays.asList("fiscalDateEnding", "reportedCurrency",
            "operatingCashflow", "paymentsForOperatingActivities", "proceedsFromOperatingActivities",
            "changeInOperatingLiabilities", "changeInOperatingAssets", "depreciationDepletionAndAmortization",
            "capitalExpenditures", "changeInReceivables", "changeInInventory", "profitLoss",
            "cashflowFromInvestment", "cashflowFromFinancing", "proceedsFromRepaymentsOfShortTermDebt",
            "paymentsForRepurchaseOfCommonStock", "paymentsForRepurchaseOfEquity",
            "paymentsForRepurchaseOfPreferredStock", "dividendPayout", "dividendPayoutCommonStock",
            "dividendPayoutPreferredStock", "proceedsFromIssuanceOfCommonStock",
            "proceedsFromIssuanceOfLongTermDebtAndCapitalSecuritiesNet", "proceedsFromIssuanceOfPreferredStock",
            "proceedsFromRepurchaseOfEquity", "proceedsFromSaleOfTreasuryStock", "changeInCashAndCashEquivalents",
            "changeInExchangeRate", "netIncome");

    /* Database url */
    private String db = "jdbc:sqlite:database/company.db";

    /* Database connection object */
    private Connection con = null;

    /* Database insert object */
    private InsertData insert;

    /* Database select object */
    private SelectData select;

    /* Database update object */
    private UpdateData update;

    /**
     * Company Database constructor
     */
    public CompanyDatabase(){
        insert = new InsertData();
        select = new SelectData();
        update = new UpdateData();
    }

    @Override
    public void connect() throws SQLException {
        con = DriverManager.getConnection(db);
    }

    @Override
    public void insertData(List<String> data) throws SQLException {
        insert.insertData(con, data);
    }

    @Override
    public List<String> selectData(String symbol) throws SQLException {
        ResultSet set = select.selectData(con, symbol);
        List<String> result = new ArrayList<>();

        //Elements with number starting characters have modified fields in the database
        for(String s : GENERAL_INFO){
            if(s.equals("52WeekHigh")){
                result.add(set.getString("a52WeekHigh"));
            }else if(s.equals("52WeekLow")){
                result.add(set.getString("a52WeekLow"));
            }else if(s.equals("Name")){
                result.add(set.getString("aName"));
            }else if(s.equals("50DayMovingAverage")){
                result.add(set.getString("a50DayMovingAverage"));
            }else if(s.equals("200DayMovingAverage")){
                result.add(set.getString("a200DayMovingAverage"));
            }else{
                result.add(set.getString(s));
            }
        }

        for(String s : CASHFLOW_ANNUAL_REPORT){
            result.add(set.getString(s));
        }

        return result;
    }

    @Override
    public int checkIfExists(String symbol) throws SQLException {
        return select.checkIfExists(con, symbol);
    }

    @Override
    public void updateData(List<String> data) throws SQLException {
        update.updateData(con, data);
    }

    @Override
    public void disconnect() throws SQLException {
        con.close();
    }
}

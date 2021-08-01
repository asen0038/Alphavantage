package alphavantage.company;

import alphavantage.company.information.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CompanyBuilderImpl implements CompanyBuilder{

    /* Overview element fields */
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

    /* Cashflow element fields */
    private final static List<String> CASHFLOW_INFO = Arrays.asList("annualReports");

    /* Annual Report element fields */
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

    /* Overview value fields */
    private List<String> valueOverview;

    /* Cashflow report value fields */
    private List<AnnualReport> reports;

    /* Annual report value fields */
    private List<String> valueReports;

    /* Company to build*/
    private Company com;

    /**
     * Company builder constructor
     */
    public CompanyBuilderImpl(){
        this.valueOverview = new ArrayList<>();
        this.reports = new ArrayList<>();
        this.valueReports = new ArrayList<>();
    }

    @Override
    public void setOverviewValues(List<String> valueOverview) {
        this.valueOverview = valueOverview;
    }

    @Override
    public void setCashFlowValues(List<AnnualReport> reports) {
        this.reports = reports;
    }

    @Override
    public Company build() {
        Overview overview = new OverviewImpl(valueOverview);
        CashFlow cashflow = new CashFlowImpl(reports);
        this.com = new CompanyImpl(overview, cashflow);
        return this.com;
    }


    @SuppressWarnings("unchecked")
    public Company generateData(String input, String symbol) {

        JSONParser parser = new JSONParser();
        String key = "";
        try {

            Reader reader = new FileReader("configure/credentials.json");
            JSONObject object = (JSONObject) parser.parse(reader);

            key = (String) object.get("in_key");

        }catch (ParseException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

        if(input.equals("online")){
            String overview = String.format("https://www.alphavantage.co/query?function=OVERVIEW&symbol=%s&apikey=%s", symbol, key);
            String cashflow = String.format("https://www.alphavantage.co/query?function=CASH_FLOW&symbol=%s&apikey=%s", symbol, key);

            String rawOverview = readURLData(overview);
            String rawCashFlow = readURLData(cashflow);

            if(rawOverview == null || rawCashFlow == null){
                return null;
            }

            List<String> valueOverview = new ArrayList<>();
            List<AnnualReport> reports = new ArrayList<>();
            List<String> valueReports = new ArrayList<>();

            Object obj = JSONValue.parse(rawOverview);
            JSONObject jsonObject = (JSONObject) obj;

            for(String element : GENERAL_INFO){
                String data = (String) jsonObject.get(element);
                valueOverview.add(data);
            }
            setOverviewValues(valueOverview);

            Object obj1 = JSONValue.parse(rawCashFlow);
            JSONObject jsonObject1 = (JSONObject) obj1;

            for(String element : CASHFLOW_INFO){
                JSONArray anr = (JSONArray) jsonObject1.get(element);
                Iterator<JSONObject> iterator = (Iterator<JSONObject>) anr.iterator();
                while (iterator.hasNext()) {
                    JSONObject r = (JSONObject) iterator.next();

                    for(String fields : CASHFLOW_ANNUAL_REPORT){
                        String field = (String) r.get(fields);
                        valueReports.add(field);
                    }

                    AnnualReport an = new AnnualReport(valueReports);
                    reports.add(an);
                    //valueReports.clear();

                    break; //only 1 year required
                }
            }
            setCashFlowValues(reports);

            return build();

        }else{ //dummy

            List<String> valueOverview = Arrays.asList(symbol, "Dummy Stock", "Demo Corporation",
                    "Demo corporation description. Not a real company. For demo and testing purposes only.",
                    "999999", "AAA", "AUD", "AUS", "Test", "Software Engineering",
                    "Demo Street, Imaginary Park, NSW, Australia, 000000", "999999", "December", "2021-03-31",
                    "99999999999", "999999999", "99.9", "99.9", "99.9", "99.9", "99.9", "99.9", "99.9", "99.9",
                    "99.9", "99.9", "99.9", "99999999", "9999999", "99.9", "99.9", "99.9", "99.9", "99.9", "99.9",
                    "99.9", "99.9", "99.9", "99.9", "99.9", "99.9", "99.9", "99.9", "99.9", "999999", "999999",
                    "999999", "9999999", "99.9", "99.9", "99.9", "99.9", "99.9", "99.9", "99.9", "99.9",
                    "2021-06-10", "2021-05-07", "9:1", "2021-05-27");

            setOverviewValues(valueOverview);

            List<AnnualReport> reports = new ArrayList<>();

            List<String> valueReports = Arrays.asList("2020-12-31", "AUD", "999999", "9999999", "None",
                    "99999999999", "99999999999", "99999999999", "99999999999", "99999999999", "99999999999",
                    "99999999999", "99999999999", "99999999999", "99999999999", "None", "None", "None",
                    "99999999999", "99999999999", "None", "None", "99999999999", "99999999999", "99999999999",
                    "99999999999", "99999999999", "99999999999", "99999999999");

            AnnualReport an = new AnnualReport(valueReports);
            reports.add(an);
            setCashFlowValues(reports);

            return build();
        }

    }

    /**
     * Can only be invoked by generateData() method
     *
     * This private method reads data
     * from the given URL and returns
     * a String concatenated from JSON data
     * It sends a GET request to the API
     *
     * @param u
     * @return A string of data concatenated in JSON format
     */
    private static String readURLData(String u){

        String result = "";

        try {
            URL url = new URL(u);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            InputStreamReader in = new InputStreamReader(con.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                result += output;
            }
            con.disconnect();

        }catch (Exception e){
            return null;
        }

        return result;
    }

    public Company buildFromDB(List<String> value){

        List<String> valueOverview = new ArrayList<>();
        for(int i = 0; i <= 59; i++){
            valueOverview.add(value.get(i));
        }
        setOverviewValues(valueOverview);

        List<AnnualReport> reports = new ArrayList<>();
        List<String> valueReports = new ArrayList<>();
        for(int i = 60; i < value.size(); i++){
            valueReports.add(value.get(i));
        }

        AnnualReport an = new AnnualReport(valueReports);
        reports.add(an);
        setCashFlowValues(reports);

        return build();
    }

}

package alphavantage.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Class for updating data in database
 * Calls SQL queries for update
 */
public class UpdateData {

    /**
     * Update Data Constructor
     */
    public UpdateData(){

    }

    /**
     * Updates the database by executing the the
     * update sql query
     *
     * @param c
     * @param data
     * @throws SQLException If there is an error in updating
     */
    public void updateData(Connection c, List<String> data) throws SQLException {

        String query = "UPDATE company SET AssetType = ? , "
                + "aName = ? , "
                + "Description = ? , "
                + "CIK = ? , "
                + "Exchange = ? , "
                + "Currency = ? , "
                + "Country = ? , "
                + "Sector = ? , "
                + "Industry = ? , "
                + "Address = ? , "
                + "FullTimeEmployees = ? , "
                + "FiscalYearEnd = ? , "
                + "LatestQuarter = ? , "
                + "MarketCapitalization = ? , "
                + "EBITDA = ? , "
                + "PERatio = ? , "
                + "PEGRatio = ? , "
                + "BookValue = ? , "
                + "DividendPerShare = ? , "
                + "DividendYield = ? , "
                + "EPS = ? , "
                + "RevenuePerShareTTM = ? , "
                + "ProfitMargin = ? , "
                + "OperatingMarginTTM = ? , "
                + "ReturnOnAssetsTTM = ? , "
                + "ReturnOnEquityTTM = ? , "
                + "RevenueTTM = ? , "
                + "GrossProfitTTM = ? , "
                + "DilutedEPSTTM = ? , "
                + "QuarterlyEarningsGrowthYOY = ? , "
                + "QuarterlyRevenueGrowthYOY = ? , "
                + "AnalystTargetPrice = ? , "
                + "TrailingPE = ? , "
                + "ForwardPE = ? , "
                + "PriceToSalesRatioTTM = ? , "
                + "PriceToBookRatio = ? , "
                + "EVToRevenue = ? , "
                + "EVToEBITDA = ? , "
                + "Beta = ? , "
                + "a52WeekHigh = ? , "
                + "a52WeekLow = ? , "
                + "a50DayMovingAverage = ? , "
                + "a200DayMovingAverage = ? , "
                + "SharesOutstanding = ? , "
                + "SharesFloat = ? , "
                + "SharesShort = ? , "
                + "SharesShortPriorMonth = ? , "
                + "ShortRatio = ? , "
                + "ShortPercentOutstanding = ? , "
                + "ShortPercentFloat = ? , "
                + "PercentInsiders = ? , "
                + "PercentInstitutions = ? , "
                + "ForwardAnnualDividendRate = ? , "
                + "ForwardAnnualDividendYield = ? , "
                + "PayoutRatio = ? , "
                + "DividendDate = ? , "
                + "ExDividendDate = ? , "
                + "LastSplitFactor = ? , "
                + "LastSplitDate = ? , "
                + "fiscalDateEnding = ? , "
                + "reportedCurrency = ? , "
                + "operatingCashflow = ? , "
                + "paymentsForOperatingActivities = ? , "
                + "proceedsFromOperatingActivities = ? , "
                + "changeInOperatingLiabilities = ? , "
                + "changeInOperatingAssets = ? , "
                + "depreciationDepletionAndAmortization = ? , "
                + "capitalExpenditures = ? , "
                + "changeInReceivables = ? , "
                + "changeInInventory = ? , "
                + "profitLoss = ? , "
                + "cashflowFromInvestment = ? , "
                + "cashflowFromFinancing = ? , "
                + "proceedsFromRepaymentsOfShortTermDebt = ? , "
                + "paymentsForRepurchaseOfCommonStock = ? , "
                + "paymentsForRepurchaseOfEquity = ? , "
                + "paymentsForRepurchaseOfPreferredStock = ? , "
                + "dividendPayout = ? , "
                + "dividendPayoutCommonStock = ? , "
                + "dividendPayoutPreferredStock = ? , "
                + "proceedsFromIssuanceOfCommonStock = ? , "
                + "proceedsFromIssuanceOfLongTermDebtAndCapitalSecuritiesNet = ? , "
                + "proceedsFromIssuanceOfPreferredStock = ? , "
                + "proceedsFromRepurchaseOfEquity = ? , "
                + "proceedsFromSaleOfTreasuryStock = ? , "
                + "changeInCashAndCashEquivalents = ? , "
                + "changeInExchangeRate = ? , "
                + "netIncome = ? "
                + "WHERE Symbol = ?";

        PreparedStatement stat = c.prepareStatement(query);
        for(int i = 1; i <= 88; i++){
            stat.setString(i, data.get(i));
        }
        stat.setString(89, data.get(0));
        stat.executeUpdate();

    }

}

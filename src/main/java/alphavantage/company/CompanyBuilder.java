package alphavantage.company;

import alphavantage.company.information.AnnualReport;

import java.util.List;

/**
 * The builder class for a Company Object
 */
public interface CompanyBuilder {

    /**
     * Sets the company's overview
     * information values
     *
     * @param valueOverview
     */
    void setOverviewValues(List<String> valueOverview);

    /**
     * Sets the company's cashflow
     * information reports
     *
     * @param reports
     */
    void setCashFlowValues(List<AnnualReport> reports);

    /**
     * Returns a fully constructed company object
     *
     * @return A company
     */
    Company build();

    /**
     * Builds a company with
     * information from API
     *
     * @param input
     * @param symbol
     * @return A company object with API info
     */
    Company generateData(String input, String symbol);

    /**
     * Builds a company with
     * information from Database
     *
     * @param value
     * @return A company object with cached info from database
     */
    Company buildFromDB(List<String> value);

}

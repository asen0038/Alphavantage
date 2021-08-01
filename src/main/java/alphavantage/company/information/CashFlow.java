package alphavantage.company.information;

import java.util.List;

/**
 * The class encapsulating the
 * company cashflow information
 */
public interface CashFlow {

    /**
     * Returns a list of element field values
     *
     * @return A list of Elements
     */
    List<String> getElement();

    /**
     * Returns a list the list of
     * annual reports
     *
     * @return A list of Annual Reports
     */
    List<AnnualReport> getReports();
}

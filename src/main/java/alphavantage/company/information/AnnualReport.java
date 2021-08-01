package alphavantage.company.information;

import java.util.Arrays;
import java.util.List;

/**
 * The class encapsulating information about
 * an annual report.
 */
public class AnnualReport {

    /* The elements fields of the annual report */
    private final List<String> ELEMENT = Arrays.asList("Fiscal Date Ending", "Reported Currency",
            "Operating Cashflow", "Payments For Operating Activities", "Proceeds From Operating Activities",
            "Change In Operating Liabilities", "Change In Operating Assets",
            "Depreciation, Depletion And Amortization",
            "Capital Expenditures", "Change In Receivables", "Change In Inventory", "Profit Loss",
            "Cashflow From Investment", "Cashflow From Financing", "Proceeds From Repayments Of Short Term Debt",
            "Payments For Repurchase Of Common Stock", "Payments For Repurchase Of Equity",
            "Payments For Repurchase Of Preferred Stock", "Dividend Payout", "Dividend Payout Common Stock",
            "Dividend Payout Preferred Stock", "Proceeds From Issuance Of Common Stock",
            "Proceeds From Issuance Of Long Term Debt And Capital Securities Net",
            "Proceeds From Issuance Of Preferred Stock", "Proceeds From Repurchase Of Equity",
            "Proceeds From Sale Of Treasury Stock", "Change In Cash And Cash Equivalents",
            "Change In Exchange Rate", "Net Income");

    /* The value fields of the annual report */
    private List<String> value;

    /**
     * Annual Report Constructor
     *
     * @param value
     */
    public AnnualReport(List<String> value) {
        this.value = value;
    }

    /**
     * Returns a list of element field values
     *
     * @return A list of Elements
     */
    public List<String> getElement() {
        return ELEMENT;
    }

    /**
     * Returns a list of value field values
     *
     * @return A list of Values
     */
    public List<String> getValue() {
        return value;
    }

}

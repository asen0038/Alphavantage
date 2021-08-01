package alphavantage.company.information;

import java.util.Arrays;
import java.util.List;

public class OverviewImpl implements Overview{

    /* The elements fields of a company overview */
    private final List<String> ELEMENT = Arrays.asList("Company", "Asset Type", "Name", "Description", "CIK",
            "Exchange", "Currency", "Country", "Sector", "Industry", "Address", "Full Time Employees",
            "Fiscal Year End", "Latest Quarter", "Market Capitalization", "EBITDA", "PE Ratio", "PEG Ratio",
            "Book Value", "Dividend Per Share", "Dividend Yield", "EPS", "Revenue Per Share TTM", "Profit Margin",
            "Operating Margin TTM", "Return On Assets TTM", "Return On Equity TTM", "Revenue TTM",
            "Gross Profit TTM", "Diluted EPSTTM", "Quarterly Earnings Growth YOY", "Quarterly Revenue Growth YOY",
            "Analyst Target Price", "Trailing PE", "Forward PE", "Price To Sales Ratio TTM", "Price To Book Ratio",
            "EV To Revenue", "EV To EBITDA", "Beta", "52 Week High", "52 Week Low", "50 Day Moving Average",
            "200 Day Moving Average", "Shares Outstanding", "Shares Float", "Shares Short", "Shares Short PriorMonth",
            "Short Ratio", "Short Percent Outstanding", "Short Percent Float", "Percent Insiders",
            "Percent Institutions", "Forward Annual Dividend Rate", "Forward Annual Dividend Yield",
            "Payout Ratio", "Dividend Date", "Ex Dividend Date", "Last Split Factor", "Last Split Date");

    /* The value fields of a company overview */
    private List<String> value;

    /**
     * Overview Constructor
     *
     * @param value
     */
    public OverviewImpl(List<String> value) {
        this.value = value;
    }

    @Override
    public List<String> getElement() {
        return ELEMENT;
    }

    @Override
    public List<String> getValue() {
        return value;
    }
}

package alphavantage.company.information;

import java.util.Arrays;
import java.util.List;

public class CashFlowImpl implements CashFlow{

    /* The elements fields of a company's cashflow */
    private final List<String> ELEMENT = Arrays.asList("Annual Reports");

    /* The list of annual reports of a company */
    private List<AnnualReport> reports;

    /**
     * Cashflow Constructor
     *
     * @param reports
     */
    public CashFlowImpl(List<AnnualReport> reports){
        this.reports = reports;
    }

    @Override
    public List<String> getElement() {
        return ELEMENT;
    }

    @Override
    public List<AnnualReport> getReports() {
        return reports;
    }
}

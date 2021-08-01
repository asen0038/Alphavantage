package alphavantage.company;

import alphavantage.company.information.CashFlow;
import alphavantage.company.information.Overview;

public class CompanyImpl implements Company{

    /* Company's overview */
    private Overview overview;

    /* Company's cashflow */
    private CashFlow cashFlow;

    /* Company's recent split */
    private boolean recentlySplit = false;

    /**
     * Company constructor
     *
     * @param overview
     * @param cashFlow
     */
    public CompanyImpl(Overview overview, CashFlow cashFlow){
        this.overview = overview;
        this.cashFlow = cashFlow;
    }

    @Override
    public Overview getOverView() {
        return overview;
    }

    @Override
    public CashFlow getCashFlow() {
        return cashFlow;
    }

    @Override
    public boolean isRecentlySplit() {
        return false;
    }

    @Override
    public void setRecentlySplit(boolean recentlySplit) {
        this.recentlySplit = recentlySplit;
    }
}

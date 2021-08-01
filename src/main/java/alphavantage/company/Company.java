package alphavantage.company;

import alphavantage.company.information.CashFlow;
import alphavantage.company.information.Overview;

/**
 * This class encapsulates a company's information
 * consisting of an overview and cashflow reports
 */
public interface Company {

    /**
     * Returns a company's overview information
     *
     * @return an overview object belonging to this company
     */
    Overview getOverView();

    /**
     * Returns a company's cashflow information
     *
     * @return an cashflow object belonging to this company
     */
    CashFlow getCashFlow();

    /**
     * Checks if this company has a recent split
     *
     * @return true if there is a recent split, false otherwise
     */
    boolean isRecentlySplit();

    /**
     * Sets the boolean value for
     * the company's recent split
     *
     * @param recentlySplit is either true or false
     */
    void setRecentlySplit(boolean recentlySplit);
}

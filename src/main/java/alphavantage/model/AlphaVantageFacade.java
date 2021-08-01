package alphavantage.model;

import alphavantage.company.Company;
import alphavantage.company.CompanyBuilder;
import alphavantage.message.Message;
import alphavantage.query.Database;

import java.sql.SQLException;

/**
 * Main facade class that controls access
 * to other parts of this module
 */
public interface AlphaVantageFacade {

    /**
     * Getter method for the current company in search
     *
     * @return the current company
     */
    Company getCompany();

    /**
     * Sets the split year for the company
     *
     * @param year Must be between 1900 and 2021
     * @throws IllegalArgumentException If year is null or empty or is not an integer
     * @throws IllegalStateException If the year is less than 1900 or greater than 2021
     */
    void setSplitYear(String year) throws IllegalArgumentException, IllegalStateException;

    /**
     * Recieves a brand new company's information
     * and also checks database if information exists
     * otherwise adds it to database
     *
     * @param symbol must not be null or empty
     * @return returns 1 if the data is new and 0 if data exists in database
     * @throws IllegalArgumentException if symbol is null or empty
     * @throws IllegalStateException if the company could not be created
     * @throws SQLException database error
     */
    int receive(String symbol) throws IllegalArgumentException, IllegalStateException, SQLException;

    /**
     * Recieves data from the API
     * and creates a company object
     *
     * @param symbol
     * @throws IllegalStateException if the company could not be created
     * @throws SQLException database error
     */
    void receiveApi(String symbol) throws IllegalStateException, SQLException;

    /**
     * Clears the current company data
     */
    void clearData();

    /**
     * Returns a formatted output data
     * to be displayed in the view
     *
     * @return formatted output data information of company
     */
    String getData();

    /**
     * Recieves data from the database and creates
     * a company object given the symbol and
     * returns the formatted data for the
     * view representation
     *
     * @param symbol
     * @return Company information
     * @throws SQLException Database error
     */
    String receiveDB(String symbol) throws SQLException;

    /**
     * Uses the message sender to send
     * the current company information
     * as an SMS
     *
     * @throws IllegalStateException
     */
    void sendData() throws IllegalStateException;

    /**
     * Sets the Company builder object
     *
     * @param cb
     */
    void setCompanyBuilder(CompanyBuilder cb);

    /**
     * Sets the Database object
     *
     * @param d
     */
    void setDatabase(Database d);

    /**
     * Sets the Message object
     *
     * @param m
     */
    void setMessager(Message m);

}

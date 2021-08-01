package alphavantage.query;

import java.sql.SQLException;
import java.util.List;

public interface Database {

    /**
     * Establishes a connection to the
     * company database
     *
     * @throws SQLException If no database of given name is found
     */
    void connect() throws SQLException;

    /**
     * Calls the insert operations to database
     *
     * @param data
     * @throws SQLException If data was not inserted due to connection issues
     */
    void insertData(List<String> data) throws SQLException;

    /**
     * Calls the select operations to database
     * and returns the data found given the symbol
     *
     * @param symbol
     * @return The data associated with the company symbol
     * @throws SQLException If data was not found due to connection issues
     */
    List<String> selectData(String symbol) throws SQLException;

    /**
     * Checks if data associated with the
     * company symbol exists in the database
     *
     * @param symbol
     * @return Returns 1 if exists and 0 if not exists
     * @throws SQLException if the data was not searched due to connection issues
     */
    int checkIfExists(String symbol) throws SQLException;

    /**
     * Calls update operations on the database
     *
     * @param data
     * @throws SQLException If the data was not updated due to connection issues
     */
    void updateData(List<String> data) throws SQLException;

    /**
     * Disconnects from the database
     *
     * @throws SQLException If no database of given name is found
     */
    void disconnect() throws SQLException;

}

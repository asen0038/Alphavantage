package alphavantage.query;

import java.sql.*;

/**
 * Class responsible for executing queries
 * related to select data and check for data
 */
public class SelectData {

    /**
     * Select data constructor
     */
    public SelectData(){

    }

    /**
     * Executes the select query to retrieve cached
     * information given the company symbol data
     * and returns a result set containing the company information
     *
     * @param c
     * @param data
     * @return A result object containing cached data
     * @throws SQLException If there is an error in selecting data
     */
    public ResultSet selectData(Connection c, String data) throws SQLException {

        String query = "SELECT * FROM company WHERE Symbol = ?";

        PreparedStatement stat  = c.prepareStatement(query);
        stat.setString(1, data);
        ResultSet set  = stat.executeQuery();

        return set;
    }

    /**
     * Executes the select query to check
     * if a compnay with given data symbol
     * exists in database or not
     *
     * @param c
     * @param data
     * @return 1 if exists, 0 if not exists
     * @throws SQLException If there is an error in selecting data
     */
    public int checkIfExists(Connection c, String data) throws SQLException {

        String query = "SELECT * FROM company WHERE Symbol = ?";

        PreparedStatement stat  = c.prepareStatement(query);
        stat.setString(1, data);
        ResultSet set  = stat.executeQuery();
        if(set.next()){
            return 1;
        }

        return 0;
    }

}

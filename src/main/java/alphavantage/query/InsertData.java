package alphavantage.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Class for executing insert statements
 * into the database
 */
public class InsertData {

    /**
     * Insert data constructor
     */
    public InsertData(){

    }

    /**
     * Executes the insert statement with the given
     * values in the data list
     *
     * @param c
     * @param data
     * @throws SQLException If the there was an error in inserting data
     */
    public void insertData(Connection c, List<String> data) throws SQLException {

        String query = "INSERT INTO company VALUES(?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement stat = c.prepareStatement(query);
        for(int i = 1; i <= 89; i++){
            stat.setString(i, data.get(i - 1));
        }
        stat.executeUpdate();

    }

}

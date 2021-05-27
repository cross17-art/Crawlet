import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BDconnection {

    private String url = "jdbc:mysql://localhost:3306/crawler?autoReconnect=true&useSSL=false";
    private String password = "1111";
    private String user_name = "root";

    Statement stm;
    Connection connection;

    BDconnection() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    public Connection getConnection() throws SQLException {
        if (connection != null) {
            return connection;
        }
        connection = DriverManager.getConnection(url, user_name, password);
        return connection;
    }

    public void Insert_db(String a_href, int term_1, int term_2, int term_3, int term_4, int level_url) throws SQLException {

        String query = "  INSERT INTO `crawler`.`terms` (`a_href`,`level_url`, `first_term`, `second_term`, `third_term`, `fourth_term`) VALUES (?,?,?,?,?,?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, a_href);
        statement.setInt(2, level_url);
        statement.setInt(3, term_1);
        statement.setInt(4, term_2);
        statement.setInt(5, term_3);
        statement.setInt(6, term_4);

        statement.executeUpdate();
        statement.close();


    }


}

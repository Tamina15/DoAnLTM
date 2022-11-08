package Server;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    public static Connection Connect() {

        try {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://127.0.0.1:1433;" + "databasename=MultipleChoice;user=sa;password=sa";
            Class.forName(driver); //load driver
            Connection con = (Connection) DriverManager.getConnection(url);
            return con;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
            return null;
        }

    }
}

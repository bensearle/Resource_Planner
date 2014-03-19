/*
 * The Firstco Assistant Resourcing Tool, designed and built by Ben Searle
 * for IN2030 Work Based Project at City University London
 */
package Resource_Planner;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 * @author Ben Searle
 */

public class JavaConnect {
    // define conn as the connection to the database
    Connection conn = null;
    public static Connection ConnectDB(){
        try{
            //  use JDBC driver for SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //  full url of database with login credentials
            String connectionUrl = "jdbc:sqlserver://*servername*\\SQLEXPRESS:1435;databaseName=*dbname*;user=*username*;password=*password*;";
            Connection con = DriverManager.getConnection(connectionUrl);
            //    JOptionPane.showMessageDialog(null, "Connection Established");
            return con;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}

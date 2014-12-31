/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clusteringuncertaindata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chandramouliswaran
 */
public class DBUtils {

    public static String url = "jdbc:mysql://localhost:3306/cluster2?profileSQL=true&user=root&password=mysql";
    private static Connection con;

    public static void connect() {

        if (con == null) {
            try {
                Class.forName(com.mysql.jdbc.Driver.class.getCanonicalName());
                con = (Connection) DriverManager.getConnection(url);

//                Statement stmt = con.createStatement();
//                
//                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM USERS");
//                
//                System.out.println("Count:"+rs.next());
//                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static ResultSet getData(String sql) {
        try {
            connect();
            Statement st = con.createStatement();
            return st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getNthDataAsString(String sql, int n) {
        try {
            ResultSet data = getData(sql);
            if (data.next()) {
                return data.getString(n);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "-1";
    }

    public static Vector<String[]> getItems() {
        try {
            String sql = "SELECT * FROM WEATHERMONITOR";

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            Vector<String[]> items = new Vector<String[]>();

            while (rs.next()) {
                String[] weather = new String[5];

                weather[0] = rs.getString("TEMPERATURE");
                weather[1] = rs.getString("PRECIPITATION");
                weather[2] = rs.getString("HUMIDITY");
                weather[3] = rs.getString("WINDSPEED");
                weather[4] = rs.getString("DIRECTION");

                items.add(weather);
            }

            return items;
        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new Vector<String[]>();
    }

    static boolean login(String username, String password) {
        String sql = "SELECT * FROM login WHERE USERNAME='" + username + "' AND PASSWORD='" + password + "'";
        if (!getNthDataAsString(sql, 1).equals("-1")) {
            return true;
        } else {
            return false;
        }

    }

    public static int insertData(String sql) {
        connect();
        try {
            Statement st = con.createStatement();
            return st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static String getLoginID(String username) {
        String sql = "SELECT ID FROM LOGIN WHERE LOGIN.USERNAME='" + username + "'";
        return getNthDataAsString(sql, 1);
    }

    public static boolean register(String name, String email, String designation, String username, String password) {
        String sql = "INSERT INTO LOGIN (USERNAME,PASSWORD) VALUES ('" + username + "','" + password + "')";
        insertData(sql);
        String sql2 = "INSERT INTO register (NAME,EMAIL,DESIGNATION,LOGIN_ID) VALUES "
                + "('" + name + "','" + email + "','" + designation + "','" + getLoginID(username) + "')";
        return insertData(sql2) > 0;
    }
}

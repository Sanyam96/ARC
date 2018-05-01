package src.database;

import src.models.PageModel;

import java.sql.*;

/*
* Class for Database connection
* */
public class DbConnection {

    /*
    * INSTRUCTIONS FOR DATABASE CONNECTIVITY:
    *
    * Create src.database in mysql by command: create database arcache;
    * use the created database by command: use arcache;
    * create table in database by command: CREATE TABLE `cache` (`pageId` int(11) DEFAULT NULL,`pageValue` int(11) DEFAULT NULL);
    *
    * */

    public static final String DB_TABLE_NAME = "cache";

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_NAME = "arcache";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;

    static final String DB_USER = "root";
    static final String DB_PASSWORD = "mysql";

    private static Connection conn = null;

    public DbConnection(){
        getConnection();
    }

    public static Connection getConnection() {
        if (conn != null) {
            return conn;
        }
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void clearConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        conn = null;
    }

    public void insertPageIntoDatabase(PageModel pageModel) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("INSERT into " + DB_TABLE_NAME + " VALUES (?,?)");
        ps.setInt(1, pageModel.getPageId());
        ps.setInt(2, pageModel.getPageValue());
        ps.executeUpdate();
    }

    public boolean checkIfPageExistsInDatabase(PageModel pageModel) throws SQLException {
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT  * from " + DB_TABLE_NAME + " WHERE  pageId = " + pageModel.getPageId());

        boolean exists = rs.next();

        rs.close();
        stmt.close();
        return exists;
    }

    public void updatePageInDatabase(PageModel pageModel) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("UPDATE " + DB_TABLE_NAME + " SET pageValue=? WHERE pageId=?");
        ps.setInt(2, pageModel.getPageId());
        ps.setInt(1, pageModel.getPageValue());
        ps.executeUpdate();
    }

    public void saveUpdate(PageModel pageModel) throws SQLException {
        if (checkIfPageExistsInDatabase(pageModel)) {
            updatePageInDatabase(pageModel);
        } else {
            insertPageIntoDatabase(pageModel);
        }
    }

}

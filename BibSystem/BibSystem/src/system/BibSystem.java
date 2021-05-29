package system;

import java.security.InvalidParameterException;
import java.sql.*;

public class BibSystem {

    private Connection dataBaseConnection;

    public BibSystem(String pathToDataBase) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        dataBaseConnection = DriverManager.getConnection("jdbc:sqlite:" + pathToDataBase);
    }

    public int getUserType(String login, String password) throws SQLException {
        Statement statement = dataBaseConnection.createStatement();

        ResultSet rs = statement.executeQuery("select user_type from User where login = " + prepareString(login) +
                " and password = " + prepareString(password) + ";");

        return rs.getInt(1);
    }


    public void insertUser(String name, String email, String login, String password) throws SQLException {

        try{
            isUserExist(login, email);
        } catch (Exception ex) {
            throw ex;
        }


        PreparedStatement insertStmt = dataBaseConnection.prepareStatement("INSERT into User(login, password," +
                " user_type, user_name, email) values(?, ?, ?, ?, ?);");

        insertStmt.setString(1, login);
        insertStmt.setString(2, password);
        insertStmt.setInt(3, 1);
        insertStmt.setString(4, name);
        insertStmt.setString(5, email);

        insertStmt.executeUpdate();
    }

    public void insertAuthor(String name) throws SQLException {
        PreparedStatement insertStmt = dataBaseConnection.prepareStatement("INSERT into Author(author_name)" +
                " values(?);");

        insertStmt.setString(1, name);

        insertStmt.executeUpdate();
    }

    private void isUserExist(String login, String email) throws SQLException {
        Statement statement = dataBaseConnection.createStatement();

        ResultSet rs = statement.executeQuery("select * from User where login = " + prepareString(login) +
                " or email = " + prepareString(email) + ";");

        if(!rs.next())
            return;

        if (rs.getString("login").equals(login))
            throw new InvalidParameterException("Such login already exists");

        if (rs.getString("email").equals(email))
            throw new InvalidParameterException("Such email already exists");

    }

    public String getUserName(String login, String password) throws SQLException {
        Statement statement = dataBaseConnection.createStatement();

        ResultSet rs = statement.executeQuery("select user_name from User where login = " + prepareString(login) +
                " and password = " + prepareString(password) + ";");

        return rs.getString(1);
    }

    public Connection getDataBaseConnection(){
        return dataBaseConnection;
    }

    private static String prepareString(String value){
        return "\'" + value + "\'";
    }
}

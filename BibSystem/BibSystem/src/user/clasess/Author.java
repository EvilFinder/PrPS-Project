package user.clasess;

import server.BibServer;
import system.BibSystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Author {


    public static String[] getPublicationList(int authorID) throws SQLException {
        ArrayList<String> publications = new ArrayList<String>();

        BibSystem system = BibServer.getBibSystem();
        Statement statement = system.getDataBaseConnection().createStatement();
        ResultSet rs = statement.executeQuery("select publication_name, year from Publication " +
                "where author_id = " + authorID + ";");
        while (rs.next()){
            publications.add(rs.getString(1) + " - " + rs.getInt(2));
        }

        return publications.toArray(new String[publications.size()]);
    }

    public static int getAuthorID(String authorName) throws SQLException {

        BibSystem system = BibServer.getBibSystem();
        Statement statement = system.getDataBaseConnection().createStatement();
        String preparedName = "'" + authorName + "'";
        ResultSet rs = statement.executeQuery("select id from Author " +
                "where author_name = " + preparedName + ";");

        return rs.getInt(1);
    }

    public static String[] getWrongPublications() throws SQLException{
        ArrayList<String> publications = new ArrayList<String>();

        BibSystem system = BibServer.getBibSystem();
        Statement statement = system.getDataBaseConnection().createStatement();
        ResultSet rs = statement.executeQuery("select publication_name, year from Publication " +
                "where author_id = " + 0 + ";");
        while (rs.next()){
            publications.add(rs.getString(1) + " - " + rs.getInt(2));
        }

        return publications.toArray(new String[publications.size()]);
    }

    public static void setPublicationToAuthor(String publicationName, int authorID) throws SQLException {
        BibSystem system = BibServer.getBibSystem();
        String preparedName = "'" + publicationName + "'";
        PreparedStatement statement = system.getDataBaseConnection().prepareStatement("update Publication " +
                "set author_id = " + authorID + " where publication_name = " + preparedName + " and author_id = " + 0 + ";");

        statement.executeUpdate();
    }

    public static void refusePublication(String publicationName, int authorID) throws SQLException{
        BibSystem system = BibServer.getBibSystem();
        String preparedName = "'" + publicationName + "'";
        PreparedStatement statement = system.getDataBaseConnection().prepareStatement("update Publication " +
                "set author_id = " + 0 + " where publication_name = " + preparedName + " and author_id = " + authorID + ";");

        statement.executeUpdate();
    }

    public static String getInformation(int authorID) throws SQLException{
        String report = "";

        BibSystem system = BibServer.getBibSystem();
        Statement statement = system.getDataBaseConnection().createStatement();
        ResultSet rs = statement.executeQuery("select count(*) from Publication;");

        int numPublications = rs.getInt(1);

        statement = system.getDataBaseConnection().createStatement();
        rs = statement.executeQuery("select count(*) from Publication where author_id = " + authorID + ";");

        return "You have " + rs.getInt(1) + " publication from " + numPublications + " general.";
    }

}

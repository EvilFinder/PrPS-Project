package user.clasess;

import server.BibServer;
import system.BibSystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SystemOperator {

    public static void addPublication(String publicationName) {
        BibSystem system = BibServer.getBibSystem();

    }

    public static String[] getDoubles() throws SQLException {
        ArrayList<String> doubles = new ArrayList<>();
        BibSystem system = BibServer.getBibSystem();
        Statement statement = system.getDataBaseConnection().createStatement();

        ResultSet rs = statement.executeQuery("select publication_name, count(*) from Publication " +
                "group by publication_name, year having count(*) > 1;");

        while (rs.next()){
            doubles.add(rs.getString(1));
        }

        return doubles.toArray(new String[doubles.size()]);
    }

    public static void deleteDuplicates() throws SQLException {
        BibSystem system = BibServer.getBibSystem();
        PreparedStatement statement = system.getDataBaseConnection().prepareStatement("DELETE FROM Publication" +
                " WHERE id NOT IN " +
                "  (SELECT MIN(id) " +
                "  FROM Publication " +
                "  GROUP BY publication_name)");

        statement.executeUpdate();
    }

    public static String[] getAllPublications() throws SQLException {
        ArrayList<String> publications = new ArrayList<>();
        BibSystem system = BibServer.getBibSystem();
        Statement statement = system.getDataBaseConnection().createStatement();

        ResultSet rs = statement.executeQuery("select publication_name, year, author_name from Publication;");

        while (rs.next()){
            publications.add(rs.getString(1) + " - " + rs.getInt(2) + " - " + rs.getString(3));
        }

        return publications.toArray(new String[publications.size()]);
    }

    public static void addPublication(String publicationName, String authorName, int year) throws SQLException{
        BibSystem system = BibServer.getBibSystem();
        Statement statement = system.getDataBaseConnection().createStatement();

        PreparedStatement insertStmt = system.getDataBaseConnection().prepareStatement("INSERT into Publication(publication_name, year, author_name, author_id)" +
                " values(?, ?, ?, ?);");

        insertStmt.setString(1, publicationName);
        insertStmt.setInt(2, year);
        insertStmt.setString(3, authorName);
        insertStmt.setInt(4, getAuthorID("'" + authorName + "'"));

        insertStmt.executeUpdate();
    }



    private static int getAuthorID(String authorName) throws SQLException{
        BibSystem system = BibServer.getBibSystem();
        Statement statement = system.getDataBaseConnection().createStatement();

        ResultSet rs = statement.executeQuery("select count(*), id from Author where author_name = " + authorName + ";");
        if(rs.next()){
            if(rs.getInt(1) > 1)
                return 0;
        } else {
            return 0;
        }


        return rs.getInt(2);
    }


}

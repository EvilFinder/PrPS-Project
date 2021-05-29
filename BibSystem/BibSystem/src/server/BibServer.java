package server;


import user.clasess.Author;
import user.frames.AdminWindow;
import user.frames.AuthorWindow;
import user.frames.AuthorizationWindow;
import system.BibSystem;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import org.jbibtex.*;

public class BibServer {
    private static BibSystem bibSystem = null;
    private static JFrame currentWindow = null;


    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        connectDataBase("database.db");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                currentWindow = new AuthorizationWindow(500, 500);
            }
        });


    }

    private static void connectDataBase(String pathToDataBase){
        try {
            bibSystem = new BibSystem(pathToDataBase);
            printFromServer("successfully connected to the data base.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void printFromServer(String message){
        System.out.println("Server: " + message);
    }

    public static void readBibTexFile(String fileName) throws ParseException {
        BibTeXParser bibTeXParser = new BibTeXParser();
    }

    public static void addNewUser(JFrame registrationForm, String name, String email, String login, String password){
        try {
            bibSystem.insertUser(name, email, login, password);
            bibSystem.insertAuthor(name);
            registrationForm.dispatchEvent(new WindowEvent(registrationForm, WindowEvent.WINDOW_CLOSING));

        } catch (SQLException e) {
            printFromServer(e.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(registrationForm.getRootPane(), ex.getMessage());
        }
    }

    public static void loginAccount(String login, String password){
        int userType = -1;

        try {
            userType = bibSystem.getUserType(login,password);
        } catch (SQLException e) {
            printFromServer("no such user");
            JOptionPane.showMessageDialog(currentWindow.getRootPane(), "Wrong login or password.");
            return;
        }

        if(userType == 0){
            printFromServer("admin entered");
            currentWindow.dispatchEvent(new WindowEvent(currentWindow, WindowEvent.WINDOW_CLOSING));
            currentWindow = new AdminWindow(500, 500);
        } else {
            printFromServer("author entered ");
            currentWindow.dispatchEvent(new WindowEvent(currentWindow, WindowEvent.WINDOW_CLOSING));
            currentWindow = null;
            try {
                currentWindow = new AuthorWindow(500, 500);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                String authorName = bibSystem.getUserName(login, password);
                ((AuthorWindow) currentWindow).setAuthorID(Author.getAuthorID(authorName));
            } catch (Exception ex){
                JOptionPane.showMessageDialog(currentWindow.getRootPane(), ex.getMessage());
            }
        }

    }

    public static BibSystem getBibSystem() {
        return bibSystem;
    }

}

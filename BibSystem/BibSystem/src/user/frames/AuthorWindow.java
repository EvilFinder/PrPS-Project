package user.frames;

import user.clasess.Author;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthorWindow extends JFrame {
    private int authorID = 0;
    private JPanel rootPanel;
    private JTextArea outputTextArea;
    private JButton getPublicationListButton;
    private JButton getWrongPublicationsButton;
    private JButton setPublicationButton;
    private JButton getPublicationsInformationButton;
    private JTextField setPublicationTextField;
    private JButton refusePublicationButton;
    private JTextField refusePublicationTextField;

    public AuthorWindow(int width, int height){

        setSize(width, height);
        setTitle("Author activity");
        setVisible(true);
        add(rootPanel);

        getPublicationListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] publications = Author.getPublicationList(authorID);
                    String output = "You have " + publications.length + " publications:\n";

                    for (String publication : publications){
                        output += publication + "\n";
                    }
                    outputTextArea.setText(output);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        getWrongPublicationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] publications = Author.getWrongPublications();
                    String output = "System has " + publications.length + " wrong publications:\n";

                    for (String publication : publications){
                        output += publication + "\n";
                    }
                    outputTextArea.setText(output);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        setPublicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Author.setPublicationToAuthor(setPublicationTextField.getText(), authorID);
                    setPublicationTextField.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        refusePublicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Author.refusePublication(refusePublicationTextField.getText(), authorID);
                    refusePublicationTextField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, "ERROR:" + ex.getMessage());
                }
            }
        });
        getPublicationsInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    outputTextArea.setText(Author.getInformation(authorID));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, "ERROR:" + ex.getMessage());
                }
            }
        });
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public int getAuthorID() {
        return authorID;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

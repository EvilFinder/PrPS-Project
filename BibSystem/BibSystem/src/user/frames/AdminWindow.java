package user.frames;

import user.clasess.SystemOperator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminWindow extends JFrame {
    private JPanel rootPanel;
    private JTextArea outputTextArea;
    private JButton getDuplicatesButton;
    private JTextField publicationToAddNameTextField;
    private JButton addPublicationButton;
    private JButton deleteDuplicatesButton;
    private JButton getAllPublicationsButton;

    public AdminWindow(int width, int height){
        setSize(width, height);
        setTitle("Admin activity");
        setVisible(true);
        add(rootPanel);

        getDuplicatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] duplicates = SystemOperator.getDoubles();
                    String output = "System has " + duplicates.length + " duplicating publication:\n";

                    for (String duplicate : duplicates){
                        output += duplicate + "\n";
                    }
                    outputTextArea.setText(output);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        deleteDuplicatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SystemOperator.deleteDuplicates();
                    JOptionPane.showMessageDialog(rootPane, "Deleted successfully");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        getAllPublicationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] publications = SystemOperator.getAllPublications();
                    String output = "System has " + publications.length + " publications:\n";

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
        addPublicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] parameters = publicationToAddNameTextField.getText().split(",");
                    if(parameters.length == 3)
                        SystemOperator.addPublication(parameters[0].trim(), parameters[1].trim(), new Integer(parameters[2].trim()));
                    else
                        JOptionPane.showMessageDialog(rootPane, "Wrong number of parameters.\n" +
                                "Try publication_name, author_name, year.");
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
}

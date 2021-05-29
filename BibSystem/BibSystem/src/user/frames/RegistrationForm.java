package user.frames;

import org.jbibtex.BibTeXParser;
import server.BibServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class RegistrationForm extends JFrame {
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JTextField loginTextField;
    private JTextField passwordTextField;
    private JTextField confirmPasswordTextField;
    private JButton createButton;
    private JPanel rootPanel;
    private JFrame thisFrame;

    public RegistrationForm(int width, int height){
        setTitle("Registration");
        setSize(width, height);
        add(rootPanel);

        setVisible(true);
        thisFrame = this;
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameTextField.getText().equals("") || emailTextField.getText().equals("")
                        || loginTextField.getText().equals("") || passwordTextField.getText().equals("")
                || confirmPasswordTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(rootPanel,
                            "You haven't entered required information",
                            "Invalid data",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(!passwordTextField.getText().equals(confirmPasswordTextField.getText())){
                    JOptionPane.showMessageDialog(rootPanel,
                            "Passwords don't match.",
                            "Invalid data",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                BibServer.addNewUser(thisFrame ,nameTextField.getText(), emailTextField.getText(),
                        loginTextField.getText(), passwordTextField.getText());

            }
        });
    }
}

package user.frames;

import server.BibServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthorizationWindow extends JFrame {
    private JLabel welcomeLabel;
    private JTextField loginTextField;
    private JButton enterButton;
    private JPanel rootPanel;
    private JPasswordField passwordTextField;
    private JButton registrationButton;

    public AuthorizationWindow(int width, int height){
        add(rootPanel);

        setTitle("Authorization window");
        setSize(width, height);
        setVisible(true);

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loginTextField.getText().equals("") || passwordTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(rootPanel,
                            "You haven't entered required information",
                            "Invalid data",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                BibServer.loginAccount(loginTextField.getText(), passwordTextField.getText());
            }

        });
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationForm(500, 500);
            }
        });
    }

    public JPanel getRootPanel(){
        return rootPanel;
    }
}

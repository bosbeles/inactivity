package examples.imageviewer;/*
Example of MiG Layout. Java Layout Manager for Swing, SWT and JavaFX 2
http://www.miglayout.com/

Mauro Masciadro
Masciar®
*/

import javax.swing.*;

import net.miginfocom.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JPanel {

	JLabel lblUsername = new JLabel("Username:");
	JLabel lblPassword = new JLabel("Password");
	JTextField txtUsername = new JTextField(20);
	JPasswordField txtPassword = new JPasswordField(20);
	JButton btnLogin = new JButton("Login");
	
	JPanel panelUsername = new JPanel();
	JPanel panelPassword = new JPanel();
	JPanel panelLogin = new JPanel();

	public Login() {

		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		//User name
		this.add(lblUsername, gc);
        gc.gridx = 1;
		this.add(txtUsername, gc);

		//User password
        gc.gridy = 1;
        gc.gridx = 0;
		this.add(lblPassword, gc);
        gc.gridx = 1;
		this.add(txtPassword, gc);

        gc.gridy = 2;
        gc.gridx = 0;
		this.add(btnLogin, gc);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtUsername.setText("");
                txtPassword.setText("");
            }
        });
		
		this.setVisible(true);

	}

    public boolean isSuccessful(){
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        return isValid("ihsan", "ihsan") || isValid("admin", "admin");

    }

    private boolean isValid(String u, String p) {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        return username.equals(u) && password.equals(p);
    }

    public void setAction(Action action) {

        btnLogin.setAction(action);
        btnLogin.setText("Login");
    }

	public static void main(String[] args) {

	}

}
package mx.udlap.medagenda.ui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;

    public LoginFrame() {
        super("MedAgenda MX - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3,2,5,5));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        panel.add(new JLabel("Usuario:"));
        txtUser = new JTextField();
        panel.add(txtUser);

        panel.add(new JLabel("Contraseña:"));
        txtPass = new JPasswordField();
        panel.add(txtPass);

        btnLogin = new JButton("Acceder");
        panel.add(new JLabel()); // filler
        panel.add(btnLogin);

        btnLogin.addActionListener(e -> onLogin());

        add(panel);
    }

    private void onLogin() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        if(user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // TODO: validar credenciales contra BD
        boolean ok = user.equals("admin") && pass.equals("1234");
        if(ok) {
            SwingUtilities.invokeLater(() -> {
                new DashboardFrame().setVisible(true);
            });
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas.","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

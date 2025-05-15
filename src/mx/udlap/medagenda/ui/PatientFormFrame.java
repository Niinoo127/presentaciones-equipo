package mx.udlap.medagenda.ui;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PatientFormFrame extends JFrame {
    private JTextField txtName, txtDob, txtPhone, txtEmail, txtEmergency;
    private JButton btnSave, btnClear;

    public PatientFormFrame() {
        super("Registrar Paciente");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(6,2,5,5));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        panel.add(new JLabel("Nombre:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Fecha Nac. (dd/MM/yyyy):"));
        txtDob = new JTextField();
        panel.add(txtDob);

        panel.add(new JLabel("Teléfono:"));
        txtPhone = new JTextField();
        panel.add(txtPhone);

        panel.add(new JLabel("Correo:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Emergencia:"));
        txtEmergency = new JTextField();
        panel.add(txtEmergency);

        btnSave = new JButton("Guardar");
        btnClear = new JButton("Limpiar");
        panel.add(btnSave);
        panel.add(btnClear);

        btnSave.addActionListener(e -> onSave());
        btnClear.addActionListener(e -> onClear());

        add(panel);
    }

    private void onSave() {
        String name = txtName.getText().trim();
        String dob = txtDob.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String emergency = txtEmergency.getText().trim();

        if(name.isEmpty()|| dob.isEmpty()|| phone.isEmpty()|| email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos obligatorios.","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validar fecha
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(dob);
        } catch(ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido.","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validar email
        if(!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Correo inválido.","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // TODO: guardar en BD
        JOptionPane.showMessageDialog(this, "Paciente registrado correctamente.","Éxito", JOptionPane.INFORMATION_MESSAGE);
        onClear();
    }

    private void onClear() {
        txtName.setText("");
        txtDob.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtEmergency.setText("");
    }
}
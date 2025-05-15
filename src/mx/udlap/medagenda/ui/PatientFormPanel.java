package mx.udlap.medagenda.ui;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PatientFormPanel extends JPanel {
    public PatientFormPanel(MainFrame parent) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Registrar Paciente", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6,2,5,5));
        JTextField txtName = new JTextField(), txtDob = new JTextField();
        JTextField txtPhone = new JTextField(), txtEmail = new JTextField(), txtEmergency = new JTextField();

        form.add(new JLabel("Nombre:"));       form.add(txtName);
        form.add(new JLabel("Fecha Nac. (dd/MM/yyyy):")); form.add(txtDob);
        form.add(new JLabel("Teléfono:"));     form.add(txtPhone);
        form.add(new JLabel("Correo:"));       form.add(txtEmail);
        form.add(new JLabel("Emergencia:"));   form.add(txtEmergency);

        JButton btnSave = new JButton("Guardar");
        JButton btnClear = new JButton("Limpiar");
        JButton btnAgendarCita = new JButton("Agendar Cita");

        form.add(btnSave); form.add(btnClear);

        add(form, BorderLayout.CENTER);

        JPanel extra = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        extra.add(btnAgendarCita);
        add(extra, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            if(txtName.getText().trim().isEmpty() || txtDob.getText().trim().isEmpty() ||
               txtPhone.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Debe llenar todos los campos obligatorios.","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try { new SimpleDateFormat("dd/MM/yyyy").parse(txtDob.getText()); }
            catch(ParseException ex) {
                JOptionPane.showMessageDialog(parent, "Formato de fecha inválido.","Error", JOptionPane.ERROR_MESSAGE); return;
            }
            if(!txtEmail.getText().contains("@")) {
                JOptionPane.showMessageDialog(parent, "Correo inválido.","Error", JOptionPane.ERROR_MESSAGE); return;
            }
            MainFrame.Paciente p = new MainFrame.Paciente(
                txtName.getText(), txtDob.getText(), txtPhone.getText(), txtEmail.getText(), txtEmergency.getText()
            );
            parent.pacientes.add(p);
            JOptionPane.showMessageDialog(parent, "Paciente registrado correctamente.","Éxito", JOptionPane.INFORMATION_MESSAGE);
            btnClear.doClick();
        });

        btnClear.addActionListener(e -> {
            txtName.setText(""); txtDob.setText(""); txtPhone.setText(""); txtEmail.setText(""); txtEmergency.setText("");
        });

        btnAgendarCita.addActionListener(e -> {
            // Si aún no se ha guardado el paciente, lo guardamos
            if(txtName.getText().trim().isEmpty() || txtDob.getText().trim().isEmpty() ||
               txtPhone.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Debe llenar todos los campos antes de agendar cita.","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MainFrame.Paciente paciente = new MainFrame.Paciente(
                txtName.getText(), txtDob.getText(), txtPhone.getText(), txtEmail.getText(), txtEmergency.getText()
            );
            if(!parent.pacientes.contains(paciente)) parent.pacientes.add(paciente);
            // Cambia al panel de citas y pasa el paciente
            parent.panelCentral.removeAll();
            parent.panelCentral.add(new AppointmentManagementPanel(parent, paciente), BorderLayout.CENTER);
            parent.panelCentral.revalidate(); parent.panelCentral.repaint();
            parent.btnPacientes.setVisible(false);
            parent.btnCitas.setVisible(false);
            parent.btnReportes.setVisible(false);
            parent.btnBack.setVisible(true);
        });
    }
}
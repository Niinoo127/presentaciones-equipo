package mx.udlap.medagenda.ui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    public DashboardFrame() {
        super("MedAgenda MX – Panel Principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,200);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton btnPacientes = new JButton("Gestión Pacientes");
        JButton btnCitas     = new JButton("Gestión Citas");
        JButton btnReportes  = new JButton("Reportes");

        btnPacientes.addActionListener(e -> new PatientFormFrame().setVisible(true));
        btnCitas    .addActionListener(e -> new AppointmentManagementFrame().setVisible(true));
        btnReportes .addActionListener(e -> new ReportsFrame().setVisible(true));

        panel.add(btnPacientes);
        panel.add(btnCitas);
        panel.add(btnReportes);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardFrame().setVisible(true);
        });
    }
}
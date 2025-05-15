package mx.udlap.medagenda.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    // Listas que persisten durante la ejecución
    ArrayList<Paciente> pacientes = new ArrayList<>();
    ArrayList<Cita> citas = new ArrayList<>();

    public JPanel panelCentral;
    public JButton btnPacientes, btnCitas, btnReportes, btnBack;

    public MainFrame() {
        super("MedAgenda MX – Sistema Integral");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPacientes = new JButton("Gestión Pacientes");
        btnCitas     = new JButton("Gestión Citas");
        btnReportes  = new JButton("Reportes");
        btnBack      = new JButton("Regresar");
        btnBack.setVisible(false);

        topBar.add(btnPacientes);
        topBar.add(btnCitas);
        topBar.add(btnReportes);
        topBar.add(btnBack);

        panelCentral = new JPanel(new BorderLayout());
        showMenuPrincipal();

        btnPacientes.addActionListener(e -> {
            panelCentral.removeAll();
            panelCentral.add(new PatientFormPanel(this), BorderLayout.CENTER);
            panelCentral.revalidate(); panelCentral.repaint();
            toggleMenu(false);
        });
        btnCitas.addActionListener(e -> {
            panelCentral.removeAll();
            panelCentral.add(new AppointmentManagementPanel(this), BorderLayout.CENTER);
            panelCentral.revalidate(); panelCentral.repaint();
            toggleMenu(false);
        });
        btnReportes.addActionListener(e -> {
            panelCentral.removeAll();
            panelCentral.add(new ReportsPanel(this), BorderLayout.CENTER);
            panelCentral.revalidate(); panelCentral.repaint();
            toggleMenu(false);
        });
        btnBack.addActionListener(e -> {
            showMenuPrincipal();
            toggleMenu(true);
        });

        setLayout(new BorderLayout());
        add(topBar, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }

    private void showMenuPrincipal() {
        panelCentral.removeAll();
        JLabel lbl = new JLabel("Bienvenido a MedAgenda MX", JLabel.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 28));
        panelCentral.add(lbl, BorderLayout.CENTER);
        panelCentral.revalidate(); panelCentral.repaint();
    }

    private void toggleMenu(boolean menu) {
        btnPacientes.setVisible(menu);
        btnCitas.setVisible(menu);
        btnReportes.setVisible(menu);
        btnBack.setVisible(!menu);
    }

    // Clases internas para datos
    public static class Paciente {
        public String nombre, fechaNac, telefono, correo, emergencia;
        public Paciente(String n, String f, String t, String c, String e) {
            nombre = n; fechaNac = f; telefono = t; correo = c; emergencia = e;
        }
        public String toString() { return nombre + " (" + telefono + ")"; }
    }

    public static class Cita {
        public Paciente paciente;
        public String fecha, hora, estado;
        public Cita(Paciente p, String f, String h, String e) {
            paciente = p; fecha = f; hora = h; estado = e;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

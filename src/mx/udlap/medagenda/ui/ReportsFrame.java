package mx.udlap.medagenda.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ReportsFrame extends JFrame {
    private JComboBox<String> cbStatus;
    private JTextField txtDate;
    private JButton btnGenerate;
    private JTable tbl;

    public ReportsFrame() {
        super("Reportes de Citas");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel pnlFilters = new JPanel(new FlowLayout());
        pnlFilters.add(new JLabel("Fecha (dd/MM/yyyy):"));
        txtDate = new JTextField(10);
        pnlFilters.add(txtDate);

        pnlFilters.add(new JLabel("Estado:"));
        cbStatus = new JComboBox<>(new String[]{"Todos","Pendiente","Confirmada","Cancelada"});
        pnlFilters.add(cbStatus);

        btnGenerate = new JButton("Generar");
        pnlFilters.add(btnGenerate);

        modelInit();
        btnGenerate.addActionListener(e -> loadData());

        add(pnlFilters, BorderLayout.NORTH);
        add(new JScrollPane(tbl), BorderLayout.CENTER);
    }

    private void modelInit() {
        tbl = new JTable(new DefaultTableModel(new Object[]{"ID","Paciente","Fecha","Estado"},0));
    }

    private void loadData() {
        // TODO: filtrar y cargar datos de BD
        DefaultTableModel m = (DefaultTableModel) tbl.getModel();
        m.setRowCount(0);
        // Ejemplo:
        m.addRow(new Object[]{1,"Juan", "10/05/2025", "Confirmada"});
    }
}
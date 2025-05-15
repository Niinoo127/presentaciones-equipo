package mx.udlap.medagenda.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ReportsPanel extends JPanel {
    private JComboBox<String> cbStatus;
    private JTextField txtDate;
    private JButton btnGenerate;
    private JTable tbl;
    private MainFrame mainFrame;

    public ReportsPanel(MainFrame parent) {
        this.mainFrame = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Reportes de Citas", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel pnlFilters = new JPanel(new FlowLayout());
        pnlFilters.add(new JLabel("Fecha (dd/MM/yyyy):"));
        txtDate = new JTextField(10);
        pnlFilters.add(txtDate);

        pnlFilters.add(new JLabel("Estado:"));
        cbStatus = new JComboBox<>(new String[]{"Todos","Pendiente","Confirmada","Cancelada"});
        pnlFilters.add(cbStatus);

        btnGenerate = new JButton("Generar");
        pnlFilters.add(btnGenerate);

        tbl = new JTable(new DefaultTableModel(new Object[]{"Paciente","Fecha","Hora","Estado"},0));
        btnGenerate.addActionListener(e -> loadData());

        add(pnlFilters, BorderLayout.SOUTH);
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Carga inicial
        loadData();
    }

    private void loadData() {
        DefaultTableModel m = (DefaultTableModel) tbl.getModel();
        m.setRowCount(0);
        String filtroFecha = txtDate.getText().trim();
        String filtroEstado = cbStatus.getSelectedItem().toString();

        for (MainFrame.Cita c : mainFrame.citas) {
            boolean matches = true;
            if(!filtroFecha.isEmpty() && !c.fecha.equals(filtroFecha)) matches = false;
            if(!filtroEstado.equals("Todos") && !c.estado.equalsIgnoreCase(filtroEstado)) matches = false;
            if(matches) {
                m.addRow(new Object[]{
                    c.paciente.toString(), c.fecha, c.hora, c.estado
                });
            }
        }
    }
}

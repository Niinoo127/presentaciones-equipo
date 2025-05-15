package mx.udlap.medagenda.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class AppointmentManagementPanel extends JPanel {
    private JTable tbl;
    private DefaultTableModel model;
    private MainFrame mainFrame;

    // Sobrecarga para permitir preselección de paciente
    public AppointmentManagementPanel(MainFrame parent) {
        this(parent, null);
    }
    public AppointmentManagementPanel(MainFrame parent, MainFrame.Paciente pacientePreseleccionado) {
        mainFrame = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Gestión de Citas", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Paciente","Fecha","Hora","Estado"},0);
        tbl = new JTable(model);
        JScrollPane scroll = new JScrollPane(tbl);

        JButton btnAdd = new JButton("Agregar"), btnEdit = new JButton("Editar"), btnDelete = new JButton("Eliminar");

        btnAdd.addActionListener(e -> showForm(null, -1, pacientePreseleccionado));
        btnEdit.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if(r>=0) showForm(model.getDataVector().elementAt(r), r, null);
            else JOptionPane.showMessageDialog(parent, "Selecciona una cita para editar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        });
        btnDelete.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if(r>=0) {
                int confirm = JOptionPane.showConfirmDialog(parent, "¿Eliminar la cita seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    mainFrame.citas.remove(r);
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(parent, "Selecciona una cita para eliminar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlButtons.add(btnAdd); pnlButtons.add(btnEdit); pnlButtons.add(btnDelete);

        add(pnlButtons, BorderLayout.SOUTH);
        add(scroll, BorderLayout.CENTER);

        // Al regresar al panel, carga las citas previas
        refreshTable();

        // Si viene paciente preseleccionado, muestra directamente el formulario de cita
        if (pacientePreseleccionado != null) SwingUtilities.invokeLater(() -> btnAdd.doClick());
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (MainFrame.Cita c : mainFrame.citas) {
            model.addRow(new Object[]{
                c.paciente.toString(), c.fecha, c.hora, c.estado
            });
        }
    }

    private void showForm(Object rowData, int rowIndex, MainFrame.Paciente preselected) {
        JDialog dlg = new JDialog(mainFrame, rowIndex == -1 ? "Agregar Cita" : "Editar Cita", true);
        dlg.setSize(350, 250);
        dlg.setLayout(new GridLayout(5, 2, 5, 5));

        JComboBox<MainFrame.Paciente> cbPaciente = new JComboBox<>();
        for (MainFrame.Paciente p : mainFrame.pacientes) cbPaciente.addItem(p);
        if (preselected != null) cbPaciente.setSelectedItem(preselected);

        JTextField txtFecha = new JTextField();
        JTextField txtHora = new JTextField();
        JComboBox<String> cbEstado = new JComboBox<>(new String[] {"Pendiente","Confirmada","Cancelada"});

        if(rowData != null && rowData instanceof java.util.Vector) {
            java.util.Vector<?> data = (java.util.Vector<?>) rowData;
            cbPaciente.setSelectedItem(getPacienteByString(data.get(0).toString()));
            txtFecha.setText(data.get(1).toString());
            txtHora.setText(data.get(2).toString());
            cbEstado.setSelectedItem(data.get(3).toString());
        }

        dlg.add(new JLabel("Paciente:")); dlg.add(cbPaciente);
        dlg.add(new JLabel("Fecha (dd/MM/yyyy):")); dlg.add(txtFecha);
        dlg.add(new JLabel("Hora (HH:mm):")); dlg.add(txtHora);
        dlg.add(new JLabel("Estado:")); dlg.add(cbEstado);

        dlg.add(new JLabel(""));
        JButton btnGuardar = new JButton(rowIndex == -1 ? "Agregar" : "Actualizar");
        dlg.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            if (cbPaciente.getSelectedItem() == null || txtFecha.getText().trim().isEmpty()
                    || txtHora.getText().trim().isEmpty() || cbEstado.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(dlg, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MainFrame.Paciente paciente = (MainFrame.Paciente) cbPaciente.getSelectedItem();
            String fecha = txtFecha.getText(), hora = txtHora.getText(), estado = cbEstado.getSelectedItem().toString();
            if(rowIndex == -1) {
                MainFrame.Cita cita = new MainFrame.Cita(paciente, fecha, hora, estado);
                mainFrame.citas.add(cita);
            } else {
                mainFrame.citas.get(rowIndex).paciente = paciente;
                mainFrame.citas.get(rowIndex).fecha = fecha;
                mainFrame.citas.get(rowIndex).hora = hora;
                mainFrame.citas.get(rowIndex).estado = estado;
            }
            refreshTable();
            dlg.dispose();
        });

        dlg.setLocationRelativeTo(mainFrame);
        dlg.setVisible(true);
    }

    private MainFrame.Paciente getPacienteByString(String s) {
        for (MainFrame.Paciente p : mainFrame.pacientes)
            if (p.toString().equals(s)) return p;
        return null;
    }
}
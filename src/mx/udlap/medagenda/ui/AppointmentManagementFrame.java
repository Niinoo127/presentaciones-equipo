package mx.udlap.medagenda.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class AppointmentManagementFrame extends JFrame {
    private JTable tbl;
    private DefaultTableModel model;
    private JButton btnAdd, btnEdit, btnDelete;

    public AppointmentManagementFrame() {
        super("Gestión de Citas");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        model = new DefaultTableModel(new Object[]{"ID","Paciente","Fecha","Hora","Estado"},0);
        tbl = new JTable(model);
        JScrollPane scroll = new JScrollPane(tbl);

        btnAdd = new JButton("Agregar");
        btnEdit = new JButton("Editar");
        btnDelete = new JButton("Eliminar");

        btnAdd.addActionListener(e -> showForm(null, -1));
        btnEdit.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if(r>=0) {
                showForm(model.getDataVector().elementAt(r), r);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una cita para editar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnDelete.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if(r>=0) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar la cita seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(r);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una cita para eliminar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnEdit);
        pnlButtons.add(btnDelete);

        add(pnlButtons, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    // Si rowIndex es -1, es agregar; si no, es editar
    private void showForm(Object rowData, int rowIndex) {
        JDialog dlg = new JDialog(this, rowIndex == -1 ? "Agregar Cita" : "Editar Cita", true);
        dlg.setSize(350, 300);
        dlg.setLayout(new GridLayout(6, 2, 5, 5));

        JTextField txtPaciente = new JTextField();
        JTextField txtFecha = new JTextField();
        JTextField txtHora = new JTextField();
        JTextField txtEstado = new JTextField();

        // Si es edición, carga los datos actuales
        if(rowData != null && rowData instanceof java.util.Vector) {
            java.util.Vector<?> data = (java.util.Vector<?>) rowData;
            txtPaciente.setText(data.get(1).toString());
            txtFecha.setText(data.get(2).toString());
            txtHora.setText(data.get(3).toString());
            txtEstado.setText(data.get(4).toString());
        }

        dlg.add(new JLabel("Paciente:"));
        dlg.add(txtPaciente);

        dlg.add(new JLabel("Fecha (dd/MM/yyyy):"));
        dlg.add(txtFecha);

        dlg.add(new JLabel("Hora (HH:mm):"));
        dlg.add(txtHora);

        dlg.add(new JLabel("Estado:"));
        dlg.add(txtEstado);

        dlg.add(new JLabel(""));
        JButton btnGuardar = new JButton(rowIndex == -1 ? "Agregar" : "Actualizar");
        dlg.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            if (txtPaciente.getText().trim().isEmpty() ||
                txtFecha.getText().trim().isEmpty() ||
                txtHora.getText().trim().isEmpty() ||
                txtEstado.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dlg, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(rowIndex == -1) {
                // Agregar
                DefaultTableModel m = (DefaultTableModel) tbl.getModel();
                m.addRow(new Object[]{
                    m.getRowCount() + 1,
                    txtPaciente.getText(),
                    txtFecha.getText(),
                    txtHora.getText(),
                    txtEstado.getText()
                });
            } else {
                // Editar
                model.setValueAt(txtPaciente.getText(), rowIndex, 1);
                model.setValueAt(txtFecha.getText(), rowIndex, 2);
                model.setValueAt(txtHora.getText(), rowIndex, 3);
                model.setValueAt(txtEstado.getText(), rowIndex, 4);
            }
            dlg.dispose();
        });

        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }
}
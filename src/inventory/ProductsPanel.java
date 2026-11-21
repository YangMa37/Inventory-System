/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package inventory;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class ProductsPanel extends javax.swing.JPanel implements Refreshable {

    private static final String[] CATEGORIES
            = {"Groceries", "Drinks", "Snacks", "School", "Hygiene", "Other"};
    private static final String[] COLUMNS
            = {"Code", "Name", "Category", "Price", "Stock"};
    private static final Color LOW_COLOR = new Color(198, 40, 40);
    private InventoryManager im;
    private String user;
    private DefaultTableModel model;
    private String lastSearch = "";

    /**
     * Creates new form ProductsPanel
     */
    public ProductsPanel() {
        initComponents();
    }

    public void setup(InventoryManager im, String user) {
        this.im = im;
        this.user = user;

        model = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;

            }

            @Override
            public Class<?> getColumnClass(int col) {
                if (col == 3) {
                    return BigDecimal.class;

                }
                if (col == 4) {
                    return Integer.class;
                }
                return String.class;

            }
        };

        tblProducts.setModel(model);
        tblProducts.setAutoCreateRowSorter(true);
        tblProducts.setDefaultRenderer(Object.class, new RowRenderer());
        tblProducts.setDefaultRenderer(Number.class, new RowRenderer());
        tblProducts.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromSelection();
            }
        });

        cboCategory.setModel(new DefaultComboBoxModel<>(CATEGORIES));
        ((FlowLayout) pnlButtons.getLayout()).setAlignment(FlowLayout.LEFT);
        refreshTable();
    }

    private class RowRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean selected, boolean focus,
                int row, int col) {
            if (value instanceof BigDecimal) {
                value = ((BigDecimal) value).toPlainString();
            }
            Component c = super.getTableCellRendererComponent(table, value, selected, focus, row, col);
            setHorizontalAlignment(col >= 3 ? RIGHT : LEFT);
            int modelRow = table.convertRowIndexToModel(row);
            int stock = (Integer) table.getModel().getValueAt(modelRow, 4);
            boolean low = stock <= Product.LOW_STOCK;

            if (selected) {
                c.setForeground(table.getSelectionForeground());
            } else {
                c.setForeground(low ? LOW_COLOR : table.getForeground());

            }
            c.setFont(c.getFont().deriveFont(low ? Font.BOLD : Font.PLAIN));
            return c;
        }
    }

    private void refreshTable() {
        List<? extends RowSorter.SortKey> keys
                = tblProducts.getRowSorter().getSortKeys();
        List<Product> found = im.search(txtSearch.getText());
        Object[][] data = new Object[found.size()][];
        for (int i = 0; i < found.size(); i++) {
            Product p = found.get(i);
            data[i] = new Object[]{p.getCode(), p.getName(),
                p.getCategory(), p.getPrice(), p.getStock()};
        }
        model.setDataVector(data, COLUMNS);
        tblProducts.getRowSorter().setSortKeys(keys);
    }

    private void fillFormFromSelection() {
        int row = tblProducts.getSelectedRow();
        if (row < 0) {
            return;
        }
        int m = tblProducts.convertRowIndexToModel(row);
        txtCode.setText(model.getValueAt(m, 0).toString());
        txtName.setText(model.getValueAt(m, 1).toString());
        cboCategory.setSelectedItem(model.getValueAt(m, 2).toString());
        txtPrice.setText(model.getValueAt(m, 3).toString());
        txtStock.setText(model.getValueAt(m, 4).toString());
        txtCode.setEditable(false);
    }

    private void clearForm() {
        tblProducts.clearSelection();
        txtCode.setText("");
        txtName.setText("");
        cboCategory.setSelectedIndex(0);
        txtPrice.setText("");
        txtStock.setText("");
        txtRestockQty.setText("");
        txtCode.setEditable(true);
    }

    void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Inventory",
                JOptionPane.WARNING_MESSAGE);
    }

    boolean confirmDelete(String name) {
        return JOptionPane.showConfirmDialog(this, "Delete " + name + "?",
                "Confirm delete", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }

    @Override
    public void refresh() {

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSearch = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnClearSearch = new javax.swing.JButton();
        lblSearch = new javax.swing.JLabel();
        pnlBottom = new javax.swing.JPanel();
        pnlFields = new javax.swing.JPanel();
        lblCode = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        cboCategory = new javax.swing.JComboBox<>();
        txtPrice = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        pnlButtons = new javax.swing.JPanel();
        btdAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        lblRestock = new javax.swing.JLabel();
        txtRestockQty = new javax.swing.JTextField();
        btnRestock = new javax.swing.JButton();
        scrProducts = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        pnlSearch.setLayout(new java.awt.BorderLayout());

        txtSearch.setText(" ");
        pnlSearch.add(txtSearch, java.awt.BorderLayout.CENTER);

        btnClearSearch.setText("Clear");
        btnClearSearch.addActionListener(this::btnClearSearchActionPerformed);
        pnlSearch.add(btnClearSearch, java.awt.BorderLayout.LINE_END);

        add(pnlSearch, java.awt.BorderLayout.PAGE_START);

        lblSearch.setText("Search");
        add(lblSearch, java.awt.BorderLayout.LINE_START);

        pnlBottom.setPreferredSize(new java.awt.Dimension(100, 100));
        pnlBottom.setLayout(new java.awt.GridLayout(2, 1));

        pnlFields.setLayout(new java.awt.GridLayout(2, 5));

        lblCode.setText("jLabel1");
        pnlFields.add(lblCode);

        lblName.setText("jLabel2");
        pnlFields.add(lblName);

        lblCategory.setText("jLabel3");
        pnlFields.add(lblCategory);

        lblPrice.setText("jLabel4");
        pnlFields.add(lblPrice);

        lblStock.setText("jLabel5");
        pnlFields.add(lblStock);

        txtCode.setText(" ");
        pnlFields.add(txtCode);

        txtName.setText(" ");
        pnlFields.add(txtName);

        cboCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlFields.add(cboCategory);

        txtPrice.setText(" ");
        pnlFields.add(txtPrice);

        txtStock.setText(" ");
        pnlFields.add(txtStock);

        pnlBottom.add(pnlFields);

        btdAdd.setText("Add");
        btdAdd.addActionListener(this::btdAddActionPerformed);
        pnlButtons.add(btdAdd);

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);
        pnlButtons.add(btnUpdate);

        btnDelete.setText("Delete");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);
        pnlButtons.add(btnDelete);

        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);
        pnlButtons.add(btnClear);

        lblRestock.setText("Restock quantity");
        pnlButtons.add(lblRestock);

        txtRestockQty.setColumns(5);
        txtRestockQty.setText(" ");
        pnlButtons.add(txtRestockQty);

        btnRestock.setText("Restock");
        btnRestock.addActionListener(this::btnRestockActionPerformed);
        pnlButtons.add(btnRestock);

        pnlBottom.add(pnlButtons);

        add(pnlBottom, java.awt.BorderLayout.PAGE_END);

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrProducts.setViewportView(tblProducts);

        add(scrProducts, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdAddActionPerformed
        String error = im.add(txtCode.getText(), txtName.getText(),
                (String) cboCategory.getSelectedItem(),
                txtPrice.getText(), txtStock.getText());
        if (error != null) {
            showError(error);
            return;
        }
        refreshTable();
        clearForm();
    }//GEN-LAST:event_btdAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (tblProducts.getSelectedRow() < 0) {
            showError("Select a product in the table first.");
            return;
        }
        String error = im.update(txtCode.getText(), txtName.getText(),
                (String) cboCategory.getSelectedItem(),
                txtPrice.getText(), txtStock.getText());
        if (error != null) {
            showError(error);
            return;
        }
        refreshTable();
        clearForm();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (tblProducts.getSelectedRow() < 0) {
            showError("Select a product in the table first.");
            return;
        }
        if (!confirmDelete(txtName.getText())) {
            return;
        }
        im.delete(txtCode.getText());
        refreshTable();
        clearForm();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnClearSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSearchActionPerformed
        txtSearch.setText("");
    }//GEN-LAST:event_btnClearSearchActionPerformed

    private void btnRestockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestockActionPerformed
        if (tblProducts.getSelectedRow() < 0) {
            showError("Select a product in the table first.");
            return;
        }
        String error = im.restock(txtCode.getText(),
                txtRestockQty.getText(), user);
        if (error != null) {
            showError(error);
            return;
        }
        refreshTable();
        clearForm();
    }//GEN-LAST:event_btnRestockActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btdAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearSearch;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRestock;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboCategory;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblRestock;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblStock;
    private javax.swing.JPanel pnlBottom;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlFields;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JScrollPane scrProducts;
    private javax.swing.JTable tblProducts;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtRestockQty;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}

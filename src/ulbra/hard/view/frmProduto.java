package ulbra.hard.view;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import ulbra.hard.model.bean.Categoria;
import ulbra.hard.model.bean.Fornecedor;
import ulbra.hard.model.bean.Produto;
import ulbra.hard.model.dao.CategoriaDAO;
import ulbra.hard.model.dao.FornecedorDAO;
import ulbra.hard.model.dao.ProdutoDAO;

/**
 *
 * @author @andre_ments
 */
public class frmProduto extends javax.swing.JFrame {

    /**
     * Creates new form frmUsuarios
     */
    enum PHASE {
        NONE, NEW, VIEW, EDIT
    }

    PHASE registerPhase = PHASE.NONE;

    Component categories, suppliers;

    public frmProduto() throws ParseException {
        initComponents();
        this.getContentPane().setBackground(new Color(20, 20, 20));

        categories = tabcProdutos.getComponent(2);
        suppliers = tabcProdutos.getComponent(3);
        tabcProdutos.remove(3);
        tabcProdutos.remove(2);

        DecimalFormat formatQnt = new DecimalFormat("#,##0");
        NumberFormatter formatterQnt = new NumberFormatter((NumberFormat) formatQnt);
        formatterQnt.setValueClass(Integer.class);
        formatterQnt.setMinimum(0.0);
        formatterQnt.setMaximum(Double.MAX_VALUE);
        DefaultFormatterFactory factoryQnt = new DefaultFormatterFactory(formatterQnt);
        txtQuantity.setFormatterFactory(factoryQnt);

        DecimalFormat formatPrice = new DecimalFormat("#,##0.00");
        NumberFormatter formatterPrice = new NumberFormatter((NumberFormat) formatPrice);
        formatterQnt.setValueClass(Double.class);
        formatterPrice.setMinimum(0.0);
        formatterPrice.setMaximum(Double.MAX_VALUE);
        DefaultFormatterFactory factoryPrice = new DefaultFormatterFactory(formatterPrice);
        txtPrice.setFormatterFactory(factoryPrice);

        populateProductsTable();
    }

    private boolean checkParameters() {
        if (txtDescription.getText().isEmpty() || txtQuantity.getText().isEmpty() || txtPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erro");
            return false;
        }
        return true;
    }

    private void populateProductsTable() {
        DefaultTableModel model = (DefaultTableModel) tableProdutos.getModel();
        model.setNumRows(0);

        ProdutoDAO dao = new ProdutoDAO();

        for (Produto p : dao.findAll()) {
            model.addRow(new Object[]{
                p.getId(),
                p.getDescription(),
                p.getQuantity(),
                p.getPrice(),
                p.getCategory(),
                p.getSupplier()
            });
        }
    }

    private void populateProductsTableCustom() {
        DefaultTableModel model = (DefaultTableModel) tableProdutos.getModel();
        model.setNumRows(0);

        ProdutoDAO dao = new ProdutoDAO();

        for (Produto p : dao.findAllByFilter(String.valueOf(comboSearch.getSelectedItem()), txtSearch.getText())) {
            model.addRow(new Object[]{
                p.getId(),
                p.getDescription(),
                p.getQuantity(),
                p.getPrice(),
                p.getCategory(),
                p.getSupplier()
            });

        }

    }

    private void populateCategoriesTable() {
        DefaultTableModel model = (DefaultTableModel) tableCategories.getModel();
        model.setNumRows(0);

        CategoriaDAO dao = new CategoriaDAO();

        for (Categoria c : dao.findAll()) {
            model.addRow(new Object[]{
                c.getId(),
                c.getDescription()
            });

        }
    }

    private void populateCategoriesTableCustom() {
        DefaultTableModel model = (DefaultTableModel) tableCategories.getModel();
        model.setNumRows(0);

        CategoriaDAO dao = new CategoriaDAO();

        for (Categoria c : dao.findAllByFilter(String.valueOf(comboSearchCategories.getSelectedItem()), txtSearchCategories.getText())) {
            model.addRow(new Object[]{
                c.getId(),
                c.getDescription()
            });

        }

    }

    //fornecedores
    private void populateSuppliersTable() {
        DefaultTableModel model = (DefaultTableModel) tableSuppliers.getModel();
        model.setNumRows(0);

        FornecedorDAO dao = new FornecedorDAO();

        for (Fornecedor f : dao.findAll()) {
            model.addRow(new Object[]{
                f.getId(),
                f.getSocialReason(),
                f.getFantasyName(),
                f.getPhone(),
                f.getEmail(),
                f.getManager()
            });

        }
    }

    private void populateSuppliersTableCustom() {
        DefaultTableModel model = (DefaultTableModel) tableSuppliers.getModel();
        model.setNumRows(0);

        FornecedorDAO dao = new FornecedorDAO();

        for (Fornecedor f : dao.findAllByFilter(String.valueOf(comboSearchSuppliers.getSelectedItem()), txtSearchSuppliers.getText())) {
            model.addRow(new Object[]{
                f.getId(),
                f.getSocialReason(),
                f.getFantasyName(),
                f.getPhone(),
                f.getEmail(),
                f.getManager()
            });

        }

    }

    private Categoria selectedCategory;
    private Fornecedor selectedSupplier;

    private void showAdditionalTabs(boolean show) {
        if (show) {
            if (tabcProdutos.getComponentCount() == 2) {
                tabcProdutos.add("categorias", categories);
                tabcProdutos.add("fornecedores", suppliers);
                populateCategoriesTable();
                populateSuppliersTable();
            }

        } else {
            if (tabcProdutos.getComponentCount() == 4) {
                tabcProdutos.remove(3);
                tabcProdutos.remove(2);
            }
        }
    }

    private void updateControls(PHASE phase) {
        switch (phase) {
            case NONE:
                showAdditionalTabs(false);
                txtDescription.setEditable(false);
                txtQuantity.setEditable(false);
                txtPrice.setEditable(false);
                btNew.setEnabled(true);
                btSave.setEnabled(false);
                btEdit.setEnabled(false);
                btDelete.setEnabled(false);
                btCancel.setEnabled(false);

                txtId.setText("");
                txtDescription.setText("");
                txtQuantity.setText("");
                txtPrice.setText("");
                txtCategory.setText("");
                txtSupplier.setText("");
                break;

            case NEW:
                showAdditionalTabs(true);
                txtDescription.setEditable(true);
                txtQuantity.setEditable(true);
                txtPrice.setEditable(true);
                btNew.setEnabled(false);
                btSave.setEnabled(true);
                btEdit.setEnabled(false);
                btDelete.setEnabled(false);
                btCancel.setEnabled(true);
                break;

            case VIEW:
                showAdditionalTabs(false);
                txtDescription.setEditable(false);
                txtQuantity.setEditable(false);
                txtPrice.setEditable(false);
                btNew.setEnabled(false);
                btSave.setEnabled(false);
                btEdit.setEnabled(true);
                btDelete.setEnabled(true);
                btCancel.setEnabled(true);
                break;

            case EDIT:
                showAdditionalTabs(true);
                txtDescription.setEditable(true);
                txtQuantity.setEditable(true);
                txtPrice.setEditable(true);
                btNew.setEnabled(false);
                btSave.setEnabled(true);
                btEdit.setEnabled(false);
                btDelete.setEnabled(false);
                btCancel.setEnabled(true);
                break;

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabcProdutos = new javax.swing.JTabbedPane();
        tabProdutosReg = new javax.swing.JPanel();
        txtId = new javax.swing.JTextField();
        txtDescription = new javax.swing.JTextField();
        btNew = new javax.swing.JButton();
        txtQuantity = new javax.swing.JFormattedTextField();
        txtPrice = new javax.swing.JFormattedTextField();
        txtCategory = new javax.swing.JTextField();
        txtSupplier = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btSave = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();
        tabProdutosCategoria = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCategories = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txtSearchCategories = new javax.swing.JTextField();
        comboSearchCategories = new javax.swing.JComboBox<>();
        btSearchCategories = new javax.swing.JButton();
        tabProdutosList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProdutos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btSearch = new javax.swing.JButton();
        comboSearch = new javax.swing.JComboBox<>();
        tabProdutosFornecedor = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSuppliers = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtSearchSuppliers = new javax.swing.JTextField();
        comboSearchSuppliers = new javax.swing.JComboBox<>();
        btSearchSuppliers = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tabProdutosReg.setBackground(new java.awt.Color(255, 255, 255));
        tabProdutosReg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtId.setEditable(false);
        txtId.setBackground(new java.awt.Color(255, 255, 255));
        txtId.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "ID:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        tabProdutosReg.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 230, -1));

        txtDescription.setEditable(false);
        txtDescription.setBackground(new java.awt.Color(255, 255, 255));
        txtDescription.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Descrição:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        tabProdutosReg.add(txtDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 310, -1));

        btNew.setText("NOVO");
        btNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNewActionPerformed(evt);
            }
        });
        tabProdutosReg.add(btNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 70, 40));

        txtQuantity.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Quantidade:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        tabProdutosReg.add(txtQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 160, 40));

        txtPrice.setEditable(false);
        txtPrice.setBackground(new java.awt.Color(255, 255, 255));
        txtPrice.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Preço", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        tabProdutosReg.add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 150, 40));

        txtCategory.setEditable(false);
        txtCategory.setBackground(new java.awt.Color(255, 255, 255));
        txtCategory.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Categoria:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        tabProdutosReg.add(txtCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 310, -1));

        txtSupplier.setEditable(false);
        txtSupplier.setBackground(new java.awt.Color(255, 255, 255));
        txtSupplier.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fornecedor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        tabProdutosReg.add(txtSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 310, -1));

        jPanel1.setBackground(new java.awt.Color(228, 238, 243));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btSave.setText("salvar");
        btSave.setEnabled(false);
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 30));

        btEdit.setText("editar");
        btEdit.setEnabled(false);
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });
        jPanel1.add(btEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 60, 30));

        btCancel.setText("cancelar");
        btCancel.setEnabled(false);
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });
        jPanel1.add(btCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 80, 30));

        btDelete.setText("excluir");
        btDelete.setEnabled(false);
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 70, 30));

        tabProdutosReg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 340, 50));

        tabcProdutos.addTab("cadastrar", tabProdutosReg);

        tabProdutosCategoria.setBackground(new java.awt.Color(255, 255, 255));
        tabProdutosCategoria.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "descrição"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCategories.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCategoriesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableCategories);

        tabProdutosCategoria.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 320, 160));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearchCategories.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel4.add(txtSearchCategories, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, -1));

        comboSearchCategories.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "id", "description" }));
        jPanel4.add(comboSearchCategories, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 107, -1));

        btSearchCategories.setText("Buscar");
        btSearchCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchCategoriesActionPerformed(evt);
            }
        });
        jPanel4.add(btSearchCategories, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, -1, -1));

        tabProdutosCategoria.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 90));

        tabcProdutos.addTab("categorias", tabProdutosCategoria);

        tabProdutosList.setBackground(new java.awt.Color(255, 255, 255));
        tabProdutosList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "descrição", "quantidade", "preço", "categoria", "fornecedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProdutosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableProdutos);

        tabProdutosList.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 320, 160));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel2.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, -1));

        btSearch.setText("Buscar");
        btSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchActionPerformed(evt);
            }
        });
        jPanel2.add(btSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, -1, -1));

        comboSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "id", "description", "quantity", "price" }));
        jPanel2.add(comboSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 107, -1));

        tabProdutosList.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 90));

        tabcProdutos.addTab("listar", tabProdutosList);

        tabProdutosFornecedor.setBackground(new java.awt.Color(255, 255, 255));
        tabProdutosFornecedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableSuppliers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "razao social", "nome fantasia", "telefone", "email", "gerente"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSuppliers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSuppliersMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableSuppliers);

        tabProdutosFornecedor.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 320, 160));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearchSuppliers.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel3.add(txtSearchSuppliers, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, -1));

        comboSearchSuppliers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "id", "socialReason", "fantasyName", "phone", "email", "manager" }));
        jPanel3.add(comboSearchSuppliers, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 107, -1));

        btSearchSuppliers.setText("Buscar");
        btSearchSuppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchSuppliersActionPerformed(evt);
            }
        });
        jPanel3.add(btSearchSuppliers, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, -1, -1));

        tabProdutosFornecedor.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 90));

        tabcProdutos.addTab("fornecedores", tabProdutosFornecedor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabcProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabcProdutos)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProdutosMouseClicked

        if (tableProdutos.getSelectedRow() != -1) {

            txtId.setText(tableProdutos.getValueAt(tableProdutos.getSelectedRow(), 0).toString());
            txtDescription.setText(tableProdutos.getValueAt(tableProdutos.getSelectedRow(), 1).toString());
            txtQuantity.setText(tableProdutos.getValueAt(tableProdutos.getSelectedRow(), 2).toString());
            txtPrice.setText(tableProdutos.getValueAt(tableProdutos.getSelectedRow(), 3).toString().replaceAll("\\.", ","));

            selectedCategory = (Categoria) tableProdutos.getValueAt(tableProdutos.getSelectedRow(), 4);
            selectedSupplier = (Fornecedor) tableProdutos.getValueAt(tableProdutos.getSelectedRow(), 5);

            txtCategory.setText(selectedCategory.toString());
            txtSupplier.setText(selectedSupplier.toString());

            registerPhase = PHASE.VIEW;
            updateControls(registerPhase);

            tabcProdutos.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tableProdutosMouseClicked

    private void tableCategoriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCategoriesMouseClicked
        if (tableCategories.getSelectedRow() != -1) {

            Categoria c = new Categoria();
            c.setId(Integer.parseInt(tableCategories.getValueAt(tableCategories.getSelectedRow(), 0).toString()));
            selectedCategory = c;

            txtCategory.setText(tableCategories.getValueAt(tableCategories.getSelectedRow(), 1).toString());

            tabcProdutos.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tableCategoriesMouseClicked

    private void tableSuppliersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSuppliersMouseClicked

        if (tableSuppliers.getSelectedRow() != -1) {

            Fornecedor f = new Fornecedor();
            f.setId(Integer.parseInt(tableSuppliers.getValueAt(tableSuppliers.getSelectedRow(), 0).toString()));
            selectedSupplier = f;

            txtSupplier.setText(tableSuppliers.getValueAt(tableSuppliers.getSelectedRow(), 2).toString() + " (" + tableSuppliers.getValueAt(tableSuppliers.getSelectedRow(), 1).toString() + ")");

            tabcProdutos.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tableSuppliersMouseClicked

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        ProdutoDAO dao = new ProdutoDAO();

        dao.remove(Integer.parseInt(txtId.getText()));

        registerPhase = PHASE.NONE;
        updateControls(registerPhase);

        populateProductsTable();
    }//GEN-LAST:event_btDeleteActionPerformed

    private void btNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNewActionPerformed
        registerPhase = PHASE.NEW;
        updateControls(registerPhase);
    }//GEN-LAST:event_btNewActionPerformed

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        if (checkParameters()) {
            Produto p = new Produto();
            ProdutoDAO dao = new ProdutoDAO();
            switch (registerPhase) {
                case EDIT:
                    p.setId(Integer.parseInt(txtId.getText()));
                case NEW:
                    p.setDescription(txtDescription.getText());
                    p.setQuantity(Integer.parseInt(txtQuantity.getText()));
                    String priceFix = txtPrice.getText().replaceAll("\\.", "").replaceAll(",", ".");
                    p.setPrice(Double.parseDouble(priceFix));
                    p.setCategory(selectedCategory);
                    p.setSupplier(selectedSupplier);

                    dao.save(p);
                    break;
            }

            registerPhase = PHASE.NONE;
            updateControls(registerPhase);
            populateProductsTable();
        }
    }//GEN-LAST:event_btSaveActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        registerPhase = PHASE.EDIT;
        updateControls(registerPhase);
    }//GEN-LAST:event_btEditActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        registerPhase = PHASE.NONE;
        updateControls(registerPhase);
    }//GEN-LAST:event_btCancelActionPerformed

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        if (txtSearch.getText() != "") {
            populateProductsTableCustom();
        }
    }//GEN-LAST:event_btSearchActionPerformed

    private void btSearchCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchCategoriesActionPerformed
        if (txtSearchCategories.getText() != "") {
            populateCategoriesTableCustom();
        }
    }//GEN-LAST:event_btSearchCategoriesActionPerformed

    private void btSearchSuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchSuppliersActionPerformed
        if (txtSearchSuppliers.getText() != "") {
            populateSuppliersTableCustom();
        }
    }//GEN-LAST:event_btSearchSuppliersActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new frmProduto().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(frmProduto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btNew;
    private javax.swing.JButton btSave;
    private javax.swing.JButton btSearch;
    private javax.swing.JButton btSearchCategories;
    private javax.swing.JButton btSearchSuppliers;
    private javax.swing.JComboBox<String> comboSearch;
    private javax.swing.JComboBox<String> comboSearchCategories;
    private javax.swing.JComboBox<String> comboSearchSuppliers;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel tabProdutosCategoria;
    private javax.swing.JPanel tabProdutosFornecedor;
    private javax.swing.JPanel tabProdutosList;
    private javax.swing.JPanel tabProdutosReg;
    private javax.swing.JTabbedPane tabcProdutos;
    private javax.swing.JTable tableCategories;
    private javax.swing.JTable tableProdutos;
    private javax.swing.JTable tableSuppliers;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtId;
    private javax.swing.JFormattedTextField txtPrice;
    private javax.swing.JFormattedTextField txtQuantity;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchCategories;
    private javax.swing.JTextField txtSearchSuppliers;
    private javax.swing.JTextField txtSupplier;
    // End of variables declaration//GEN-END:variables
}

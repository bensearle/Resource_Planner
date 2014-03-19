/*
 * The Firstco Assistant Resourcing Tool, designed and built by Ben Searle
 * for IN2030 Work Based Project at City University London
*/
package Resource_Planner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 * @author Ben Searle
 */
public class Projects extends javax.swing.JPanel {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    /**
     * Creates new form EditDetails
     */
    public Projects() {
        initComponents();
    }
    
    /**
     * method to be called every time the panel is set to visible
     * clear data, hide items, call methods, retrieve connection
     */
    public void updatePage(){
        conn = JavaConnect.ConnectDB();  //  get the connection url
        updateTableProjects();  //  populate the table
        fillPName();  //  populate the combo box
        fillPManager();  //  populate the combo box
        fillNewPManager();  //  populate the combo box
        lbPManager.setVisible(false);  //  hide label
        lbModlPN.setVisible(false);  //  hide label
        lbModPM.setVisible(false);  //  hide label
    }
    
        /**
     * method populates username combo box with all users that have permission to be a project manager
     */
    public void fillPManager(){
        cbPManager.removeAllItems();  // remove all items from the combo box
        cbPManager.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT dn FROM BSUser WHERE pm = 'y' ORDER BY dn";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("dn");  //  create a string for each of the names in the database
                cbPManager.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     /**
     * method populates username combo box with all users that have permission to be a project manager
     */
        public void fillNewPManager(){
        cbModRole.removeAllItems();  // remove all items from the combo box
        cbModRole.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT dn FROM BSUser WHERE pm = 'y' ORDER BY dn";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("dn");  //  create a string for each of the names in the database
                cbModRole.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
        
        /**
     * method populates username combo box with all existing users
     */
      public void fillPName(){
        cbDelProject.removeAllItems();  // remove all items from the combo box
        cbDelProject.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT pName FROM BSProject WHERE pNumber <> 000 ORDER BY pName DESC";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("pName");  //  create a string for each of the names in the database
                cbDelProject.addItem(name);  // add the string to the combo box list
            } 
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
      
        /**
     * method populates the table with data from the SQL Server, BSProject table.
     */
    public void updateTableProjects(){
        try {
            String sql = "SELECT pNumber AS [Project Number], pName AS [Project Name], pManager AS [Project Manager] FROM BSProject WHERE pNumber <> 000 ORDER BY pNumber DESC;";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableProjects.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
      /**
     * method to add project to BSProject
     */
    public void addProject(){
        try {
             //  if all boxes have been filled in, add project
            if (tbPNumber.getText().trim().length() != 0 && tbPName.getText().trim().length() !=0 && lbPManager.getText().trim().length() !=0){
            String sql = "INSERT INTO BSProject (pNumber, pName, pManager) VALUES (?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, tbPNumber.getText());  // value to be inserted into the table
            pst.setString(2, tbPNumber.getText() + " - " + tbPName.getText());  // value to be inserted into the table
            pst.setString(3, lbPManager.getText());  // value to be inserted into the table
            pst.execute();
            } 
            //  if there are empty boxes, tell user to enter all required information
            else{
                JOptionPane.showMessageDialog(null, "Please enter ALL information");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method to update the project manager of a project
     */
    public void updatePManager(){
        String proName = lbModlPN.getText();  //  string of project to be updated
        String newPM = lbModPM.getText();  //  string of project manager to be changed
        try {
            String sql = "UPDATE BSProject SET pManager = '"+newPM+"' WHERE pName = '"+proName+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * method to delete project from BSProject
     */
    public void deleteProject(){
        try {
            String sql = "DELETE FROM BSProject WHERE pName = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, lbModlPN.getText());  //  string of project number of project to be deleted
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            String sql = "DELETE FROM BSBooking WHERE bProject = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, lbModlPN.getText());  //  string of project number of project to be deleted
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method sets selected item of combo box based on the selected item in the table
     */
        public void clickTable(){
              int row = tableProjects.getSelectedRow();  //  define row as the selected row of the table
        String pr = tableProjects.getModel().getValueAt(row, 1).toString();  //  get seleted project from table
        cbDelProject.setSelectedItem(pr);  //  set selected item to selected project
                }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonViewTime = new javax.swing.JButton();
        buttonLogout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        buttonEditDetails = new javax.swing.JButton();
        buttonViewEngineers = new javax.swing.JButton();
        buttonViewProjects = new javax.swing.JButton();
        buttonViewWeek = new javax.swing.JButton();
        buttonAdmin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbModRole = new javax.swing.JComboBox();
        buttonDeleteProject = new javax.swing.JButton();
        buttonCreateProject = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tbPNumber = new javax.swing.JTextField();
        tbPName = new javax.swing.JTextField();
        cbPManager = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        buttonModifyProject = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProjects = new javax.swing.JTable();
        lbPManager = new javax.swing.JLabel();
        cbDelProject = new javax.swing.JComboBox();
        lbModlPN = new javax.swing.JLabel();
        lbModPM = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        buttonViewTime.setBackground(new java.awt.Color(220, 200, 255));
        buttonViewTime.setForeground(new java.awt.Color(117, 1, 93));
        buttonViewTime.setText("View Your Time");
        buttonViewTime.setBorder(null);
        buttonViewTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonViewTimeActionPerformed(evt);
            }
        });

        buttonLogout.setBackground(new java.awt.Color(220, 200, 255));
        buttonLogout.setForeground(new java.awt.Color(117, 1, 93));
        buttonLogout.setText("Logout");
        buttonLogout.setBorder(null);

        jLabel1.setFont(new java.awt.Font("Impact", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(117, 1, 93));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FIRSTCo Assistant Resourcing Tool");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(117, 1, 93));
        jLabel2.setText("Logged in as");

        userLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        userLabel.setForeground(new java.awt.Color(117, 1, 93));
        userLabel.setText("Ben Searle");

        buttonEditDetails.setBackground(new java.awt.Color(220, 200, 255));
        buttonEditDetails.setForeground(new java.awt.Color(117, 1, 93));
        buttonEditDetails.setText("Edit Your Details");
        buttonEditDetails.setBorder(null);

        buttonViewEngineers.setBackground(new java.awt.Color(220, 200, 255));
        buttonViewEngineers.setForeground(new java.awt.Color(117, 1, 93));
        buttonViewEngineers.setText("View All Engineers");
        buttonViewEngineers.setBorder(null);

        buttonViewProjects.setBackground(new java.awt.Color(220, 200, 255));
        buttonViewProjects.setForeground(new java.awt.Color(117, 1, 93));
        buttonViewProjects.setText("View Projects");
        buttonViewProjects.setBorder(null);
        buttonViewProjects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonViewProjectsActionPerformed(evt);
            }
        });

        buttonViewWeek.setBackground(new java.awt.Color(220, 200, 255));
        buttonViewWeek.setForeground(new java.awt.Color(117, 1, 93));
        buttonViewWeek.setText("View Week");
        buttonViewWeek.setBorder(null);

        buttonAdmin.setBackground(new java.awt.Color(220, 200, 255));
        buttonAdmin.setForeground(new java.awt.Color(117, 1, 93));
        buttonAdmin.setText("Admin");
        buttonAdmin.setBorder(null);
        buttonAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAdminActionPerformed(evt);
            }
        });

        setBackground(new java.awt.Color(220, 200, 255));
        setForeground(new java.awt.Color(220, 200, 255));
        setMaximumSize(new java.awt.Dimension(850, 600));
        setMinimumSize(new java.awt.Dimension(850, 600));
        setPreferredSize(new java.awt.Dimension(850, 600));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(117, 1, 93));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Project");

        cbModRole.setBackground(new java.awt.Color(220, 200, 255));
        cbModRole.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbModRole.setForeground(new java.awt.Color(117, 1, 93));
        cbModRole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "New PM" }));
        cbModRole.setBorder(null);
        cbModRole.setNextFocusableComponent(buttonModifyProject);
        cbModRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbModRoleActionPerformed(evt);
            }
        });

        buttonDeleteProject.setBackground(new java.awt.Color(220, 200, 255));
        buttonDeleteProject.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonDeleteProject.setForeground(new java.awt.Color(117, 1, 93));
        buttonDeleteProject.setText("Delete Project");
        buttonDeleteProject.setBorder(null);
        buttonDeleteProject.setNextFocusableComponent(tbPNumber);
        buttonDeleteProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteProjectActionPerformed(evt);
            }
        });

        buttonCreateProject.setBackground(new java.awt.Color(220, 200, 255));
        buttonCreateProject.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonCreateProject.setForeground(new java.awt.Color(117, 1, 93));
        buttonCreateProject.setText("Create New Project");
        buttonCreateProject.setBorder(null);
        buttonCreateProject.setNextFocusableComponent(cbDelProject);
        buttonCreateProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateProjectActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(117, 1, 93));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Project Name");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(117, 1, 93));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Project Manager");

        tbPNumber.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tbPNumber.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        tbPNumber.setNextFocusableComponent(tbPName);
        tbPNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbPNumberActionPerformed(evt);
            }
        });

        tbPName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tbPName.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        tbPName.setNextFocusableComponent(cbPManager);

        cbPManager.setBackground(new java.awt.Color(220, 200, 255));
        cbPManager.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbPManager.setForeground(new java.awt.Color(117, 1, 93));
        cbPManager.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PM Name" }));
        cbPManager.setBorder(null);
        cbPManager.setNextFocusableComponent(buttonCreateProject);
        cbPManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPManagerActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(117, 1, 93));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("New Project");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(117, 1, 93));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Modify Project");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(117, 1, 93));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("New PM");

        buttonModifyProject.setBackground(new java.awt.Color(220, 200, 255));
        buttonModifyProject.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonModifyProject.setForeground(new java.awt.Color(117, 1, 93));
        buttonModifyProject.setText("Update Project");
        buttonModifyProject.setBorder(null);
        buttonModifyProject.setNextFocusableComponent(buttonDeleteProject);
        buttonModifyProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonModifyProjectActionPerformed(evt);
            }
        });

        tableProjects.setModel(new javax.swing.table.DefaultTableModel(
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
        tableProjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProjectsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableProjects);

        lbPManager.setText("manager");

        cbDelProject.setBackground(new java.awt.Color(220, 200, 255));
        cbDelProject.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbDelProject.setForeground(new java.awt.Color(117, 1, 93));
        cbDelProject.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Existing Project" }));
        cbDelProject.setBorder(null);
        cbDelProject.setNextFocusableComponent(cbModRole);
        cbDelProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDelProjectActionPerformed(evt);
            }
        });

        lbModlPN.setText("Project");

        lbModPM.setText("newPM");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(117, 1, 93));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Project Number");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbPManager, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbModPM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lbModlPN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonDeleteProject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbModRole, 0, 231, Short.MAX_VALUE)
                            .addComponent(cbDelProject, 0, 231, Short.MAX_VALUE)
                            .addComponent(buttonModifyProject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonCreateProject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbPManager, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tbPName)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tbPNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonCreateProject, buttonDeleteProject, buttonModifyProject, cbDelProject, cbModRole, cbPManager, jLabel7, jLabel8, tbPName, tbPNumber});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tbPNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tbPName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbPManager, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCreateProject, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbDelProject, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbModRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbPManager)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbModlPN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbModPM))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonModifyProject, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonDeleteProject, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonCreateProject, buttonDeleteProject, buttonModifyProject, cbDelProject, cbModRole, cbPManager, jLabel7, jLabel8, tbPName, tbPNumber});

    }// </editor-fold>//GEN-END:initComponents
    
    private void buttonViewTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonViewTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonViewTimeActionPerformed
    
    private void buttonViewProjectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonViewProjectsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonViewProjectsActionPerformed
    
    private void buttonAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAdminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonAdminActionPerformed
    
    private void tbPNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbPNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPNumberActionPerformed
    
    private void buttonModifyProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonModifyProjectActionPerformed

        updatePManager();  //  call the method to modify user
        updateTableProjects();  // update the user table
        fillPName();  // update the username combo box
        cbDelProject.setSelectedIndex(0);  //  clear box
        lbModlPN.setText("");  //  clear box
        cbModRole.setSelectedIndex(0);  //  clear box
    }//GEN-LAST:event_buttonModifyProjectActionPerformed
    
    private void buttonCreateProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateProjectActionPerformed
        addProject();  // call method to add user
        updateTableProjects();  // update the user table
        fillPName();  // update the username combo box
        tbPNumber.setText("");  // clear text field after database has been updated
        tbPName.setText("");  // clear text field after database has been updated
        cbPManager.setSelectedIndex(0);  // set combo box to blank after database has been updated
    }//GEN-LAST:event_buttonCreateProjectActionPerformed
    
    private void cbPManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPManagerActionPerformed
        String Name = (String)cbPManager.getSelectedItem();  // create string of selected item in combo box
        lbPManager.setText("" + Name);  // set label text to string of selected item
    }//GEN-LAST:event_cbPManagerActionPerformed
    
    private void buttonDeleteProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteProjectActionPerformed
        deleteProject();
        updateTableProjects();  // update the user table
        fillPName();  // update the username combo box
        cbDelProject.setSelectedIndex(0);  //  clear box
        lbModlPN.setText("");  //  clear box
        cbModRole.setSelectedIndex(0);  //  clear box
    }//GEN-LAST:event_buttonDeleteProjectActionPerformed
    
    private void cbDelProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDelProjectActionPerformed
        String Name = (String)cbDelProject.getSelectedItem();  // create string of selected item in combo box
        lbModlPN.setText("" + Name);  // set label text to string of selected item
    }//GEN-LAST:event_cbDelProjectActionPerformed
    
    private void cbModRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbModRoleActionPerformed
        String Name = (String)cbModRole.getSelectedItem();  // create string of selected item in combo box
        lbModPM.setText("" + Name);  // set label text to string of selected item
    }//GEN-LAST:event_cbModRoleActionPerformed

    private void tableProjectsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProjectsMouseClicked
        // TODO add your handling code here:
            clickTable();  //  call method to when table is clicked
    }//GEN-LAST:event_tableProjectsMouseClicked
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonAdmin;
    private javax.swing.JButton buttonCreateProject;
    private javax.swing.JButton buttonDeleteProject;
    private javax.swing.JButton buttonEditDetails;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JButton buttonModifyProject;
    private javax.swing.JButton buttonViewEngineers;
    private javax.swing.JButton buttonViewProjects;
    private javax.swing.JButton buttonViewTime;
    private javax.swing.JButton buttonViewWeek;
    private javax.swing.JComboBox cbDelProject;
    private javax.swing.JComboBox cbModRole;
    private javax.swing.JComboBox cbPManager;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbModPM;
    private javax.swing.JLabel lbModlPN;
    private javax.swing.JLabel lbPManager;
    private javax.swing.JTable tableProjects;
    private javax.swing.JTextField tbPName;
    private javax.swing.JTextField tbPNumber;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}

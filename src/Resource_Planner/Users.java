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
public class Users extends javax.swing.JPanel {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    
    
    /**
     * Creates new form EditDetails
     */
    public Users() {
        initComponents();
    }
    
    /**
     * method to be called every time the panel is set to visible
     * clear data, hide items, call methods, retrieve connection
     */
    public void updatePage(){
        conn = JavaConnect.ConnectDB();  //  get the connection url
        updateTableUsers();  //  populate the table
        fillUsername();  //  populate the username combo bocx
        fillRole(); //  populate the username combo bocx
        lbRole.setVisible(false);  //  hide label
        lbModUN.setVisible(false);  //  hide label
        lbModRole.setVisible(false);  //  hide label
    }
    
    public void fillRole(){
        // fill new user role combo box
        cbRole.removeAllItems();  // remove all items from the combo box
        cbRole.addItem("");  //  add blank to the combo box
        cbRole.addItem("Engineer");  //  add item to the combo box
        cbRole.addItem("Project Manager");  //  add item to the combo box
        cbRole.addItem("Resource Manager");  //  add item to the combo box
        cbRole.addItem("Administrator");  //  add item to the combo box
        // fill existing user combo box
        cbModRole.removeAllItems();  // remove all items from the combo box
        cbModRole.addItem("");  //  add blank to the combo box
        cbModRole.addItem("Engineer");  //  add item to the combo box
        cbModRole.addItem("Project Manager");  //  add item to the combo box
        cbModRole.addItem("Resource Manager");  //  add item to the combo box
        cbModRole.addItem("Administrator");  //  add item to the combo box
    }
    
    
    /**
     * method populates the table with data from the SQL Server, BSUser table.
     */
    public void updateTableUsers(){
        try {
            String sql = "SELECT un AS [Username], dn AS [Display Name], en AS [Engineer], pm AS [Project Manager], rm AS [Resource Manager], ad AS [Admin] FROM BSUSer ORDER BY dn;";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableUsers.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method populates username combo box with all existing users
     */
    public void fillUsername(){
        cbModUN.removeAllItems();  // remove all items from the combo box
        cbModUN.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT dn FROM BSUser ORDER BY dn";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("dn");  //  create a string for each of the names in the database
                cbModUN.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method adds a new user to the table BSUser
     */
    public void addUser(){
        String userEN = "";  // permission initial value set to false
        String userRM = "";  // permission initial value set to false
        String userPM = "";  // permission initial value set to false
        String userAD = "";  // permission initial value set to false
        String userUN = tbUN.getText();  //  create string from text box
        String userDN = tbDN.getText();  //  create string from text box
        String userRole = lbRole.getText();  //  create string from text box
        try {
            //  if all boxes have been filled in, add user
            if(userUN.trim().length() != 0 && userDN.trim().length() !=0 && userRole.trim().length() !=0){
                //  set permission for Administrator
                if (lbRole.getText().equals("Administrator")) {
                    userEN = "y";  // set permission to true
                    userPM = "y";  // set permission to true
                    userRM = "y";  // set permission to true
                    userAD = "y";  // set permission to true
                }
                //  set permission for Resource Manager
                else if (lbRole.getText().equals("Resource Manager")) {
                    userEN = "y";  // set permission to true
                    userPM = "y";  // set permission to true
                    userRM = "y";  // set permission to true
                }
                //  set permission for Project Manager
                else if (lbRole.getText().equals("Project Manager")) {
                    userEN = "y";  // set permission to true
                    userPM = "y";  // set permission to true
                }
                //  set permission for Engineer
                else if (lbRole.getText().equals("Engineer")) {
                    userEN = "y";  // set permission to true
                }
                //  insert username, display name and permissions to BSUser
                String sql = "INSERT INTO BSUser (un, dn, en, pm, rm, ad) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, userUN);  // value to be inserted into the table
                pst.setString(2, userDN);  // value to be inserted into the table
                pst.setString(3, userEN);  // value to be inserted into the table
                pst.setString(4, userPM);  // value to be inserted into the table
                pst.setString(5, userRM);  // value to be inserted into the table
                pst.setString(6, userAD);  // value to be inserted into the table
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
    
    public void listZero(){
        String zeroEngineer = tbDN.getText();
        try {
            String sql = "SELECT we FROM BSWeek ORDER BY we";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String zeroWeek = rs.getString("we");  //  create a string for each of the names in the database
                assignZero(zeroEngineer, zeroWeek);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void assignZero(String zeroEngineer, String zeroDate){
        
        try {
            String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, zeroEngineer);  //  value to add to database
            pst.setString(2, "000 - Project Zero");  //  value to add to database
            pst.setString(3, zeroDate);  //  value to add to database
            pst.setString(4, "0");  //  value to add to database
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    /**
     * method updates BSUser with new values
     */
    public void updateUser(){
        String username = lbModUN.getText();  //  create string from text box
        String displayname = tbModDN.getText();  //  create string from text box
        String userEN = "";  // permission initial value set to false
        String userRM = "";  // permission initial value set to false
        String userPM = "";  // permission initial value set to false
        String userAD = "";  // permission initial value set to false
        try {
            //  set permission for Administrator
            if (lbModRole.getText().equals("Administrator")) {
                userEN = "y";  // set permission to true
                userPM = "y";  // set permission to true
                userRM = "y";  // set permission to true
                userAD = "y";  // set permission to true
            }
            //  set permission for Resource Manager
            else if (lbModRole.getText().equals("Resource Manager")) {
                userEN = "y";  // set permission to true
                userPM = "y";  // set permission to true
                userRM = "y";  // set permission to true
            }
            //  set permission for Project Manager
            else if (lbModRole.getText().equals("Project Manager")) {
                userEN = "y";  // set permission to true
                userPM = "y";  // set permission to true
            }
            //  set permission for Engineer
            else if (lbModRole.getText().equals("Engineer")) {
                userEN = "y";  // set permission to true
            }
            //  if there is text in the display name box, update the display name
            if (tbModDN.getText().trim().length() != 0) {
                String sql = "UPDATE BSUser SET dn = '"+displayname+"' WHERE dn = '"+username+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
            //  if there is text on the role lable, update the permissions
            if (lbModRole.getText().trim().length() != 0) {
                String sql = "UPDATE BSUser SET en = '"+userEN+"', pm = '"+userPM+"' ,rm = '"+userRM+"', ad = '"+userAD+"' WHERE dn = '"+username+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            //  if there is text in the display name box, update the display name
            if(tbModDN.getText().trim().length() != 0) {
                String sql = "UPDATE BSBooking SET bEngineer = '"+displayname+"' WHERE bEngineer = '"+username+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            //  if there is text in the display name box, update the display name
            if(tbModDN.getText().trim().length() != 0) {
                String sql = "UPDATE BSProject SET pManager = '"+displayname+"' WHERE pManager = '"+username+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method deletes selected user
     */
    public void deleteUser(){
        try {
            String sql = "DELETE FROM BSUser WHERE dn = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, lbModUN.getText());  //  username of user to be deleted
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            String sql = "DELETE FROM BSBooking WHERE bEngineer = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, lbModUN.getText());  //  username of user to be deleted
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void resetPassword(){
        String username = lbModUN.getText();  //  create string from text box
        String passord = "firstco";  //  string of password field
        try {
            //  if both old passwords match and both new passwords match, then update the database with new password
            String sql = "UPDATE BSUser SET pw = '"+passord+"' WHERE dn = '"+username+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "The password of "+username+" has successfully been reset");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method sets selected item of combo box based on the selected item in the table
     */
    public void clickTable(){
        int row = tableUsers.getSelectedRow();  //  define row as the selected row of the table
        String dn = tableUsers.getModel().getValueAt(row, 1).toString();  //  get seleted username from table
        cbModUN.setSelectedItem(dn);  //  set selected item to selected username
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
        buttonResetPassword = new javax.swing.JButton();
        cbModRole = new javax.swing.JComboBox();
        buttonDeleteUser = new javax.swing.JButton();
        buttonCreateUser = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tbUN = new javax.swing.JTextField();
        tbDN = new javax.swing.JTextField();
        cbRole = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tbModDN = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        buttonModifyUser = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();
        lbRole = new javax.swing.JLabel();
        cbModUN = new javax.swing.JComboBox();
        lbModUN = new javax.swing.JLabel();
        lbModRole = new javax.swing.JLabel();

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
        jLabel3.setText("Name");

        buttonResetPassword.setBackground(new java.awt.Color(220, 200, 255));
        buttonResetPassword.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonResetPassword.setForeground(new java.awt.Color(117, 1, 93));
        buttonResetPassword.setText("Reset Password");
        buttonResetPassword.setBorder(null);
        buttonResetPassword.setNextFocusableComponent(buttonDeleteUser);
        buttonResetPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonResetPasswordActionPerformed(evt);
            }
        });

        cbModRole.setBackground(new java.awt.Color(220, 200, 255));
        cbModRole.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbModRole.setForeground(new java.awt.Color(117, 1, 93));
        cbModRole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Existing User Role" }));
        cbModRole.setBorder(null);
        cbModRole.setNextFocusableComponent(buttonModifyUser);
        cbModRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbModRoleActionPerformed(evt);
            }
        });

        buttonDeleteUser.setBackground(new java.awt.Color(220, 200, 255));
        buttonDeleteUser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonDeleteUser.setForeground(new java.awt.Color(117, 1, 93));
        buttonDeleteUser.setText("Delete User");
        buttonDeleteUser.setBorder(null);
        buttonDeleteUser.setNextFocusableComponent(tbUN);
        buttonDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteUserActionPerformed(evt);
            }
        });

        buttonCreateUser.setBackground(new java.awt.Color(220, 200, 255));
        buttonCreateUser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonCreateUser.setForeground(new java.awt.Color(117, 1, 93));
        buttonCreateUser.setText("Create New User");
        buttonCreateUser.setBorder(null);
        buttonCreateUser.setNextFocusableComponent(cbModUN);
        buttonCreateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateUserActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(117, 1, 93));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Username");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(117, 1, 93));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Display Name");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(117, 1, 93));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Role");

        tbUN.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tbUN.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        tbUN.setNextFocusableComponent(tbDN);
        tbUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbUNActionPerformed(evt);
            }
        });

        tbDN.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tbDN.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        tbDN.setNextFocusableComponent(cbRole);

        cbRole.setBackground(new java.awt.Color(220, 200, 255));
        cbRole.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbRole.setForeground(new java.awt.Color(117, 1, 93));
        cbRole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "New User Role" }));
        cbRole.setBorder(null);
        cbRole.setNextFocusableComponent(buttonCreateUser);
        cbRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRoleActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(117, 1, 93));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("New User");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(117, 1, 93));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Modify User");

        tbModDN.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tbModDN.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        tbModDN.setNextFocusableComponent(cbModRole);
        tbModDN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbModDNActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(117, 1, 93));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("New Display Name");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(117, 1, 93));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("New Role");

        buttonModifyUser.setBackground(new java.awt.Color(220, 200, 255));
        buttonModifyUser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonModifyUser.setForeground(new java.awt.Color(117, 1, 93));
        buttonModifyUser.setText("Update User");
        buttonModifyUser.setBorder(null);
        buttonModifyUser.setNextFocusableComponent(buttonResetPassword);
        buttonModifyUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonModifyUserActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(117, 1, 93));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Default/Reset Password is");

        jLabel12.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(117, 1, 93));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("firstco");

        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
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
        tableUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUsersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableUsers);

        lbRole.setText("newRole");

        cbModUN.setBackground(new java.awt.Color(220, 200, 255));
        cbModUN.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbModUN.setForeground(new java.awt.Color(117, 1, 93));
        cbModUN.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Existing Username" }));
        cbModUN.setBorder(null);
        cbModUN.setNextFocusableComponent(tbModDN);
        cbModUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbModUNActionPerformed(evt);
            }
        });

        lbModUN.setText("Username");

        lbModRole.setText("modRole");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbRole, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbModRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbModUN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonDeleteUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonResetPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tbModDN)
                            .addComponent(cbModRole, 0, 231, Short.MAX_VALUE)
                            .addComponent(cbModUN, 0, 231, Short.MAX_VALUE)
                            .addComponent(buttonModifyUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(buttonCreateUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tbDN)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tbUN, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonCreateUser, buttonDeleteUser, buttonModifyUser, buttonResetPassword, cbModRole, cbModUN, cbRole, jLabel7, jLabel8, tbDN, tbModDN, tbUN});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tbUN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tbDN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbModUN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tbModDN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbModRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonModifyUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonResetPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbRole)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbModUN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbModRole)))
                        .addGap(4, 4, 4)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonCreateUser, buttonDeleteUser, buttonModifyUser, buttonResetPassword, cbModRole, cbModUN, cbRole, jLabel7, jLabel8, tbDN, tbModDN, tbUN});

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
    
    private void buttonResetPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonResetPasswordActionPerformed
        // TODO add your handling code here:
        resetPassword();  //  call the method to reset password
        cbModUN.setSelectedIndex(0);  //  clear box
        lbModUN.setText("");  //  clear box
        tbModDN.setText("");  //  clear box
        cbModRole.setSelectedIndex(0);  //  clear box
    }//GEN-LAST:event_buttonResetPasswordActionPerformed
    
    private void tbUNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbUNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbUNActionPerformed
    
    private void tbModDNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbModDNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbModDNActionPerformed
    
    private void buttonModifyUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonModifyUserActionPerformed
        updateUser();  //  call the method to modify user
        updateTableUsers();  // update the user table
        fillUsername();  // update the username combo box
        cbModUN.setSelectedIndex(0);  //  clear box
        lbModUN.setText("");  //  clear box
        tbModDN.setText("");  //  clear box
        cbModRole.setSelectedIndex(0);  //  clear box
    }//GEN-LAST:event_buttonModifyUserActionPerformed
    
    private void buttonCreateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateUserActionPerformed
        addUser();  // call method to add user
        listZero();
        updateTableUsers();  // update the user table
        fillUsername();  // update the username combo box
        tbUN.setText("");  // clear text field after database has been updated
        tbDN.setText("");  // clear text field after database has been updated
        cbRole.setSelectedIndex(0);  // set combo box to blank after database has been updated
    }//GEN-LAST:event_buttonCreateUserActionPerformed
    
    private void cbRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRoleActionPerformed
        String Name = (String)cbRole.getSelectedItem();  // create string of selected item in combo box
        lbRole.setText("" + Name);  // set label text to string of selected item
    }//GEN-LAST:event_cbRoleActionPerformed
    
    private void buttonDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteUserActionPerformed
        deleteUser();  //  call method to delete user
        updateTableUsers();  // update the user table
        fillUsername();  // update the username combo box
        cbModUN.setSelectedIndex(0);  //  clear box
        lbModUN.setText("");  //  clear box
        tbModDN.setText("");  //  clear box
        cbModRole.setSelectedIndex(0);  //  clear box
    }//GEN-LAST:event_buttonDeleteUserActionPerformed
    
    private void cbModUNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbModUNActionPerformed
        String Name = (String)cbModUN.getSelectedItem();  // create string of selected item in combo box
        lbModUN.setText("" + Name);  // set label text to string of selected item
    }//GEN-LAST:event_cbModUNActionPerformed
    
    private void cbModRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbModRoleActionPerformed
        String Name = (String)cbModRole.getSelectedItem();  // create string of selected item in combo box
        lbModRole.setText("" + Name);  // set label text to string of selected item
    }//GEN-LAST:event_cbModRoleActionPerformed
    
    private void tableUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUsersMouseClicked
        // TODO add your handling code here:
        clickTable();  //  call method to when table is clicked
    }//GEN-LAST:event_tableUsersMouseClicked
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonAdmin;
    private javax.swing.JButton buttonCreateUser;
    private javax.swing.JButton buttonDeleteUser;
    private javax.swing.JButton buttonEditDetails;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JButton buttonModifyUser;
    private javax.swing.JButton buttonResetPassword;
    private javax.swing.JButton buttonViewEngineers;
    private javax.swing.JButton buttonViewProjects;
    private javax.swing.JButton buttonViewTime;
    private javax.swing.JButton buttonViewWeek;
    private javax.swing.JComboBox cbModRole;
    private javax.swing.JComboBox cbModUN;
    private javax.swing.JComboBox cbRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbModRole;
    private javax.swing.JLabel lbModUN;
    private javax.swing.JLabel lbRole;
    private javax.swing.JTable tableUsers;
    private javax.swing.JTextField tbDN;
    private javax.swing.JTextField tbModDN;
    private javax.swing.JTextField tbUN;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}

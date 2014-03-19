/*
 * The Firstco Assistant Resourcing Tool, designed and built by Ben Searle
 * for IN2030 Work Based Project at City University London
 */
package Resource_Planner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * @author Ben Searle
 */
public class Overview extends javax.swing.JFrame {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String displayname = null;  //  string for user's display name
    String username = null;  //  string for user's username
    String password = null;  //  string for user's password
    String winUser = null;  //  string for the user logged into Windows
    /**
     * Creates new form Overview
     */
    public Overview() {
        initComponents();
        conn = JavaConnect.ConnectDB();  //  get the connection url
        getContentPane().setBackground(new java.awt.Color(220, 200, 255));  //  set background colour of frame
        lbIncorrect.setVisible(false);  //  hide label
        startup();  //  call method to show initial panels
        winUser = System.getProperty("user.name");  //  get the username of the user logged into Windows
        LogFromWin();
    }
    
    /**
     * method for clearing all panels from the page
     */
    public void clearPage(){
        changePassword1.setVisible(false);  //  hide panel
        yourTime1.setVisible(false);  //  hide panel
        viewData1.setVisible(false);  //  hide panel
        projects1.setVisible(false);  //  hide panel
        users1.setVisible(false);  //  hide panel
        admin1.setVisible(false);  //  hide panel
        charts1.setVisible(false);  //  hide panel
        assign1.setVisible(false);  //  hide panel
        log1.setVisible(false);  //  hide panel
    }
    
    /**
     * method to be run on startup, clears panels and navigation buttons
     */
    public void startup(){
        clearPage();  //  calls method to clear page
        buttonEditDetails.setVisible(false);  //  hide button
        buttonViewTime.setVisible(false);  //  hide button
        buttonTables.setVisible(false);  //  hide button
        buttonGraphs.setVisible(false);  //  hide button
        buttonProjects.setVisible(false);  //  hide button
        buttonUsers.setVisible(false);  //  hide button
        buttonAdmin.setVisible(false);  //  hide button
        buttonLogout.setVisible(false);  //  hide button
        buttonLog.setVisible(false);
        buttonAssign.setVisible(false);
        userLabel.setVisible(false);  //  hide panel
        pageLabel.setVisible(false);  //  hide panel
        yourTime1.setVisible(false);  //  hide panel
    }
    
    /**
     * method for logging in as an administrator
     */
    public void loginAD(){
        login1.setVisible(false);  //  hide login panel
        userLabel.setText(displayname);  //  set text of user label
        buttonEditDetails.setVisible(true);  //  show button
        buttonViewTime.setVisible(true);  //  show button
        buttonTables.setVisible(true);  //  show button
        buttonGraphs.setVisible(true);  //  show button
        buttonProjects.setVisible(true);  //  show button
        buttonUsers.setVisible(true);  //  show button
        buttonAdmin.setVisible(true);  //  show button
        buttonAssign.setVisible(true);
        buttonLog.setVisible(true);  //  show button
        buttonLogout.setVisible(true);  //  show button
        userLabel.setVisible(true);  //  show label
        pageLabel.setVisible(true);  //  show label
        pageLabel.setText("Current Page: Your Time");  //  set text of page label
        yourTime1.setVisible(true);  //  show your time panel
        yourTime1.user = displayname;  //  pass variable to class
        yourTime1.updatePage();  //  update page
        changePassword1.oldPassword = password;  //  pass variable to class
        changePassword1.user = username;  //  pass variable to class
        assign1.loggedInUser = username;
    }
    
    /**
     * method for logging in as an resource manager
     */
    public void loginRM(){
        login1.setVisible(false);  //  hide login panel
        userLabel.setText(displayname);  //  set text of user label
        buttonEditDetails.setVisible(true);  //  show button
        buttonViewTime.setVisible(true);  //  show button
        buttonTables.setVisible(true);  //  show button
        buttonGraphs.setVisible(true);  //  show button
        buttonProjects.setVisible(true);  //  show button
        buttonLogout.setVisible(true);  //  show button
        buttonAssign.setVisible(true);
        userLabel.setVisible(true);  //  show label
        pageLabel.setVisible(true);  //  show label
        pageLabel.setText("Current Page: Your Time");  //  set text of page label
        yourTime1.setVisible(true);  //  show your time panel
        yourTime1.user = displayname;  //  pass variable to class
        yourTime1.updatePage();  //  update page
        changePassword1.oldPassword = password;  //  pass variable to class
        changePassword1.user = username;  //  pass variable to class
        assign1.loggedInUser = username;
    }
    
    /**
     * method for logging in as a project manager
     */
    public void loginPM(){
        login1.setVisible(false);  //  hide login panel
        userLabel.setText(displayname);  //  set text of user label
        buttonEditDetails.setVisible(true);  //  show button
        buttonViewTime.setVisible(true);  //  show button
        buttonTables.setVisible(true);  //  show button
        buttonGraphs.setVisible(true);  //  show button
        buttonLogout.setVisible(true);  //  show button
        buttonAssign.setVisible(true);
        userLabel.setVisible(true);  //  show label
        pageLabel.setVisible(true);  //  show label
        pageLabel.setText("Current Page: Your Time");  //  set text of page label
        yourTime1.setVisible(true);  //  show your time panel
        yourTime1.user = displayname;  //  pass variable to class
        yourTime1.updatePage();  //  update page
        changePassword1.oldPassword = password;  //  pass variable to class
        changePassword1.user = username;  //  pass variable to class
        assign1.loggedInUser = username;
    }
    
    /**
     * method for logging in as an engineer
     */
    public void loginEN(){
        login1.setVisible(false);  //  hide login panel
        userLabel.setText(displayname);  //  set text of user label
        buttonEditDetails.setVisible(true);  //  show button
        buttonViewTime.setVisible(true);  //  show button
        buttonLogout.setVisible(true);  //  show button
        userLabel.setVisible(true);  //  show label
        pageLabel.setVisible(true);  //  show label
        pageLabel.setText("Current Page: Your Time");  //  set text of page label
        yourTime1.setVisible(true);  //  show your time panel
        yourTime1.user = displayname;  //  pass variable to class
        yourTime1.updatePage();  //  update page
        changePassword1.oldPassword = password;  //  pass variable to class
        changePassword1.user = username;  //  pass variable to class
        assign1.loggedInUser = username;
    }
    
    /**
     * method for authenticating the user's log in details
     */
    public void LogFromWin(){
        String sql = "SELECT * FROM BSUser";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String user = winUser;
            String y = "y";  //  string y to represent permission
            while(rs.next()) {
                String un = rs.getString("un");  // Set the table column to a string
                String dn = rs.getString("dn");  // Set the table column to a string
                String pw = rs.getString("pw");  // Set the table column to a string
                String en = rs.getString("en");  // Set the table column to a string
                String pm = rs.getString("pm");  // Set the table column to a string
                String rm = rs.getString("rm");  // Set the table column to a string
                String ad = rs.getString("ad");  // Set the table column to a string
                if (user.equals(un)){
                    displayname = dn;  //  define dn
                    username = un;  //  define un
                    password = pw;  //  define pw
                    //  ad calls loginAD
                    if ((y.equals(ad))){
                        loginAD();
                    }
                    //  rm calls loginRM
                    else if ((y.equals(rm))){
                        loginRM();
                    }
                    //  pm calls loginPM
                    else if ((y.equals(pm))){
                        loginPM();
                    }
                    //  en calls loginEN
                    else if ((y.equals(en))){
                        loginEN();
                    }
                    break;  // end while loop
                }
            }
        }
        catch (Exception e){
        }
    }
    
    /**
     * method for authenticating the user's log in details
     */
    public void AuthenticateUser(){
        String sql = "SELECT * FROM BSUser";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String user = boxUN.getText();  //  set text to string
            String pass = new String (boxPW.getPassword());  //  set text to string
            String y = "y";  //  string y to represent permission
            while(rs.next()) {
                String un = rs.getString("un");  // Set the table column to a string
                String dn = rs.getString("dn");  // Set the table column to a string
                String pw = rs.getString("pw");  // Set the table column to a string
                String en = rs.getString("en");  // Set the table column to a string
                String pm = rs.getString("pm");  // Set the table column to a string
                String rm = rs.getString("rm");  // Set the table column to a string
                String ad = rs.getString("ad");  // Set the table column to a string
                if ((user.equals(un)) && (pass.equals(pw))){
                    displayname = dn;  //  define dn
                    username = un;  //  define un
                    password = pw;  //  define pw
                    //  ad calls loginAD
                    if ((y.equals(ad))){
                        loginAD();
                    }
                    //  rm calls loginRM
                    else if ((y.equals(rm))){
                        loginRM();
                    }
                    //  pm calls loginPM
                    else if ((y.equals(pm))){
                        loginPM();
                    }
                    //  en calls loginEN
                    else if ((y.equals(en))){
                        loginEN();
                    }
                    break;  // end while loop
                }
                //  tell user to enter their username
                else if (user.equals("")){
                    lbIncorrect.setVisible(true);  //  view label
                    lbIncorrect.setText("Please enter your username");  //  set label text
                    break;  // end while loop
                }
                //  tell user to enter their password
                else if (pass.equals("")){
                    lbIncorrect.setVisible(true);  //  view label
                    lbIncorrect.setText("Please enter your password");  //  set label text
                    break;  // end while loop
                }
                //  tell user that there login details are incorrect
                else if ((!user.equals(un)) || (!pass.equals(pw))){
                    lbIncorrect.setVisible(true);  //  view label
                    lbIncorrect.setText("Incorrect details");  //  set label text
                }
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    /**
     * method to log user out of the system, set all user related values to null
     * allows new user to log in
     */
    public void logout(){
        clearPage();
        login1.setVisible(true);  //  show login panel
        userLabel.setText(null);  //  clear label
        buttonEditDetails.setVisible(false);  //  hide button
        buttonViewTime.setVisible(false);  //  hide button
        buttonTables.setVisible(false);  //  hide button
        buttonProjects.setVisible(false);  //  hide button
        buttonUsers.setVisible(false);  //  hide button
        buttonAdmin.setVisible(false);  //  hide button
        buttonGraphs.setVisible(false);  //  hide button
        buttonAssign.setVisible(false);  //  hide button
        buttonLog.setVisible(false);  //  hide button
        userLabel.setVisible(false);  //  hide object
        pageLabel.setVisible(false);  //  hide object
        changePassword1.setVisible(false);  //  hide object
       // yourTime1.setVisible(false);  //  hide object
      //  viewData1.setVisible(false);  //  hide object
      //  projects1.setVisible(false);  //  hide object
      //  charts1.setVisible(false);  //  hide object
    //    users1.setVisible(false);  //  hide object
     //   admin1.setVisible(false);  //  hide object
        buttonLogout.setVisible(false);  //  hide object
        boxUN.setText("");  //  clear username field
        boxPW.setText("");  //  clear password field
        lbIncorrect.setVisible(false);  //  hide object
        yourTime1.user = null;  //  set user variable to null
        changePassword1.oldPassword = null;  //  set user variable to null
        changePassword1.user = null;  //  set user variable to null
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        buttonTables = new javax.swing.JButton();
        buttonProjects = new javax.swing.JButton();
        buttonUsers = new javax.swing.JButton();
        buttonViewTime = new javax.swing.JButton();
        buttonEditDetails = new javax.swing.JButton();
        buttonAdmin = new javax.swing.JButton();
        buttonGraphs = new javax.swing.JButton();
        buttonAssign = new javax.swing.JButton();
        buttonLog = new javax.swing.JButton();
        login1 = new javax.swing.JPanel();
        boxUN = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        buttonLogin = new javax.swing.JButton();
        boxPW = new javax.swing.JPasswordField();
        lbIncorrect = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        buttonLogout = new javax.swing.JButton();
        userLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        pageLabel = new javax.swing.JLabel();
        admin1 = new Resource_Planner.Admin();
        changePassword1 = new Resource_Planner.ChangePassword();
        projects1 = new Resource_Planner.Projects();
        users1 = new Resource_Planner.Users();
        viewData1 = new Resource_Planner.Tables();
        yourTime1 = new Resource_Planner.YourTime();
        charts1 = new Resource_Planner.Charts();
        assign1 = new Resource_Planner.Assign();
        log1 = new Resource_Planner.Log();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Resource Planner");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(220, 200, 255));
        jPanel1.setForeground(new java.awt.Color(220, 200, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 25));
        jPanel1.setMinimumSize(new java.awt.Dimension(850, 25));
        jPanel1.setPreferredSize(new java.awt.Dimension(850, 25));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        buttonTables.setBackground(new java.awt.Color(220, 200, 255));
        buttonTables.setForeground(new java.awt.Color(117, 1, 93));
        buttonTables.setText("Tables");
        buttonTables.setBorder(null);
        buttonTables.setMargin(new java.awt.Insets(1, 1, 1, 1));
        buttonTables.setMaximumSize(new java.awt.Dimension(30, 15));
        buttonTables.setMinimumSize(new java.awt.Dimension(30, 15));
        buttonTables.setPreferredSize(new java.awt.Dimension(30, 15));
        buttonTables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTablesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel1.add(buttonTables, gridBagConstraints);

        buttonProjects.setBackground(new java.awt.Color(220, 200, 255));
        buttonProjects.setForeground(new java.awt.Color(117, 1, 93));
        buttonProjects.setText("Projects");
        buttonProjects.setBorder(null);
        buttonProjects.setMargin(new java.awt.Insets(1, 1, 1, 1));
        buttonProjects.setMaximumSize(new java.awt.Dimension(30, 15));
        buttonProjects.setMinimumSize(new java.awt.Dimension(30, 15));
        buttonProjects.setPreferredSize(new java.awt.Dimension(30, 15));
        buttonProjects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonProjectsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel1.add(buttonProjects, gridBagConstraints);

        buttonUsers.setBackground(new java.awt.Color(220, 200, 255));
        buttonUsers.setForeground(new java.awt.Color(117, 1, 93));
        buttonUsers.setText("Users");
        buttonUsers.setBorder(null);
        buttonUsers.setMargin(new java.awt.Insets(1, 1, 1, 1));
        buttonUsers.setMaximumSize(new java.awt.Dimension(30, 15));
        buttonUsers.setMinimumSize(new java.awt.Dimension(30, 15));
        buttonUsers.setPreferredSize(new java.awt.Dimension(30, 15));
        buttonUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUsersActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel1.add(buttonUsers, gridBagConstraints);

        buttonViewTime.setBackground(new java.awt.Color(220, 200, 255));
        buttonViewTime.setForeground(new java.awt.Color(117, 1, 93));
        buttonViewTime.setText("Your Time");
        buttonViewTime.setBorder(null);
        buttonViewTime.setMargin(new java.awt.Insets(1, 1, 1, 1));
        buttonViewTime.setMaximumSize(new java.awt.Dimension(30, 15));
        buttonViewTime.setMinimumSize(new java.awt.Dimension(30, 15));
        buttonViewTime.setPreferredSize(new java.awt.Dimension(30, 15));
        buttonViewTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonViewTimeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel1.add(buttonViewTime, gridBagConstraints);

        buttonEditDetails.setBackground(new java.awt.Color(220, 200, 255));
        buttonEditDetails.setForeground(new java.awt.Color(117, 1, 93));
        buttonEditDetails.setText("Settings");
        buttonEditDetails.setBorder(null);
        buttonEditDetails.setMargin(new java.awt.Insets(1, 1, 1, 1));
        buttonEditDetails.setMaximumSize(new java.awt.Dimension(30, 15));
        buttonEditDetails.setMinimumSize(new java.awt.Dimension(30, 15));
        buttonEditDetails.setPreferredSize(new java.awt.Dimension(30, 15));
        buttonEditDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditDetailsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel1.add(buttonEditDetails, gridBagConstraints);

        buttonAdmin.setBackground(new java.awt.Color(220, 200, 255));
        buttonAdmin.setForeground(new java.awt.Color(117, 1, 93));
        buttonAdmin.setText("Administrator");
        buttonAdmin.setBorder(null);
        buttonAdmin.setMargin(new java.awt.Insets(1, 1, 1, 1));
        buttonAdmin.setMaximumSize(new java.awt.Dimension(30, 15));
        buttonAdmin.setMinimumSize(new java.awt.Dimension(30, 15));
        buttonAdmin.setPreferredSize(new java.awt.Dimension(30, 15));
        buttonAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAdminActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel1.add(buttonAdmin, gridBagConstraints);

        buttonGraphs.setBackground(new java.awt.Color(220, 200, 255));
        buttonGraphs.setForeground(new java.awt.Color(117, 1, 93));
        buttonGraphs.setText("Graphs");
        buttonGraphs.setBorder(null);
        buttonGraphs.setMargin(new java.awt.Insets(1, 1, 1, 1));
        buttonGraphs.setMaximumSize(new java.awt.Dimension(30, 15));
        buttonGraphs.setMinimumSize(new java.awt.Dimension(30, 15));
        buttonGraphs.setPreferredSize(new java.awt.Dimension(30, 15));
        buttonGraphs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGraphsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel1.add(buttonGraphs, gridBagConstraints);

        buttonAssign.setBackground(new java.awt.Color(220, 200, 255));
        buttonAssign.setForeground(new java.awt.Color(117, 1, 93));
        buttonAssign.setText("Assign");
        buttonAssign.setBorder(null);
        buttonAssign.setMargin(new java.awt.Insets(1, 1, 1, 1));
        buttonAssign.setMaximumSize(new java.awt.Dimension(30, 15));
        buttonAssign.setMinimumSize(new java.awt.Dimension(30, 15));
        buttonAssign.setPreferredSize(new java.awt.Dimension(30, 15));
        buttonAssign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAssignActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel1.add(buttonAssign, gridBagConstraints);

        buttonLog.setBackground(new java.awt.Color(220, 200, 255));
        buttonLog.setForeground(new java.awt.Color(117, 1, 93));
        buttonLog.setText("Log");
        buttonLog.setBorder(null);
        buttonLog.setMargin(new java.awt.Insets(1, 1, 1, 1));
        buttonLog.setMaximumSize(new java.awt.Dimension(30, 15));
        buttonLog.setMinimumSize(new java.awt.Dimension(30, 15));
        buttonLog.setPreferredSize(new java.awt.Dimension(30, 15));
        buttonLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel1.add(buttonLog, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(jPanel1, gridBagConstraints);

        login1.setBackground(new java.awt.Color(220, 200, 255));
        login1.setForeground(new java.awt.Color(220, 200, 255));
        login1.setMaximumSize(new java.awt.Dimension(850, 600));
        login1.setMinimumSize(new java.awt.Dimension(850, 600));
        login1.setName(""); // NOI18N
        login1.setPreferredSize(new java.awt.Dimension(850, 600));

        boxUN.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        boxUN.setText("bsearle");
        boxUN.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(117, 1, 93));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Username");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(117, 1, 93));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Password");

        buttonLogin.setBackground(new java.awt.Color(220, 200, 255));
        buttonLogin.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonLogin.setForeground(new java.awt.Color(117, 1, 93));
        buttonLogin.setText("Login");
        buttonLogin.setBorder(null);
        buttonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoginActionPerformed(evt);
            }
        });

        boxPW.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        boxPW.setText("ben");
        boxPW.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        lbIncorrect.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbIncorrect.setForeground(new java.awt.Color(117, 1, 93));
        lbIncorrect.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbIncorrect.setText("Incorrect Login");

        javax.swing.GroupLayout login1Layout = new javax.swing.GroupLayout(login1);
        login1.setLayout(login1Layout);
        login1Layout.setHorizontalGroup(
            login1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login1Layout.createSequentialGroup()
                .addGap(287, 287, 287)
                .addGroup(login1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(login1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boxUN, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(buttonLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boxPW))
                .addGap(280, 280, 280))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, login1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbIncorrect, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        login1Layout.setVerticalGroup(
            login1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(login1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boxUN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(login1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(boxPW, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(buttonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbIncorrect)
                .addContainerGap(373, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(login1, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(117, 1, 93));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("R e s o u r c e   P l a n n e r");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(jLabel14, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(220, 200, 255));
        jPanel2.setForeground(new java.awt.Color(220, 200, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(300, 25));
        jPanel2.setMinimumSize(new java.awt.Dimension(300, 25));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 25));

        buttonLogout.setBackground(new java.awt.Color(220, 200, 255));
        buttonLogout.setForeground(new java.awt.Color(117, 1, 93));
        buttonLogout.setText("Logout");
        buttonLogout.setBorder(null);
        buttonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogoutActionPerformed(evt);
            }
        });

        userLabel.setBackground(new java.awt.Color(220, 200, 255));
        userLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        userLabel.setForeground(new java.awt.Color(117, 1, 93));
        userLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        userLabel.setText("Logged in as Ben Searle");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLabel))
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(220, 200, 255));
        jPanel3.setForeground(new java.awt.Color(220, 200, 255));
        jPanel3.setToolTipText("");
        jPanel3.setMaximumSize(new java.awt.Dimension(300, 25));
        jPanel3.setMinimumSize(new java.awt.Dimension(300, 25));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 25));

        pageLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pageLabel.setForeground(new java.awt.Color(117, 1, 93));
        pageLabel.setText("Current Page");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jPanel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(admin1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(changePassword1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(projects1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(users1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(viewData1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(yourTime1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(charts1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(assign1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(log1, gridBagConstraints);

        setSize(new java.awt.Dimension(1920, 1080));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void buttonViewTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonViewTimeActionPerformed
        pageLabel.setText("Current Page: " + buttonViewTime.getText());  //  set label text to display current page
        clearPage();  //  call method to clear page
        yourTime1.setVisible(true);  //  view panel
        yourTime1.updatePage();  //  update panel
    }//GEN-LAST:event_buttonViewTimeActionPerformed
        
    private void buttonTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTablesActionPerformed
        pageLabel.setText("Current Page: " + buttonTables.getText());  //  set label text to display current page
        clearPage();  //  call method to clear page
        viewData1.setVisible(true);  //  view panel
        viewData1.updatePage();  //  update panel
    }//GEN-LAST:event_buttonTablesActionPerformed
    
    private void buttonUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUsersActionPerformed
        pageLabel.setText("Current Page: " + buttonUsers.getText());  //  set label text to display current page
        clearPage();  //  call method to clear page
        users1.setVisible(true);  //  view panel
        users1.updatePage();  //  update panel
    }//GEN-LAST:event_buttonUsersActionPerformed
    
    private void buttonProjectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonProjectsActionPerformed
        pageLabel.setText("Current Page: " + buttonProjects.getText());  //  set label text to display current page
        clearPage();  //  call method to clear page
        projects1.setVisible(true);  //  view panel
        projects1.updatePage();  //  update panel
    }//GEN-LAST:event_buttonProjectsActionPerformed
    
    private void buttonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLogoutActionPerformed
        logout();  //  call method to logout
    }//GEN-LAST:event_buttonLogoutActionPerformed
    
    private void buttonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoginActionPerformed
        AuthenticateUser();  //  call method to authenticate user
    }//GEN-LAST:event_buttonLoginActionPerformed
    
    private void buttonAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAdminActionPerformed
        // TODO add your handling code here:
        pageLabel.setText("Current Page: " + buttonAdmin.getText());  //  set label text to display current page
        clearPage();  //  call method to clear page
        admin1.setVisible(true);  //  view panel
        admin1.updatePage();  //  update panel
    }//GEN-LAST:event_buttonAdminActionPerformed
    
    private void buttonGraphsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGraphsActionPerformed
        // TODO add your handling code here:
        pageLabel.setText("Current Page: " + buttonGraphs.getText());  //  set label text to display current page
        clearPage();  //  call method to clear page
        charts1.setVisible(true);  //  view panel
        charts1.updatePage();  //  update panel
    }//GEN-LAST:event_buttonGraphsActionPerformed

    private void buttonEditDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditDetailsActionPerformed
        pageLabel.setText("Current Page: " + buttonEditDetails.getText());  //  set label text to display current page
        clearPage();  //  call method to clear page
        changePassword1.setVisible(true);  //  view panel
        changePassword1.updatePage();  //  update panel
    }//GEN-LAST:event_buttonEditDetailsActionPerformed

    private void buttonAssignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAssignActionPerformed
        // TODO add your handling code here:
        pageLabel.setText("Current Page: " + buttonAssign.getText());  //  set label text to display current page
        clearPage();  //  call method to clear page
        assign1.setVisible(true);  //  view panel
        assign1.updatePage();  //  update panel
    }//GEN-LAST:event_buttonAssignActionPerformed

    private void buttonLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLogActionPerformed
        // TODO add your handling code here:
        pageLabel.setText("Current Page: " + buttonLog.getText());  //  set label text to display current page
        clearPage();  //  call method to clear page
        log1.setVisible(true);  //  view panel
        log1.updatePage();  //  update panel 
        
    }//GEN-LAST:event_buttonLogActionPerformed
    
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
            java.util.logging.Logger.getLogger(Overview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Overview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Overview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Overview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Overview().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Resource_Planner.Admin admin1;
    private Resource_Planner.Assign assign1;
    private javax.swing.JPasswordField boxPW;
    private javax.swing.JTextField boxUN;
    private javax.swing.JButton buttonAdmin;
    private javax.swing.JButton buttonAssign;
    private javax.swing.JButton buttonEditDetails;
    private javax.swing.JButton buttonGraphs;
    private javax.swing.JButton buttonLog;
    private javax.swing.JButton buttonLogin;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JButton buttonProjects;
    private javax.swing.JButton buttonTables;
    private javax.swing.JButton buttonUsers;
    private javax.swing.JButton buttonViewTime;
    private Resource_Planner.ChangePassword changePassword1;
    private Resource_Planner.Charts charts1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbIncorrect;
    private Resource_Planner.Log log1;
    private javax.swing.JPanel login1;
    private javax.swing.JLabel pageLabel;
    private Resource_Planner.Projects projects1;
    private javax.swing.JLabel userLabel;
    private Resource_Planner.Users users1;
    private Resource_Planner.Tables viewData1;
    private Resource_Planner.YourTime yourTime1;
    // End of variables declaration//GEN-END:variables
}

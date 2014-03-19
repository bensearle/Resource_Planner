/*
 * The Firstco Assistant Resourcing Tool, designed and built by Ben Searle
 * for IN2030 Work Based Project at City University London
 */
package Resource_Planner;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

/**
 * @author Ben Searle
 */
public class Log extends javax.swing.JPanel {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    /**
     * Creates new form ViewData
     */
    public Log() {
        initComponents();
    }
    
    /**
     * method to be called every time the panel is set to visible
     * clear data, hide items, call methods, retrieve connection
     */
    public void updatePage(){
        conn = JavaConnect.ConnectDB();  //  get the connection url
        invisiblePanel.setVisible(false);  //  hide panel
        fillUN();  //  populate combo box
        fillUA();  //  populate combo box
        fillEn();  //  populate combo box
        fillPr();  //  populate combo box
        fillWk();  //  populate combo box
        updateTable();  // populate the table
    }
    
    /**
     * update method updates the table
     * there are many variations of how the table is populated
     */
    public void updateTable(){
        String bUN = lbFilterUN.getText();  //  get string from label
        String bUA = lbFilterUA.getText();  //  get string from label
        String bEn = lbFilterEn.getText();  //  get string from label
        String bPr = lbFilterPr.getText();  //  get string from label
        String bWk = lbFilterWk.getText();  //  get string from label
        String where1 = "";
        String and1 = "";
        String and2 = "";
        String and3 = "";
        String and4 = "";
        String ifUN = "";
        String ifUA = "";
        String ifEn = "";
        String ifPr = "";
        String ifWk = "";
        
        if (cbFilterUN.getSelectedIndex() != 0){
            ifUN = "lUser = '"+bUN+"'";
        }if (cbFilterUA.getSelectedIndex() != 0){
            //  is an AND clause required
            if (ifUN.length() != 0){
                and1 = "AND";
            }
            ifUA = "lClicked = '"+bUA+"'";
        }if (cbFilterEn.getSelectedIndex() != 0){
            //  is an AND clause required
            if (ifUN.length() != 0 || ifUA.length() != 0){
                and2 = "AND";
            }
            ifEn = "lbEngineer = '"+bEn+"'";
        }if (cbFilterPr.getSelectedIndex() != 0){
            //  is an AND clause required
            if (ifUN.length() != 0 || ifUA.length() != 0 || ifEn.length() != 0){
                and3 = "AND";
            }
            ifPr = "lbProject = '"+bPr+"'";
        }if (cbFilterWk.getSelectedIndex() != 0){
            //  is an AND clause required
            if (ifUN.length() != 0 || ifUA.length() != 0 || ifEn.length() != 0 || ifPr.length() != 0){
                and4 = "AND";
            }
            ifWk = "lbDate = '"+bWk+"'";
        }
        //  is a WHERE clause required
        if (ifUN.length() != 0 || ifUA.length() != 0 || ifEn.length() != 0 || ifPr.length() != 0 || ifWk.length() != 0){
            where1 = "WHERE";
        }
        try {
            /*
             * populate if group is ticked, all days are summed
             */
            
            String sql = "SELECT lUser AS [User], lDate AS [Time], lClicked AS [Action], lbEngineer AS [Engineer], lbProject AS [Project], lbDate AS [Week Ending], lbDays AS [Days] FROM BSLog "
                    + " "+where1+" "+ifUN+" "+and1+" "+ifUA+" "+and2+" "+ifEn+" "+and3+" "+ifPr+" "+and4+" "+ifWk+" "
                    + "ORDER BY lID DESC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableBookings.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (Exception e){
            //   JOptionPane.showMessageDialog(null, e);  // error is due to bDate = '"+bWk+"' works fine
        }
    }
    
    /**
     * method to populate combo box with engineers that appear in the table
     */
    public void fillUN(){
        cbFilterUN.removeAllItems();  // remove all items from the combo box
        cbFilterUN.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT DISTINCT lUser FROM BSLog ORDER BY lUser ASC";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("lUser");  //  create a string for each of the names in the database
                cbFilterUN.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method to populate combo box with Assign / Unassign that appear in the table
     */
    public void fillUA(){
        cbFilterUA.removeAllItems();  // remove all items from the combo box
        cbFilterUA.addItem("");  // add an empty item to the combo box
        cbFilterUA.addItem("Assign");  // add an empty item to the combo box
        cbFilterUA.addItem("Unassign");  // add an empty item to the combo box
    }
    /**
     * method to populate combo box with engineers that appear in the table
     */
    public void fillEn(){
        cbFilterEn.removeAllItems();  // remove all items from the combo box
        cbFilterEn.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT DISTINCT lbEngineer FROM BSLog ORDER BY lbEngineer ASC";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("lbEngineer");  //  create a string for each of the names in the database
                cbFilterEn.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method to populate combo box with projects that appear in the table
     */
    public void fillPr(){
        cbFilterPr.removeAllItems();  // remove all items from the combo box
        cbFilterPr.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT DISTINCT lbProject FROM BSLog ORDER BY lbProject DESC";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("lbProject");  //  create a string for each of the names in the database
                cbFilterPr.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        cbFilterPr.removeItem("000 - Project Zero");
    }
    
    /**
     * method to populate combo box with weeks that appear in the table
     */
    public void fillWk(){
        cbFilterWk.removeAllItems();  // remove all items from the combo box
        cbFilterWk.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT DISTINCT lbDate FROM BSLog ORDER BY lbDate ASC";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("lbDate");  //  create a string for each of the names in the database
                cbFilterWk.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void limitLog(){
        int max = 0;
        try {
            String sql = "SELECT MAX(lID) FROM BSLog;";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
            max = rs.getInt(1);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            int maxDel = max - 5000;
            String sql = "DELETE FROM BSLog WHERE lID < "+maxDel+"";
            pst = conn.prepareStatement(sql);
            pst.execute();
            updateTable();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
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
        cbFilterEn = new javax.swing.JComboBox();
        cbFilterWk = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBookings = new javax.swing.JTable();
        cbFilterPr = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        invisiblePanel = new javax.swing.JPanel();
        lbFilterEn = new javax.swing.JLabel();
        lbFilterPr = new javax.swing.JLabel();
        lbFilterWk = new javax.swing.JLabel();
        lbFilterUN = new javax.swing.JLabel();
        lbFilterUA = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbFilterUN = new javax.swing.JComboBox();
        cbFilterUA = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();

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
        setToolTipText("");
        setMaximumSize(new java.awt.Dimension(850, 600));
        setMinimumSize(new java.awt.Dimension(850, 600));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(850, 600));

        cbFilterEn.setBackground(new java.awt.Color(220, 200, 255));
        cbFilterEn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbFilterEn.setForeground(new java.awt.Color(117, 1, 93));
        cbFilterEn.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Engineer" }));
        cbFilterEn.setBorder(null);
        cbFilterEn.setMaximumSize(new java.awt.Dimension(101, 26));
        cbFilterEn.setMinimumSize(new java.awt.Dimension(101, 26));
        cbFilterEn.setNextFocusableComponent(cbFilterPr);
        cbFilterEn.setPreferredSize(new java.awt.Dimension(101, 26));
        cbFilterEn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterEnActionPerformed(evt);
            }
        });

        cbFilterWk.setBackground(new java.awt.Color(220, 200, 255));
        cbFilterWk.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbFilterWk.setForeground(new java.awt.Color(117, 1, 93));
        cbFilterWk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Week" }));
        cbFilterWk.setBorder(null);
        cbFilterWk.setMaximumSize(new java.awt.Dimension(101, 26));
        cbFilterWk.setMinimumSize(new java.awt.Dimension(101, 26));
        cbFilterWk.setPreferredSize(new java.awt.Dimension(101, 26));
        cbFilterWk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterWkActionPerformed(evt);
            }
        });

        tableBookings.setModel(new javax.swing.table.DefaultTableModel(
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
        tableBookings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBookingsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableBookings);

        cbFilterPr.setBackground(new java.awt.Color(220, 200, 255));
        cbFilterPr.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbFilterPr.setForeground(new java.awt.Color(117, 1, 93));
        cbFilterPr.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Project" }));
        cbFilterPr.setBorder(null);
        cbFilterPr.setMaximumSize(new java.awt.Dimension(101, 26));
        cbFilterPr.setMinimumSize(new java.awt.Dimension(101, 26));
        cbFilterPr.setNextFocusableComponent(cbFilterWk);
        cbFilterPr.setPreferredSize(new java.awt.Dimension(101, 26));
        cbFilterPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterPrActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(117, 1, 93));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Project");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(117, 1, 93));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Week");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(117, 1, 93));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Engineer");

        lbFilterEn.setText("FilterEngineer");

        lbFilterPr.setText("FilterProject");

        lbFilterWk.setText("FilterWeek");

        lbFilterUN.setText("FilterUser");

        lbFilterUA.setText("FilterAction");

        javax.swing.GroupLayout invisiblePanelLayout = new javax.swing.GroupLayout(invisiblePanel);
        invisiblePanel.setLayout(invisiblePanelLayout);
        invisiblePanelLayout.setHorizontalGroup(
            invisiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(invisiblePanelLayout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addComponent(lbFilterUN, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbFilterUA, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbFilterEn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lbFilterPr, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbFilterWk, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        invisiblePanelLayout.setVerticalGroup(
            invisiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(invisiblePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(invisiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFilterEn)
                    .addComponent(lbFilterPr)
                    .addComponent(lbFilterWk)
                    .addComponent(lbFilterUA)
                    .addComponent(lbFilterUN))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(117, 1, 93));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("User");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(117, 1, 93));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Action");

        cbFilterUN.setBackground(new java.awt.Color(220, 200, 255));
        cbFilterUN.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbFilterUN.setForeground(new java.awt.Color(117, 1, 93));
        cbFilterUN.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select User" }));
        cbFilterUN.setBorder(null);
        cbFilterUN.setMaximumSize(new java.awt.Dimension(101, 26));
        cbFilterUN.setMinimumSize(new java.awt.Dimension(101, 26));
        cbFilterUN.setNextFocusableComponent(cbFilterPr);
        cbFilterUN.setPreferredSize(new java.awt.Dimension(101, 26));
        cbFilterUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterUNActionPerformed(evt);
            }
        });

        cbFilterUA.setBackground(new java.awt.Color(220, 200, 255));
        cbFilterUA.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbFilterUA.setForeground(new java.awt.Color(117, 1, 93));
        cbFilterUA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Action" }));
        cbFilterUA.setBorder(null);
        cbFilterUA.setMaximumSize(new java.awt.Dimension(101, 26));
        cbFilterUA.setMinimumSize(new java.awt.Dimension(101, 26));
        cbFilterUA.setNextFocusableComponent(cbFilterWk);
        cbFilterUA.setPreferredSize(new java.awt.Dimension(101, 26));
        cbFilterUA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterUAActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbFilterUN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbFilterUA, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbFilterEn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbFilterPr, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbFilterWk, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(invisiblePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbFilterWk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbFilterPr, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbFilterEn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbFilterUA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbFilterUN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(invisiblePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbFilterEn, cbFilterPr, cbFilterWk});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel13, jLabel18});

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
    
    private void cbFilterWkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterWkActionPerformed
        String Name = (String)cbFilterWk.getSelectedItem();  // create string of selected item in combo box
        lbFilterWk.setText("" + Name);  // set label text to string of selected item
        updateTable();  //  call method to update the table
    }//GEN-LAST:event_cbFilterWkActionPerformed
    
    private void cbFilterPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterPrActionPerformed
        String Name = (String)cbFilterPr.getSelectedItem();  // create string of selected item in combo box
        lbFilterPr.setText("" + Name);  // set label text to string of selected item
        updateTable();  //  call method to update the table
    }//GEN-LAST:event_cbFilterPrActionPerformed
    
    private void cbFilterEnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterEnActionPerformed
        String Name = (String)cbFilterEn.getSelectedItem();  // create string of selected item in combo box
        lbFilterEn.setText("" + Name);  // set label text to string of selected item
        updateTable();  //  call method to update the table
    }//GEN-LAST:event_cbFilterEnActionPerformed
    
    private void tableBookingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBookingsMouseClicked
        
    }//GEN-LAST:event_tableBookingsMouseClicked
    
    private void cbFilterUNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterUNActionPerformed
        String Name = (String)cbFilterUN.getSelectedItem();  // create string of selected item in combo box
        lbFilterUN.setText("" + Name);  // set label text to string of selected item
        updateTable();  //  call method to update the table
    }//GEN-LAST:event_cbFilterUNActionPerformed
    
    private void cbFilterUAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterUAActionPerformed
        String Name = (String)cbFilterUA.getSelectedItem();  // create string of selected item in combo box
        lbFilterUA.setText("" + Name);  // set label text to string of selected item
        updateTable();  //  call method to update the table
    }//GEN-LAST:event_cbFilterUAActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       limitLog(); 
    }//GEN-LAST:event_jButton1ActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonAdmin;
    private javax.swing.JButton buttonEditDetails;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JButton buttonViewEngineers;
    private javax.swing.JButton buttonViewProjects;
    private javax.swing.JButton buttonViewTime;
    private javax.swing.JButton buttonViewWeek;
    private javax.swing.JComboBox cbFilterEn;
    private javax.swing.JComboBox cbFilterPr;
    private javax.swing.JComboBox cbFilterUA;
    private javax.swing.JComboBox cbFilterUN;
    private javax.swing.JComboBox cbFilterWk;
    private javax.swing.JPanel invisiblePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbFilterEn;
    private javax.swing.JLabel lbFilterPr;
    private javax.swing.JLabel lbFilterUA;
    private javax.swing.JLabel lbFilterUN;
    private javax.swing.JLabel lbFilterWk;
    private javax.swing.JTable tableBookings;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}

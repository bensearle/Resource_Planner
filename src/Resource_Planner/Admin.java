/*
 * The Firstco Assistant Resourcing Tool, designed and built by Ben Searle
 * for IN2030 Work Based Project at City University London
 */
package Resource_Planner;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Ben Searle
 */
public class Admin extends javax.swing.JPanel {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    /**
     * Creates new form EditDetails
     */
    public Admin() {
        initComponents();
    }
    
    /**
     * method to be called every time the panel is set to visible
     * clear data, hide items, call methods, retrieve connection
     */
    public void updatePage(){
        conn = JavaConnect.ConnectDB();  //  get the connection url
        updateTableWeeks();  //  populate the table
        fillWeek();  //  populate the username combo bocx
        lbDelWE.setVisible(false);  //  hide label
    }
    
    /**
     * method populates the table with data from the SQL Server, BSWeek table.
     */
    public void updateTableWeeks(){
        try {
            String sql = "SELECT we AS [Week Ending] FROM BSWeek;";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableWeeks.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method populates week combo box with all existing weeks
     */
    public void fillWeek(){
        cbDelWE.removeAllItems();  // remove all items from the combo box
        cbDelWE.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT we FROM BSWeek";  // select names from the week table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("we");  //  create a string for each of the names in the database
                cbDelWE.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method adds a week to the table BSWeek
     */
    public void addWeek(){
        try {
            //  insert week in to BSWeek
            String sql = "INSERT INTO BSWeek (we) VALUES (?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, tbWE.getText());  // value to be inserted into the table
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void listZero(){
        String zeroWeek = tbWE.getText();
        try {
            String sql = "SELECT dn FROM BSUser ORDER BY dn";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String zeroEngineer = rs.getString("dn");  //  create a string for each of the names in the database
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
     * method deletes selected week
     */
    public void deleteWeek(){
        exportWeek(lbDelWE.getText());
        try {
            String sql = "DELETE FROM BSWeek WHERE we = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, lbDelWE.getText());  //  week ending to be deleted
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            String sql = "DELETE FROM BSBooking WHERE bDate = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, lbDelWE.getText());  //  week ending to be deleted
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    /**
     * method sets selected item of combo box based on the selected item in the table
     */
    public void clickTable(){
        int row = tableWeeks.getSelectedRow();  //  define row as the selected row of the table
        String un = tableWeeks.getModel().getValueAt(row, 0).toString();  //  get seleted username from table
        cbDelWE.setSelectedItem(un);  //  set selected item to selected username
    }
    
    public void export(){
        try {
            java.util.Date date= new java.util.Date();
            int day = date.getDate();
            int monthI = date.getMonth();
            String month = null;
            if (date.getMonth()<10){
                month = "0" + date.getMonth();
            }
            else if (date.getMonth()>9){
                month = "" + date.getMonth();
            }
            int year = date.getYear()+1900;
            String pr0 = "000 - Project Zero";
            String sql = "SELECT * FROM BSBooking WHERE bProject <> '"+pr0+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("ID");
            rowhead.createCell((short) 1).setCellValue("Date");
            rowhead.createCell((short) 2).setCellValue("Project");
            rowhead.createCell((short) 3).setCellValue("Engineer");
            rowhead.createCell((short) 4).setCellValue("Days");
            
            int index = 1;
            while (rs.next()) {
                
                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(rs.getInt(1));
                row.createCell((short) 1).setCellValue(rs.getDate(2));
                row.createCell((short) 2).setCellValue(rs.getString(3));
                row.createCell((short) 3).setCellValue(rs.getString(4));
                row.createCell((short) 4).setCellValue(rs.getDouble(5));
                index++;
            }
            FileOutputStream fileOut = new FileOutputStream("\\\\FST-HQ-NAS-01\\Archive\\Archive\\IT\\Ben\\FART\\DATA\\allData"+year+"_"+month+"_"+day+".xls");
            wb.write(fileOut);
            fileOut.close();
            JOptionPane.showMessageDialog(null, "Data has been exported to excel file");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void exportWeek(String week){
        try {
            String pr0 = "000 - Project Zero";
            String sql = "SELECT * FROM BSBooking WHERE bDate = '"+week+"' AND bProject <> '"+pr0+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("ID");
            rowhead.createCell((short) 1).setCellValue("Date");
            rowhead.createCell((short) 2).setCellValue("Project");
            rowhead.createCell((short) 3).setCellValue("Engineer");
            rowhead.createCell((short) 4).setCellValue("Days");
            
            int index = 1;
            while (rs.next()) {
                
                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(rs.getInt(1));
                row.createCell((short) 1).setCellValue(rs.getDate(2));
                row.createCell((short) 2).setCellValue(rs.getString(3));
                row.createCell((short) 3).setCellValue(rs.getString(4));
                row.createCell((short) 4).setCellValue(rs.getDouble(5));
                index++;
            }
            FileOutputStream fileOut = new FileOutputStream("\\\\FST-HQ-NAS-01\\Archive\\Archive\\IT\\Ben\\FART\\DATA\\'"+week+"'.xls");
            wb.write(fileOut);
            fileOut.close();
//            JOptionPane.showMessageDialog(null, "Week has been deleted");
        } catch (Exception e) {
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
        jLabel3 = new javax.swing.JLabel();
        buttonDeleteWeek = new javax.swing.JButton();
        buttonCreateWeek = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        tbWE = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableWeeks = new javax.swing.JTable();
        cbDelWE = new javax.swing.JComboBox();
        lbDelWE = new javax.swing.JLabel();
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
        setMaximumSize(new java.awt.Dimension(850, 600));
        setMinimumSize(new java.awt.Dimension(850, 600));
        setPreferredSize(new java.awt.Dimension(850, 600));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(117, 1, 93));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Week");

        buttonDeleteWeek.setBackground(new java.awt.Color(220, 200, 255));
        buttonDeleteWeek.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonDeleteWeek.setForeground(new java.awt.Color(117, 1, 93));
        buttonDeleteWeek.setText("Delete Week");
        buttonDeleteWeek.setBorder(null);
        buttonDeleteWeek.setNextFocusableComponent(tbWE);
        buttonDeleteWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteWeekActionPerformed(evt);
            }
        });

        buttonCreateWeek.setBackground(new java.awt.Color(220, 200, 255));
        buttonCreateWeek.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonCreateWeek.setForeground(new java.awt.Color(117, 1, 93));
        buttonCreateWeek.setText("Create New Week");
        buttonCreateWeek.setBorder(null);
        buttonCreateWeek.setNextFocusableComponent(cbDelWE);
        buttonCreateWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateWeekActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(117, 1, 93));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Week Ending");

        tbWE.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tbWE.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        tbWE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbWEActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(117, 1, 93));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Create Week");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(117, 1, 93));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Delete Week");

        tableWeeks.setModel(new javax.swing.table.DefaultTableModel(
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
        tableWeeks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableWeeksMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableWeeks);

        cbDelWE.setBackground(new java.awt.Color(220, 200, 255));
        cbDelWE.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbDelWE.setForeground(new java.awt.Color(117, 1, 93));
        cbDelWE.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Existing Week" }));
        cbDelWE.setBorder(null);
        cbDelWE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDelWEActionPerformed(evt);
            }
        });

        lbDelWE.setText("Week");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel.png"))); // NOI18N
        jButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton1.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton1.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton1.setName(""); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(100, 100));
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tbWE, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonCreateWeek, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonDeleteWeek, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbDelWE, 0, 231, Short.MAX_VALUE)
                    .addComponent(lbDelWE, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonCreateWeek, buttonDeleteWeek, cbDelWE, jLabel7, jLabel8, tbWE});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbDelWE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(buttonDeleteWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbDelWE)
                        .addGap(688, 688, 688))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(tbWE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(buttonCreateWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonCreateWeek, buttonDeleteWeek, cbDelWE, jLabel7, jLabel8, tbWE});

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
    
    private void tbWEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbWEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbWEActionPerformed
    
    private void buttonCreateWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateWeekActionPerformed
        addWeek();  // call method to add user
        listZero();
        updateTableWeeks();  // update the user table
        fillWeek();
        tbWE.setText("");  // clear text field after database has been updated
    }//GEN-LAST:event_buttonCreateWeekActionPerformed
    
    private void buttonDeleteWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteWeekActionPerformed
        deleteWeek();  //  call method to delete user
        updateTableWeeks();  // update the user table
        cbDelWE.setSelectedIndex(0);  //  clear box
        lbDelWE.setText("");  //  clear box
    }//GEN-LAST:event_buttonDeleteWeekActionPerformed
    
    private void cbDelWEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDelWEActionPerformed
        String Name = (String)cbDelWE.getSelectedItem();  // create string of selected item in combo box
        lbDelWE.setText("" + Name);  // set label text to string of selected item
    }//GEN-LAST:event_cbDelWEActionPerformed
    
    private void tableWeeksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableWeeksMouseClicked
        // TODO add your handling code here:
        clickTable();  //  call method to when table is clicked
    }//GEN-LAST:event_tableWeeksMouseClicked
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        export();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonAdmin;
    private javax.swing.JButton buttonCreateWeek;
    private javax.swing.JButton buttonDeleteWeek;
    private javax.swing.JButton buttonEditDetails;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JButton buttonViewEngineers;
    private javax.swing.JButton buttonViewProjects;
    private javax.swing.JButton buttonViewTime;
    private javax.swing.JButton buttonViewWeek;
    private javax.swing.JComboBox cbDelWE;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbDelWE;
    private javax.swing.JTable tableWeeks;
    private javax.swing.JTextField tbWE;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}

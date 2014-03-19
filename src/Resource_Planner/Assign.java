/*
 * The Firstco Assistant Resourcing Tool, designed and built by Ben Searle
 * for IN2030 Work Based Project at City University London
 */
package Resource_Planner;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

import java.sql.Timestamp;

/**
 * @author Ben Searle
 */
public class Assign extends javax.swing.JPanel {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    public String loggedInUser;  //  String for the username used to log in
    //   java.util.Date date= new java.util.Date();
    //   Timestamp currentTimestamp = new Timestamp(date.getTime());
    /**
     * Creates new form ViewData
     */
    public Assign() {
        initComponents();
    }
    
    /**
     * method to be called every time the panel is set to visible
     * clear data, hide items, call methods, retrieve connection
     */
    public void updatePage(){
        conn = JavaConnect.ConnectDB();  //  get the connection url
        /*  lbFilterWk.setVisible(false);  //  hide object
         * lbFilterPr.setVisible(false);  //  hide object
         * lbFilterEn1.setVisible(false);  //  hide object
         * lbFilterEn2.setVisible(false);  //  hide object
         * lbFilterEn3.setVisible(false);  //  hide object
         * lbFilterEn4.setVisible(false);  //  hide object
         * lbFilterEn5.setVisible(false);  //  hide object
         * lbFilterEn6.setVisible(false);  //  hide object
         * lbFilterEn7.setVisible(false);  //  hide object
         * lbFilterEn8.setVisible(false);  //  hide object
         * lbFilterEn9.setVisible(false);  //  hide object
         * lbFilterEn10.setVisible(false);  //  hide object
         *
         */
        invisiblePanel.setVisible(false);
        
        
        fillEngineer();  //  populate combo box
        fillProject();  //  populate combo box
        fillWeek();  //  populate combo box
        fillDays();
        updateTable();  // populate the table
        updateTableEng();
    }
    
    
    /**
     * update method updates the table
     * there are many variations of how the table is populated
     */
    public void updateTable(){
        String bPr = lbFilterPr.getText();  //  get string from label
        String bWk = lbFilterWk.getText();  //  get string from label
        String pr0 = "000 - Project Zero";
        try {
            /*
             * populate if group is ticked, all days are summed
             */
            if (bPr.trim().length() != 0 && bWk.trim().length() != 0) {
                String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bProject = '"+bPr+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bEngineer ASC";
                pst = conn.prepareStatement(sql);
            }
            
            else if (bPr.trim().length() != 0 && bWk.trim().length() == 0) {
                String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bProject = '"+bPr+"' AND bDays <> 0 ORDER BY bEngineer ASC";
                pst = conn.prepareStatement(sql);
            }
            
            else if (bPr.trim().length() == 0 && bWk.trim().length() != 0) {
                String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bEngineer ASC";
                pst = conn.prepareStatement(sql);
            }
            
            else {
                String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bDays <> 0 ORDER BY bEngineer ASC";
                pst = conn.prepareStatement(sql);
            }
            rs = pst.executeQuery();
            tableBookings.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (Exception e){
            // JOptionPane.showMessageDialog(null, e);  // error is due to bDate = '"+bWk+"' works fine
        }
    }
    
    /**
     * update method updates the table
     * there are many variations of how the table is populated
     */
    public void updateTableEng(){
        String bEn1 = lbFilterEn1.getText();  //  get string from label
        String bEn2 = lbFilterEn2.getText();  //  get string from label
        String bEn3 = lbFilterEn3.getText();  //  get string from label
        String bEn4 = lbFilterEn4.getText();  //  get string from label
        String bEn5 = lbFilterEn5.getText();  //  get string from label
        String bEn6 = lbFilterEn6.getText();  //  get string from label
        String bEn7 = lbFilterEn7.getText();  //  get string from label
        String bEn8 = lbFilterEn8.getText();  //  get string from label
        String bEn9 = lbFilterEn9.getText();  //  get string from label
        String bEn10 = lbFilterEn10.getText();  //  get string from label
        try {
            String sql = "SELECT dn AS [Engineers] FROM BSUser WHERE dn <> '"+bEn1+"' AND dn <> '"+bEn2+"' AND dn <> '"+bEn3+"' AND dn <> '"+bEn4+"' AND dn <> '"+bEn5+"' AND dn <> '"+bEn6+"' AND dn <> '"+bEn7+"' AND dn <> '"+bEn8+"' AND dn <> '"+bEn9+"' AND dn <> '"+bEn10+"' ORDER BY dn ASC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableEng.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);  // error is due to bDate = '"+bWk+"' works fine
        }
    }
    
    
    /**
     * method to populate combo box with all engineers
     */
    public void fillEngineer(){
        eng1.removeAllItems();
        eng1.addItem("");  // add an empty item to the combo box
        eng2.removeAllItems();
        eng2.addItem("");  // add an empty item to the combo box
        eng3.removeAllItems();
        eng3.addItem("");  // add an empty item to the combo box
        eng4.removeAllItems();
        eng4.addItem("");  // add an empty item to the combo box
        eng5.removeAllItems();
        eng5.addItem("");  // add an empty item to the combo box
        eng6.removeAllItems();
        eng6.addItem("");  // add an empty item to the combo box
        eng7.removeAllItems();
        eng7.addItem("");  // add an empty item to the combo box
        eng8.removeAllItems();
        eng8.addItem("");  // add an empty item to the combo box
        eng9.removeAllItems();
        eng9.addItem("");  // add an empty item to the combo box
        eng10.removeAllItems();
        eng10.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT dn FROM BSUser ORDER BY dn ASC";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("dn");  //  create a string for each of the names in the database
                eng1.addItem(name);  // add the string to the combo box list
                eng2.addItem(name);  // add the string to the combo box list
                eng3.addItem(name);  // add the string to the combo box list
                eng4.addItem(name);  // add the string to the combo box list
                eng5.addItem(name);  // add the string to the combo box list
                eng6.addItem(name);  // add the string to the combo box list
                eng7.addItem(name);  // add the string to the combo box list
                eng8.addItem(name);  // add the string to the combo box list
                eng9.addItem(name);  // add the string to the combo box list
                eng10.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method to populate combo box with all projects
     */
    public void fillProject(){
        cbFilterPr.removeAllItems();
        cbFilterPr.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT pName FROM BSProject ORDER BY pName DESC";  // select projects from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("pName");  //  create a string for each of the projects in the database
                cbFilterPr.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        cbFilterPr.removeItem("000 - Project Zero");
    }
    
    /**
     * method to populate combo box with all weeks in the system
     */
    public void fillWeek(){
        cbFilterWk.removeAllItems();
        cbFilterWk.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT we FROM BSWeek ORDER BY we ASC";  // select weeks from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("we");  //  create a string for each of the weeks in the database
                cbFilterWk.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void fillDays (){
        cbFilterDy.removeAllItems();  // remove all items from the combo box
        cbFilterDy.addItem("5.0");  //  add item to combo box
        cbFilterDy.addItem("4.5");  //  add item to combo box
        cbFilterDy.addItem("4.0");  //  add item to combo box
        cbFilterDy.addItem("3.5");  //  add item to combo box
        cbFilterDy.addItem("3.0");  //  add item to combo box
        cbFilterDy.addItem("2.5");  //  add item to combo box
        cbFilterDy.addItem("2.0");  //  add item to combo box
        cbFilterDy.addItem("1.5");  //  add item to combo box
        cbFilterDy.addItem("1.0");  //  add item to combo box
        cbFilterDy.addItem("0.5");  //  add item to combo box
    }
    
    /**
     * method to add bookings in to the system
     */
    public void superAssign(){
        java.util.Date date= new java.util.Date();
        Timestamp currentTimestamp = new Timestamp(date.getTime());
        String bEn1 = lbFilterEn1.getText();  //  string of label
        String bEn2 = lbFilterEn2.getText();  //  string of label
        String bEn3 = lbFilterEn3.getText();  //  string of label
        String bEn4 = lbFilterEn4.getText();  //  string of label
        String bEn5 = lbFilterEn5.getText();  //  string of label
        String bEn6 = lbFilterEn6.getText();  //  string of label
        String bEn7 = lbFilterEn7.getText();  //  string of label
        String bEn8 = lbFilterEn8.getText();  //  string of label
        String bEn9 = lbFilterEn9.getText();  //  string of label
        String bEn10 = lbFilterEn10.getText();  //  string of label
        String bPr = lbFilterPr.getText();  //  string of label
        String bWk = lbFilterWk.getText();  //  string of label
        String bDy = lbFilterDy.getText();  //  string of label
        
        if (eng1.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate, lbDays) VALUES (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Assign");  //  value to add to database
                pst.setString(4, lbFilterEn1.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.setString(7, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try{
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn1+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn1.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng2.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate, lbDays) VALUES (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Assign");  //  value to add to database
                pst.setString(4, lbFilterEn2.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.setString(7, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try{
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn2+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn2.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng3.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate, lbDays) VALUES (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Assign");  //  value to add to database
                pst.setString(4, lbFilterEn3.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.setString(7, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try{
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn3+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn3.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng4.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate, lbDays) VALUES (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Assign");  //  value to add to database
                pst.setString(4, lbFilterEn4.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.setString(7, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try{
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn4+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn4.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng5.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate, lbDays) VALUES (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Assign");  //  value to add to database
                pst.setString(4, lbFilterEn5.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.setString(7, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try{
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn5+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn5.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng6.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate, lbDays) VALUES (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Assign");  //  value to add to database
                pst.setString(4, lbFilterEn6.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.setString(7, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try{
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn6+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn6.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng7.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate, lbDays) VALUES (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Assign");  //  value to add to database
                pst.setString(4, lbFilterEn7.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.setString(7, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try{
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn7+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn7.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng8.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate, lbDays) VALUES (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Assign");  //  value to add to database
                pst.setString(4, lbFilterEn8.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.setString(7, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try{
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn8+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn8.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng9.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate, lbDays) VALUES (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Assign");  //  value to add to database
                pst.setString(4, lbFilterEn9.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.setString(7, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try{
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn9+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn9.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng10.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate, lbDays) VALUES (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Assign");  //  value to add to database
                pst.setString(4, lbFilterEn10.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.setString(7, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try{
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn10+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn10.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    /**
     * method to add bookings in to the system
     */
    public void assign(){
        try {
            if (eng1.getSelectedIndex() != 0){
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn1.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }
            if (eng2.getSelectedIndex() != 0){
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn2.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }
            if (eng3.getSelectedIndex() != 0){
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn3.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }
            if (eng4.getSelectedIndex() != 0){
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn4.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }
            if (eng5.getSelectedIndex() != 0){
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn5.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }
            if (eng6.getSelectedIndex() != 0){
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn6.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }
            if (eng7.getSelectedIndex() != 0){
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn7.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }
            if (eng8.getSelectedIndex() != 0){
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn8.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }
            if (eng9.getSelectedIndex() != 0){
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn9.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }
            if (eng10.getSelectedIndex() != 0){
                String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, lbFilterEn10.getText());  //  value to add to database
                pst.setString(2, lbFilterPr.getText());  //  value to add to database
                pst.setString(3, lbFilterWk.getText());  //  value to add to database
                pst.setString(4, lbFilterDy.getText());  //  value to add to database
                pst.execute();
            }
            
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method to remove bookings in to the system
     */
    public void unassign(){
        java.util.Date date= new java.util.Date();
        Timestamp currentTimestamp = new Timestamp(date.getTime());
        String bEn1 = lbFilterEn1.getText();  //  string of label
        String bEn2 = lbFilterEn2.getText();  //  string of label
        String bEn3 = lbFilterEn3.getText();  //  string of label
        String bEn4 = lbFilterEn4.getText();  //  string of label
        String bEn5 = lbFilterEn5.getText();  //  string of label
        String bEn6 = lbFilterEn6.getText();  //  string of label
        String bEn7 = lbFilterEn7.getText();  //  string of label
        String bEn8 = lbFilterEn8.getText();  //  string of label
        String bEn9 = lbFilterEn9.getText();  //  string of label
        String bEn10 = lbFilterEn10.getText();  //  string of label
        String bPr = lbFilterPr.getText();  //  string of label
        String bWk = lbFilterWk.getText();  //  string of label
        if (eng1.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Unassign");  //  value to add to database
                pst.setString(4, lbFilterEn1.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn1+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng2.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Unassign");  //  value to add to database
                pst.setString(4, lbFilterEn2.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn2+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng3.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Unassign");  //  value to add to database
                pst.setString(4, lbFilterEn3.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn3+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng4.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Unassign");  //  value to add to database
                pst.setString(4, lbFilterEn4.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn4+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng5.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Unassign");  //  value to add to database
                pst.setString(4, lbFilterEn5.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn5+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng6.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Unassign");  //  value to add to database
                pst.setString(4, lbFilterEn6.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn6+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng7.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Unassign");  //  value to add to database
                pst.setString(4, lbFilterEn7.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn7+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng8.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Unassign");  //  value to add to database
                pst.setString(4, lbFilterEn8.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn8+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng9.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Unassign");  //  value to add to database
                pst.setString(4, lbFilterEn9.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn9+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (eng10.getSelectedIndex() != 0){
            try {
                String sql = "INSERT INTO BSLog (lUser, lDate, lClicked, lbEngineer, lbProject, lbDate) VALUES (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, loggedInUser);  //  value to add to database
                pst.setTimestamp(2, currentTimestamp);  //  value to add to database
                pst.setString(3, "Unassign");  //  value to add to database
                pst.setString(4, lbFilterEn10.getText());  //  value to add to database
                pst.setString(5, lbFilterPr.getText());  //  value to add to database
                pst.setString(6, lbFilterWk.getText());  //  value to add to database
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn10+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    /**
     * method to change bookings in to the system
     * method is not used, instead unassign+assign is used when assigning to prevent duplicates
     */
    public void update(){
        String bEn1 = lbFilterEn1.getText();  //  string of label
        String bEn2 = lbFilterEn2.getText();  //  string of label
        String bEn3 = lbFilterEn3.getText();  //  string of label
        String bEn4 = lbFilterEn4.getText();  //  string of label
        String bEn5 = lbFilterEn5.getText();  //  string of label
        String bEn6 = lbFilterEn6.getText();  //  string of label
        String bEn7 = lbFilterEn7.getText();  //  string of label
        String bEn8 = lbFilterEn8.getText();  //  string of label
        String bEn9 = lbFilterEn9.getText();  //  string of label
        String bEn10 = lbFilterEn10.getText();  //  string of label
        String bPr = lbFilterPr.getText();  //  string of label
        String bWk = lbFilterWk.getText();  //  string of label
        String bDy = lbFilterDy.getText();  //  string of label
        try {
            if (eng1.getSelectedIndex() != 0){
                String sql = "UPDATE BSBooking SET bDays = '"+bDy+"' WHERE bEngineer = '"+bEn1+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
            if (eng2.getSelectedIndex() != 0){
                String sql = "UPDATE BSBooking SET bDays = '"+bDy+"' WHERE bEngineer = '"+bEn2+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
            if (eng3.getSelectedIndex() != 0){
                String sql = "UPDATE BSBooking SET bDays = '"+bDy+"' WHERE bEngineer = '"+bEn3+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
            if (eng4.getSelectedIndex() != 0){
                String sql = "UPDATE BSBooking SET bDays = '"+bDy+"' WHERE bEngineer = '"+bEn4+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
            if (eng5.getSelectedIndex() != 0){
                String sql = "UPDATE BSBooking SET bDays = '"+bDy+"' WHERE bEngineer = '"+bEn5+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
            if (eng6.getSelectedIndex() != 0){
                String sql = "UPDATE BSBooking SET bDays = '"+bDy+"' WHERE bEngineer = '"+bEn6+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
            if (eng7.getSelectedIndex() != 0){
                String sql = "UPDATE BSBooking SET bDays = '"+bDy+"' WHERE bEngineer = '"+bEn7+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
            if (eng8.getSelectedIndex() != 0){
                String sql = "UPDATE BSBooking SET bDays = '"+bDy+"' WHERE bEngineer = '"+bEn8+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
            if (eng9.getSelectedIndex() != 0){
                String sql = "UPDATE BSBooking SET bDays = '"+bDy+"' WHERE bEngineer = '"+bEn9+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
            if (eng10.getSelectedIndex() != 0){
                String sql = "UPDATE BSBooking SET bDays = '"+bDy+"' WHERE bEngineer = '"+bEn10+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method to update combo boxes with selected line from table
     */
    public void clickTableEng(){
        int row = tableEng.getSelectedRow();  //  chose the selected row
        String eng = tableEng.getModel().getValueAt(row, 0).toString();  //  creates string of selected item
        if (eng1.getSelectedIndex() == 0){
            eng1.setSelectedItem(eng);
        }
        else if (eng2.getSelectedIndex() == 0){
            eng2.setSelectedItem(eng);
        }
        else if (eng3.getSelectedIndex() == 0){
            eng3.setSelectedItem(eng);
        }
        else if (eng4.getSelectedIndex() == 0){
            eng4.setSelectedItem(eng);
        }
        else if (eng5.getSelectedIndex() == 0){
            eng5.setSelectedItem(eng);
        }
        else if (eng6.getSelectedIndex() == 0){
            eng6.setSelectedItem(eng);
        }
        else if (eng7.getSelectedIndex() == 0){
            eng7.setSelectedItem(eng);
        }
        else if (eng8.getSelectedIndex() == 0){
            eng8.setSelectedItem(eng);
        }
        else if (eng9.getSelectedIndex() == 0){
            eng9.setSelectedItem(eng);
        }
        else if (eng10.getSelectedIndex() == 0){
            eng10.setSelectedItem(eng);
        }
        updateTableEng();
    }
    /**
     * method to update combo boxes with selected line from table
     */
    public void clickTableBookings(){
        int row = tableBookings.getSelectedRow();  //  chose the selected row
        String engineer = tableBookings.getModel().getValueAt(row, 0).toString();  //  creates string of selected item
        String project = tableBookings.getModel().getValueAt(row, 1).toString();  //  creates string of selected item
        String week = tableBookings.getModel().getValueAt(row, 2).toString();  //  creates string of selected item
        eng1.setSelectedItem(engineer);
        eng2.setSelectedIndex(0);
        eng3.setSelectedIndex(0);
        eng4.setSelectedIndex(0);
        eng5.setSelectedIndex(0);
        eng6.setSelectedIndex(0);
        eng7.setSelectedIndex(0);
        eng8.setSelectedIndex(0);
        eng9.setSelectedIndex(0);
        eng10.setSelectedIndex(0);
        cbFilterPr.setSelectedItem(project);
        cbFilterWk.setSelectedItem(week);
    }
    
    public void clearEng(){
        eng1.setSelectedIndex(0);
        eng2.setSelectedIndex(0);
        eng3.setSelectedIndex(0);
        eng4.setSelectedIndex(0);
        eng5.setSelectedIndex(0);
        eng6.setSelectedIndex(0);
        eng7.setSelectedIndex(0);
        eng8.setSelectedIndex(0);
        eng9.setSelectedIndex(0);
        eng10.setSelectedIndex(0);
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
        eng1 = new javax.swing.JComboBox();
        cbFilterWk = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBookings = new javax.swing.JTable();
        cbFilterPr = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        buttonAssign = new javax.swing.JButton();
        buttonUnassign = new javax.swing.JButton();
        buttonNext = new javax.swing.JButton();
        eng2 = new javax.swing.JComboBox();
        eng3 = new javax.swing.JComboBox();
        eng4 = new javax.swing.JComboBox();
        eng5 = new javax.swing.JComboBox();
        eng6 = new javax.swing.JComboBox();
        eng7 = new javax.swing.JComboBox();
        eng8 = new javax.swing.JComboBox();
        eng9 = new javax.swing.JComboBox();
        eng10 = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableEng = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        cbFilterDy = new javax.swing.JComboBox();
        invisiblePanel = new javax.swing.JPanel();
        lbFilterEn2 = new javax.swing.JLabel();
        lbFilterEn8 = new javax.swing.JLabel();
        lbFilterEn1 = new javax.swing.JLabel();
        lbFilterEn3 = new javax.swing.JLabel();
        lbFilterEn6 = new javax.swing.JLabel();
        lbFilterEn10 = new javax.swing.JLabel();
        lbFilterEn4 = new javax.swing.JLabel();
        lbFilterEn9 = new javax.swing.JLabel();
        lbFilterPr = new javax.swing.JLabel();
        lbFilterDy = new javax.swing.JLabel();
        lbFilterEn5 = new javax.swing.JLabel();
        lbFilterWk = new javax.swing.JLabel();
        lbFilterEn7 = new javax.swing.JLabel();
        buttonClearEng = new javax.swing.JButton();

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

        eng1.setBackground(new java.awt.Color(220, 200, 255));
        eng1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eng1.setForeground(new java.awt.Color(117, 1, 93));
        eng1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Engineer" }));
        eng1.setBorder(null);
        eng1.setMaximumSize(new java.awt.Dimension(101, 26));
        eng1.setMinimumSize(new java.awt.Dimension(101, 26));
        eng1.setNextFocusableComponent(cbFilterPr);
        eng1.setPreferredSize(new java.awt.Dimension(101, 26));
        eng1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eng1ActionPerformed(evt);
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
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Project");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(117, 1, 93));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Week");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(117, 1, 93));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Engineers");

        buttonAssign.setBackground(new java.awt.Color(220, 200, 255));
        buttonAssign.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonAssign.setForeground(new java.awt.Color(117, 1, 93));
        buttonAssign.setText("Assign");
        buttonAssign.setBorder(null);
        buttonAssign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAssignActionPerformed(evt);
            }
        });

        buttonUnassign.setBackground(new java.awt.Color(220, 200, 255));
        buttonUnassign.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonUnassign.setForeground(new java.awt.Color(117, 1, 93));
        buttonUnassign.setText("Unassign");
        buttonUnassign.setBorder(null);
        buttonUnassign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUnassignActionPerformed(evt);
            }
        });

        buttonNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nextSmall.png"))); // NOI18N
        buttonNext.setFocusPainted(false);
        buttonNext.setMaximumSize(new java.awt.Dimension(20, 27));
        buttonNext.setMinimumSize(new java.awt.Dimension(20, 27));
        buttonNext.setPreferredSize(new java.awt.Dimension(20, 27));
        buttonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });

        eng2.setBackground(new java.awt.Color(220, 200, 255));
        eng2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eng2.setForeground(new java.awt.Color(117, 1, 93));
        eng2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Engineer" }));
        eng2.setBorder(null);
        eng2.setMaximumSize(new java.awt.Dimension(101, 26));
        eng2.setMinimumSize(new java.awt.Dimension(101, 26));
        eng2.setNextFocusableComponent(cbFilterPr);
        eng2.setPreferredSize(new java.awt.Dimension(101, 26));
        eng2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eng2ActionPerformed(evt);
            }
        });

        eng3.setBackground(new java.awt.Color(220, 200, 255));
        eng3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eng3.setForeground(new java.awt.Color(117, 1, 93));
        eng3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Engineer" }));
        eng3.setBorder(null);
        eng3.setMaximumSize(new java.awt.Dimension(101, 26));
        eng3.setMinimumSize(new java.awt.Dimension(101, 26));
        eng3.setNextFocusableComponent(cbFilterPr);
        eng3.setPreferredSize(new java.awt.Dimension(101, 26));
        eng3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eng3ActionPerformed(evt);
            }
        });

        eng4.setBackground(new java.awt.Color(220, 200, 255));
        eng4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eng4.setForeground(new java.awt.Color(117, 1, 93));
        eng4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Engineer" }));
        eng4.setBorder(null);
        eng4.setMaximumSize(new java.awt.Dimension(101, 26));
        eng4.setMinimumSize(new java.awt.Dimension(101, 26));
        eng4.setNextFocusableComponent(cbFilterPr);
        eng4.setPreferredSize(new java.awt.Dimension(101, 26));
        eng4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eng4ActionPerformed(evt);
            }
        });

        eng5.setBackground(new java.awt.Color(220, 200, 255));
        eng5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eng5.setForeground(new java.awt.Color(117, 1, 93));
        eng5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Engineer" }));
        eng5.setBorder(null);
        eng5.setMaximumSize(new java.awt.Dimension(101, 26));
        eng5.setMinimumSize(new java.awt.Dimension(101, 26));
        eng5.setNextFocusableComponent(cbFilterPr);
        eng5.setPreferredSize(new java.awt.Dimension(101, 26));
        eng5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eng5ActionPerformed(evt);
            }
        });

        eng6.setBackground(new java.awt.Color(220, 200, 255));
        eng6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eng6.setForeground(new java.awt.Color(117, 1, 93));
        eng6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Engineer" }));
        eng6.setBorder(null);
        eng6.setMaximumSize(new java.awt.Dimension(101, 26));
        eng6.setMinimumSize(new java.awt.Dimension(101, 26));
        eng6.setNextFocusableComponent(cbFilterPr);
        eng6.setPreferredSize(new java.awt.Dimension(101, 26));
        eng6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eng6ActionPerformed(evt);
            }
        });

        eng7.setBackground(new java.awt.Color(220, 200, 255));
        eng7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eng7.setForeground(new java.awt.Color(117, 1, 93));
        eng7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Engineer" }));
        eng7.setBorder(null);
        eng7.setMaximumSize(new java.awt.Dimension(101, 26));
        eng7.setMinimumSize(new java.awt.Dimension(101, 26));
        eng7.setNextFocusableComponent(cbFilterPr);
        eng7.setPreferredSize(new java.awt.Dimension(101, 26));
        eng7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eng7ActionPerformed(evt);
            }
        });

        eng8.setBackground(new java.awt.Color(220, 200, 255));
        eng8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eng8.setForeground(new java.awt.Color(117, 1, 93));
        eng8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Engineer" }));
        eng8.setBorder(null);
        eng8.setMaximumSize(new java.awt.Dimension(101, 26));
        eng8.setMinimumSize(new java.awt.Dimension(101, 26));
        eng8.setNextFocusableComponent(cbFilterPr);
        eng8.setPreferredSize(new java.awt.Dimension(101, 26));
        eng8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eng8ActionPerformed(evt);
            }
        });

        eng9.setBackground(new java.awt.Color(220, 200, 255));
        eng9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eng9.setForeground(new java.awt.Color(117, 1, 93));
        eng9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Engineer" }));
        eng9.setBorder(null);
        eng9.setMaximumSize(new java.awt.Dimension(101, 26));
        eng9.setMinimumSize(new java.awt.Dimension(101, 26));
        eng9.setNextFocusableComponent(cbFilterPr);
        eng9.setPreferredSize(new java.awt.Dimension(101, 26));
        eng9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eng9ActionPerformed(evt);
            }
        });

        eng10.setBackground(new java.awt.Color(220, 200, 255));
        eng10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eng10.setForeground(new java.awt.Color(117, 1, 93));
        eng10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Engineer" }));
        eng10.setBorder(null);
        eng10.setMaximumSize(new java.awt.Dimension(101, 26));
        eng10.setMinimumSize(new java.awt.Dimension(101, 26));
        eng10.setNextFocusableComponent(cbFilterPr);
        eng10.setPreferredSize(new java.awt.Dimension(101, 26));
        eng10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eng10ActionPerformed(evt);
            }
        });

        tableEng.setModel(new javax.swing.table.DefaultTableModel(
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
        tableEng.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEngMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableEng);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(117, 1, 93));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Days");

        cbFilterDy.setBackground(new java.awt.Color(220, 200, 255));
        cbFilterDy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbFilterDy.setForeground(new java.awt.Color(117, 1, 93));
        cbFilterDy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Days" }));
        cbFilterDy.setBorder(null);
        cbFilterDy.setMaximumSize(new java.awt.Dimension(101, 26));
        cbFilterDy.setMinimumSize(new java.awt.Dimension(101, 26));
        cbFilterDy.setNextFocusableComponent(cbFilterWk);
        cbFilterDy.setPreferredSize(new java.awt.Dimension(101, 26));
        cbFilterDy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterDyActionPerformed(evt);
            }
        });

        lbFilterEn2.setText("FilterEngineer2");

        lbFilterEn8.setText("FilterEngineer8");

        lbFilterEn1.setText("FilterEngineer1");

        lbFilterEn3.setText("FilterEngineer3");

        lbFilterEn6.setText("FilterEngineer6");

        lbFilterEn10.setText("FilterEngineer10");

        lbFilterEn4.setText("FilterEngineer4");

        lbFilterEn9.setText("FilterEngineer9");

        lbFilterPr.setText("FilterProject");

        lbFilterDy.setText("FilterDays");

        lbFilterEn5.setText("FilterEngineer5");

        lbFilterWk.setText("FilterWeek");

        lbFilterEn7.setText("FilterEngineer7");

        javax.swing.GroupLayout invisiblePanelLayout = new javax.swing.GroupLayout(invisiblePanel);
        invisiblePanel.setLayout(invisiblePanelLayout);
        invisiblePanelLayout.setHorizontalGroup(
            invisiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(invisiblePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(invisiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbFilterEn1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(invisiblePanelLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(invisiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(invisiblePanelLayout.createSequentialGroup()
                                .addGroup(invisiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(invisiblePanelLayout.createSequentialGroup()
                                        .addComponent(lbFilterPr, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbFilterWk, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(invisiblePanelLayout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(lbFilterEn3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbFilterDy, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(invisiblePanelLayout.createSequentialGroup()
                                .addComponent(lbFilterEn2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(lbFilterEn4)
                                .addGap(18, 18, 18)
                                .addComponent(lbFilterEn5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbFilterEn6)
                                .addGap(18, 18, 18)
                                .addComponent(lbFilterEn7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbFilterEn8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbFilterEn9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbFilterEn10)))))
                .addContainerGap())
        );
        invisiblePanelLayout.setVerticalGroup(
            invisiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(invisiblePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(invisiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFilterPr)
                    .addComponent(lbFilterWk)
                    .addComponent(lbFilterDy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbFilterEn1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(invisiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFilterEn2)
                    .addComponent(lbFilterEn4)
                    .addComponent(lbFilterEn5)
                    .addComponent(lbFilterEn6)
                    .addComponent(lbFilterEn7)
                    .addComponent(lbFilterEn8)
                    .addComponent(lbFilterEn9)
                    .addComponent(lbFilterEn10))
                .addGap(5, 5, 5)
                .addComponent(lbFilterEn3)
                .addContainerGap())
        );

        buttonClearEng.setBackground(new java.awt.Color(220, 200, 255));
        buttonClearEng.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonClearEng.setForeground(new java.awt.Color(117, 1, 93));
        buttonClearEng.setText("<-- Clear Engineers");
        buttonClearEng.setBorder(null);
        buttonClearEng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearEngActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbFilterDy, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbFilterPr, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(eng1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eng6, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(eng2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eng7, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(eng3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eng8, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(eng4, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eng9, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(eng5, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eng10, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbFilterWk, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(buttonUnassign, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonAssign, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buttonClearEng, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(invisiblePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbFilterPr, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(cbFilterWk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(cbFilterDy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)
                        .addComponent(jLabel18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(eng1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(eng6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(eng2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(eng7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonAssign)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonUnassign, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(eng3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eng8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(eng4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eng9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(eng5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eng10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonClearEng, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addGap(35, 35, 35)
                .addComponent(invisiblePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbFilterPr, cbFilterWk, eng1});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonAssign, buttonUnassign});

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
    
    private void eng1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eng1ActionPerformed
        String Name = (String)eng1.getSelectedItem();  // create string of selected item in combo box
        lbFilterEn1.setText("" + Name);  // set label text to string of selected item
        updateTableEng();  //  call method to update the table
    }//GEN-LAST:event_eng1ActionPerformed
    
    private void buttonAssignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAssignActionPerformed
        //      unassign();
        superAssign();  //  call method to assign
        updateTable();  //  call method to update the table
    }//GEN-LAST:event_buttonAssignActionPerformed
    
    private void buttonUnassignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUnassignActionPerformed
        unassign();  //  call method to unassign
        updateTable();  //  call method to update the table
    }//GEN-LAST:event_buttonUnassignActionPerformed
    
    private void tableBookingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBookingsMouseClicked
clickTableBookings();        
    }//GEN-LAST:event_tableBookingsMouseClicked
    
    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        // TODO add your handling code here:
        int count = cbFilterWk.getItemCount();
        if (cbFilterWk.getSelectedIndex()<count-1){
            int next = (int)cbFilterWk.getSelectedIndex()+1;
            cbFilterWk.setSelectedIndex(next);
            String name = (String)cbFilterWk.getSelectedItem();
            lbFilterWk.setText("" + name);  // set label text to string of selected item
        }
        
    }//GEN-LAST:event_buttonNextActionPerformed
    
    private void eng2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eng2ActionPerformed
        // TODO add your handling code here:
        String Name = (String)eng2.getSelectedItem();  // create string of selected item in combo box
        lbFilterEn2.setText("" + Name);  // set label text to string of selected item
        updateTableEng();  //  call method to update the table
    }//GEN-LAST:event_eng2ActionPerformed
    
    private void eng3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eng3ActionPerformed
        // TODO add your handling code here:
        String Name = (String)eng3.getSelectedItem();  // create string of selected item in combo box
        lbFilterEn3.setText("" + Name);  // set label text to string of selected item
        updateTableEng();  //  call method to update the table
    }//GEN-LAST:event_eng3ActionPerformed
    
    private void eng4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eng4ActionPerformed
        // TODO add your handling code here:
        String Name = (String)eng4.getSelectedItem();  // create string of selected item in combo box
        lbFilterEn4.setText("" + Name);  // set label text to string of selected item
        updateTableEng();  //  call method to update the table
    }//GEN-LAST:event_eng4ActionPerformed
    
    private void eng5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eng5ActionPerformed
        // TODO add your handling code here:
        String Name = (String)eng5.getSelectedItem();  // create string of selected item in combo box
        lbFilterEn5.setText("" + Name);  // set label text to string of selected item
        updateTableEng();  //  call method to update the table
    }//GEN-LAST:event_eng5ActionPerformed
    
    private void eng6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eng6ActionPerformed
        // TODO add your handling code here:
        String Name = (String)eng6.getSelectedItem();  // create string of selected item in combo box
        lbFilterEn6.setText("" + Name);  // set label text to string of selected item
        updateTableEng();  //  call method to update the table
    }//GEN-LAST:event_eng6ActionPerformed
    
    private void eng7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eng7ActionPerformed
        // TODO add your handling code here:
        String Name = (String)eng7.getSelectedItem();  // create string of selected item in combo box
        lbFilterEn7.setText("" + Name);  // set label text to string of selected item
        updateTableEng();  //  call method to update the table
    }//GEN-LAST:event_eng7ActionPerformed
    
    private void eng8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eng8ActionPerformed
        // TODO add your handling code here:
        String Name = (String)eng8.getSelectedItem();  // create string of selected item in combo box
        lbFilterEn8.setText("" + Name);  // set label text to string of selected item
        updateTableEng();  //  call method to update the table
    }//GEN-LAST:event_eng8ActionPerformed
    
    private void eng9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eng9ActionPerformed
        // TODO add your handling code here:
        String Name = (String)eng9.getSelectedItem();  // create string of selected item in combo box
        lbFilterEn9.setText("" + Name);  // set label text to string of selected item
        updateTableEng();  //  call method to update the table
    }//GEN-LAST:event_eng9ActionPerformed
    
    private void eng10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eng10ActionPerformed
        // TODO add your handling code here:
        String Name = (String)eng10.getSelectedItem();  // create string of selected item in combo box
        lbFilterEn10.setText("" + Name);  // set label text to string of selected item
        updateTableEng();  //  call method to update the table
    }//GEN-LAST:event_eng10ActionPerformed
    
    private void cbFilterDyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterDyActionPerformed
        // TODO add your handling code here:
        String Name = (String)cbFilterDy.getSelectedItem();  // create string of selected item in combo box
        lbFilterDy.setText("" + Name);  // set label text to string of selected item
    }//GEN-LAST:event_cbFilterDyActionPerformed
    
    private void tableEngMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEngMouseClicked
        clickTableEng();  //  call method to when table is clicked
    }//GEN-LAST:event_tableEngMouseClicked
    
    private void buttonClearEngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearEngActionPerformed
        // TODO add your handling code here:
        clearEng();
    }//GEN-LAST:event_buttonClearEngActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonAdmin;
    private javax.swing.JButton buttonAssign;
    private javax.swing.JButton buttonClearEng;
    private javax.swing.JButton buttonEditDetails;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JButton buttonNext;
    private javax.swing.JButton buttonUnassign;
    private javax.swing.JButton buttonViewEngineers;
    private javax.swing.JButton buttonViewProjects;
    private javax.swing.JButton buttonViewTime;
    private javax.swing.JButton buttonViewWeek;
    private javax.swing.JComboBox cbFilterDy;
    private javax.swing.JComboBox cbFilterPr;
    private javax.swing.JComboBox cbFilterWk;
    private javax.swing.JComboBox eng1;
    private javax.swing.JComboBox eng10;
    private javax.swing.JComboBox eng2;
    private javax.swing.JComboBox eng3;
    private javax.swing.JComboBox eng4;
    private javax.swing.JComboBox eng5;
    private javax.swing.JComboBox eng6;
    private javax.swing.JComboBox eng7;
    private javax.swing.JComboBox eng8;
    private javax.swing.JComboBox eng9;
    private javax.swing.JPanel invisiblePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbFilterDy;
    private javax.swing.JLabel lbFilterEn1;
    private javax.swing.JLabel lbFilterEn10;
    private javax.swing.JLabel lbFilterEn2;
    private javax.swing.JLabel lbFilterEn3;
    private javax.swing.JLabel lbFilterEn4;
    private javax.swing.JLabel lbFilterEn5;
    private javax.swing.JLabel lbFilterEn6;
    private javax.swing.JLabel lbFilterEn7;
    private javax.swing.JLabel lbFilterEn8;
    private javax.swing.JLabel lbFilterEn9;
    private javax.swing.JLabel lbFilterPr;
    private javax.swing.JLabel lbFilterWk;
    private javax.swing.JTable tableBookings;
    private javax.swing.JTable tableEng;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}

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
public class Tables extends javax.swing.JPanel {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    /**
     * Creates new form Tables
     */
    public Tables() {
        initComponents();
    }
    
    /**
     * method to be called every time the panel is set to visible
     * clear data, hide items, call methods, retrieve connection
     */
    public void updatePage(){
        conn = JavaConnect.ConnectDB();  //  get the connection url
        lbFilterWk.setVisible(false);  //  hide object
        lbFilterPr.setVisible(false);  //  hide object
        lbFilterEn.setVisible(false);  //  hide object
        lbOrderBy.setVisible(false);  //  hide object
        lbGroupA.setVisible(false);  //  hide object
        lbGroupB.setVisible(false);  //  hide object
        cbGroupA.setVisible(false);  //  hide object
        cbGroupB.setVisible(false);  //  hide object
        jLabel16.setVisible(false);  //  hide object
        jLabel15.setVisible(false);  //  hide object
        buttonSwitch.setVisible(false);  //  hide object
        cbGroup.setSelected(false);  //  hide object
        jLabel14.setText("Order By");  //  change label text
        jLabel11.setText("Filter Table");  //  change label text
        
        cbGroupA.setVisible(false);  //  hide object
        cbGroupB.setVisible(false);  //  hide object
        jLabel16.setVisible(false);  //  hide object
        jLabel15.setVisible(false);  //  hide object
        buttonSwitch.setVisible(false);  //  hide object
        cbOrderBy.setVisible(true);  //  show object
        jLabel14.setVisible(true);  //  show object
        fillEn();  //  populate combo box
        fillPr();  //  populate combo box
        fillWk();  //  populate combo box
        fillOrder();  //  populate combo box
        fillGrouping();  //  populate combo box
        updateTable();  // populate the table
    }
    
    /**
     * update method updates the table
     * there are many variations of how the table is populated
     */
    public void updateTable(){
        String bEn = lbFilterEn.getText();  //  get string from label
        String bPr = lbFilterPr.getText();  //  get string from label
        String bWk = lbFilterWk.getText();  //  get string from label
        String bDy = lbOrderBy.getText();  //  get string from label
        try {
            /*
             * populate if group is ticked, all days are summed
             */
            if (cbGroup.isSelected() == true){
                if ("Engineer".equals(lbGroupA.getText()) && "Project".equals(lbGroupB.getText())){
                    String sql = "SELECT DISTINCT bProject AS [Project], SUM(bDays) AS [Total Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND GROUP BY bProject";
                    pst = conn.prepareStatement(sql);
                }
                else if ("Engineer".equals(lbGroupA.getText()) && "Week".equals(lbGroupB.getText())){
                    String sql = "SELECT DISTINCT bDate AS [Week Ending], SUM(bDays) AS [Total Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' GROUP BY bDate";
                    pst = conn.prepareStatement(sql);
                }
                
                else if ("Project".equals(lbGroupA.getText()) && "Engineer".equals(lbGroupB.getText())){
                    String sql = "SELECT DISTINCT bEngineer AS [Engineer], SUM(bDays) AS [Total Days] FROM BSBooking WHERE bProject = '"+bPr+"' GROUP BY bEngineer";
                    pst = conn.prepareStatement(sql);
                }
                else if ("Project".equals(lbGroupA.getText()) && "Week".equals(lbGroupB.getText())){
                    String sql = "SELECT DISTINCT bDate AS [Week Ending], SUM(bDays) AS [Total Days] FROM BSBooking WHERE bProject = '"+bPr+"' GROUP BY bDate";
                    pst = conn.prepareStatement(sql);
                }
                
                else if ("Week".equals(lbGroupA.getText()) && "Engineer".equals(lbGroupB.getText())){
                    String sql = "SELECT DISTINCT bEngineer AS [Engineer], SUM(bDays) AS [Total Days] FROM BSBooking WHERE bDate = '"+bWk+"' GROUP BY bEngineer";
                    pst = conn.prepareStatement(sql);
                }
                else if ("Week".equals(lbGroupA.getText()) && "Project".equals(lbGroupB.getText())){
                    String sql = "SELECT DISTINCT bProject AS [Project], SUM(bDays) AS [Total Days] FROM BSBooking WHERE bDate = '"+bWk+"' GROUP BY bProject";
                    pst = conn.prepareStatement(sql);
                }
            }
            //  populate if group is not ticked
            else{
                /**
                 * table is to be sorted by engineer
                 * table is filtered based on which combo boxes have been selected
                 */
                if ("Engineer".equals(lbOrderBy.getText())){
                    if (bEn.trim().length() != 0 && bPr.trim().length() != 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bEngineer";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() != 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bProject = '"+bPr+"' AND bDays <> 0 ORDER BY bEngineer";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() == 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bEngineer";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() != 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bProject = '"+bPr+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bEngineer";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() == 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bDays <> 0 ORDER BY bEngineer";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() != 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bProject = '"+bPr+"' AND bDays <> 0 ORDER BY bEngineer";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() == 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bEngineer";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() == 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bDays <> 0 ORDER BY bEngineer";
                        pst = conn.prepareStatement(sql);
                    }
                }
                /**
                 * table is to be sorted by project
                 * table is filtered based on which combo boxes have been selected
                 */
                else if ("Project".equals(lbOrderBy.getText())){
                    if (bEn.trim().length() != 0 && bPr.trim().length() != 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bProject";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() != 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bProject = '"+bPr+"' AND bDays <> 0 ORDER BY bProject";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() == 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bProject";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() != 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bProject = '"+bPr+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bProject";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() == 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bDays <> 0 ORDER BY bProject";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() != 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bProject = '"+bPr+"' AND bDays <> 0 ORDER BY bProject";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() == 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bProject";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() == 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking  WHERE bDays <> 0ORDER BY bProject";
                        pst = conn.prepareStatement(sql);
                    }
                }
                /**
                 * table is to be sorted by week
                 * table is filtered based on which combo boxes have been selected
                 */
                else if ("Date".equals(lbOrderBy.getText())){
                    if (bEn.trim().length() != 0 && bPr.trim().length() != 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bDate";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() != 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bProject = '"+bPr+"' AND bDays <> 0 ORDER BY bDate";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() == 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bDate";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() != 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bProject = '"+bPr+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bDate";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() == 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bDays <> 0 ORDER BY bDate";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() != 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bProject = '"+bPr+"' AND bDays <> 0 ORDER BY bDate";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() == 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bDate";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() == 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bDays <> 0 ORDER BY bDate";
                        pst = conn.prepareStatement(sql);
                    }
                }
                /**
                 * table is to be sorted by days
                 * table is filtered based on which combo boxes have been selected
                 */
                else if ("Days".equals(lbOrderBy.getText())){
                    if (bEn.trim().length() != 0 && bPr.trim().length() != 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bDays";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() != 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bProject = '"+bPr+"' AND bDays <> 0 ORDER BY bDays";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() == 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bDays";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() != 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bProject = '"+bPr+"' AND bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bDays";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() != 0 && bPr.trim().length() == 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bDays <> 0 ORDER BY bDays";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() != 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bProject = '"+bPr+"' AND bDays <> 0 ORDER BY bDays";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() == 0 && bWk.trim().length() != 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bDate = '"+bWk+"' AND bDays <> 0 ORDER BY bDays";
                        pst = conn.prepareStatement(sql);
                    }
                    else if (bEn.trim().length() == 0 && bPr.trim().length() == 0 && bWk.trim().length() == 0 ) {
                        String sql = "SELECT bEngineer AS [Name], bProject AS [Project], bDate AS [Week Ending], bDays AS [Days] FROM BSBooking WHERE bDays <> 0 ORDER BY bDays";
                        pst = conn.prepareStatement(sql);
                    }
                }
            }
            
            rs = pst.executeQuery();
            tableBookings.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (Exception e){
            // JOptionPane.showMessageDialog(null, e);  // error is due to bDate = '"+bWk+"' works fine
        }
    }
    
    
    public void fillGrouping(){
        // fill new user role combo box
        cbGroupA.removeAllItems();  // remove all items from the combo box
        cbGroupA.addItem("Engineer");  //  add item to combo box
        cbGroupA.addItem("Project");  //  add item to combo box
        cbGroupA.addItem("Week");  //  add item to combo box
        cbGroupA.setSelectedItem("Week");  //  add item to combo box
        // fill existing user combo box
        cbGroupB.removeAllItems();  // remove all items from the combo box
        cbGroupB.addItem("Engineer");  //  add item to combo box
        cbGroupB.addItem("Project");  //  add item to combo box
        cbGroupB.addItem("Week");  //  add item to combo box
        cbGroupB.setSelectedItem("Engineer");  //  add item to combo box
    }
    
    /**
     * method to populate combo box with engineers that appear in the table
     */
    public void fillEn(){
        cbFilterEn.removeAllItems();  // remove all items from the combo box
        cbFilterEn.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT DISTINCT bEngineer FROM BSBooking ORDER BY bEngineer ASC";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("bEngineer");  //  create a string for each of the names in the database
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
            String sql = "SELECT DISTINCT bProject FROM BSBooking ORDER BY bProject DESC";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("bProject");  //  create a string for each of the names in the database
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
            String sql = "SELECT DISTINCT bDate FROM BSBooking ORDER BY bDate ASC";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("bDate");  //  create a string for each of the names in the database
                cbFilterWk.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method to populate combo box with options to order the data
     */
    public void fillOrder(){
        cbOrderBy.removeAllItems();  // remove all items from the combo box
        cbOrderBy.addItem("Engineer");  // add an Engineer item to the combo box
        cbOrderBy.addItem("Project");  // add an Project item to the combo box
        cbOrderBy.addItem("Date");  // add an Date item to the combo box
        cbOrderBy.addItem("Days");  // add an Days item to the combo box
    }
    
    /**
     * method to populate combo box with all engineers
     */
    public void fillEngineer(){
        cbFilterEn.removeAllItems();
        cbFilterEn.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT dn FROM BSUser ORDER BY dn ASC";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("dn");  //  create a string for each of the names in the database
                cbFilterEn.addItem(name);  // add the string to the combo box list
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
        cbOrderBy.removeAllItems();  // remove all items from the combo box
        cbOrderBy.addItem("");  //  add item to combo box
        cbOrderBy.addItem("0.5");  //  add item to combo box
        cbOrderBy.addItem("1.0");  //  add item to combo box
        cbOrderBy.addItem("1.5");  //  add item to combo box
        cbOrderBy.addItem("2.0");  //  add item to combo box
        cbOrderBy.addItem("2.5");  //  add item to combo box
        cbOrderBy.addItem("3.0");  //  add item to combo box
        cbOrderBy.addItem("3.5");  //  add item to combo box
        cbOrderBy.addItem("4.0");  //  add item to combo box
        cbOrderBy.addItem("4.5");  //  add item to combo box
        cbOrderBy.addItem("5.0");  //  add item to combo box
    }
    
    /**
     * method to add bookings in to the system
     */
    public void assign(){
        try {
            String sql = "INSERT INTO BSBooking (bEngineer, bProject, bDate, bDays) VALUES (?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, lbFilterEn.getText());  //  value to add to database
            pst.setString(2, lbFilterPr.getText());  //  value to add to database
            pst.setString(3, lbFilterWk.getText());  //  value to add to database
            pst.setString(4, lbOrderBy.getText());  //  value to add to database
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method to remove bookings in to the system
     */
    public void unassign(){
        String bEn = lbFilterEn.getText();
        String bPr = lbFilterPr.getText();
        String bWk = lbFilterWk.getText();
        String bDy = lbOrderBy.getText();
        try {
            String sql = "DELETE FROM BSBooking WHERE bEngineer = '"+bEn+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
            pst = conn.prepareStatement(sql);
            //        pst.setString(1, lbModUN.getText());
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method to change bookings in to the system
     */
    public void update(){
        String bEn = lbFilterEn.getText();  //  string of label
        String bPr = lbFilterPr.getText();  //  string of label
        String bWk = lbFilterWk.getText();  //  string of label
        String bDy = lbOrderBy.getText();  //  string of label
        try {
            String sql = "UPDATE BSBooking SET bDays = '"+bDy+"' WHERE bEngineer = '"+bEn+"' AND bProject = '"+bPr+"' AND bDate = '"+bWk+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    
    /**
     * method to make a chart based on parameter passed
     */
    public void makeChart(String button){
        String bEn = lbFilterEn.getText();  //  create string from label text
        String bPr = lbFilterPr.getText();  //  create string from label text
        String bWk = lbFilterWk.getText();  //  create string from label text
        String title = "Firstco Assistant Resourcing Tool designed by Ben Searle";  //  title of new jFrame
        int x = 1280;  // set x axis for JFrame that the chart appears
        int y = 720;  // set y axis for JFrame that the chart appears
        try {
            //  shows chart for engineer against week
            if (button.equals("engineer")){
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String wk;
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bDate AS wk, bProject AS pr, bDays AS dy FROM BSBooking WHERE bEngineer = '"+bEn+"' ORDER BY bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        wk = rs.getString("wk");
                        pr = rs.getString("pr");
                        dataset.setValue(dy, pr, wk);
                    }
                }catch (Exception e){
                    //    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chart = ChartFactory.createStackedBarChart(bEn, "Week", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
                CategoryPlot p = chart.getCategoryPlot();  //  make chart
                //  set look and feel settings
                CategoryAxis xAxis =(CategoryAxis)p.getDomainAxis();
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                BarRenderer renderer = (BarRenderer)p.getRenderer();
                ChartFrame frame = new ChartFrame(title, chart);
                frame.setVisible(true);
                frame.setSize(x,y);
                ValueMarker marker = new ValueMarker(5);  //  create marker to show the maximum utilisation
                marker.setLabel("Maximum Utilisation");  //  create marker to show the maximum utilisatio
                marker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
                marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                marker.setPaint(new Color (117, 1, 93));  //  set colour
                p.addRangeMarker(marker);
            }
            //  shows chart for project against week
            else if (button.equals("project")){
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                String en;
                Double dy;
                String wk;
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bDate AS wk, bEngineer AS en, bDays AS dy FROM BSBooking WHERE bProject = '"+bPr+"' ORDER BY bEngineer ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        wk = rs.getString("wk");
                        en = rs.getString("en");
                        dataset.setValue(dy, en, wk);
                    }
                }catch (Exception e){
                    //    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chart = ChartFactory.createStackedBarChart(bPr, "Week", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
                CategoryPlot p = chart.getCategoryPlot();  //  make chart
                //  set look and feel settings
                CategoryAxis xAxis =(CategoryAxis)p.getDomainAxis();
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                BarRenderer renderer = (BarRenderer)p.getRenderer();
                ChartFrame frame = new ChartFrame(title, chart);
                frame.setVisible(true);
                frame.setSize(x,y);
            }
            //  shows chart for week against engineer
            else if (button.equals("weeken")){
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, bDays AS dy FROM BSBooking WHERE bDate = '"+bWk+"'");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        dataset.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chart = ChartFactory.createStackedBarChart(bWk, "Engineer", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
                CategoryPlot p = chart.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                BarRenderer renderer = (BarRenderer)p.getRenderer();
                CategoryPlot plot = chart.getCategoryPlot();
                CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                ChartFrame frame = new ChartFrame(title, chart);
                frame.setVisible(true);
                frame.setSize(x,y);
                ValueMarker marker = new ValueMarker(5);  //  create marker to show the maximum utilisatio
                marker.setLabel("Maximum Utilisation");  //  create marker to show the maximum utilisatio
                marker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
                marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                marker.setPaint(new Color (117, 1, 93));  //  set colour
                p.addRangeMarker(marker);
            }
            //  shows chart for week against project
            else if (button.equals("weekpr")){
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bProject, bEngineer, sumPrIndiv, sumPrTot FROM\n" +
                            "(SELECT bProject, bEngineer, SUM(bDays) AS sumPrIndiv FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk+"') GROUP by bProject, bEngineer) AS one\n" +
                            "INNER JOIN ( SELECT bProject AS pr2, SUM(bDays) AS sumPrTot FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk+"') GROUP by bProject) AS two\n" +
                            "ON one.bProject = two.pr2 ORDER BY sumPrTot DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("sumPrIndiv");
                        en = rs.getString("bEngineer");
                        pr = rs.getString("bProject");
                        dataset.setValue(dy, en, pr);
                    }
                }catch (Exception e){
                    //    JOptionPane.showMessageDialog(null, e);
                }
                
                JFreeChart chart = ChartFactory.createStackedBarChart(""+bWk+"", "Project", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
                CategoryPlot p = chart.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                BarRenderer renderer = (BarRenderer)p.getRenderer();
                CategoryPlot plot = chart.getCategoryPlot();
                CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                ChartFrame frame = new ChartFrame(title, chart);
                frame.setVisible(true);
                frame.setSize(x,y);
            }
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
        lbFilterWk = new javax.swing.JLabel();
        cbFilterPr = new javax.swing.JComboBox();
        lbFilterPr = new javax.swing.JLabel();
        lbFilterEn = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbOrderBy = new javax.swing.JComboBox();
        lbOrderBy = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbGroup = new javax.swing.JCheckBox();
        cbGroupA = new javax.swing.JComboBox();
        cbGroupB = new javax.swing.JComboBox();
        lbGroupB = new javax.swing.JLabel();
        lbGroupA = new javax.swing.JLabel();
        buttonSwitch = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        buttonChartEngineer = new javax.swing.JButton();
        buttonChartProject = new javax.swing.JButton();
        buttonChartWeekEn = new javax.swing.JButton();
        buttonChartWeekPr = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        buttonNext = new javax.swing.JButton();
        buttonNext1 = new javax.swing.JButton();
        buttonNext2 = new javax.swing.JButton();

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
        cbFilterWk.setNextFocusableComponent(cbOrderBy);
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

        lbFilterWk.setText("FilterWeek");

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

        lbFilterPr.setText("FilterProject");

        lbFilterEn.setText("FilterEngineer");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(117, 1, 93));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Filter Table");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(117, 1, 93));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Project");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(117, 1, 93));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Week");

        cbOrderBy.setBackground(new java.awt.Color(220, 200, 255));
        cbOrderBy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbOrderBy.setForeground(new java.awt.Color(117, 1, 93));
        cbOrderBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Order By" }));
        cbOrderBy.setBorder(null);
        cbOrderBy.setMaximumSize(new java.awt.Dimension(101, 26));
        cbOrderBy.setNextFocusableComponent(cbGroupA);
        cbOrderBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOrderByActionPerformed(evt);
            }
        });

        lbOrderBy.setText("OrderBy");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(117, 1, 93));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Order By");

        cbGroup.setBackground(new java.awt.Color(220, 200, 255));
        cbGroup.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbGroup.setForeground(new java.awt.Color(117, 1, 93));
        cbGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGroupActionPerformed(evt);
            }
        });

        cbGroupA.setBackground(new java.awt.Color(220, 200, 255));
        cbGroupA.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbGroupA.setForeground(new java.awt.Color(117, 1, 93));
        cbGroupA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GroupA" }));
        cbGroupA.setBorder(null);
        cbGroupA.setNextFocusableComponent(cbGroupB);
        cbGroupA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGroupAActionPerformed(evt);
            }
        });

        cbGroupB.setBackground(new java.awt.Color(220, 200, 255));
        cbGroupB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbGroupB.setForeground(new java.awt.Color(117, 1, 93));
        cbGroupB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GroupB" }));
        cbGroupB.setBorder(null);
        cbGroupB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGroupBActionPerformed(evt);
            }
        });

        lbGroupB.setText("GroupB");

        lbGroupA.setText("GroupA");

        buttonSwitch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrows.png"))); // NOI18N
        buttonSwitch.setFocusPainted(false);
        buttonSwitch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSwitchActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(117, 1, 93));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Against");
        jLabel15.setMaximumSize(new java.awt.Dimension(70, 22));
        jLabel15.setMinimumSize(new java.awt.Dimension(70, 22));
        jLabel15.setPreferredSize(new java.awt.Dimension(70, 22));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(117, 1, 93));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("View");
        jLabel16.setMaximumSize(new java.awt.Dimension(70, 22));
        jLabel16.setMinimumSize(new java.awt.Dimension(70, 22));
        jLabel16.setPreferredSize(new java.awt.Dimension(70, 22));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(117, 1, 93));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Group ?");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(117, 1, 93));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Engineer");

        buttonChartEngineer.setBackground(new java.awt.Color(220, 200, 255));
        buttonChartEngineer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonChartEngineer.setForeground(new java.awt.Color(117, 1, 93));
        buttonChartEngineer.setText("Engineer / Week");
        buttonChartEngineer.setBorder(null);
        buttonChartEngineer.setNextFocusableComponent(buttonChartProject);
        buttonChartEngineer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChartEngineerActionPerformed(evt);
            }
        });

        buttonChartProject.setBackground(new java.awt.Color(220, 200, 255));
        buttonChartProject.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonChartProject.setForeground(new java.awt.Color(117, 1, 93));
        buttonChartProject.setText("Project / Week");
        buttonChartProject.setBorder(null);
        buttonChartProject.setNextFocusableComponent(buttonChartWeekEn);
        buttonChartProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChartProjectActionPerformed(evt);
            }
        });

        buttonChartWeekEn.setBackground(new java.awt.Color(220, 200, 255));
        buttonChartWeekEn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonChartWeekEn.setForeground(new java.awt.Color(117, 1, 93));
        buttonChartWeekEn.setText("Week / Engineer");
        buttonChartWeekEn.setBorder(null);
        buttonChartWeekEn.setNextFocusableComponent(buttonChartWeekPr);
        buttonChartWeekEn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChartWeekEnActionPerformed(evt);
            }
        });

        buttonChartWeekPr.setBackground(new java.awt.Color(220, 200, 255));
        buttonChartWeekPr.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonChartWeekPr.setForeground(new java.awt.Color(117, 1, 93));
        buttonChartWeekPr.setText("Week / Project");
        buttonChartWeekPr.setBorder(null);
        buttonChartWeekPr.setNextFocusableComponent(cbGroup);
        buttonChartWeekPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChartWeekPrActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(117, 1, 93));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Charts");

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

        buttonNext1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nextSmall.png"))); // NOI18N
        buttonNext1.setFocusPainted(false);
        buttonNext1.setMaximumSize(new java.awt.Dimension(20, 27));
        buttonNext1.setMinimumSize(new java.awt.Dimension(20, 27));
        buttonNext1.setPreferredSize(new java.awt.Dimension(20, 27));
        buttonNext1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNext1ActionPerformed(evt);
            }
        });

        buttonNext2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nextSmall.png"))); // NOI18N
        buttonNext2.setFocusPainted(false);
        buttonNext2.setMaximumSize(new java.awt.Dimension(20, 27));
        buttonNext2.setMinimumSize(new java.awt.Dimension(20, 27));
        buttonNext2.setPreferredSize(new java.awt.Dimension(20, 27));
        buttonNext2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNext2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbFilterEn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(lbFilterPr, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(lbFilterWk, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(lbOrderBy, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lbGroupA)
                        .addGap(64, 64, 64)
                        .addComponent(lbGroupB)
                        .addContainerGap(194, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cbFilterEn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cbFilterPr, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cbFilterWk, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(buttonNext2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cbOrderBy, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(buttonNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(buttonNext1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cbGroupA, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cbGroupB, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonSwitch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 29, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbGroup)
                                        .addGap(108, 108, 108)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(buttonChartWeekEn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(buttonChartProject, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(buttonChartWeekPr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(buttonChartEngineer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel12, jLabel13, jLabel18});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel11)
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonNext2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbFilterEn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel18)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonNext1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbFilterPr, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonNext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbFilterWk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbGroupA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbGroupB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(buttonSwitch, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel20))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addComponent(cbGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonChartEngineer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(cbOrderBy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonChartProject, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonChartWeekEn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonChartWeekPr, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFilterEn)
                    .addComponent(lbFilterPr)
                    .addComponent(lbFilterWk)
                    .addComponent(lbOrderBy)
                    .addComponent(lbGroupA)
                    .addComponent(lbGroupB))
                .addGap(370, 370, 370))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonChartEngineer, cbFilterEn, cbFilterPr, cbFilterWk, cbOrderBy});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbGroup, jLabel17});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbGroupA, cbGroupB});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel13, jLabel14, jLabel18});

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
    
    private void cbOrderByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOrderByActionPerformed
        String Name = (String)cbOrderBy.getSelectedItem();  // create string of selected item in combo box
        lbOrderBy.setText("" + Name);  // set label text to string of selected item
        updateTable();  //  call method to update the table
    }//GEN-LAST:event_cbOrderByActionPerformed
    
    private void cbGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGroupActionPerformed
        updateTable();  //  call method to update the table
        //  settings if the Group box is ticked
        if (cbGroup.isSelected() == true){
            cbGroupA.setVisible(true);  //  show object
            cbGroupB.setVisible(true);  //  show object
            jLabel16.setVisible(true);  //  show object
            jLabel15.setVisible(true);  //  show object
            buttonSwitch.setVisible(true);  //  show object
            cbOrderBy.setVisible(false);  //  hide object
            jLabel14.setVisible(false);  //  hide object
            jLabel11.setText("View");  //  change label text
            //  if assign button is ticked, repeat steps to untick it
        }
        //  settings if the Group box is unticked
        else {
            cbGroupA.setVisible(false);  //  hide object
            cbGroupB.setVisible(false);  //  hide object
            jLabel16.setVisible(false);  //  hide object
            jLabel15.setVisible(false);  //  hide object
            buttonSwitch.setVisible(false);  //  hide object
            cbOrderBy.setVisible(true);  //  show object
            jLabel14.setVisible(true);  //  show object
            jLabel11.setText("Filter Table");  //  change label text
        }
    }//GEN-LAST:event_cbGroupActionPerformed
    
    private void cbGroupAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGroupAActionPerformed
        String Name = (String)cbGroupA.getSelectedItem();  // create string of selected item in combo box
        lbGroupA.setText("" + Name);  // set label text to string of selected item
        updateTable();  //  call method to update the table
    }//GEN-LAST:event_cbGroupAActionPerformed
    
    private void cbGroupBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGroupBActionPerformed
        String Name = (String)cbGroupB.getSelectedItem();  // create string of selected item in combo box
        lbGroupB.setText("" + Name);  // set label text to string of selected item
        updateTable();  //  call method to update the table
    }//GEN-LAST:event_cbGroupBActionPerformed
    
    private void buttonSwitchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSwitchActionPerformed
        Object currentA = cbGroupA.getSelectedItem();  //  get selected item of A
        Object currentB = cbGroupB.getSelectedItem();  //  get selected item of B
        cbGroupA.setSelectedItem(currentB);  //  set selected item of A with B
        cbGroupB.setSelectedItem(currentA);  //  set selected item of B with A
    }//GEN-LAST:event_buttonSwitchActionPerformed
    
    private void tableBookingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBookingsMouseClicked
        
    }//GEN-LAST:event_tableBookingsMouseClicked
    
    private void buttonChartEngineerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChartEngineerActionPerformed
        makeChart("engineer");  //  call method make chart and pass parameter to represent type of graph
    }//GEN-LAST:event_buttonChartEngineerActionPerformed
    
    private void buttonChartProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChartProjectActionPerformed
        makeChart("project");  //  call method make chart and pass parameter to represent type of graph
    }//GEN-LAST:event_buttonChartProjectActionPerformed
    
    private void buttonChartWeekEnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChartWeekEnActionPerformed
        makeChart("weeken");  //  call method make chart and pass parameter to represent type of graph
    }//GEN-LAST:event_buttonChartWeekEnActionPerformed
    
    private void buttonChartWeekPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChartWeekPrActionPerformed
        makeChart("weekpr");  //  call method make chart and pass parameter to represent type of graph
    }//GEN-LAST:event_buttonChartWeekPrActionPerformed
    
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
    
    private void buttonNext1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNext1ActionPerformed
        // TODO add your handling code here:
        int count = cbFilterPr.getItemCount();
        if (cbFilterPr.getSelectedIndex()<count-1){
            int next = (int)cbFilterPr.getSelectedIndex()+1;
            cbFilterPr.setSelectedIndex(next);
            String name = (String)cbFilterPr.getSelectedItem();
            lbFilterPr.setText("" + name);  // set label text to string of selected item
        }
    }//GEN-LAST:event_buttonNext1ActionPerformed
    
    private void buttonNext2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNext2ActionPerformed
        // TODO add your handling code here:
        int count = cbFilterEn.getItemCount();
        if (cbFilterEn.getSelectedIndex()<count-1){
            int next = (int)cbFilterEn.getSelectedIndex()+1;
            cbFilterEn.setSelectedIndex(next);
            String name = (String)cbFilterEn.getSelectedItem();
            lbFilterEn.setText("" + name);  // set label text to string of selected item
        }
    }//GEN-LAST:event_buttonNext2ActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonAdmin;
    private javax.swing.JButton buttonChartEngineer;
    private javax.swing.JButton buttonChartProject;
    private javax.swing.JButton buttonChartWeekEn;
    private javax.swing.JButton buttonChartWeekPr;
    private javax.swing.JButton buttonEditDetails;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JButton buttonNext;
    private javax.swing.JButton buttonNext1;
    private javax.swing.JButton buttonNext2;
    private javax.swing.JButton buttonSwitch;
    private javax.swing.JButton buttonViewEngineers;
    private javax.swing.JButton buttonViewProjects;
    private javax.swing.JButton buttonViewTime;
    private javax.swing.JButton buttonViewWeek;
    private javax.swing.JComboBox cbFilterEn;
    private javax.swing.JComboBox cbFilterPr;
    private javax.swing.JComboBox cbFilterWk;
    private javax.swing.JCheckBox cbGroup;
    private javax.swing.JComboBox cbGroupA;
    private javax.swing.JComboBox cbGroupB;
    private javax.swing.JComboBox cbOrderBy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbFilterEn;
    private javax.swing.JLabel lbFilterPr;
    private javax.swing.JLabel lbFilterWk;
    private javax.swing.JLabel lbGroupA;
    private javax.swing.JLabel lbGroupB;
    private javax.swing.JLabel lbOrderBy;
    private javax.swing.JTable tableBookings;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}

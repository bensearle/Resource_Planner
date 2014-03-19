/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Resource_Planner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author bsearle
 */
public class Charts extends javax.swing.JPanel {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int t = 1;  //  used to detect what graph type is selected
    
    /**
     * Creates new form Charts
     */
    public Charts() {
        initComponents();
        lbChartType.setVisible(false);
        lbChartFilter.setVisible(false);
        lbWeek0.setVisible(false);
        lbWeek1.setVisible(false);
        lbWeek2.setVisible(false);
        lbWeek3.setVisible(false);
        lbWeek4.setVisible(false);
        lbWeek5.setVisible(false);
        lbExtraWeeks.setVisible(false);
        cbChartFilterDuplicate.setVisible(false);
    }
    
    public void updatePage(){
        conn = JavaConnect.ConnectDB();  //  get the connection url
        fillExtraWeeks();
        fillEn();
        fillGraphs();
        cbExtraWeeks.setVisible(false);
        jLabel1.setVisible(false);
    }
    
    /**
     * method to populate combo box with all engineers
     */
    public void fillEn(){
        cbChartFilter.removeAllItems();
        //      cbGraphFilter.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT dn FROM BSUser ORDER BY dn ASC";  // select names from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("dn");  //  create a string for each of the names in the database
                cbChartFilter.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method to populate combo box with all projects
     */
    public void fillPr(){
        cbChartFilter.removeAllItems();
        //     cbGraphFilter.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT pName FROM BSProject WHERE pNumber <> 000 ORDER BY pNumber DESC";  // select projects from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("pName");  //  create a string for each of the projects in the database
                cbChartFilter.addItem(name);  // add the string to the combo box list
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * method to populate combo box with all weeks in the system
     */
    public void fillWk(){
        cbChartFilter.removeAllItems();
        //    cbChartFilterDuplicate.removeAllItems();
        //      cbGraphFilter.addItem("");  // add an empty item to the combo box
        try {
            String sql = "SELECT we FROM BSWeek";  // select weeks from the user table
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("we");  //  create a string for each of the weeks in the database
                cbChartFilter.addItem(name);  // add the string to the combo box list
                cbChartFilterDuplicate.addItem(name);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void limitWk(){
        int count = cbChartFilter.getItemCount();
        if(cbChartFilter.getItemCount() > 0){
            cbChartFilter.removeItemAt(count-1);
            cbChartFilter.removeItemAt(count-2);
            cbChartFilter.removeItemAt(count-3);
            cbChartFilter.removeItemAt(count-4);
            cbChartFilter.removeItemAt(count-5);
            cbChartFilter.removeItemAt(count-6);
        }
    }
    
    public void fillGraphs (){
        cbChartType.removeAllItems();  // remove all items from the combo box
        cbChartType.addItem("Engineer / Week");  //  add item to combo box
        cbChartType.addItem("Project / Week");  //  add item to combo box
        cbChartType.addItem("Week / Engineer");  //  add item to combo box
        cbChartType.addItem("Week / Project");  //  add item to combo box
    }
    
    public void fillExtraWeeks(){
        cbExtraWeeks.removeAllItems();  // remove all items from the combo box
        cbExtraWeeks.addItem("1");  //  add item to combo box
        cbExtraWeeks.addItem("2");  //  add item to combo box
        cbExtraWeeks.addItem("3");  //  add item to combo box
        cbExtraWeeks.addItem("4");  //  add item to combo box
        cbExtraWeeks.addItem("5");  //  add item to combo box
        cbExtraWeeks.addItem("6");  //  add item to combo box
    }
    
    public void futureWeeks(){
        int plus1 = (int)cbChartFilterDuplicate.getSelectedIndex()+1;
        cbChartFilterDuplicate.setSelectedIndex(plus1);
        String name1 = (String)cbChartFilterDuplicate.getSelectedItem();
        lbWeek1.setText("" + name1);  // set label text to string of selected item
        
        int plus2 = (int)cbChartFilterDuplicate.getSelectedIndex()+1;
        cbChartFilterDuplicate.setSelectedIndex(plus2);
        String name2 = (String)cbChartFilterDuplicate.getSelectedItem();
        lbWeek2.setText("" + name2);  // set label text to string of selected item
        
        int plus3 = (int)cbChartFilterDuplicate.getSelectedIndex()+1;
        cbChartFilterDuplicate.setSelectedIndex(plus3);
        String name3 = (String)cbChartFilterDuplicate.getSelectedItem();
        lbWeek3.setText("" + name3);  // set label text to string of selected item
        
        int plus4 = (int)cbChartFilterDuplicate.getSelectedIndex()+1;
        cbChartFilterDuplicate.setSelectedIndex(plus4);
        String name4 = (String)cbChartFilterDuplicate.getSelectedItem();
        lbWeek4.setText("" + name4);  // set label text to string of selected item
        
        int plus5 = (int)cbChartFilterDuplicate.getSelectedIndex()+1;
        cbChartFilterDuplicate.setSelectedIndex(plus5);
        String name5 = (String)cbChartFilterDuplicate.getSelectedItem();
        lbWeek5.setText("" + name5);  // set label text to string of selected item
        
        int plus0 = (int)cbChartFilterDuplicate.getSelectedIndex()-5;
        cbChartFilterDuplicate.setSelectedIndex(plus0);
        String name0 = (String)cbChartFilterDuplicate.getSelectedItem();
        lbWeek0.setText("" + name0);  // set label text to string of selected item
        
        
        
    }
    
    public void pickChart(){
        jPanel.removeAll();
        int prev = (int)cbChartFilter.getSelectedIndex();
        cbChartFilter.setSelectedIndex(prev);
        String name = (String)cbChartFilter.getSelectedItem();
        lbChartFilter.setText("" + name);  // set label text to string of selected item
        
        
        switch (lbChartType.getText()) {
            case "Engineer / Week":
                makeChart("engineer");
                break;
            case "Project / Week":
                makeChart("project");
                break;
            case "Week / Engineer":
                String sWE = lbChartFilter.getText();
                cbChartFilterDuplicate.setSelectedItem(sWE);
                switch (lbExtraWeeks.getText()){
                    case "1":
                        makeChart("weeken1");
                        break;
                    case "2":
                        futureWeeks();
                        makeChart("weeken2");
                        break;
                    case "3":
                        futureWeeks();
                        makeChart("weeken3");
                        break;
                    case "4":
                        futureWeeks();
                        makeChart("weeken4");
                        break;
                    case "5":
                        futureWeeks();
                        makeChart("weeken5");
                        break;
                    case "6":
                        futureWeeks();
                        makeChart("weeken6");
                        break;
                }
                
                break;
            case "Week / Project":
                String sWP = lbChartFilter.getText();
                cbChartFilterDuplicate.setSelectedItem(sWP);
                switch (lbExtraWeeks.getText()){
                    case "1":
                        makeChart("weekpr1");
                        break;
                    case "2":
                        futureWeeks();
                        makeChart("weekpr2");
                        break;
                    case "3":
                        futureWeeks();
                        makeChart("weekpr3");
                        break;
                    case "4":
                        futureWeeks();
                        makeChart("weekpr4");
                        break;
                    case "5":
                        futureWeeks();
                        makeChart("weekpr5");
                        break;
                    case "6":
                        futureWeeks();
                        makeChart("weekpr6");
                        break;
                }
                break;
        }
        
        
    }
    
    /**
     * method to make a chart based on parameter passed
     */
    public void makeChart(String button){
        String bEn = lbChartFilter.getText();  //  create string from label text
        String bPr = lbChartFilter.getText();  //  create string from label text
        String bWk = lbChartFilter.getText();  //  create string from label text
        String bWk1 = lbWeek0.getText();  //  create string from label text
        String bWk2 = lbWeek1.getText();  //  create string from label text
        String bWk3 = lbWeek2.getText();  //  create string from label text
        String bWk4 = lbWeek3.getText();  //  create string from label text
        String bWk5 = lbWeek4.getText();  //  create string from label text
        String bWk6 = lbWeek5.getText();  //  create string from label text
        int x = 850;  // set x axis for JFrame that the chart appears
        int xHalf = 425;  // set x axis for JFrame that the chart appears
        int y = 520;  // set y axis for JFrame that the chart appears
        try {
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
                chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                BarRenderer renderer = (BarRenderer)p.getRenderer();
                CategoryPlot plot = chart.getCategoryPlot();
                CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
                panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
                ValueMarker marker = new ValueMarker(5);  //  create marker to show the maximum utilisatio
                marker.setLabel("Maximum Utilisation");  //  create marker to show the maximum utilisatio
                marker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
                marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                marker.setPaint(new Color (117, 1, 93));  //  set colour
                p.addRangeMarker(marker);
            }
            else if (button.equals("project")){
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                String en;
                Double dy;
                String wk;
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bDate AS wk, bEngineer AS en, bDays AS dy FROM BSBooking WHERE bProject = '"+bPr+"' ORDER BY bDate ASC, bEngineer ASC");
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
                chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                BarRenderer renderer = (BarRenderer)p.getRenderer();
                CategoryPlot plot = chart.getCategoryPlot();
                CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
                panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
            }
            //  shows chart for week against engineer
            else if (button.equals("weeken1")){
                DefaultCategoryDataset datasetL = new DefaultCategoryDataset();
                DefaultCategoryDataset datasetR = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                String middleEng = null;
                // get the middle engineer
                try {
                    PreparedStatement ps = conn.prepareStatement(
                            "SELECT TOP 1 bEngineer FROM (SELECT DISTINCT TOP 50 PERCENT bEngineer FROM BSBooking "
                            + "ORDER BY bEngineer ASC) AS middleEng ORDER BY 1 DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        middleEng = rs.getString("bEngineer");
                    }
                }catch (Exception e){
                     //  JOptionPane.showMessageDialog(null, e);
                }
                //  get the data set for the first group of engineers
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, bDays AS dy FROM BSBooking WHERE bDate = '"+bWk+"' AND bEngineer <= '"+middleEng+"' ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetL.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    //    JOptionPane.showMessageDialog(null, e);
                }
                //  get the dataset for the second group of engineers
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, bDays AS dy FROM BSBooking WHERE bDate = '"+bWk+"' AND bEngineer > '"+middleEng+"' ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetR.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    //    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chartL = ChartFactory.createStackedBarChart(bWk, "Engineer", "Days Booked", datasetL, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
                JFreeChart chartR = ChartFactory.createStackedBarChart(bWk, "Engineer", "Days Booked", datasetR, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
                CategoryPlot pL = chartL.getCategoryPlot();  //  make chart
                CategoryPlot pR = chartR.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chartL.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                chartR.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                ChartPanel panelL = new ChartPanel(chartL); //creating the chart panel, which extends JPanel
                ChartPanel panelR = new ChartPanel(chartR); //creating the chart panel, which extends JPanel
                panelL.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                panelR.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panelL, BorderLayout.WEST);  //  add the chart to the jPanel
                jPanel.add(panelR, BorderLayout.EAST);  //  add the chart to the jPanel
                ValueMarker marker = new ValueMarker(5);  //  create marker to show the maximum utilisatio
                marker.setLabel("Maximum Utilisation");  //  create marker to show the maximum utilisatio
                marker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
                marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                marker.setPaint(new Color (117, 1, 93));  //  set colour
                pL.addRangeMarker(marker);
                pR.addRangeMarker(marker);
            }
            else if (button.equals("weeken2")){
                DefaultCategoryDataset datasetL = new DefaultCategoryDataset();
                DefaultCategoryDataset datasetR = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                String middleEng = null;
                try {
                    PreparedStatement ps = conn.prepareStatement(
                            "SELECT TOP 1 bEngineer FROM (SELECT DISTINCT TOP 50 PERCENT bEngineer FROM BSBooking "
                            + "ORDER BY bEngineer ASC) AS middleEng ORDER BY 1 DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        middleEng = rs.getString("bEngineer");
                    }
                }catch (Exception e){
                     //  JOptionPane.showMessageDialog(null, e);
                }
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, SUM(bDays) AS dy FROM BSBooking WHERE (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"') AND bEngineer <= '"+middleEng+"' GROUP BY bProject, bEngineer ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetL.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, SUM(bDays) AS dy FROM BSBooking WHERE (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"') AND bEngineer > '"+middleEng+"' GROUP BY bProject, bEngineer ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetR.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chartL = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk2+"", "Engineer", "Days Booked", datasetL, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
                JFreeChart chartR = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk2+"", "Engineer", "Days Booked", datasetR, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
                
                CategoryPlot pL = chartL.getCategoryPlot();  //  make chart
                CategoryPlot pR = chartR.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chartL.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                chartR.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                ChartPanel panelL = new ChartPanel(chartL); //creating the chart panel, which extends JPanel
                ChartPanel panelR = new ChartPanel(chartR); //creating the chart panel, which extends JPanel
                panelL.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                panelR.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panelL, BorderLayout.WEST);  //  add the chart to the jPanel
                jPanel.add(panelR, BorderLayout.EAST);  //  add the chart to the jPanel
                ValueMarker marker = new ValueMarker(10);  //  create marker to show the maximum utilisatio
                marker.setLabel("Maximum Utilisation");  //  create marker to show the maximum utilisatio
                marker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
                marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                marker.setPaint(new Color (117, 1, 93));  //  set colour
                pL.addRangeMarker(marker);
                pR.addRangeMarker(marker);            
            }
            else if (button.equals("weeken3")){
DefaultCategoryDataset datasetL = new DefaultCategoryDataset();
                DefaultCategoryDataset datasetR = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                String middleEng = null;
                try {
                    PreparedStatement ps = conn.prepareStatement(
                            "SELECT TOP 1 bEngineer FROM (SELECT DISTINCT TOP 50 PERCENT bEngineer FROM BSBooking "
                            + "ORDER BY bEngineer ASC) AS middleEng ORDER BY 1 DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        middleEng = rs.getString("bEngineer");
                    }
                }catch (Exception e){
                     //  JOptionPane.showMessageDialog(null, e);
                }
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, SUM(bDays) AS dy FROM BSBooking WHERE (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"') AND bEngineer <= '"+middleEng+"' GROUP BY bProject, bEngineer ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetL.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, SUM(bDays) AS dy FROM BSBooking WHERE (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"') AND bEngineer > '"+middleEng+"' GROUP BY bProject, bEngineer ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetR.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chartL = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk3+"", "Engineer", "Days Booked", datasetL, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
                 JFreeChart chartR = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk3+"", "Engineer", "Days Booked", datasetR, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
               
                CategoryPlot pL = chartL.getCategoryPlot();  //  make chart
                CategoryPlot pR = chartR.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chartL.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                chartR.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                ChartPanel panelL = new ChartPanel(chartL); //creating the chart panel, which extends JPanel
                ChartPanel panelR = new ChartPanel(chartR); //creating the chart panel, which extends JPanel
                panelL.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                panelR.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panelL, BorderLayout.WEST);  //  add the chart to the jPanel
                jPanel.add(panelR, BorderLayout.EAST);  //  add the chart to the jPanel
                ValueMarker marker = new ValueMarker(15);  //  create marker to show the maximum utilisatio
                marker.setLabel("Maximum Utilisation");  //  create marker to show the maximum utilisatio
                marker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
                marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                marker.setPaint(new Color (117, 1, 93));  //  set colour
                pL.addRangeMarker(marker);
                pR.addRangeMarker(marker);
            }
            else if (button.equals("weeken4")){
                DefaultCategoryDataset datasetL = new DefaultCategoryDataset();
                DefaultCategoryDataset datasetR = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                String middleEng = null;
                try {
                    PreparedStatement ps = conn.prepareStatement(
                            "SELECT TOP 1 bEngineer FROM (SELECT DISTINCT TOP 50 PERCENT bEngineer FROM BSBooking "
                            + "ORDER BY bEngineer ASC) AS middleEng ORDER BY 1 DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        middleEng = rs.getString("bEngineer");
                    }
                }catch (Exception e){
                     //  JOptionPane.showMessageDialog(null, e);
                }
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, SUM(bDays) AS dy FROM BSBooking WHERE (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"') AND bEngineer <= '"+middleEng+"' GROUP BY bProject, bEngineer ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetL.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, SUM(bDays) AS dy FROM BSBooking WHERE (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"') AND bEngineer > '"+middleEng+"' GROUP BY bProject, bEngineer ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetR.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chartL = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk4+"", "Engineer", "Days Booked", datasetL, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
                JFreeChart chartR = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk4+"", "Engineer", "Days Booked", datasetR, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
               
                CategoryPlot pL = chartL.getCategoryPlot();  //  make chart
                CategoryPlot pR = chartR.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chartL.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                chartR.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                ChartPanel panelL = new ChartPanel(chartL); //creating the chart panel, which extends JPanel
                ChartPanel panelR = new ChartPanel(chartR); //creating the chart panel, which extends JPanel
                panelL.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                panelR.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panelL, BorderLayout.WEST);  //  add the chart to the jPanel
                jPanel.add(panelR, BorderLayout.EAST);  //  add the chart to the jPanel
                ValueMarker marker = new ValueMarker(20);  //  create marker to show the maximum utilisatio
                marker.setLabel("Maximum Utilisation");  //  create marker to show the maximum utilisatio
                marker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
                marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                marker.setPaint(new Color (117, 1, 93));  //  set colour
                pL.addRangeMarker(marker);
                pR.addRangeMarker(marker);
            }
            else if (button.equals("weeken5")){
                DefaultCategoryDataset datasetL = new DefaultCategoryDataset();
                DefaultCategoryDataset datasetR = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                String middleEng = null;
                try {
                    PreparedStatement ps = conn.prepareStatement(
                            "SELECT TOP 1 bEngineer FROM (SELECT DISTINCT TOP 50 PERCENT bEngineer FROM BSBooking "
                            + "ORDER BY bEngineer ASC) AS middleEng ORDER BY 1 DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        middleEng = rs.getString("bEngineer");
                    }
                }catch (Exception e){
                     //  JOptionPane.showMessageDialog(null, e);
                }
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, SUM(bDays) AS dy FROM BSBooking WHERE (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"' OR bDate = '"+bWk5+"') AND bEngineer <= '"+middleEng+"' GROUP BY bProject, bEngineer ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetL.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, SUM(bDays) AS dy FROM BSBooking WHERE (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"' OR bDate = '"+bWk5+"') AND bEngineer > '"+middleEng+"' GROUP BY bProject, bEngineer ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetR.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chartL = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk5+"", "Engineer", "Days Booked", datasetL, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
                JFreeChart chartR = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk5+"", "Engineer", "Days Booked", datasetR, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
                
                CategoryPlot pL = chartL.getCategoryPlot();  //  make chart
                CategoryPlot pR = chartR.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chartL.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                chartR.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                ChartPanel panelL = new ChartPanel(chartL); //creating the chart panel, which extends JPanel
                ChartPanel panelR = new ChartPanel(chartR); //creating the chart panel, which extends JPanel
                panelL.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                panelR.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panelL, BorderLayout.WEST);  //  add the chart to the jPanel
                jPanel.add(panelR, BorderLayout.EAST);  //  add the chart to the jPanel
                ValueMarker marker = new ValueMarker(25);  //  create marker to show the maximum utilisatio
                marker.setLabel("Maximum Utilisation");  //  create marker to show the maximum utilisatio
                marker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
                marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                marker.setPaint(new Color (117, 1, 93));  //  set colour
                pL.addRangeMarker(marker);
                pR.addRangeMarker(marker);
            }
            else if (button.equals("weeken6")){
               DefaultCategoryDataset datasetL = new DefaultCategoryDataset();
                DefaultCategoryDataset datasetR = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                String middleEng = null;
                try {
                    PreparedStatement ps = conn.prepareStatement(
                            "SELECT TOP 1 bEngineer FROM (SELECT DISTINCT TOP 50 PERCENT bEngineer FROM BSBooking "
                            + "ORDER BY bEngineer ASC) AS middleEng ORDER BY 1 DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        middleEng = rs.getString("bEngineer");
                    }
                }catch (Exception e){
                     //  JOptionPane.showMessageDialog(null, e);
                }
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, SUM(bDays) AS dy FROM BSBooking WHERE (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"' OR bDate = '"+bWk5+"' OR bDate = '"+bWk6+"') AND bEngineer <= '"+middleEng+"' GROUP BY bProject, bEngineer ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetL.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, SUM(bDays) AS dy FROM BSBooking WHERE (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"' OR bDate = '"+bWk5+"' OR bDate = '"+bWk6+"') AND bEngineer > '"+middleEng+"' GROUP BY bProject, bEngineer ORDER BY bEngineer ASC, bProject ASC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("dy");
                        en = rs.getString("en");
                        pr = rs.getString("pr");
                        datasetR.setValue(dy, pr, en);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chartL = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk6+"", "Engineer", "Days Booked", datasetL, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
                 JFreeChart chartR = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk6+"", "Engineer", "Days Booked", datasetR, PlotOrientation.HORIZONTAL, false, true, true);  //  set title, axis names, look and feel of chart
               
                CategoryPlot pL = chartL.getCategoryPlot();  //  make chart
                CategoryPlot pR = chartR.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chartL.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartL.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                chartR.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chartR.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                ChartPanel panelL = new ChartPanel(chartL); //creating the chart panel, which extends JPanel
                ChartPanel panelR = new ChartPanel(chartR); //creating the chart panel, which extends JPanel
                panelL.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                panelR.setPreferredSize(new Dimension(xHalf, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panelL, BorderLayout.WEST);  //  add the chart to the jPanel
                jPanel.add(panelR, BorderLayout.EAST);  //  add the chart to the jPanel
                ValueMarker marker = new ValueMarker(30);  //  create marker to show the maximum utilisatio
                marker.setLabel("Maximum Utilisation");  //  create marker to show the maximum utilisatio
                marker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
                marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                marker.setPaint(new Color (117, 1, 93));  //  set colour
                pL.addRangeMarker(marker);
                pR.addRangeMarker(marker);
            }
            
            
            else if (button.equals("weekpr1")){
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
                ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
                panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
            }
            
            else if (button.equals("weekpr2")){
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                try {
                    //          PreparedStatement ps = conn.prepareStatement("SELECT bEngineer AS en, bProject AS pr, SUM(bDays) AS dy FROM BSBooking WHERE bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' GROUP BY bProject, bEngineer ORDER BY SUM(bDays) ASC");
                    PreparedStatement ps = conn.prepareStatement("SELECT bProject, bEngineer, sumPrIndiv, sumPrTot FROM\n" +
                            "(SELECT bProject, bEngineer, SUM(bDays) AS sumPrIndiv FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"') GROUP by bProject, bEngineer) AS one\n" +
                            "INNER JOIN ( SELECT bProject AS pr2, SUM(bDays) AS sumPrTot FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"') GROUP by bProject) AS two\n" +
                            "ON one.bProject = two.pr2 ORDER BY sumPrTot DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("sumPrIndiv");
                        en = rs.getString("bEngineer");
                        pr = rs.getString("bProject");
                        dataset.setValue(dy, en, pr);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chart = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk2+"", "Project", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
                CategoryPlot p = chart.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                BarRenderer renderer = (BarRenderer)p.getRenderer();
                CategoryPlot plot = chart.getCategoryPlot();
                CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
                panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
            }
            else if (button.equals("weekpr3")){
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bProject, bEngineer, sumPrIndiv, sumPrTot FROM\n" +
                            "(SELECT bProject, bEngineer, SUM(bDays) AS sumPrIndiv FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"') GROUP by bProject, bEngineer) AS one\n" +
                            "INNER JOIN ( SELECT bProject AS pr2, SUM(bDays) AS sumPrTot FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"') GROUP by bProject) AS two\n" +
                            "ON one.bProject = two.pr2 ORDER BY sumPrTot DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("sumPrIndiv");
                        en = rs.getString("bEngineer");
                        pr = rs.getString("bProject");
                        dataset.setValue(dy, en, pr);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chart = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk3+"", "Project", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
                CategoryPlot p = chart.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                BarRenderer renderer = (BarRenderer)p.getRenderer();
                CategoryPlot plot = chart.getCategoryPlot();
                CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
                panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
            }
            else if (button.equals("weekpr4")){
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bProject, bEngineer, sumPrIndiv, sumPrTot FROM\n" +
                            "(SELECT bProject, bEngineer, SUM(bDays) AS sumPrIndiv FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"') GROUP by bProject, bEngineer) AS one\n" +
                            "INNER JOIN ( SELECT bProject AS pr2, SUM(bDays) AS sumPrTot FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"') GROUP by bProject) AS two\n" +
                            "ON one.bProject = two.pr2 ORDER BY sumPrTot DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("sumPrIndiv");
                        en = rs.getString("bEngineer");
                        pr = rs.getString("bProject");
                        dataset.setValue(dy, en, pr);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chart = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk4+"", "Project", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
                CategoryPlot p = chart.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                BarRenderer renderer = (BarRenderer)p.getRenderer();
                CategoryPlot plot = chart.getCategoryPlot();
                CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
                panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
            }
            else if (button.equals("weekpr5")){
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bProject, bEngineer, sumPrIndiv, sumPrTot FROM\n" +
                            "(SELECT bProject, bEngineer, SUM(bDays) AS sumPrIndiv FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"' OR bDate = '"+bWk5+"') GROUP by bProject, bEngineer) AS one\n" +
                            "INNER JOIN ( SELECT bProject AS pr2, SUM(bDays) AS sumPrTot FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"' OR bDate = '"+bWk5+"') GROUP by bProject) AS two\n" +
                            "ON one.bProject = two.pr2 ORDER BY sumPrTot DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("sumPrIndiv");
                        en = rs.getString("bEngineer");
                        pr = rs.getString("bProject");
                        dataset.setValue(dy, en, pr);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chart = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk5+"", "Project", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
                CategoryPlot p = chart.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                BarRenderer renderer = (BarRenderer)p.getRenderer();
                CategoryPlot plot = chart.getCategoryPlot();
                CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
                panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
            }
            else if (button.equals("weekpr6")){
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                String pr;
                Double dy;
                String en;
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT bProject, bEngineer, sumPrIndiv, sumPrTot FROM\n" +
                            "(SELECT bProject, bEngineer, SUM(bDays) AS sumPrIndiv FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"' OR bDate = '"+bWk5+"' OR bDate = '"+bWk6+"') GROUP by bProject, bEngineer) AS one\n" +
                            "INNER JOIN ( SELECT bProject AS pr2, SUM(bDays) AS sumPrTot FROM BSBooking WHERE bDays <> 0 AND (bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"' OR bDate = '"+bWk5+"' OR bDate = '"+bWk6+"') GROUP by bProject) AS two\n" +
                            "ON one.bProject = two.pr2 ORDER BY sumPrTot DESC");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dy = rs.getDouble("sumPrIndiv");
                        en = rs.getString("bEngineer");
                        pr = rs.getString("bProject");
                        dataset.setValue(dy, en, pr);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JFreeChart chart = ChartFactory.createStackedBarChart(""+bWk1+" to "+bWk6+"", "Project", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
                CategoryPlot p = chart.getCategoryPlot();  //  make chart
                //  set look and feel settings
                chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
                chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
                BarRenderer renderer = (BarRenderer)p.getRenderer();
                CategoryPlot plot = chart.getCategoryPlot();
                CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
                xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
                panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
                jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
                jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
            }
            
            
            
            
            
            
            /*
             * //  shows chart for week against project
             * else if (button.equals("weekpr5")){
             * String sql = "SELECT bProject, SUM(bDays) FROM BSBooking WHERE bDate = '"+bWk0+"' OR bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"' OR bDate = '"+bWk5+"' GROUP BY bProject ORDER BY SUM(bDays) DESC";  //  get data to populate chart
             * JDBCCategoryDataset dataset = new JDBCCategoryDataset(conn, sql);
             * JFreeChart chart = ChartFactory.createBarChart(""+bWk0+" to "+bWk5+"", "Engineer", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
             * CategoryPlot p = chart.getCategoryPlot();  //  make chart
             * //  set look and feel settings
             * chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
             * chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
             * chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
             * BarRenderer renderer = (BarRenderer)p.getRenderer();
             * CategoryPlot plot = chart.getCategoryPlot();
             * CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
             * xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
             * ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
             * panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
             * jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
             * jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
             * }
             * else if (button.equals("weekpr4")){
             * String sql = "SELECT bProject, SUM(bDays) FROM BSBooking WHERE bDate = '"+bWk0+"' OR bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' OR bDate = '"+bWk4+"' GROUP BY bProject ORDER BY SUM(bDays) DESC";  //  get data to populate chart
             * JDBCCategoryDataset dataset = new JDBCCategoryDataset(conn, sql);
             * JFreeChart chart = ChartFactory.createBarChart(""+bWk0+" to "+bWk4+"", "Engineer", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
             * CategoryPlot p = chart.getCategoryPlot();  //  make chart
             * //  set look and feel settings
             * chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
             * chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
             * chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
             * BarRenderer renderer = (BarRenderer)p.getRenderer();
             * CategoryPlot plot = chart.getCategoryPlot();
             * CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
             * xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
             * ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
             * panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
             * jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
             * jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
             * }
             * else if (button.equals("weekpr3")){
             * String sql = "SELECT bProject, SUM(bDays) FROM BSBooking WHERE bDate = '"+bWk0+"' OR bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' OR bDate = '"+bWk3+"' GROUP BY bProject ORDER BY SUM(bDays) DESC";  //  get data to populate chart
             * JDBCCategoryDataset dataset = new JDBCCategoryDataset(conn, sql);
             * JFreeChart chart = ChartFactory.createBarChart(""+bWk0+" to "+bWk3+"", "Engineer", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
             * CategoryPlot p = chart.getCategoryPlot();  //  make chart
             * //  set look and feel settings
             * chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
             * chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
             * chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
             * BarRenderer renderer = (BarRenderer)p.getRenderer();
             * CategoryPlot plot = chart.getCategoryPlot();
             * CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
             * xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
             * ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
             * panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
             * jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
             * jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
             * }
             * else if (button.equals("weekpr2")){
             * String sql = "SELECT bProject, SUM(bDays) FROM BSBooking WHERE bDate = '"+bWk0+"' OR bDate = '"+bWk1+"' OR bDate = '"+bWk2+"' GROUP BY bProject ORDER BY SUM(bDays) DESC";  //  get data to populate chart
             * JDBCCategoryDataset dataset = new JDBCCategoryDataset(conn, sql);
             * JFreeChart chart = ChartFactory.createBarChart(""+bWk0+" to "+bWk2+"", "Engineer", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
             * CategoryPlot p = chart.getCategoryPlot();  //  make chart
             * //  set look and feel settings
             * chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
             * chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
             * chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
             * BarRenderer renderer = (BarRenderer)p.getRenderer();
             * CategoryPlot plot = chart.getCategoryPlot();
             * CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
             * xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
             * ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
             * panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
             * jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
             * jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
             * }
             * else if (button.equals("weekpr1")){
             * String sql = "SELECT bProject, SUM(bDays) FROM BSBooking WHERE bDate = '"+bWk0+"' OR bDate = '"+bWk1+"' GROUP BY bProject ORDER BY SUM(bDays) DESC";  //  get data to populate chart
             * JDBCCategoryDataset dataset = new JDBCCategoryDataset(conn, sql);
             * JFreeChart chart = ChartFactory.createBarChart(""+bWk0+" to "+bWk1+"", "Engineer", "Days Booked", dataset, PlotOrientation.VERTICAL, false, true, true);  //  set title, axis names, look and feel of chart
             * CategoryPlot p = chart.getCategoryPlot();  //  make chart
             * //  set look and feel settings
             * chart.setBackgroundPaint(new Color (220, 200, 255));  //  set colour
             * chart.getPlot().setBackgroundPaint(new Color (220, 200, 255));  //  set colour
             * chart.getPlot().setOutlinePaint(new Color (220, 200, 255));  //  set colour
             * BarRenderer renderer = (BarRenderer)p.getRenderer();
             * CategoryPlot plot = chart.getCategoryPlot();
             * CategoryAxis xAxis =(CategoryAxis)plot.getDomainAxis();
             * xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
             * ChartPanel panel1 = new ChartPanel(chart); //creating the chart panel, which extends JPanel
             * panel1.setPreferredSize(new Dimension(x, y)); //size according to my window
             * jPanel.setLayout(new BorderLayout());  //  set layout type of jPanel;
             * jPanel.add(panel1, BorderLayout.NORTH);  //  add the chart to the jPanel
             * }*/
        }catch (Exception e){
            // JOptionPane.showMessageDialog(null, e);
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
        java.awt.GridBagConstraints gridBagConstraints;

        lbChartFilter = new javax.swing.JLabel();
        cbChartFilter = new javax.swing.JComboBox();
        cbChartType = new javax.swing.JComboBox();
        lbChartType = new javax.swing.JLabel();
        jPanel = new javax.swing.JPanel();
        buttonNext = new javax.swing.JButton();
        buttonPrevious = new javax.swing.JButton();
        lbWeek1 = new javax.swing.JLabel();
        lbWeek2 = new javax.swing.JLabel();
        lbWeek4 = new javax.swing.JLabel();
        lbWeek5 = new javax.swing.JLabel();
        lbWeek3 = new javax.swing.JLabel();
        lbWeek0 = new javax.swing.JLabel();
        lbExtraWeeks = new javax.swing.JLabel();
        cbChartFilterDuplicate = new javax.swing.JComboBox();
        spacing1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbExtraWeeks = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(220, 200, 255));
        setForeground(new java.awt.Color(220, 200, 255));
        setMaximumSize(new java.awt.Dimension(850, 600));
        setMinimumSize(new java.awt.Dimension(850, 600));
        setPreferredSize(new java.awt.Dimension(850, 600));
        setLayout(new java.awt.GridBagLayout());

        lbChartFilter.setText("lbChartFilter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lbChartFilter, gridBagConstraints);

        cbChartFilter.setBackground(new java.awt.Color(220, 200, 255));
        cbChartFilter.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbChartFilter.setForeground(new java.awt.Color(117, 1, 93));
        cbChartFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbChartFilter.setBorder(null);
        cbChartFilter.setMaximumSize(new java.awt.Dimension(190, 30));
        cbChartFilter.setMinimumSize(new java.awt.Dimension(190, 30));
        cbChartFilter.setName(""); // NOI18N
        cbChartFilter.setPreferredSize(new java.awt.Dimension(190, 30));
        cbChartFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbChartFilterActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(cbChartFilter, gridBagConstraints);

        cbChartType.setBackground(new java.awt.Color(220, 200, 255));
        cbChartType.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbChartType.setForeground(new java.awt.Color(117, 1, 93));
        cbChartType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbChartType.setBorder(null);
        cbChartType.setLightWeightPopupEnabled(false);
        cbChartType.setMaximumSize(new java.awt.Dimension(170, 30));
        cbChartType.setMinimumSize(new java.awt.Dimension(170, 30));
        cbChartType.setPreferredSize(new java.awt.Dimension(170, 30));
        cbChartType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbChartTypeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        add(cbChartType, gridBagConstraints);

        lbChartType.setText("lbChartType");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lbChartType, gridBagConstraints);

        jPanel.setMaximumSize(new java.awt.Dimension(850, 520));
        jPanel.setMinimumSize(new java.awt.Dimension(850, 520));
        jPanel.setPreferredSize(new java.awt.Dimension(850, 520));

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jPanel, gridBagConstraints);

        buttonNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/next.png"))); // NOI18N
        buttonNext.setFocusPainted(false);
        buttonNext.setMaximumSize(new java.awt.Dimension(32, 27));
        buttonNext.setMinimumSize(new java.awt.Dimension(32, 27));
        buttonNext.setPreferredSize(new java.awt.Dimension(32, 27));
        buttonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(buttonNext, gridBagConstraints);

        buttonPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/previous.png"))); // NOI18N
        buttonPrevious.setFocusPainted(false);
        buttonPrevious.setMaximumSize(new java.awt.Dimension(32, 27));
        buttonPrevious.setMinimumSize(new java.awt.Dimension(32, 27));
        buttonPrevious.setPreferredSize(new java.awt.Dimension(32, 27));
        buttonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPreviousActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        add(buttonPrevious, gridBagConstraints);

        lbWeek1.setText("lbWeek1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lbWeek1, gridBagConstraints);

        lbWeek2.setText("lbWeek2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lbWeek2, gridBagConstraints);

        lbWeek4.setText("lbWeek4");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lbWeek4, gridBagConstraints);

        lbWeek5.setText("lbWeek5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lbWeek5, gridBagConstraints);

        lbWeek3.setText("lbWeek3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lbWeek3, gridBagConstraints);

        lbWeek0.setText("lbWeek0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lbWeek0, gridBagConstraints);

        lbExtraWeeks.setText("lbExtraWeeks");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lbExtraWeeks, gridBagConstraints);

        cbChartFilterDuplicate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(cbChartFilterDuplicate, gridBagConstraints);

        spacing1.setBackground(new java.awt.Color(220, 200, 255));
        spacing1.setForeground(new java.awt.Color(220, 200, 255));
        spacing1.setMaximumSize(new java.awt.Dimension(425, 10));
        spacing1.setMinimumSize(new java.awt.Dimension(425, 10));
        spacing1.setPreferredSize(new java.awt.Dimension(425, 10));

        javax.swing.GroupLayout spacing1Layout = new javax.swing.GroupLayout(spacing1);
        spacing1.setLayout(spacing1Layout);
        spacing1Layout.setHorizontalGroup(
            spacing1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
        );
        spacing1Layout.setVerticalGroup(
            spacing1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        add(spacing1, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(220, 200, 255));
        jPanel1.setForeground(new java.awt.Color(220, 200, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(210, 30));
        jPanel1.setMinimumSize(new java.awt.Dimension(210, 30));
        jPanel1.setPreferredSize(new java.awt.Dimension(210, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(117, 1, 93));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Weeks to show -");

        cbExtraWeeks.setBackground(new java.awt.Color(220, 200, 255));
        cbExtraWeeks.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbExtraWeeks.setForeground(new java.awt.Color(117, 1, 93));
        cbExtraWeeks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbExtraWeeks.setBorder(null);
        cbExtraWeeks.setLightWeightPopupEnabled(false);
        cbExtraWeeks.setMaximumSize(new java.awt.Dimension(50, 30));
        cbExtraWeeks.setMinimumSize(new java.awt.Dimension(50, 30));
        cbExtraWeeks.setPreferredSize(new java.awt.Dimension(50, 30));
        cbExtraWeeks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbExtraWeeksActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbExtraWeeks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbExtraWeeks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    
    private void cbChartFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbChartFilterActionPerformed
        // TODO add your handling code here:
        String name = (String)cbChartFilter.getSelectedItem();  // create string of selected item in combo box
        lbChartFilter.setText("" + name);  // set label text to string of selected item
        pickChart();
    }//GEN-LAST:event_cbChartFilterActionPerformed
    
    private void cbChartTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbChartTypeActionPerformed
        // TODO add your handling code here:
        String name = (String)cbChartType.getSelectedItem();  // create string of selected item in combo box
        lbChartType.setText("" + name);  // set label text to string of selected item
        
        switch (lbChartType.getText()) {
            case "Engineer / Week":
                
                if (t == 2){
                    cbExtraWeeks.setVisible(false);
                    jLabel1.setVisible(false);
                }
                fillEn();
                t = 1;
                break;
            case "Project / Week":
                
                if (t == 2){
                    cbExtraWeeks.setVisible(false);
                    jLabel1.setVisible(false);
                }
                fillPr();
                t = 1;
                break;
            case "Week / Engineer":
                if (t == 1){
                    cbExtraWeeks.setSelectedIndex(0);
                    fillWk();
                    limitWk();
                    cbExtraWeeks.setVisible(true);
                    jLabel1.setVisible(true);
                    t = 2;
                }
                else {
                    pickChart();
                }
                break;
            case "Week / Project":
                if (t == 1){
                    cbExtraWeeks.setSelectedIndex(0);
                    fillWk();
                    limitWk();
                    cbExtraWeeks.setVisible(true);
                    jLabel1.setVisible(true);
                    t = 2;
                }
                else {
                    pickChart();
                }
                break;
        }
    }//GEN-LAST:event_cbChartTypeActionPerformed
    
    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        // TODO add your handling code here:
        int count = cbChartFilter.getItemCount();
        if (cbChartFilter.getSelectedIndex()<count-1){
            int next = (int)cbChartFilter.getSelectedIndex()+1;
            cbChartFilter.setSelectedIndex(next);
            String name = (String)cbChartFilter.getSelectedItem();
            lbChartFilter.setText("" + name);  // set label text to string of selected item
            pickChart();
        }
        
    }//GEN-LAST:event_buttonNextActionPerformed
    
    private void buttonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPreviousActionPerformed
        // TODO add your handling code here:
        if (cbChartFilter.getSelectedIndex()>0){
            int prev = (int)cbChartFilter.getSelectedIndex()-1;
            cbChartFilter.setSelectedIndex(prev);
            String name = (String)cbChartFilter.getSelectedItem();
            lbChartFilter.setText("" + name);  // set label text to string of selected item
            pickChart();
        }
    }//GEN-LAST:event_buttonPreviousActionPerformed
    
    private void cbExtraWeeksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbExtraWeeksActionPerformed
        // TODO add your handling code here:
        String name = (String)cbExtraWeeks.getSelectedItem();  // create string of selected item in combo box
        lbExtraWeeks.setText("" + name);  // set label text to string of selected item
        pickChart();
    }//GEN-LAST:event_cbExtraWeeksActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonNext;
    private javax.swing.JButton buttonPrevious;
    private javax.swing.JComboBox cbChartFilter;
    private javax.swing.JComboBox cbChartFilterDuplicate;
    private javax.swing.JComboBox cbChartType;
    private javax.swing.JComboBox cbExtraWeeks;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbChartFilter;
    private javax.swing.JLabel lbChartType;
    private javax.swing.JLabel lbExtraWeeks;
    private javax.swing.JLabel lbWeek0;
    private javax.swing.JLabel lbWeek1;
    private javax.swing.JLabel lbWeek2;
    private javax.swing.JLabel lbWeek3;
    private javax.swing.JLabel lbWeek4;
    private javax.swing.JLabel lbWeek5;
    private javax.swing.JPanel spacing1;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaapplication1;

import java.awt.Color;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFrame;
import java.text.SimpleDateFormat;  
import java.util.Date;  

/**
 *
 * @author 91894
 */
public class UpdateFeesDetails extends javax.swing.JFrame {

    /**
     * Creates new form addFees
     */
    public UpdateFeesDetails() {
        initComponents();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        displayCashFirst();
        fillComboBox();
        
        int receiptNo = getReceiptNo();
        txt_receiptno.setText(Integer.toString(receiptNo));
        
        setRecords();
    }
    
    public void displayCashFirst(){
        lbl_ddno.setVisible(false);
        lbl_chequeno.setVisible(false);
        lbl_upino.setVisible(false);
        lbl_bankname.setVisible(false);
        
        txt_ddno.setVisible(false);
        txt_chequeno.setVisible(false);
        txt_upino.setVisible(false);
        txt_bankname.setVisible(false);
        
        
    }
    
    public boolean validation(){
        if(combo_payementmode.getSelectedItem().toString().equalsIgnoreCase("cheque")){
            if(txt_chequeno.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter cheque number!");
                return false;
            }
            if(txt_bankname.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter bank name!");
                return false;
            }
        }
        if(combo_payementmode.getSelectedItem().toString().equalsIgnoreCase("demand draft")){
            if(txt_ddno.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter Demand Draft number!");
                return false;
            }
            if(txt_bankname.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter bank name!");
                return false;
            }
        }
        if(combo_payementmode.getSelectedItem().toString().equalsIgnoreCase("credit/ debit card")){
            if(txt_bankname.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter bank name!");
                return false;
            }
        }
        if(combo_payementmode.getSelectedItem().toString().equalsIgnoreCase("upi")){
            if(txt_upino.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter UPI transaction id!");
                return false;
            }
            if(txt_bankname.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter bank name!");
                return false;
            }
        }
        if(date_current.getDate() == null){
            JOptionPane.showMessageDialog(this,"Please select a date!");
            return false;
        }
        if(txt_recievedfrom.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please enter depositer name!");
            return false;
        }
        if(txt_header.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please enter header name!");
            return false;
        }
        if(txt_rollno.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please enter fees amount(in numbers)!");
            return false;
        }
        
        return true;
    }
    
    public void fillComboBox(){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/fees_ms","root","root");
            PreparedStatement pst = con.prepareStatement("select cname from course");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                combo_coursename.addItem(rs.getString("cname"));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int getReceiptNo(){
        int receiptNo = 0;
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/fees_ms","root","root");
            PreparedStatement pst = con.prepareStatement("select max(reciept_no) from fees_details");
            ResultSet rs = pst.executeQuery();
            
            if(rs.next() == true){
                receiptNo = rs.getInt(1);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return receiptNo+1;
    }
    
    public String updateData(){
        
        String status = "";
        
        int receiptNo = Integer.parseInt(txt_receiptno.getText());
        String studentName = txt_recievedfrom.getText();
        String rollNo = txt_rollno.getText();
        String paymentMode = combo_payementmode.getSelectedItem().toString();
        String ChequeNo = txt_chequeno.getText();
        String ddNo = txt_ddno.getText();
        String upiNo = txt_upino.getText();
        String bankName = txt_bankname.getText();
        String courseName = combo_coursename.getSelectedItem().toString();
        String gstIN = lbl_gstin.getText();
        float Amount = Float.parseFloat(txt_amount1.getText());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(date_current.getDate());
        float totalAmount = Float.parseFloat(txt_totalamount.getText());
        float cgst = Float.parseFloat(txt_cgst.getText());
        float sgst = Float.parseFloat(txt_sgst.getText());
        String totalInWords = lbl_totalwords.getText();
        String remarks = txt_remarks.getText();
        int year1 = Integer.parseInt(date_year1.getText());
        int year2 = Integer.parseInt(date_year2.getText());
        
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/fees_ms","root","root");
            PreparedStatement pst = con.prepareStatement("update fees_details set student_name = ?,roll_no = ?,payment_mode = ?,cheque_no = ?,dd_no = ?,upi_no = ?,bank_name = ?,course_name = ?,gstin = ?,amount = ?,date_of_receipt = ?,total_amount = ?,cgst = ?,sgst = ?,total_in_words = ?,remark = ?,year1 = ?,year2 = ? where reciept_no=?");
            
            pst.setString(1, studentName);
            pst.setString(2, rollNo);
            pst.setString(3, paymentMode);
            pst.setString(4, ChequeNo);
            pst.setString(5, ddNo);
            pst.setString(6, upiNo);
            pst.setString(7, bankName);
            pst.setString(8, courseName);
            pst.setString(9, gstIN);
            pst.setFloat(10, Amount);
            pst.setString(11, date);
            pst.setFloat(12, totalAmount);
            pst.setFloat(13, cgst);
            pst.setFloat(14, sgst);
            pst.setString(15, totalInWords);
            pst.setString(16, remarks);
            pst.setInt(17, year1);
            pst.setInt(18, year2);
            pst.setInt(19, receiptNo);
            
            int rowCount = pst.executeUpdate();
            if(rowCount == 1){
                status = "success";
            }else{
                status  = "failed";
            }
            
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return status;
    }
    
    public void setRecords(){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/fees_ms","root","root");
            PreparedStatement pst = con.prepareStatement("select * from fees_details order by reciept_no desc fetch first 1 rows only");
            ResultSet rs = pst.executeQuery();
            rs.next();
            
            txt_receiptno.setText(rs.getString("reciept_no"));
            combo_payementmode.setSelectedItem(rs.getString("payment_mode"));
            String payment = rs.getString("payment_mode");
            if(payment.equalsIgnoreCase("cash")){
                lbl_ddno.setVisible(false);
                txt_ddno.setVisible(false);
                lbl_chequeno.setVisible(false);
                txt_chequeno.setVisible(false);
                lbl_upino.setVisible(false);
                txt_upino.setVisible(false);
                lbl_bankname.setVisible(false);
                txt_bankname.setVisible(false);
            }
            if(payment.equalsIgnoreCase("demand draft")){
                lbl_ddno.setVisible(true);
                txt_ddno.setText(rs.getString("dd_no"));
                lbl_chequeno.setVisible(false);
                txt_chequeno.setVisible(false);
                lbl_upino.setVisible(false);
                txt_upino.setVisible(false);
                lbl_bankname.setVisible(true);
                txt_bankname.setText(rs.getString("bank_name"));
                
            }
            if(payment.equalsIgnoreCase("credit/ debit card")){
                lbl_ddno.setVisible(false);
                txt_ddno.setVisible(false);
                lbl_chequeno.setVisible(false);
                txt_chequeno.setVisible(false);
                lbl_upino.setVisible(false);
                txt_upino.setVisible(false);
                lbl_bankname.setVisible(true);
                txt_bankname.setText(rs.getString("bank_name"));
            }
            if(payment.equalsIgnoreCase("cheque")){
                lbl_ddno.setVisible(false);
                txt_ddno.setVisible(false);
                lbl_chequeno.setVisible(true);
                txt_chequeno.setText(rs.getString("cheque_no"));
                lbl_upino.setVisible(false);
                txt_upino.setVisible(false);
                lbl_bankname.setVisible(true);
                txt_bankname.setText(rs.getString("bank_name"));
            }
            if(payment.equalsIgnoreCase("upi")){
                lbl_ddno.setVisible(false);
                txt_ddno.setVisible(false);
                lbl_chequeno.setVisible(false);
                txt_chequeno.setVisible(false);
                lbl_upino.setVisible(true);
                txt_upino.setText(rs.getString("upi_no"));
                lbl_bankname.setVisible(true);
                txt_bankname.setText(rs.getString("bank_name"));
            }
            txt_recievedfrom.setText(rs.getString("student_name"));
            String sDate = rs.getString("date_of_receipt");
            SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
            date_current.setDate(date1.parse(sDate));
            txt_rollno.setText(rs.getString("roll_no"));
            date_year1.setText(rs.getString("year1"));
            date_year2.setText(rs.getString("year2"));
            combo_coursename.setSelectedItem(rs.getString("course_name"));
            txt_header.setText(rs.getString("course_name"));
            txt_amount1.setText(rs.getString("amount"));
            txt_cgst.setText(rs.getString("cgst"));
            txt_sgst.setText(rs.getString("sgst"));
            txt_totalamount.setText(rs.getString("total_amount"));
            lbl_totalwords.setText(rs.getString("total_in_words"));
            txt_remarks.setText(rs.getString("remark"));
            
        } catch(Exception e){
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        panel_parent = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_receiptno = new app.bolivia.swing.JCTextField();
        jLabel2 = new javax.swing.JLabel();
        lbl_bankname = new javax.swing.JLabel();
        lbl_ddno = new javax.swing.JLabel();
        txt_upino = new app.bolivia.swing.JCTextField();
        lbl_gstin = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        date_current = new com.toedter.calendar.JDateChooser();
        lbl_upino = new javax.swing.JLabel();
        lbl_chequeno = new javax.swing.JLabel();
        txt_chequeno = new app.bolivia.swing.JCTextField();
        txt_ddno = new app.bolivia.swing.JCTextField();
        panel_child = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txt_cgst = new app.bolivia.swing.JCTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        combo_coursename = new rojerusan.RSComboMetro();
        jLabel20 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        lbl_totalwords = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_header = new app.bolivia.swing.JCTextField();
        txt_rollno = new app.bolivia.swing.JCTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        txt_sgst = new app.bolivia.swing.JCTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_totalamount = new app.bolivia.swing.JCTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_remarks = new javax.swing.JTextArea();
        jLabel30 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        btn_print = new javax.swing.JButton();
        txt_amount1 = new app.bolivia.swing.JCTextField();
        txt_recievedfrom = new app.bolivia.swing.JCTextField();
        date_year2 = new app.bolivia.swing.JCTextField();
        date_year1 = new app.bolivia.swing.JCTextField();
        combo_payementmode = new rojerusan.RSComboMetro();
        jLabel16 = new javax.swing.JLabel();
        txt_bankname = new app.bolivia.swing.JCTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my icons/home.png"))); // NOI18N
        jLabel9.setText(" Home");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 380, 70));

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my icons/search2.png"))); // NOI18N
        jLabel10.setText(" Search Record");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, -1, 60));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 380, 70));

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5MouseExited(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my icons/edit2.png"))); // NOI18N
        jLabel11.setText(" Edit Course");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, -1, 60));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 380, 70));

        jPanel6.setBackground(new java.awt.Color(0, 102, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel6MouseExited(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my icons/list.png"))); // NOI18N
        jLabel12.setText(" Course List");
        jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, -1, 60));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 380, 70));

        jPanel7.setBackground(new java.awt.Color(0, 102, 102));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
        });
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my icons/view all record.png"))); // NOI18N
        jLabel13.setText(" View All Record");
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, -1, 60));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 380, 70));

        jPanel8.setBackground(new java.awt.Color(0, 102, 102));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel8MouseExited(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my icons/back-button.png"))); // NOI18N
        jLabel14.setText(" Back");
        jPanel8.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, -1, 60));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 550, 380, 70));

        jPanel9.setBackground(new java.awt.Color(0, 102, 102));
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel9MouseExited(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my icons/logout.png"))); // NOI18N
        jLabel15.setText(" Logout");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, -1, 60));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 640, 380, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 840));

        panel_parent.setBackground(new java.awt.Color(0, 153, 153));
        panel_parent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Reciept No.:");
        panel_parent.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        txt_receiptno.setBackground(new java.awt.Color(0, 153, 153));
        txt_receiptno.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_receiptno.setForeground(new java.awt.Color(255, 255, 255));
        txt_receiptno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_receiptno.setPhColor(new java.awt.Color(255, 255, 255));
        txt_receiptno.setPlaceholder("TIGC-");
        panel_parent.add(txt_receiptno, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 320, 30));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Mode of Payment:");
        panel_parent.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        lbl_bankname.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_bankname.setForeground(new java.awt.Color(255, 255, 255));
        lbl_bankname.setText("Bank Name:");
        panel_parent.add(lbl_bankname, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        lbl_ddno.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_ddno.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ddno.setText("DD No.:");
        panel_parent.add(lbl_ddno, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        txt_upino.setBackground(new java.awt.Color(0, 153, 153));
        txt_upino.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_upino.setForeground(new java.awt.Color(255, 255, 255));
        txt_upino.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_upino.setPhColor(new java.awt.Color(255, 255, 255));
        txt_upino.setPlaceholder("Enter UPI Transaction ID");
        panel_parent.add(txt_upino, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 320, -1));

        lbl_gstin.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_gstin.setForeground(new java.awt.Color(255, 255, 255));
        lbl_gstin.setText("27AAATL0123");
        panel_parent.add(lbl_gstin, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 50, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Date:");
        panel_parent.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, -1, -1));

        date_current.setBackground(new java.awt.Color(0, 153, 153));
        date_current.setForeground(new java.awt.Color(0, 153, 153));
        panel_parent.add(date_current, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 190, 30));

        lbl_upino.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_upino.setForeground(new java.awt.Color(255, 255, 255));
        lbl_upino.setText("UPI Trans. ID:");
        panel_parent.add(lbl_upino, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        lbl_chequeno.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_chequeno.setForeground(new java.awt.Color(255, 255, 255));
        lbl_chequeno.setText("Cheque No.:");
        panel_parent.add(lbl_chequeno, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        txt_chequeno.setBackground(new java.awt.Color(0, 153, 153));
        txt_chequeno.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_chequeno.setForeground(new java.awt.Color(255, 255, 255));
        txt_chequeno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_chequeno.setPhColor(new java.awt.Color(255, 255, 255));
        txt_chequeno.setPlaceholder("Enter Cheque Number");
        panel_parent.add(txt_chequeno, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 320, -1));

        txt_ddno.setBackground(new java.awt.Color(0, 153, 153));
        txt_ddno.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_ddno.setForeground(new java.awt.Color(255, 255, 255));
        txt_ddno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_ddno.setPhColor(new java.awt.Color(255, 255, 255));
        txt_ddno.setPlaceholder("Enter Demand Draft Number");
        panel_parent.add(txt_ddno, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 320, -1));

        panel_child.setBackground(new java.awt.Color(0, 153, 153));
        panel_child.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("The following payment in the college office for the year:");
        panel_child.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        txt_cgst.setEditable(false);
        txt_cgst.setBackground(new java.awt.Color(0, 153, 153));
        txt_cgst.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_cgst.setForeground(new java.awt.Color(255, 255, 255));
        txt_cgst.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_cgst.setPhColor(new java.awt.Color(255, 255, 255));
        panel_child.add(txt_cgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 240, 270, -1));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Amount(Rs.)");
        panel_child.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 140, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("TO");
        jLabel8.setToolTipText("");
        panel_child.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, -1, -1));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Roll Number:");
        panel_child.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 90, -1, -1));

        combo_coursename.setColorArrow(new java.awt.Color(0, 153, 153));
        combo_coursename.setColorBorde(new java.awt.Color(0, 153, 153));
        combo_coursename.setColorFondo(new java.awt.Color(0, 153, 153));
        combo_coursename.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        combo_coursename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_coursenameActionPerformed(evt);
            }
        });
        panel_child.add(combo_coursename, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 310, -1));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Recieved From:");
        panel_child.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        panel_child.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 480, 310, 10));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jSeparator2.setPreferredSize(new java.awt.Dimension(0, 10));
        panel_child.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 950, 10));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Course:");
        panel_child.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        lbl_totalwords.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_totalwords.setForeground(new java.awt.Color(255, 255, 255));
        panel_child.add(lbl_totalwords, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, 590, 20));

        jLabel23.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Header");
        panel_child.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, -1, -1));

        txt_header.setEditable(false);
        txt_header.setBackground(new java.awt.Color(0, 153, 153));
        txt_header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_header.setForeground(new java.awt.Color(255, 255, 255));
        txt_header.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_header.setPhColor(new java.awt.Color(255, 255, 255));
        txt_header.setPlaceholder("Enter Header");
        panel_child.add(txt_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 420, -1));

        txt_rollno.setBackground(new java.awt.Color(0, 153, 153));
        txt_rollno.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_rollno.setForeground(new java.awt.Color(255, 255, 255));
        txt_rollno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_rollno.setPhColor(new java.awt.Color(255, 255, 255));
        txt_rollno.setPlaceholder("Enter Roll Number");
        txt_rollno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rollnoActionPerformed(evt);
            }
        });
        panel_child.add(txt_rollno, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 80, 170, -1));

        jLabel24.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Sl No.");
        panel_child.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("CGST - 9%");
        panel_child.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, -1, -1));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        panel_child.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 950, 10));

        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("SGST - 9%");
        panel_child.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, -1, -1));

        txt_sgst.setEditable(false);
        txt_sgst.setBackground(new java.awt.Color(0, 153, 153));
        txt_sgst.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_sgst.setForeground(new java.awt.Color(255, 255, 255));
        txt_sgst.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_sgst.setPhColor(new java.awt.Color(255, 255, 255));
        panel_child.add(txt_sgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 290, 270, -1));

        jLabel27.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Total Amount to be paid: ");
        panel_child.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, -1, -1));

        jLabel28.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Remarks: ");
        panel_child.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, -1, -1));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Reciever's Signature");
        panel_child.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 490, -1, -1));

        txt_totalamount.setEditable(false);
        txt_totalamount.setBackground(new java.awt.Color(0, 153, 153));
        txt_totalamount.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_totalamount.setForeground(new java.awt.Color(255, 255, 255));
        txt_totalamount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_totalamount.setPhColor(new java.awt.Color(255, 255, 255));
        txt_totalamount.setPlaceholder("Total Amount");
        panel_child.add(txt_totalamount, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 350, 270, -1));

        txt_remarks.setBackground(new java.awt.Color(0, 153, 153));
        txt_remarks.setColumns(20);
        txt_remarks.setForeground(new java.awt.Color(255, 255, 255));
        txt_remarks.setRows(5);
        txt_remarks.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 2, new java.awt.Color(255, 255, 255)));
        jScrollPane1.setViewportView(txt_remarks);

        panel_child.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 460, 410, 110));

        jLabel30.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Total Amount in Words: ");
        panel_child.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        panel_child.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 340, 380, 10));

        btn_print.setBackground(new java.awt.Color(153, 153, 153));
        btn_print.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setText("Print");
        btn_print.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });
        panel_child.add(btn_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 537, 100, 40));

        txt_amount1.setBackground(new java.awt.Color(0, 153, 153));
        txt_amount1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_amount1.setForeground(new java.awt.Color(255, 255, 255));
        txt_amount1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_amount1.setPhColor(new java.awt.Color(255, 255, 255));
        txt_amount1.setPlaceholder("Enter Amount INR");
        txt_amount1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_amount1ActionPerformed(evt);
            }
        });
        panel_child.add(txt_amount1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 190, 270, -1));

        txt_recievedfrom.setBackground(new java.awt.Color(0, 153, 153));
        txt_recievedfrom.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_recievedfrom.setForeground(new java.awt.Color(255, 255, 255));
        txt_recievedfrom.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_recievedfrom.setPhColor(new java.awt.Color(255, 255, 255));
        txt_recievedfrom.setPlaceholder("Enter Depositer Name");
        panel_child.add(txt_recievedfrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 320, -1));

        date_year2.setEditable(false);
        date_year2.setBackground(new java.awt.Color(0, 153, 153));
        date_year2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        date_year2.setForeground(new java.awt.Color(255, 255, 255));
        date_year2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        date_year2.setPhColor(new java.awt.Color(255, 255, 255));
        date_year2.setPlaceholder("Year2");
        date_year2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date_year2ActionPerformed(evt);
            }
        });
        panel_child.add(date_year2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 150, -1));

        date_year1.setBackground(new java.awt.Color(0, 153, 153));
        date_year1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        date_year1.setForeground(new java.awt.Color(255, 255, 255));
        date_year1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        date_year1.setPhColor(new java.awt.Color(255, 255, 255));
        date_year1.setPlaceholder("Year1");
        date_year1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date_year1ActionPerformed(evt);
            }
        });
        panel_child.add(date_year1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 150, -1));

        panel_parent.add(panel_child, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 1090, 670));

        combo_payementmode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cash", "Demand Draft", "Credit/ Debit Card", "Cheque", "UPI" }));
        combo_payementmode.setColorArrow(new java.awt.Color(0, 153, 153));
        combo_payementmode.setColorBorde(new java.awt.Color(0, 153, 153));
        combo_payementmode.setColorFondo(new java.awt.Color(0, 153, 153));
        combo_payementmode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        combo_payementmode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_payementmodeActionPerformed(evt);
            }
        });
        panel_parent.add(combo_payementmode, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 310, -1));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("GSTIN: ");
        panel_parent.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 50, -1, -1));

        txt_bankname.setBackground(new java.awt.Color(0, 153, 153));
        txt_bankname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_bankname.setForeground(new java.awt.Color(255, 255, 255));
        txt_bankname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_bankname.setPhColor(new java.awt.Color(255, 255, 255));
        txt_bankname.setPlaceholder("Enter Bank Name");
        panel_parent.add(txt_bankname, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 320, -1));

        getContentPane().add(panel_parent, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, 1080, 830));

        setSize(new java.awt.Dimension(1537, 835));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
        // TODO add your handling code here:
        Color color = new Color(0,51,51);
        jPanel3.setBackground(color);
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited
        // TODO add your handling code here:
        Color color = new Color(0,102,102);
        jPanel3.setBackground(color);
    }//GEN-LAST:event_jPanel3MouseExited

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        // TODO add your handling code here:
        Color color = new Color(0,51,51);
        jPanel4.setBackground(color);
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        // TODO add your handling code here:
        Color color = new Color(0,102,102);
        jPanel4.setBackground(color);
    }//GEN-LAST:event_jPanel4MouseExited

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered
        // TODO add your handling code here:
        Color color = new Color(0,51,51);
        jPanel5.setBackground(color);
    }//GEN-LAST:event_jPanel5MouseEntered

    private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseExited
        // TODO add your handling code here:
        Color color = new Color(0,102,102);
        jPanel5.setBackground(color);
    }//GEN-LAST:event_jPanel5MouseExited

    private void jPanel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseEntered
        // TODO add your handling code here:
        Color color = new Color(0,51,51);
        jPanel6.setBackground(color);
    }//GEN-LAST:event_jPanel6MouseEntered

    private void jPanel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseExited
        // TODO add your handling code here:
        Color color = new Color(0,102,102);
        jPanel6.setBackground(color);
    }//GEN-LAST:event_jPanel6MouseExited

    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered
        // TODO add your handling code here:
        Color color = new Color(0,51,51);
        jPanel7.setBackground(color);
    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited
        // TODO add your handling code here:
        Color color = new Color(0,102,102);
        jPanel7.setBackground(color);
    }//GEN-LAST:event_jPanel7MouseExited

    private void jPanel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseEntered
        // TODO add your handling code here:
        Color color = new Color(0,51,51);
        jPanel8.setBackground(color);
    }//GEN-LAST:event_jPanel8MouseEntered

    private void jPanel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseExited
        // TODO add your handling code here:
        Color color = new Color(0,102,102);
        jPanel8.setBackground(color);
    }//GEN-LAST:event_jPanel8MouseExited

    private void jPanel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseEntered
        // TODO add your handling code here:
        Color color = new Color(0,51,51);
        jPanel9.setBackground(color);
    }//GEN-LAST:event_jPanel9MouseEntered

    private void jPanel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseExited
        // TODO add your handling code here:
        Color color = new Color(0,102,102);
        jPanel9.setBackground(color);
    }//GEN-LAST:event_jPanel9MouseExited

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
        Home home = new Home();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel3MouseClicked

    private void combo_payementmodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_payementmodeActionPerformed
        // TODO add your handling code here:
        if(combo_payementmode.getSelectedIndex() == 0){{
            lbl_ddno.setVisible(false);
            txt_ddno.setVisible(false);
            lbl_chequeno.setVisible(false);
            txt_chequeno.setVisible(false);
            lbl_upino.setVisible(false);
            txt_upino.setVisible(false);
            lbl_bankname.setVisible(false);
            txt_bankname.setVisible(false);
        }
        } else if(combo_payementmode.getSelectedIndex() == 1){
            lbl_ddno.setVisible(true);
            txt_ddno.setVisible(true);
            lbl_chequeno.setVisible(false);
            txt_chequeno.setVisible(false);
            lbl_upino.setVisible(false);
            txt_upino.setVisible(false);
            lbl_bankname.setVisible(true);
            txt_bankname.setVisible(true);
        } else if(combo_payementmode.getSelectedIndex() == 2){
            lbl_ddno.setVisible(false);
            txt_ddno.setVisible(false);
            lbl_chequeno.setVisible(false);
            txt_chequeno.setVisible(false);
            lbl_upino.setVisible(false);
            txt_upino.setVisible(false);
            lbl_bankname.setVisible(true);
            txt_bankname.setVisible(true);
        } else if(combo_payementmode.getSelectedIndex() == 3){
            lbl_ddno.setVisible(false);
            txt_ddno.setVisible(false);
            lbl_chequeno.setVisible(true);
            txt_chequeno.setVisible(true);
            lbl_upino.setVisible(false);
            txt_upino.setVisible(false);
            lbl_bankname.setVisible(true);
            txt_bankname.setVisible(true);
        } else if(combo_payementmode.getSelectedIndex() == 4){
            lbl_ddno.setVisible(false);
            txt_ddno.setVisible(false);
            lbl_chequeno.setVisible(false);
            txt_chequeno.setVisible(false);
            lbl_upino.setVisible(true);
            txt_upino.setVisible(true);
            lbl_bankname.setVisible(true);
            txt_bankname.setVisible(true);
        }
    }//GEN-LAST:event_combo_payementmodeActionPerformed

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
        Login login = new Login();
        login.show();
        this.dispose();
    }//GEN-LAST:event_jPanel9MouseClicked

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        if(validation()){
            String result = updateData();
            
            if(result == "success"){
                JOptionPane.showMessageDialog(this,"Record Updated Successfully");
                PrintReceipt print = new PrintReceipt();
                print.show();
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(this, "Updation Failed");
            }
        }
    }//GEN-LAST:event_btn_printActionPerformed

    private void txt_rollnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rollnoActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_rollnoActionPerformed

    private void combo_coursenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_coursenameActionPerformed
        // TODO add your handling code here:
        txt_header.setText(combo_coursename.getSelectedItem().toString());
    }//GEN-LAST:event_combo_coursenameActionPerformed

    private void txt_amount1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_amount1ActionPerformed
        // TODO add your handling code here:
        double amt = Double.parseDouble(txt_amount1.getText());
        
        Double cgst = (double)(amt*0.09);
        Double sgst = (double)(amt*0.09);
        
        txt_cgst.setText(cgst.toString());
        txt_sgst.setText(sgst.toString());
        
        double total = amt + cgst + sgst;
        
        txt_totalamount.setText(String.valueOf(total));
        
        lbl_totalwords.setText(NumberToWordsConverter.convert((int)(total)) + " Rupees Only");
    }//GEN-LAST:event_txt_amount1ActionPerformed

    private void date_year2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_date_year2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_date_year2ActionPerformed

    private void date_year1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_date_year1ActionPerformed
        // TODO add your handling code here:
        int year = Integer.parseInt(date_year1.getText());
        int year2 = year +1;
        date_year2.setText(Integer.toString(year2));
    }//GEN-LAST:event_date_year1ActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        EditCourse editcourse = new EditCourse();
        editcourse.show();
        this.dispose();
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
        ViewCourse vcourse = new ViewCourse();
        vcourse.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
        SearchRecord search = new SearchRecord();
        search.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
        ViewRecord view = new ViewRecord();
        view.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel7MouseClicked

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
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateFeesDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print;
    private rojerusan.RSComboMetro combo_coursename;
    private rojerusan.RSComboMetro combo_payementmode;
    private com.toedter.calendar.JDateChooser date_current;
    private app.bolivia.swing.JCTextField date_year1;
    private app.bolivia.swing.JCTextField date_year2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_bankname;
    private javax.swing.JLabel lbl_chequeno;
    private javax.swing.JLabel lbl_ddno;
    private javax.swing.JLabel lbl_gstin;
    private javax.swing.JLabel lbl_totalwords;
    private javax.swing.JLabel lbl_upino;
    private javax.swing.JPanel panel_child;
    private javax.swing.JPanel panel_parent;
    private app.bolivia.swing.JCTextField txt_amount1;
    private app.bolivia.swing.JCTextField txt_bankname;
    private app.bolivia.swing.JCTextField txt_cgst;
    private app.bolivia.swing.JCTextField txt_chequeno;
    private app.bolivia.swing.JCTextField txt_ddno;
    private app.bolivia.swing.JCTextField txt_header;
    private app.bolivia.swing.JCTextField txt_receiptno;
    private app.bolivia.swing.JCTextField txt_recievedfrom;
    private javax.swing.JTextArea txt_remarks;
    private app.bolivia.swing.JCTextField txt_rollno;
    private app.bolivia.swing.JCTextField txt_sgst;
    private app.bolivia.swing.JCTextField txt_totalamount;
    private app.bolivia.swing.JCTextField txt_upino;
    // End of variables declaration//GEN-END:variables
}

package com.company.examples.types;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Dao {

    public void insertrow(product p) {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/capstone", "root", "root");

            PreparedStatement pstmt = con.prepareStatement("Insert into prodtable (pogid,supc,price,Quantity) values(?,?,?,?)");
            pstmt.setString(1, p.getPogId());
            pstmt.setString(2, p.getSupc());
            pstmt.setString(3, p.getPrice());
            pstmt.setString(4, p.getQuantity());

            int rowAffected = pstmt.executeUpdate();
            if(rowAffected==1)
            {
                System.out.println("The row is inserted");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if(rs != null)  rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
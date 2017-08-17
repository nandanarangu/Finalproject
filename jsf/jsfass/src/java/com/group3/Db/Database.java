/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Manu
 */
public class Database {
    // Used to establish connection  
    Connection con;
    public Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "1234");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

}
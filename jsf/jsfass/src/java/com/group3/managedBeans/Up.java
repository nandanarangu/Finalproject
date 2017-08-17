/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.managedBeans;
import com.group3.Db.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
/**
 *
 * @author Manu
 */
@ManagedBean(name="up")
@RequestScoped
public class Up {
    String id;
    String name;
    String email;
    String password;
    String gender;
    String address;
    Connection conn;
    Database db=new Database();
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    // you need to work on this. 

    public String update() {
         String updateQuery = "UPDATE udetail SET username=?,email =?,password=?,address=?,gender=? WHERE userid =?";
       
        int res=0;
        try{
        conn = db.getConnection();
        PreparedStatement ps=conn.prepareStatement(updateQuery);
        ps.setString(1,name);
        ps.setString(2,email);
        ps.setString(3,password);
        ps.setString(4,address);
        ps.setString(5,gender);
        ps.setString(6,id);
        res=ps.executeUpdate();
        conn.close();
         }catch(SQLException e)
       {System.out.print("Error");
       }
        if (res != 0) {
            return "index.xhtml?faces-redirect=true";
        } else {
            return "edit.xhtml?faces-redirect=true";
        }
        
      }
}

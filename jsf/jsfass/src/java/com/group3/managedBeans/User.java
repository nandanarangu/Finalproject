package com.group3.managedBeans;
import com.group3.Db.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.Date;

@ManagedBean(name="user")
@RequestScoped
public class User {
    String id;
    String name;
    String email;
    String password;
    String gender;
    String address;
    ArrayList usersList;
    Connection connection;
    private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
 
    Database db=new Database();
    
    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

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
// Used to fetch all records  

    public ArrayList usersList() {
        
        try {
            usersList = new ArrayList();
             connection= db.getConnection();
            Statement stmt = db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from udetail");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("userid"));
                user.setName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setGender(rs.getString("gender"));
                usersList.add(user);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return usersList;
    }
// Used to save user record  

    public String save() {
        String bool=" ";
        int result=0;
        Date today=new Date();
        try {
            connection = db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("insert into udetail(userid,username,email,password,address,gender) values(?,?,?,?,?,?)");
            stmt.setString(1, name+email);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5, address);
            stmt.setString(6, gender);
            result=stmt.executeUpdate();
        }catch (Exception e) {
            System.out.println(e);
        }
        try{
            String insertQuery="insert into user_history"+"(email,status,statusdate,username,address,gender)"+"values(?,?,?,?,?,?)";
            PreparedStatement stmt1= connection.prepareStatement(insertQuery);
            stmt1.setString(1, email);
            stmt1.setString(2, "insert");
            stmt1.setString(3, today.toString());
            stmt1.setString(4, name);
            stmt1.setString(5, address);
            stmt1.setString(6, gender);
            stmt1.executeQuery();
            bool="true";
        }catch (Exception e) {
            System.out.println(e);
        }
        
        if (bool.equals("true") && result!=0) {
            return "index.xhtml?faces-forward=true";
        } else {
            return "create.xhtml?faces-redirect=true";
        }
        
    }
// Used to fetch record to update  

    public String edit(String id) {
        User user = new User();
      
        try {
            connection = db.getConnection();
            PreparedStatement stmt  =connection.prepareStatement("select * from udetail where userid = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
            user.setId(rs.getString("userid"));
            user.setName(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setAddress(rs.getString("address"));
            user.setGender(rs.getString("gender"));
            }
            connection.close();
            sessionMap.put("eUser",user);
        } 
        
        catch (Exception e) {
            System.out.println(e);
           
        }
        return "edit.xhtml?faces-redirect=true";
    }
// you need to work on this. 

    public String update(User uss) {
        String bool=" "; 
     String updateQuery = "update udetail SET username=?,email =?,password=?,address=?,gender=? WHERE userid =?";
        //String st= (String)id;
        //int res=0;
     
        User us= (User)sessionMap.get("eUser");
        try{
        connection = db.getConnection();
        PreparedStatement ps = connection.prepareStatement(updateQuery);
        ps.setString(1,uss.name);
        ps.setString(2,uss.email);
        ps.setString(3,uss.password);
        ps.setString(4,uss.address);
        ps.setString(5,uss.gender);
        ps.setString(6,uss.id);
        ps.executeUpdate();
        String insertQuery="insert into user_history"+"(email,status,statusdate,username,address,gender)"+"values(?,?,?,?,?,?)";
        PreparedStatement stmt1 = connection.prepareStatement(insertQuery);
            stmt1.setString(1, uss.email);
            stmt1.setString(2, "update");
            Date today = new Date();
            stmt1.setString(3, today.toString());
            stmt1.setString(4, uss.name);
            stmt1.setString(5, uss.address);
            stmt1.setString(6, uss.gender);
            stmt1.executeUpdate();
        connection.close();
        ps.close();
        bool="true";
         }catch(SQLException e)
       {System.out.print("Error");
       }
        if (bool.equals("true")) {
            return "index.xhtml?faces-redirect=true";
        } else {
            return "edit.xhtml?faces-redirect=true";
        }
        
      }
// Used to delete user record  

    public String delete(String id) {
         String bool=" "; 
         String deleteQuery="delete from udetail where userid= ?";
        try{
        connection = db.getConnection();
        PreparedStatement ps=connection.prepareStatement(deleteQuery);
        ps.setString(1,id);
        ps.executeQuery();
        bool="true";
        connection.close();
         }catch(SQLException e)
       {System.out.print("Error");
       }
        if (bool.equals("true")) {
            return "index.xhtml?faces-redirect=true";
        } else {
            return "edit.xhtml?faces-redirect=true";
        }
    }
// Used to set user gender  

    public String getGenderName(char gender) {
        if (gender == 'M') {
            return "Male";
        } else {
            return "Female";
        }
    }
}

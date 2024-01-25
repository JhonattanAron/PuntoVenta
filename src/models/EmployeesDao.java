
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

/**
 *
 * @author aronc
 */
public class EmployeesDao {
    //instaciar la conexion
    Conection_MySQL cn = new Conection_MySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //VARIABLES PARA ENVIAR DATOS ENTRE INTEFRAZES
    public static int id_user =0;
    public static String Full_name_user="";
    public static String username_user = "";
    public static String addres_user = "";
    public static String rol_user = "";
    public static String email_user = "";
    public static String telephone_user = "";
    
    //metodo de login
    public Employees loginQuery(String user,String Password){
        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        Employees employes = new Employees();
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            //enviar parametros
            pst.setString(1, user);
            pst.setString(2, Password);
            rs = pst.executeQuery();
            
            if(rs.next()){
                employes.setId(rs.getInt("id_Employees"));
                id_user = employes.getId();
                employes.setFullname(rs.getString("full_name"));
                Full_name_user = employes.getFullname();
                employes.setUsername(rs.getString("username"));
                username_user = employes.getUsername();
                employes.setAddres(rs.getString("adrees"));
                addres_user = employes.getAddres();
                employes.setTelephone(rs.getString("telephone"));
                telephone_user = employes.getTelephone();
                employes.setRol(rs.getString("rol"));
                rol_user = employes.getRol();
                employes.setEmail(rs.getString("email"));
                email_user = employes.getEmail();
                
                
                
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener al empleado"+e);
        }
        return employes;
    }
    
    //regitrar empleado
    public boolean registerEmployeeQuerry(Employees employees){
        String query = "INSERT INTO employees(id_Employees, full_name, username, adrees, telephone, email, password, rol,created,"
                + "updated) VALUES(?,?,?,?,?,?,?,?,?,?)";
        
        Timestamp datetimes = new Timestamp(new Date().getTime());
        //capturar error
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1,employees.getId());
            pst.setString(2, employees.getFullname());
            pst.setString(3, employees.getUsername());
            pst.setString(4, employees.getAddres());
            pst.setString(5, employees.getTelephone());
            pst.setString(6, employees.getEmail());
            pst.setString(7, employees.getPassword());
            pst.setString(8, employees.getRol());
            pst.setTimestamp(9, datetimes);
            pst.setTimestamp(10, datetimes);
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al empleado "+e);
            return false;
            
            
        }
        
    }
    
    //listar empleado
    public List listEmployeeQuerry(String value){
        List<Employees> list_employees = new ArrayList();
        String query ="SELECT * FROM employees ORDER BY rol ASC";
        String query_search_emplyees = "SELECT * FROM employees WHERE id_Employees LIKE '"+value+"%'";
        try {
            conn= cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_emplyees);
                rs = pst.executeQuery();
            }
            while (rs.next()) {                
                Employees employees = new Employees();
                employees.setId(rs.getInt("id_Employees"));
                employees.setFullname(rs.getString("full_name"));
                employees.setUsername(rs.getString("username"));
                employees.setAddres(rs.getString("adrees"));
                employees.setTelephone(rs.getString("telephone"));
                employees.setEmail(rs.getString("email"));
                employees.setPassword(rs.getString("password"));
                employees.setRol(rs.getString("rol"));
                employees.setCreate(query);
                
                list_employees.add(employees);
                
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            
        }
        return list_employees;
    }

    //modificar empleados
    public boolean updateEmployeeQuerry(Employees employees){
        String query = "UPDATE employees SET full_name = ? ,username= ?,adrees=?,telephone=?,email=?,rol=?,updated=? "
                + "WHERE id_Employees = ?";
        
        Timestamp datetimes = new Timestamp(new Date().getTime());
        //capturar error
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, employees.getFullname());
            pst.setString(2, employees.getUsername());
            pst.setString(3, employees.getAddres());
            pst.setString(4, employees.getTelephone());
            pst.setString(5, employees.getEmail());
            pst.setString(6, employees.getRol());
            pst.setTimestamp(7, datetimes);
            pst.setInt(8, employees.getId());
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del empleado: "+e.getMessage());
            return false;
            
            
        }
        
    }
    //borrar empleados
    public boolean deleteEmployeeQuerry(int id){
        String query = "DELETE FROM `puntosdeventa_dtabase`.`employees` WHERE (`id_Employees` = '"+id+"');";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No ser puede eliminar un empleado que tenga relacion con otra tabla: "+e.getMessage());
            return false;
        }
        
    }
    //cambiar contraseña
   public boolean updateEmployeesPassword(Employees employees){
       String query = "UPDATE employees SET password =? WHERE username = '"+username_user+"'";
       try {
           conn = cn.getConnection();
           pst = conn.prepareStatement(query);
           pst.setString(1,employees.getPassword());
           pst.executeUpdate();
           return true;
           
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar la contraseña");
           return false;
       }
   } 
}


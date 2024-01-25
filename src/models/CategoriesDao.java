/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author aronc
 */
public class CategoriesDao {
    //INSTANCIAR CONEXION
    Conection_MySQL cn = new Conection_MySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //REGISTRAR CATEGORIAS
    public boolean RegisterCtategoryQuerry(Categories categories){
        String querry ="INSET INTO categories(naem,create,update) VALUES(?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.setString(1,categories.getName());
            pst.setTimestamp(2, datetime);
            pst.setTimestamp(2, datetime);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al registrar la categoria");
            return false;
        
        }
    }
    //listar categorias
    public List listCategoryquerry(String value){
        List<Categories> list_category = new ArrayList();
        String querry = "SELECT * FROM categories ";
        String querry_search_categories = "SELECT * FROM categories WHERE id LIKE '%" + value + "'%";
        
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase(""))
            {
                pst = conn.prepareStatement(querry);
                pst.executeQuery();
                
            }else{
                pst = conn.prepareStatement(querry_search_categories);
                pst.executeQuery();
            }
            while(rs.next()){
               Categories categori = new Categories();
               categori.setId(rs.getInt("id"));
               categori.setName(rs.getString("name"));
               list_category.add(categori);
            }
            
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
        return list_category;
        
    }
    
    //modificar categoria
    public boolean UpdateCtategoryQuerry(Categories categories){
        String querry ="UPDATE INTO categories name=?,update=? WHERE id=?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.setString(1,categories.getName());
            pst.setTimestamp(2, datetime);
            pst.setInt(3, categories.getId());
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al modificar la categoria");
            return false;
        
        }
    }
    //eliminar categoria
    public boolean DeleteCustomersQuerry(int id){
        String querry = "DELETE FROM categories WHERE is ="+id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al eliminar la categoria: "+e);
            return false;
        }
    }
       
}

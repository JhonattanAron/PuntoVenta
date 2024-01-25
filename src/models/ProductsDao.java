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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author aronc
 */
public class ProductsDao {

    //instancia conexion
    Conection_MySQL cn = new Conection_MySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //registrar Productos
    public boolean registerProductsQuerry(Products products) {
        String querry = "INSERT INTO products(id,code,name,desscription,unit_price,create,"
                + "update,category_id) VALUES(?,?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.setInt(1,products.getId());
            pst.setInt(2, products.getCode());
            pst.setString(3, products.getName());
            pst.setString(4, products.getDescription());
            pst.setDouble(5, products.getUnit_price());
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            pst.setInt(8, products.getCategory_id());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "no fue posible registrar el prodcuto: " + e);
            return false;
        }

    }

    //lsitar productos
    public List listProductQuerry(String values) {
        List<Products> products_list = new ArrayList();
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro,categories ca "
                + "WHERE pro.category_id = ca.id;";
        String query_search_products = "SELECTO pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca "
                + "ON pro category_id = ca.id WHRE pro.name LIKE '&" + values + "&'";
        try {
            conn = cn.getConnection();
            if (values.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_products);
                pst.executeQuery();
            }
            while (rs.next()) {
                Products productos = new Products();
                productos.setId(rs.getInt("id"));
                productos.setCode(rs.getInt("code"));
                productos.setName(rs.getString("name"));
                productos.setDescription(rs.getString("description"));
                productos.setUnit_price(rs.getDouble("unit_price"));
                productos.setProduct_quantity(rs.getInt("product_quantity"));
                productos.setCategory_name(rs.getString("category_name"));
                products_list.add(productos);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al econtrar el cliente");

        }
        return products_list;
    }

    //modificar productos
    public boolean updateProductsQuerry(Products products) {
        String querry = "UPDATE products SET code=?, name=?,desscription=?,unit_price=?,update=?,"
                + "category_id = ? WHERE id=?";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.setInt(2, products.getCode());
            pst.setString(3, products.getName());
            pst.setString(4, products.getDescription());
            pst.setDouble(5, products.getUnit_price());
            pst.setTimestamp(6, datetime);
            pst.setInt(8, products.getCategory_id());
            pst.setInt(9,products.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "no fue posible modificar el prodcuto: " + e);
            return false;
        }

    }
    
    //eliminar productos
    public boolean DeleteProductsQuerry(int id){
        String querry = "DELETE FROM products WHERE is ="+id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al eliminar el producto"+e);
            return false;
        }
    }
    
    //buscar producto
    public Products searchProducts(int id){
        String querry = "SELECT pro.*,ca.name AS category_name FROM products pro"
                + "INSERT JOIN categories ca ON pro.category_id = ca.id WHERE pro.id=?";
        
        Products product = new Products();
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            if(rs.next()){
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setCategory_id(rs.getInt("category"));
                product.setCategory_name(rs.getString("category_name"));
                
            }
        
        }catch(SQLException e){
            
            JOptionPane.showMessageDialog(null, "eror al buscar el producto"+e);
            
        }
        return product;
        
    }
    
    //bucar producto por codigo
    public Products searchCode(int code){
        String querry = "SELECT pro.id,pro.name FROM products pro WHERE pro.code=?";
        
        Products product = new Products();
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.setInt(1,code);
            rs = pst.executeQuery();
            
            if(rs.next()){
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));    
            }
        
        }catch(SQLException e){
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
        return product;
        
    }
    
    //traer cantidad de productos
    public Products searchId(int id){
        String query = "SELECT pro.products_quantity FROM products pro WHERE pro.id=?";
        Products products = new Products();
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                products.setProduct_quantity(rs.getInt("product_quantity"));
            }
        
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return products;
    }
    
    //actualizar stock
    public boolean updateStockQuery(int amount,int product_id){
        String query = "UPDATE products SET products_quantity = ? WHERE id =?";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, amount);
            pst.setInt(2, product_id);
            pst.execute();
            return true;
        
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
}

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
public class SupliersDao {

    //instanciar conexion
    Conection_MySQL cn = new Conection_MySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //registrar proovedor
    public boolean RegisterSupliersQuerry(Supliers suplier) {
        String querry = "INSERT INTO `puntosdeventa_dtabase`.`supliers` (`id`, `name`, `Descriprion`, `telephone`, `adrees`, `email`, `city`, `create`, `update`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.setInt(1, suplier.getId());
            pst.setString(2, suplier.getFull_name());
            pst.setString(3, suplier.getDescription());
            pst.setString(4, suplier.getTelephone());
            pst.setString(5, suplier.getAdrees());
            pst.setString(6, suplier.getEmail());
            pst.setString(7, suplier.getCity());
            pst.setTimestamp(8, datetime);
            pst.setTimestamp(9, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al registrar el Proveedor" + e);
            return false;

        }
    }
    //enlistar provvedores
    public List ListsupliersQuerry(String value) {

        List<Supliers> list_supliers = new ArrayList();
        String querry = "SELECT * FROM supliers";
        String querry_search_supliers = "SELECT * FROM supliers WHERE name LIKE '%"+value+"%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(querry);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(querry_search_supliers);
                rs = pst.executeQuery();    
            }
            while(rs.next()){
                Supliers suplier = new Supliers();
                suplier.setId(rs.getInt("id"));
                suplier.setFull_name(rs.getString("name"));
                suplier.setDescription(rs.getString("Descriprion"));
                suplier.setTelephone(rs.getString("telephone"));
                suplier.setAdrees(rs.getString("adrees"));
                suplier.setEmail(rs.getString("email"));
                suplier.setCity(rs.getString("city"));
                list_supliers.add(suplier);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_supliers; 

    }
    //modificar provedor
    public boolean UpdateSupliersQuerry(Supliers suplier) {
        String querry = "UPDATE `puntosdeventa_dtabase`.`supliers` SET `name` = ?, `Descriprion` = ?, `telephone` = ?, `adrees` = ?, `email` = ?, `city` = ?, `update` =? "
                + "WHERE (`id` =?);";

        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.setString(1, suplier.getFull_name());
            pst.setString(2, suplier.getDescription());
            pst.setString(3, suplier.getTelephone());
            pst.setString(4, suplier.getAdrees());
            pst.setString(5, suplier.getEmail());
            pst.setString(6, suplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setInt(8,suplier.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al modificar los datos de el Proveedor: " + e);
            return false;

        }
    }
    //eliminar provedor
    public boolean deleteSuplierQuerry(int id){
        String querry = "DELETE FROM `puntosdeventa_dtabase`.`supliers` WHERE (`id_Employees` = '"+id+"')";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "error al borrar el proveedor");
            return false;
        }
    }
    
}

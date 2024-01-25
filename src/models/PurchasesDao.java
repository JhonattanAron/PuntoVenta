/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author aronc
 */
public class PurchasesDao {

    //instaciar la conexion
    Conection_MySQL cn = new Conection_MySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //registrar compra 
    public boolean registerPurchasesQuery(int supliers_id, int employee_id, double total) {
        String query = "INSERT INTO purchases(suplier_id,employed_id,total,created)"
                + "VALUES (?,?,?,?)";
        Timestamp datetimes = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, supliers_id);
            pst.setInt(2, employee_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, datetimes);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al insertar la compra: " + e);
            return false;
        }
    }

    //registrar detalles de la compra
    public boolean registerPurchasesDetailsQuery(int purchase_id, double purchase_price, int purchase_amount,
            double purchase_subtotal, int product_id) {

        String query = "INSERT INTO purchase_details(purchase_id,purchase_price,purchase_amount,"
                + "purchase_subtotal,purchase_date,product_id) VALUES(?,?,?,?,?,?)";
        Timestamp datetimes = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, purchase_id);
            pst.setDouble(2, purchase_price);
            pst.setInt(3, purchase_amount);
            pst.setDouble(4, purchase_subtotal);
            pst.setTimestamp(5, datetimes);
            pst.setInt(6, product_id);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al registrar la compra: " + e);
            return false;
        }

    }

    //OBTENER ID DE LA COMPRA 
    public int purchaseId() {
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM purchases";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    //Listar todas las compras realizadas 
    public List ListAllPurchaseQuery() {
        List<Purchases> list_purchase = new ArrayList();
        String query = "SELECT pu.*,su.name AS supliers_name FROM purchases pu,supliers su WHERE "
                + "pu.suplier_id=su.id ORDER BY pu.id ASC";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Purchases purchase = new Purchases();
                purchase.setId(rs.getInt("id"));
                purchase.setSupliers_name_product(rs.getString("suplier_name"));
                purchase.setTotal(rs.getDouble("total"));
                purchase.setCreated(rs.getString("created"));
                list_purchase.add(purchase);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_purchase;
    }

    //listar compras para imprimir factura
    public List ListPurchaseDetailsQuery(int id) {
        List<Purchases> list_purchases = new ArrayList();
        String query = "SELECT pu.create,pude.purchase_price,pude.purchase_amount,pude.purchase_subtotal,su.name AS suplier_name,\n"
                + "pro.name AS product_name, em.full_name FROM purchases pu INNER JOIN purchase_details pude ON pu.id = pude.purchase_id\n"
                + "INNER JOIN products pro ON pude.product_id = pro.id INNER JOIN supliers su ON pu.suplier_id = su.id\n"
                + "INNER JOIN employees em ON pu.employed_id = em.id_Employees WHERE pu.id = ?";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                Purchases purchase = new Purchases();
                purchase.setProduct_name(rs.getString("product_name"));
                purchase.setPurchase_amount(rs.getInt("purchase_amount"));
                purchase.setPurchase_price(rs.getDouble("purchase_price"));
                purchase.setSupliers_name_product(rs.getString("suplier_name"));
                purchase.setCreated(rs.getString("create"));
                purchase.setPurchaser(rs.getString("full_name"));
                list_purchases.add(purchase);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_purchases;
    }

}

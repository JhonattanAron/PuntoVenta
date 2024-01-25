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
public class CustomersDao {

    //iniciar conexion
    Conection_MySQL cn = new Conection_MySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Registrar Clientes
    public boolean RegisterCustmersQuerry(Customer customer) {
        String querry = "INSERT INTO `puntosdeventa_dtabase`.`customers` (`id`, `full_name`, `adrees`, `telephone`, `email`, `create`, `update`) "
                + "VALUES (?,?,?,?,?,?,?);";

        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.setInt(1, customer.getId());
            pst.setString(2, customer.getFull_name());
            pst.setString(3, customer.getAddres());
            pst.setString(4, customer.getTelephone());
            pst.setString(5, customer.getEmail());
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "error al registrar el Cliente" + e);
            return false;
        }

    }

    public List ListCustomerQuerry(String value) {
        List<Customer> List_customers = new ArrayList();
        String querry = "SELECT * FROM customers";
        String querry_search_customers = "SELECT * FROM customers WHERE id LIKE '%"+value+"%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(querry);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(querry_search_customers);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Customer customer = new Customer();

                customer.setId(rs.getInt("id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setAddres(rs.getString("adrees"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                List_customers.add(customer);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return List_customers;

    }

    //update 
    public boolean UpdateCustmersQuerry(Customer customer) {
        String querry = "UPDATE `puntosdeventa_dtabase`.`customers` SET `full_name` = ?, `adrees` =?, `telephone` =?, `email` =?, `update` =? WHERE (`id` =?);";

        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.setString(1, customer.getFull_name());
            pst.setString(2, customer.getAddres());
            pst.setString(3, customer.getTelephone());
            pst.setString(4, customer.getEmail());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, customer.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "error al modificar los datos del cliente el Cliente" + e.getMessage());
            return false;
        }

    }

    public boolean DeleteCustomersQuerry(int id) {
        String querry = "DELETE FROM customers WHERE is =" + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(querry);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al eliminar al cliente" + e);
            return false;
        }
    }
}

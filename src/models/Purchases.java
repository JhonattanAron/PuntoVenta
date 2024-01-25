/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author aronc
 */
public class Purchases {
    private int id;
    private int code;
    private String product_name;
    private int purchase_amount;
    private double purchase_price;
    private double purchase_subtotal;
    private String created;
    private double total;
    private String supliers_name_product;
    private String purchaser;

    public Purchases() {
    }

    public Purchases(int id, int code, String product_name, int purchase_amount, double purchase_price, double purchase_subtotal, String created, double total, String supliers_name_product, String purchaser) {
        this.id = id;
        this.code = code;
        this.product_name = product_name;
        this.purchase_amount = purchase_amount;
        this.purchase_price = purchase_price;
        this.purchase_subtotal = purchase_subtotal;
        this.created = created;
        this.total = total;
        this.supliers_name_product = supliers_name_product;
        this.purchaser = purchaser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPurchase_amount() {
        return purchase_amount;
    }

    public void setPurchase_amount(int purchase_amount) {
        this.purchase_amount = purchase_amount;
    }

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public double getPurchase_subtotal() {
        return purchase_subtotal;
    }

    public void setPurchase_subtotal(double purchase_subtotal) {
        this.purchase_subtotal = purchase_subtotal;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getSupliers_name_product() {
        return supliers_name_product;
    }

    public void setSupliers_name_product(String supliers_name_product) {
        this.supliers_name_product = supliers_name_product;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }
    
    
    
            
    
}

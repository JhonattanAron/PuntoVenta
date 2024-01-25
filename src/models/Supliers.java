/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author aronc
 */
public class Supliers {
    private int  id;
    private String full_name;
    private String Description;
    private String telephone;
    private String Adrees;   
    private String email;
    private String city;
    private String create;
    private String update;

    public Supliers() {
    }

    public Supliers(int id, String full_name, String Description, String telephone, String Adrees, String email, String city, String create, String update) {
        this.id = id;
        this.full_name = full_name;
        this.Description = Description;
        this.telephone = telephone;
        this.Adrees = Adrees;
        this.email = email;
        this.city = city;
        this.create = create;
        this.update = update;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdrees() {
        return Adrees;
    }

    public void setAdrees(String Adrees) {
        this.Adrees = Adrees;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
    
    
    
}

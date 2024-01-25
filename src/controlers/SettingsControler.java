/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import models.EmployeesDao;
import static models.EmployeesDao.*;
import views.SystemView;

/**
 *
 * @author aronc
 */
public class SettingsControler implements MouseListener {

    private SystemView views;

    public SettingsControler(SystemView views) {
        this.views = views;
        this.views.jLabelProducts.addMouseListener(this);
        this.views.jLabelPurchases.addMouseListener(this);
        this.views.jLabelCategorys.addMouseListener(this);
        this.views.jLabelCustomers.addMouseListener(this);
        this.views.jLabelEmployes.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
        this.views.jLabelSettings.addMouseListener(this);
        this.views.jLabelSuplayers.addMouseListener(this);
        Profile();
    }
    //esignar el perfil del usuario
    public void Profile(){
        this.views.txt_id_profile.setText(""+id_user);
        this.views.txt_fullname_profile.setText(Full_name_user);
        this.views.txt_adrres_profile.setText(email_user);
        this.views.txt_phone_profile.setText(telephone_user);
        this.views.txt_email_profile.setText(email_user);
    }
    
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //definir color de la barra de navegacion izquierda
        if (e.getSource()==views.jLabelProducts) {
            views.jPanelProducts.setBackground(new Color(152,202,63));     
        }else if(e.getSource()==views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color(152,202,63));
        }else if(e.getSource()==views.jLabelEmployes){
            views.jPanelEmployes.setBackground(new Color(152,202,63));
        }else if(e.getSource()==views.jLabelReports){
            views.jPanelReports.setBackground(new Color(152,202,63));
        }else if(e.getSource()==views.jLabelCategorys){
            views.jPanelCategorys.setBackground(new Color(152,202,63));
        }else if(e.getSource()==views.jLabelSuplayers){
            views.jPanelSuplayers.setBackground(new Color(152,202,63));    
        }else if(e.getSource()==views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color(152,202,63));
        }else if(e.getSource()==views.jLabelSettings){
            views.jPanelSettings.setBackground(new Color(152,202,63));  
        }
            
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource()==views.jLabelProducts) {
            views.jPanelProducts.setBackground(new Color(18,45,63));     
        }else if(e.getSource()==views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color(18,45,63));
        }else if(e.getSource()==views.jLabelEmployes){
            views.jPanelEmployes.setBackground(new Color(18,45,63));
        }else if(e.getSource()==views.jLabelReports){
            views.jPanelReports.setBackground(new Color(18,45,63));
        }else if(e.getSource()==views.jLabelCategorys){
            views.jPanelCategorys.setBackground(new Color(18,45,63));
        }else if(e.getSource()==views.jLabelSuplayers){
            views.jPanelSuplayers.setBackground(new Color(18,45,63));    
        }else if(e.getSource()==views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color(18,45,63));
        }else if(e.getSource()==views.jLabelSettings){
            views.jPanelSettings.setBackground(new Color(18,45,63));  
        }
    }
    
}

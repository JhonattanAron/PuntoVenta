/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static models.EmployeesDao.rol_user;
import models.Supliers;
import models.SupliersDao;
import views.SystemView;

/**
 *
 * @author aronc
 */
public class SuppliersController implements ActionListener, KeyListener, MouseListener {

    private Supliers suplier;
    private SupliersDao supliersDao;
    private SystemView view;
    DefaultTableModel model = new DefaultTableModel();

    String rol = rol_user;

    public SuppliersController(Supliers suplier, SupliersDao supliersDao, SystemView view) {
        this.suplier = suplier;
        this.supliersDao = supliersDao;
        this.view = view;

        //boton de registrar provedor
        this.view.btn_register_suppller.addActionListener(this);
        //boton de modificar provedores
        this.view.btn_update_suppller.addActionListener(this);
        //boton eliminar
        this.view.btn_delete_suppller.addActionListener(this);

        //buscar PROVEDORES
        this.view.txt_search_suppller.addKeyListener(this);
        this.view.suppller_table.addMouseListener(this);
        
        this.view.jLabelSuplayers.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.btn_register_suppller) {
            if (view.txt_suppller_name.getText().equals("")
                    || view.txt_suppller_description.getText().equals("")
                    || view.txt_suppller_adrees.getText().equals("")
                    || view.txt_suppller_telephone.getText().equals("")
                    || view.txt_suppller_email.getText().equals("")
                    || view.cmb_suppller_city.getSelectedItem().toString().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");

            } else {
                //realizar insercion
                suplier.setFull_name(view.txt_suppller_name.getText().trim());
                suplier.setDescription(view.txt_suppller_description.getText().trim());
                suplier.setAdrees(view.txt_suppller_adrees.getText().trim());
                suplier.setTelephone(view.txt_suppller_telephone.getText().trim());
                suplier.setEmail(view.txt_suppller_email.getText().trim());
                suplier.setCity(view.cmb_suppller_city.getSelectedItem().toString());

                if (supliersDao.RegisterSupliersQuerry(suplier)) {
                    JOptionPane.showMessageDialog(null, "Provedor registrado con exito");
                    CleanField();
                    CleanTable();
                    listAllSuppliers();

                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error :c");
                }

            }

        } else if (e.getSource() == view.btn_update_suppller) {
            if (view.txt_suppller_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Seleciona fila para continuar");
            } else {

                if (view.txt_suppller_name.getText().equals("")
                        || view.txt_suppller_adrees.getText().equals("")
                        || view.txt_suppller_telephone.getText().equals("")
                        || view.txt_suppller_email.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    suplier.setFull_name(view.txt_suppller_name.getText().trim());
                    suplier.setDescription(view.txt_suppller_description.getText().trim());
                    suplier.setAdrees(view.txt_suppller_adrees.getText().trim());
                    suplier.setTelephone(view.txt_suppller_telephone.getText().trim());
                    suplier.setEmail(view.txt_suppller_email.getText().trim());
                    suplier.setCity(view.cmb_suppller_city.getSelectedItem().toString());
                    suplier.setId(Integer.parseInt(view.txt_suppller_id.getText().trim()));

                    if (supliersDao.UpdateSupliersQuerry(suplier)) {
                        CleanTable();
                        CleanField();
                        listAllSuppliers();
                        JOptionPane.showMessageDialog(null, "Datos del provedor modificadps con exito");
                        view.btn_register_suppller.setEnabled(true);

                    } else {
                        JOptionPane.showMessageDialog(null, "Error al modificar");
                    }

                }

            }
        }else if (e.getSource()==view.btn_delete_suppller) {
            int row = view.suppller_table.getSelectedRow();
            
            if (row==-1) {
                JOptionPane.showMessageDialog(null,"Seleciona una fila para continuar");
                
            }else{
                int id = Integer.parseInt(view.suppller_table.getValueAt(row,1).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar el Provedor?");
                
                if(question == JOptionPane.YES_OPTION && supliersDao.deleteSuplierQuerry(id) != false){
                    CleanField();
                    view.btn_register_suppller.setEnabled(true);
                    CleanTable();
                    CleanField();
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null,"Provedor eliminado con exito");
                
                }
                
            }
            
        }else if (e.getSource()==view.btn_cancel_suppller) {
            CleanField();
            view.btn_register_suppller.setEnabled(true);
            
        }
    }

    public void listAllSuppliers() {
        List<Supliers> listpro = supliersDao.ListsupliersQuerry(view.txt_search_suppller.getText());
        model = (DefaultTableModel) view.suppller_table.getModel();
        Object[] row = new Object[8];
        for (int i = 0; i < listpro.size(); i++) {
            row[0] = listpro.get(i).getId();
            row[1] = listpro.get(i).getFull_name();
            row[2] = listpro.get(i).getTelephone();
            row[3] = listpro.get(i).getDescription();
            row[4] = listpro.get(i).getAdrees();
            row[5] = listpro.get(i).getTelephone();
            row[6] = listpro.get(i).getEmail();
            row[7] = listpro.get(i).getCity();
            model.addRow(row);
        }
    }

    public void CleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;

        }
    }

    public void CleanField() {
        view.txt_suppller_id.setText("");
        view.txt_suppller_id.setEditable(true);
        view.txt_suppller_name.setText("");
        view.txt_suppller_description.setText("");
        view.txt_suppller_adrees.setText("");
        view.txt_suppller_telephone.setText("");
        view.txt_suppller_email.setText("");
        view.cmb_suppller_city.setSelectedIndex(0);

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == view.txt_search_suppller) {
            CleanTable();
            listAllSuppliers();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == view.suppller_table) {
            int row = view.suppller_table.rowAtPoint(e.getPoint());

            view.txt_suppller_id.setText(view.suppller_table.getValueAt(row, 0).toString());
            view.txt_suppller_name.setText(view.suppller_table.getValueAt(row, 1).toString());
            view.txt_suppller_description.setText(view.suppller_table.getValueAt(row, 2).toString());
            view.txt_suppller_adrees.setText(view.suppller_table.getValueAt(row, 3).toString());
            view.txt_suppller_telephone.setText(view.suppller_table.getValueAt(row, 4).toString());
            view.txt_suppller_email.setText(view.suppller_table.getValueAt(row, 5).toString());
            view.cmb_suppller_city.setSelectedItem(view.suppller_table.getValueAt(row, 6).toString());

            //desabilitar botnoes
            view.btn_register_suppller.setEnabled(false);
            view.txt_suppller_id.setEditable(false);
        }else if (e.getSource()== view.jLabelSuplayers) {
            if (rol.equals("Administrador")) {
                view.jTabbedPane1.setSelectedIndex(4);
                
                CleanTable();
                CleanField();
                listAllSuppliers();
                
            }else{
               view.jTabbedPane1.setEnabledAt(4,false);
               view.jLabelSuplayers.setEnabled(false);
               JOptionPane.showMessageDialog(null, "No tienes permisos de Administrador");
            }
            
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}

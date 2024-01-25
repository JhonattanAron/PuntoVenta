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
import models.Customer;
import models.CustomersDao;
import views.SystemView;

/**
 *
 * @author aronc
 */
public class CustomerContoller implements ActionListener,MouseListener,KeyListener {

    private Customer customer;
    private CustomersDao customerDao;
    private SystemView view;
    DefaultTableModel model = new DefaultTableModel();

    public CustomerContoller(Customer customer, CustomersDao customerDao, SystemView view) {
        this.customer = customer;
        this.customerDao = customerDao;
        this.view = view;

        //boton de registar cliente
        this.view.btn_register_customers.addActionListener(this);
        //boton modificar cliente
        this.view.btn_update_customers.addActionListener(this);
        //boton eliminar cliente
        this.view.btn_delete_customers.addActionListener(this);
        //cancelar
        this.view.btn_cancel_customers.addActionListener(this);
        //buscador
        this.view.txt_search_customer.addKeyListener(this);
        this.view.customers_table.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //registrar el cliente
        if (e.getSource() == view.btn_register_customers) {
            //verificar campos vacios
            if (view.txt_customers_id.getText().equals("") || view.txt_customers_fullname.getText().equals("")
                    || view.txt_customers_adrees.getText().equals("") || view.txt_customers_mail.getText().equals("")
                    || view.txt_customers_telephone.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");

            } else {
                customer.setId(Integer.parseInt(view.txt_customers_id.getText().trim()));
                customer.setFull_name(view.txt_customers_fullname.getText().trim());
                customer.setAddres(view.txt_customers_adrees.getText().trim());
                customer.setEmail(view.txt_customers_mail.getText().trim());
                customer.setTelephone(view.txt_customers_telephone.getText().trim());

                if (customerDao.RegisterCustmersQuerry(customer)) {
                    JOptionPane.showMessageDialog(null, "Empleado registrado con exito");
                    CleanTable();
                    listAllCustomers();
                    CleanField();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
                }

            }

        } else if (e.getSource() == view.btn_update_customers) {
            if (view.txt_customers_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Selecione una fila para continuar");
            } else {
                //verificar si los campos estan vacios
                if (view.txt_customers_fullname.equals("")
                        || view.txt_customers_mail.equals("")
                        || view.txt_customers_telephone.equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                    //realizar inserccion    
                } else {
                    customer.setId(Integer.parseInt(view.txt_customers_id.getText().trim()));
                    customer.setFull_name(view.txt_customers_fullname.getText().trim());
                    customer.setAddres(view.txt_customers_adrees.getText().trim());
                    customer.setTelephone(view.txt_customers_telephone.getText().trim());
                    customer.setEmail(view.txt_customers_mail.getText().trim());

                    if (customerDao.UpdateCustmersQuerry(customer)) {
                        view.btn_register_customers.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos del Cliente modificados con exito");
                        CleanTable();
                        listAllCustomers();
                        CleanField();
                    } else {
                        JOptionPane.showMessageDialog(null, "Algo a ido mal :c");
                    }
                }
            }
        }else if (e.getSource()==view.btn_delete_customers) {
            int row = view.customers_table.getSelectedRow();
            
            if (row==-1) {
                JOptionPane.showMessageDialog(null,"Debe seleccionar un Cliente para eliminarlo");
                
            }else{
                int id = Integer.parseInt(view.customers_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad desea eliminar el Cliente");
                
                if (question==JOptionPane.YES_OPTION && customerDao.DeleteCustomersQuerry(id) != false) {
                    CleanTable();
                    view.btn_register_customers.setEnabled(true);
                    listAllCustomers();
                    JOptionPane.showMessageDialog(null, "Cliente Eliminado con exito");
                    CleanField();
                }
            }
            
        }else if (e.getSource()==view.btn_cancel_customers) {
            view.btn_register_customers.setEnabled(true);
            CleanField();
            
            
        }
    }

    //listar Empleados
    public void listAllCustomers() {
        List<Customer> listcus = customerDao.ListCustomerQuerry(view.txt_search_customer.getText());
        model = (DefaultTableModel) view.customers_table.getModel();
        Object[] row = new Object[5];
        for (int i = 0; i < listcus.size(); i++) {
            row[0] = listcus.get(i).getId();
            row[1] = listcus.get(i).getFull_name();
            row[2] = listcus.get(i).getTelephone();
            row[3] = listcus.get(i).getAddres();
            row[4] = listcus.get(i).getEmail();
            model.addRow(row);
        }
    }

    //limpiar tabla
    public void CleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource()== view.customers_table) {
            int row = view.customers_table.rowAtPoint(e.getPoint());
            view.txt_customers_id.setText(view.customers_table.getValueAt(row, 0).toString());
            view.txt_customers_fullname.setText(view.customers_table.getValueAt(row, 1).toString());
            view.txt_customers_adrees.setText(view.customers_table.getValueAt(row, 2).toString());
            view.txt_customers_telephone.setText(view.customers_table.getValueAt(row,3).toString());
            view.txt_customers_mail.setText(view.customers_table.getValueAt(row,4).toString());
            
            //DESABILITAR BOTNOS
            view.btn_register_category.setEnabled(false);
            view.txt_customers_id.setEditable(false);
            
            
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource()==view.txt_search_customer) {
            CleanTable();
            listAllCustomers();
            CleanField();
            
            
            
        }
        
    }
    public void CleanField(){
        view.txt_customers_id.setEditable(true);
        view.txt_customers_id.setText("");
        view.txt_customers_fullname.setText("");
        view.txt_customers_adrees.setText("");
        view.txt_customers_telephone.setText("");
        view.txt_customers_mail.setText("");
    }

}

package controlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Employees;
import models.EmployeesDao;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.rol_user;
import views.SystemView;

/**
 *
 * @author aronc
 */
public class EmployeesControler implements ActionListener, MouseListener, KeyListener {

    private Employees employees;
    private EmployeesDao employesDao;
    private SystemView views;
    //rol
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public EmployeesControler(Employees employees, EmployeesDao employesDao, SystemView views) {
        this.employees = employees;
        this.employesDao = employesDao;
        this.views = views;
        //boton registrar empleados
        this.views.btn_register_employed.addActionListener(this);
        //boton registrar empleados
        this.views.btn_update_employed.addActionListener(this);
        //boton de eliminar empleado
        this.views.btn_delete_employed.addActionListener(this);
        //boton de cancelar
        this.views.btn_cancel_employed.addActionListener(this);
        //boton de cambiar contrasena
        this.views.btn_modify_data.addActionListener(this);
        //colocar label en escucha
        this.views.jLabelEmployes.addMouseListener(this);

        this.views.emplyed_table.addMouseListener(this);
        this.views.txt_search_employed.addKeyListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_employed) {
            //verificar si los campos estan vacion
            if (views.txt_employed_id.getText().equals("")
                    || views.txt_employed_fullname.getText().equals("")
                    || views.txt_employed_username.getText().equals("")
                    || views.txt_employed_adrees.getText().equals("")
                    || views.txt_employed_email.getText().equals("")
                    || views.cmb_employed_rol.getSelectedItem().toString().equals("")
                    || String.valueOf(views.txt_employed_password.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "todos los campos son obligatorios");
            } else if (views.txt_employed_id.getText().length() > 10) {

                JOptionPane.showMessageDialog(null, "Ingrese un numero de cedula valido");

            } else if (!views.txt_employed_id.getText().matches("[0-9]+")) {
                JOptionPane.showMessageDialog(null, "Ingrese un número de cédula válido sin letras ni otros caracteres");

            } else {
                //realizar insercion
                employees.setId(Integer.parseInt(views.txt_employed_id.getText().trim()));
                employees.setFullname(views.txt_employed_fullname.getText().trim());
                employees.setUsername(views.txt_employed_username.getText().trim());
                employees.setAddres(views.txt_employed_adrees.getText().trim());
                employees.setTelephone(views.txt_employed_telephone.getText().trim());
                employees.setEmail(views.txt_employed_email.getText().trim());
                employees.setPassword(String.valueOf(views.txt_employed_password.getPassword()));
                employees.setRol(views.cmb_employed_rol.getSelectedItem().toString());

                if (employesDao.registerEmployeeQuerry(employees)) {
                    CleanTable();
                    CleanField();
                    ListAllEmployees();
                    JOptionPane.showMessageDialog(null, "empleado registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "error al registrar");
                }
            }

        } else if (e.getSource() == views.btn_update_employed) {
            if (views.txt_employed_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Seleciona fila para continuar");
            } else {
                //verificar si los campos estan vacios
                if (views.txt_employed_id.getText().equals("")
                        || views.txt_employed_fullname.getText().equals("")
                        || views.cmb_employed_rol.getSelectedItem().toString().equals("")) {
                    JOptionPane.showMessageDialog(null, "TODOS LOS CAMPOS SON OBLIGATORIOS");

                } else {
                    //realizar insercion
                    employees.setId(Integer.parseInt(views.txt_employed_id.getText().trim()));
                    employees.setFullname(views.txt_employed_fullname.getText().trim());
                    employees.setUsername(views.txt_employed_username.getText().trim());
                    employees.setAddres(views.txt_employed_adrees.getText().trim());
                    employees.setTelephone(views.txt_employed_telephone.getText().trim());
                    employees.setEmail(views.txt_employed_email.getText().trim());
                    employees.setPassword(String.valueOf(views.txt_employed_password.getPassword()));
                    employees.setRol(views.cmb_employed_rol.getSelectedItem().toString());

                    if (employesDao.updateEmployeeQuerry(employees)) {
                        CleanTable();
                        CleanField();
                        ListAllEmployees();
                        views.btn_register_employed.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "DATOS DEL EMPLEADO MODIFICADOS CON EXITO");
                    } else {
                        JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR LOS DATOS DEL EMPLEADO");
                    }

                }
            }
        } else if (e.getSource() == views.btn_delete_employed) {
            int row = views.emplyed_table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un empleado para eliminar");
            } else if (views.emplyed_table.getValueAt(row, 0).equals(id_user)) {
                JOptionPane.showMessageDialog(null, "no puede selecionar el usuario autenticado");

            } else {
                int id = Integer.parseInt(views.emplyed_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar el empleado");

                if (question == JOptionPane.YES_OPTION && employesDao.deleteEmployeeQuerry(id) != false) {
                    CleanField();
                    CleanTable();
                    views.btn_register_employed.setEnabled(true);
                    views.txt_employed_password.setEnabled(true);
                    ListAllEmployees();
                    JOptionPane.showMessageDialog(null, "Empleado eliminado con exito");

                }

            }
        } else if (e.getSource() == views.btn_cancel_employed) {
            CleanField();
            views.btn_register_employed.setEnabled(true);
            views.txt_employed_password.setEnabled(true);
            views.txt_employed_id.setEnabled(true);
        } else if (e.getSource() == views.btn_modify_data) {
            //recolectar informacion de las cajas password
            String password = String.valueOf(views.txt_password_modify.getPassword());
            String confirmPassword = String.valueOf(views.txt_password_modify_confirm.getPassword());
            //verificar que las contrasenas estran vacias
            if (password.equals("") || !confirmPassword.equals("")) {
                //verificar que las contrasenas sean iguales
                if (password.equals(confirmPassword)) {
                    employees.setPassword(String.valueOf(views.txt_password_modify.getPassword()));

                    if (employesDao.updateEmployeesPassword(employees) != false) {
                        JOptionPane.showMessageDialog(null, "Contraseña modificada con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "error al modificar");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "las Contrase no coinciden");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }
        }
    }

    //listar empleados
    public void ListAllEmployees() {
        if (rol.equals("Administrador")) {
            List<Employees> list = employesDao.listEmployeeQuerry(views.txt_search_employed.getText());
            model = (DefaultTableModel) views.emplyed_table.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getFullname();
                row[2] = list.get(i).getUsername();
                row[3] = list.get(i).getAddres();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getRol();
                model.addRow(row);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.emplyed_table) {
            int row = views.emplyed_table.rowAtPoint(e.getPoint());

            views.txt_employed_id.setText(views.emplyed_table.getValueAt(row, 0).toString());
            views.txt_employed_fullname.setText(views.emplyed_table.getValueAt(row, 1).toString());
            views.txt_employed_username.setText(views.emplyed_table.getValueAt(row, 2).toString());
            views.txt_employed_adrees.setText(views.emplyed_table.getValueAt(row, 3).toString());
            views.txt_employed_telephone.setText(views.emplyed_table.getValueAt(row, 4).toString());
            views.txt_employed_email.setText(views.emplyed_table.getValueAt(row, 5).toString());
            views.cmb_employed_rol.setSelectedItem(views.emplyed_table.getValueAt(row, 6).toString());

            //desabilitar
            views.txt_employed_id.setEditable(false);
            views.txt_employed_password.setEnabled(false);
            views.btn_register_employed.setEnabled(false);
        } else if (e.getSource() == views.jLabelEmployes) {
            if (rol.equals("Administrador")) {
                views.jTabbedPane1.setSelectedIndex(3);
                //limpiar tabla
                CleanTable();
                //limpiar campos
                CleanField();
                //listar empleaods
                ListAllEmployees();

            } else {
                views.jTabbedPane1.setEnabledAt(3, false);
                views.jLabelEmployes.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes permiso de Administrador");
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_employed) {
            CleanTable();
            ListAllEmployees();
        }
    }

    public void CleanField() {
        views.txt_employed_id.setText("");
        views.txt_employed_id.setEditable(true);
        views.txt_employed_fullname.setText("");
        views.txt_employed_username.setText("");
        views.txt_employed_adrees.setText("");
        views.txt_employed_telephone.setText("");
        views.txt_employed_email.setText("");
        views.txt_employed_password.setEnabled(true);
        views.txt_employed_password.setText("");
        views.cmb_employed_rol.setSelectedIndex(0);
    }

    public void CleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;

        }
    }

}

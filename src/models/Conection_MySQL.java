
package models;
import  java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conection_MySQL {
    private String database_name ="puntosdeventa_dtabase";
    private String user = "root";
    private String password = "123456";
    private String url= "jdbc:mysql://localhost:3306/"+database_name;
    Connection conn = null;
    
    public Connection getConnection(){
        try {
            //obtener valor del driver
            Class .forName("com.mysql.cj.jdbc.Driver");
            //obtener la conexion
            conn = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            System.err.println("a ocurrido un ClassNotFoundException: "+e.getMessage());
        }catch(SQLException e){
            System.err.println("a ocurrido un sql exepction"+e.getMessage());
        }
        return conn;
        
    }
    
    
}

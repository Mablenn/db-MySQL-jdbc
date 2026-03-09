/*
 * Esta clase es un ejemplo sencillo de como establecer una conexión
 * a una base de datos MySQL y actualizar registros de una tabla.
 * 
 * Clases a destacar:
 * 1 - Class.forName("com.mysql.jdbc.Driver"); Carga el driver en memoria antes de establecer la conexión
 * 2 - DriverManager: Establece la conexión a través de la URL que se le pasa 
 * como parámetro a su método 'getConnection' 
 * 3 - Connection: (Interfaz) Cualquier conexión realizada a la base de datos implementan esta interfaz
 * Toda transferencia de datos se realiza a través de un objeto Connection
 * 4 - PreparedStatement: (Interfaz) El objeto PreparedStatement almacena la sentencia SQL para después 
 * ejecutarla las veces que sea necesario utilizando los métodos 'execute()' o 'executeQuery()' del mismo modo
 * que se realizan con un objeto 'Statement'.
 * 
 * Excepciones:
 * ClassNotFoundException -> lanzada por el método 'forName()'
 * El resto de excepciones a capturar serán del tipo SQLException
 */

package ejemplos01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Ejemplo07_actualizar {
   
    public static void main (String args[]){
        Connection cn = null;
        PreparedStatement pst = null;
        try{
            //Carga del driver en momoria
            Class.forName("com.mysql.jdbc.Driver");
            //Crear un objeto Connection y le asignamos el valor 
            //del método getConnection de la clase DriverManager
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejemploDB", "root", "rootpwd");
            if(cn!=null){
               System.out.println("Conexión establecida con la base de datos");
            }
            //En lugar de realizar siempre la misma consulta de actualización pasando los parámetros
            //del precio_producto y el proveedor, preparamos una consulta donde solo cambiará el nombre
            //del proveedor
            String SQL ="UPDATE producto SET precio_unidad=precio_unidad*1.03 WHERE proveedor=?";
            //Crear objeto PreparedStatement para enviar consultas preparadas
            //Preparar la consulta precompilada
            pst = cn.prepareStatement(SQL);
            //Asignar valor que será el código de un proveedor. De este modo utilizamos 
            //una consulta precompilada donde solo debemos
            //indicar los campos de la tabla donde se realiza la actualización de datos.
            pst.setString(1, "PV-1");
            //Ejecutar sentencia. Como la consulta no devuelve ningún resultado, utilizamos el método 'execute()'
            pst.execute(SQL);
            System.out.println("Registros actualizados en la tabla");
            pst.close();
            cn.close();
        } catch (SQLException e){
           System.out.println("Error en la consexion con la base de datos " + e.getMessage()); 
        } catch (ClassNotFoundException e){
            System.out.println("Error en la carga del driver: " + e.getMessage());
        }//fin try-catch
    } //Fin main
}

/*
    Pasos:
    1 - Cargar el driver en memoria
    2 - Establecer conexión
    3 - Preparar la consulta
    4 - Ejecutar la consulta
    6 - Cerrar Preparedstatement y conexión
*/

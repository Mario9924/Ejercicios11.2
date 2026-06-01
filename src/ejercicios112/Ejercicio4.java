package ejercicios112;

import java.sql.*;

/**
 *
 * A partir de la BD con datos climatológicos se pide:
 * 
 * - La ciudad con la temperatura media anual más elevada. 
 * - El mes y ciudad con temperatura máxima mayor.
 * - El mes y ciudad con temperatura mínima mayor.
 * - Las ciudades correspondientes a las 5 temperaturas mínimas mensuales más bajas (pueden repetirse). 
 * - El mes y ciudad con una humedad relativa menor. 
 * - Las 5 ciudades que tienen meses con una humedad relativa mayor (eliminar duplicidad de ciudades si hay varios meses para una misma). 
 * - La ciudad con una más días anuales de helada. 
 * - Las 3 ciudades con más horas de sol anuales. 
 * - Al final del fichero, pinta todos los meses de Valladolid con su Tmed, TMax, Tmin.
 * 
 * @author Mario Gutiérrez
 * @see https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY3MTEwMzc0/details
 * @version 1.0
 */
public class Ejercicio4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        String url = "jdbc:mysql://localhost:3307/datos_climaticos";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);//
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);//
                ResultSet rs = stmt.executeQuery("Select * from medias"); // USAR PARA CONSULTAS
                
            ){
            
            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            /*
                Si lo que queremos es realizar sentencias DDL: Create - Drop - Alter - etc
                 tenemos que usar 
                stmt.executeUpdate("SENTENCIA DDL") ya que no devuelve un result set, solamente el número de registros modificados
            */
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}

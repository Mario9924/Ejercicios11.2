package ejercicios112;

import java.sql.*;

/**
 * 3- Cargar el script de ciudades del mundo. A partir de la información,
 * muestra:
 *
 * - Listado de todas las lenguas y número de ellas. 
 * - Listado de lenguas que se hablan en España.
 * - Listado de los países donde se habla español, con el porcentaje de uso del país. 
 * - Listado de lenguas NO oficiales de Andorra. 
 * - País con menos extensión y país con más extensión del mundo. 
 * - País con más población de Oceanía.
 * - Listado de países que empiezan por la letra B. 
 * - Lugar que ocupa Valladolid en cuanto a población de España. 
 * - La población de  la capital de Camerún.
 *
 * Al final, introduce en el listado de ciudades tu pueblo/ciudad (si eres de
 * Valladolid, pon otra que no esté en la lista).
 * 
 * 
 * @author Mario Gutiérrez
 * @see https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY3MTEwMzc0/details
 * @version 1.0
 */
public class Ejercicio3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        String url = "jdbc:mysql://localhost:3307/world";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("select * from countrylanguage"); // USAR PARA CONSULTAS
                
            ){
            
            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            while(rs.next()){
            
            }
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}

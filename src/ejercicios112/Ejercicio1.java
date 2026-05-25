package ejercicios112;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Mediante el uso de la BD nba vamos a mostrar al usuario una serie de opciones
 * a. Quién es el jugador o jugadores más alto de la NBA. 
 * b. Quién es el jugador o jugadores más bajo de la NBA. 
 * c. Quienes son los jugadores de los Lakers.
 * d. Quién es el jugador con más puntos por partido (obvio que es un error). 
 * e. Un listado con los nombres cuyo apellido empieza por J. 
 * f. Número de jugadores españoles y listado de sus nombres y equipos. 
 * g. El número de puntos más alto de cualquier equipo en un partido (ya sea local o visitante).
 * h. El único partido donde tanto locales como visitantes hicieron el número de
 * puntos más alto (el obtenido antes). Obvio que esto es un error de la BD, un
 * partido de baloncesto no puede terminar empatado.
 * 
 * 
 * @author Mario Gutiérrez
 * @see https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY3MTEwMzc0/details
 * @version 1.0
 */
public class Ejercicio1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        Scanner reader = new Scanner(System.in);
        int opcionMenuIn = 0;
        double estaturaMediaJugadores = 0.0;
        int numeroJugadores = 0;
        String url = "jdbc:mysql://localhost:3307/nba";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery("Select * from Jugadores"); // USAR PARA CONSULTAS
                
            ){
            
            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            /*
                Para los apartados A y B calculamos la altura media y luego mostramos la información
                 que corresponda en cada caso.
            
                La altura está almacenada en el formato String "6-9", lo "convertimos en double" tras 
                    reemplazar el caracter '-' por '.' de modo que quedará cómo "6.9"
            */
            
            rs.beforeFirst();
            
            while(rs.next()){
                estaturaMediaJugadores  = Double.parseDouble(rs.getString("Altura").replace("-", "."));
                numeroJugadores++;
            }
            
            estaturaMediaJugadores = estaturaMediaJugadores / numeroJugadores;
            
            // A - Jugadores más altos
            rs.beforeFirst();
            while(rs.next()){
                if ( Double.parseDouble(rs.getString("Altura").replace("-", ".")) > estaturaMediaJugadores){
                    System.out.println("Nombre: " + rs.getString("Nombre") + ". Altura " + rs.getString("Altura").replace("-", "."));
                }
            }
            
            // B - Jugadores más bajos
            rs.beforeFirst();
            while(rs.next()){
                if ( Double.parseDouble(rs.getString("Altura").replace("-", ".")) < estaturaMediaJugadores){
                    System.out.println("Nombre: " + rs.getString("Nombre") + ". Altura " + rs.getString("Altura").replace("-", "."));
                }
            }
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}

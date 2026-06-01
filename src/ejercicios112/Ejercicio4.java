package ejercicios112;

import java.sql.*;
import java.util.HashMap;

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
        HashMap<String, Double> temperaturaMediaAnual = new HashMap<>();
        String ciudadTempMediaAnualMax = "";
        double tempMediaAnualMax = 0.0;
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);//
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);//
                ResultSet rs = stmt.executeQuery("Select * from medias"); // USAR PARA CONSULTAS
                
            ){
            
            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // - La ciudad con la temperatura media anual más elevada.
            
            while (rs.next()){
                // Guardamos en el hashmap                
                String ciudad = rs.getString("ciudad");
                double tempMedia = rs.getDouble("Tmed");
                
                // Comprobamos que exista
                if (temperaturaMediaAnual.containsKey(ciudad)){
                    // Comprobamos que la tempMedia sea superior a la guardada y actualizamos si se cumple
                    if (tempMedia > temperaturaMediaAnual.get(ciudad)){
                        temperaturaMediaAnual.replace(ciudad, tempMedia); // Actualizamos la información
                    }
                } else {
                    temperaturaMediaAnual.put(ciudad, tempMedia);
                }
            }
            
            // Recorremos los resultados anteriores para obtener la ciudad con mayor temperatura media Anual
            for (String key : temperaturaMediaAnual.keySet()){                
                if (temperaturaMediaAnual.get(key) > tempMediaAnualMax){
                    tempMediaAnualMax = temperaturaMediaAnual.get(key);
                    ciudadTempMediaAnualMax = key;
                }
            }
            System.out.println("Ciudad: " + ciudadTempMediaAnualMax  + " temperatura: " + tempMediaAnualMax);
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}

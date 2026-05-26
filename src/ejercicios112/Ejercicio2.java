package ejercicios112;

import java.io.IOException;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Esta aplicación permite reproducir un vídeo desde una url guardada en en una
 * base de datos. Al usuario le damos dos opciones: Reproducir canción Añadir
 * canción
 *
 * @author Mario Gutiérrez
 * @see
 * https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY3MTEwMzc0/details
 * @version 1.0
 */
public class Ejercicio2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        Scanner reader = new Scanner(System.in);
        int opcionMenuIn = 0;
        String nombreCancionIn = "";
        String urlCancionIn = "";
        boolean continuar = true;
        int numeroCanciones = 1;
        int cancionElegidaIn = 0;
        String url = "jdbc:mysql://localhost:3307/ejercicio112";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass); 
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); 
                
                ) {

            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            while (continuar) {
                /*
                    Si no ejecutamos al comienzo el select de forma continua, sólo devolverá los valores que encuentre al inicio del
                     programa. Aunque la conexión tenga concurrencia "Updatable" al no lanzar más veces la consulta queda "atascado"
                     en el estado inicial al comienzo del programa
                */
                ResultSet rs = stmt.executeQuery("select * from canciones");
                try {
                    System.out.println("Bienvenido al reproductor de música. Elige una opción"
                            + "\n1 - Reproducir cancion"
                            + "\n2 - Añadir canción"
                            + "\nOtro - salir de la aplicación");
                    opcionMenuIn = reader.nextInt();
                    switch (opcionMenuIn) {
                        case 1:
                            do {
                                rs.beforeFirst(); // Para volver al comienzo de la tabla, sino, empieza a contar desde la última canción elegida
                                numeroCanciones = 1; // Comenzamos también de nuevo en 1
                                /*
                                    Se calcula de forma continua puesto que se permite añadir canciones nuevas
                                 */
                                System.out.println("Elige una canción por favor");
                                while (rs.next()) {
                                    System.out.println(numeroCanciones + " - " + rs.getString("Nombre"));
                                    numeroCanciones++;
                                }
                                cancionElegidaIn = reader.nextInt();
                            } while (cancionElegidaIn <= 0 || cancionElegidaIn > numeroCanciones);

                            // Nos movemos a la fila correspondiente
                            rs.absolute(cancionElegidaIn);
                            System.out.println("Has elegido la canción: " + rs.getString("Nombre"));

                            // Una vez elegido el nombre de la canción, lanzamos el proceso correspondiente
                            try {
                                // TODO code application logic here
                                ProcessBuilder pb = new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", rs.getString("url"));
                                pb.start();
                            } catch (IOException ioex) {
                                System.err.println("Error al reproducir la canción: " + ioex);
                            }
                            break;
                        case 2:
                            reader.nextLine();
                            System.out.println("Para introducir los datos de la canción necesitamos que nos indiques:"
                                    + "\nTitulo de la canción:");
                            nombreCancionIn = reader.nextLine();
                            System.out.println("Url de la canción");
                            urlCancionIn = reader.nextLine();

                            // Realizamos el prepared statement y lo lanzamos
                            try (
                                    PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `canciones` "
                                            + "(`nombre`, `url`) VALUES ( ?, ?);");) {
                                pstmt.setString(1, nombreCancionIn);
                                pstmt.setString(2, urlCancionIn);
                                pstmt.executeUpdate();
                            } catch (SQLException sqlex) {
                                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
                            } catch (Exception e) {
                                System.out.println("Error: " + e);
                            }

                            break;
                        default:
                            System.out.println("Hasta luego!!");
                            continuar = false;
                            rs.close();
                            break;
                    }
                } catch (InputMismatchException ime) {
                    System.err.println("El tipo de dato introducido no es correcto: " + ime);
                    reader.nextLine();
                } catch (Exception e) {
                    System.out.println("ERROR INESPERADO: " + e);
                }
            }

        } catch (SQLException sqlex) {
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

}

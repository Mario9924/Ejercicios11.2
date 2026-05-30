package ejercicios112;

import com.mysql.cj.jdbc.result.ResultSetFactory;
import java.sql.*;
import java.util.HashMap;

/**
 * 3- Cargar el script de ciudades del mundo. A partir de la información,
 * muestra:
 *
 * - Listado de todas las lenguas y número de ellas. - Listado de lenguas que se
 * hablan en España. - Listado de los países donde se habla español, con el
 * porcentaje de uso del país. - Listado de lenguas NO oficiales de Andorra. -
 * País con menos extensión y país con más extensión del mundo. - País con más
 * población de Oceanía. - Listado de países que empiezan por la letra B. -
 * Lugar que ocupa Valladolid en cuanto a población de España. - La población de
 * la capital de Camerún.
 *
 * Al final, introduce en el listado de ciudades tu pueblo/ciudad (si eres de
 * Valladolid, pon otra que no esté en la lista).
 *
 *
 * @author Mario Gutiérrez
 * @see
 * https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY3MTEwMzc0/details
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
        HashMap<String, Integer> listadoLenguas = new HashMap<>();
        try (
                Connection conn = DriverManager.getConnection(url, user, pass); 
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
                ResultSet rs = stmt.executeQuery("select * from countrylanguage"); // USAR PARA CONSULTAS
                ) {

            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Listado de lenguas y número de ellas
            /* Se comenta puesto que son muchas líneas
            while(rs.next()){
                String lenguaje = rs.getString("language");
                if (listadoLenguas.containsKey(lenguaje)){
                    listadoLenguas.replace(lenguaje, listadoLenguas.get(lenguaje)+1);
                } else {
                    listadoLenguas.put(lenguaje, 1);
                }
            }
            
            for (String key : listadoLenguas.keySet()){
                System.out.println(key + " - " + listadoLenguas.get(key));
            }
             */
            // Lenguas de españa
            rs.beforeFirst();
            System.out.println("Listado de lenguas habladas en España");
            while (rs.next()) {
                if (rs.getString("countrycode").equalsIgnoreCase("ESP")) {
                    System.out.println(rs.getString("language"));
                }
            }
            /*
            System.out.println("Opción 2: ");
            // Opción 2 - Mediante un nuevo result set a partir de otra consulta distinta a la BD
            try (
                    ResultSet rs2 = stmt.executeQuery("select * from countrylanguage where countrycode = (select code from country where name = 'Spain')");) 
            
            {
                while(rs2.next()){
                    System.out.println(rs2.getString("language"));
                }
            } catch (SQLException sqlex) {
                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            
             */
            //Mostramos los países que hablan español y el porcentaje

            rs.beforeFirst();
            while (rs.next()) {
                if (rs.getString("language").equalsIgnoreCase("spanish")) {
                    try (Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs2 = stmt2.executeQuery("select * from country where code = '" + rs.getString("countrycode") + "'");) {
                        while (rs2.next()) {
                            System.out.print(rs2.getString("name"));
                        }
                    } catch (SQLException sqlex) {
                        System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
                    } catch (Exception e) {
                        System.out.println("Error: " + e);
                    }
                    System.out.print(" - " + rs.getString("percentage") + "%\n");
                }
            }

            // - Listado de lenguas NO oficiales de Andorra.
            try (
                    Statement stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs3 = stmt3.executeQuery("Select code from country where name = 'Andorra'");) {
                String codigoPais = "";
                while (rs3.next()) {
                    codigoPais = rs3.getString("code");
                    System.out.println("El codigo de andorra es: " + codigoPais);
                }

                try (
                        Statement stmt4 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY); ResultSet rs4 = stmt4.executeQuery("select * from countrylanguage where countrycode='" + codigoPais + "';");) {
                    while (rs4.next()) {
                        if (rs4.getString("IsOfficial").equalsIgnoreCase("F")) {
                            System.out.println("Lengua no oficial de Andorra: " + rs4.getString("Language"));
                        }
                    }
                }
            } catch (SQLException sqlex) {
                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }

            // - País con menos extensión y país con más extensión del mundo
            try (
                    Statement stmt5 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs5 = stmt5.executeQuery("Select * from country");) {
                // Comprobamos las extensiones de los países
                double minExtension = 99999999.00;
                String paisMinExtension = "";
                double maxExtension = 0;
                String paisMaxExtension = "";

                while (rs5.next()) {
                    if (rs5.getDouble("SurfaceArea") < minExtension) {
                        minExtension = rs5.getDouble("SurfaceArea");
                        paisMinExtension = rs5.getString("Name");
                    } else if (rs5.getDouble("SurfaceArea") > maxExtension) {
                        maxExtension = rs5.getDouble("SurfaceArea");
                        paisMaxExtension = rs5.getString("Name");
                    }
                }


                System.out.println("El país con menor extensión es: " + paisMinExtension + " con una superfice de " + minExtension
                        + "\nEl país con mayor extensión es: " + paisMaxExtension + " con una superfice de " + maxExtension);
            } catch (SQLException sqlex) {
                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }

            // - País con más población de Oceanía. 18886000 99999999
            try (
                    Statement stmt6 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
                    ResultSet rs6 = stmt6.executeQuery("Select name, population from country where continent='Oceania'");) {
                int maxPoblacionOceania = 0;
                String nombreMaxPoblacionOceania = "";

                while (rs6.next()) {
                    if (rs6.getInt("population") > maxPoblacionOceania) {
                        maxPoblacionOceania = rs6.getInt("population");
                        nombreMaxPoblacionOceania = rs6.getString("name");
                    }
                }

                System.out.println("El país con mayor población de Oceanía es: " + nombreMaxPoblacionOceania + " con " + maxPoblacionOceania + " habitantes");
            }

            // - Listado de países que empiezan por la letra B.
            System.out.println("\nListado de países que empiezan por la letra 'B'\n");
            try (
                    Statement stmt7 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
                    ResultSet rs7 = stmt7.executeQuery("Select * from country");) {
                while (rs7.next()) {
                    if (rs7.getString("Name").toUpperCase().charAt(0) == 'B') {
                        System.out.println(rs7.getString("Name"));
                    }
                }
            } catch (SQLException sqlex) {
                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }

            // - Lugar que ocupa Valladolid en cuanto a población de España.
            System.out.println("\nPosición que ocupa Valladolid en las ciudades Españolas");
            try (
                    Statement stmt8 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs8 = stmt8.executeQuery("Select * from city WHERE CountryCode='esp'");) 
            {
                int contador = 0;
                while (rs8.next()){
                    contador++;
                    if (rs8.getString("name").toLowerCase().equalsIgnoreCase("Valladolid")){
                        System.out.println("La posición que ocupa Valladolid en el listado es la posición número : " + contador);
                        break;
                    }
                }
            } catch (SQLException sqlex) {
                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }

            
            // - La población de la capital de Camerún.
            System.out.println("\nLa población de la capitual de Camerún es: ");
            try (
                    Statement stmt9 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs9 = stmt9.executeQuery("Select * from country");
                    
                )
            {
                 
                String codCameron = "";
                while (rs9.next()){
                    if (rs9.getString("name").equalsIgnoreCase("cameroon")){
                        codCameron = rs9.getString("code");
                        break;
                    }
                }
                ResultSet rs10 = stmt9.executeQuery("Select * from city");
                while(rs10.next()){
                    if (rs10.getString("CountryCode").equalsIgnoreCase(codCameron) && rs10.getString("name").equalsIgnoreCase("Yaoundé")){
                        System.out.println(rs10.getString("Name") + " - Población de " + rs10.getDouble("Population"));
                    }
                }
                
            } catch (SQLException sqlex) {
                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            
            
            try (
                    PreparedStatement pstmt = conn.prepareStatement("INSERT INTO city "
                            + "(`Name`,`CountryCode`,`District`,`Population`)"
                            + "VALUES(?, ?, ?, ?)");
                )
            {
                pstmt.setString(1,"Medina del campo");
                pstmt.setString(2,"ESP");
                pstmt.setString(3,"Centre");
                pstmt.setDouble(4,Double.parseDouble("20097"));
                pstmt.executeUpdate();
                System.out.println("Se introduce la ciudad de \"Medina del campo\"");
            } catch (SQLException sqlex) {
                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        } catch (SQLException sqlex) {
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

}

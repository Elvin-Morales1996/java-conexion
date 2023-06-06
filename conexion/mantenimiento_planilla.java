package conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;

public class mantenimiento_planilla {

    private static String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static String DB_CONNECTION = "jdbc:oracle:thin:@orcljava.coehxub0jkfc.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static String DB_USER = "elvin_morales";
    private static String DB_PASSWORD = "123456";

    private static Connection getDBConnetion() {
        Connection dbConnection = null;

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dbConnection;
    }

    public static void main(String[] args) {

        String numeronit, nombre, direccion;
        double ingresos, impuestos;
        int opcion = 0;

        mantenimiento_planilla m = new mantenimiento_planilla();

        planilla planillas = new planilla();

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("INTRODUZCA UNA OPCION\n"
                    + "1. INGRESAR UN REGISTRO\n"
                    + "2. CONSULTAR REGISTRO\n"
                    + "3. SALIR"));

            try {
                switch (opcion) {

                    case 1:
                        numeronit = JOptionPane.showInputDialog("digite su numero de nit");
                        nombre = JOptionPane.showInputDialog("digite su nombre completo");
                        direccion = JOptionPane.showInputDialog("digite su direccion");
                        ingresos = Double.parseDouble(JOptionPane.showInputDialog("digite su ingreso  $"));
                        impuestos = Double.parseDouble(JOptionPane.showInputDialog("impuestos  $"));
                        
                        planillas.setNonit(numeronit);
                        planillas.setNombre(nombre);
                        planillas.setDireccion(direccion);
                        planillas.setIngresos(ingresos);
                        planillas.setImpuestos(impuestos);
                        System.out.println("registro ingresados" );
                        m.insertarplanilla(planillas);
                        
                        break;
                    case 2:
                        
                        m.consultarplanilla();

                        break;
                    case 3:

                        break;

                    default:
                        JOptionPane.showInternalMessageDialog(null, "opcion no valida");
                }
            } catch (InputMismatchException e) {
                System.out.println("debes insertar un numero..." + e);

            }

        } while (opcion != 3);

    }

    public void insertarplanilla(planilla planillas) {

        Connection dbConnection = null;
        CallableStatement callableStatement = null;
        int flag = 0;
        String StringSql = "{call SP_INSERTAR_PLANILLA(?, ?, ?, ?, ?)}";

        try {
            dbConnection = getDBConnetion();
            callableStatement = dbConnection.prepareCall(StringSql);

            callableStatement.setString(1, planillas.getNonit());
            callableStatement.setString(2, planillas.getNombre());
            callableStatement.setString(3, planillas.getDireccion());
            callableStatement.setDouble(4, planillas.getIngresos());
            callableStatement.setDouble(5, planillas.getImpuestos());

            flag = callableStatement.executeUpdate();

            if (flag == 1) {
                System.out.println("Registrado satisfactoriamente...");
            } else {
                System.out.println("Los datos no pudieron ser registrados...");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }

                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public void consultarplanilla() {
        Statement stmt = null;
        ResultSet rs = null;
        Connection dbConnection = null;

        try {
            dbConnection = getDBConnetion();
            stmt = dbConnection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM PLANILLA");

            while (rs.next()) {
                System.out.println("NOMBRE DEL CONTRIBUYENTE:" + rs.getString("NOMBRE_CONTRIBUYENTE")
                        + " DIRECCION: " + rs.getString("DIRECCION")
                        + " INGRESOS ANUALES: " + rs.getString("INGRESOS_ANUALES")
                        + "IMPUESTOS" + rs.getString("IMPUESTOS"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (Exception e) {
            }
        }
    }
}

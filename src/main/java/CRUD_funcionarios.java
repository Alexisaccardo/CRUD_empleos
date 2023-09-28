import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.*;
import java.util.Base64;
import java.util.Scanner;

public class CRUD_funcionarios {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("*****GESTION DE REGISTRO*****");

        System.out.println("Deseas registrar pedido, registrar funcionario, actualizar pedido, consultar pedido, eliminar pedido o eliminar funcionario?: ");
        String result = scanner.nextLine();

        if (result.equals("registrar pedido")) {

            System.out.println("Ingrese su correo: ");
            String email = scanner.nextLine();

            System.out.println("Ingrese su password: ");
            String pass = scanner.nextLine();

            String newdocument = Select_admin(email, pass);
            if (newdocument.equals("Administrador") || newdocument.equals("Lider")) {

                System.out.println("Ingrese codigo del pedido: ");
                String code = scanner.nextLine();

                System.out.print("Ingrese el estado del pedido: ");
                String state = scanner.nextLine();

                System.out.println("Ingrese el nombre del cliente: ");
                String name = scanner.nextLine();

                System.out.println("Ingrese el valor de pedido: ");
                String value = scanner.nextLine();

                Insert(code, state, name, value);
            } else {
                System.out.println("tu cargo: " + newdocument + " no coincide con el cargo de Administrador o lider");
            }

        }
        if (result.equals("registrar funcionario")) {

            System.out.println("Ingrese su correo: ");
            String email = scanner.nextLine();

            System.out.println("Ingrese su password: ");
            String pass = scanner.nextLine();

            String newdocument = Select_admin(email, pass);
            if (newdocument.equals("Administrador")) {

                System.out.println("Ingrese el correo del funcionario a registrar: ");
                email = scanner.nextLine();

                System.out.println("Ingrese password del funcionario a registrar");
                pass = scanner.nextLine();

                System.out.println("Ingrese el cargo asignado al funcionario: ");
                String charge = scanner.nextLine();

                if (charge.equals("Lider")) {
                    String password_encriptada = encript(pass);

                    Insert_pass(email, password_encriptada, charge);
                } else {
                    Insert_pass(email, pass, charge);
                }
            }
        }
        if (result.equals("actualizar pedido")) {

            System.out.println("Ingrese su correo: ");
            String email = scanner.nextLine();

            System.out.println("Ingrese su password: ");
            String pass = scanner.nextLine();

            String newdocument = Select_admin(email, pass);
            if (newdocument.equals("Administrador")) {

                System.out.println("Ingrese codigo del pedido: ");
                String code = scanner.nextLine();

                System.out.print("Ingrese el estado actualizado del pedido: ");
                String state = scanner.nextLine();

                System.out.println("Ingrese el nombre actualizado del cliente: ");
                String name = scanner.nextLine();

                System.out.println("Ingrese el valor actualizado del pedido: ");
                String value = scanner.nextLine();


                Editar(code, state, name, value);
            } else {
                System.out.println("tu cargo: " + newdocument + " no coincide con el cargo de Administrador");
            }

        }
        if (result.equals("consultar pedido")) {

            System.out.println("Ingrese su correo: ");
            String email = scanner.nextLine();

            System.out.println("Ingrese su password: ");
            String pass = scanner.nextLine();

            System.out.println("Ingrese codigo de consulta: ");
            String consultation_code = scanner.nextLine();

            String c = Select_admin(email, pass);

            if (c.equals("")) {

                System.out.println("Ingrese nombre al que pertenece pedido");
                String name = scanner.nextLine();

                Select_Cliente(consultation_code, name);
            } else {
                String newdocument = Select_admin(email, pass);

                if (newdocument.equals("Administrador") || newdocument.equals("Lider") || newdocument.equals("Desarrollador")) {

                    System.out.println("Ingrese codigo de consulta: ");
                    consultation_code = scanner.nextLine();

                    Select_One(consultation_code);
                }

            }
        }
        if (result.equals("eliminar pedido")) {

            System.out.println("Ingrese su correo: ");
            String email = scanner.nextLine();

            System.out.println("Ingrese su password: ");
            String pass = scanner.nextLine();

            String newdocument = Select_admin(email, pass);
            if (newdocument.equals("Administrador")) {

                System.out.println("Ingrese el codigo del pedido que deseas eliminar: ");
                String producto_cod = scanner.nextLine();

                Eliminar(producto_cod);

            } else {
                System.out.println("tu cargo: " + newdocument + " no coincide con el cargo de Administrador");
            }
        }
        if (result.equals("eliminar funcionario")) {

            System.out.println("Ingrese su correo: ");
            String email = scanner.nextLine();

            System.out.println("Ingrese su password: ");
            String pass = scanner.nextLine();

            String newdocument = Select_admin(email, pass);
            if (newdocument.equals("Administrador")) {

                System.out.println("Ingrese el correo del funcionario que deseas eliminar: ");
                email = scanner.nextLine();

                System.out.println("Ingrese el cargo del funcionario a eliminar: ");
                String charge = scanner.nextLine();

                if (charge.equals("Desarrollador")) {

                    Eliminar_funcionary(email, charge);
                } else {
                    System.out.println("Este funcionario no coincide con el cargo de Desarrollador");
                }
            }
        }
    }

    private static void Select_Cliente(String consultation_code, String name) throws ClassNotFoundException, SQLException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/crud";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM pedidos WHERE codigo = ? and nombre = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, consultation_code); // Establecer el valor del parámetro
        statement.setString(2, name);

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            String codigo = resultSet.getString("codigo");
            String estado = resultSet.getString("estado");
            String nombre = resultSet.getString("nombre");
            String valor = resultSet.getString("valor");


            System.out.println("Estes es el codigo del pedido: " + codigo + " Estado: " + estado + " Nombre del cliente: " + nombre + " Por un valor de: " + valor);

        } else {
            System.out.println("No se encontro un registro de pedido con el codigo especificado.");
        }

        // Cerrar recursos
        resultSet.close();
        statement.close();
        connection.close();
    }


    private static void Eliminar_funcionary(String email, String charge) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/crud";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        String sentenciaSQL = "DELETE FROM funcionarios WHERE correo = ? AND cargo = ?";
        PreparedStatement prepareStatement = connection2.prepareStatement(sentenciaSQL);
        prepareStatement.setString(1, email);
        prepareStatement.setString(2, charge);
        prepareStatement.executeUpdate();

        System.out.println("Funcionario eliminado de manera exitosa");

    }

    private static void Insert_pass(String email, String password_encriptada, String charge) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/crud";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM funcionarios");


            // Sentencia INSERT
            String sql = "INSERT INTO funcionarios (correo, password, cargo) VALUES (?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password_encriptada);
            preparedStatement.setString(3, charge);


            // Ejecutar la sentencia
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Funcionario registrado de manera exitosa.");
            } else {
                System.out.println("No se pudo registrar el funcionario");
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void Eliminar(String producto_cod) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/crud";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        String sentenciaSQL = "DELETE FROM pedidos WHERE codigo = ?";
        PreparedStatement prepareStatement = connection2.prepareStatement(sentenciaSQL);
        prepareStatement.setString(1, producto_cod);
        prepareStatement.executeUpdate();

        System.out.println("Pedido eliminado de manera exitosa");

    }

    private static void Select_One(String consultation_code) throws ClassNotFoundException, SQLException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/crud";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM pedidos WHERE codigo = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, consultation_code); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            String codigo = resultSet.getString("codigo");
            String estado = resultSet.getString("estado");
            String nombre = resultSet.getString("nombre");
            String valor = resultSet.getString("valor");


            System.out.println("Estes es el codigo del pedido: " + codigo + " Estado: " + estado + " Nombre del cliente: " + nombre + " Por un valor de: " + valor);

        } else {
            System.out.println("No se encontro un registro de pedido con el codigo especificado.");
        }

        // Cerrar recursos
        resultSet.close();
        statement.close();
        connection.close();
    }

    private static void Editar(String code, String state, String name, String value) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/crud";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consulta = "UPDATE pedidos SET estado = ?, nombre = ?, valor = ? WHERE codigo = ?";
        PreparedStatement preparedStatement = connection2.prepareStatement(consulta);
        preparedStatement.setString(1, state);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, value);
        preparedStatement.setString(4, code);

        int filasActualizadas = preparedStatement.executeUpdate();
        if (filasActualizadas > 0) {
            System.out.println("Pedido actualizado exitosamente");
        } else {
            System.out.println("No se encontro el registro para actualizar");
        }

        preparedStatement.close();
        connection2.close();
    }

    private static void Insert(String code, String state, String name, String value) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/crud";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pedidos");


            // Sentencia INSERT
            String sql = "INSERT INTO pedidos (codigo, estado, nombre, valor) VALUES (?, ?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, state);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, value);



            // Ejecutar la sentencia
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("pedido registrado de exitosamente.");
            } else {
                System.out.println("No se pudo registrar su pedido");
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String Select_admin(String email, String pass) throws ClassNotFoundException, SQLException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/crud";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM funcionarios WHERE correo = ? AND password = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, email); // Establecer el valor del parámetro
        statement.setString(2, pass);

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            String correo = resultSet.getString("correo");
            correo.equalsIgnoreCase(email);
            String password_funcionary = resultSet.getString("password");
            String cargo = resultSet.getString("cargo");

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();

            return cargo;

        }else{
            System.out.println("Este funcionario no existe o no se encuentra registrado");
        }

        return "";
    }
    private static String encript(String text) throws Exception {

        byte[] bytesCodificados = Base64.getEncoder().encode(text.getBytes());

        String textoCodificado = new String(bytesCodificados);

        return textoCodificado;
    }
}

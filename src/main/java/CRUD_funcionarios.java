import java.sql.*;
import java.util.Scanner;

public class CRUD_funcionarios {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("*****DEPARTAMENTOS*****");

        System.out.println("Deseas registrar, actualizar, consultar o eliminar?: ");
        String result = scanner.nextLine();

        if (result.equals("registrar")){

            System.out.println("Ingrese su documento: ");
            String document = scanner.nextLine();

            String newdocument = Select_admin(document);
            if (newdocument.equals("Administrador") || newdocument.equals("Lider")){

                System.out.println("Ingrese codigo del pedido: ");
                String code = scanner.nextLine();

                System.out.print("Ingrese el estado del pedido: ");
                String state = scanner.nextLine();

                System.out.println("Ingrese el nombre del cliente: ");
                String name = scanner.nextLine();

                System.out.println("Ingrese el valor de pedido: ");
                String value = scanner.nextLine();

                Insert(code, state, name, value);
            }else{
                System.out.println("tu cargo: " + newdocument + " no coincide con el cargo de Administrador o lider");
            }

        }
            if (result.equals("actualizar")){

                System.out.println("Ingrese su documento: ");
                String document = scanner.nextLine();

                String newdocument = Select_admin(document);
                if (newdocument.equals("Administrador")){

                    System.out.println("Ingrese codigo del pedido: ");
                    String code = scanner.nextLine();

                    System.out.print("Ingrese el estado actualizado del pedido: ");
                    String state = scanner.nextLine();

                    System.out.println("Ingrese el nombre actualizado del cliente: ");
                    String name = scanner.nextLine();

                    System.out.println("Ingrese el valor actualizado del pedido: ");
                    String value = scanner.nextLine();


                    Editar(code, state, name, value);
                }else{
                    System.out.println("tu cargo: " + newdocument + " no coincide con el cargo de Administrador");
                }

                }
                if (result.equals("consultar")){

                    System.out.println("Ingrese su documento: ");
                    String document = scanner.nextLine();

                    String newdocument = Select_admin(document);
                    if (newdocument.equals("Administrador") || newdocument.equals("Lider") || newdocument.equals("Desarrollador")){

                        System.out.println("Ingrese codigo de consulta: ");
                        String consultation_code = scanner.nextLine();

                        Select_One(consultation_code);
                    }
                }
                if (result.equals("eliminar")) {

                    System.out.println("Ingrese su documento: ");
                    String document = scanner.nextLine();

                    String newdocument = Select_admin(document);
                    if (newdocument.equals("Administrador")) {

                        System.out.println("Ingrese el codigo del pedido que deseas eliminar: ");
                        String producto_cod = scanner.nextLine();

                        Eliminar(producto_cod);

                    }else{
                        System.out.println("tu cargo: " + newdocument + " no coincide con el cargo de Administrador");
                    }
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

    private static String Select_admin(String document) throws ClassNotFoundException, SQLException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/crud";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM funcionarios WHERE documento = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, document); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            String documento = resultSet.getString("documento");
            String cargo = resultSet.getString("cargo");

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();

            return cargo;

        }else{
            System.out.println("Este documento no se encuentra registrado como funcionario");
        }

        return "";
    }
}

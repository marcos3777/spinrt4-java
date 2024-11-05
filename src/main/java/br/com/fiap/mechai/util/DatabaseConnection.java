package br.com.fiap.mechai.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        String usuario = System.getenv("USER_ORACLE");  // Variável de ambiente para o usuário Oracle
        String senha = System.getenv("PASSWORD_ORACLE"); // Variável de ambiente para a senha do Oracle
        String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";

        // Confirmação para exibir o usuário e senha configurados nas variáveis de ambiente
        System.out.println("USER_ORACLE: " + usuario);
        System.out.println("PASSWORD_ORACLE: " + senha);

        // Estabelecendo a conexão
        return DriverManager.getConnection(url, usuario, senha);
    }

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Conexão estabelecida com sucesso!");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


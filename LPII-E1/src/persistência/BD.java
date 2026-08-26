package persistência;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {

    static final String URL_SERVIDOR = "jdbc:mysql://localhost/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String URL_BD = "jdbc:mysql://localhost/banco?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USUÁRIO = "root";
    static final String SENHA = "admin";
    public static Connection conexão = null;
    
    public static void criaConexão () {
        try {
            try (Connection conexão_servidor = DriverManager.getConnection(URL_SERVIDOR, USUÁRIO, SENHA);
                 Statement comando = conexão_servidor.createStatement()) {
                comando.executeUpdate("CREATE DATABASE IF NOT EXISTS banco");
                comando.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS banco.Seguradoras (" +
                        "Nome VARCHAR(50) NOT NULL PRIMARY KEY, " +
                        "Cidade VARCHAR(50), " +
                        "CoberturaPercentual DECIMAL(5,2) NOT NULL)"
                );
                comando.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS banco.Pecas (" +
                        "Codigo INT NOT NULL PRIMARY KEY, " +
                        "Nome VARCHAR(50) NOT NULL, " +
                        "Categoria VARCHAR(50) NOT NULL, " +
                        "Preco DECIMAL(10,2) NOT NULL, " +
                        "Tipo VARCHAR(50) NOT NULL, " +
                        "Cor VARCHAR(15) NOT NULL, " +
                        "MaoDeObra BOOLEAN NOT NULL)"
                );
            }
            conexão = DriverManager.getConnection (URL_BD, USUÁRIO, SENHA);
        } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
    }
    
    public static void fechaConexão () {
        try {
            if (conexão != null) conexão.close();
        } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
    }
}

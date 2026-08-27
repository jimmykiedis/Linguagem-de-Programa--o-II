package persistência;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BD {

    static final String URL_BD = "jdbc:mysql://localhost/banco?useUnicode=true&characterEncoding=utf8&connectionCollation=utf8mb4_unicode_ci&serverTimezone=UTC&useSSL=false";
    static final String USUARIO = "root";
    static final String SENHA = "admin";
    public static Connection conexao = null;
    
    public static void criaConexao() {
        try {
            conexao = DriverManager.getConnection(URL_BD, USUARIO, SENHA);
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
        }
    }
    
    public static void fechaConexao() {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
        }
    }
}

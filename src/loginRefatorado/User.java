package loginRefatorado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    
    private String nome = "";
    private boolean autenticado = false;
    
    /**
     * Conecta ao banco de dados MySQL
     */
    private Connection conectarBD() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url = "jdbc:mysql://127.0.0.1:3306/test?useSSL=false&allowPublicKeyRetrieval=true";
            String usuario = "root";
            String senha = "1234";
            
            System.out.println("Tentando conectar com usuario: " + usuario);
            conn = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conectado ao banco de dados!");
            return conn;
            
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Verifica se usuário e senha são válidos
     */
    public boolean verificarUsuario(String login, String senha) {
        this.nome = "";
        this.autenticado = false;
        
        if (login == null || senha == null || login.isEmpty() || senha.isEmpty()) {
            System.out.println("Login e senha não podem estar vazios");
            return false;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = conectarBD();
            if (conn == null) {
                System.out.println("Não foi possível conectar ao banco");
                return false;
            }
            
            criarTabelaSeNaoExistir(conn);
            
            String sql = "SELECT nome FROM usuarios WHERE login = ? AND senha = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                this.nome = rs.getString("nome");
                this.autenticado = true;
                System.out.println("Usuário autenticado: " + this.nome);
                return true;
            } else {
                System.out.println("Usuário ou senha inválidos");
                System.out.println("Tente usar: admin / 123456");
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao verificar usuário: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("Conexões fechadas");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexões: " + e.getMessage());
            }
        }
    }
    
    /**
     * Cria a tabela de usuários se não existir
     */
    private void criarTabelaSeNaoExistir(Connection conn) {
        try {
            String createTable = "CREATE TABLE IF NOT EXISTS usuarios (" +
                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                "login VARCHAR(50) UNIQUE, " +
                                "senha VARCHAR(50), " +
                                "nome VARCHAR(100))";
            PreparedStatement stmt = conn.prepareStatement(createTable);
            stmt.execute();
            stmt.close();
            System.out.println("Tabela 'usuarios' verificada/criada");
            
            String checkUsers = "SELECT COUNT(*) as total FROM usuarios";
            PreparedStatement checkStmt = conn.prepareStatement(checkUsers);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt("total") == 0) {
                String insertUser = "INSERT INTO usuarios (login, senha, nome) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertUser);
                insertStmt.setString(1, "admin");
                insertStmt.setString(2, "123456");
                insertStmt.setString(3, "Administrador do Sistema");
                insertStmt.execute();
                insertStmt.close();
                System.out.println("Usuário de teste criado: admin / 123456");
            }
            
            rs.close();
            checkStmt.close();
            
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }
    
    /**
     * Testa a conexão com o banco
     */
    public static void testarConexao() {
        System.out.println("Testando conexão com MySQL...");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/test?useSSL=false";
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            System.out.println("Conexão estabelecida com sucesso!");
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado");
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
        }
    }
    
    public String getNome() {
        return nome;
    }
    
    public boolean isAutenticado() {
        return autenticado;
    }
    
    /**
     * METODO PRINCIPAL
     */
    public static void main(String[] args) {
        System.out.println("Iniciando teste de login...");
        
        testarConexao();
        
        System.out.println("\n--- Testando sistema de login ---");
        
        User usuario = new User();
        
        boolean resultado = usuario.verificarUsuario("admin", "123456");
        
        if (resultado) {
            System.out.println("Login realizado com sucesso!");
            System.out.println("Nome do usuário: " + usuario.getNome());
        } else {
            System.out.println("Falha no login!");
            System.out.println("Use: login = admin, senha = 123456");
        }
        
        System.out.println("Teste finalizado!");
    }
}
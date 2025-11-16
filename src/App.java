import login.User;

public class App {
    public static void main(String[] args) {     
        User usuario = new User();
        boolean resultado = usuario.verificarUsuario("admin", "123456");
        if (resultado) {
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Falha no login!");
    }
    }
}


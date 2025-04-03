
package main;

/**
 *
 * @author Aley Cabrera D
 */
import dao.UsuarioDAO;
import modelo.Usuario;
import vista.FormularioUsuario;
import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Mostrar formulario
        FormularioUsuario formulario = new FormularioUsuario(null);
        formulario.setVisible(true);
        
        Usuario nuevoUsuario = formulario.getUsuario();
        if (nuevoUsuario != null) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            try {
                if (usuarioDAO.insertarUsuario(nuevoUsuario)) {
                    JOptionPane.showMessageDialog(null, 
                        "Usuario registrado exitosamente!\nID: " + nuevoUsuario.getId(), 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error al registrar usuario: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, 
                "Registro cancelado", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        
        System.exit(0);
    }
}


package vista;

/**
 *
 * @author Aley Cabrera D
 */
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FormularioUsuario extends JDialog {
    private JTextField txtPrimerNombre, txtSegundoNombre, txtPrimerApellido, txtSegundoApellido;
    private JTextField txtEmail, txtCelular, txtNombrePH, txtTipoPropiedad, txtNumeroPropiedad;
    private JPasswordField txtContraseña, txtConfirmarContraseña;
    private JButton btnGuardar, btnCancelar;
    
    private Usuario usuario;
    private boolean guardado = false;

    public FormularioUsuario(JFrame parent) {
        super(parent, "Nuevo Usuario", true);
        initComponents();
    }

    private void initComponents() {
        setSize(500, 450);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        
        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(11, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Campos del formulario
        panelForm.add(new JLabel("Primer Nombre*:"));
        txtPrimerNombre = new JTextField();
        panelForm.add(txtPrimerNombre);
        
        panelForm.add(new JLabel("Segundo Nombre:"));
        txtSegundoNombre = new JTextField();
        panelForm.add(txtSegundoNombre);
        
        panelForm.add(new JLabel("Primer Apellido*:"));
        txtPrimerApellido = new JTextField();
        panelForm.add(txtPrimerApellido);
        
        panelForm.add(new JLabel("Segundo Apellido:"));
        txtSegundoApellido = new JTextField();
        panelForm.add(txtSegundoApellido);
        
        panelForm.add(new JLabel("Email*:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);
        
        panelForm.add(new JLabel("Celular*:"));
        txtCelular = new JTextField();
        panelForm.add(txtCelular);
        
        panelForm.add(new JLabel("Nombre Propiedad Horizontal*:"));
        txtNombrePH = new JTextField();
        panelForm.add(txtNombrePH);
        
        panelForm.add(new JLabel("Tipo Propiedad*:"));
        txtTipoPropiedad = new JTextField();
        panelForm.add(txtTipoPropiedad);
        
        panelForm.add(new JLabel("Número Propiedad*:"));
        txtNumeroPropiedad = new JTextField();
        panelForm.add(txtNumeroPropiedad);
        
        panelForm.add(new JLabel("Contraseña*:"));
        txtContraseña = new JPasswordField();
        panelForm.add(txtContraseña);
        
        panelForm.add(new JLabel("Confirmar Contraseña*:"));
        txtConfirmarContraseña = new JPasswordField();
        panelForm.add(txtConfirmarContraseña);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(this::guardarUsuario);
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        
        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void guardarUsuario(ActionEvent evt) {
        // Validación básica
        if (txtPrimerNombre.getText().trim().isEmpty() ||
            txtPrimerApellido.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos marcados con * son obligatorios", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar contraseñas
        if (!new String(txtContraseña.getPassword()).equals(new String(txtConfirmarContraseña.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear el objeto usuario
        usuario = new Usuario(
            txtPrimerNombre.getText(),
            txtSegundoNombre.getText(),
            txtPrimerApellido.getText(),
            txtSegundoApellido.getText(),
            txtEmail.getText(),
            txtCelular.getText(),
            txtNombrePH.getText(),
            txtTipoPropiedad.getText(),
            Integer.parseInt(txtNumeroPropiedad.getText()),
            new String(txtContraseña.getPassword())
        );
        
        guardado = true;
        dispose();
    }

    public Usuario getUsuario() {
        return guardado ? usuario : null;
    }
}

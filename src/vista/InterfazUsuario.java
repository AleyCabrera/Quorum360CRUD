
package vista;

/**
 *
 * @author Aley Cabrera D
 */
import dao.UsuarioDAO;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class InterfazUsuario extends JFrame {
    private UsuarioDAO usuarioDAO;
    private JTable tablaUsuarios;
    private JButton btnNuevo, btnEditar, btnEliminar, btnActualizar;
    private UsuarioTableModel tableModel;
    
    public InterfazUsuario() {
        usuarioDAO = new UsuarioDAO();
        initComponents();
        cargarDatos();
    }
    
    private void initComponents() {
        setTitle("Gestión de Usuarios - Quorum360");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con borde
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de botones con estilo
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Acciones"));

        btnNuevo = new JButton("Nuevo");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");

        // Añadir iconos a los botones (opcional)
        btnNuevo.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
        btnEditar.setIcon(new ImageIcon(getClass().getResource("/icons/edit.png")));
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
        btnActualizar.setIcon(new ImageIcon(getClass().getResource("/icons/refresh.png")));

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        // Tabla de usuarios con modelo vacío inicial
        tablaUsuarios = new JTable();
        tablaUsuarios.setAutoCreateRowSorter(true); // Permitir ordenar
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Usuarios"));

        panelPrincipal.add(panelBotones, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);

        configurarAcciones();
    }
    
    private void configurarAcciones() {
    btnNuevo.addActionListener(e -> mostrarFormularioNuevoUsuario());
    btnEditar.addActionListener(e -> editarUsuarioSeleccionado());
    btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());
    btnActualizar.addActionListener(e -> cargarDatos());
    
        // Permitir editar con doble clic
        tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editarUsuarioSeleccionado();
                }
            }
        });
    }
    
    private void mostrarFormularioNuevoUsuario() {
        FormularioUsuario formulario = new FormularioUsuario(this, null);
        formulario.setVisible(true);

        Usuario nuevoUsuario = formulario.getUsuario();
        if (nuevoUsuario != null) {
            try {
                if (usuarioDAO.insertarUsuario(nuevoUsuario)) {
                    JOptionPane.showMessageDialog(this, 
                        "Usuario creado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                }
            } catch (SQLException ex) {
                // Manejo específico para email duplicado
                if (ex.getMessage().contains("Duplicate entry") || ex.getMessage().contains("ya está registrado")) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: El email ya está registrado\nPor favor use otro email", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    mostrarError("Error al crear usuario: " + ex.getMessage());
                }
            }
        }
    }

    private void editarUsuarioSeleccionado() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un usuario para editar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = tableModel.getUsuarioAt(filaSeleccionada);
        FormularioUsuario formulario = new FormularioUsuario(this, usuario);
        formulario.setVisible(true);
    
    Usuario usuarioEditado = formulario.getUsuario();
        if (usuarioEditado != null) {
            try {
                if (usuarioDAO.actualizarUsuario(usuarioEditado)) {
                    JOptionPane.showMessageDialog(this, 
                        "Usuario actualizado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                }
            } catch (SQLException ex) {
                mostrarError("Error al actualizar usuario: " + ex.getMessage());
            }
        }
    }

    private void eliminarUsuarioSeleccionado() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un usuario para eliminar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este usuario?", 
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            Usuario usuario = tableModel.getUsuarioAt(filaSeleccionada);
            try {
                if (usuarioDAO.eliminarUsuario(usuario.getId())) {
                    JOptionPane.showMessageDialog(this, 
                        "Usuario eliminado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                }
            } catch (SQLException ex) {
                mostrarError("Error al eliminar usuario: " + ex.getMessage());
            }
        }
    }

    // Método auxiliar para mostrar errores
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void cargarDatos() {
        try {
            List<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
            tableModel = new UsuarioTableModel(usuarios);
            tablaUsuarios.setModel(tableModel);

            // Ajustar el ancho de las columnas
            tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
            tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
            tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(200); // Email

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar usuarios: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazUsuario().setVisible(true);
        });
    }
}

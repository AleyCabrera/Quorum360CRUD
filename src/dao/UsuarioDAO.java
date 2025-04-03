
package dao;

import conexion.DatabaseConnection;
import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    // CREATE - Insertar nuevo usuario
    public boolean insertarUsuario(Usuario usuario) throws SQLException {
        // Primero verificar si el email ya existe
        if (existeEmail(usuario.getEmail())) {
            throw new SQLException("El email " + usuario.getEmail() + " ya está registrado");
        }
        
        String sql = "INSERT INTO usuarios (PrimerNombre, SegundoNombre, PrimerApellido, SegundoApellido, "
                   + "Email, Celular, NombrePropiedadHorizontal, TipoPropiedad, NumeroPropiedad, "
                   + "Contraseña, ConfirmarContraseña) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, usuario.getPrimerNombre());
            stmt.setString(2, usuario.getSegundoNombre());
            stmt.setString(3, usuario.getPrimerApellido());
            stmt.setString(4, usuario.getSegundoApellido());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getCelular());
            stmt.setString(7, usuario.getNombrePropiedadHorizontal());
            stmt.setString(8, usuario.getTipoPropiedad());
            stmt.setInt(9, usuario.getNumeroPropiedad());
            stmt.setString(10, usuario.getContraseña());
            stmt.setString(11, usuario.getContraseña()); // Confirmación
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                    return true;
                }
            }
            return false;
        } finally {
            if (generatedKeys != null) generatedKeys.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.closeConnection(conn);
        }
    }
    
    // Método auxiliar para verificar email
    private boolean existeEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM usuarios WHERE Email = ? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true si existe
            }
        }
    }
    
    // READ - Obtener usuario por ID
    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE ID = ?";
        Usuario usuario = null;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = mapearUsuario(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.closeConnection(conn);
        }
        return usuario;
    }
    
    // READ - Obtener todos los usuarios
    public List<Usuario> obtenerTodosUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.closeConnection(conn);
        }
        return usuarios;
    }
    
    // UPDATE - Actualizar usuario
    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET PrimerNombre = ?, SegundoNombre = ?, PrimerApellido = ?, "
                   + "SegundoApellido = ?, Email = ?, Celular = ?, NombrePropiedadHorizontal = ?, "
                   + "TipoPropiedad = ?, NumeroPropiedad = ?, Contraseña = ? WHERE ID = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, usuario.getPrimerNombre());
            stmt.setString(2, usuario.getSegundoNombre());
            stmt.setString(3, usuario.getPrimerApellido());
            stmt.setString(4, usuario.getSegundoApellido());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getCelular());
            stmt.setString(7, usuario.getNombrePropiedadHorizontal());
            stmt.setString(8, usuario.getTipoPropiedad());
            stmt.setInt(9, usuario.getNumeroPropiedad());
            stmt.setString(10, usuario.getContraseña());
            stmt.setInt(11, usuario.getId());
            
            return stmt.executeUpdate() > 0;
        } finally {
            if (stmt != null) stmt.close();
            DatabaseConnection.closeConnection(conn);
        }
    }
    
    // DELETE - Eliminar usuario
    public boolean eliminarUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE ID = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        } finally {
            if (stmt != null) stmt.close();
            DatabaseConnection.closeConnection(conn);
        }
    }
    
    // Método auxiliar para búsqueda por email
    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE Email = ?";
        Usuario usuario = null;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = mapearUsuario(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.closeConnection(conn);
        }
        return usuario;
    }
    
    // Método auxiliar para mapear ResultSet a objeto Usuario
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("ID"));
        usuario.setPrimerNombre(rs.getString("PrimerNombre"));
        usuario.setSegundoNombre(rs.getString("SegundoNombre"));
        usuario.setPrimerApellido(rs.getString("PrimerApellido"));
        usuario.setSegundoApellido(rs.getString("SegundoApellido"));
        usuario.setEmail(rs.getString("Email"));
        usuario.setCelular(rs.getString("Celular"));
        usuario.setNombrePropiedadHorizontal(rs.getString("NombrePropiedadHorizontal"));
        usuario.setTipoPropiedad(rs.getString("TipoPropiedad"));
        usuario.setNumeroPropiedad(rs.getInt("NumeroPropiedad"));
        usuario.setContraseña(rs.getString("Contraseña"));
        return usuario;
    }
}

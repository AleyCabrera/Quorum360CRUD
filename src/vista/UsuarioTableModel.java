
package vista;

/**
 *
 * @author Aley Cabrera D
 */
import modelo.Usuario;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UsuarioTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Nombre", "Email", "Celular", "Propiedad"};
    private List<Usuario> usuarios;
    
    public UsuarioTableModel(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    @Override
    public int getRowCount() {
        return usuarios.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = usuarios.get(rowIndex);
        switch (columnIndex) {
            case 0: return usuario.getId();
            case 1: return usuario.getPrimerNombre() + " " + usuario.getPrimerApellido();
            case 2: return usuario.getEmail();
            case 3: return usuario.getCelular();
            case 4: return usuario.getNumeroPropiedad();
            default: return null;
        }
    }
    
    public Usuario getUsuarioAt(int row) {
        return usuarios.get(row);
    }
    
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        fireTableDataChanged();
    }
}

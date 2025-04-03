
package modelo;

/**
 *
 * @author Aley Cabrera D
 */
public class Usuario {
    // Atributos (deben coincidir con tu tabla SQL)
    private int id;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;
    private String celular;
    private String nombrePropiedadHorizontal;
    private String tipoPropiedad;
    private int numeroPropiedad;
    private String contraseña;
    
     // Constructor vacío (necesario para el mapeo desde la BD)
    public Usuario() {
        // Constructor sin argumentos
    }
    
    // Constructor con todos los argumentos
    public Usuario(String primerNombre, String segundoNombre, String primerApellido, 
                  String segundoApellido, String email, String celular, 
                  String nombrePropiedadHorizontal, String tipoPropiedad, 
                  int numeroPropiedad, String contraseña) {
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.email = email;
        this.celular = celular;
        this.nombrePropiedadHorizontal = nombrePropiedadHorizontal;
        this.tipoPropiedad = tipoPropiedad;
        this.numeroPropiedad = numeroPropiedad;
        this.contraseña = contraseña;
    }

    // Getters y Setters (generados automáticamente en NetBeans)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNombrePropiedadHorizontal() {
        return nombrePropiedadHorizontal;
    }

    public void setNombrePropiedadHorizontal(String nombrePropiedadHorizontal) {
        this.nombrePropiedadHorizontal = nombrePropiedadHorizontal;
    }

    public String getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(String tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }

    public int getNumeroPropiedad() {
        return numeroPropiedad;
    }

    public void setNumeroPropiedad(int numeroPropiedad) {
        this.numeroPropiedad = numeroPropiedad;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + primerNombre + " " + primerApellido + 
               ", email=" + email + ", propiedad=" + numeroPropiedad + "}";
    }
}


//Clase Linea
package proyectointegrador_equipo10;

public class Linea {
    private String etiqueta;
    private String codop;
    private String operando;
    private String direccion; 
    private String tamaño;

    public Linea(String etiqueta, String codop, String operando, String direccion, String tamaño) {
        this.etiqueta = etiqueta;
        this.codop = codop;
        this.operando = operando;
        this.direccion = direccion;
        this.tamaño = tamaño;
    } //Fin de constructor 
    
    //Getters y setters 
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setCodop(String codop) {
        this.codop = codop;
    }

    public String getCodop() {
        return codop;
    }

    public void setOperando(String operando) {
        this.operando = operando;
    }

    public String getOperando() {
        return operando;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }
    
    
} //Fin de la clase linea 

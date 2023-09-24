//Clase Linea
package proyectointegrador_equipo10;

public class Linea {
    private String etiqueta;
    private String codop;
    private String operando;

    public Linea(String etiqueta, String codop, String operando) {
        this.etiqueta = etiqueta;
        this.codop = codop;
        this.operando = operando;
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
} //Fin de la clase linea 

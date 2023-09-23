//Clase Etiqueta

package proyectoprueba;

public class Etiqueta {
    private String etiqueta; 
    
    //Constructor 
    public Etiqueta() {
        this.etiqueta = null; //Inicializar etiqueta en null
    } //Fin del constructor

    public String getetiqueta() {
        return etiqueta;
    } //Fin de getter

    public void setetiqueta(String varetiqueta) {
        this.etiqueta = varetiqueta;
    }//Fin de setter 
    
    
      
} //Fin de la clase
/*
Programacion de bajo nivel
Proyecto Integrador | Parte 1

    Integrantes de este codigo 
    -Martinez Isaac
    -Hernandez Gutierrez Emmanuel
    -Jimenez Castellanos Alex
*/

package proyectointegrador_equipo10;

//Librerias
import java.io.BufferedReader;
import java.io.FileReader;

public class ProyectoIntegrador_Equipo10 {

    public static void main(String[] args) {
        
        String Archivo = ("P1ASM.asm.txt"); //Variable auxiliar para leer el archivo
    
        //Leer el archivo P1ASM.asm
        try { //Leer archivo
            BufferedReader Read = new BufferedReader(new FileReader(Archivo)); //Leer el archivo P1ASM.asm
            String Linea; //Variable auxiliar, todo lo que se lee se guarda en este variable
             
            while((Linea = Read.readLine()) != null) { //Mientras que exista algo para leer en el archivo, entonces continuara leyendo
                
                //Inicializar Variables
                String Etiqueta = null;
                String Codop = null;
                String Operando = null;
                //String Comentario = null;
                
                Linea = Linea.trim();
                //.trim evita los espacios o los tabuladores que hay de una palabra a otra y pasa directamente hacia la siguiente palabra
                 
                //Validar linea de comentario
                if(Linea.isEmpty()) { //Si la linea esta vacia
                    continue; //Continuar con las demas lineas
                }//Fin de if 
                
                //Validacion para comentario
                else if(Linea.startsWith(";")) { //Si la linea empieza con ";" entonces es un comentario 
                    System.out.println("COMENTARIO \n"); //Escribir comentario
                } //Fin de else if   
                
                //Valifacion para terminar el archivo
                else if(Linea.equalsIgnoreCase("END")) {//Si la linea termina en "END", entonces deja de leer el archivo
                    //Impresion de las variables por default cuando encuentre END
                    System.out.println("ETIQUETA = null");
                    System.out.println("CODOP = END");
                    System.out.println("OPERANDO = null");
            //Esta validacion puede mejorar...
                    Read.close(); // Funcion para cerrar el archivo de lectura
                    break; //El break indica el fin del ciclo 
                } //Fin de else if
                
                //Leer palabras 
                else {
                    String[] Palabras = Linea.split("\\s+"); //Arreglo que parte los espacios
                    //Palabras es un arreglo que guardara las palabras que esten separadas por espacio en la variable linea
                                        
                    /*Funcion Split: Se utiliza para dividir la cadena en partes, en este caso mediante espacios "\\S+"
                    Esta funcion nos proporciona palabra por palabra, es util en nuestro codigo porque asi podemos
                    guardar dichas palabras en las variables de Etiqueta, Codop y Operando.
                    \\s+ : Significa espacios " " */                                                       

                    for(String Palabra : Palabras) {
                        //Validar Etiqueta
                        if(Palabra.endsWith(":")) { //Validacion para etiqueta
                            Etiqueta = Palabra; //La palabra identificada se guardara en el String Etiqueta
                        } //Fin de if
                        
                        //Las variables ya estan inicializadas en null, por lo tanto siempre entra a la condicion
                        else if(Codop == null) { //Si el codigo operando es igual a null 
                            Codop = Palabra; //La palabra identificada se guardara en el String Codop
                        } //Fin de else if
                        else if(Operando == null) { //Si el operando es igual a null
                            Operando = Palabra; //La palabra identificada se guardara en el String Operando
                        } //Fin de else if                       
                    } //Fin de for                                         
                    
                    //Validaciones para los espacios
                    //Etiqueta
                    //CODOP                   
                    //Operando 
                    
                    //Impresion de las variables
                    System.out.println("ETIQUETA = " + Etiqueta);
                    System.out.println("CODOP = " + Codop);
                    System.out.println("OPERANDO = " + Operando + "\n");
                } //Fin de else 
            } //Fin de while       
        
        } //Fin de try                        
        catch (Exception e) { //Mostrar mensaje de error
            System.out.println("Error " + e.getMessage()); //Mensaje de error
        } //Fin de catch
        
    } //Fin de main    
} //Fin de la clase

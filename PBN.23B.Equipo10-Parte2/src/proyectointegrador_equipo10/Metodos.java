//Clase de metodos
package proyectointegrador_equipo10;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Metodos {
    
        //Funcion para validar si operando es Binario 
        public static Boolean IsBinario(String binario) {
        
        if (binario.length() > 1) { //Valida si hay algo en binario despues de %
            binario = binario.substring(1); //Le quita el primer caracter (%) y reemplaza la misma variable
        } //Fin de if
        else { //Si no hay nada despues de % entonces es un error 
            System.out.println("Error Operando Binario"); //Imprimir error 
            return false; //Error
        } //Fin de error 
        
        String patron = "^[01]+$";  // Patrón para permitir solo 1 y 0, al menos un carácter.
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(binario);
        
        //Comprobar
        if (matcher.matches()) { //Si encuentra un patron entonces es binario 
            //System.out.println(binario); //Impresion del binario
        } else { //De lo contario imprime un error
            //System.out.println("Error Operando: " + binario +" no es un binario"); //Mensaje de error 
        } //Fin de else 
        
        return matcher.matches(); //Retorna el valor           
    } //Fin de la funcion para comprobar binario
    
    //Funcion para validar si operando es decimal
    public static Boolean IsDecimal(String decimal) {
        String patrondecimal = "[0-9]+$"; //Variable auxiliar para comparar
        Pattern pattern = Pattern.compile(patrondecimal); //Junta los caracteres en un objeto para ser comparados
        Matcher matcher = pattern.matcher(decimal);

        return matcher.matches();
        } //Fin de la funcion para comprobar decimal
    
} //Fin de la clase metodos 

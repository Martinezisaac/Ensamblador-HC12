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
            //System.out.println("Error Operando Binario"); //Imprimir error 
            return false; //Error
        } //Fin de error 

        String patron = "^[01]+$";  // Patrón para permitir solo 1 y 0, al menos un carácter.
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(binario);

        return matcher.matches(); //Retorna el binario           
    } //Fin de la funcion para comprobar binario
    
    //Funcion para validar si es octal 
    public static Boolean IsOctal(String octal) {
        if (octal.length() > 1) { //Valida si hay algo en octal despues de @
            octal = octal.substring(1); //Le quita el primer caracter (@) y reemplaza la misma variable
        } //Fin de if
        else { //Si no hay nada despues de @ entonces es un error 
            //System.out.println("Error Operando Octal"); //Imprimir error 
            return false; //Error
        } //Fin de error 
        
        String patron = "^[0-7]+$"; //Determinar patron con sintaxis de un octal 
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(octal); 
        
        return matcher.matches(); //Retorna el octal 
               
    } //Fin de la funcion para comprobar operando octal 
    
    //Funcion para validar si es hexadecimal 
    public static Boolean IsHexadecimal(String hexadecimal) {
        if (hexadecimal.length() > 1) { //Valida si hay algo en octal despues de @
            hexadecimal = hexadecimal.substring(1); //Le quita el primer caracter (@) y reemplaza la misma variable
        } //Fin de if
        else { //Si no hay nada despues de @ entonces es un error 
            //System.out.println("Error Operando Octal"); //Imprimir error 
            return false; //Error
        } //Fin de error      
        
        String patron = "^[0-9A-Fa-f]+$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(hexadecimal);
        
        return matcher.matches(); //Retorna el hexadecimal          
    } //Fin de la funcion para comprobar operando octal
    
    //Funcion para validar si operando es decimal
    public static Boolean IsDecimal(String decimal) {
        String patrondecimal = "[0-9]+$"; //Variable auxiliar para comparar
        Pattern pattern = Pattern.compile(patrondecimal); //Junta los caracteres en un objeto para ser comparados
        Matcher matcher = pattern.matcher(decimal);

        return matcher.matches(); //Retorna el decimal 
        } //Fin de la funcion para comprobar decimal
    
    //Funcion para convertir de un operando a un decimal 
    public static String ConvertBinarioDecimal(String binario) {
        if(IsBinario(binario)) {
            int decimal = 0; //Variable auxiliar
            int longitud = binario.length(); //Identificar longitud del binario 
            binario = binario.substring(1); //Eliminar identificador de binario%

            // Invierte la cadena binaria
            StringBuilder binarioReverso = new StringBuilder(binario).reverse();

            for (int i = 0; i < longitud-1; i++) { 
                // Obtiene el dígito en la posición i y conviértelo a entero
                int digito = Character.getNumericValue(binarioReverso.charAt(i)); //Obtener valores de binario de manera inversa

                // Multiplica el dígito por 2 elevado a la potencia correspondiente
                decimal += digito * Math.pow(2, i); //Elevar a la potencia 2 conforme avanza de posicion 
                } //Fin de for 
            String DecimalString = Integer.toString(decimal); //Convertir decimal a String 
            return DecimalString; 
        } //Fin de if
        else { //Si no es binario, entonces muestra mensaje de error 
            return "Error Operando Binario"; //Mensaje de error
        } //Fin de else       
    } //Fin del metodo  
    } //Fin de la clase metodos 



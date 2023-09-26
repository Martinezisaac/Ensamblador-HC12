//Clase de metodos
package proyectointegrador_equipo10;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Metodos {
    
    public static boolean ComprobarEtiqueta(String etiqueta) {
        if(etiqueta.matches("[a-zA-Z][a-zA-Z0-9_]{0,7}")) {
            return true; 
        } //Fin de if          
        else {
            return true;  
        } //Fin de else 
    } //Fin de compribar etiqueta
    
    public static void DeterminarBits(int ValorDecimal) {
        if (ValorDecimal >= 0 && ValorDecimal <= 255) {
            System.out.println("8 bits");
        } //Fin de if
        else if (ValorDecimal >= 256 && ValorDecimal <= 65535) {
            System.out.println("16 bits");
        } //Fin de else if
        else {
            System.out.println("Fuera de rango");
        } //Fin de else 
    } //Fin de la funcion para determinar bits
    
    public static boolean Determinar8Bits(int ValorDecimal) {
        return  ValorDecimal >= 0 && ValorDecimal <= 255; 
    } //Fin de la funcion para determinar bits
    
    public static boolean Determinar16Bits(int ValorDecimal) {
        return  ValorDecimal >= 256 && ValorDecimal <= 65535; 
    } //Fin de la funcion para determinar bits
    
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
            char caractdel = '@';//Declaracion del caracter que se tiene que quitar para hacer la comparacion.
            String newx = octal.replace(String.valueOf(caractdel), "");//Se remplaza el caractern a eliminar con une spacio en blanco.
            String patronocta = "^[0-7]+$";//Declaracn de la variable que cintene los caracteres para comparar.
            Pattern pattern = Pattern.compile(patronocta);//junta los caracteres en un objeto para ser comparados.
            Matcher matcher = pattern.matcher(newx);//Crea un objeto para contner la cadena de la variable de entrada.
            return matcher.matches();//Da el return verdadero si la cadena contiene los caracteres del patron. 
               
    } //Fin de la funcion para comprobar operando octal 
    
    //Funcion para validar si es hexadecimal 
    public static Boolean IsHexadecimal(String hexadecimal) {
            char caractdel = '$';//Declaracion del caracter que se tiene que quitar para hacer la comparacion.
            String newx = hexadecimal.replace(String.valueOf(caractdel), "");//Se remplaza el caractern a eliminar con une spacio en blanco.
            String patronhexa = "^[0-9A-F]+$";//Declaracn de la variable que cintene los caracteres para comparar.
            Pattern pattern = Pattern.compile(patronhexa);//junta los caracteres en un objeto para ser comparados.
            Matcher matcher = pattern.matcher(newx);//Crea un objeto para contner la cadena de la variable de entrada.
            return matcher.matches();//Da el return verdadero si la cadena contiene los caracteres del patron.         
    } //Fin de la funcion para comprobar operando hexadecimal
    
    //Funcion para validar si operando es decimal
    public static Boolean IsDecimal(String decimal) {
        String patrondecimal = "[0-9]+$"; //Variable auxiliar para comparar
        Pattern pattern = Pattern.compile(patrondecimal); //Junta los caracteres en un objeto para ser comparados
        Matcher matcher = pattern.matcher(decimal);

        return matcher.matches(); //Retorna el decimal 
        } //Fin de la funcion para comprobar decimal
    
    //Funcion para convertir de un operando binario a un decimal 
    public static String ConvertBinarioDecimal(String binario) {
        if(IsBinario(binario)) {
            int decimal = 0; //Variable auxiliar
            int longitud = binario.length(); //Identificar longitud del binario 
            binario = binario.substring(1); //Eliminar identificador de binario %

            // Invierte la cadena binaria
            StringBuilder binarioReverso = new StringBuilder(binario).reverse();

            for (int i = 0; i < longitud-1; i++) {  //Obtiene el dígito en la posición i y conviértelo a entero              
                int digito = Character.getNumericValue(binarioReverso.charAt(i)); //Obtener valores de binario de manera inversa

                decimal += digito * Math.pow(2, i); //Elevar a la potencia 2 conforme avanza de posicion 
                } //Fin de for 
            String DecimalString = Integer.toString(decimal); //Convertir decimal a String 
            return DecimalString; //Retornar el valor en decimal 
        } //Fin de if
        else { //Si no es binario, entonces muestra mensaje de error 
            return "Error Operando Binario"; //Mensaje de error
        } //Fin de else       
    } //Fin del metodo para convertir de binario a decimal 
    
    //Funcion para convertir de octal a Decimal
    public static String ConvertOctalDecimal(String octal) {
        if (IsOctal(octal)) {
            int decimal = 0; //Variable auxiliar
            octal = octal.substring(1); // Quita el primer caracter ("@")
          
            StringBuilder octalReverso = new StringBuilder(octal).reverse(); // Invierte la cadena octal

            for (int i = 0; i < octal.length(); i++) { //Obtiene el dígito en la posición i y conviértelo a entero
                char digito = octalReverso.charAt(i); //Obtener valores de octal de manera inversa
               
                int valorDecimal = Character.getNumericValue(digito); // Convierte el caracter octal a un valor decimal

                decimal += valorDecimal * Math.pow(8, i); // Multiplica el valor decimal por 8 elevado a la potencia correspondiente
            } //Fin de for 

            String decimalString = Integer.toString(decimal);
            return decimalString;
        } //Fin if 
        else {
            return "Error, el numero ingresado no es octal"; //Mensaje de error 
        } //Fin de else 
    } //Fin de la funcion para convertir de octal a decimal 
    
    //Funcion para convertir de un operando hexadecimal a un decimal
    public static String ConvertHexadecimalDecimal(String hexadecimal) {
        if (IsHexadecimal(hexadecimal)) {
            int decimal = 0; //Variable auxiliar para comparar
            hexadecimal = hexadecimal.substring(1); // Quita el primer caracter ("$")

            StringBuilder hexadecimalReverso = new StringBuilder(hexadecimal).reverse(); // Invierte la cadena hexadecimal

            for (int i = 0; i < hexadecimal.length(); i++) { // Obtiene el dígito en la posición i y conviértelo a entero 
                char digito = hexadecimalReverso.charAt(i); //Obtener valores de hexadecimal de manera inversa

                // Convierte el caracter hexadecimal a un valor decimal
                int valorDecimal; 
                if (Character.isDigit(digito)) { 
                    valorDecimal = Character.getNumericValue(digito);
                } //Fin de if 
                else {
                    valorDecimal = Character.toUpperCase(digito) - 'A' + 10; // A=10, B=11, ..., F=15
                } //FIn de else 
                
                decimal += valorDecimal * Math.pow(16, i); // Multiplica el valor decimal por 16 elevado a la potencia correspondiente
            } //Fin de for 

            String decimalString = Integer.toString(decimal); //Convertir decimal a String
            return decimalString; //Retornar el valor en decimal 
        } //Fin de if 
        else { //Si no es hexadecimal, entonces muestra mensaje de error
            return "Error, el numero ingresado no es hexadecimal"; //Mensaje de error 
        } //Fin de else 
    } //Fin de la funcion para convertir de hexadecimal a decimal 
    
    } //Fin de la clase metodos 
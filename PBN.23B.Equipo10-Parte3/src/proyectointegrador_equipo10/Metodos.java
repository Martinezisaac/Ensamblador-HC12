//Clase de metodos
package proyectointegrador_equipo10;

import java.awt.List;
import java.util.ArrayList;
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
        if (hexadecimal.length() > 1) { //Valida si hay algo en octal despues de $
            hexadecimal = hexadecimal.substring(1); //Le quita el primer caracter ($) y reemplaza la misma variable
        } //Fin de if
        else { //Si no hay nada despues de $ entonces es un error 
            //System.out.println("Error Operando hexadecmial"); //Imprimir error 
            return false; //Error
        } //Fin de error      
        
        String patron = "^[0-9A-Fa-f]+$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(hexadecimal);
        
        return matcher.matches(); //Retorna el hexadecimal          
    } //Fin de la funcion para comprobar operando hexadecimal
    
    //Funcion para validar si operando es decimal
    public static Boolean IsDecimal(String decimal) {
        if(!decimal.matches("[0-9]+$")) {
            return false;
        } //Fin de else 
        
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
            return "0"; //Mensaje de error
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
            //System.err.println("No se puede convertir a entero. El formato no es válido.");
            return "0"; //Mensaje de error 
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
            return "0"; //Mensaje de error 
        } //Fin de else 
    } //Fin de la funcion para convertir de hexadecimal a decimal
    
    static boolean cracteretq(String x){
        String cet1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:_";//Caracteres permitidos en la comparacion.
        String etqupper = x.toUpperCase();//Convierte la variable en mayusculas para la comparacion.
        if(etqupper.length()<=5){
            for(int i=0; i < etqupper.length(); i++){//Recorre la cadena x, que se ingresa a la funcion.
                char charetq = etqupper.charAt(i);//Se recorre la cadena caracter por caracter en la pisicion de i.
                if(cet1.indexOf(charetq) == -1){//Se revisa si los caracteres si estan en cet1.
                    return false;
                } //Fin de if
            } //Fin de for
        }
        return true;
    }//Fin de caracteretq

        static boolean codops(String o){
            String cet3 = "\t\s";//Caracteres permitidos en la comparacion.
            for(int i=0; i < o.length();i++){//Recorre la cadena o, que se ingresa a la funcion.
                char vals = o.charAt(i);//Se recorre la cadena caracter por caracter en la pisicion de i.
                if(cet3.indexOf(vals) == -1){//Se revisa si los caracteres si estan en cet3.
                    return false;
                } //Fin de if
            } //Fin de for
            return true;
        }//Fin de codops
        
        static boolean reconocer(String[] arr, String text){
            for (String elemento : arr) {
                if (elemento.equals(text)) {
                    return true; // Si encontramos el carácter, retornamos true
                }
            }
            return false; // Si no encontramos el carácter, retornamos false
        }
    
        static String sumaHexadecimal(String numero1, String numero2) {
            // Convertir los números hexadecimales a enteros
            int entero1 = Integer.parseInt(numero1, 16);
            int entero2 = Integer.parseInt(numero2, 16);

            // Realizar la suma
            int resultado = entero1 + entero2;

            // Convertir el resultado a hexadecimal
            String resultadoHexadecimal = Integer.toHexString(resultado);

            return resultadoHexadecimal;
    }
        
        static String quitar(String texto, String quitar) {
        // Utiliza el método replace para reemplazar la parte a eliminar con una cadena vacía
        String resultado = texto.replace(quitar, "");

        return resultado;
    }
        
    /*
            public static boolean IsHexa(String x){
                char caractdel = '$';//Declaracion del caracter que se tiene que quitar para hacer la comparacion.
                String newx = x.replace(String.valueOf(caractdel), "");//Se remplaza el caractern a eliminar con une spacio en blanco.
                String patronhexa = "^[0-9A-F]+$";//Declaracn de la variable que cintene los caracteres para comparar.
                Pattern pattern = Pattern.compile(patronhexa);//junta los caracteres en un objeto para ser comparados.
                Matcher matcher = pattern.matcher(newx);//Crea un objeto para contner la cadena de la variable de entrada.
                return matcher.matches();//Da el return verdadero si la cadena contiene los caracteres del patron.
        }//Fin de IsHexa
    */
/*
        public static boolean rangohexa(String x){
            char caractdel = '$';//Declaracion del caracter que se tiene que quitar para hacer la comparacion.
            String newx = x.replace(String.valueOf(caractdel), "");//Se remplaza el caractern a eliminar con une spacio en blanco.
            try{
                int hexaint = Integer.parseInt(newx, 16);//Convierte la cadena de texto a int.
                System.out.println(hexaint);
                return hexaint >= 0 && hexaint <= 0xFFFF;//Compara si el hexadecimal esta en el rango de 0 a 65535
            }catch(NumberFormatException e){//Exepcion para el caso contrario del catch.
                return false;
            }//Fin de try catch
        }//Fin de rangohexa.
*/

        /*
        public static boolean IsOctal(String x){
                char caractdel = '@';//Declaracion del caracter que se tiene que quitar para hacer la comparacion.
                String newx = x.replace(String.valueOf(caractdel), "");//Se remplaza el caractern a eliminar con une spacio en blanco.
                String patronocta = "^[0-7]+$";//Declaracn de la variable que cintene los caracteres para comparar.
                Pattern pattern = Pattern.compile(patronocta);//junta los caracteres en un objeto para ser comparados.
                Matcher matcher = pattern.matcher(newx);//Crea un objeto para contner la cadena de la variable de entrada.
                return matcher.matches();//Da el return verdadero si la cadena contiene los caracteres del patron.
        }//Fin de IsOctal
    */
/*
        public static boolean rangoocta(String x){
            char caractdel = '@';//Declaracion del caracter que se tiene que quitar para hacer la comparacion.
            String newx = x.replace(String.valueOf(caractdel), "");//Se remplaza el caractern a eliminar con une spacio en blanco.
            if(newx.length() > 6){
                return false;
            }

            try{
                int octaint = Integer.parseInt(newx, 8);//Convierte la cadena de texto a int.
                System.out.println(octaint);
                return octaint >= 0 && octaint <= 65535;//Compara si el hexadecimal esta en el rango de 0 a 65535
            }catch(NumberFormatException e){//Exepcion para el caso contrario del catch.
                return false;
            }//Fin de try catch

        }//Fin de rangooctal
*/
    
            /*
        static boolean valespacios(String y){
            String cet2 = ":\t\s";//Caracteres permitidos en la comparacion.
            for(int i=0; i < y.length();i++){//Recorre la cadena y, que se ingresa a la funcion. 
                char vals = y.charAt(i);//Se recorre la cadena caracter por caracter en la pisicion de i.
                if(cet2.indexOf(vals) == -1){//Se revisa si los caracteres si estan en cet2.
                    return false;
                } //Fin de if
            } //Fin de for
            return true;
        }//Fin vlaespacios*/
          
    } //Fin de la clase metodos 
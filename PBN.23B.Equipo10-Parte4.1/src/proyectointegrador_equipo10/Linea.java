/* 
Proyecto Integrador | Programacion de bajo nivel
Equipo 10 | Integrantes: 
    - Hernandez Gutierrez Emmanuel 
    - Jimenez Castellanos Jesus Alejandro
    - Martinez Isaac
*/

package proyectointegrador_equipo10;

public class Linea {
    private String tipo;
    private String valor;
    private String EQUval;
    private String etiqueta; 
    private String codop; //Variable para guardar el codigo operando 
    public String DirAux; //Variable auxiliar para mostrar mensaje en la tabla
    private String operando;  
    private String direccion;
    private String tamaño;
      
    public Linea(String etiqueta, String codop, String operando, String direccion, String tamaño, String DirAux) {
        
        this.tipo = tipo;
        this.valor = valor;
        this.etiqueta = etiqueta;
        this.codop = codop;
        this.operando = operando;
        this.direccion = direccion;
        this.tamaño = tamaño;
        this.DirAux = DirAux; 
    } //Fin de constructor 
    
    //Getters y setters 
    public String getTipo() {     
        
        EQUval = valor;
        
            //Validar ORG
            //No tiene etiqueta
            //Tiene codigo operando
            //Su modo de direccionamiento siempre es DIRECT
        if (codop != null && etiqueta == null && codop.equalsIgnoreCase("ORG") && operando != null)  { //Si encuentra "ORG" con un operando
            System.out.println("entre a org");
            int Conversion = 0;
            valor = operando;
                if(valor.matches("\\d+")){ //Verificar decimal
                    Conversion = Integer.parseInt(valor); //Obtener valor decimal y guardar en Conversion
                } //Fin de if para validar
                else if(valor.matches("%[01]+")) { //Verificar binario
                    Conversion = Integer.parseInt(valor.replace("%",""),2); //Quitar simbolo de binario y evaluar en base 2
                } //Fin de validacion binario
                else if(valor.matches("@[0-7]+")) { //Verificar octal
                    Conversion = Integer.parseInt(valor.replace("@",""),8); //Quitar simbolo de octal y evaluar en base 8
                } //Fin de validacion octal
                else if(valor.matches("\\$[A-F0-9]+")) { //Verificar hexadecimal
                    Conversion = Integer.parseInt(valor.replace("$",""),16); //Quitar simbolo de hexadecimal y evaluar en base 16
                } //Fin de validacion octal
                else {
                    System.out.println("Error");
                    //return "Error OPR";
                } //Fin de validacion 
                
                if(Conversion >= 0 && Conversion <= 65535) { //Evaluar 16 bytes
                    //valor = Integer.toHexString(Conversion).toUpperCase(); //Convierte a Hexadecimal y hace todo a mayusculas
                    String valorHexadecimal = String.format("%04X", Conversion); //Rellena con 0s a la izquierda para cumplir con el formato
                    valor = valorHexadecimal; //Guarda el valor
                    return "DIR_INIC"; //Retorna DIR_INIC
                } //Fin de if
                else {
                    valor = "Desbordamiento";
                    return "Error"; //Devolver Tipo Error
                } //Fin de else 
        } //Fin de if 
             
        //Validar EQU
            //Siempre tiene una etiqueta
            //Tiene codigo operando
            //Su modo de direccionamiento siempre es DIRECT
        else if(codop != null && etiqueta != null && codop.equalsIgnoreCase("EQU") && operando != null) {  
            System.out.println("entre a equ");
            int Conversion = 0;
            valor = operando;
                if(valor.matches("\\d+")){ //Verificar decimal
                    Conversion = Integer.parseInt(valor); //Obtener valor decimal y guardar en Conversion
                } //Fin de if para validar
                else if(valor.matches("%[01]+")) { //Verificar binario
                    Conversion = Integer.parseInt(valor.replace("%",""),2); //Quitar simbolo de binario y evaluar en base 2
                } //Fin de validacion binario
                else if(valor.matches("@[0-7]+")) { //Verificar octal
                    Conversion = Integer.parseInt(valor.replace("@",""),8); //Quitar simbolo de octal y evaluar en base 8
                } //Fin de validacion octal
                else if(valor.matches("\\$[A-F0-9]+")) { //Verificar hexadecimal
                    Conversion = Integer.parseInt(valor.replace("$",""),16); //Quitar simbolo de hexadecimal y evaluar en base 16
                } //Fin de validacion octal
                else {
                    System.out.println("Error");
                    //return "Error OPR";
                } //Fin de validacion 
                
                if(Conversion >= 0 && Conversion <= 65535) { //Evaluar 16 bytes
                    //valor = Integer.toHexString(Conversion).toUpperCase(); //Convierte a Hexadecimal y hace todo a mayusculas
                    String valorHexadecimal = String.format("%04X", Conversion); //Rellena con 0s a la izquierda para cumplir con el formato
                    valor = valorHexadecimal; //Guarda el valor
                    return "VALOR"; //Retorna DIR_INIC
                } //Fin de if
                else {
                    valor = "Desbordamiento";
                    return "Error"; //Devolver Tipo Error
                } //Fin de else
        } //Fin de else if para EQU
        
        /*
        //Validaciones para encontrar y calcular DS.B | DS.W | DC.B | DC.W
        if(codop != null && codop.equalsIgnoreCase("DS.B") && operando.matches("\\d+")){ //Validar que CODOP sea igual a DS.B y que el operando sea decimal
            if(Metodos.IsDecimal(operando)){ //Validar si es operando 
                setTamaño(operando); //Al ser de un byte, se guarda el valor del operando 
            } //Fin de if
        } //Fin de else if
        else if(codop != null && codop.equalsIgnoreCase("DS.W") && operando.matches("\\d+")){ //Validar que CODOP sea igual a DS.W y que el operando sea decimal
            if(Metodos.IsDecimal(operando)){
                setTamaño(Metodos.ad2bytes(operando, 2));
            } //Fin de if
        } //Fin de else if
        else if(codop != null && codop.equalsIgnoreCase("DC.B") && operando != null){
            String[] operandos = codop.split(",");
            setTamaño(String.valueOf(operandos.length)); 
            if(operando.startsWith("")){
                setTamaño(String.valueOf(operando.length()-2));//Si el operando contiene comillas restar 2 del lenght para obtener solo el contenido
            } //Fin de if 
        } //Fin de else 
        else if(codop != null && codop.equalsIgnoreCase("DC.W") && operando != null){
            String[] operandos = codop.split(",");
            setTamaño(String.valueOf(operandos.length*2));            
                if(operando.contains("")){
                    setTamaño(String.valueOf((operando.length()-2)*2));//Si el operando contiene comillas restar 2 del lenght para obtener solo el contenido
                } //Fin de if
        } //Fin de else if
        */
        
        //Devolcer CONTLOC si el codigo operando no esta vacio 
        else if(codop != null) {
            valor = EQUval;
            return "CONTLOC";  
        } //Fin de if 
        return "Error";
    } //Fin de getTipo

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEQUval() {
        return EQUval;
    }

    public void setEQUval(String EQUval) {
        this.EQUval = EQUval;
    }
    
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

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

    public String getDirAux() {
        return DirAux;
    }

    public void setDirAux(String DirAux) {
        this.DirAux = DirAux;
    }

    public String getDireccion() {
        ArchivoSalvacion BD = new ArchivoSalvacion("Salvation.txt"); //Objeto con archivo salvacion
        //TamañoAux = null;
        //DirAux = null;
        
        //Validar Directivas
        
        //Validar operando y establcer direccionamiento como error
        if(getCodop().equals("Error")) { //Si el codigo operando presenta un error, entonces no puede calcular el modo de direccionamiento
            setDirAux("Error DIR"); //Mensaje de error para la tabla
            return("Error");  //Mensaje de error para Direccion
        } //Fin de if
               
        //Validar ORG
            //No tiene etiqueta
            //Tiene codigo operando
            //Su modo de direccionamiento siempre es DIRECT
        if (codop != null && etiqueta == null && codop.equalsIgnoreCase("ORG") && operando != null)  { //Si encuentra "ORG" con un operando
            //setValor(operando);
            setTamaño("0");//No mostrar un mensaje de error, la validacion cumple las condiciones
            setDirAux("DIRECT"); //Variable para mostrar en tabla
            return "DIRECT"; //Retorna el objeto Direccion            
        } //Fin de if 
        
        //Validar END
            //Puede o no tener una etiqueta
            //No tiene codigo operando
            //Su modo de direccionamiento siempre es DIRECT
        else if (codop != null && codop.equalsIgnoreCase("END") && operando == null)  { //Si encuentra "END" sin un operando
            setTamaño("0"); //No mostrar un mensaje de error, la validacion cumple las condiciones
            setDirAux("DIRECT");            
            return "DIRECT"; //Retorna el objeto Direccion 
        } //Fin de else if para END
                
        //Validar EQU
            //Siempre tiene una etiqueta
            //Tiene codigo operando
            //Su modo de direccionamiento siempre es DIRECT
        else if(codop != null && etiqueta != null && codop.equalsIgnoreCase("EQU") && operando != null) {
            setTamaño("0"); //No mostrar un mensaje de error, la validacion cumple las condiciones
            setDirAux("DIRECT");            
            return "DIRECT"; //Retorna el objeto Direccion
        } //Fin de else if para EQU
        
        //Verificar DIRECT
        else if (("DS.B".equalsIgnoreCase(codop) || "DS.W".equalsIgnoreCase(codop) || "DC.B".equalsIgnoreCase(codop) || "DC.W".equalsIgnoreCase(codop))) { //Verificar que si el codop es ORG o END devuelva DIRECT
            setDirAux("DIRECT"); //Asignar el valor que se imprimirÃ¡ en la tabla
            return "DIRECT";
        } //Fin de if 
        
        //Validar Modos de direccionamiento
        
        // Comprobar si es un INH (sin operando)
        else if (operando == null) { //Si el operando es nulo, entonces se considera INH            
            setDirAux("INH");
            return "INH";
        } //Fin de validar INH 

        else if (operando != null) {
            // Comprobar el tipo de direccionamiento Inmediato (IMM)
            if (operando.startsWith("#")) {
                  String operandoSinNumeral = operando.substring(1); // Quitar el símbolo "#" del operando
                if (operandoSinNumeral.startsWith("$")) {
                    // Comprobar si el operando es hexadecimal (#$)
                    String valorHexadecimal = operandoSinNumeral.substring(1);
                    try {
                        int valor = Integer.parseInt(valorHexadecimal, 16);                       
                        
                        if (valor >= 0 && valor <= 255) {
                            setDirAux("IMM(8b)"); //Variable para mostrar en tabla
                            return "IMM"; //Retorna el objeto Direccion 
                        }//fin de if
                        else if (valor >= 256 && valor <= 65535) {
                            for(int i = 0; i <=592; i++) {
                                if("#opr8i".equals((BD.PosicionMatriz(i, 1)))){
                                   setDirAux("Error DIR");
                                   return "Error"; 
                                } //Fin de if                 
                            }//fin de for
                            setDirAux("IMM(16b)");
                            return "IMM";
                        } //Fin de else    
                    }//fin de try //fin de try 
                    catch (NumberFormatException e) {
                        // No es un valor hexadecimal válido
                    }//fin de catch
                }//fin de if 
                
            else if (operandoSinNumeral.startsWith("@")) {
            // Comprobar si el operando es octal (#@)
                String valorOctal = operandoSinNumeral.substring(1);
                try {
                    int valor = Integer.parseInt(valorOctal, 8);
                    if (valor >= 0 && valor <= 255) {
                        setDirAux("IMM(8b)");
                        return "IMM";
                    }//fin de if
                    else if (valor >= 256 && valor <= 65535) {
                        for(int i = 0; i <=592; i++) {
                                if("#opr8i".equals((BD.PosicionMatriz(i, 1)))){
                                    setDirAux("Error DIR");
                                   return "Error"; 
                                } //Fin de if                 
                            }//fin de for
                        setDirAux("IMM(16b)");
                        return "IMM";
                    }//fin de else if
                }//fin de try//fin de try
                    catch (NumberFormatException e) {
                       // No es un valor octal válido
                   }//fin de catch
            }//fin de else if  

            else if (operandoSinNumeral.startsWith("%")) {
            // Comprobar si el operando es binario (#%)
                String valorBinario = operandoSinNumeral.substring(1);
                if (Metodos.IsBinario(valorBinario)) {
                    int valor = Integer.parseInt(valorBinario, 2);
                    if (valor >= 0 && valor <= 255) {
                        setDirAux("IMM(8b)");
                        return "IMM";
                    }//fin de if
                    else if (valor >= 256 && valor <= 65535) {
                        for(int i = 0; i <=592; i++) {
                            if("#opr8i".equals((BD.PosicionMatriz(i, 1)))){
                                setDirAux("Error DIR");
                                return "Error"; 
                            } //Fin de if                 
                        }//fin de for
                        setDirAux("IMM(16b)");
                        return "IMM";
                    }//fin de else if
                }//fin de if
            }//fin de else if 

            else if (Metodos.IsDecimal(operandoSinNumeral)) {
            // Comprobar si el operando es decimal (#)
                int valorDecimal = Integer.parseInt(operandoSinNumeral);
                if (valorDecimal >= 0 && valorDecimal <= 255) {
                    setDirAux("IMM(8b)");
                    return "IMM";
                }//fin de if
                else if (valorDecimal >= 256 && valorDecimal <= 65535) {
                    for(int i = 0; i <=592; i++) {
                        if("#opr8i".equals((BD.PosicionMatriz(i, 1)))){
                            setDirAux("Error DIR");
                            return "Error"; 
                        } //Fin de if                 
                    }//fin de for
                    setDirAux("IMM(16b)");
                    return "IMM";
                }//fin de else if
            }//fin de else if
        
            // Comprobar si el operando es decimal
            else if (Metodos.IsDecimal(operandoSinNumeral)) {
                int valorDecimal = Integer.parseInt(operandoSinNumeral);
                if (valorDecimal >= 0 && valorDecimal <= 255) {
                    setDirAux("IMM(8b)");
                    return "IMM";
                }//fin de if 
                else if (valorDecimal >= 256 && valorDecimal <= 65535) {
                    setDirAux("IMM(16b)");
                    return "IMM";
                }//fin de else if
            } //fin de else if
            } //fin if de IMM

            // Comprobar el tipo de direccionamiento Directo (DIR)
            if (operando.matches("^[#@$]?[0-9]+$|^%[0-1]{1,8}$")) {
                // Quitar el símbolo "#" u otros caracteres iniciales si están presentes
                String operandoSinSimbolo = operando.replaceAll("^[#@$]+", "");

                // Procesar el operando como un valor hexadecimal o binario
                try {
                    int valor;
                    if (operandoSinSimbolo.startsWith("%")) {
                       // Si comienza con "%" es un valor binario
                       valor = Integer.parseInt(operandoSinSimbolo.substring(1), 2);
                    } //Fin de if
                    else {
                       // De lo contrario, es un valor decimal
                       valor = Integer.parseInt(operandoSinSimbolo, 16);
                    } //Fin de else 
                    if (valor >= 0 && valor <= 255) {
                        setDirAux("DIR");
                       return "DIR";
                    } //Fin de if 
                } //Fin de try
                catch (NumberFormatException e) {
                   // No es un valor válido
                } //Fin de catch
            } //Fin de else if

            // Comprobar el tipo de direccionamiento Extendido (EXT)
            if (operando.matches("^[#@$]?+[0-9A-Fa-f]+$|^%[0-1]{8}$")) {
                // Quitar el símbolo "#" u otros caracteres iniciales si están presentes
                String operandoSinSimbolo = operando.replaceAll("^[#@$]+", "");

                // Procesar el operando como un valor hexadecimal
                try {
                    int valor;
                    if (operandoSinSimbolo.startsWith("%")) {
                        // Si comienza con "%" es un valor binario
                        valor = Integer.parseInt(operandoSinSimbolo.substring(1), 2);
                    } //Fin de if
                    else {
                       // De lo contrario, es un valor hexadecimal
                       valor = Integer.parseInt(operandoSinSimbolo, 16);
                   } //Fin de else
                   if (valor >= 256 && valor <= 65535) {
                       setDirAux("EXT");
                       return "EXT";
                   } //Fin de if
                } //Fin de try
                catch (NumberFormatException e) {
                   // No es un valor válido
                } //Fin de catch
            } //Fin de if

            // Comprobar el tipo de direccionamiento Indexado de 5 bits (IDX)
            if (operando.matches("^\\d+,((X|x|Y|y|SP|sp|PC|pc))$")) {
                String[] parts = operando.split(",");
                int valorIndexado = Integer.parseInt(parts[0]);
                if (valorIndexado >= -16 && valorIndexado <= 15) {
                    setDirAux("IDX(5b)");
                    return "IDX";
                }// Comprobar el tipo de direccionamiento Indexado de 9 bits (IDX)
                else if ((valorIndexado >= -256 && valorIndexado <= -17) || (valorIndexado >= 16 && valorIndexado <= 255)) {
                    setDirAux("IDX1");
                    return "IDX1";
                } //Fin de else if
            } //Fin de else if

            // Comprobar el tipo de direccionamiento Indexado indirecto de 16 bits (IDX2)
            if (operando.matches("^\\d+,((X|x|Y|y|SP|sp|PC|pc))$")) {
                String[] parts = operando.split(",");
                int valorIndexado = Integer.parseInt(parts[0]);
                if (valorIndexado >= 256 && valorIndexado <= 65535) {
                    setDirAux("IDX2");
                    return "IDX2";
                } //Fin de if 
            } //Fin de if

               // Comprobar el tipo de direccionamiento Indexado indirecto de 16 bits ([IDX2])
            if (operando.matches("^\\[\\d{1,5},((X|x|Y|y|SP|sp|PC|pc))+\\]$")) {
                // Extraer el valor dentro de los corchetes y comprobar si está en el rango de 0 a 65535
                String operandoSinCorchetes = operando.substring(1, operando.length() - 1);
                String[] parts = operandoSinCorchetes.split(",");
                int valorIndexado = Integer.parseInt(parts[0]);
                if (valorIndexado >= 0 && valorIndexado <= 65535) {
                    setDirAux("[IDX2]");
                    return "[IDX2]";
                } //Fin de else 
            } //Fin de else if

            // Comprobar el tipo de direccionamiento Indexado predecremento(IDX)
            if (operando.matches("^[1-8],([-](X|x|Y|y|SP|sp))$")) {
                setDirAux("IDX(PreDec)");
                return "IDX";
            } //Fin de else 
            
            // Comprobar el tipo de direccionamiento Indexado postdecremento(IDX)
            if (operando.matches("^[1-8],([+](X|x|Y|y|SP|sp))$")) {
                setDirAux("IDX(PreInc)");
                return "IDX";
            } //Fin de else

            // Comprobar el tipo de direccionamiento Indexado postdecremento(IDX)
            if (operando.matches("^[1-8],((X|x|Y|y|SP|sp)[-])$")) {
                DirAux = "IDX(PostDec)";
                return "IDX";
            } //Fin de else if
            
            // Comprobar el tipo de direccionamiento Indexado postincremento (IDX)
            if (operando.matches("^[1-8],((X|x|Y|y|SP|sp)[+])$")) {
                DirAux = "IDX(PostInc)";
                return "IDX";
            } //Fin de else if

            // Comprobar el tipo de direccionamiento Indexado de acumulador (IDX)
            if (operando.matches("^(A|a|B|b|D|d),(X|x|Y|y|SP|sp|PC|pc)$")) {
                DirAux = "IDX(Acc)";
                return "IDX";
            }//fin de else if
            
            // Comprobar el tipo de direccionamiento Indexado acumulador indirecto ([D,IDX])
            //if (operando.matches("^\\[\\d{1,5},((X|x|Y|y|SP|sp|PC|pc))+\\]$"))
            if (operando.matches("\\[[Dd],(?i)(X|Y|SP|PC)\\]")) {
                setDirAux("[D,IDX]");
                return "[D,IDX]";
            }//fin de else if

            //Relativo REL
            if (operando.matches("^[a-zA-Z_][a-zA-Z0-9_]{0,7}$|^-?\\d{0,8}$")) {
            // Comprobar si el operando es una etiqueta válida o un valor decimal en el rango adecuado
                if (Metodos.ComprobarEtiqueta(operando)) {
                    for(int i = 0; i<=592; i++) {
                        if(getCodop().equals(BD.PosicionMatriz(i, 0)) && "2".equals(BD.PosicionMatriz(i, 5))) {
                            setDirAux("REL(8b)");
                            return "REL";
                        } //Fin de if 

                        else if(getCodop().equals(BD.PosicionMatriz(i, 0)) && "4".equals(BD.PosicionMatriz(i, 5))) {
                            setDirAux("REL(16b)");
                            return "REL";
                        } //Fin de else if  
                    } //Fin de for                                       
                } //Fin de if
                
                int valorDecimal = Integer.parseInt(operando);
                if (valorDecimal >= -128 && valorDecimal <= 127) { //REL 8bit
                    setDirAux("REL(8b)");
                    return "REL";
                } //Fin de if
                else if (valorDecimal >= -32768 && valorDecimal <= 32767) { //REL 16bit
                    setDirAux("REL(16b)");
                    return "REL";
                } //Fin else if
            } //Fin de else if
            
            //Rel con ciclo 
            if (operando.matches("^(A|a|B|b|D|d|X|x|Y|y|SP|sp|PC|pc),[a-zA-Z_][a-zA-Z0-7_]+$")) {
                String[] partes = operando.split(",");
                String registro = partes[0].toUpperCase(); // Convertir el registro a mayúsculas para hacer comparaciones sin distinción de mayúsculas y minúsculas
                String resto = partes[1].trim(); // Eliminar espacios en blanco antes y después de la parte después de la coma
                
                if(Metodos.ComprobarEtiqueta(resto)) {
                    setDirAux("REL(9-bit)"); //Relativo con ciclo 
                    return "REL(9-bit)";
                }//Fin de if
                
                // Verificar si el registro es válido
                if (registro.matches("^[[A-a]|[B-b]|[D-d]|[X-x]|[Y-y]|[SP-sp]|[PC-pc]]$")) {
                    // Verificar si la parte después de la coma es un valor numérico o una palabra válida
                    if (Metodos.IsDecimal(resto) || Metodos.IsBinario(resto) || Metodos.IsOctal(resto) || Metodos.IsHexadecimal(resto) || Metodos.ComprobarEtiqueta(resto)) {
                        //return "Relativo con ciclo (REL) de " + (resto.length() <= 2 ? "8" : "16") + " bits";
                        setDirAux("REL(9-bit)"); //Relativo con ciclo 
                        return "REL(9-bit)";
                    } //Fin de if
                } //Fin de if 
            } //Fin de else if       
        } //Fin de else if
        
        //Devolver errores
        
        //Si no entra en ningunna de las posibles opciones, entonces el operando esta erroneo y se devuelve mensaje de error
        setOperando("Error OPR"); //Mensaje de confirmacion

        //Si no entra en ningunna de las posibles opciones, el modo de direccionamiento se devuelve como error
        setDirAux("Error DIR"); //Mensaje de confirmacion    
        return "Error"; // No se reconoce ningún tipo de direccionamiento      
   } //Fin de public String 
    
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
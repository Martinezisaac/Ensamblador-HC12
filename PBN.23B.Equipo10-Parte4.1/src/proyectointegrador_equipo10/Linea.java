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
    private String postbyte;
      
    public Linea(String etiqueta, String codop, String operando, String direccion, String tamaño, String DirAux, String Postbyte) {
        
        this.tipo = tipo;
        this.valor = valor;
        this.etiqueta = etiqueta;
        this.codop = codop;
        this.operando = operando;
        this.direccion = direccion;
        this.tamaño = tamaño;
        this.DirAux = DirAux; 
        this.postbyte = postbyte;
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
                
                if (operando.contains("#$") || operando.contains("#@") || operando.contains("#%")) { //Validar sistemas numericos para hexadecimal, octal y binario                    
                    String ValorOperando = operando.substring(2); // Quitar el símbolo "#" y "%|$|@" del operando    
                    if (ValorOperando.matches("([01]+)|([0-9A-Fa-f]+)|([0-7]+)")) {
                    int valor = Integer.parseInt(ValorOperando, 16); 
                        try {
                            if(operando.contains("#$")){
                                valor = Integer.parseInt(ValorOperando, 16); 
                            } //Fin de if
                            else if(operando.contains("#@")){
                                valor = Integer.parseInt(ValorOperando, 8); 
                            } //Fin de if
                            else if(operando.contains("#%")){                           
                                valor = Integer.parseInt(ValorOperando, 2); 
                            } //Fin de if
                                                                         
                            if (valor >= 0 && valor <= 255) {
                                for(int i = 0; i <=592; i++) {
                                    if(codop.equals(BD.PosicionMatriz(i, 0)) && "#opr8i".equals((BD.PosicionMatriz(i, 1)))){
                                        setDirAux("IMM(8b)"); //Variable para mostrar en tabla
                                        return "IMM"; //Retorna el objeto Direccion
                                    } //Fin de if
                                    else if(codop.equals(BD.PosicionMatriz(i, 0)) && "#opr16i".equals((BD.PosicionMatriz(i, 1))))  {
                                        setDirAux("IMM(16b)");
                                        return "IMM"; 
                                    } //Fin de else     
                                }//fin de for                            
                            }//fin de if
                            else if (valor >= 256 && valor <= 65535) {
                                for(int i = 0; i <=592; i++) {
                                    if(codop.equals(BD.PosicionMatriz(i, 0)) && "#opr8i".equals((BD.PosicionMatriz(i, 1)))){
                                        setDirAux("Error DIR");
                                        return "Error"; 
                                    } //Fin de if
                                    else if(codop.equals(BD.PosicionMatriz(i, 0)) && "#opr16i".equals((BD.PosicionMatriz(i, 1))))  {
                                        setDirAux("IMM(16b)");
                                        return "IMM"; 
                                    } //Fin de else     
                                }//fin de for                         
                            } //Fin de else if 
                        }//fin de try
                        catch (NumberFormatException e) {
                            // No es un valor hexadecimal válido
                        }//fin de catch                   
                } //Fin de if
                } //Fin de matcher
                
                if(operando.contains("#")) { //Validar sistema numerico decimal
                    String ValorOperando = operando.substring(1); // Quitar el símbolo "#" del operando
                        try {
                            int valor = Integer.parseInt(ValorOperando);                       
                        
                            if (valor >= 0 && valor <= 255) {
                                for(int i = 0; i <=592; i++) {
                                    if(codop.equals(BD.PosicionMatriz(i, 0)) && "#opr8i".equals((BD.PosicionMatriz(i, 1)))){
                                        setDirAux("IMM(8b)"); //Variable para mostrar en tabla
                                        return "IMM"; //Retorna el objeto Direccion
                                    } //Fin de if
                                    else if(codop.equals(BD.PosicionMatriz(i, 0)) && "#opr16i".equals((BD.PosicionMatriz(i, 1))))  {
                                        setDirAux("IMM(16b)");
                                        return "IMM"; 
                                    } //Fin de else     
                                }//fin de for                            
                            }//fin de if
                            else if (valor >= 256 && valor <= 65535) {
                                for(int i = 0; i <=592; i++) {
                                    if(codop.equals(BD.PosicionMatriz(i, 0)) && "#opr8i".equals((BD.PosicionMatriz(i, 1)))){
                                        setDirAux("Error DIR");
                                        return "Error"; 
                                    } //Fin de if
                                    else if(codop.equals(BD.PosicionMatriz(i, 0)) && "#opr16i".equals((BD.PosicionMatriz(i, 1))))  {
                                       setDirAux("IMM(16b)");
                                       return "IMM"; 
                                    } //Fin de else                                
                                }//fin de for                         
                            } //Fin de else if   
                        }//fin de try
                        catch (NumberFormatException e) {
                            // No es un valor hexadecimal válido
                        }//fin de catch
                } //Fin de else if               
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
            if (operando.matches("^-?\\d+,((X|x|Y|y|SP|sp|PC|pc))$")) {
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
            
            if (operando.matches("^,((X|x|Y|y|SP|sp|PC|pc))$")) { //Validar en caso de que la primera parte del operando no exista
                setDirAux("IDX(5b)");
                return "IDX";
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

    public String getPostbyte() {
        /*
        Para calcular postbyte podemos hacer uso de algunas validaciones que tenemos anteriormente para asignar el postbyte en caso de que este correcto, caso contrario
        marcamos un "Error Postbyte". Basicamente:           
            - Si el operando esta mal, Direccion esta mal porque no se puede asignar
            - Si direccion esta mal, Tamaño se asigna a "0" por lo que la linea leida no afecta al contador de programa
            - Las directivas tienen tamaño "0", no afecta al contador de programa y por ello sigue sumando sin ser afectado */
        
        ArchivoSalvacion BD = new ArchivoSalvacion("Salvation.txt"); //Objeto con archivo salvacion
        int Conversion = 0; //Variable auxiliar
        
        for(int i = 0; i <= 592; i++) { //Recorrer Archivo salvacion para todas las validaciones
        
        /* Utilzimos 3 validaciones en cada if
            - Validacion 1 | Si el modo de direccionamiento coincide con el archivo salvacion
            - Validacion 2 | Si el CODOP coincide con el archivo salvacion
            - Validacion 3 | Si las dos validaciones anteriores son correctas enotnces procede a buscar caracteres dentro de la forma del operando, los cuales
                       iremos sustituyendo a medida que el codigo avanza */
        
        //Ignorar directivas ORG, EQU y END
        if(codop.equals("ORG") || codop.equals("EQU") || codop.equals("END")) {
            return ""; //Devolver postbyte
        } //Fin de if                
        
        //Calcular Inherentes
            //Los inherentes no se calculan puesto que no tienen operandos, su codigo postbyte ya viene por defecto
        
        //Calcular Inmediatos 8 bits (ii)
        if(DirAux.equals("IMM(8b)") && codop.equals(BD.PosicionMatriz(i, 0)) && BD.PosicionMatriz(i, 3).contains("ii")) {
                String ValorOperando = operando; //Variable auxiliar para obtener operando
                
                //En estas validaciones quitamos # de todos los sistemas numericos, principal identificador para los inmediatos

                if(ValorOperando.matches("#\\d+")){ //Verificar decimal
                    Conversion = Integer.parseInt(ValorOperando.replace("#","")); //Obtener valor decimal y guardar en Conversion
                } //Fin de if para validar
                else if(ValorOperando.matches("#%[01]+")) { //Verificar binario
                    Conversion = Integer.parseInt(ValorOperando.replace("#%",""),2); //Quitar simbolo de binario y evaluar en base 2
                } //Fin de validacion binario
                else if(ValorOperando.matches("#@[0-7]+")) { //Verificar octal
                    Conversion = Integer.parseInt(ValorOperando.replace("#@",""),8); //Quitar simbolo de octal y evaluar en base 8
                } //Fin de validacion octal
                else if(ValorOperando.matches("#\\$[A-F0-9]+")) { //Verificar hexadecimal
                    Conversion = Integer.parseInt(ValorOperando.replace("#$",""),16); //Quitar simbolo de hexadecimal y evaluar en base 16
                } //Fin de validacion octal
                else {
                    System.out.println("Error");
                    return "Error Postbyte";
                    //return "Error OPR";
                } //Fin de validacion 

                String ValorHexadecimal = String.format("%02x", Conversion).toUpperCase(); //Colocar formato de dos digitos (rellenar con 0 en caso de)
                postbyte = BD.PosicionMatriz(i,3).replace("ii", ValorHexadecimal); //Establecer postbyte
                //return postbyte;
            } //Fin de validar inmediatos de 8 bits
        
        //Calcular Inmediatos 16 bits (jj kk)
        if(DirAux.equals("IMM(16b)") && codop.equals(BD.PosicionMatriz(i, 0)) && BD.PosicionMatriz(i, 3).contains("jj kk")) {
                String ValorOperando = operando; //Variable auxiliar para obtener operando
                
                //En estas validaciones quitamos # de todos los sistemas numericos, principal identificador para los inmediatos

                if(ValorOperando.matches("#\\d+")){ //Verificar decimal
                    Conversion = Integer.parseInt(ValorOperando.replace("#","")); //Obtener valor decimal y guardar en Conversion
                } //Fin de if para validar
                else if(ValorOperando.matches("#%[01]+")) { //Verificar binario
                    Conversion = Integer.parseInt(ValorOperando.replace("#%",""),2); //Quitar simbolo de binario y evaluar en base 2
                } //Fin de validacion binario
                else if(ValorOperando.matches("#@[0-7]+")) { //Verificar octal
                    Conversion = Integer.parseInt(ValorOperando.replace("#@",""),8); //Quitar simbolo de octal y evaluar en base 8
                } //Fin de validacion octal
                else if(ValorOperando.matches("#\\$[A-F0-9]+")) { //Verificar hexadecimal
                    Conversion = Integer.parseInt(ValorOperando.replace("#$",""),16); //Quitar simbolo de hexadecimal y evaluar en base 16
                } //Fin de validacion octal
                else {
                    System.out.println("Error");
                    return "Error Postbyte";
                    //return "Error OPR";
                } //Fin de validacion 

                String ValorHexadecimal = String.format("%04x", Conversion).toUpperCase(); //Colocar formato de dos digitos (rellenar con 0 en caso de)
                String ValorSeparado = ValorHexadecimal.replaceAll("(.{2})", "$1 ").trim(); //Colocar espacio por cada dos caracteres con una variable auxiliar
                postbyte = BD.PosicionMatriz(i,3).replace("jj kk", ValorSeparado); //Establecer postbyte
                //return postbyte;
            } //Fin de validar inmediatos de 16bits
        
        //Calcular Directos (dd)
         if(DirAux.equals("DIR") && codop.equals(BD.PosicionMatriz(i, 0)) && BD.PosicionMatriz(i, 3).contains("dd")) {
                String ValorOperando = operando; //Variable auxiliar para obtener operando

                if(ValorOperando.matches("\\d+")){ //Verificar decimal
                    Conversion = Integer.parseInt(ValorOperando); //Obtener valor decimal y guardar en Conversion
                } //Fin de if para validar
                else if(ValorOperando.matches("%[01]+")) { //Verificar binario
                    Conversion = Integer.parseInt(ValorOperando.replace("%",""),2); //Quitar simbolo de binario y evaluar en base 2
                } //Fin de validacion binario
                else if(ValorOperando.matches("@[0-7]+")) { //Verificar octal
                    Conversion = Integer.parseInt(ValorOperando.replace("@",""),8); //Quitar simbolo de octal y evaluar en base 8
                } //Fin de validacion octal
                else if(ValorOperando.matches("\\$[A-F0-9]+")) { //Verificar hexadecimal
                    Conversion = Integer.parseInt(ValorOperando.replace("$",""),16); //Quitar simbolo de hexadecimal y evaluar en base 16
                } //Fin de validacion octal
                else {
                    System.out.println("Error");
                    return "Error Postbyte";
                    //return "Error OPR";
                } //Fin de validacion 

                String ValorHexadecimal = String.format("%02x", Conversion).toUpperCase(); //Colocar formato de dos digitos (rellenar con 0 en caso de)
                postbyte = BD.PosicionMatriz(i,3).replace("dd", ValorHexadecimal); //Establecer postbyte
                //return postbyte;
            } //Fin de validar Directos
        
        //Calcular Extendidos (hh ll)
        
                    if(DirAux.equals("EXT") && codop.equals(BD.PosicionMatriz(i, 0)) && BD.PosicionMatriz(i, 3).contains("hh ll")) {
                String ValorOperando = operando; //Variable auxiliar para obtener operando

                if(ValorOperando.matches("\\d+")){ //Verificar decimal
                    Conversion = Integer.parseInt(ValorOperando); //Obtener valor decimal y guardar en Conversion
                } //Fin de if para validar
                else if(ValorOperando.matches("%[01]+")) { //Verificar binario
                    Conversion = Integer.parseInt(ValorOperando.replace("%",""),2); //Quitar simbolo de binario y evaluar en base 2
                } //Fin de validacion binario
                else if(ValorOperando.matches("@[0-7]+")) { //Verificar octal
                    Conversion = Integer.parseInt(ValorOperando.replace("@",""),8); //Quitar simbolo de octal y evaluar en base 8
                } //Fin de validacion octal
                else if(ValorOperando.matches("\\$[A-F0-9]+")) { //Verificar hexadecimal
                    Conversion = Integer.parseInt(ValorOperando.replace("$",""),16); //Quitar simbolo de hexadecimal y evaluar en base 16
                } //Fin de validacion octal
                else {
                    System.out.println("Error");
                    return "Error Postbyte";
                    //return "Error OPR";
                } //Fin de validacion 

                String ValorHexadecimal = String.format("%04x", Conversion).toUpperCase(); //Colocar formato de dos digitos (rellenar con 0 en caso de)
                String ValorSeparado = ValorHexadecimal.replaceAll("(.{2})", "$1 ").trim(); //Colocar espacio por cada dos caracteres con una variable auxiliar
                postbyte = BD.PosicionMatriz(i,3).replace("hh ll", ValorSeparado); //Establecer postbyte
                //return postbyte;
            } //Fin de validar Extendidos
        
        //Calcular Directiva DS
        
        //Calcular Directiva DC
        
        //Calcular Indexados
        //Calcular Indexados(5b) (xb)
        if(DirAux.equals("IDX(5b)") && codop.equals(BD.PosicionMatriz(i, 0)) && "oprx0_xysp".equals(BD.PosicionMatriz(i, 1)) &&BD.PosicionMatriz(i, 3).contains("xb")) {
            String FormaXB5 = "rr0nnnnn"; //Declaramos la forma para calcular, al ya tener validado el modo de direccionamiento podemos declarar una variable auxiliara para cuando entre a este caso con la forma a calcular
            //Variables auxiliares para calcular los indexados
            String Binario = null; //Variable auxiliar para guardar binarios
            String[] partes = operando.split(","); //Dividir en dos el operando separandolo por la coma
            String ValorNumerico = partes[0]; //Parte 1 con el valor numerico
            String ValorRegistro = partes[1]; //Parte 2 con los registros
            
            //Calcular "rr" | Registros
            if (ValorRegistro.equalsIgnoreCase("X")) { //Validar el registro X
                FormaXB5 = FormaXB5.replace("rr", "00"); //Establecer valor del registro X = 00
            } //Fin para validar registro X 
            else if (ValorRegistro.equalsIgnoreCase("Y")) { //Validar el registro Y
                FormaXB5 = FormaXB5.replace("rr", "01"); //Establecer valor del registro Y = 01
            } //Fin de validar el registro Y
            else if(ValorRegistro.equalsIgnoreCase("SP")) { //Validar el registro SP
                FormaXB5 = FormaXB5.replace("rr", "10"); //Establecer valor del registro Y = 10
            } //Fin de validar el registro SP
            else if(ValorRegistro.equalsIgnoreCase("PC")) { //Validar el registro PC
                FormaXB5 = FormaXB5.replace("rr", "11"); //Establecer valor del registro PC = 11
            } //Fin de validar para el registro PC 
            else {
                System.out.println("Error"); //Si no concuerda con ninguna de las estructuras de las bases
                return "Error Postbyte";
            } //Fin de else 
            
            //Con estas validaciones se sustituye rr de la forma del xb
            //rr0nnnnn -> rr = 00 | rr = 01 | rr = 10 | rr = 11
            
            //Calcular "nnnnn" | Valor Numerico            
            if(operando.startsWith(",")) { //Validar que exista algo dentro de la primera parte del operando (un valor antes de la coma)
                FormaXB5 = FormaXB5.replace("nnnnn", "00000"); //Establecer valor deafult con 00000, no existe la primera parte del operando 
            } //Fin de validacion en caso de que no xista nada en la primera parte del operando
            else { //Entonces si hay un valor 
                int ValorDecimal = Integer.parseInt(ValorNumerico); //Declarar variable auxiliar para obtener el decimal del valor numerico 
                Binario = Integer.toBinaryString(ValorDecimal); //Convertir el valor decimal a un valor numerico 
                
                if(ValorDecimal < 0) { //Validar en caso de que la primera parte del operando es negativo
                    Binario = Binario.substring(Binario.length()-5); //Si es negativo, toma los ultimos 5 valores de la cadena
                } //Fin de validar en caso de que el valor decimal sea negativo
             
                String BinarioCompleto = String.format("%5s", Binario).replace(' ', '0'); //Colocar formato de cinco digitos (rellenar con 0 en caso de tener espacios con blanco)                                
                FormaXB5 = FormaXB5.replace("nnnnn", BinarioCompleto); //SustituciÃ³n de nnnnn por el binario calculado
            } //Fin de else para saber si hay un valor en la primera parte del operando                             
            
            int ConversionDecimal = Integer.parseInt(FormaXB5, 2); //Convertir de binario a decimal             
            String ValorConvertidoDecimal = String.format("%02x", ConversionDecimal).toUpperCase(); //Colocar formato de dos digitos (rellenar con 0 en caso de)
            String ValorSeparado = ValorConvertidoDecimal.replaceAll("(.{2})", "$1 ").trim(); //Colocar espacio por cada dos caracteres con una variable auxiliar
            postbyte = BD.PosicionMatriz(i,3).replace("xb", ValorSeparado); //Establecer postbyte
            //postbyte = FormaXB5; //Validar forma en la tabla
        } //Fin de if para calcular IDX(5b)
        
        /*
        //Calcular IDX5B
        if(DirAux.equals("IDX(5b)") && codop.equals(BD.PosicionMatriz(i, 0)) && BD.PosicionMatriz(i, 3).endsWith("xb")) {
            String[] parts = operando.split(",");
            int valorIndexado = Integer.parseInt(parts[0]);
            if(!operando.matches("(-16|[\\-0-9]|1[0-5]),(X|Y|SP|PC)")&&(valorIndexado >= -16 && valorIndexado <= 15)){
                
                return "Error 1";
            }
            System.out.println("opernado " + operando);
            System.out.println("valor de la tabal A-3 "+ Metodos.ta3(operando));
            postbyte =  BD.PosicionMatriz(i,3).replace("xb", Metodos.ta3(operando));
        }//Fin IDX5B
        */
        
        //Calcular indexados(9b -> IDX1) (xb ff)
        if(DirAux.equals("IDX1") && codop.equals(BD.PosicionMatriz(i, 0)) && BD.PosicionMatriz(i, 3).contains("xb ff")) {
            //Variables auxiliares para calcular los indexados
            String FormaXB9 = "111rr0zs"; //Declaramos la forma para calcular, al ya tener validado el modo de direccionamiento podemos declarar una variable auxiliara para cuando entre a este caso con la forma a calcular
            String ValorFF = null; //Variable auxiliar para calcular ff
            String Binario = null; //Variable auxiliar para guardar binarios
            String[] partes = operando.split(","); //Dividir en dos el operando separandolo por la coma
            String ValorNumerico = partes[0]; //Parte 1 con el valor numerico
            String ValorRegistro = partes[1]; //Parte 2 con los registros
            
            //Calcular "rr" | Registros
            if (ValorRegistro.equalsIgnoreCase("X")) { //Validar el registro X
                FormaXB9 = FormaXB9.replace("rr", "00"); //Establecer valor del registro X = 00
            } //Fin para validar registro X 
            else if (ValorRegistro.equalsIgnoreCase("Y")) { //Validar el registro Y
                FormaXB9 = FormaXB9.replace("rr", "01"); //Establecer valor del registro Y = 01
            } //Fin de validar el registro Y
            else if(ValorRegistro.equalsIgnoreCase("SP")) { //Validar el registro SP
                FormaXB9 = FormaXB9.replace("rr", "10"); //Establecer valor del registro Y = 10
            } //Fin de validar el registro SP
            else if(ValorRegistro.equalsIgnoreCase("PC")) { //Validar el registro PC
                FormaXB9 = FormaXB9.replace("rr", "11"); //Establecer valor del registro PC = 11
            } //Fin de validar para el registro PC 
            else {
                System.out.println("Error"); //Si no concuerda con ninguna de las estructuras de las bases
                return "Error Postbyte";
            } //Fin de else
            
            //Calcular z | En este caso ya sabemos que es de 9 bits, por lo tando z = 0
            FormaXB9 = FormaXB9.replace("z","0"); //Reemplazar z por 0 en la forma
            
            //Calcular s | if z = s = 1 -> En pocas palabras, si el numero es negativo entonces s = 1, caso contrario s = 0
            int ValorDecimal = Integer.parseInt(ValorNumerico); //Declarar variable auxiliar con valor decimal        
            if(ValorDecimal < 0) { //Validar si la primera parte del operando es negatica
                FormaXB9 = FormaXB9.replace("s","1"); //Reemplazar s por 1 en la forma                                
            } //Fin de if para validar si el numero es negatico
            else { //Entonces la primera parte del operando es positiva
                FormaXB9 = FormaXB9.replace("s","0"); //Reemplazar z por 0 en la forma
            } //Finde if para validar si el numero es negatico
            //Hasta este punto la forma ya esta calculada
                     
            //En este punto la forma ya esta calculada, por lo que podemos obtener XB | Convertir a decimal la forma xb
            int ConversionDecimal = Integer.parseInt(FormaXB9, 2); //Convertir de binario a decimal 
            String ValorConvertidoDecimal = String.format("%02x", ConversionDecimal).toUpperCase(); //Colocar formato de dos digitos (rellenar con 0 en caso de)
            String ValorSeparado = ValorConvertidoDecimal.replaceAll("(.{2})", "$1 ").trim(); //Colocar espacio por cada dos caracteres con una variable auxiliar
            
            //Calcular ff | Primera parte del operando 
            if(ValorDecimal < 0) { //Validar si la primera parte del operando es negativa
                Binario = Integer.toBinaryString(ValorDecimal); //Convertir a binario el valor decimal
                Binario = Binario.substring(Binario.length()-8); //Si es negativo, toma los ultimos 8 valores de la cadena
                ConversionDecimal = Integer.parseInt(Binario, 2); //Convertir de binario a decimal
                ValorFF = String.format("%02x", ConversionDecimal).toUpperCase(); //Asignar el valor decimal a FF y completar con 0 en caso de necesitarlo
            } //Fin de if para validar si el numero es negatico
            else { //Entonces la primera parte del operando es positiva
                ValorFF = String.format("%02x", ValorDecimal).toUpperCase(); //Asignar el valor decimal a FF y completar con 0 en caso de necesitarlo
            } //Finde if para validar si el numero es negatico
            
            postbyte = BD.PosicionMatriz(i,3).replace("xb", ValorSeparado).replace("ff",ValorFF); //Establecer postbyte
        } //Fin de if para calcular IDX1
        
        //Calcular IDX Pre/Post Incremento/Decremento
        if(DirAux.matches("^IDX\\((PreDec|PreInc|PostDec|PostInc)\\)$") && codop.equals(BD.PosicionMatriz(i, 0)) && BD.PosicionMatriz(i, 3).endsWith("xb")) {
            String[] parts = operando.split(",");
            int valorIndexado = Integer.parseInt(parts[0]);
            if(operando.matches("^[1-8],\\+(X|Y|SP|PC)$")&&(valorIndexado >= 1 && valorIndexado <= 8)){//Pre-inc
                System.out.println("opernado " + operando);
                System.out.println("valor de la tabal A-3 "+ Metodos.ta3(operando));
                postbyte =  BD.PosicionMatriz(i,3).replace("xb", Metodos.ta3(operando));
            }else if(operando.matches("^[1-8],\\-(X|Y|SP|PC)$")&&(valorIndexado >= 1 && valorIndexado <= 8)){//Pre-dec
                System.out.println("opernado " + operando);
                System.out.println("valor de la tabal A-3 "+ Metodos.ta3(operando));
                postbyte =  BD.PosicionMatriz(i,3).replace("xb", Metodos.ta3(operando));
            }else if(operando.matches("^[1-8],(X\\+|Y\\+|SP\\+|PC\\+)$")&&(valorIndexado >= 1 && valorIndexado <= 8)){//Post-dec
                System.out.println("opernado " + operando);
                System.out.println("valor de la tabal A-3 "+ Metodos.ta3(operando));
                postbyte =  BD.PosicionMatriz(i,3).replace("xb", Metodos.ta3(operando));
            }else if(operando.matches("^[1-8],(X\\-|Y\\-|SP\\-|PC\\-)$")&&(valorIndexado >= 1 && valorIndexado <= 8)){//Post-inc
                System.out.println("opernado " + operando);
                System.out.println("valor de la tabal A-3 "+ Metodos.ta3(operando));
                postbyte =  BD.PosicionMatriz(i,3).replace("xb", Metodos.ta3(operando));
            }else{
             return "Error 1";
            }
        }//Fin IDX Pre/Post Incremento/Decremento
        
        } //Fin de for
        
        return postbyte; //Posible Return "Error Postbyte" en caso de que no entre en ninguna de las validaciones anteriores
    } //Fin de get Postbyte

    public void setPostbyte(String postbyte) {
        this.postbyte = postbyte;
    }    
} //Fin de la clase linea 
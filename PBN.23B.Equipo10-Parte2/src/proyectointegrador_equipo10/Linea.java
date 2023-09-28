//Clase Linea
package proyectointegrador_equipo10;

public class Linea {
    private String etiqueta;
    private String codop;
    private String operando;
    private String direccion; 
    private String tamaño;
    public String TamañoAux;
    public String DireccionAux;
      
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
        // Comprobar si es un NOP (sin operando)
        TamañoAux = null;
        DireccionAux = null;
        
        if (codop != null && codop.equalsIgnoreCase("ORG") && operando != null)  { //Si encuentra "ORG" con un operando 
            return "DIRECT"; //Retorna modo de direccionamiento "DIRECT"
        } //Fin de validaro para ORG
        
        else if (codop != null && codop.equalsIgnoreCase("END") && operando == null)  { //Si encuentra "ORG" sin un operando
            return "DIRECT"; //Retorna modo de direccionamiento "DIRECT"
        } //Fin de validar para END
        
        // Comprobar si es un INH (sin operando)
        else if (operando == null) { //Si el operando es nulo, entonces se considera INH
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
                            return "IMM";
                        }//fin de if
                        else if (valor >= 256 && valor <= 65535) {
                            return "IMM";
                        }//fin de else if
                    }//fin de try 
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
                        return "IMM";
                    }//fin de if
                    else if (valor >= 256 && valor <= 65535) {
                        return "IMM";
                    }//fin de else if
                }//fin de try
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
                        return "IMM";
                    }//fin de if
                    else if (valor >= 256 && valor <= 65535) {
                        return "IMM";
                    }//fin de else if
                }//fin de if
            }//fin de else if 

            else if (Metodos.IsDecimal(operandoSinNumeral)) {
            // Comprobar si el operando es decimal (#)
                int valorDecimal = Integer.parseInt(operandoSinNumeral);
                if (valorDecimal >= 0 && valorDecimal <= 255) {
                    return "IMM";
                }//fin de if
                else if (valorDecimal >= 256 && valorDecimal <= 65535) {
                    return "IMM";
                }//fin de else if
            }//fin de else if
        
            // Comprobar si el operando es decimal
            else if (Metodos.IsDecimal(operandoSinNumeral)) {
                int valorDecimal = Integer.parseInt(operandoSinNumeral);
                if (valorDecimal >= 0 && valorDecimal <= 255) {
                   return "IMM";
                }//fin de if 
                else if (valorDecimal >= 256 && valorDecimal <= 65535) {
                    return "IMM";
                }//fin de else if
            } //fin de else if
            } //fin if de IMM

            // Comprobar el tipo de direccionamiento Directo (DIR)
            else if (operando.matches("^[#@$]?[0-9]+$|^%[0-1]{1,8}$")) {
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
                       return "EXT";
                   } //Fin de if
                } //Fin de try
                catch (NumberFormatException e) {
                   // No es un valor válido
                } //Fin de catch
            } //Fin de if

            // Comprobar el tipo de direccionamiento Indexado de 5 bits (IDX)
            else if (operando.matches("^-?\\d+,[[X-x]|[Y-y]|[SP-sp]|[PC-pc]]+$")) {
                String[] parts = operando.split(",");
                int valorIndexado = Integer.parseInt(parts[0]);
                if (valorIndexado >= -16 && valorIndexado <= 15) {
                    return "IDX";
                }// Comprobar el tipo de direccionamiento Indexado de 9 bits (IDX)
                else if ((valorIndexado >= -256 && valorIndexado <= -17) || (valorIndexado >= 16 && valorIndexado <= 255)) {
                    return "IDX1";
                } //Fin de else if
            } //Fin de else if

            // Comprobar el tipo de direccionamiento Indexado indirecto de 16 bits (IDX2)
            if (operando.matches("^[0-9]+,[XYSPPCxysppc]+$")) {
                String[] parts = operando.split(",");
                int valorIndexado = Integer.parseInt(parts[0]);
                if (valorIndexado >= 256 && valorIndexado <= 65535) {
                    return "IDX2";
                } //Fin de if 
            } //Fin de if

               // Comprobar el tipo de direccionamiento Indexado indirecto de 16 bits ([IDX2])
            else if (operando.matches("^\\[\\d{1,5},[XYSPPCpc]+\\]$")) {
                // Extraer el valor dentro de los corchetes y comprobar si está en el rango de 0 a 65535
                String operandoSinCorchetes = operando.substring(1, operando.length() - 1);
                String[] parts = operandoSinCorchetes.split(",");
                int valorIndexado = Integer.parseInt(parts[0]);
                if (valorIndexado >= 0 && valorIndexado <= 65535) {
                    return "[IDX2]";
                } //Fin de else 
            } //Fin de else if

            // Comprobar el tipo de direccionamiento Indexado pre/post decremento/incremento (IDX)
            else if (operando.matches("^[1-8],([-+][XYSP])$")) {
                return "IDX";
            } //Fin de else 

            // Comprobar el tipo de direccionamiento Indexado pre/post decremento/incremento (IDX)
            else if (operando.matches("^[1-8],([XYSP][-+])$")) {
                return "IDX";
            } //Fin de else if

            // Comprobar el tipo de direccionamiento Indexado de acumulador (IDX)
            else if (operando.matches("^[[A-a]|[B-b]|[D-d]],[[X-x]|[Y-y]|[SP-sp]|[PC-pc]]")) {
               return "IDX";
            }//fin de else if

            // Comprobar el tipo de direccionamiento Indexado acumulador indirecto ([D,IDX])
            else if (operando.matches("^\\[[D-d],[[X-x]|[Y-y]|[SP-sp]|[PC-pc]]+\\]$")) {
               return "[D,IDX]";
            }//fin de else if

            //Relativo REL
            else if (operando.matches("^[a-zA-Z_][a-zA-Z0-9_]*$|^-?\\d+$")) {
            // Comprobar si el operando es una etiqueta válida o un valor decimal en el rango adecuado
                if (Metodos.ComprobarEtiqueta(operando)) {
                    return "REL";
                } //Fin de if
                int valorDecimal = Integer.parseInt(operando);
                if (valorDecimal >= -128 && valorDecimal <= 127) { //REL 8bit
                    return "REL";
                } //Fin de if
                else if (valorDecimal >= -32768 && valorDecimal <= 32767) { //REL 16bit
                    return "REL";
                } //Fin else if
            } //Fin de else if
            
            //Rel con ciclo 
            else if (operando.matches("^[[ABDXYSPabdxysp]],[a-zA-Z_][a-zA-Z0-9_]*$|^-?\\d+$")) {
                String[] partes = operando.split(",");
                String registro = partes[0].toUpperCase(); // Convertir el registro a mayúsculas para hacer comparaciones sin distinción de mayúsculas y minúsculas
                String resto = partes[1].trim(); // Eliminar espacios en blanco antes y después de la parte después de la coma
                
                // Verificar si el registro es válido
                if (registro.matches("[[A-a]|[B-b]|[D-d]|[X-x]|[Y-y]|[SP-sp]|[PC-pc]]")) {
                    // Verificar si la parte después de la coma es un valor numérico o una palabra válida
                    if (Metodos.IsDecimal(resto) || Metodos.IsBinario(resto) || Metodos.IsOctal(resto) || Metodos.IsHexadecimal(resto) || Metodos.ComprobarEtiqueta(resto)) {
                        //return "Relativo con ciclo (REL) de " + (resto.length() <= 2 ? "8" : "16") + " bits";
                        return "REL(9-bit)";
                    } //Fin de if
                } //Fin de if
            } //Fin de else if
        }//fin de else if
        return "No reconocido"; // Si no se reconoce ningún tipo de direccionamiento
   }//fin de public String 
   
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

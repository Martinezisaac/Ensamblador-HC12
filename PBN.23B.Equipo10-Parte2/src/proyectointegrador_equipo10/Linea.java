//Clase Linea
package proyectointegrador_equipo10;

public class Linea {
    private String etiqueta;
    private String codop;
    private String operando;
    private String direccion; 
    private String tamaño;
  
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
       if (codop != null && codop.equalsIgnoreCase("NOP") && operando == null) {
           return "Inherente (INH)";
       }//fin de if
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
                    return "Inmediato (IMM) de 8 bits";
                }//fin de if
                else if (valor >= 256 && valor <= 65535) {
                    return "Inmediato (IMM) de 16 bits";
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
                    return "Inmediato (IMM) de 8 bits";
                }//fin de if
                else if (valor >= 256 && valor <= 65535) {
                    return "Inmediato (IMM) de 16 bits";
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
                    return "Inmediato (IMM) de 8 bits";
                }//fin de if
                else if (valor >= 256 && valor <= 65535) {
                    return "Inmediato (IMM) de 16 bits";
                }//fin de else if
            }//fin de if
        }//fin de else if 
        else if (Metodos.IsDecimal(operandoSinNumeral)) {
            // Comprobar si el operando es decimal (#)
            int valorDecimal = Integer.parseInt(operandoSinNumeral);
            if (valorDecimal >= 0 && valorDecimal <= 255) {
                return "Inmediato (IMM) de 8 bits";
            }//fin de if
            else if (valorDecimal >= 256 && valorDecimal <= 65535) {
                return "Inmediato (IMM) de 16 bits";
            }//fin de else if
        }//fin de else if
    // Comprobar si el operando es decimal
    else if (Metodos.IsDecimal(operandoSinNumeral)) {
        int valorDecimal = Integer.parseInt(operandoSinNumeral);
        if (valorDecimal >= 0 && valorDecimal <= 255) {
            return "Inmediato (IMM) de 8 bits";
        }//fin de if 
        else if (valorDecimal >= 256 && valorDecimal <= 65535) {
            return "Inmediato (IMM) de 16 bits";
        }//fin de else if
        }//fin de else if
}// fin if de IMM
        // Comprobar el tipo de direccionamiento Directo (DIR)
        else if (Metodos.IsDecimal(operando)) {
            int valorDecimal = Integer.parseInt(operando);
            if (valorDecimal >= 0 && valorDecimal <= 255) {
                return "Directo (DIR) de 8 bits";
            }//fin de if 
            else if (valorDecimal >= 256 && valorDecimal <= 65535) {
                return "Directo (DIR) de 16 bits";
            }//fin de else if
        }//fin de else if (DIR)
        // Comprobar el tipo de direccionamiento Extendido (EXT)
        else if (Metodos.IsDecimal(operando)) {
            int valorDecimal = Integer.parseInt(operando);
            if (valorDecimal >= 256 && valorDecimal <= 65535) {
                return "Extendido (EXT)";
            }//fin de if
        }//fin de else if
        // Comprobar el tipo de direccionamiento Indexado de 5 bits (IDX)
        else if (operando.matches("^[0-9]+,[XYSP]+$")) {
            int valorIndexado = Integer.parseInt(operando.split(",")[0]);
            if (valorIndexado >= -16 && valorIndexado <= 15) {
                return "Indexado de 5 bits (IDX)";
            }//fin de if
        }//fin de else if
        // Comprobar el tipo de direccionamiento Indexado de 9 bits (IDX1)
        else if (operando.matches("^[0-9]+,[XYSP]+$")) {
            int valorIndexado = Integer.parseInt(operando.split(",")[0]);
            if ((valorIndexado >= -256 && valorIndexado <= -17) || (valorIndexado >= 16 && valorIndexado <= 255)) {
                return "Indexado de 9 bits (IDX1)";
            }//fin de if
        }//fin de else if
        // Comprobar el tipo de direccionamiento Indexado de 16 bits (IDX2)
        else if (operando.matches("^[0-9]+,[XYSP]+$")) {
            int valorIndexado = Integer.parseInt(operando.split(",")[0]);
            if (valorIndexado >= 256 && valorIndexado <= 65535) {
                return "Indexado de 16 bits (IDX2)";
            }//fin de if
        }//fin de else if
        // Comprobar el tipo de direccionamiento Indexado indirecto de 16 bits ([IDX2])
        else if (operando.matches("^\\[[0-9]+,[XYSP]+\\]$")) {
            return "Indexado indirecto de 16 bits ([IDX2])";
        }//fin de else if
        // Comprobar el tipo de direccionamiento Indexado pre/post decremento/incremento (IDX)
        else if (operando.matches("^[1-8],[-+][XYSP]+$|^[1-8],[XYSP]+[-+]$")) {
            return "Indexado pre/post decremento/incremento (IDX)";
        }//fin de else if
        // Comprobar el tipo de direccionamiento Indexado de acumulador (IDX)
        else if (operando.matches("^[ABD],[XYSP]+$")) {
            return "Indexado de acumulador (IDX)";
        }//fin de else if
        // Comprobar el tipo de direccionamiento Indexado acumulador indirecto ([D,IDX])
        else if (operando.matches("^\\[[ABD],[-+][XYSP]+\\]$")) {
            return "Indexado acumulador indirecto ([D,IDX])";
        }//fin de else if
        // Comprobar el tipo de direccionamiento Relativo (REL)
        else if (Metodos.IsDecimal(operando)) {
            int valorDecimal = Integer.parseInt(operando);
            if (valorDecimal >= -128 && valorDecimal <= 127) {
                return "Relativo (REL) de 8 bits";
            }//fin de if
            else if (valorDecimal >= -32768 && valorDecimal <= 32767) {
                return "Relativo (REL) de 16 bits";
            }//fin de else if
        }//fin de else if
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

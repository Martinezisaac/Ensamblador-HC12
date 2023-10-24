/* 
Proyecto Integrador | Programacion de bajo nivel
Equipo 10 | Integrantes: 
    - Hernandez Gutierrez Emmanuel 
    - Jimenez Castellanos Jesus Alejandro
    - Martinez Isaac
*/

package proyectointegrador_equipo10;

//Librerias
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
//import javax.swing.table.DefaultTableModel;

public class ProyectoIntegradorP2_Equipo10 { //Inicio de la clase
    
    //Variables auxiliares
    static Metodos metodos = new Metodos(); //Instanciacion para clase metodos
    static ArrayList<String> ArrayEtiqueta = new ArrayList<>(); //Arraylist para guardar etiquetas

    public static void main(String[] args) { //Inicio de Main
        
         // Crear variable para archivo TABSIM y LISTADO
        String ArchivoTABSIM = "TABSIM.txt";
        String ArchivoLISTADO = "LISTADO.lst";

        // Verificar si el archivo existe y eliminarlo en caso de que exista
        File archivoExistente = new File(ArchivoTABSIM); 
        if (archivoExistente.exists()) { //Si el archivo TABSIM, entonces se elimina para que se cree nuevamente y actualizar los cambios con cada ejecucion
            archivoExistente.delete(); //Eliminar TABSIM          
            System.out.println("Archivo existente eliminado: " + ArchivoTABSIM); //Mensaje de confirmacion 
        } //Fin de if 
        
        // Verificar si el archivo existe y eliminarlo en caso de que exista
        File archivoExistente2 = new File(ArchivoLISTADO); 
        if (archivoExistente2.exists()) { //Si el archivo LISTADO, entonces se elimina para que se cree nuevamente y actualizar los cambios con cada ejecucion
            archivoExistente2.delete(); //Eliminar LISTADO
            System.out.println("Archivo existente eliminado: " + ArchivoLISTADO); //Mensaje de confirmacion 
        } //Fin de if
        
        // Codigo para abrir el archivo de manera directa, sin abrir el explorador de archivos
        //String Archivo = ("P2ASM.asm"); //Variable auxiliar para leer el archivo
            /*Archivos disponibles para probar el programa: 
                - P1ASM.asm
                - P2ASM.asm
                - P3ASM.asm*/
            
        //String DecimalString = "0"; //Variable auxiliar para convertir de otros sistemas a decimal
                               
        Linea linea = new Linea(null , null , null, null, null, null, null); // Instanciar objeto Linea con variables inicializadas en null
              //Contenido de Linea = linea(etiqueta, codop, operando, direccion, tamaño, DirAux, Postbyte)
                
        ArchivoSalvacion BD = new ArchivoSalvacion("Salvation.txt"); //Objeto con archivo salvacion
            /*Salvation es un archivo de tipo "txt", contiene toda la informacion sobre los codigos operandos, modos de direccionamiento, tamaños de los
              bytes por calcular y ya calculados, su descripcion y mas informacion relevante. 
              Este archivo es utilizado para realizar comparaciones y devolver informacion dentro de Salvation.txt */
        
        File selectedFile = null;          
        JFileChooser fileChooser = new JFileChooser();

        //Establecer un filtro para mostrar solo ciertos tipos de archivos (opcional)
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "asm"); //Solo abrira archivos de tipo asm
        fileChooser.setFileFilter(filter); //Aplicar filtro

        //Abrir el diálogo para elegir un archivo
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile(); //Abrir archivo
        } //Fin de if 
        else {
            System.out.println("No se selecciono ningun archivo..."); //Mensaje de error
            return;  //Si no se selecciona un archivo, sale del programa
        } //Fin de else          
        //BufferedReader Read = new BufferedReader(new FileReader(Archivo)); //Lee el archivo contenido en la variable Archivo
            
        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) { //Intento para leer un archivo, el archivo debera contener un codigo en ensamblador para que el programa funcione de manera correcta    
            String Linea; //Variable auxiliar, todo lo que se lee en el archvio se guarda en este variable
           
            //Si el archivo se abre de manera correcta entonces entra al try y crea la tabla con la informacion del archivo .asm
            
            // Crear el modelo de datos para la JTable
            DefaultTableModel tabla = new DefaultTableModel( //Crear tabla
                new Object[]{"CONTLOC","ETQ", "CODOP", "OPR", "ADDR", "SIZE", "POSTBYTE"}, 0); //Definir estructura de la tabla
            
            DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();//Declaracion de un objeto DefaultTableCellRenderer

            // Crear la tabla con el modelo de datos
            JTable tbl = new JTable(tabla);
            tbl.setEnabled(false);
            centrar.setHorizontalAlignment(SwingConstants.CENTER);//Se decide hacia que direccion se desean acomodar.
            tbl.getColumnModel().getColumn(0).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la columna 0
            tbl.getColumnModel().getColumn(1).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la columna 1
            tbl.getColumnModel().getColumn(2).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la columna 2
            tbl.getColumnModel().getColumn(3).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la columna 3
            tbl.getColumnModel().getColumn(4).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la columna 4
            tbl.getColumnModel().getColumn(5).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la columna 5
            tbl.getColumnModel().getColumn(6).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la columna 6
           
            // Configurar del frame
            JFrame frame = new JFrame("Partes de código Ensamblador"); //Nombre de la ventana
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(new JScrollPane(tbl), BorderLayout.CENTER);
            frame.pack();
            frame.setSize(700, 500); //Definir tamaño de la tabla

            // Centrar la ventana en la pantalla
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - frame.getWidth()) / 2;
            int y = (screenSize.height - frame.getHeight()) / 2;
            frame.setLocation(x, y);

            frame.setVisible(true); //Hacer visible la tabla
            
            //Algoritmo para detectar las partes de un ensamblador 
            while((Linea = br.readLine()) != null) { //Guardar cada linea en la variable Linea                  
                //(LineaRead.readLine()) != null - Caso alternativo para leer archivos con direcciones que coloquemos de manera manual
                
                String DecimalString = "0"; //Variable auxiliar para inicializar variable en 0 para cada iteracion realizada

                //Inicializar objetos en null en cada iteracion
                //linea.setValor("Hola"); //Etiqueta
                linea.setEtiqueta(null); //Etiqueta
                linea.setCodop(null); //Codigo Operando
                linea.setOperando(null); //Operando
                linea.setDireccion(null); //Direccionamiento
                //linea.setTamaño(null); //Tamaño en bytes
                linea.setDirAux(null); //Direccionamiento auxiliar para mostrar en la tabla 

                Linea = Linea.trim(); //.trim evita los espacios o los tabuladores que hay de una palabra a otra y pasa directamente hacia la siguiente palabra

                //Validar linea vacia
                if(Linea.isEmpty()) { //Validar Si una linea esta vacia
                    continue; //Continuar con las demas lineas
                }//Fin de if 

               // Validar comentarios
                else if (Linea.startsWith(";")) { // Si la línea empieza con ; entonces se debe considerar como comentario
                    if (!Linea.matches("^;.{0,80}$")) { // Si la linea encontrada no contiene de 0 a 80 entonces es un error de comentario
                        System.out.println("Error Comentario\n"); //Mensaje de error
                    } //Fin de if 
                    else { //Entonces la linea si tiene de 0 a 80 caracteres                         
                        System.out.println("COMMENT"); //Mostrar mensaje 
                        linea.setCodop("COMMENT"); //Establcer comment en la tabla
                    } //Fin de else 
                } // Fin de else if

                //Leer palabras 
                else { //Si la linea no encunetra un comentario o el fin del programa
                    String[] Palabras = Linea.split("\\s+"); //Arreglo que parte los espacios
                    /* - Palabras es un arreglo que guardara las palabras que esten separadas por espacio en la variable linea                                       
                       - Funcion Split: Se utiliza para dividir la cadena en partes, en este caso mediante espacios "\\S+"
                         Esta funcion nos proporciona palabra por palabra, es util en nuestro codigo porque asi podemos
                         guardar dichas palabras en las variables de Etiqueta, Codop y Operando.
                       - s+ : Significa espacios " " */
                    
                    //Definir operando para posteriormente validar
                    if(Metodos.reconocer(Palabras, "DC.B")){//Validamos que exista la palabra DC.B
                        linea.setOperando(Metodos.separarValores(Palabras, Metodos.encontrarIndice(Palabras, "DC.B")+1));//Mandamos el valor a operando
                         //despues de encontrar el inidice de la cadena en la que se debe de basar para separar la parte deseada.
                    } //Fin de if
                    if(Metodos.reconocer(Palabras, "DC.W")){
                        linea.setOperando(Metodos.separarValores(Palabras, Metodos.encontrarIndice(Palabras, "DC.W")+1));//Mandamos el valor a operando
                        //despues de encontrar el inidice de la cadena en la que se debe de basar para separar la parte deseada.  
                    }//Fin de if
                        
                    System.out.println("despues"+ linea.getOperando()); //Mensaje en consola del operando 

                    for(String Palabra : Palabras) { //For each / enhanced for loop

                        Pattern patronetiqueta = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{0,7}$"); //Definir un patron con minusculas, mayusculas, puntos y con una longitud de 0 a 8 caracteres                 
                        Matcher matcheretiqueta = patronetiqueta.matcher(Palabra); // Crear un Matcher para verificar si la etiqueta cumple con el patrón

                        //Validar etiqueta
                        if(Palabra.endsWith(":")) { //Si empieza con ":"   
                            Palabra = Palabra.substring(0, Palabra.length() - 1 ); //Eliminar ":" de la palabra etiqueta
                            if (!matcheretiqueta.matches() && Metodos.cracteretq(Palabra) && Palabra.length()<=7) { //Validar la longitud maxima de 8 caracteres y los caracteres que hay en "Palabra"  
                                linea.setEtiqueta(Palabra); //La palabra identificada se guardara en el objeto etiqueta
                            } //Fin de if
                            else { //Si palabra no empieza con ":" 
                                linea.setEtiqueta("Error ETQ"); //Definir mensaje de etiqueta en caso de error
                            } //Fin de else 
                        } // Fin de if                                                                      

                        //Las variables ya estan inicializadas en null, por lo tanto siempre entra a la condicion debido al while inicial, por cada iteracion los setters
                        //de Etiqueta, Codop, Operando, Direccion y Tamaño vuelven a tener el valor de null
                        
                        //Validacion para CODOP
                        else if(linea.getCodop() == null) { //Si el codigo operando es igual a null                        
                            linea.setCodop(Palabra); //La palabra identificada se guardara en el objeto Codop

                            Pattern patron = Pattern.compile("[a-zA-Z.]{0,5}+$"); //Crear un patron con minusculas, mayusculas, puntos y con una longitud de 0 a 5 caracteres                 
                            Matcher matcher = patron.matcher(Palabra); // Crear un Matcher para verificar si Palabra cumple con el patrón

                            if (!matcher.matches() && Metodos.codops(linea.getCodop()) != true) { // Verificar si la palabra no cumple con el patrón
                                   linea.setCodop("Error CODOP"); //Definir mensaje de etiqueta en caso de error
                            } //Fin de if
                            
                            //Algoritmo para realizar busquedas en el archivo salvacion 
                            for(int i = 0; i <= 592; i++) { //Busca desde la linea 0 hasta las 592 lineas que conforma el archivo salvacion 
                                //Validar Codigos Operandos
                                //El if compara si el CODOP del .asm y del archivo salvacion coinciden, si no encuentra ninguna coincidencia entonces CODOP esta mal escrito en el archivo .asm
                                if(Palabra.equals((BD.PosicionMatriz(i, 0)))) { //Si los CODOPS de .asm y ArchivoSalvation son iguales                                                 
                                   linea.setCodop(Palabra); //Escribir CODOP 
                                   break; //Romper ciclo 
                                } //Fin de if 
                                else { //Si los CODOPS de .asm y ArchivoSalvation no son iguales
                                    linea.setCodop("Error CODOP"); //Mensaje de error 
                                } //Fin de else 
                            } //Fin de for
                    
                        } //Fin de else if

                        //Validaciones para Operando                    
                        else if(linea.getOperando() == null) { //Si el operando es igual a null                        
                            linea.setOperando(Palabra); //La palabra identificada se guardara en el objeto Operando

                            /*Validadores para identificar que tipo de operando es
                                - Se valida cada sistema numerico:
                                - Binario = Empiezan con %
                                - Hexadecimal = Empiezan con $
                                - Octal = Empizan con @
                                - Decimal = Empiezan con cualquier numero (0,9)
                                - Resto de Operandos = Operandos sin tipo de sistema asignado, por lo general aqui caen todos los operandos de tipo IDX y compañia */
                            
                            //Validar Binario
                            if(linea.getOperando().startsWith("%")) { //Si empieza con % entonces puede ser binario     
                                DecimalString = Metodos.ConvertBinarioDecimal(linea.getOperando()); //Convierte de binario a decimal                                
                                if(!Metodos.IsBinario(linea.getOperando())) { //Validar la sintaxis de un binario 
                                     linea.setOperando("Error OPR"); //Mostrar mensaje de error
                                } //Fin de if sintaxis
                            } //Fin de if
                            
                            //Validar hexadecimal
                            else if(linea.getOperando().startsWith("$")) { //Si empieza con $ entonces puede ser hexadecimal
                                DecimalString = Metodos.ConvertHexadecimalDecimal(linea.getOperando()); //Convierte de hexadecimal a decimal
                                if(!Metodos.IsHexadecimal(linea.getOperando())) { //Validar la sintaxis de un hexadecimal 
                                     linea.setOperando("Error OPR"); //Mostrar mensaje de error
                                } //Fin de if sintaxis
                            } //Fin de else if
                            
                            //Validar octal
                            else if(linea.getOperando().startsWith("@")) { //Si empieza con @ entonces puede ser octal
                                DecimalString = Metodos.ConvertOctalDecimal(linea.getOperando()); //Convierte de octal a decimal
                                if(!Metodos.IsOctal(linea.getOperando())) { //Validar si la sintaxis de un octal 
                                     linea.setOperando("Error OPR"); //Mostrar mensaje de error
                                } //Fin de if sintaxis
                            } //Fin de else if
                            
                            //Validar decimal
                            else if(linea.getOperando().matches("\\d+")) { //Validar si es decimal si contiene solamente numeros 
                                DecimalString = linea.getOperando(); //Guardar automaticamente el valor decimal en la variable DecimalString 
                                if(!Metodos.IsDecimal(linea.getOperando())) { //Validar si la sintaxis de un decimal 
                                     linea.setOperando("Error OPR"); //Mostrar mensaje de error
                                } //Fin de if sintaxis
                            } //Fin de else if                            
                            
                            //Si el operando no cae en ninguno de los sistemas numericos anteriores, entonces cae aqui
                            else { //Si no es nignuno de los posibles tipos de operandos, entonces es invalido
                                linea.setOperando(Palabra); //FALTA VALIDAR SI ES OTRO TIPO DE OPERANDO FUERA DE HEXA,OCTAL,BIN O DECIMAL 
                            } //Fin de else if
                        } //Fin de else if                       

                        else if(linea.getTamaño() == null) { //Siempre va a ser null porque nosotros los calculamos
                            linea.setTamaño(linea.getOperando());
                        } //Fin de else if
                    } //Fin de for each

                    // Validar espacios en blanco o tabuladores en Etiqueta
                    if (linea.getEtiqueta() != null && (linea.getEtiqueta().contains(" ") || linea.getEtiqueta().contains("\t"))) {
                        System.out.println("Error ETQ");
                        linea.setEtiqueta("Error ETQ"); // Restablecer etiqueta si es inválida
                    } //Fin de if      

                    int Decimal = Integer.valueOf(DecimalString); //Convertir Decimal String a variable de tipo decimal para validar la cantidad de bits
                    //Metodos.DeterminarBits(Decimal); //Metodo para determinar la cantidad de bits
                    
                    // Determinar el tipo de direccionamiento después de asignar valores
                    String tipoDireccionamiento = linea.getDireccion();
                    linea.setDireccion(tipoDireccionamiento);  
                    
                    /*
                    //Algoritmo para realizar busquedas en el archivo salvacion 
                    for(int i = 0; i <= 592; i++) { //Busca desde la linea 0 hasta las 592 lineas que conforma el archivo salvacion 
                        //Determinar Tamaño
                        //El if compara si el CODOP y la direccion del .asm son iguales al del archivo salvacion, en dado caso de que ambos sean iguales entonces encontro una coincidencia
                        if(linea.getCodop().equals(BD.PosicionMatriz(i, 0)) && linea.getDireccion().equals(BD.PosicionMatriz(i, 2))) {                                                 
                            linea.setTamaño(BD.PosicionMatriz(i, 5)); //Obtener tamaño
                            linea.setTamaño(linea.getTamaño()); //Mensaje de confirmacion 
                            linea.setPostbyte(BD.PosicionMatriz(i, 3)); //Obtener Postbyte
                            linea.setTamaño(linea.getTamaño()); //Mensaje de confirmacion
                            break; //Sale del if si lo encuentra 
                        } //Fin de if                        
                    } //Fin de for
                    */
             
                    //Impresion de las variables
                    System.out.println("POSTBYTE = " + linea.getPostbyte()); //Impresion de codigo postbyte calculado por cada iteracion
                    System.out.println("ETIQUETA = " + linea.getEtiqueta()); //Impresion de etiqueta por cada iteracion
                    System.out.println("CODOP = " + linea.getCodop()); //Impresion de Codigo Operando por cada iteracion
                    System.out.println("OPERANDO = " + linea.getOperando()); //Impresion de Operando por cada iteracion
                    System.out.println("VALOR DECIMAL = " + Decimal); //Impresion de Valor Decimal por cada iteracion
                    System.out.println("DIRECCION = " + linea.getDireccion()); //Impresion de Direccion por cada iteracion
                    System.out.println("DIRECCION AUX = " + linea.getDirAux()); //Impresion de Direccion por cada iteracion
                    System.out.println("TAMANO = " + linea.getTamaño() + "\n"); //Impresion de Tamaño por cada iteracion              
                } //Fin de else 
                               
                //Algoritmo para realizar busquedas en el archivo salvacion                
                if(linea.getCodop() != null) { //Validar si codigo operando existe, nos sirve para validar comentarios
                    for(int i = 0; i <= 592; i++) { //Busca desde la linea 0 hasta las 592 lineas que conforma el archivo salvacion 
                        //Determinar Tamaño y Codigo postbyte
                        //El if compara si el CODOP y la direccion del .asm son iguales al del archivo salvacion, en dado caso de que ambos sean iguales entonces encontro una coincidencia
                        if(linea.getCodop().equals(BD.PosicionMatriz(i, 0)) && linea.getDireccion().equals(BD.PosicionMatriz(i, 2))) {                            
                            linea.setTamaño(BD.PosicionMatriz(i, 5)); //Obtener tamaño
                            linea.setPostbyte(BD.PosicionMatriz(i, 3)); //Obtener Postbyte 
                            break; //Sale del if si lo encuentra 
                        } //Fin de if                        
                        else { //Si no encuentra una coincidencia entonces manda un mensaje de error
                            linea.setTamaño("0"); //Impresion de error
                            linea.setPostbyte("Error Postbyte"); //Ayuda no funciona REVISAR
                            linea.setDirAux("Error DIR"); //Mensaje de confirmacion
                            linea.setDireccion("Error"); //Mensaje de confirmacion                     
                            //System.out.println("hola mundo");
                        } //Fin de else                        
                    } //Fin de for
                } //Fin de if                                  

                //Validaciones DS y DC
                if(linea.getCodop().equals("DS.B") && linea.getOperando().matches("\\d+")) {
                    linea.setTamaño("1");
                    linea.setTamaño(String.valueOf(Integer.parseInt(linea.getTamaño())*Integer.parseInt(linea.getOperando()))); //Multiplicar el size por el operando
                } //Fin de if
                else if (linea.getCodop().equals("DS.W") && linea.getOperando().matches("\\d+")) {
                    linea.setTamaño("2"); //Si es .W tiene peso de 2 bytes
                    linea.setTamaño(String.valueOf(Integer.parseInt(linea.getTamaño())*Integer.parseInt(linea.getOperando()))); //Multiplicar el size por el operando
                } //Fin de else if
                if (linea.getCodop().equals("DC.B")) {
                    String[] partes = linea.getOperando().split(","); // Dividir el operando si tiene ","
                    linea.setTamaño(String.valueOf(partes.length)); // Poner tamaño del length de cuantas partes tiene el operando
                    if (linea.getOperando().contains("\"")) {
                        linea.setTamaño(String.valueOf(linea.getOperando().length() - 2)); // Si el operando contiene comillas, restar 2 al length para obtener solo el contenido
                    } // Fin de if
                } else if (linea.getCodop().equalsIgnoreCase("DC.W")) {
                    String[] partes = linea.getOperando().split(","); // Dividir el operando si tiene ","
                    linea.setTamaño(String.valueOf(partes.length * 2)); // Si es .W el tamaño es de 2 bytes, por lo que se multiplica por 2
                    if (linea.getOperando().contains("\"")) {
                        linea.setTamaño(String.valueOf((linea.getOperando().length() - 2) * 2)); // Si el operando contiene comillas, restar 2 al length y luego multiplicar por 2 para obtener solo el contenido
                    } // Fin de if 
                } // Fin de else if
                              
                // Validar si la etiqueta ya existe en ArrayEtiqueta
                if (linea.getEtiqueta() != null) { //Validar si la etiqueta es diferente de null
                    /*Se valida si es diferente debido a que en el arraylist se guardan etiquetas nulas, y por lo tanto si existen 2 etiquetas nulas se marcara
                      como "ETQ Dup", Lo que se necesita validar es entre las etiquetas que son diferentes de null */
                    if (ArrayEtiqueta.contains(linea.getEtiqueta())) { //Validar si la etiqueta ya existe en el arraylist
                        linea.setEtiqueta("ETQ Dup"); //Establecer etiqueta repetida
                        System.out.println("Error: Etiqueta duplicada - " + linea.getEtiqueta()); //Mensaje de confirmacion
                        // Aquí puedes manejar el error según tus necesidades
                    } //Fin de if  
                    else { //Si la etiqueta no existe en el arraylist
                        ArrayEtiqueta.add(linea.getEtiqueta()); //Agregar etiqueta al arraylist
                    } //Fin de else  
                } //Fin de if 
               
                // Actualizar Archivo TABSIM y Archivo LISTADO
                escribirEnTABSIM(linea.getEtiqueta(), linea.getCodop(), linea.getOperando(), linea.getValor());
                escribirEnLISTADO(linea.getTipo(), linea.getValor(), linea.getEtiqueta(), linea.getCodop(), linea.getOperando());
                
                // Agregar una fila con los datos a la JTable
                tabla.addRow(new Object[]{linea.getValor(), linea.getEtiqueta(), linea.getCodop(), linea.getOperando(), linea.getDirAux(), linea.getTamaño(), linea.getPostbyte()}); //Agregar objetos a la tabla                
                //Aqui muestra el objeto DirAux para que indique las especificaciones de algunos modos de direccionamiento
                //El objeto Direccion contiene el modo de direccionamiento tal cual viene en el archivo Salvacion
                
                if(linea.getCodop().equals("EQU")) { //Validar si el codigo operando contiene EQU
                    linea.setValor(linea.getEQUval()); //Establecer valor 
                    System.out.println("EQU: " + linea.getEQUval()); //Impresion en consola
                } //Fin de if                   
                else if(linea.getTamaño() != null || linea.getTamaño() != "0") { //Validar si existe algo en tamaño 
                    int conversion = Integer.parseInt(linea.getValor(), 16); //Variable auxiliar
                    int tamañodecimal = Integer.parseInt(linea.getTamaño()); //Variable auxiliar 
                    int ValorDecimal = conversion + tamañodecimal; //Sumar variables auxiliares en decimal para posteriormente convertir a hexadecimal

                        if(ValorDecimal > 65535){ //Validar bits
                            //String valorHexadecimal = String.format("%04X", ValorDecimal); //Convierte el valor a hexadecimal y rellena con 0s
                            linea.setValor("Desbordamiento"); //Guarda el valor 
                        } //Fin de if
                        else { //No existe desbordamiento
                            String valorHexadecimal = String.format("%04X", ValorDecimal); //Convierte el valor a hexadecimal y rellena con 0s
                            linea.setValor(valorHexadecimal); //Guarda el valor 
                        } //Fin de else 
                } //Fin de if                

            } //Fin de while
            
            //Impresion del arraylist con las etiquetas
            for (int i = 0; i < ArrayEtiqueta.size(); i++) {
                System.out.println(ArrayEtiqueta.get(i));                   
                //System.out.println(linea.getValor()); //Impresion de valor para verificarlo 
            } //Fin de for
            
            //Impresion de los dos archivos
            try {
                //escribirEnLISTADO(linea.getTipo(), linea.getValor(), linea.getEtiqueta(), linea.getCodop(), linea.getOperando());
                Path filePathListado = Paths.get("LISTADO.lst"); //Definir ruta del archivo 
                Desktop.getDesktop().open(filePathListado.toFile()); //Abrir el archivo LISTADO.lst
                
                Path filePathTABSIM = Paths.get("TABSIM.txt"); //Definir ruta del archivo 
                Desktop.getDesktop().open(filePathTABSIM.toFile()); //Abrir el archivo TABSIM.txt
            } //Fin de try 
            catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al crear el archivo");
            } //Fin de catch
            
        } //Fin de try                        
        catch (IOException e) { //Catch en caso de no poder abrir un archivo
            System.out.println("Error " + e.getMessage()); //Mensaje de error
        } //Fin de catch       
    } //Fin de main 
    
    // Función para escribir en el archivo TABSIM
    private static void escribirEnTABSIM(String etiqueta, String codop, String operando, String valor) {
        if (etiqueta != null) { //Si la etiqueta es diferentes de null
            try (BufferedWriter w = new BufferedWriter(new FileWriter("TABSIM.txt", true))) {
                // Escribir en el archivo TABSIM solo si la etiqueta no es nula 
                
                if(ArrayEtiqueta.contains(etiqueta)) { //Validar etiquetas
                    //ArrayEtiqueta.add(etiqueta); //Guardar las etiquetas existentes
                    
                    if (codop.equals("EQU")) { //Encontrar EQU
                    String tipo = "ABSOLUTA"; //Definir tipo "ABSOLUTA"
                    w.write(tipo + "\t" + etiqueta + "\t" + metodos.FormatoHexadecimal(operando) + "\n"); //Escribir en archivo
                    } //Fin de if 
                    else {
                        String CONTLOC = "$" + valor; //Agrega identificador de hexadecmial para que posteriormente entre a las validaciones 
                        String tipo = "RELATIVO"; //Definir tipo "RELATIVO"
                        w.write(tipo + "\t" + etiqueta + "\t" + metodos.FormatoHexadecimal(CONTLOC) + "\n"); //Escribir en archivo
                    } //Fin de else
                } //Fin de if
                else {
                    // Etiqueta duplicada, modificar el contenido del ArrayList
                    //for (int i = 0; i < ArrayEtiqueta.size(); i++) {
                        //if (ArrayEtiqueta.get(i).equals(etiqueta)) {
                        //ArrayEtiqueta.set(i, "ETIQUETA DUPLICADA");
                        //break;  // Romper el bucle una vez que se ha modificado la etiqueta duplicada
                        //}
                    //}
                    //w.write("ETIQUETA DUPLICADA\n"); // Escribir en archivo -----------------------------------------------------------------------------
                } //Fin de etiqueta
            } //Fin de try
            catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al crear el archivo");
            } //Fin de catch
        } //Fin de if 
    } //Fin de la funcion para escribir en archivo TABSIM
    
    // Función para escribir en el archivo LISTADO
    private static void escribirEnLISTADO(String Tipo, String Valor, String Etiqueta, String Codop, String Operando) {
        if (Etiqueta != null) { //Si la etiqueta es diferentes de null
        try (BufferedWriter w = new BufferedWriter(new FileWriter("LISTADO.lst", true))) {
            
            if (!ArrayEtiqueta.contains(Etiqueta)) { //Validar si existe una etiqueta repetida
                w.write("ETIQUETA DUPLICADA\n"); //Escribir en el archivo que existe una etiqueta duplicada -----------------------------------------------------------------------------
                //System.out.println("Error: Etiqueta duplicada - " + Etiqueta); //Imprimir en consola mensaje de error             
                //return;  // Terminar la función si la etiqueta está duplicada
            } //Fin de la funcion para validar etiqueta
            //if(Etiqueta != null) { //Si la etiqueta contiene algo diferente de null
            else {    
            w.write(Tipo + "\t" + Valor + "\t" + Etiqueta + "\t" + Codop + "\t" + Operando + "\n"); //Escribir en archivo
                System.out.println("Funciona :)"); //Mensaje de confirmacion en consola
            } //Fin de para validar si la etiqueta es diferente           
        } //Fin de try
        
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al crear el archivo");
        } //Fin de catch
        
    } //Fin de la funcion para escribir en archivo LISTADO
        else if(Etiqueta == null) { //Validar si la etiqueta es igual a null
           //Este if es para todas las etiquetas de contenido null
           //En este else if solamente escribira el contenido de la etiqueta null, no es necesario validar más cosas porque ya estan validadas en el if anterior
           try (BufferedWriter w = new BufferedWriter(new FileWriter("LISTADO.lst", true))) {
                w.write(Tipo + "\t" + Valor + "\t" + Etiqueta + "\t" + Codop + "\t" + Operando + "\n"); //Escribir en archivo
            } //Fin de try
            catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al crear el archivo");
            } //Fin de catch 
        } //Fin de else if 
    } //Fin de la funcion
 
} //Fin de la clase principal
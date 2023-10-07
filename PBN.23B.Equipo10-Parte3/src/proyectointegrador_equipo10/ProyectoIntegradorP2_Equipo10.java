/* Isaaccommit
Proyecto Integrador Parte 2 | Programacion de bajo nivel
Equipo 10 | Integrantes: 
    - Hernandez Gutierrez Emmanuel 
    - Jimenez Castellanos Jesus Alejandro
    - Martinez Isaac
*/

// Archivo principal: Contiene todo el algoritmo que hace funcionar al programa, llama funciones de otras clases y contiene un main
// para la ejecucion del programa

package proyectointegrador_equipo10;

//Librerias
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    public static void main(String[] args) { //Inicio de Main

        String DecimalString = "0"; //Variable auxiliar para convertir de otros sistemas a decimal 
        
        //String Archivo = ("P2ASM.asm"); //Variable auxiliar para leer el archivo
            /*Archivos disponibles para probar el programa: 
                - P1ASM.asm
                - P2ASM.asm */
            
        ArchivoSalvacion archivosalvacion = new ArchivoSalvacion("Salvation.txt"); //Instanciar objeto para mandar a llamar el archivo de Salvation
            /*Salvation es un archivo de tipo "txt", contiene toda la informacion sobre los codigos operandos, modos de direccionamiento, tamaños de los
              bytes por calcular y ya calculados, su descripcion y mas informacion relevante. 
              Este archivo es utilizado para realizar comparaciones y devolver informacion dentro de Salvation.txt */
            
        Linea linea = new Linea(null , null , null, null, null, null); // Instanciar objeto Linea con variables inicializadas en null
    
        ArchivoSalvacion BD = new ArchivoSalvacion("Salvation.txt"); //Objeto con archivo salvacion
        
        //PRUEBAS DE ISAAC PARA DESPUES DETECTAR TAMANO ///// NO MOVERLE A NADA PLS :)
        /*
        //System.out.println(ArchivoSalvacion.Salvacion[100][0]); //impresion de prueba

        //0 para CODOPS
        //1 para operandos
        //2 para modos de direccionamiento
        //3 para codigo maquina
        //4 para bytes por calcular
        //5 para bytes totales

        String Salvacion = null;
        Salvacion = ArchivoSalvacion.Salvacion[100][0];
            System.out.println(Salvacion);


        for(int i = 0; i <= 587; i++) {
        if(Linea.getDireccion() == ArchivoSalvacion[i][0]){
            Linea.setTamaño();
        }//Fin de if
        } //FIn de for
        */
    
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
                new Object[]{"CONTLOC", "ETQ", "CODOP", "OPR", "ADDR", "SIZE"}, 0); //Definir estructura de la tabla
            
            DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();//Declaracion de un objeto DefaultTableCellRenderer

            // Crear la tabla con el modelo de datos
            JTable tbl = new JTable(tabla);
            tbl.setEnabled(false);
            centrar.setHorizontalAlignment(SwingConstants.CENTER);//Se decide hacia que direccion se desean acomodar.
            tbl.getColumnModel().getColumn(0).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la coalumna 0
            tbl.getColumnModel().getColumn(1).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la coalumna 1
            tbl.getColumnModel().getColumn(2).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la coalumna 2
            tbl.getColumnModel().getColumn(3).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la coalumna 3
            tbl.getColumnModel().getColumn(4).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la coalumna 4
            tbl.getColumnModel().getColumn(5).setCellRenderer(centrar);//Se acomoda al centro la infiormacion de la coalumna 5
            
            // Configurar del frame
            JFrame frame = new JFrame("Partes de código Ensamblador"); //Nombre de la ventana
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(new JScrollPane(tbl), BorderLayout.CENTER);
            frame.pack();

            // Centrar la ventana en la pantalla
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - frame.getWidth()) / 2;
            int y = (screenSize.height - frame.getHeight()) / 2;
            frame.setLocation(x, y);

            frame.setVisible(true); //Hacer visible la tabla
            
            //Algoritmo para detectar las partes de un ensamblador 
            while((Linea = br.readLine()) != null) { //Guardar cada linea en la variable Linea 
                //linea.DirAux = null;
                //(LineaRead.readLine()) != null - Caso alternativo para leer archivos con direcciones que coloquemos de manera manual
                DecimalString = "0"; //Inicializar variable en 0 para cada iteracion realizada

                //Inicializar objetos en null en cada iteracion  
                linea.setEtiqueta(null); //Etiqueta
                linea.setCodop(null); //Codigo Operando
                linea.setOperando(null); //Operando
                linea.setDireccion(null); //Direccionamiento
                linea.setTamaño(null); //Tamaño en bytes
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
                        System.out.println("COMENTARIO \n"); // Escribir comentario
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
                            for(int i = 0; i <= 587; i++) { //Busca desde la linea 0 hasta las 587 lineas que conforma el archivo salvacion 
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
                    
                    //Algoritmo para realizar busquedas en el archivo salvacion 
                    for(int i = 0; i <= 587; i++) { //Busca desde la linea 0 hasta las 587 lineas que conforma el archivo salvacion 
                        //Determinar Tamaño
                        //El if compara si el CODOP y la direccion del .asm son iguales al del archivo salvacion, en dado caso de que ambos sean iguales entonces encontro una coincidencia
                        if(linea.getCodop().equals(BD.PosicionMatriz(i, 0)) && linea.getDireccion().equals(BD.PosicionMatriz(i, 2))) {                                                 
                            linea.setTamaño(BD.PosicionMatriz(i, 5)); 
                            linea.setTamaño(linea.getTamaño() + " bytes"); //Mensaje de confirmacion 
                            break; //Sale del if si lo encuentra 
                        } //Fin de if                        
                        else { //Si no encuentra una coincidencia entonces manda un mensaje de error
                            linea.setTamaño("-"); //Impresion de error
                            linea.setDirAux("Error DIR"); //Ayuda no funciona REVISAR
                            linea.setDireccion("Error"); //Ayuda no funciona REVISAR
                        } //Fin de else
                    } //Fin de for  
               
                    //Impresion de las variables
                    System.out.println("ETIQUETA = " + linea.getEtiqueta()); //Impresion de etiqueta por cada iteracion
                    System.out.println("CODOP = " + linea.getCodop()); //Impresion de Codigo Operando por cada iteracion
                    System.out.println("OPERANDO = " + linea.getOperando()); //Impresion de Operando por cada iteracion
                    System.out.println("VALOR DECIMAL = " + Decimal); //Impresion de Valor Decimal por cada iteracion
                    System.out.println("DIRECCION = " + linea.getDireccion()); //Impresion de Direccion por cada iteracion
                    System.out.println("TAMANO = " + linea.getTamaño() + "\n"); //Impresion de Tamaño por cada iteracion              
                } //Fin de else 
                
                // Agrega una fila con los datos a la JTable
                    tabla.addRow(new Object[]{null, linea.getEtiqueta(), linea.getCodop(), linea.getOperando(), linea.getDirAux(), linea.getTamaño()}); //Agregar objetos a la tabla
                    //Aqui muestra el objeto DirAux para que indique las especificaciones de algunos modos de direccionamiento
                    //El objeto Direccion contiene el modo de direccionamiento tal cual viene en el archivo Salvacion 
                    
            } //Fin de while       

            } //Fin de try                        
            catch (IOException e) { //Catch en caso de no poder abrir un archivo
                System.out.println("Error " + e.getMessage()); //Mensaje de error
            } //Fin de catch       
        } //Fin de main 

} //Fin de la clase principal
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
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
//import javax.swing.table.DefaultTableModel;

public class ProyectoIntegradorP2_Equipo10 { 
    
    //Variables auxiliares
    static Metodos metodos = new Metodos(); //Instanciacion para clase metodos
    static ArrayList<String> ArrayEtiqueta = new ArrayList<>(); //Arraylist para guardar etiquetas
    static ArrayList<String> ArrayLinea = new ArrayList<>(); //Arraylist para guardar linea por linea del ASM
    
    public static void main(String[] args) { //Inicio de Main
        
        //Declaracion de variables auxiliares 
        
        //Variables auxiliares para la creacion de archivos al final del procesamiento del ASM en su primera fase
        String ArchivoTABSIM = "TABSIM.txt"; //Declarar variable para archivo TABSIM
        String ArchivoLISTADO = "LISTADO.lst"; //Declarar variable para archivo LISTADO
        
            //Verificar si los archivos existen y eliminarlos en caso de que ya existan
            //Verificar archivo TABSIM
            File archivoExistente = new File(ArchivoTABSIM); //Variable auxiliar para TABSIM
            if (archivoExistente.exists()) { //Validar si TABSIM existe
                archivoExistente.delete(); //Eliminar TABSIM          
                System.out.println("Archivo existente eliminado: " + ArchivoTABSIM); //Mensaje de confirmacion 
            } //Fin de if 

            // Verificar archivo LISTADO
            File archivoExistente2 = new File(ArchivoLISTADO);  //Variable auxiliar
            if (archivoExistente2.exists()) { //Si el archivo LISTADO, entonces se elimina para que se cree nuevamente y actualizar los cambios con cada ejecucion
                archivoExistente2.delete(); //Eliminar LISTADO
                System.out.println("Archivo existente eliminado: " + ArchivoLISTADO + "\n"); //Mensaje de confirmacion 
            } //Fin de if
        
        //Variables auxiliares
        StringBuilder LineaCompleta = new StringBuilder(); //Declarar variable para aguardar el procesamiento de la linea leida por ASM
        int numlineas = 0; //Declarar variable para determinar el numero de lineas cada que existe una etiqueta
                      
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
            
        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) { //Intento para leer un archivo, el archivo debera contener un codigo en ensamblador para que el programa funcione de manera correcta    
            String Linea; //Variable auxiliar, todo lo que se lee en el archvio se guarda en este variable            
           
            //Si el archivo se abre de manera correcta entonces entra al try y crea la tabla con la informacion del archivo .asm
            
            // Crear el modelo de datos para la JTable
            DefaultTableModel tabla = new DefaultTableModel( //Crear tabla
                new Object[]{"CONTLOC","ETQ", "CODOP", "OPR", "ADDR", "SIZE", "POSTBYTE"}, 0); //Definir estructura de la tabla
            
            DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();//Declaracion de un objeto DefaultTableCellRenderer

            // Crear la tabla con el modelo de datos
            JTable tbl = new JTable(tabla); //Declarar tabla
            tbl.setEnabled(false);
            centrar.setHorizontalAlignment(SwingConstants.CENTER);//Se decide hacia que direccion se desean acomodar.
            tbl.getColumnModel().getColumn(0).setCellRenderer(centrar); //Se acomoda al centro la informacion de la columna 0
            tbl.getColumnModel().getColumn(1).setCellRenderer(centrar); //Se acomoda al centro la informacion de la columna 1
            tbl.getColumnModel().getColumn(2).setCellRenderer(centrar); //Se acomoda al centro la informacion de la columna 2
            tbl.getColumnModel().getColumn(3).setCellRenderer(centrar); //Se acomoda al centro la informacion de la columna 3
            tbl.getColumnModel().getColumn(4).setCellRenderer(centrar); //Se acomoda al centro la informacion de la columna 4
            tbl.getColumnModel().getColumn(5).setCellRenderer(centrar); //Se acomoda al centro la informacion de la columna 5
            tbl.getColumnModel().getColumn(6).setCellRenderer(centrar); //Se acomoda al centro la informacion de la columna 6
           
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
            
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Evitar el cierre directo por defecto, cuando el usuario seleccione que no o seleccione la flecha de cierre entonces la tabla no se cierra
            
            //Añadir un JOptionPane cuando el usuario cierre la ventana/tabla
            frame.addWindowListener(new WindowAdapter() { //Agregar un WindowsListener a la tabla
            @Override //Sobreescribir metodo
            public void windowClosing(WindowEvent e) { //Funcion para cerrar una ventana (es decir la tabla)
                int option = JOptionPane.showConfirmDialog(frame, "¿Estás seguro de que deseas cerrar la tabla?", "Cerrar Aplicación", JOptionPane.YES_NO_OPTION); //JOptionPane emergente 
                if (option == JOptionPane.YES_OPTION) { //Validar si en el joptionpane el usuario presiona que si 
                    frame.dispose(); //Cerrar la tabla al presionar el botón "Reiniciar"
                } //Fin de if 
                //En caso de presionar que no, la ventana emergente se cierra y la tabla continua 
            } //Fin de windows Closing 
            }); //Fin de WindowsListener 

            //Crear boton para reinciar el programa y buscar otro archivo 
            JButton BotonReiniciar = new JButton("Buscar otro ASM"); //Crear boton para buscar archivo nuevamente
            Color ColorPersonalizado = new Color(220, 220, 220); // Color personalizado del boton: Color negro 
            //BotonReiniciar.setForeground(Color.WHITE); // Color personalizado de la letra del boton: Color blanco
            BotonReiniciar.setBackground(ColorPersonalizado); //Aplicar color al boton 
            frame.add(BotonReiniciar, BorderLayout.SOUTH); //Agregar boton en la tabla en la parte inferior
            
            JTableHeader encabezado = tbl.getTableHeader(); //Asignar color a los headers 
            encabezado.setForeground(Color.BLACK);
            encabezado.setBackground(new Color(220, 220, 220));
            
            //tbl.setFont(new Font("Consolas", Font.PLAIN, 12)); //Asignar fuente a la tabla
            
            //Funcion para reiniciar el programa y abrirlo nuevamente en caso de apretar el boton para reiniciar 
            BotonReiniciar.addActionListener(new ActionListener() { //Agregar un WindowsListener al boton de reiniciar
                @Override //Sobreescribir metodo
                public void actionPerformed(ActionEvent e) { //Funcion para determinar la accion a realizar
                    ArrayEtiqueta.clear(); //Reinicar el arraylist con las etiquetas guardadas para almacenar las nuevas etiquetas del archivo a abrir
                    //frame.setVisible(false); //Ocultar la tabla al presionar el botón "Reiniciar"
                    frame.dispose(); //Cerrar la tabla al presionar el botón "Reiniciar"
                    //tabla.setRowCount(0); //Reiniciar filas a 0
                    ProyectoIntegradorP2_Equipo10.main(args); //Mandar a llamar todo el main nuevamente 
                } //Fin de action performed
            }); //Fin para reiniciar tabla
            
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

                    for(String Palabra : Palabras) { //For each / enhanced for loop
                        
                        Palabra.toUpperCase(); //Establecer todo como mayuscula
                        Pattern patronetiqueta = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{0,7}$"); //Definir un patron con minusculas, mayusculas, puntos y con una longitud de 0 a 8 caracteres                 
                        Matcher matcheretiqueta = patronetiqueta.matcher(Palabra); // Crear un Matcher para verificar si la etiqueta cumple con el patrón

                        //Validar etiqueta
                        if(Palabra.endsWith(":")) { //Si empieza con ":"   
                            Palabra = Palabra.substring(0, Palabra.length() - 1 ).toUpperCase(); //Eliminar ":" de la palabra etiqueta
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
                                if(Palabra.equalsIgnoreCase((BD.PosicionMatriz(i, 0)))) { //Si los CODOPS de .asm y ArchivoSalvation son iguales                                                 
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
                            linea.setOperando(Palabra.toUpperCase()); //La palabra identificada se guardara en el objeto Operando

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
                                linea.setOperando(Palabra.toUpperCase()); //FALTA VALIDAR SI ES OTRO TIPO DE OPERANDO FUERA DE HEXA,OCTAL,BIN O DECIMAL 
                            } //Fin de else if
                        } //Fin de else if                       

                        else if(linea.getTamaño() == null) { //Siempre va a ser null porque nosotros los calculamos
                            linea.setTamaño(linea.getOperando());
                        } //Fin de else if
                    } //Fin de for each

                    // Validar espacios en blanco o tabuladores en Etiqueta
                    if (linea.getEtiqueta() != null && (linea.getEtiqueta().contains(" ") || linea.getEtiqueta().contains("\t"))) {
                        linea.setEtiqueta("Error ETQ"); // Restablecer etiqueta si es inválida
                    } //Fin de if      

                    int Decimal = Integer.valueOf(DecimalString); //Convertir Decimal String a variable de tipo decimal para validar la cantidad de bits                                
                } //Fin de else 
                               
                //Algoritmo para realizar busquedas en el archivo salvacion                
                if(linea.getCodop() != null) { //Validar si codigo operando existe, nos sirve para validar comentarios
                    for(int i = 0; i <= 592; i++) { //Busca desde la linea 0 hasta las 592 lineas que conforma el archivo salvacion 
                        //Determinar Tamaño y Codigo postbyte
                        //El if compara si el CODOP y la direccion del .asm son iguales al del archivo salvacion, en dado caso de que ambos sean iguales entonces encontro una coincidencia
                        if(linea.getCodop().equalsIgnoreCase(BD.PosicionMatriz(i, 0)) && linea.getDireccion().equalsIgnoreCase(BD.PosicionMatriz(i, 2))) {                            
                            linea.setTamaño(BD.PosicionMatriz(i, 5)); //Obtener tamaño
                            linea.setPostbyte(BD.PosicionMatriz(i, 3)); //Obtener Postbyte 
                            break; //Sale del if si lo encuentra 
                        } //Fin de if                        
                        else { //Si no encuentra una coincidencia entonces manda un mensaje de error
                            linea.setTamaño("0"); //Impresion de error
                            linea.setPostbyte("Error Postbyte"); //Ayuda no funciona REVISAR
                            linea.setDirAux("Error DIR"); //Mensaje de confirmacion
                            linea.setDireccion("Error"); //Mensaje de confirmacion                     
                        } //Fin de else                        
                    } //Fin de for
                } //Fin de if                                  

                //Validaciones DS y DC
                if(linea.getCodop().equalsIgnoreCase("DS.B") && linea.getOperando().matches("\\d+")) {
                    linea.setTamaño("1");
                    linea.setTamaño(String.valueOf(Integer.parseInt(linea.getTamaño())*Integer.parseInt(linea.getOperando()))); //Multiplicar el size por el operando
                } //Fin de if
                else if (linea.getCodop().equalsIgnoreCase("DS.W") && linea.getOperando().matches("\\d+")) {
                    linea.setTamaño("2"); //Si es .W tiene peso de 2 bytes
                    linea.setTamaño(String.valueOf(Integer.parseInt(linea.getTamaño())*Integer.parseInt(linea.getOperando()))); //Multiplicar el size por el operando
                } //Fin de else if
                if (linea.getCodop().equalsIgnoreCase("DC.B")) {
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
                        numlineas++;
                    } //Fin de else  
                } //Fin de if 
                
                // Actualizar Archivo TABSIM y Archivo LISTADO
                escribirEnTABSIM(linea.getEtiqueta(), linea.getCodop(), linea.getOperando(), linea.getValor());
                escribirEnLISTADO(linea.getTipo(), linea.getValor(), linea.getEtiqueta(), linea.getCodop(), linea.getOperando());
                      
                //Impresion de las variables
                    System.out.println("CONTLOC = " + linea.getValor());
                    //System.out.println("VALOR DECIMAL = " + Decimal);
                    System.out.println("ETIQUETA = " + linea.getEtiqueta());
                    System.out.println("CODOP = " + linea.getCodop());
                    System.out.println("OPERANDO = " + linea.getOperando());
                    System.out.println("DIRECCION = " + linea.getDireccion());
                    System.out.println("DIRECCION AUX = " + linea.getDirAux());
                    System.out.println("TAMANO = " + linea.getTamaño()); 
                    System.out.println("POSTBYTE = " + linea.getPostbyte() + "\n");
                    
                    //Agregar lo calculado en un arraylist y separarlos con tabulador                 
                    ArrayLinea.add(linea.getValor() + "\t"); //Separar espacios por tabuldaroes             
                    ArrayLinea.add(linea.getEtiqueta() + "\t");
                    ArrayLinea.add(linea.getCodop() + "\t");
                    ArrayLinea.add(linea.getOperando() + "\t");
                    ArrayLinea.add(linea.getDirAux() + "\t");                
                    ArrayLinea.add(linea.getTamaño() + "\t");
                    ArrayLinea.add(linea.getPostbyte() + "\n"); //Aqui indica una separacion de linea
                
                // Agregar una fila con los datos a la JTable
                tabla.addRow(new Object[]{linea.getValor(), linea.getEtiqueta(), linea.getCodop(), linea.getOperando(), linea.getDirAux(), linea.getTamaño(), linea.getPostbyte()}); //Agregar objetos a la tabla                
                //Aqui muestra el objeto DirAux para que indique las especificaciones de algunos modos de direccionamiento
                //El objeto Direccion contiene el modo de direccionamiento tal cual viene en el archivo Salvacion
                               
                //Devolver el CONTLOC con lo que llevaba
                if(linea.getCodop().equalsIgnoreCase("EQU")) { //Validar si el codigo operando contiene EQU
                    linea.setValor(linea.getEQUval()); //Establecer valor
                    //System.out.println("EQU: " + linea.getEQUval()); //Impresion en consola
                    //System.out.println("EQU val = " + linea.getValor());
                } //Fin de if 
                
                
                //Devolver CONTLOC desde ORG sumando 
                else if(linea.getTamaño() != null || linea.getTamaño() != "0") { //Validar si existe algo en tamaño 
                    try {
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
                        }
                        catch(Exception e) {
                        System.out.println("Desbordamiento");
                    }
                } //Fin de if
                
                                
                //Codigo Auxiliar para el algoritmo de dos fases
                /* El ArrayList ArrayLinea guarda cada uno de los campos ya calculados, se agrega dentro del while para evitar que en la siguiente iteración se eliminen.
                Posteriormente se guarda en un String Builder mediante un fore each, de esta manera al terminar el ciclo while tenemos toda la tabla almacenada en un 
                StringBuilder */                  
                               
                for (String lineas : ArrayLinea) { //For each para guardar el contenido de arreglo etiqueta en un String Builder, de esta manera guardamos todo el programa en un StringBuilder
                    LineaCompleta.append(lineas); // Agregar cada objeto seguido de un tabulador al StringBuilder
                } //Fin de for
                
                ArrayLinea.clear(); //Limpiar informacion de la linea para que no se duplique en el arraylist
                
                if(Linea != null && Linea.contains("END")) { //Validar que la ultima linea sea el END
                    break; //Salir del ciclo y terminar programa
                } //Fin de if               
            } //Fin de while  
            
            /*
            Al termino del while la primera fase ha sido completada sin calcular los relativos de 8b, 9b, 16b, para ello se propone el siguiente algoritmo que consta de ciertas funciones
            para obtener y establecer valores en la misma tabla. Se decribe el funcionamiento de las funciones utilizadas:
                “Nombre de la tabla”.getValueAt(fila, columna); - Obtener valor de la tabla original
                “Nombre de la tabla”.setValueAt(“Valor a reemplazar”, fila, columna); - Establecer x valor a la tabla original 
            
            Fila
                En el programa se establece como “i”, pertenece a un ciclo for que ayuda a recorrer todas las posiciones de la tabla. Para encontrar un valor / celda de la tabla en específico 
                es posible aplicar varias validaciones para que el ciclo pare y realice las tareas de acuerdo con las condiciones aplicadas, en el programa se aplican las siguientes validaciones 
                para que el ciclo pare:
                    - Validar que el operando del relativo coincida con una etiqueta almacenada en el archivo TABSIM, al realizar esta validación logramos hacer el cálculo 
                      y evitamos que el programa deje de funcionar en el intento.
                    - Validar modo de direccionamiento, al validar esto indicamos en que condición puede entrar para empezar a calcular de acuerdo a la cantidad de bytes
                    - Validar que el código postbyte termine con “rr” o “qq rr”, esta valiación nos ayuda a diferenciar cada uno de los relativos, además de que nos ayudará 
                      para reemplazar los valores correspondientes por “rr” o “qq rr”

                Si se cumplen con estas condiciones, entonces el programa para y empieza a hacer los cálculos necesarios, termina de hacerlos y continúa el ciclo for hasta que ya no existan condiciones. 
         
            Columna
                0 = Valor / CONTLOC
                1 = Etiquetas
                2 = CODOP
                3 = Operando
                4 = Direccionamiento
                5 = Tamaño
                6 = Postbyte
            */
                       
            for(int i = 0; i < tabla.getRowCount(); i++) { //for para contenido de la tabla
                for(int u = 0; u <= numlineas-1; u++) { //Fin de for para contenido de TABSIM
                                     
                ArchivoSalvacion tabsim = new ArchivoSalvacion("TABSIM.txt"); //Objeto con archivo TABSIM
                
                //Variables Auxiliareas para realizar calculos o comparaciones               
                String Postbyte = tabla.getValueAt(i, 6).toString(); //Obtener Postbyte de la tabla                                 
                String Tamaño = tabla.getValueAt(i, 5).toString(); // Obtener tamaño de la tabla
                String ValBinario = null; //Variable auxiliar para almacenar binarios
                int ValorTamaño = Integer.parseInt(Tamaño); //Tamaño en decimal                
                String Valor = tabla.getValueAt(i, 0).toString(); //Obtener valor / CONTLOC de la tabla
                int ValorDecimal = Integer.parseInt(Valor, 16); //Valor / CONTLOC en decimal             
                int Origen = ValorTamaño + ValorDecimal; //Origen = Valor / CONTLOC + Tamaño
                
                    if(tabla.getValueAt(i, 4).equals("REL(16b)") && tabla.getValueAt(i, 6).toString().endsWith("qq rr")) { //Validar si Direccionamiento = REL(16b) && Postbyte termina con "qq rr"                        
                        //Realizar calculo para REL(16b)                       
                        if(tabla.getValueAt(i, 3).equals(tabsim.PosicionMatriz(u, 1))) { //Validar si en operando existe una etiqueta ya registrada en TABSIM
                            String ValTabsim = tabsim.PosicionMatriz(u, 2).replace(" ", ""); //Quitar espacio para su posterior conversion del valor del archivo TABSIM
                            int Destino = Integer.parseInt(ValTabsim, 16); //Valor del TABSIM en decimal 

                            if(Origen > Destino) { //Destino > Origen
                                int Salto = Origen - Destino; //Destino - Origen
                                ValBinario = Integer.toBinaryString(Salto*-1); //Convertir salto a binario y aplicar C2
                                ValBinario = ValBinario.substring(ValBinario.length()-16); //Obtener los ultimos 16 bits (al aplicar C2 ValBinario tiene una cadena de 32 caracteres)
                                Salto = Integer.parseInt(ValBinario, 2); //Convertidor ValBinario en C2 a decimal
                                String ValFinalHex = String.format("%04X", Salto).replaceAll("(.{2})(?!$)", "$1 "); //Convertir a hexadecimal, Rellenar con 0s en caso de y separar de dos en dos
                                String PostbyteCalculado = Postbyte.replace("qq rr", ValFinalHex); //Reemplazar "rr" por el valor calculado                               
                                tabla.setValueAt(PostbyteCalculado, i, 6); //Establecer postbyte en la tabla principal                            
                            } //Fin de Origen > Destino                           
                            else if(Origen < Destino) {
                                int Salto = Destino - Origen; //Origen - Destino
                                String ValFinalHex = String.format("%04X", Salto).replaceAll("(.{2})(?!$)", "$1 "); //Convertir a hexadecimal, Rellenar con 0s en caso de y separar de dos en dos
                                String PostbyteCalculado = Postbyte.replace("qq rr", ValFinalHex); //Reemplazar "rr" por el valor calculado
                                tabla.setValueAt(PostbyteCalculado, i, 6); //Establecer postbyte en la tabla principal                              
                            } //Fin de Origen < Destino
                            else if(Origen == Destino) { //Origen == Destino
                                String PostbyteCalculado = Postbyte.replace("qq rr", "00 00"); //Reemplazar "rr" por el valor calculado
                                tabla.setValueAt(PostbyteCalculado, i, 6); //Establecer postbyte en la tabla principal                                
                            } //Fin de validar si Origen == Destino                           
                        } //Fin de Validar si en operando existe una etiqueta ya registrada en TABSIM
                    } //Fin de Validar si Direccionamiento = REL(16b) && Postbyte termina con "qq rr"
                    
                    else if(tabla.getValueAt(i, 4).equals("REL(9-bit)") && tabla.getValueAt(i, 6).toString().endsWith("lb rr")) { //Validar si - Direccionamiento = REL(9b) && Postbyte termina con "lb rr" 
                        //Realizar calculo para REL(9b)
                         String[] partes = String.valueOf(tabla.getValueAt(i, 3)).split(","); //Dividir en dos el operando separandolo por la coma
                        if(partes[1].equalsIgnoreCase(tabsim.PosicionMatriz(u, 1))) { //Validar si en operando existe una etiqueta ya registrada en TABSIM
                            String ValTabsim = tabsim.PosicionMatriz(u, 2).replace(" ", ""); //Quitar espacio para su posterior conversion
                            int Destino = Integer.parseInt(ValTabsim, 16); //Valor del CONTLOC de la misma linea en decimal
                            String RB = null;

                            if(Origen > Destino) { //Destino > Origen negativo
                                RB = "Negativo";
                                int Salto = (Destino - Origen); //Destino - Origen
                                String ValFinalHex = String.format("%02X", Salto).replaceAll("(.{2})(?!$)", "$1 "); //Convertir a hexadecimal, Rellenar con 0s en caso de y separar de dos en dos
                                int tamvalfinal = ValFinalHex.length();//Determino el tamaño de la cadena del complemento
                                String sub = ValFinalHex.substring(tamvalfinal-2, tamvalfinal);//Pongo los rtangos para extraer el valor de la cadena con el complemetno a dos
                                String PostbyteCalculado = Postbyte.replace("rr", sub).replace("lb", Metodos.rel9(String.valueOf(tabla.getValueAt(i, 2)), partes[0], RB)); //Reemplazar "rr" por el valor calculado                               
                                tabla.setValueAt(PostbyteCalculado, i, 6); //Establecer postbyte en la tabla principal                            
                            } //Fin de Origen > Destino                           
                            else if(Origen < Destino) {
                                RB = "Positivo";
                                int VaLFinal = Destino - Origen; //Origen - Destino
                                System.out.println("Valor rr "+ VaLFinal);
                                String ValFinalHex = String.format("%02X", VaLFinal).replaceAll("(.{2})(?!$)", "$1 "); //Convertir a hexadecimal, Rellenar con 0s en caso de y separar de dos en dos
                                String PostbyteCalculado = Postbyte.replace("rr", ValFinalHex).replace("lb", Metodos.rel9(String.valueOf(tabla.getValueAt(i, 2)), partes[0], RB)); //Reemplazar "rr" por el valor calculado
                                tabla.setValueAt(PostbyteCalculado, i, 6); //Establecer postbyte en la tabla principal                              
                            } //Fin de Origen < Destino
                            else if(Origen == Destino) { //Origen == Destino
                                String PostbyteCalculado = Postbyte.replace("lb rr", "00 00"); //Reemplazar "rr" por el valor calculado
                                tabla.setValueAt(PostbyteCalculado, i, 6); //Establecer postbyte en la tabla principal                                
                            } //Fin de validar si Origen == Destino                           
                        } //Fin de Validar si en operando existe una etiqueta ya registrada en TABSIM
                    } //Fin de Validar si Direccionamiento = REL(16b) && Postbyte termina con "lb rr"
                    
                    else if(tabla.getValueAt(i, 4).equals("REL(8b)") && tabla.getValueAt(i, 6).toString().endsWith("rr")) { //Validar si el postbyte termina con "rr" para REL(8b)              
                        //Realizar calculo para REL(8b)
                        if(tabla.getValueAt(i, 3).equals(tabsim.PosicionMatriz(u, 1))) { //Validar si en operando existe una etiqueta ya registrada en TABSIM                           
                            String ValTabsim = tabsim.PosicionMatriz(u, 2).replace(" ", ""); //Quitar espacio para su posterior conversion
                            int Destino = Integer.parseInt(ValTabsim, 16); //Valor del CONTLOC de la misma linea en decimal 
                            
                            if(Origen > Destino) { //Destino > Origen
                                int Salto = Origen - Destino; //Origen - Destino
                                ValBinario = Integer.toBinaryString(Salto*-1); //Convertir salto a binario y aplicar C2
                                ValBinario = ValBinario.substring(ValBinario.length()-8); //Obtener los ultimos 16 bits (al aplicar C2 ValBinario tiene una cadena de 32 caracteres)
                                Salto = Integer.parseInt(ValBinario, 2); //Convertidor ValBinario en C2 a decimal
                                String ValFinalHex = String.format("%02X", Salto).replaceAll("(.{2})(?!$)", "$1 "); //Convertir a hexadecimal, Rellenar con 0s en caso de y separar de dos en dos
                                String PostbyteCalculado = Postbyte.replace("rr", ValFinalHex); //Reemplazar "rr" por el valor calculado                               
                                tabla.setValueAt(PostbyteCalculado, i, 6); //Establecer postbyte en la tabla principal                            
                            } //Fin de Origen > Destino                           
                            else if(Origen < Destino) {
                                int Salto = Destino - Origen; //Origen - Destino
                                String ValFinalHex = String.format("%02X", Salto).replaceAll("(.{2})(?!$)", "$1 "); //Convertir a hexadecimal, Rellenar con 0s en caso de y separar de dos en dos
                                String PostbyteCalculado = Postbyte.replace("rr", ValFinalHex); //Reemplazar "rr" por el valor calculado                               
                                tabla.setValueAt(PostbyteCalculado, i, 6); //Establecer postbyte en la tabla principal                              
                            } //Fin de Origen < Destino
                            else if(Origen == Destino) { //Origen == Destino
                                String PostbyteCalculado = Postbyte.replace("rr", "00"); //Reemplazar "rr" por el valor calculado
                                tabla.setValueAt(PostbyteCalculado.toUpperCase(), i, 6); //Establecer postbyte en la tabla principal  
                            } //Fin de validar si Origen == Destino                           
                        } //Validar si las etiquetas concuerdan
                    } //Fin de Validar si Direccionamiento = REL(16b) && Postbyte termina con "qq rr" 
                    
                    else if(tabla.getValueAt(i, 2).equals("DC.B")) { //Validar si el postbyte termina DC.B
                        
                        if(tabla.getValueAt(i, 3).toString().startsWith("\"")){//se verifica si el operando inicia con "
                           String cadena = tabla.getValueAt(i, 3).toString().replace("\"", "");//Se remplazan las comillas existentes por un epacio en blanco 
                           String cadenaasci = Metodos.cAHex(cadena);//Se convierten en hexadecimal 
                           String ValFinalHex = cadenaasci.replaceAll("(.{2})(?!$)", "$1 ");//Se separa la cadena de ASCII en hexadecimal por espacios
                           tabla.setValueAt(ValFinalHex.toUpperCase(), i, 6); //Establecer postbyte en la tabla principal
                        }  
                        if(tabla.getValueAt(i, 3).toString().replace(" ", "").contains(",")){
                            
                            String opr = tabla.getValueAt(i, 3).toString().replace(" ", "");
                            System.out.println("Operando= "+opr);
                            
                             String convertFinal = "";  
                             
                            String[] partes = opr.split(",");
                            
                            for (String elemento : partes){
                                String convert;
                            if(elemento.startsWith("%")){
                                convert = Metodos.binAHexa(elemento.replace("%", ""));
                                System.out.println("Valor de DC.B bintohexa = "+convert);
                            }else if(elemento.startsWith("@")){
                                convert = Metodos.OctAHexa(elemento.replace("@", ""));
                                System.out.println("Valor de DC.B octtohexa = "+convert);
                            }else if(elemento.startsWith("$")){
                                convert = elemento.replace("$", "");
                                System.out.println("Valor de DC.B sin $ = "+convert);
                            }else{
                                convert = Metodos.DecimalToHexadecimal(elemento);
                                System.out.println("Valor de DC.B sin $ = "+convert);  
                            }
                             // Concatenar el resultado al final de la cadena
                             convert = String.format("%02X", Long.parseLong(convert, 16));
                             convertFinal += convert + " ";
                            
                            }
                             // Eliminar el espacio adicional al final y establecer el resultado en la tabla
    convertFinal = convertFinal.trim();
    tabla.setValueAt(convertFinal.toUpperCase(), i, 6);
                        }
                    }
              
                    
                    else if(tabla.getValueAt(i, 2).equals("DC.W")) { //Validar si el postbyte termina DC.B
                        
                        if(tabla.getValueAt(i, 3).toString().startsWith("\"")) {
                            // Se verifica si el operando inicia con "
                            String cadena = tabla.getValueAt(i, 3).toString().replace("\"", ""); // Se reemplazan las comillas existentes por un espacio en blanco 
                            String cadenaasci = Metodos.cAHex(cadena).trim(); // Se convierten en hexadecimal 

                            // Formatear la cadena hexadecimal para asegurar que cada par tenga al menos cuatro dígitos
                            StringBuilder cadenaFormateada = new StringBuilder();

                            for (int j = 0; j < cadenaasci.length(); j += 2) {
                                String par = cadenaasci.substring(j, Math.min(j + 2, cadenaasci.length()));
                                // Rellenar con ceros a la izquierda si es necesario
                                String parFormateado = String.format("%04X", Long.parseLong(par, 16));
                                cadenaFormateada.append(parFormateado).append("");
                            }

                            // Eliminar el espacio adicional al final y establecer el resultado en la tabla principal
                            String ValFinalHex = cadenaFormateada.toString().replaceAll("(.{2})(?!$)", "$1 ");
                            tabla.setValueAt(ValFinalHex.toUpperCase(), i, 6);
                        }  
                        if(tabla.getValueAt(i, 3).toString().replace(" ", "").contains(",")){
                            
                            String opr = tabla.getValueAt(i, 3).toString().replace(" ", "");
                            System.out.println("Operando= "+opr);
                            
                             String convertFinal = "";  
                             
                            String[] partes = opr.split(",");
                            
                            for (String elemento : partes){
                                String convert;
                            if(elemento.startsWith("%")){
                                convert = Metodos.binAHexa(elemento.replace("%", ""));
                                System.out.println("Valor de DC.B bintohexa = "+convert);
                            }else if(elemento.startsWith("@")){
                                convert = Metodos.OctAHexa(elemento.replace("@", ""));
                                System.out.println("Valor de DC.B octtohexa = "+convert);
                            }else if(elemento.startsWith("$")){
                                convert = elemento.replace("$", "");
                                System.out.println("Valor de DC.B sin $ = "+convert);
                            }else{
                                convert = Metodos.DecimalToHexadecimal(elemento);
                                System.out.println("Valor de DC.B sin $ = "+convert);  
                            }
                             // Concatenar el resultado al final de la cadena
                             convert = String.format("%04X", Long.parseLong(convert, 16)).replaceAll("(.{2})(?!$)", "$1 ");
                             convertFinal += convert + " ";
                            
                            }
                             // Eliminar el espacio adicional al final y establecer el resultado en la tabla
    convertFinal = convertFinal.trim();
    tabla.setValueAt(convertFinal.toUpperCase(), i, 6);
                        }

                        
                    }//Fin de validar si el postbyte termina DC.W
                    
                    /*
                    //Etiquetas como Operando en los modos de direccionamiento DIR, EXT y IMM  
                    //Reconocer y establecer valores de etiqueta para DIR y EXT
                    if(tabla.getValueAt(i, 4).equals("DIR") && tabla.getValueAt(i, 4).toString().matches("^[a-zA-Z_][a-zA-Z0-9_]{0,7}$|^-?\\d{0,8}$")) {
                        if(tabla.getValueAt(i, 3).equals(tabsim.PosicionMatriz(u, 1))) {
                            String ValTabsim = tabsim.PosicionMatriz(u, 2).replace(" ", ""); //Quitar espacio para su posterior conversion
                            int ValTabsimDecimal = Integer.parseInt(ValTabsim, 16); //Valor del CONTLOC de la misma linea en decimal

                            if (ValTabsimDecimal >= 0 && ValTabsimDecimal <= 255) { //Validar si es de 8 bits
                                tabla.setValueAt("hola8", i, 6); //Establecer postbyte en la tabla principal
                            } //Fin de if
                            else if (ValTabsimDecimal >= 256 && ValTabsimDecimal <= 65535) { //Validar si es de 16 bits
                                tabla.setValueAt("hola16", i, 6); //Establecer postbyte en la tabla principal
                            } //Fin de if
                            else { //Validar desbordamiento 
                                tabla.setValueAt("Desbordamiento", i, 6); //Establecer postbyte en la tabla principal
                                tabla.setValueAt("0", i, 5); //Establecer tamaño en la tabla principal
                            } //Fin de else 
                        } //Fin de validar si el operando coincide con una etiqueta de TABSIM
                    } //Fin de if   
                    
                    //Reconocer y establecer valores de etiqueta para IMM(8b y 16b)
                    if(tabla.getValueAt(i, 4).equals("IMM(8b)") && tabla.getValueAt(i, 3).toString().matches("^#[a-zA-Z_][a-zA-Z0-9_]{0,7}$|^-?\\d{0,8}$")) {
                        
                        String Operando = tabla.getValueAt(i, 3).toString().substring(1); // Eliminar # del operando
                        if(Operando.equals(tabsim.PosicionMatriz(u, 1))) { //Validar si en operando existe una etiqueta ya registrada en TABSIM                            
                            String ValTabsim = tabsim.PosicionMatriz(u, 2).replace(" ", ""); //Quitar espacio para su posterior conversion
                            String ValTabsimRecortado = ValTabsim.substring(Math.max(ValTabsim.length() - 2, 0));; //Extraer el numero despues del espacio 
                            int ValTabsimDecimal = Integer.parseInt(ValTabsim, 16); //Valor del CONTLOC de la misma linea en decimal

                            if (ValTabsimDecimal >= 0 && ValTabsimDecimal <= 255) { //Validar si es de 8 bits
                                String ValorSeparado = ValTabsim.replaceAll("(.{2})", "$1 ").trim(); //Colocar espacio por cada dos caracteres con una variable auxiliar
                                tabla.setValueAt(Postbyte.replace("ii", ValTabsimRecortado), i, 6); //Establecer postbyte                                                     
                                //tabla.setValueAt("imm16", i, 6); //Establecer postbyte en la tabla principal
                                //tabla.setValueAt("imm8", i, 6); //Establecer postbyte en la tabla principal
                            } //Fin de if
                            else {
                                tabla.setValueAt("Desbordamiento", i, 6); //Establecer postbyte en la tabla principal
                                tabla.setValueAt("0", i, 5); //Establecer tamaño en la tabla principal
                            } //Fin de else 
                        } //Fin de validar si el operando coincide con una etiqueta de TABSIM
                    } //Fin de if
                    
                    if(tabla.getValueAt(i, 4).equals("IMM(16b)") && tabla.getValueAt(i, 3).toString().matches("^#[a-zA-Z_][a-zA-Z0-9_]{0,7}$|^-?\\d{0,8}$")) {
                        String Operando = tabla.getValueAt(i, 3).toString().substring(1); // Eliminar # del operando
                        if(Operando.equals(tabsim.PosicionMatriz(u, 1))) { //Validar si en operando existe una etiqueta ya registrada en TABSIM 
                            String ValTabsim = tabsim.PosicionMatriz(u, 2).replace(" ", ""); //Quitar espacio para su posterior conversion
                            int ValTabsimDecimal = Integer.parseInt(ValTabsim, 16); //Valor del CONTLOC de la misma linea en decimal
                            
                            if (ValTabsimDecimal >= 0 && ValTabsimDecimal <= 65535) { //Validar si es de 8 bits                            
                                String ValorSeparado = ValTabsim.replaceAll("(.{2})", "$1 ").trim(); //Colocar espacio por cada dos caracteres con una variable auxiliar
                                tabla.setValueAt(Postbyte.replace("jj kk", ValorSeparado), i, 6); //Establecer postbyte                                                     
                                //tabla.setValueAt("imm16", i, 6); //Establecer postbyte en la tabla principal
                            } //Fin de if
                            else { //Validar desbordamiento
                                tabla.setValueAt("Desbordamiento", i, 6); //Establecer postbyte en la tabla principal
                                tabla.setValueAt("0", i, 5); //Establecer tamaño en la tabla principal
                            } //Fin de else 
                        } //Fin de validar si el operando coincide con una etiqueta de TABSIM
                    } //Fin de if
                */
                } //Fin de Validar si en operando existe una etiqueta ya registrada en TABSIM
            } //Fin de for Tabla   
            
            
            //ESPACIO PARA ALGORITMO PARA VALIDAR NUEVAMEBTE CONTLOC 
                //Es necesario validar el CONTOLOC para modificar los tamaños modificados en el algoritmo anterior 
                               
            //Impresion del arraylist con las etiquetas
            System.out.println("ETIQUETAS"); //Indicar impresion del TABSIM
            for (int i = 0; i < ArrayEtiqueta.size(); i++) {
                System.out.println("ETQ: " + ArrayEtiqueta.get(i));
                //System.out.println(linea.getValor()); //Impresion de valor para verificarlo 
            } //Fin de for
                        
            //Impresion de etiquetas y valor del archivo TABSIM
            System.out.println("\nTABSIM"); //Indicar impresion del TABSIM
            for(int i = 0; i <= numlineas-1; i++) { //Recorrer Archivo tabsim
                ArchivoSalvacion tabsim = new ArchivoSalvacion("TABSIM.txt"); //Objeto con archivo salvacion
                System.out.println("ETQ TAB: " +tabsim.PosicionMatriz(i, 1) + "     VALOR TAB: " + tabsim.PosicionMatriz(i, 2)); //Impresion de etiqueta y valor del Archivo TABSIM
            } //Fin de for
            
            /* Crear segunda tabla para vizualicacion 
            String[] lineas = LineaCompleta.toString().split("\n");
            Object[][] datos = new Object[lineas.length][];
            int fila = 0;

            for (String nuevalinea : lineas) {
                String[] columnas = nuevalinea.split("\t");
                datos[fila] = columnas;
                fila++;
            } //Fin de for

            // Crear el modelo de la tabla y asignar los datos
            String[] columnas = {"Valor", "Etiqueta", "CODOP", "Operando", "ADDR", "Tamaño", "Postbyte"}; //Encabezados
            DefaultTableModel modelo = new DefaultTableModel(datos, columnas);

            // Crear el JTable y asignar el modelo
            JTable nuevatabla = new JTable(modelo);

            // Mostrar la tabla en un JFrame
            JFrame frame2 = new JFrame();
            frame2.add(new JScrollPane(nuevatabla));
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.pack();
            frame2.setVisible(true);
            */
            
            //Contenido de la tabla (1era Fase)
            System.out.println("\nTABLA (Fase 1) \n" + LineaCompleta.toString()); //Imprimir StringBuilder (Validamos el contenido de la tabla)
            //frame.setVisible(true); //Hacer visible la tabla
            // Abrir archivos TABSIM y LISTADO
            
            try { //Impresion de los dos archivos
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
                    
                    if (codop.equalsIgnoreCase("EQU")) { //Encontrar EQU
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
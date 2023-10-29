/* 
Proyecto Integrador | Programacion de bajo nivel
Equipo 10 | Integrantes: 
    - Hernandez Gutierrez Emmanuel 
    - Jimenez Castellanos Jesus Alejandro
    - Martinez Isaac
*/

package proyectointegrador_equipo10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

    /*Como utilizar el archivo salvacion
        
        Ejemplo para imprmimir un texto desde el archivo salvacion
        //System.out.println(ArchivoSalvacion.Salvacion[100][0]); //impresion de prueba
            //Imprime el contenido de la fila 100 y columna 0 mediante una funcion

            Descripcion del contenido de las filas en el archivo salvacion
            //0 | CODOPS
            //1 | Operandos
            //2 | Modos de direccionamiento
            //3 | Codigo maquina
            //4 | Bytes por calcular
            //5 | Bytes totales    

            En el archivo salvacion existen 592 columnas */

public class TablaA3 {
    static String[][] Salvacion; //Variable auxiliar para guarda la matriz
    private String Archivo; //Variable auxiliar para leer el archivo 
    
    //Constructor 
    public TablaA3(String Archivo) {
        this.Archivo = Archivo;
        Salvacion = Arreglotxt(); //Salvacion es la matriz 
    } //Fin del constructor 
    
    //Funcion para convertir a matriz
    private String[][] Arreglotxt() {
        //Variables auxiliares
        String line = null;
        int numLineas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(Archivo))) {
            while (br.readLine() != null) { //Mientras que existen lineas que leer
                numLineas++;//Contar el numero de lineas del archivo
            } //Fin de while
        } //Fin de try
        catch (IOException e) {
            e.printStackTrace(); //Mensaje de error
        } //Fin de catch

        String[][] matriz = new String[numLineas][];//Crear una matriz con el numero de lineas especifico

        try (BufferedReader br = new BufferedReader(new FileReader(Archivo))) {//pasar el contenido a la matriz
            for (int i = 0; (line = br.readLine()) != null; i++) { //Realizar por cada linea siempre y cuando sea diferente de null
                String[] campos = line.split("\t"); //Crear arreglo y partir linea por espacios con .split            
                for (int j = 0; j < campos.length; j++) { // Eliminar comillas de cada valor antes de agregarlo a la matriz
                    campos[j] = campos[j].replace("\"", "").trim();
                } //Fin de for
                matriz[i] = campos;
            } //Fin de for
        } //Fin de try 
        catch (IOException e) {
            e.printStackTrace(); //Mensaje de error
            return null;
        } //Fin de catch
      return matriz; //Devolver matriz
    } //Fin de la funcion para convertir un txt a matriz 
    
    //Funcion para buscar un posicion en la matriz
    public String PosicionMatriz(int fila, int columna) {
        //fila = fila - 1; 
        //columna = columna - 1;
        return Salvacion[fila][columna]; //Impresion del archivo con los valores de columna y fila definidos         
    } //Fin de la funcion para obtener la posicion de la columna y fila en especifico 
    
} //Fin de la clase 

// Clase para leer el archivo salvacion 
package proyectointegrador_equipo10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArchivoSalvacion {
    static String[][] Salvacion; //Variable auxiliar para guarda la matriz
    private String Archivo; //Variable auxiliar para leer el archivo 
    
    //Constructor 
    public ArchivoSalvacion(String Archivo) {
        this.Archivo = Archivo;
        Salvacion = Arreglotxt(); //Salvacion es la matriz 
    } //Fin del constructor 
    
    //Funcion para convertir a matriz
    private String[][] Arreglotxt() {
        String line = null;
        int numLineas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(Archivo))) {
            while (br.readLine() != null) {
                numLineas++;//Contar el numero de lineas del archivo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] matriz = new String[numLineas][];//Crear una matriz con el numero de lineas especifico

        try (BufferedReader br = new BufferedReader(new FileReader(Archivo))) {//pasar el contenido a la matriz
            for (int i = 0; (line = br.readLine()) != null; i++) {
                String[] campos = line.split("\t");
                // Eliminar comillas de cada valor antes de agregarlo a la matriz
                for (int j = 0; j < campos.length; j++) {
                    campos[j] = campos[j].replace("\"", "").trim();
                }
                matriz[i] = campos;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
      return matriz;
    } //Fin de la funcion para convertir un txt a matriz 
    
    //Funcion para buscar un posicion en la matriz //Hola
    public String PosicionMatriz(int fila, int columna) {
        //fila = fila - 1; 
        //columna = columna - 1;
        return Salvacion[fila][columna]; //Impresion del archivo con los valores de columna y fila definidos 
        
    } //Fin de la funcion para obtener la posicion de la columna y fila en especifico 
} //Fin de la clase 

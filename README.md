El ensamblador HC12 es un lenguaje de programación de bajo nivel específico para los microcontroladores de la familia HC12 (también conocida como HCS12) de Freescale Semiconductor, que anteriormente formaba parte de Motorola. 
Estos microcontroladores son utilizados en una amplia gama de aplicaciones embebidas debido a su capacidad y versatilidad.

Algunos ejemplos del código ensamblador en HC12 se encuentran en la carpeta, contienen los diversos modos de direccionamiento, ademas de un archivo "Killer" de prueba para ejecutar y probar errores dentro del código ensamblador 

Para la ejecución del programa, es posible utilizar los archivos .JAR, basta unicamente con abrir el ejecutable y cargar archivos de texto con código ensablador dentro, para ello se proporciona la carpeta con archivos de prueba dentro del mismo repositorio 

# HOLA MUNDO 

Este proyecto implementa un ensamblador para el procesador HC12, diseñado en Java, que realiza las operaciones esenciales para interpretar y ejecutar instrucciones en este microcontrolador. Es ideal para quienes desean comprender mejor el funcionamiento del ensamblador y cómo interactúa con las instrucciones de bajo nivel.

##Objetivo:
El proyecto tiene fines educativos y busca ayudar a entender los principios de un ensamblador, las operaciones del procesador HC12, y la gestión de sus instrucciones en bajo nivel.


##Funcionalidades principales:
Lectura de archivos de entrada:

1. Soporte para archivos con código fuente en formato ensamblador (ASM).
2. Identificación de etiquetas, códigos operando, tamaño y modo de direccionamiento.
3. Cálculo del postbyte:

##Descripción de Prefijos
Binario | Octal |  Hexadecimal |  Decimal
|--------------|--------------|--------------|  
| % | @ | $ | No tiene símbolo |  


Determinación del postbyte según las instrucciones específicas del HC12.
Contador de programa (Program Counter):

Actualización dinámica del contador de programa basado en cada instrucción procesada.
Compatibilidad con etiquetas y saltos (para el modo de direccionamiento de los relativos):

Resolución de etiquetas para instrucciones de salto y direccionamiento relativo.
Validación y manejo de errores:

## Detección de errores de sintaxis en el código ensamblador.
Manejo de instrucciones y operandos no válidos.
1. En caso de que exista un error el programa encontrará y marcara el error:
CONTLOC | OPR |  ADDR |  Postbyte
|--------------|--------------|--------------|  
| Mensaje de error | Desbordamiento       | Error OPR       | Error DIR      |  Error Postbyte 
| Ejemplo de error | #$FFt | opr      | dir      | lol

Descripción de los errores:
- Operando -> #FFt | "t" no es parte de los hexadecimales
- 

Requisitos del sistema:
1. Java Development Kit (JDK): versión 8 o superior.
2. Un editor de texto o IDE (como IntelliJ IDEA o Eclipse) para ejecutar el proyecto.
3. Archivos
4. Es posible descargar la carpeta de "Ejecutables" y "ASM". Los ejecutables son archivos .JAR que se podrán ejecutar de manera directa sin utilizar una IDE, el uso de este programa requiere de archivos ASM, mismos que se proporcionan en el repositorio, la carpeta "ASM" cuenta con varios archivos para probar el programa, con diferentes modos de direccionamiento, incluso con errores, para hacer uso se recomienda leer los manueles y ver los ejemplos proporcionados 

Cómo usar:
1. Clona este repositorio en tu máquina local.
2. Carga el proyecto en tu IDE favorito.
3. Compila y ejecuta la clase principal.
4. Proporciona un archivo ensamblador válido para procesar las instrucciones del HC12 (en la carpeta ASM se proporcionan un conjunto de archivos para probar).



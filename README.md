# HC12

El ensamblador HC12 es un lenguaje de programación de bajo nivel específico para los microcontroladores de la familia HC12 (también conocida como HCS12) de Freescale Semiconductor, que anteriormente formaba parte de Motorola. Estos microcontroladores son utilizados en una amplia gama de aplicaciones embebidas debido a su capacidad y versatilidad. Este proyecto implementa un ensamblador para el procesador HC12, diseñado en Java, que realiza las operaciones esenciales para interpretar y ejecutar instrucciones en este microcontrolador. Es ideal para quienes desean comprender mejor el funcionamiento del ensamblador y cómo interactúa con las instrucciones de bajo nivel.

##Objetivo:
El proyecto tiene fines educativos y busca ayudar a entender los principios de un ensamblador, las operaciones del procesador HC12, y la gestión de sus instrucciones en bajo nivel.

##Funcionalidades principales:
Lectura de archivos de entrada:

1. Soporte para archivos con código fuente en formato ensamblador (ASM).
2. Identificación de etiquetas, códigos operando, tamaño y modo de direccionamiento.
3. Cálculo del postbyte:

##Descripción de Prefijos para los sistemas númericos
| **Binario** | **Octal** | **Hexadecimal** | **Decimal** |
| ----- | ------------- | ------------ | --- | 
| % | @ | $ | No tiene símbolo |  

## Determinación del postbyte según las instrucciones específicas del HC12.
| **Contador de programa**      | CONTLOC         | Mantiene la sumatoria de acuerdo a las instrucciones proporcionadas.            |
| **Etiqueta**                  | ETQ             | Etiquetas encontradas en el archivo ASM.                                        |
| **Código Operando**           | OPR             | Número decimal, binario, octal o hexadecimal.                                  |
| **Modo de direccionamiento**  | ADDR               | Especifica cómo se obtiene la dirección o el operando para una instrucción. En el HC12, existen diferentes tipos de modos de direccionamiento que determinan cómo se accede a los datos. |
| **Tamaño**                    | SIZE            | Cantidad de bytes que ocupa una instrucción en memoria.                         |
| **Postbyte**                  | POSTBYTE        | Define la longitud de la instrucción en hexadecimal.                           |

## Tipos de modos de direccionamiento 

## Detección de errores de sintaxis en el código ensamblador.
Manejo de instrucciones y operandos no válidos.
1. En caso de que exista un error el programa encontrará y marcara el error:
   
| **Contador de programa** | **Operando** |  **ADDR** |  **Postbyte** |
| ------------ | ------------ | ------------ | ------------ | 
| Desbordamiento | Error OPR | Error DIR | Error Postbyte |
| Número imposible de calcular | #$FFt | Código operando erróneo | Número imposible de calcular | 

### ¿A qué se refiere "Número imposible calcular"?
1. El número proporcionado en cualquiera de las 4 sistemas númericos esta mal proporcionado
- Debido a que el prefijo está mal
- No es un numero entre 64 kylobytes (número decimal mayor o igual a 65536)
- Existen caractéres inválidos dentro del operando
- No es un número válido de acuerdo con su sistema decimal

### Ejemplo de números imposibles de calcular
- Operando -> #$FFt | "t" no es parte de los hexadecimales
- Operando -> #ABC | El número está en sistema hexadecimal y no tiene su prefijo
- Operando -> #FFFF | El número es mayor a 65536 en sistema decimal, por lo que causa un desbordamiento de memoria 

Requisitos del sistema:
1. Java Development Kit (JDK): versión 8 o superior.
2. Un editor de texto o IDE (como IntelliJ IDEA o Eclipse) para ejecutar el proyecto.
3. Archivos ASM
4. Es posible descargar la carpeta de "Ejecutables" y "ASM". Los ejecutables son archivos .JAR que se podrán ejecutar de manera directa sin utilizar una IDE, el uso de este programa requiere de archivos ASM, mismos que se proporcionan en el repositorio, la carpeta "ASM" cuenta con varios archivos para probar el programa, con diferentes modos de direccionamiento, incluso con errores, para hacer uso se recomienda leer los manueles y ver los ejemplos proporcionados 

Cómo usar:
1. Clona este repositorio en tu máquina local.
2. Carga el proyecto en tu IDE favorito.
3. Compila y ejecuta la clase principal, el programa terminado se encuentra disponible dentro de la carpeta "Parte 5". También es posible utilizar los archivos .JAR para ejecutar de manera directa.  
4. Proporciona un archivo ensamblador válido para procesar las instrucciones del HC12 (en la carpeta ASM se proporcionan un conjunto de archivos para probar).



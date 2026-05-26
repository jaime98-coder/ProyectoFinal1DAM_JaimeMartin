=========================================================
PROYECTO FINAL 1º DAM: TIENDA DE ROPA ARTESANAL
=========================================================
Autor: Jaime Martín

1. IDE Y VERSIÓN DE JAVA USADOS
- Entorno de Desarrollo (IDE): Eclipse IDE.
- Versión de Java: Java 21 (JDK 21 LTS).

2. DEPENDENCIAS EXTERNAS
- Este proyecto NO utiliza dependencias externas ni librerías de terceros.
- Toda la aplicación está desarrollada utilizando las bibliotecas estándar de Java SE (Collections, Stream API, Ficheros de texto genéricos). No es necesario instalar ni configurar Maven, Gradle ni descargar archivos .jar adicionales.

3. CÓMO COMPILAR Y EJECUTAR
- Paso 1: Descomprimir el archivo .zip de la entrega.
- Paso 2: Importar el proyecto en Eclipse (File > Import > General > Existing Projects into Workspace y seleccionar la carpeta del proyecto).
- Paso 3: Asegurarse de que la carpeta "recursos" (que contiene los archivos .csv iniciales) se encuentra en la raíz del proyecto, exactamente al mismo nivel que las carpetas "src" y "doc".
- Paso 4: En el panel "Package Explorer", desplegar la carpeta "src" -> paquete "tienda" -> abrir el archivo "Main.java".
- Paso 5: Hacer clic derecho sobre el código de "Main.java" -> Run As -> Java Application.
- El programa compilará automáticamente y la interfaz interactiva se mostrará en la consola de Eclipse.

4. CREDENCIALES DE BASE DE DATOS (AMPLIACIÓN)
- Este proyecto NO requiere conexión a un motor de base de datos relacional (MySQL, Oracle, etc.), por lo que no hay credenciales, puertos ni usuarios que configurar.
- El sistema de persistencia y almacenamiento se ha implementado de forma local leyendo y escribiendo sobre archivos separados por punto y coma (.csv) ubicados en la carpeta "recursos/". Los archivos son: clientes.csv, inventario.csv e historial_pedidos.csv.
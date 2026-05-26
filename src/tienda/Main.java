package tienda;

import tienda.servicio.GestorRopa;
import tienda.ui.Menu;

/**
 * Punto de entrada único y principal de la aplicación de la Tienda de Ropa
 * Artesanal. Esta clase cumple la función de lanzador del sistema: se encarga
 * de instanciar el núcleo de la lógica de negocio (GestorRopa) y de vincularlo
 * con la capa de presentación (Menu), delegando en esta última el hilo de
 * ejecución principal. * @author Jaime Martín
 * 
 * @version 1.0
 */
public class Main {

	public static void main(String[] args) {
		// 1. Instanciamos el núcleo lógico del sistema (Capa de Servicio/Negocio)
		GestorRopa gestor = new GestorRopa();

		// 2. Instanciamos la interfaz de usuario por consola pasándole el gestor (Capa
		// UI)
		// De esta forma aplicamos una inyección de dependencias manual y limpia
		Menu menu = new Menu(gestor);

		// 3. Arrancamos el bucle interactivo del programa (lectura, validación y
		// persistencia)
		menu.iniciar();
	}
}
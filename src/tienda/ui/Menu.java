package tienda.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import tienda.modelo.*;
import tienda.servicio.GestorRopa;
import tienda.excepciones.*;

/**
 * Clase que gestiona la interfaz de usuario por consola. Proporciona menús
 * interactivos y submenús validados para realizar las operaciones CRUD de la
 * tienda artesanal, capturando excepciones de forma limpia. * @author Jaime
 * Martín
 * 
 * @version 1.0
 */
public class Menu {

	private Scanner sc;
	private GestorRopa gestor;

	/**
	 * Constructor de la interfaz de usuario.
	 * 
	 * @param gestor El motor lógico de la aplicación (Lógica de negocio).
	 */
	public Menu(GestorRopa gestor) {
		this.sc = new Scanner(System.in);
		this.gestor = gestor;
	}

	/**
	 * Método principal que arranca el bucle de la aplicación. Carga los datos al
	 * iniciar y los salva automáticamente al salir.
	 */
	public void iniciar() {
		System.out.println("=========================================");
		System.out.println("   INICIANDO TIENDA DE ROPA ARTESANAL   ");
		System.out.println("=========================================");

		// Carga automática al arrancar (Apartado 3.7 del PDF)
		try {
			gestor.cargarDatos();
			System.out.println("Datos históricos cargados correctamente desde los archivos CSV.");
		} catch (IOException e) {
			System.out.println("Aviso: No se pudieron cargar los datos previos (Primer arranque).");
		}

		int opcion = 0;
		do {
			mostrarMenuPrincipal();
			opcion = leerEntero("Seleccione una opción: ");
			System.out.println();

			switch (opcion) {
			case 1:
				menuCatalogo();
				break;
			case 2:
				menuClientes();
				break;
			case 3:
				menuPedidos();
				break;
			case 4:
				menuConsultasStreams();
				break;
			case 5:
				// Guardado automático al cerrar
				try {
					System.out.println("Guardando datos en ficheros CSV...");
					gestor.guardarDatos();
					System.out.println("¡Datos guardados con éxito! Gracias por usar la aplicación.");
				} catch (IOException e) {
					System.out.println("Error crítico al guardar los datos en el disco duro: " + e.getMessage());
				}
				break;
			default:
				System.out.println("Opción inválida. Por favor, elija un número del 1 al 5.");
			}
			System.out.println();
		} while (opcion != 5);
	}

	/**
	 * Muestra el menú principal de la aplicación con las opciones disponibles para
	 * el usuario. Este método se llama en cada iteración del bucle principal para
	 * que el usuario pueda elegir qué acción realizar.
	 */
	private void mostrarMenuPrincipal() {
		System.out.println("========= MENÚ PRINCIPAL =========");
		System.out.println("1. Gestión de Catálogo (Ropa)");
		System.out.println("2. Gestión de Clientes");
		System.out.println("3. Gestión de Pedidos (Ventas)");
		System.out.println("4. Consultas Avanzadas y Reportes (Streams)");
		System.out.println("5. Guardar y Salir");
		System.out.println("==================================");
	}

	// =========================================================================
	// SUBMENÚS Y OPERACIONES CRUD
	// =========================================================================

	/**
	 * Submenú para la gestión de prendas en el inventario de la tienda.
	 */
	private void menuCatalogo() {
		int opcion = 0;
		do {
			System.out.println("---- GESTIÓN DE CATÁLOGO ----");
			System.out.println("1. Dar de alta una prenda (Añadir)");
			System.out.println("2. Dar de baja una prenda (Eliminar)");
			System.out.println("3. Buscar una prenda por ID");
			System.out.println("4. Mostrar catálogo completo");
			System.out.println("5. Volver al menú principal");
			opcion = leerEntero("Elija una opción: ");
			System.out.println();

			switch (opcion) {
			case 1:
				ejecutarAltaRopa();
				break;
			case 2:
				ejecutarBajaRopa();
				break;
			case 3:
				ejecutarBuscarRopa();
				break;
			case 4:
				System.out.println("--- CATÁLOGO COMPLETO ---");
				ejecutarMostrarCatalogo();
				break;
			case 5:
				System.out.println("Volviendo...");
				break;
			default:
				System.out.println("Opción incorrecta.");
			}
			System.out.println();
		} while (opcion != 5);
	}

	private void ejecutarAltaRopa() {
		System.out.println("--- ALTA DE NUEVA PRENDA ---");
		System.out.println("Seleccione el tipo de prenda:");
		System.out.println("1. Camiseta | 2. Sudadera | 3. Body | 4. Cargo | 5. Vaquero | 6. Falda | 7. Vestido");
		int tipo = leerEntero("Tipo: ");

		if (tipo < 1 || tipo > 7) {
			System.out.println("Tipo de prenda no válido.");
			return;
		}

		String id = leerTextoObligatorio("Introduce el ID único (ej: C01): ").toUpperCase();
		String nombre = leerTextoObligatorio("Introduce el nombre comercial: ");
		double precio = leerDouble("Introduce el precio base (Euros): ");
		String talla = leerTextoObligatorio("Introduce la talla: ").toUpperCase();
		int stock = leerEntero("Introduce las unidades en stock: ");

		Ropa nuevaPrenda = null;

		// Atributos específicos según la jerarquía polimórfica
		if (tipo >= 1 && tipo <= 3) { // Prendas Superiores
			String manga = leerTextoObligatorio("Introduce el tipo de manga (Corta/Larga): ");
			if (tipo == 1)
				nuevaPrenda = new Camiseta(id, nombre, precio, talla, stock, manga);
			if (tipo == 2) {
				boolean capucha = leerBoolean("¿Tiene capucha? (S/N): ");
				nuevaPrenda = new Sudadera(id, nombre, precio, talla, stock, manga, capucha);
			}
			if (tipo == 3) {
				String cierre = leerTextoObligatorio("Introduce el tipo de cierre (Broches/Cremallera): ");
				nuevaPrenda = new Body(id, nombre, precio, talla, stock, manga, cierre);
			}
		} else if (tipo >= 4 && tipo <= 6) { // Prendas Inferiores
			String largo = leerTextoObligatorio("Introduce el largo (Corto/Medio/Largo): ");
			if (tipo == 4) {
				int bolsillos = leerEntero("Introduce el número de bolsillos: ");
				nuevaPrenda = new Cargo(id, nombre, precio, talla, stock, largo, bolsillos);
			}
			if (tipo == 5)
				nuevaPrenda = new Vaquero(id, nombre, precio, talla, stock, largo);
			if (tipo == 6) {
				boolean volantes = leerBoolean("¿Tiene volantes? (S/N): ");
				nuevaPrenda = new Falda(id, nombre, precio, talla, stock, largo, volantes);
			}
		} else if (tipo == 7) { // Cuerpo completo
			String largo = leerTextoObligatorio("Introduce el largo: ");
			String ocasion = leerTextoObligatorio("Introduce la ocasión (Casual/Fiesta/Formal): ");
			nuevaPrenda = new Vestido(id, nombre, precio, talla, stock, largo, ocasion);
		}

		gestor.agregarRopa(nuevaPrenda);
		System.out.println("Prenda registrada con éxito en el catálogo en memoria.");
	}

	private void ejecutarBajaRopa() {
		System.out.println("--- BAJA DE PRENDA ---");
		String id = leerTextoObligatorio("Introduce el ID de la prenda a eliminar: ").toUpperCase();
		try {
			gestor.eliminarRopa(id);
			System.out.println("La prenda con ID '" + id + "' ha sido eliminada del inventario.");
		} catch (ArticuloNoEncontradoException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void ejecutarBuscarRopa() {
		System.out.println("--- BUSCAR PRENDA ---");
		String id = leerTextoObligatorio("Introduce el ID de la prenda: ");
		try {
			Ropa ropa = gestor.buscarRopa(id);
			System.out.println("\nResultado encontrado:");
			System.out.println(ropa.toString());

			if (ropa instanceof CuidadoEspecial) {
				System.out.println("[CUIDADO ESPECIAL]: " + ((CuidadoEspecial) ropa).getInstruccionesCuidado());
			}
		} catch (ArticuloNoEncontradoException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void ejecutarMostrarCatalogo() {
		System.out.println("--- CATÁLOGO COMPLETO ---");
		ArrayList<Ropa> catalogo = gestor.getCatalogoCompleto();

		if (catalogo.isEmpty()) {
			System.out.println("El catálogo está vacío actualmente.");
		} else {
			for (Ropa ropa : catalogo) {
				System.out.println(" - " + ropa.toString());
			}
			System.out.println("-------------------------");
			System.out.println("Total de artículos: " + catalogo.size());
		}
	}

	/**
	 * Submenú para la gestión de Clientes de la tienda.
	 */
	private void menuClientes() {
		System.out.println("---- GESTIÓN DE CLIENTES ----");
		System.out.println("--- REGISTRAR NUEVO CLIENTE ---");
		String dni = leerTextoObligatorio("Introduce el DNI del cliente: ").toUpperCase();
		// VALIDACIÓN: Comprobamos si el DNI ya está registrado en el sistema con el fin
		// de mejorar la experiencia de usuario, ya que el propio HashSet hace que no se
		// pueda insertar otro cliente con mismo DNI, pero así podemos mostrar un
		// mensaje de error más claro
		Cliente clienteExistente = gestor.buscarClientePorDni(dni);
		if (clienteExistente != null) {
			// Si el cliente ya existe, mostramos el error y el bloque else se encarga de
			// saltar el resto
			System.out.println("Error: El cliente con DNI '" + dni + "' ya está dado de alta.");
			System.out.println("Nombre del cliente registrado: " + clienteExistente.getNombre());
		} else {
			// Si el cliente NO existe, pedimos el resto de datos de forma segura
			String nombre = leerTextoObligatorio("Introduce el nombre completo: ");
			String email = leerTextoObligatorio("Introduce el correo electrónico: ");
			String tlf = leerTextoObligatorio("Introduce el número de teléfono: ");

			Cliente nuevo = new Cliente(dni, nombre, email, tlf);
			gestor.registrarCliente(nuevo);
			System.out.println("Cliente guardado correctamente.");
		}
	}

	/**
	 * Submenú interactivo para gestionar el flujo real de pedidos, el carrito de
	 * compras y la validación de clientes por DNI.
	 */
	private void menuPedidos() {
		System.out.println("---- GESTIÓN DE PEDIDOS Y VENTAS ----");
		System.out.println("1. Crear un nuevo Pedido interactivo (Carrito de compra)");
		System.out.println("2. Volver al menú principal");
		int opcionElegida = leerEntero("Elija opción: ");

		if (opcionElegida == 1) {
			System.out.println("\n--- APERTURA DE NUEVO PEDIDO ---");
			String dni = leerTextoObligatorio("Introduce el DNI del cliente: ").toUpperCase();

			// 1. VALIDACIÓN FUNDAMENTAL: Comprobar que el cliente exista en el sistema
			Cliente clienteReal = gestor.buscarClientePorDni(dni);
			if (clienteReal == null) {
				System.out.println("Error: No existe ningún cliente registrado con el DNI '" + dni + "'.");
				System.out.println("Por favor, registre primero al cliente en la opción correspondiente del menú.");
				return;
			}

			// 2. Si existe, creamos el pedido real vinculado al cliente de la base de datos
			Pedido nuevoPedido = gestor.crearPedido(clienteReal);
			System.out.println("¡Pedido '" + nuevoPedido.getIdPedido() + "' inicializado con éxito para: "
					+ clienteReal.getNombre() + "!");

			// 3. SUB-BUCLE: El carrito de compra para añadir ropa al pedido actual
			int subOpcion = 0;
			do {
				System.out.println("\n[CARRITO] GESTIÓN DEL PEDIDO ACTUAL [" + nuevoPedido.getIdPedido() + "]");
				System.out.println("1. Añadir prenda al carrito de este pedido");
				System.out.println("2. Mostrar ticket y desglose actual del importe");
				System.out.println("3. Finalizar pedido y volver");
				subOpcion = leerEntero("Seleccione una acción: ");

				switch (subOpcion) {
				case 1:
					String idArticulo = leerTextoObligatorio("Introduce el ID de la prenda que compra (ej: C01): ")
							.toUpperCase();
					try {
						Ropa prenda = gestor.buscarRopa(idArticulo);

						nuevoPedido.agregarRopa(prenda);
						System.out.println(
								"El articulo '" + prenda.getNombre() + "' ha sido añadido con éxito al pedido.");
						System.out.println("   [Stock restante en tienda: " + prenda.getStock() + " uds]");

					} catch (ArticuloNoEncontradoException e) {
						System.out.println("Error: " + e.getMessage());
					} catch (StockAgotadoException e) {
						System.out.println("Error de Almacén: " + e.getMessage());
					}
					break;

				case 2:
					System.out.println(nuevoPedido.toString());
					break;

				case 3:
					try {
						nuevoPedido.cambiarEstado(EstadoPedido.COMPLETADO);
						System.out.println(
								"Pedido '" + nuevoPedido.getIdPedido() + "' COMPLETADO y cobrado correctamente.");
					} catch (EstadoPedidoInvalidoException e) {
						System.out.println("Error al procesar el estado del pedido: " + e.getMessage());
					}
					break;

				default:
					System.out.println("Opción no válida del carrito.");
				}
			} while (subOpcion != 3);
		}
	}

	/**
	 * Submenú que explota al máximo nivel el uso obligatorio de la Stream API
	 */
	private void menuConsultasStreams() {
		int opcion = 0;
		do {
			System.out.println("---- CONSULTAS AVANZADAS (STREAMS) ----");
			System.out.println("1. Filtrar catálogo por Talla");
			System.out.println("2. Filtrar catálogo por Precio Máximo");
			System.out.println("3. Ver sólo artículos que admiten Personalización");
			System.out.println("4. Ver catálogo completo Ordenado por Precio (Económicos primero)");
			System.out.println("5. Consultar los Ingresos Totales de la caja (Pedidos Completados)");
			System.out.println("6. Volver al menú principal");
			opcion = leerEntero("Elija consulta: ");
			System.out.println();

			switch (opcion) {
			case 1:
				String talla = leerTextoObligatorio("Introduce la talla a buscar: ").toUpperCase();
				ArrayList<Ropa> porTalla = gestor.buscarPorTalla(talla);
				imprimirListaRopa(porTalla);
				break;
			case 2:
				double max = leerDouble("Introduce el presupuesto máximo (Euros): ");
				ArrayList<Ropa> porPrecio = gestor.buscarPorPrecioMax(max);
				imprimirListaRopa(porPrecio);
				break;
			case 3:
				System.out.println("--- PRENDAS ARTESANALES PERSONALIZABLES ---");
				ArrayList<Personalizable> personalizables = gestor.getArticulosPersonalizables();
				if (personalizables.isEmpty())
					System.out.println("No hay prendas configuradas con personalización.");
				for (Personalizable personalizable : personalizables) {
					System.out.println(" -> " + personalizable.toString() + " | Opciones: "
							+ personalizable.getOpcionesPersonalizacion());
				}
				break;
			case 4:
				System.out.println("--- PRENDAS ORDENADAS POR PRECIO ---");
				ArrayList<Ropa> ordenadas = gestor.getPrendasOrdenadasPorPrecio();
				imprimirListaRopa(ordenadas);
				break;
			case 5:
				double ingresos = gestor.calcularIngresosTotales();
				System.out.printf("INGRESOS TOTALES EN CAJA REGISTRADORA: %.2f Euros\n", ingresos);
				break;
			case 6:
				System.out.println("Saliendo de reportes...");
				break;
			default:
				System.out.println("Opción inválida.");
			}
			System.out.println();
		} while (opcion != 6);
	}

	/**
	 * Método auxiliar para imprimir una lista de prendas de forma legible.
	 * 
	 * @param lista la lista de prendas a imprimir
	 */
	private void imprimirListaRopa(ArrayList<Ropa> lista) {
		if (lista.isEmpty()) {
			System.out.println("No se encontraron prendas con los criterios especificados.");
		} else {
			for (Ropa ropa : lista) {
				System.out.println(ropa.toString());
			}
		}
	}

	// =========================================================================
	// METODOS AUXILIARES DE VALIDACIÓN ROBUSTA
	// =========================================================================

	/**
	 * Lee un número entero del usuario, validando que la entrada sea correcta.
	 * 
	 * @param mensaje el mensaje que se mostrará al usuario
	 * 
	 * @return el número entero ingresado por el usuario
	 */
	private int leerEntero(String mensaje) {
		int numero = 0;
		boolean valido = false;
		while (!valido) {
			System.out.print(mensaje);
			try {
				numero = sc.nextInt();
				valido = true;
			} catch (InputMismatchException e) {
				System.out.println("Error: Debes introducir un número entero válido.");
			} finally {
				sc.nextLine(); // Limpia la basura y el salto de línea del buffer
			}
		}
		return numero;
	}

	/**
	 * Lee un número decimal del usuario, permitiendo tanto punto como coma.
	 * 
	 * @param mensaje el mensaje que se mostrará al usuario
	 * 
	 * @return el número decimal ingresado
	 */
	private double leerDouble(String mensaje) {
		double numero = 0.0;
		boolean valido = false;
		while (!valido) {
			System.out.print(mensaje);
			try {
				String entrada = sc.nextLine().replace(",", ".");
				numero = Double.parseDouble(entrada);
				if (numero < 0) {
					System.out.println("Error: El valor monetario no puede ser negativo.");
				} else {
					valido = true;
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Debes introducir un número decimal válido.");
			}
		}
		return numero;
	}

	/**
	 * Lee una cadena de texto del usuario y asegura que no esté vacía. * @param
	 * mensaje el mensaje que se mostrará al usuario
	 * 
	 * @return el texto ingresado por el usuario
	 */
	private String leerTextoObligatorio(String mensaje) {
		String texto = "";
		while (texto.trim().isEmpty()) {
			System.out.print(mensaje);
			texto = sc.nextLine();
			if (texto.trim().isEmpty()) {
				System.out.println("Error: Este campo es obligatorio y no puede dejarse en blanco.");
			}
		}
		return texto;
	}

	/**
	 * Lee una respuesta de Sí/No del usuario y la convierte a booleano.
	 * 
	 * @param mensaje El mensaje que se mostrará al usuario
	 * 
	 * @return true si responde 'S', false si responde 'N'
	 */
	private boolean leerBoolean(String mensaje) {
		while (true) {
			String entrada = leerTextoObligatorio(mensaje).toUpperCase();
			if (entrada.startsWith("S"))
				return true;
			if (entrada.startsWith("N"))
				return false;
			System.out.println("Error: Por favor, responde 'S' para Sí o 'N' para No.");
		}
	}
}
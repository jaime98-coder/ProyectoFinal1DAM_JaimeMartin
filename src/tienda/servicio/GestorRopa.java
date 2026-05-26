package tienda.servicio;

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

import tienda.modelo.*;
import tienda.excepciones.ArticuloNoEncontradoException;

/**
 * Clase gestora central de la tienda artesanal. Responsable de administrar las
 * colecciones de inventario, clientes e historial de pedidos en memoria RAM,
 * así como de controlar la persistencia de datos delegando en las entidades.
 * 
 * @author Jaime Martín
 * 
 * @version 1.0
 */
public class GestorRopa {

	// Atributos privados para cumplir estrictamente con el encapsulamiento
	private HashMap<String, Ropa> inventario;
	private HashSet<Cliente> clientes;
	private ArrayList<Pedido> historialPedidos;

	// Rutas físicas hacia la carpeta exterior exigida por el PDF
	private String rutaInventario = "recursos/inventario.csv";
	private String rutaClientes = "recursos/clientes.csv";
	private String rutaPedidos = "recursos/historial_pedidos.csv";

	/**
	 * Constructor del gestor. Inicializa las tres colecciones estructuradas vacías
	 * para alojar los datos en memoria durante la ejecución.
	 */
	public GestorRopa() {
		this.inventario = new HashMap<>();
		this.clientes = new HashSet<>();
		this.historialPedidos = new ArrayList<>();
	}

	// =========================================================================
	// ENTRADA/SALIDA DE FICHEROS
	// =========================================================================

	/**
	 * Lee de manera secuencial los archivos CSV y reconstruye las colecciones.
	 * Utiliza un bloque catch específico para FileNotFoundException para que el
	 * programa no explote si es el primer arranque de la tienda.
	 * 
	 * @throws IOException Si ocurre un error crítico de lectura en el disco duro.
	 */
	public void cargarDatos() throws IOException {
		// 1. Cargar la colección de Clientes
		try (BufferedReader in = new BufferedReader(new FileReader(rutaClientes))) {
			String lineaTXT = in.readLine();
			while (lineaTXT != null) {
				String[] partes = lineaTXT.split(Ropa.SEPARADOR);
				if (partes.length == 4) {
					Cliente clienteGuardado = new Cliente(partes[0], partes[1], partes[2], partes[3]);
					clientes.add(clienteGuardado);
				}
				lineaTXT = in.readLine();
			}
		} catch (FileNotFoundException e) {

		}

		// 2. Cargar el Inventario Polimórfico (Usa switch para discriminar las clases
		// hijas)
		try (BufferedReader in = new BufferedReader(new FileReader(rutaInventario))) {
			String lineaTXT = in.readLine();
			while (lineaTXT != null) {
				String[] partes = lineaTXT.split(Ropa.SEPARADOR);
				if (partes.length >= 6) {
					String tipo = partes[0]; // Camiseta, Sudadera, Vestido...
					String id = partes[1];
					String nombre = partes[2];
					double precio = Double.parseDouble(partes[3]);
					String talla = partes[4];
					int stock = Integer.parseInt(partes[5]);

					// Reconstrucción exacta según la jerarquía polimórfica
					switch (tipo) {
					case "Camiseta":
						inventario.put(id, new Camiseta(id, nombre, precio, talla, stock, partes[6]));
						break;
					case "Sudadera":
						inventario.put(id, new Sudadera(id, nombre, precio, talla, stock, partes[6],
								Boolean.parseBoolean(partes[7])));
						break;
					case "Body":
						inventario.put(id, new Body(id, nombre, precio, talla, stock, partes[6], partes[7]));
						break;
					case "Cargo":
						inventario.put(id,
								new Cargo(id, nombre, precio, talla, stock, partes[6], Integer.parseInt(partes[7])));
						break;
					case "Vaquero":
						inventario.put(id, new Vaquero(id, nombre, precio, talla, stock, partes[6]));
						break;
					case "Falda":
						inventario.put(id, new Falda(id, nombre, precio, talla, stock, partes[6],
								Boolean.parseBoolean(partes[7])));
						break;
					case "Vestido":
						inventario.put(id, new Vestido(id, nombre, precio, talla, stock, partes[6], partes[7]));
						break;
					}
				}
				lineaTXT = in.readLine();
			}
		} catch (FileNotFoundException e) {
			// Atrapado de forma silenciosa
		}

		// 3. Cargar el Historial de Pedidos
		try (BufferedReader in = new BufferedReader(new FileReader(rutaPedidos))) {
			String lineaTXT = in.readLine();
			while (lineaTXT != null) {
				String[] partes = lineaTXT.split(Ropa.SEPARADOR);
				if (partes.length == 4) {
					String idPedido = partes[0];
					String dniCliente = partes[1];
					EstadoPedido estado = EstadoPedido.valueOf(partes[2]);
					String listaIds = partes[3];

					// Vinculamos el ticket al objeto Cliente real que ya reside en el conjunto
					Cliente clienteAsociado = buscarClientePorDni(dniCliente);

					if (clienteAsociado != null) {
						Pedido pedido = new Pedido(idPedido, clienteAsociado);
						try {
							pedido.cambiarEstado(estado);
						} catch (Exception e) {
							// Ignorar validación de transiciones en carga automática
						}

						// Volvemos a insertar las prendas compradas en el carrito de este pedido
						if (!listaIds.equals("VACIO")) {
							String[] ids = listaIds.split(",");
							for (String idArticulo : ids) {
								Ropa ropa = inventario.get(idArticulo);
								if (ropa != null) {
									pedido.getListaRopa().add(ropa);
								}
							}
						}
						historialPedidos.add(pedido);
					}
				}
				lineaTXT = in.readLine();
			}
		} catch (FileNotFoundException e) {
			// Atrapado de forma silenciosa
		}
	}

	/**
	 * Vuelca de forma masiva los datos de la memoria RAM a archivos físicos. Delega
	 * en el método getFichaArchivo() de cada entidad. * @throws IOException Si
	 * ocurre un error de escritura o falta de permisos.
	 */
	public void guardarDatos() throws IOException {
		// Aseguramos que el directorio de recursos externo exista en la raíz
		File carpeta = new File("recursos");
		if (!carpeta.exists()) {
			carpeta.mkdir();
		}

		// 1. Salvar Clientes
		try (BufferedWriter out = new BufferedWriter(new FileWriter(rutaClientes))) {
			for (Cliente cliente : clientes) {
				out.write(cliente.getFichaArchivo());
				out.newLine();
			}
		}

		// 2. Salvar Inventario
		try (BufferedWriter out = new BufferedWriter(new FileWriter(rutaInventario))) {
			for (Ropa ropa : inventario.values()) {
				out.write(ropa.getFichaArchivo());
				out.newLine();
			}
		}

		// 3. Salvar Pedidos
		try (BufferedWriter out = new BufferedWriter(new FileWriter(rutaPedidos))) {
			for (Pedido pedido : historialPedidos) {
				out.write(pedido.getFichaArchivo());
				out.newLine();
			}
		}
	}

	// =========================================================================
	// METODOS LOGICOS DE GESTIÓN (CRUD)
	// =========================================================================

	public void agregarRopa(Ropa ropa) {
		if (ropa != null) {
			inventario.put(ropa.getIdArticulo(), ropa);
		}
	}

	public void eliminarRopa(String idArticulo) throws ArticuloNoEncontradoException {
		String idNormalizado = idArticulo.toUpperCase(); // Convertimos a mayúscula

		if (!inventario.containsKey(idNormalizado)) {
			throw new ArticuloNoEncontradoException(
					"No se puede eliminar: El artículo '" + idNormalizado + "' no existe.");
		}
		inventario.remove(idNormalizado);
	}

	public Ropa buscarRopa(String idArticulo) throws ArticuloNoEncontradoException {
		String idNormalizado = idArticulo.toUpperCase(); // Convertimos a mayúscula

		Ropa ropa = inventario.get(idNormalizado);
		if (ropa == null) {
			throw new ArticuloNoEncontradoException("Búsqueda fallida: El artículo '" + idNormalizado + "' no existe.");
		}
		return ropa;
	}

	public void registrarCliente(Cliente cliente) {
		if (cliente != null) {
			clientes.add(cliente);
		}
	}

	public Pedido crearPedido(Cliente cliente) {
		String idNuevo = "P" + String.format("%03d", historialPedidos.size() + 1);
		Pedido nuevo = new Pedido(idNuevo, cliente);
		historialPedidos.add(nuevo);
		return nuevo;
	}

	// Método auxiliar privado para re-asociar objetos en el cargador
	public Cliente buscarClientePorDni(String dni) {
		for (Cliente clienteBuscado : clientes) {
			if (clienteBuscado.getDniCliente().equalsIgnoreCase(dni)) {
				return clienteBuscado;
			}
		}
		return null;
	}

	// CONSULTAS AVANZADAS (Stream API)
	public double calcularIngresosTotales() {
		return historialPedidos.stream().filter(p -> p.getEstado() == EstadoPedido.COMPLETADO)
				.mapToDouble(Pedido::calcularTotal).sum();
	}

	public ArrayList<Ropa> buscarPorTalla(String talla) {
		return inventario.values().stream().filter(r -> r.getTalla().equalsIgnoreCase(talla))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public ArrayList<Ropa> buscarPorPrecioMax(double max) {
		return inventario.values().stream().filter(r -> r.getPrecio() <= max)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public ArrayList<Personalizable> getArticulosPersonalizables() {
		return inventario.values().stream().filter(r -> r instanceof Personalizable).map(r -> (Personalizable) r)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public ArrayList<Ropa> getPrendasOrdenadasPorPrecio() {
		return inventario.values().stream().sorted((r1, r2) -> Double.compare(r1.getPrecio(), r2.getPrecio()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public ArrayList<Ropa> getCatalogoCompleto() {
		return new ArrayList<>(inventario.values());

	}
}
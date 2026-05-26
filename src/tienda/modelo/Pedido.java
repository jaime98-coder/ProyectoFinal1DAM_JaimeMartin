package tienda.modelo;

import java.util.ArrayList;
import tienda.excepciones.ArticuloNoEncontradoException;
import tienda.excepciones.EstadoPedidoInvalidoException;
import tienda.excepciones.StockAgotadoException;

/**
 * Clase que representa un pedido realizado por un cliente en la tienda
 * artesanal. Conecta al cliente con las prendas adquiridas, calcula el importe
 * total y gestiona el ciclo de vida del pedido (Pendiente, Completado o
 * Cancelado).
 * 
 * @author Jaime Martin
 * 
 * @version 1.0
 */
public class Pedido {

	/** Identificador único del pedido */
	private String idPedido;

	/** Cliente que realiza la compra. */
	private Cliente cliente;

	/** Colección de prendas incluidas en este pedido. */
	private ArrayList<Ropa> listaRopa;

	/** Estado actual en el que se encuentra el procesamiento del pedido. */
	private EstadoPedido estado;

	/**
	 * Constructor para crear un nuevo pedido con un identificador único y el
	 * cliente que lo realiza. Inicializa la lista de ropa vacía y establece el
	 * estado del pedido como PENDIENTE de forma predeterminada.
	 * 
	 * @param idPedido El identificador único del pedido
	 * 
	 * @param cliente  El cliente que realiza el pedido
	 */
	public Pedido(String idPedido, Cliente cliente) {
		this.idPedido = idPedido;
		this.cliente = cliente;
		this.listaRopa = new ArrayList<>();
		this.estado = EstadoPedido.PENDIENTE;
	}

	/** @return El identificador único del pedido */
	public String getIdPedido() {
		return idPedido;
	}

	/** @return El cliente asociado al pedido */
	public Cliente getCliente() {
		return cliente;
	}

	/** @return La lista de prendas añadidas al pedido */
	public ArrayList<Ropa> getListaRopa() {
		return listaRopa;
	}

	/** @return El estado actual del pedido */
	public EstadoPedido getEstado() {
		return estado;
	}

	/**
	 * Añade una prenda de ropa a la lista del pedido. Antes de añadirla, verifica
	 * si hay stock disponible utilizando la lógica de la propia prenda.
	 * 
	 * @param ropa La prenda de ropa que se desea añadir
	 * @throws StockAgotadoException Si la prenda no tiene unidades disponibles
	 *                               (stock 0 o negativo)
	 */
	public void agregarRopa(Ropa ropa) throws StockAgotadoException {
		// Verifica si hay stock usando tu excepción propia
		ropa.verificarDisponibilidad(1);

		// Si hay stock, lo añade al carrito
		this.listaRopa.add(ropa);

		// Restamos una unidad del inventario de la tienda
		ropa.setStock(ropa.getStock() - 1);
	}

	/**
	 * Elimina una prenda del pedido actual.
	 * 
	 * @param ropa La prenda exacta que se desea retirar del pedido
	 * 
	 * @throws ArticuloNoEncontradoException Si la prenda no existía dentro de este
	 *                                       pedido
	 */
	public void eliminarRopa(Ropa ropa) throws ArticuloNoEncontradoException {
		// .remove(Object) devuelve true si el elemento existía y fue borrado
		boolean eliminado = this.listaRopa.remove(ropa);

		if (!eliminado) {
			throw new ArticuloNoEncontradoException(
					"No se pudo eliminar: La prenda con ID " + ropa.getIdArticulo() + " no pertenece a este pedido.");
		}
	}

	/**
	 * Modifica el estado del pedido.
	 * 
	 * @param nuevoEstado El nuevo estado al que se quiere cambiar (PENDIENTE,
	 *                    COMPLETADO, CANCELADO)
	 * 
	 * @throws EstadoPedidoInvalidoException Si se intenta realizar una transición
	 *                                       de estado ilegal
	 */
	public void cambiarEstado(EstadoPedido nuevoEstado) throws EstadoPedidoInvalidoException {
		if (this.estado == EstadoPedido.COMPLETADO && nuevoEstado == EstadoPedido.CANCELADO) {
			throw new EstadoPedidoInvalidoException(
					"Error de gestión: No se puede cancelar un pedido que ya ha sido COMPLETADO.");
		}
		if (this.estado == EstadoPedido.CANCELADO) {
			throw new EstadoPedidoInvalidoException(
					"Error de gestión: El pedido está CANCELADO y no puede cambiar de estado.");
		}

		this.estado = nuevoEstado;
	}

	/**
	 * Calcula el precio total acumulado del pedido. Utiliza polimorfismo dinámico
	 * para comprobar si las prendas tienen aplicados costes adicionales por
	 * personalizaciones artesanales o empaquetados premium de regalo.
	 * 
	 * @return El coste total del pedido en euros
	 */
	public double calcularTotal() {
		double total = 0.0;

		for (Ropa ropa : listaRopa) {
			// 1. Sumamos el precio base de la prenda artesanal
			total += ropa.getPrecio();

			// 2. Si la prenda es Personalizable, sumamos su coste extra (si lo tiene)
			if (ropa instanceof Personalizable) {
				Personalizable personalizable = (Personalizable) ropa;
				total += personalizable.getCostePersonalizacion();
			}

			// 3. Si la prenda permite Empaquetado Premium, sumamos su coste extra (si está
			// activo)
			if (ropa instanceof EmpaquetablePremium) {
				EmpaquetablePremium empaquetable = (EmpaquetablePremium) ropa;
				total += empaquetable.getCosteEmpaquetadoPremium();
			}
		}

		return total;
	}

	/**
	 * Genera una línea estructurada para guardar el pedido en pedidos.csv. Formato:
	 * idPedido;dniCliente;ESTADO;idRopa1,idRopa2,idRopa3
	 */
	public String getFichaArchivo() {
		StringBuilder idsArticulos = new StringBuilder();

		if (listaRopa.isEmpty()) {
			idsArticulos.append("VACIO");
		} else {
			for (int i = 0; i < listaRopa.size(); i++) {
				idsArticulos.append(listaRopa.get(i).getIdArticulo());
				// Añadimos una coma para separar los IDs, salvo en el último elemento
				if (i < listaRopa.size() - 1) {
					idsArticulos.append(",");
				}
			}
		}

		return idPedido + Ropa.SEPARADOR + cliente.getDniCliente() + Ropa.SEPARADOR + estado.name() + Ropa.SEPARADOR
				+ idsArticulos.toString();
	}

	/**
	 * Devuelve una representación textual del pedido, incluyendo su identificador,
	 * estado, información del cliente, lista de prendas adquiridas y el total a
	 * pagar. Esta representación se formatea de manera clara y legible, con
	 * secciones separadas por líneas para facilitar su lectura.
	 * 
	 * @return Una cadena de texto que representa el ticket del pedido
	 */
	@Override
	public String toString() {
		String ticket = "=====================================================\n";
		ticket += "TICKET DE PEDIDO NÚMERO: " + idPedido + "\n";
		ticket += "ESTADO: " + estado + "\n";
		ticket += "-----------------------------------------------------\n";
		ticket += cliente.toString() + "\n";
		ticket += "-----------------------------------------------------\n";
		ticket += "ARTÍCULOS ADQUIRIDOS:\n";

		if (listaRopa.isEmpty()) {
			ticket += "   (El carrito del pedido está vacío)\n";
		} else {
			for (Ropa ropa : listaRopa) {
				ticket += " - " + ropa.toString() + "\n";
			}
		}

		ticket += "-----------------------------------------------------\n";
		ticket += String.format("TOTAL A PAGAR: %.2f€\n", calcularTotal());
		ticket += "=====================================================";

		return ticket;
	}
}
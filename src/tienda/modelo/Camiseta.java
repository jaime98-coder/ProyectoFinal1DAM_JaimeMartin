package tienda.modelo;

import java.util.ArrayList;

/**
 * Representa una camiseta de la tienda artesanal. Implementa Personalizable
 * para permitir opciones de estampado y bordado.
 *
 * @author Jaime Martin
 */
public class Camiseta extends PrendaSuperior implements Personalizable {

	/** Opción de personalización actualmente aplicada. Null si no tiene ninguna. */
	private String personalizacionAplicada;

	/**
	 * Crea una nueva camiseta. La personalización empieza como null (sin
	 * personalizar).
	 *
	 * @param idArticulo identificador único del artículo
	 * @param nombre     nombre de la camiseta
	 * @param precio     precio base en euros
	 * @param talla      talla de la prenda
	 * @param stock      unidades disponibles
	 * @param tipoManga  tipo de manga
	 */
	public Camiseta(String idArticulo, String nombre, double precio, String talla, int stock, String tipoManga) {
		super(idArticulo, nombre, precio, talla, stock, tipoManga);
		this.personalizacionAplicada = null;
	}

	/**
	 * Devuelve las opciones de personalización disponibles para esta camiseta.
	 *
	 * @return lista con las opciones disponibles
	 */
	@Override
	public ArrayList<String> getOpcionesPersonalizacion() {
		ArrayList<String> opciones = new ArrayList<>();
		opciones.add("Estampado");
		opciones.add("Bordado");
		return opciones;
	}

	/**
	 * Aplica una opción de personalización a la camiseta.
	 *
	 * @param opcion la opción a aplicar
	 * @throws IllegalArgumentException si la opción no está entre las disponibles
	 */
	@Override
	public void aplicarPersonalizacion(String opcion) {
		if (getOpcionesPersonalizacion().contains(opcion)) {
			this.personalizacionAplicada = opcion;
		} else {
			throw new IllegalArgumentException("Opción no disponible: " + opcion);
		}
	}

	/**
	 * Devuelve el coste de personalización según la opción aplicada actualmente. Si
	 * no hay personalización aplicada, devuelve 0.0.
	 *
	 * @return coste de personalización en euros
	 */
	@Override
	public double getCostePersonalizacion() {
		if (personalizacionAplicada == null)
			return 0.0;

		switch (personalizacionAplicada) {
		case "Estampado": {
			return 5.0;
		}
		case "Bordado": {
			return 10.0;
		}
		default: {
			return 0.0;
		}
		}
	}

	@Override
	public String getFichaArchivo() {

		return super.getFichaArchivo();
	}

	/**
	 * Devuelve una representación textual de la camiseta, incluyendo la
	 * personalización si tiene una aplicada.
	 *
	 * @return cadena con los datos de la camiseta
	 */
	@Override
	public String toString() {
		return "Camiseta - " + super.toString();
	}
}
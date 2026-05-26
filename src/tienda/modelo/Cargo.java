package tienda.modelo;

/**
 * Representa un pantalón cargo de la tienda artesanal.
 *
 * @author Jaime Martin
 * @version 1.0
 */
public class Cargo extends PrendaInferior {

	/** Número de bolsillos del pantalón cargo. */
	private int numeroBolsillos;

	/**
	 * Crea un nuevo pantalón cargo.
	 *
	 * @param idArticulo      identificador único del artículo
	 * @param nombre          nombre del pantalón
	 * @param precio          precio base en euros
	 * @param talla           talla de la prenda
	 * @param stock           unidades disponibles
	 * @param largo           largo de la prenda
	 * @param numeroBolsillos número de bolsillos del cargo
	 */
	public Cargo(String idArticulo, String nombre, double precio, String talla, int stock, String largo,
			int numeroBolsillos) {
		super(idArticulo, nombre, precio, talla, stock, largo);
		this.numeroBolsillos = numeroBolsillos;
	}

	/** @return el número de bolsillos del cargo */
	public int getNumeroBolsillos() {
		return numeroBolsillos;
	}

	@Override
	public String getFichaArchivo() {
		return super.getFichaArchivo() + Ropa.SEPARADOR + numeroBolsillos;
	}

	/**
	 * Devuelve una representación textual del pantalón cargo.
	 *
	 * @return cadena con los datos del cargo
	 */
	@Override
	public String toString() {
		return "Cargo - " + super.toString() + " | Bolsillos: " + numeroBolsillos;
	}
}
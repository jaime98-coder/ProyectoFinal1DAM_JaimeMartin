package tienda.modelo;

public class Vaquero extends PrendaInferior {
	/**
	 * Constructor de la clase Vaquero
	 * 
	 * @param idArticulo identificador del artículo
	 * @param nombre     nombre del artículo
	 * @param precio     precio del artículo
	 * @param talla      talla del artículo
	 * @param stock      unidades disponibles del artículo
	 * @param largo      largo del artículo
	 */
	public Vaquero(String idArticulo, String nombre, double precio, String talla, int stock, String largo) {
		super(idArticulo, nombre, precio, talla, stock, largo);
	}

	@Override
	public String getFichaArchivo() {
		return super.getFichaArchivo();
	}

	@Override
	public String toString() {
		return "Vaquero - " + super.toString();
	}

}

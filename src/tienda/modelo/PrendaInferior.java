package tienda.modelo;

public abstract class PrendaInferior extends Ropa {
	private String largo;

	/**
	 * Constructor de la clase PrendaInferior, que hereda de Ropa e incluye el
	 * atributo específico "largo".
	 * 
	 * @param idArticulo Identificador único del artículo.
	 * @param nombre     Nombre del artículo.
	 * @param precio     Precio del artículo.
	 * @param talla      Talla del artículo.
	 * @param stock      Cantidad de stock disponible del artículo.
	 * @param largo      Largo específico de la prenda inferior (por ejemplo,
	 *                   "corto", "largo", "capri").
	 */
	public PrendaInferior(String idArticulo, String nombre, double precio, String talla, int stock, String largo) {
		super(idArticulo, nombre, precio, talla, stock);
		this.largo = largo;
	}

	@Override
	public String getFichaArchivo() {
		return super.getFichaArchivo() + Ropa.SEPARADOR + largo;
	}

	@Override
	public String toString() {
		return "PrendaInferior" + " | Id del articulo: " + super.getIdArticulo() + " | Nombre: " + super.getNombre()
				+ " | Precio: " + super.getPrecio() + " | Stock: " + super.getStock() + " | Largo: " + largo;
	}

}

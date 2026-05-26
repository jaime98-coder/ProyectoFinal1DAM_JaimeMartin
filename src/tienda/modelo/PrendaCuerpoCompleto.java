package tienda.modelo;

public abstract class PrendaCuerpoCompleto extends Ropa {
	private String largo;

	public PrendaCuerpoCompleto(String idArticulo, String nombre, double precio, String talla, int stock,
			String largo) {
		super(idArticulo, nombre, precio, talla, stock);
		this.largo = largo;
	}

	@Override
	public String getFichaArchivo() {
		return super.getFichaArchivo() + Ropa.SEPARADOR + largo;
	}

	@Override
	public String toString() {
		return "PrendaCuerpoCompleto" + " | Id del articulo: " + super.getIdArticulo() + " | Nombre: "
				+ super.getNombre() + " | Precio: " + super.getPrecio() + " | Stock: " + super.getStock() + " | Largo: "
				+ largo;
	}

}

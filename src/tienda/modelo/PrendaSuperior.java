package tienda.modelo;

public abstract class PrendaSuperior extends Ropa {
	private String tipoManga;

	public PrendaSuperior(String idArticulo, String nombre, double precio, String talla, int stock, String tipoManga) {
		super(idArticulo, nombre, precio, talla, stock);
		this.tipoManga = tipoManga;
	}

	@Override
	public String getFichaArchivo() {
		// Devuelve: TipoClase;id;nombre;precio;talla;stock;tipoManga
		return super.getFichaArchivo() + Ropa.SEPARADOR + tipoManga;
	}

	@Override
	public String toString() {
		return "PrendaSuperior" + " Id del articulo: " + super.getIdArticulo() + " | Nombre: " + super.getNombre()
				+ " | Precio: " + super.getPrecio() + " | Stock: " + super.getStock() + " | Tipo de Manga: "
				+ tipoManga;
	}

}

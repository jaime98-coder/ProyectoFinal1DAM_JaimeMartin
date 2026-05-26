package tienda.modelo;

import java.util.Objects;

import tienda.excepciones.StockAgotadoException;

public abstract class Ropa {
	private String idArticulo;
	private String nombre;
	private double precio;
	private String talla;
	private int stock;

	// Constante pública para usarla en todo el proyecto
	public static final String SEPARADOR = ";";

	public Ropa(String idArticulo, String nombre, double precio, String talla, int stock) {
		// El identificador del artículo se convierte a mayúsculas para mantener un
		// formato consistente y evitar problemas de comparación debido a diferencias en mayúsculas/minúsculas.
		this.idArticulo = idArticulo.toUpperCase();
		this.nombre = nombre;
		if (precio > 0) {
			this.precio = precio;
		}
		this.talla = talla;
		this.stock = stock;
	}

	public String getIdArticulo() {
		return idArticulo;
	}

	public String getNombre() {
		return nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public String getTalla() {
		return talla;
	}

	public int getStock() {
		return stock;
	}

	/**
	 * Establece el stock del artículo. Solo se permite asignar un valor no negativo
	 * para evitar inconsistencias en la gestión de inventario.
	 * 
	 * @param stock
	 */
	public void setStock(int stock) {
		if (stock >= 0) {
			this.stock = stock;
		}
	}

	/**
	 * Genera una línea de texto plano estructurada para persistir el objeto. La
	 * primera columna siempre será el tipo exacto de prenda (Camiseta, Vestido...).
	 * 
	 * @return Cadena con formato: TipoClase;id;nombre;precio;talla;stock
	 */
	public String getFichaArchivo() {
		return this.getClass().getSimpleName() + SEPARADOR + idArticulo + SEPARADOR + nombre + SEPARADOR + precio
				+ SEPARADOR + talla + SEPARADOR + stock;
	}

	public void verificarDisponibilidad(int cantidad) throws StockAgotadoException {
		if (cantidad > stock) {
			throw new StockAgotadoException("No hay suficiente stock disponible para el artículo: " + nombre);
		}
	}

	public abstract String toString();

	@Override
	public int hashCode() {
		return Objects.hash(idArticulo);
	}

	/**
	 * Dos objetos Ropa son iguales si tienen el mismo idArticulo, ya que se asume
	 * que el idArticulo es único para cada prenda de ropa. Esto permite comparar
	 * dos objetos Ropa basándose en su identificador único, lo que es útil para
	 * operaciones como búsqueda, eliminación o actualización de prendas de ropa en
	 * una colección.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ropa other = (Ropa) obj;
		return Objects.equals(idArticulo, other.idArticulo);
	}

}

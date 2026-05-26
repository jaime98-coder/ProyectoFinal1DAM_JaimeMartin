package tienda.modelo;

/**
 * Representa una sudadera de la tienda artesanal. Implementa
 * EmpaquetablePremium para permitir empaquetado especial de regalo.
 *
 * @author Jaime Martin
 * @version 1.0
 */
public class Sudadera extends PrendaSuperior implements EmpaquetablePremium {

	/** Indica si la sudadera tiene capucha. */
	private boolean tieneCapucha;

	/** Indica si el empaquetado premium está activado. */
	private boolean empaquetadoPremiumActivado;

	/** Mensaje personalizado de la tarjeta de regalo. Null si no tiene. */
	private String mensajeTarjeta;

	/**
	 * Crea una nueva sudadera.
	 *
	 * @param idArticulo   identificador único del artículo
	 * @param nombre       nombre de la sudadera
	 * @param precio       precio base en euros
	 * @param talla        talla de la prenda
	 * @param stock        unidades disponibles
	 * @param tipoManga    tipo de manga
	 * @param tieneCapucha true si la sudadera tiene capucha
	 */
	public Sudadera(String idArticulo, String nombre, double precio, String talla, int stock, String tipoManga,
			boolean tieneCapucha) {
		super(idArticulo, nombre, precio, talla, stock, tipoManga);
		this.tieneCapucha = tieneCapucha;
		this.empaquetadoPremiumActivado = false;
		this.mensajeTarjeta = null;
	}

	/** @return true si la sudadera tiene capucha */
	public boolean isTieneCapucha() {
		return tieneCapucha;
	}

	/**
	 * Activa el empaquetado premium para esta sudadera.
	 */
	@Override
	public void aplicarEmpaquetadoPremium() {
		this.empaquetadoPremiumActivado = true;
	}

	/**
	 * Añade una tarjeta personalizada al empaquetado.
	 *
	 * @param mensaje mensaje a incluir en la tarjeta
	 */
	@Override
	public void anadirTarjetaPersonalizada(String mensaje) {
		this.mensajeTarjeta = mensaje;
	}

	/**
	 * Devuelve el coste del empaquetado premium. Si no está activado, devuelve 0.0.
	 *
	 * @return coste del empaquetado en euros
	 */
	@Override
	public double getCosteEmpaquetadoPremium() {
		if (!empaquetadoPremiumActivado) {
			return 0.0;
		} else {
			// con tarjeta cuesta más que sin tarjeta
			return mensajeTarjeta != null ? 7.0 : 4.0;
		}

	}

	@Override
	public String getFichaArchivo() {
		// Devuelve: TipoClase;id;nombre;precio;talla;stock;tipoManga;tieneCapucha
		return super.getFichaArchivo() + Ropa.SEPARADOR + tieneCapucha;
	}

	/**
	 * Devuelve una representación textual de la sudadera.
	 *
	 * @return cadena con los datos de la sudadera
	 */
	@Override
	public String toString() {
		return "Sudadera - " + super.toString() + " | Capucha: " + (tieneCapucha ? "Sí" : "No");
	}
}
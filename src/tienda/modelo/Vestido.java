package tienda.modelo;

public class Vestido extends PrendaCuerpoCompleto implements CuidadoEspecial, EmpaquetablePremium {
	private String ocasion;
	private boolean empaquetadoPremiumActivado;
	private String mensajeTarjeta;

	/**
	 * Constructor para crear un nuevo Vestido.
	 *
	 * @param idArticulo identificador único del artículo
	 * @param nombre     nombre del vestido
	 * @param precio     precio en euros
	 * @param talla      talla del vestido (S, M, L, etc.)
	 * @param stock      cantidad disponible en stock
	 * @param largo      largo del vestido (corto, midi, largo)
	 * @param ocasion    ocasión para la que es adecuado el vestido (formal, casual,
	 *                   fiesta, etc.)
	 */
	public Vestido(String idArticulo, String nombre, double precio, String talla, int stock, String largo,
			String ocasion) {
		super(idArticulo, nombre, precio, talla, stock, largo);
		this.ocasion = ocasion;
		this.empaquetadoPremiumActivado = false; // por defecto, el empaquetado premium no está activado
	}

	/**
	 * Activa el empaquetado premium para este Vestido.
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

	/**
	 * Devuelve las instrucciones de cuidado para el vestido.
	 *
	 * @return instrucciones de lavado y conservación
	 */
	@Override
	public String getInstruccionesCuidado() {
		return "Lavar a mano con agua fría. No usar secadora. " + "\nPlanchar a baja temperatura. \nOcasión: "
				+ ocasion;
	}

	@Override
	public String getFichaArchivo() {
		return super.getFichaArchivo() + Ropa.SEPARADOR + ocasion;
	}

	@Override
	public String toString() {
		return "Vestido -" + super.toString() + " | Ocasion: " + ocasion + " | empaquetadoPremiumActivado: "
				+ empaquetadoPremiumActivado + " | mensajeTarjeta: " + mensajeTarjeta;
	}

}

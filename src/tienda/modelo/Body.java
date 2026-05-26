package tienda.modelo;

/**
 * Representa un body de la tienda artesanal. Implementa CuidadoEspecial por sus
 * materiales delicados y tipo de cierre.
 *
 * @author Jaime Martin
 * @version 1.0
 */
public class Body extends PrendaSuperior implements CuidadoEspecial {

	/** Tipo de cierre del body (broches, cremallera...). */
	private String tipoCierre;

	/**
	 * Crea un nuevo body.
	 *
	 * @param idArticulo identificador único del artículo
	 * @param nombre     nombre del body
	 * @param precio     precio base en euros
	 * @param talla      talla de la prenda
	 * @param stock      unidades disponibles
	 * @param tipoManga  tipo de manga
	 * @param tipoCierre tipo de cierre del body
	 */
	public Body(String idArticulo, String nombre, double precio, String talla, int stock, String tipoManga,
			String tipoCierre) {
		super(idArticulo, nombre, precio, talla, stock, tipoManga);
		this.tipoCierre = tipoCierre;
	}

	/** @return el tipo de cierre del body */
	public String getTipoCierre() {
		return tipoCierre;
	}

	/**
	 * Devuelve las instrucciones de cuidado para el body.
	 *
	 * @return instrucciones de lavado y conservación
	 */
	@Override
	public String getInstruccionesCuidado() {
		return "Lavar a mano con agua fría. No usar secadora. " + "\nPlanchar a baja temperatura. \nCierre tipo: "
				+ tipoCierre;
	}

	@Override
	public String getFichaArchivo() {
		return super.getFichaArchivo() + Ropa.SEPARADOR + tipoCierre;
	}

	/**
	 * Devuelve una representación textual del body.
	 *
	 * @return cadena con los datos del body
	 */
	@Override
	public String toString() {
		return "Body - " + super.toString() + " | Cierre: " + tipoCierre;
	}
}
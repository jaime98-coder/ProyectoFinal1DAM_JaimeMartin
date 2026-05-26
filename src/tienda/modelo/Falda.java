package tienda.modelo;

/**
 * Clase que representa una falda en la tienda de ropa. Hereda de PrendaInferior
 * e implementa CuidadoEspecial. Contiene información sobre si la falda tiene
 * volantes y proporciona instrucciones de cuidado específicas.
 * 
 * @author Jaime
 * @version 1.0
 */
public class Falda extends PrendaInferior implements CuidadoEspecial {

	/** Indica si la falda tiene volantes o no. */
	private boolean tieneVolantes;

	/**
	 * Constructor para crear una instancia de Falda.
	 * 
	 * @param idArticulo    Identificador único del artículo
	 * @param nombre        Nombre de la falda
	 * @param precio        Precio base en euros
	 * @param talla         Talla de la prenda
	 * @param stock         Unidades disponibles en stock
	 * @param largo         Largo de la falda (corto, medio, largo)
	 * @param tieneVolantes Indica si la falda tiene volantes o no
	 */
	public Falda(String idArticulo, String nombre, double precio, String talla, int stock, String largo,
			boolean tieneVolantes) {
		super(idArticulo, nombre, precio, talla, stock, largo);
		this.tieneVolantes = tieneVolantes;
	}

	/**
	 * Devuelve las instrucciones de cuidado para el body.
	 *
	 * @return instrucciones de lavado y conservación
	 */
	@Override
	public String getInstruccionesCuidado() {
		return "Lavar a mano con agua fría. No usar secadora. " + "\nPlanchar a baja temperatura."
				+ "\nTiene volantes: " + (tieneVolantes ? "Sí" : "No");
	}

	@Override
	public String getFichaArchivo() {
		return super.getFichaArchivo() + Ropa.SEPARADOR + tieneVolantes;
	}

	@Override
	public String toString() {
		return "Falda - " + super.toString() + " | tieneVolantes: " + tieneVolantes;
	}

}

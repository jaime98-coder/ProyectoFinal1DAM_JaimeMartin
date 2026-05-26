package tienda.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Clase genérica propia (tipo T) que actúa como contenedor universal de datos.
 * Cumple con el requisito de uso de genéricos y prepara la arquitectura del
 * proyecto para una futura implementación del patrón DAO.
 * 
 * @param <T> El tipo de objeto (Entidad) que va a gestionar este repositorio.
 * 
 * @author Jaime
 * @version 1.0
 */
public class Repositorio<T> {

	/** Lista interna que almacena genéricamente los elementos de tipo T */
	private List<T> elementos;

	/**
	 * Constructor que inicializa el repositorio con una lista vacía.
	 */
	public Repositorio() {
		this.elementos = new ArrayList<>();
	}

	/**
	 * Añade un nuevo elemento genérico al repositorio. Evita insertar valores nulos
	 * o duplicados exactos. * @param elemento Objeto de tipo T a guardar
	 */
	public void agregar(T elemento) {
		if (elemento != null && !elementos.contains(elemento)) {
			elementos.add(elemento);
		}
	}

	/**
	 * Elimina un elemento del repositorio. * @param elemento Objeto de tipo T a
	 * borrar
	 * 
	 * @return true si se eliminó con éxito, false si no existía
	 */
	public boolean eliminar(T elemento) {
		return elementos.remove(elemento);
	}

	/**
	 * Devuelve todos los elementos almacenados. * @return Una copia de la lista
	 * para proteger la encapsulación
	 */
	public List<T> obtenerTodos() {
		return new ArrayList<>(elementos);
	}

	/**
	 * Método de búsqueda genérica avanzado. Combina Genéricos (tipo T) con la
	 * Stream API. Permite filtrar la colección pasando cualquier condición
	 * (Lambda).
	 * 
	 * @param condicion Regla de filtrado (Interfaz funcional Predicate)
	 * @return Una lista filtrada solo con los elementos que cumplen la condición
	 */
	public List<T> filtrar(Predicate<T> condicion) {
		return elementos.stream().filter(condicion).collect(Collectors.toList());
	}
}
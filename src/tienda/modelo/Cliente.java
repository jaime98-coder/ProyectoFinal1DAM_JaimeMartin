package tienda.modelo;

import java.util.Objects;

public class Cliente {
	private String dniCliente;
	private String nombre;
	private String email;
	private String telefono;

	/**
	 * Constructor para crear un nuevo cliente con sus datos personales.
	 * 
	 * @param dniCliente. el DNI del cliente, que debe ser único
	 * @param nombre.     el nombre completo del cliente
	 * @param email.      la dirección de correo electrónico del cliente
	 * @param telefono.   el número de teléfono del cliente
	 */
	public Cliente(String dniCliente, String nombre, String email, String telefono) {
		// El DNI se convierte a mayúsculas para mantener un formato consistente y
		// evitar problemas de comparación debido a diferencias en
		// mayúsculas/minúsculas.
		this.dniCliente = dniCliente.toUpperCase();
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
	}

	/**
	 * Constructor de copia para crear un nuevo cliente a partir de otro existente.
	 * 
	 * @param otro. el cliente del que se copiarán los datos
	 */
	public Cliente(Cliente otro) {
		this.dniCliente = otro.dniCliente;
		this.nombre = otro.nombre;
		this.email = otro.email;
		this.telefono = otro.telefono;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dniCliente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(dniCliente, other.dniCliente);
	}

	/**
	 * Genera una línea estructurada para guardar el cliente en clientes.csv.
	 * Formato: dniCliente;nombre;email;telefono
	 */
	public String getFichaArchivo() {
		return dniCliente + Ropa.SEPARADOR + nombre + Ropa.SEPARADOR + email + Ropa.SEPARADOR + telefono;
	}

	public String getDniCliente() {
		return dniCliente;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return "Cliente -  DNI Cliente: " + dniCliente + " | Nombre: " + nombre + " |  Email: " + email
				+ " |  Teléfono: " + telefono;
	}

}

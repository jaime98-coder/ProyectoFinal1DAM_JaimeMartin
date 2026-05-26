package tienda.modelo;
/**
 * Enum para representar el estado de un pedido.
 * PENDIENTE: El pedido ha sido realizado pero no se ha completado.
 * COMPLETADO: El pedido ha sido completado y entregado al cliente.
 * CANCELADO: El pedido ha sido cancelado y no se procesará.
 */
public enum EstadoPedido {
	PENDIENTE, COMPLETADO, CANCELADO;
}

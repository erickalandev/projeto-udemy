package io.github.erickalandev.exception;

public class PedidoNotFoundException extends RuntimeException{
	
	public PedidoNotFoundException() {
		super("Pedido nao encontrado");
	}
}

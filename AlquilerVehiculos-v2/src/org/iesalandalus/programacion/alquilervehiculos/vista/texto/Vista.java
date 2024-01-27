package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;

public abstract class Vista {

	protected Controlador controlador;
	
	public void setControlador(Controlador controlador){
		if(controlador == null) {
			throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
		}
		
		this.controlador = controlador;
	}
	
	public abstract void comenzar();
	
	public abstract void terminar();
}

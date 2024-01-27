package org.iesalandalus.programacion.alquilervehiculos.modelo;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.FuenteDatosFicheros;

public enum FactoriaFuenteDatos {

	FICHEROS;
	
	public static IFuenteDatos crear() {
		return new FuenteDatosFicheros();
	}
}

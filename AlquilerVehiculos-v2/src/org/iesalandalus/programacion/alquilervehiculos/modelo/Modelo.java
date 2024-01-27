package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.FuenteDatosFicheros;

public abstract class Modelo {

	protected IClientes clientes;
	protected IVehiculos vehiculos;
	protected IAlquileres alquileres;
	protected IFuenteDatos fuenteDatos;

	public Modelo (IFuenteDatos factoriaFuenteDatos) {
		//Crea el objeto fuenteDatos
		if (factoriaFuenteDatos.equals(FactoriaFuenteDatos.FICHEROS)) {
			fuenteDatos = new FuenteDatosFicheros();
		}
	}

	protected IClientes getClientes() {
		return clientes;
	}
	
	protected IVehiculos getVehiculos() {
		return vehiculos;
	}
	
	protected IAlquileres getAlquileres() {
		return alquileres;
	}
	
	protected void setFuenteDatos(IFuenteDatos fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}
	
	public void comenzar() {
		clientes = fuenteDatos.crearClientes();
		vehiculos = fuenteDatos.crearVehiculos();
		alquileres = fuenteDatos.crearAlquileres();
	}
	
	public void terminar() {
		System.out.println("El modelo ha finalizado.");
	}
	
	public abstract void insertar(Cliente cliente) throws OperationNotSupportedException;
	
	public abstract void insertar(Vehiculo vehiculo) throws OperationNotSupportedException;
	
	public abstract void insertar(Alquiler alquiler) throws OperationNotSupportedException;
	
	public abstract Cliente buscar(Cliente cliente) throws OperationNotSupportedException;
	
	public abstract Vehiculo buscar(Vehiculo vehiculo) throws OperationNotSupportedException;
	
	public abstract Alquiler buscar(Alquiler alquiler) throws OperationNotSupportedException;
	
	public abstract void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException;
	
	public abstract void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException;
	
	public abstract void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException;
	
	public abstract void borrar(Cliente cliente) throws OperationNotSupportedException;
	
	public abstract void borrar(Vehiculo vehiculo) throws OperationNotSupportedException;
	
	public abstract void borrar(Alquiler alquiler) throws OperationNotSupportedException;
	
	public abstract List<Cliente> getListaClientes();
	
	public abstract List<Vehiculo> getListaVehiculos();
	
	public abstract List<Alquiler> getListaAlquileres();
	
	public abstract List<Alquiler> getListaAlquileres(Cliente cliente);
	
	public abstract List<Alquiler> getListaAlquileres(Vehiculo vehiculo);
}

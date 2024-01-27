package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;

public class ModeloCascada extends Modelo{
	
	public ModeloCascada(IFuenteDatos fuenteDatos) {
		super(fuenteDatos);
	}
	
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		clientes.insertar(cliente);
	}
	
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		vehiculos.insertar(vehiculo);
	}
	
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if(alquiler == null) {
			throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
		}
		
		Cliente clienteBuscado = clientes.buscar(alquiler.getCliente());
		Vehiculo vehiculoBuscado = vehiculos.buscar(alquiler.getVehiculo());
		
		if(clienteBuscado == null) {
			throw new OperationNotSupportedException("ERROR: No existe el cliente del alquiler.");
		}
		
		if(vehiculoBuscado == null) {
			throw new OperationNotSupportedException("ERROR: No existe el vehículo del alquiler.");
		}
		
		alquileres.insertar(new Alquiler(clienteBuscado, vehiculoBuscado, alquiler.getFechaAlquiler()));
	}
	
	public Cliente buscar(Cliente cliente) throws OperationNotSupportedException {
		return clientes.buscar(cliente);
	}
	
	public Vehiculo buscar(Vehiculo vehiculo) throws OperationNotSupportedException {
		Vehiculo vehiculoBuscado = null;
		Vehiculo vehiculoTipo = vehiculos.buscar(vehiculo);
		
		if(vehiculoTipo instanceof Turismo) {
			vehiculoBuscado = (Vehiculo) new Turismo((Turismo) vehiculoTipo);
		}else if(vehiculoTipo instanceof Autobus) {
			vehiculoBuscado = (Vehiculo) new Autobus((Autobus) vehiculoTipo);
		}else if(vehiculoTipo instanceof Furgoneta) {
			vehiculoBuscado = (Vehiculo) new Furgoneta((Furgoneta) vehiculoTipo);
		}
		
		return vehiculoBuscado;
	}
	
	public Alquiler buscar(Alquiler alquiler) throws OperationNotSupportedException {
		return alquileres.buscar(alquiler);
	}
	
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		clientes.modificar(cliente, nombre, telefono);
	}
	
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if(buscar(cliente) == null)
			throw new OperationNotSupportedException("ERROR: No existe el cliente a devolver.");
		
		alquileres.devolver(cliente, fechaDevolucion);
	}
	
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if(buscar(vehiculo) == null)
			throw new OperationNotSupportedException("ERROR: No existe el vehiculo a devolver.");
		
		alquileres.devolver(vehiculo, fechaDevolucion);
	}
	
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		for(Alquiler alquiler : alquileres.get(cliente)) {
			alquileres.borrar(alquiler);
		}
		
		clientes.borrar(cliente);
	}
	
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		for(Alquiler alquiler : alquileres.get(vehiculo)) {
			alquileres.borrar(alquiler);
		}
		
		vehiculos.borrar(vehiculo);
	}
	
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		alquileres.borrar(alquiler);
	}
	
	public List<Cliente> getListaClientes(){
		List<Cliente> listaCliente = new ArrayList<>();
		
		for(Cliente cliente : clientes.get()) {
			listaCliente.add(cliente);
		}
		
		return listaCliente;
	}
	
	public List<Vehiculo> getListaVehiculos(){
		List<Vehiculo> listaVehiculo = new ArrayList<>();
		
		for(Vehiculo vehiculo : vehiculos.get()) {
			listaVehiculo.add(vehiculo);
		}
		
		return listaVehiculo;
	}
	
	public List<Alquiler> getListaAlquileres(){
		List<Alquiler> listaAlquiler = new ArrayList<>();
		
		for(Alquiler alquiler : alquileres.get()) {
			listaAlquiler.add(alquiler);
		}
		
		return listaAlquiler;
	}
	
	public List<Alquiler> getListaAlquileres(Cliente cliente){
		List<Alquiler> listaAlquilerCliente = new ArrayList<>();
		
		for(Alquiler alquiler : alquileres.get(cliente)) {
			listaAlquilerCliente.add(alquiler);
		}
		
		return listaAlquilerCliente;
	}
	
	public List<Alquiler> getListaAlquileres(Vehiculo vehiculo){
		List<Alquiler> listaAlquilerVehiculo = new ArrayList<>();
		
		for(Alquiler alquiler : alquileres.get(vehiculo)) {
			listaAlquilerVehiculo.add(alquiler);
		}
		
		return listaAlquilerVehiculo;
	}
}

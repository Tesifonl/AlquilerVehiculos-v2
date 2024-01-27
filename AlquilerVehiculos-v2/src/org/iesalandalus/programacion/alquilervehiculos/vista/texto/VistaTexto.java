package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public class VistaTexto extends Vista{
	
	public VistaTexto() {
		super();
	}
	
	public void comenzar() {
		Consola.mostrarMenu();
		ejecutar(Consola.elegirAccion());
	}
	
	public void terminar() {
		System.out.println("La vista ha terminado.");
	}
	
	private void ejecutar(Accion opcion) {
		switch(opcion.ordinal()) {
		case 0:
			terminar();
			break;
			
		case 1:
			insertarCliente();
			comenzar();
			break;
			
		case 2:
			insertarVehiculo();
			comenzar();
			break;
			
		case 3:
			insertarAlquiler();
			comenzar();
			break;
			
		case 4:
			buscarCliente();
			comenzar();
			break;
			
		case 5:
			buscarVehiculo();
			comenzar();
			break;
			
		case 6:
			buscarAlquiler();
			comenzar();
			break;
		case 7:
			
			modificarCliente();
			comenzar();
			break;
			
		case 8:
			devolverAlquilerCliente();
			comenzar();
			break;
			
		case 9:
			devolverAlquilerVehiculo();
			comenzar();
			break;
			
		case 10:
			borrarCliente();
			comenzar();
			break;
			
		case 111:
			borrarVehiculo();
			comenzar();
			break;
			
		case 12:
			borrarAlquiler();
			comenzar();
			break;
			
		case 13:
			listarClientes();
			comenzar();
			break;
			
		case 14:
			listarVehiculos();
			comenzar();
			break;
			
		case 15:
			listarAlquileres();
			comenzar();
			break;
			
		case 16:
			listarAlquileresCliente();
			comenzar();
			break;
			
		case 17:
			listarAlquileresVehiculo();
			comenzar();
			break;
			
		case 18:
			mostrarEstadisticasMensualesTipoVehiculo();
			comenzar();
			break;
		}
	}
	
	public void insertarCliente() {
		Consola.mostrarCabecera(Accion.INSERTAR_CLIENTE.toString());
		
		try {
			controlador.insertar(Consola.leerCliente());
			System.out.println("EXITO: Se ha introducido exitosamente el cliente.");
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}
	
	public void insertarVehiculo() {
		Consola.mostrarCabecera(Accion.INSERTAR_VEHICULO.toString());
		
		try {
			controlador.insertar(Consola.leerVehiculo());
			System.out.println("EXITO: Se ha introducido exitosamente el vehículo.");
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void insertarAlquiler() {
		Consola.mostrarCabecera(Accion.INSERTAR_ALQUILER.toString());
		
		try {
			controlador.insertar(Consola.leerAlquiler());
			System.out.println("EXITO: Se ha introducido exitosamente el alquiler.");
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void buscarCliente() {
		Cliente cliente;
		Consola.mostrarCabecera(Accion.BUSCAR_CLIENTE.toString());
		
		try {
			cliente = controlador.buscar(Consola.leerClienteDni());
			String mensaje = (cliente != null) ? cliente.toString() : "ERROR: No existe el cliente introducido.";
			System.out.println(mensaje);
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException| OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void buscarVehiculo() {
		Vehiculo vehiculo;
		Consola.mostrarCabecera(Accion.BUSCAR_VEHICULO.toString());
		
		try {
			vehiculo = controlador.buscar(Consola.leerVehiculoMatricula());
			String mensaje = (vehiculo != null) ? vehiculo.toString() : "ERROR: No existe el vehículo introducido.";
			System.out.println(mensaje);
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void buscarAlquiler() {
		Alquiler alquiler;
		Consola.mostrarCabecera(Accion.BUSCAR_ALQUILER.toString());
		
		try {
			alquiler = controlador.buscar(Consola.leerAlquiler());
			String mensaje = (alquiler != null) ? alquiler.toString() : "ERROR: No existe el alquiler introducido.";
			System.out.println(mensaje);
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void modificarCliente() {
		Consola.mostrarCabecera(Accion.MODIFICAR_CLIENTE.toString());
		
		try {
			controlador.modificar(Consola.leerClienteDni(), Consola.leerNombre(), Consola.leerTelefono());
			System.out.println("EXITO: Se ha modificado el cliente con exito.");
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void devolverAlquilerCliente() {
		Consola.mostrarCabecera(Accion.DEVOLVER_ALQUILER_CLIENTE.toString());
		
		try {
			controlador.devolver(Consola.leerCliente(), Consola.leerFechaDevolucion());
			System.out.println("EXITO: Se ha devuelto el aquiler exitosamente.");
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}
	
	public void devolverAlquilerVehiculo() {
		Consola.mostrarCabecera(Accion.DEVOLVER_ALQUILER_VEHICULO.toString());
		
		try {
			controlador.devolver(Consola.leerVehiculo(), Consola.leerFechaDevolucion());
			System.out.println("EXITO: Se ha devuelto el aquiler exitosamente.");
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void borrarCliente() {
		Consola.mostrarCabecera(Accion.BORRAR_CLIENTE.toString());
		
		try {
			controlador.borrar(Consola.leerClienteDni());
			System.out.println("EXITO: Se ha borrado el cliente con exito.");
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void borrarVehiculo() {
		Consola.mostrarCabecera(Accion.BORRAR_VEHICULO.toString());
		
		try {
			controlador.borrar(Consola.leerVehiculoMatricula());
			System.out.println("EXITO: Se ha borrado el vehículo con exito.");
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void borrarAlquiler() {
		Consola.mostrarCabecera(Accion.BORRAR_ALQUILER.toString());
		
		try {
			controlador.borrar(Consola.leerAlquiler());
			System.out.println("EXITO: Se ha borrado el alquiler con exito.");
			System.out.println("");
		} catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void listarClientes() {
		Consola.mostrarCabecera(Accion.LISTAR_CLIENTES.toString());
		
		try {
			List<Cliente> clientes = controlador.getListaClientes();
			clientes.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
			
			if(clientes.size() == 0) {
				System.out.println("ERROR: No hay ningún cliente introducido.");
				System.out.println("");
			}else {
				for(Cliente cliente : clientes) {
					System.out.println(cliente);
					System.out.println("");
				}
			}
		} catch(NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void listarVehiculos() {
		Consola.mostrarCabecera(Accion.LISTAR_VEHICULOS.toString());
		
		try {
			List<Vehiculo> vehiculos = controlador.getListaVehiculos();
			vehiculos.sort(Comparator.comparing(Vehiculo::getMarca).thenComparing(Vehiculo::getModelo)
					.thenComparing(Vehiculo::getMatricula));
			
			if(vehiculos.size() == 0) {
				System.out.println("ERROR: No hay ningún vehículo introducido.");
				System.out.println("");
			}else {
				for(Vehiculo vehiculo : vehiculos) {
					System.out.println(vehiculo);
					System.out.println("");
				}
			}
		} catch(NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void listarAlquileres() {
		Consola.mostrarCabecera(Accion.LISTAR_ALQUILERES.toString());
		
		try {
			List <Alquiler> alquileres = controlador.getListaAlquileres();
			Comparator<Cliente> compararCliente = Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni);
			alquileres.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente ,compararCliente));
			
			if(alquileres.size() == 0) {
				System.out.println("ERROR: No hay ningún alquiler introducido.");
				System.out.println("");
			}else {
				for(Alquiler alquiler : alquileres) {
					System.out.println(alquiler);
					System.out.println("");
				}
			}
		} catch(NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void listarAlquileresCliente() {
		Consola.mostrarCabecera(Accion.LISTAR_ALQUILERES_CLIENTE.toString());
		
		try {
			List <Alquiler> alquileresCliente = controlador.getListaAlquileres(Consola.leerClienteDni());
			Comparator<Cliente> compararCliente = Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni);
			alquileresCliente.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente ,compararCliente));
			
			if(alquileresCliente.size() == 0) {
				System.out.println("ERROR: No hay ningún alquiler con el cliente introducido.");
				System.out.println("");
			}else {
				for(Alquiler alquilerClientes : alquileresCliente) {
					System.out.println(alquilerClientes);
					System.out.println("");
				}
			}
		} catch(NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	public void listarAlquileresVehiculo() {
		Consola.mostrarCabecera(Accion.LISTAR_ALQUILERES_VEHICULO.toString());
		
		try {
			List<Alquiler> alquileresVehiculo = controlador.getListaAlquileres(Consola.leerVehiculoMatricula());
			Comparator<Cliente> compararCliente = Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni);
			alquileresVehiculo.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente ,compararCliente));
			
			if(alquileresVehiculo.size() == 0) {
				System.out.println("ERROR: No hay ningún alquiler con el vehículo introducido.");
			}else {
				for(Alquiler alquilerTurismos : alquileresVehiculo) {
					System.out.println(alquilerTurismos);
					System.out.println("");
				}
			}
		} catch(NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}
	
	public void mostrarEstadisticasMensualesTipoVehiculo() {
		
		Map<TipoVehiculo, Integer> estadisticas = inicializarEstadisticas();
		
		//Crear un ojbeto Entry con los tipos de clave y valor del diccionario
		//para recorrer el diccionario siempre se utiliza el tipo Entry
		for (Entry<TipoVehiculo, Integer> elemento : estadisticas.entrySet()) {
			
			//Imprimimos los datos para cada entry (elemento)
			System.out.println("Tipo vehiculo: " + elemento.getKey() + ": Numero veces: " + elemento.getValue() );
		}
	}
	
	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		
		//Pedir al usuario que introduzca el mes
		LocalDate mes = Consola.leerMes();
		
		//Creamos el objeto Map para devolver. La inicialización del objeto se realiza con
		//la clase HashMap y no con la interfaz Map, ya que la interfaz no tiene constructor
		Map<TipoVehiculo, Integer> mapEstadisticas = new HashMap<TipoVehiculo, Integer>();
		
		//Recojo todos los alquileres de la aplicación
		List<Alquiler> alquileres = controlador.getListaAlquileres();
		
		//Recorrer la lista de alquileres
		for (Alquiler alquiler: alquileres) {
			
			//Si el mes del aquiler coincide con el mes introducido por el usuario
			if (alquiler.getFechaAlquiler().getMonthValue() == mes.getMonthValue()) {
			
				//Si el vehiculo del alquiler es un Turismo
				if (alquiler.getVehiculo() instanceof Turismo) {
					//Si no existe en el map la clave Turismo
					if (!mapEstadisticas.containsKey(TipoVehiculo.TURISMO)) {
						
						//Añadimos el tipo de vehiculo y como valor 1 (esta una vez)
						mapEstadisticas.put(TipoVehiculo.TURISMO, 1);
					}
					else {
						//Guardo el numero de veces que está el tipo vehiculo en el map
						//la funcion get devuelve el valor que tiene el map para la clave que
						//se le indica en el parámetro
					    Integer veces = mapEstadisticas.get(TipoVehiculo.TURISMO);
					    //Guardo el numero de veces como valor de la clave Turismo
						mapEstadisticas.put(TipoVehiculo.TURISMO, veces++);
					}
				}
				//Si el vehiculo del alquiler es una Furgoneta
				else if (alquiler.getVehiculo() instanceof Furgoneta) {
					//Si no existe en el map la clave Furgoneta
					if (!mapEstadisticas.containsKey(TipoVehiculo.FURGONETA)) {
						
						//Añadimos el tipo de vehiculo y como valor 1 (esta una vez)
						mapEstadisticas.put(TipoVehiculo.FURGONETA, 1);
					}
					else {
						//Guardo el numero de veces que está el tipo vehiculo en el map
						//la funcion get devuelve el valor que tiene el map para la clave que
						//se le indica en el parámetro
					    Integer veces = mapEstadisticas.get(TipoVehiculo.FURGONETA);
					    //Guardo el numero de veces como valor de la clave Turismo
						mapEstadisticas.put(TipoVehiculo.FURGONETA, veces++);
					}
				}
				else if (alquiler.getVehiculo() instanceof Autobus) {
					//Si no existe en el map la clave Autobus
					if (!mapEstadisticas.containsKey(TipoVehiculo.AUTOBUS)) {
						
						//Añadimos el tipo de vehiculo y como valor 1 (esta una vez)
						mapEstadisticas.put(TipoVehiculo.AUTOBUS, 1);
					}
					else {
						//Guardo el numero de veces que está el tipo vehiculo en el map
						//la funcion get devuelve el valor que tiene el map para la clave que
						//se le indica en el parámetro
					    Integer veces = mapEstadisticas.get(TipoVehiculo.AUTOBUS);
					    //Guardo el numero de veces como valor de la clave Turismo
						mapEstadisticas.put(TipoVehiculo.AUTOBUS, veces++);
					}
				}
			}
		}
		
		return mapEstadisticas;
		
	}
}

package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {

	//Atributos XML
	private static final String RUTA_FICHERO = "datos/alquileres.xml";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String RAIZ = "Alquileres";
	private static final String ALQUILER = "Alquiler";
	private static final String DNI_CLIENTE = "Dni";
	private static final String MATRICULA_VEHICULO = "Matricula";
	private static final String FECHA_ALQUILER = "FechaAlquiler";
	private static final String FECHA_DEVOLUCION = "FechaDevolucion";
	private static final String FORMATO = "Formato";
	private static final String TIPO_DATO = "TipoDato";
	
	//Patron Singleton: Solo puede haber una instancia, se indica del mismo tipo que
	//la propia clase y se indica como static para que siempre
	//sea la misma
	private static Alquileres alquileres;
	List<Alquiler> coleccionAlquileres;

	public Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	//Creamos el metodo con visibilidad de paquete, sin indicar modificador
	//de acceso.
	static Alquileres getInstancia() {
		//Si la instancia no está creada, la creamos
		if (alquileres == null) {
			alquileres = new Alquileres();
		}
			
		return alquileres;
	}
	
	@Override
	public void comenzar() {
		leerXML();
	}
	
	private void leerXML() {
		try {
			//Crear el objeto Document con la funcion de utilidades xml que lee un fichero
			//xml y lo guarda como objeto Document Java
			Document document = UtilidadesXML.xmlToDom(RUTA_FICHERO);
			
			//Guardamos en una lista de nodos xml aquellos que tienen como etiqueta Alquiler
			NodeList listaAlquileresXML = document.getElementsByTagName(ALQUILER);
			
			//Recorremos la lista de nodos que hemos guardado del xml
			for (int i = 0; i < listaAlquileresXML.getLength(); i++) {
				//Recoger los datos del xml
				Element elemento = (Element) listaAlquileresXML.item(i);
				//Crear objeto tipo Alquiler
				Alquiler alquiler = elementToAlquiler(elemento);
				insertar(alquiler);
			}
		}
		catch (OperationNotSupportedException exop) {
			System.out.println(exop.getMessage());
		}
		catch (Exception ex) {
			System.out.println("Error al leer el fichero XML alquileres.xml");
		}
	}
	
	private Alquiler elementToAlquiler(Element elemento) throws OperationNotSupportedException {
		Alquiler alquiler = null;
		
		//Recuperamos los atributos dni y matricula que tiene la etiqueta Alquiler
		String dni = elemento.getAttribute(DNI_CLIENTE);
		String matricula = elemento.getAttribute(MATRICULA_VEHICULO);
		
		String fechaAlquiler = elemento.getElementsByTagName(FECHA_ALQUILER).item(0).getTextContent();
		String fechaDevolucion = elemento.getElementsByTagName(FECHA_DEVOLUCION).item(0).getTextContent();

		//Creamos objetos para buscar el cliente y el vehiculo a partir del dni y de la matricula del
		//atributo del fichero xml
		Cliente cliente = null;
		Vehiculo vehiculo = null;
		
		//Buscamos el cliente en la lista 
		for (int i = 0; i < Clientes.getInstancia().get().size(); i++) {
			//Si el dni del cliente de la lista es igual al dni del atributo leido del xml
			if (Clientes.getInstancia().get().get(i).getDni().equals(dni)) {
				cliente = Clientes.getInstancia().get().get(i);
			}
		}
		
		//Buscamos el vehiculo en la lista
		for (int i = 0; i < Vehiculos.getInstancia().get().size(); i++) {
			//Si la matricula del vehiculo de la lista es igual a la matricula del atributo leido del xml
			if (Vehiculos.getInstancia().get().get(i).getMatricula().equals(matricula)) {
				vehiculo = Vehiculos.getInstancia().get().get(i);
			}
		}

		//Si el cliente es null => al buscar el cliente no lo ha encontrado
		if (cliente == null) {
			throw new OperationNotSupportedException("ERROR: No existe un cliente no ese DNI.");
		}
		//Si el vehiculo es null => al buscar el vehiculo no lo ha encontrado
		else if (vehiculo == null) {
			throw new OperationNotSupportedException("ERROR: No existe un vehiculo no esa matricula.");
		}
		else {
			//Crear objeto tipo cliente
			alquiler = new Alquiler(cliente, vehiculo, LocalDate.parse(fechaAlquiler));
			
			//Si la fecha de devolucion viene con datos
			if (!fechaDevolucion.equals("")) {
				//Llamar a la funcion devolver para que asigne la fecha de devolución al objeto alquiler
				alquiler.devolver(LocalDate.parse(fechaDevolucion));
			}
		}
				
		return alquiler;
	}

	@Override
	public void terminar() {
		escribirXML();
		
	}
	
	private void escribirXML() {
		try {
			Document document = UtilidadesXML.crearDomVacio(RAIZ);
			
			//Recorrer la lista de alquileres
			for (int i = 0; i < coleccionAlquileres.size(); i++) {
				Element element = alquilerToElement(document, coleccionAlquileres.get(i));
			}
			
			UtilidadesXML.domToXML(document, RUTA_FICHERO);
		}
		catch (Exception ex) {
			System.out.println("Error al crear el fichero XML");
		}
	}

	private Element alquilerToElement(Document dom, Alquiler alquiler) {
		Element elementAlquiler = dom.createElement(ALQUILER);
		
		//Añadir atributo dni a la etiqueta Alquiler
		elementAlquiler.appendChild(dom.createAttribute(DNI_CLIENTE)).setNodeValue(alquiler.getCliente().getDni());
		
		//Añadir atributo dni a la etiqueta Alquiler
		elementAlquiler.appendChild(dom.createAttribute(MATRICULA_VEHICULO)).setNodeValue(alquiler.getVehiculo().getMatricula());	
		
		//Creo un elemento para añadir la fecha de alquiler
		Element elementFechaAlquiler = dom.createElement(FECHA_ALQUILER);

		//A la etiqueta nombre le tengo que añadir un hijo que es un nodo de texto
		//con la fecha de Alquiler en formato dd/MM/yyyy
		elementFechaAlquiler.appendChild(dom.createTextNode(alquiler.getFechaAlquiler().format(FORMATO_FECHA)));
		
		//Añadimos atributo a la etiqueta Fecha de alquiler con el formato
		elementFechaAlquiler.appendChild(dom.createAttribute(FORMATO)).setNodeValue(FORMATO_FECHA.toString());
				
		//Añadimos atributo a la etiqueta Fecha de alquiler con el tipo de dato
		//El tipo de dato será el nombre de la clase que tiene la variable
		elementFechaAlquiler.appendChild(dom.createAttribute(TIPO_DATO)).setNodeValue(alquiler.getFechaAlquiler().getClass().getName());
	
		//Creo un elemento para añadir la fecha de devolución
		Element elementFechaDevolucion = dom.createElement(FECHA_DEVOLUCION);
		
		//Añadimos atributo a la etiqueta Fecha de alquiler con el formato
		elementFechaDevolucion.appendChild(dom.createAttribute(FORMATO)).setNodeValue(FORMATO_FECHA.toString());
						
		//Añadimos atributo a la etiqueta Fecha de alquiler con el tipo de dato
		//El tipo de dato será el nombre de la clase que tiene la variable
		elementFechaDevolucion.appendChild(dom.createAttribute(TIPO_DATO)).setNodeValue(alquiler.getFechaDevolucion().getClass().getName());

		//Si la fecha de devolucion no es null, creamos hijo
		//si es null no entra en el if y no creará hijo => quedará etiqueta creada pero vacía
		if (alquiler.getFechaDevolucion() != null) {
			//A la etiqueta nombre le tengo que añadir un hijo que es un nodo de texto
			//con el nombre del cliente
			elementFechaDevolucion.appendChild(dom.createTextNode(alquiler.getFechaDevolucion().format(FORMATO_FECHA)));
		}
		
		return elementAlquiler;
	}
	
	@Override
	public List<Alquiler> get() {
		List <Alquiler> copiaAlquileres = new ArrayList<>(coleccionAlquileres);
		
		return copiaAlquileres;
	}
	
	@Override
	public List<Alquiler> get(Cliente cliente) {
		ArrayList<Alquiler> alquilerCliente = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				alquilerCliente.add(alquiler);
			}
		}
		
		return alquilerCliente;
	}
	
	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {
		ArrayList<Alquiler> alquilerVehiculo = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(vehiculo)) {
				alquilerVehiculo.add(alquiler);
			}
		}
		
		return alquilerVehiculo;
	}
	
	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		
		coleccionAlquileres.add(alquiler);
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler) throws OperationNotSupportedException {
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getFechaDevolucion() == null) {
				if (alquiler.getCliente().equals(cliente)) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
				}
				
				if (alquiler.getVehiculo().equals(vehiculo)) {
					throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado.");
				}
				
			} else {
				if ((alquiler.getCliente().equals(cliente)) && (alquiler.getFechaDevolucion().isAfter(fechaAlquiler) ||
						alquiler.getFechaDevolucion().isEqual(fechaAlquiler))) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
				}
				
				if ((alquiler.getVehiculo().equals(vehiculo)) && (alquiler.getFechaDevolucion().isAfter(fechaAlquiler) ||
						alquiler.getFechaDevolucion().isEqual(fechaAlquiler))) {
					throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");
				}
			}
		}
	}
	
	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un cliente nulo.");
		}

		//Busco el alquiler del cliente sin devolver
		Alquiler alquiler = getAlquilerAbierto(cliente);
		
		//Si tiene alquiler sin devolver
		if (alquiler != null) {
			alquiler.devolver(fechaDevolucion);
		}
	}
	
	private Alquiler getAlquilerAbierto(Cliente cliente) {
		//Recupero la lista de alquileres de ese cliente
		//No buscaré después en toda la lista, sino en la del cliente
		List<Alquiler> listaAlquileresCliente = get(cliente);
		
		//Buscar el alquiler que no se ha devuelto
		for (Alquiler alquiler : listaAlquileresCliente) {
			if (alquiler.getFechaDevolucion() == null) {
				return alquiler;
			}
		}
		
		//Si no tiene alquileres a devolver
		return null;
	}
	
	
	
	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
		}
		
		Alquiler alquiler = getAlquilerAbierto(vehiculo);
		
		if (alquiler != null) {
			alquiler.devolver(fechaDevolucion);
		}
	}
	
	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		//Recupero la lista de alquileres de ese cliente
		//No buscaré después en toda la lista, sino en la del cliente
		List<Alquiler> listaAlquileresVehiculo = get(vehiculo);
		
		//Buscar el alquiler que no se ha devuelto
		for (Alquiler alquiler : listaAlquileresVehiculo) {
			if (alquiler.getFechaDevolucion() == null) {
				return alquiler;
			}
		}
		
		//Si no tiene alquileres a devolver
		return null;
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		Alquiler alquilerEncontrado;
		
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		
		int indice = coleccionAlquileres.indexOf(alquiler);
		if(coleccionAlquileres.contains(alquiler)) {
			alquilerEncontrado = coleccionAlquileres.get(indice);
		}else {
			alquilerEncontrado = null;
		}
		
		return alquilerEncontrado;
	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}

		if (buscar(alquiler) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		
		coleccionAlquileres.remove(alquiler);
	}

}

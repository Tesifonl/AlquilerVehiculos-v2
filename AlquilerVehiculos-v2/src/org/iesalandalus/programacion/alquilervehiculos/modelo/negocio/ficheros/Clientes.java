package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXML;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Clientes implements IClientes {

	//Atributos para las etiquetas
	private static final String RUTA_FICHERO = "datos/clientes.xml";
	private static final String RAIZ = "Clientes";
	private static final String CLIENTE = "Cliente";
	private static final String NOMBRE = "Nombre";
	private static final String DNI = "Dni";
	private static final String TELEFONO = "Telefono";
	private static final String TIPO_DATO = "TipoDato";
	
	
	//Patron Singleton: Solo puede haber una instancia, se indica del mismo tipo que
	//la propia clase y se indica como static para que siempre
	//sea la misma
	private static Clientes clientes;
	private ArrayList<Cliente> coleccionClientes;

	Clientes() {
		coleccionClientes = new ArrayList<>();
	}

	//Creamos el metodo con visibilidad de paquete, sin indicar modificador
	//de acceso.
	static Clientes getInstancia() {
		//Si la instancia no está creada, la creamos
		if (clientes == null) {
			clientes = new Clientes();
		}
		
		return clientes;
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
			
			//Guardamos en una lista de nodos xml aquellos que tienen como etiqueta Cliente
			NodeList listaClientesXML = document.getElementsByTagName(CLIENTE);
			
			//Recorremos la lista de nodos que hemos guardado del xml
			for (int i = 0; i < listaClientesXML.getLength(); i++) {
				//Recoger los datos del xml
				Element elemento = (Element) listaClientesXML.item(i);
				//Crear objeto tipo cliente
				Cliente cliente = elementToCliente(elemento);
				insertar(cliente);
			}
			
		}
		catch (Exception ex) {
			System.out.println("Error al leer el fichero XML clientes.xml");
		}
	}
	
	private Cliente elementToCliente(Element elemento) {
		String dni = elemento.getAttribute(DNI);
		String nombre = elemento.getElementsByTagName(NOMBRE).item(0).getTextContent();
		String telefono = elemento.getElementsByTagName(TELEFONO).item(0).getTextContent();
		
		//Crear objeto tipo cliente
		Cliente cliente = new Cliente(nombre, dni, telefono);
		return cliente;
	}

	@Override
	public void terminar() {
		escribirXML();
		
	}
	
	private void escribirXML() {
		try {
			Document document = UtilidadesXML.crearDomVacio(RAIZ);
			
			//Recorrer la lista de clientes
			for (int i = 0; i < coleccionClientes.size(); i++) {
				Element element = clienteToElement(document, coleccionClientes.get(i));
			}
			
			UtilidadesXML.domToXML(document, RUTA_FICHERO);
		}
		catch (Exception ex) {
			System.out.println("Error al crear el fichero XML");
		}
	}
	
	private Element clienteToElement(Document dom, Cliente cliente) {
		
		Element elementCliente = dom.createElement(CLIENTE);
		
		//Al elemento principal Cliente le añado un objeto Atributo (Attr) con el valor dni
		//ya que en el fichero xml el dni está añadido como atributo y no como valor de etiqueta	
		elementCliente.appendChild(dom.createAttribute(DNI)).setNodeValue(cliente.getDni());
		
		//Creo un elemento para añadir el nombre del cliente
		Element elementNombre = dom.createElement(NOMBRE);
		
		//Añadimos atributos a la etiqueta Nombre con el tipo de dato
		//El tipo de dato del dni será el nombre de la clase que tiene la variable dni
		elementNombre.appendChild(dom.createAttribute(TIPO_DATO)).setNodeValue(cliente.getDni().getClass().getName());
		
		//A la etiqueta nombre le tengo que añadir un hijo que es un nodo de texto
		//con el nombre del cliente
		elementNombre.appendChild(dom.createTextNode(cliente.getNombre()));
		
		//Creo un elemento para añadir el nombre del cliente
		Element elementTelefono = dom.createElement(TELEFONO);
		
		//Añadimos atributos a la etiqueta Nombre con el tipo de dato
		//El tipo de dato del dni será el nombre de la clase que tiene la variable dni
		elementNombre.appendChild(dom.createAttribute(TIPO_DATO)).setNodeValue(cliente.getTelefono().getClass().getName());
		
		//A la etiqueta nombre le tengo que añadir un hijo que es un nodo de texto
		//con el nombre del cliente
		elementTelefono.appendChild(dom.createTextNode(cliente.getTelefono()));

		//Al elemento principal Cliente le añado como hijo el nombre
		elementCliente.appendChild(elementNombre);
		elementCliente.appendChild(elementTelefono);

		return elementCliente;
	}
	
	@Override
	public List<Cliente> get() {
		List <Cliente> copiaCliente = new ArrayList<>(coleccionClientes);
		
		return copiaCliente;
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}

		if (coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		}

		coleccionClientes.add(cliente);
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		Cliente clienteEncontrado;
		
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}

		int indice = coleccionClientes.indexOf(cliente);
		if(coleccionClientes.contains(cliente)) {
			clienteEncontrado = coleccionClientes.get(indice);
		}else {
			clienteEncontrado = null;
		}
		
		return clienteEncontrado;
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}

		if (!coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}

		coleccionClientes.remove(cliente);
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}

		if (!coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}

		if ((nombre != null) && (!nombre.trim().isEmpty())) {
			buscar(cliente).setNombre(nombre);
		}

		if ((telefono != null) && (!telefono.trim().isEmpty())) {
			buscar(cliente).setTelefono(telefono);
		}
	}
}
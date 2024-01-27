package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

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
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Vehiculos implements IVehiculos {

	//Atributos XML
	private static final String RUTA_FICHERO = "datos/vehiculos.xml";
	private static final String RAIZ = "Vehiculos";
	private static final String VEHICULO = "Vehiculo";
	private static final String MARCA = "Marca";
	private static final String MODELO = "Modelo";
	private static final String MATRICULA = "Matricula";
	private static final String CILINDRADA = "Cilindrada";
	private static final String PLAZAS = "Plazas";
	private static final String PMA = "Pma";
	private static final String TIPO = "Tipo";
	private static final String TURISMO = "Turismo";
	private static final String AUTOBUS = "Autobus";
	private static final String FURGONETA = "Furgoneta";
	private static final String TIPO_DATO = "TipoDato";
	
	
	//Patron Singleton: Solo puede haber una instancia, se indica del mismo tipo que
	//la propia clase y se indica como static para que siempre
	//sea la misma
	private static Vehiculos vehiculos;
	private List<Vehiculo> coleccionVehiculos;

	public Vehiculos() {
		coleccionVehiculos = new ArrayList<>();
	}

	//Creamos el metodo con visibilidad de paquete, sin indicar modificador
	//de acceso.
	static Vehiculos getInstancia() {
		//Si la instancia no está creada, la creamos
		if (vehiculos == null) {
			vehiculos = new Vehiculos();
		}
				
		return vehiculos;
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
			
			//Guardamos en una lista de nodos xml aquellos que tienen como etiqueta Vehiculo
			NodeList listaVehiculosXML = document.getElementsByTagName(VEHICULO);
			
			//Recorremos la lista de nodos que hemos guardado del xml
			for (int i = 0; i < listaVehiculosXML.getLength(); i++) {
				//Recoger los datos del xml
				Element elemento = (Element) listaVehiculosXML.item(i);
				//Crear objeto tipo Alquiler
				Vehiculo vehiculo = elementToVehiculo(elemento);
				insertar(vehiculo);
			}
		}
		catch (OperationNotSupportedException exop) {
			System.out.println(exop.getMessage());
		}
		catch (Exception ex) {
			System.out.println("Error al leer el fichero XML alquileres.xml");
		}
	}
	
	
	private Vehiculo elementToVehiculo(Element elemento) throws OperationNotSupportedException {
		Vehiculo vehiculo = null;
		
		//Recuperamos los atributos tipo y matricula que tiene la etiqueta Vehiculo
		String tipo = elemento.getAttribute(TIPO);
		String matricula = elemento.getAttribute(MATRICULA);
		
		//Recuperamos cada dato de las etiquetas
		String marca = elemento.getElementsByTagName(MARCA).item(0).getTextContent();
		String modelo = elemento.getElementsByTagName(MODELO).item(0).getTextContent();
		String turismo = elemento.getElementsByTagName(TURISMO).item(0).getTextContent();
		String furgoneta = elemento.getElementsByTagName(FURGONETA).item(0).getTextContent();
		String autobus = elemento.getElementsByTagName(AUTOBUS).item(0).getTextContent();
		
		//Si el tipo de vehiculo es turismo
		if (tipo.equals("Turismo")) {
			String cilindrada = elemento.getElementsByTagName(CILINDRADA).item(0).getTextContent();
			
			//Creamos un nuevo vehiculo de tipo Turismo
			//Convertimos a entero (parseInt) la cilindrada porque todo lo que se lee de un xml
			//siempre son datos de tipo String
			vehiculo = new Turismo(marca, modelo, Integer.parseInt(cilindrada), matricula);
		}
		else if (tipo.equals("Autobus")) {
			String plazas = elemento.getElementsByTagName(PLAZAS).item(0).getTextContent();
			
			//Creamos un nuevo vehiculo de tipo Autobus
			vehiculo = new Autobus(marca, modelo, Integer.parseInt(plazas), matricula);
		}
		else if (tipo.equals("Furgoneta")) {
			String plazas = elemento.getElementsByTagName(PLAZAS).item(0).getTextContent();
			String pma = elemento.getElementsByTagName(PMA).item(0).getTextContent();
			
			//Creamos un nuevo vehiculo de tipo Autobus
			vehiculo = new Furgoneta(marca, modelo, Integer.parseInt(pma), Integer.parseInt(plazas), matricula);
		}

		//Devolvemos el vehiculo (el tipo que se haya creado)
		return vehiculo;
	}
	
	@Override
	public void terminar() {
		escribirXML();
	}
	
	private void escribirXML() {
		try {
			Document document = UtilidadesXML.crearDomVacio(RAIZ);
			
			//Recorrer la lista de vehiculos
			for (int i = 0; i < coleccionVehiculos.size(); i++) {
				Element element = vehiculoToElement(document, coleccionVehiculos.get(i));
			}
			
			UtilidadesXML.domToXML(document, RUTA_FICHERO);
		}
		catch (Exception ex) {
			System.out.println("Error al crear el fichero XML");
		}
	}
	
	private Element vehiculoToElement(Document dom, Vehiculo vehiculo) {
		
		Element elementVehiculo = dom.createElement(VEHICULO);
		
		//Al elemento principal Vehiculo le añado dos objetos Atributo (Attr) con el valor matricula y tipo
		//ya que en el fichero xml el matricula y tipo está añadido como atributo y no como valor de etiqueta	
		elementVehiculo.appendChild(dom.createAttribute(MATRICULA)).setNodeValue(vehiculo.getMatricula());
		
		Element elementTipo = null;
		//El tipo de vehiculo dependerá del tipo de instancia
		if (vehiculo instanceof Turismo) {
			elementVehiculo.appendChild(dom.createAttribute(TIPO)).setNodeValue(TURISMO);
			//Cremos elemento tipo turismo y añadimos la cilindrada
			elementTipo = dom.createElement(TURISMO);
			Element elementCilindrada = dom.createElement(CILINDRADA);
			elementCilindrada.appendChild(dom.createTextNode(String.valueOf(((Turismo) vehiculo).getCilindrada())));
			elementTipo.appendChild(elementCilindrada);
			
		}
		else if (vehiculo instanceof Autobus) {
			elementVehiculo.appendChild(dom.createAttribute(TIPO)).setNodeValue(AUTOBUS);
			elementTipo = dom.createElement(AUTOBUS);
			Element elementPlazas = dom.createElement(PLAZAS);
			elementPlazas.appendChild(dom.createTextNode(String.valueOf(((Autobus) vehiculo).getPlazas())));
			elementTipo.appendChild(elementPlazas);
			
		}
		else if (vehiculo instanceof Furgoneta) {
			elementVehiculo.appendChild(dom.createAttribute(TIPO)).setNodeValue(FURGONETA);
			Element elementPlazas = dom.createElement(PLAZAS);
			elementPlazas.appendChild(dom.createTextNode(String.valueOf(((Furgoneta) vehiculo).getPlazas())));
			elementTipo.appendChild(elementPlazas);
			
			Element elementPma = dom.createElement(PMA);
			elementPlazas.appendChild(dom.createTextNode(String.valueOf(((Furgoneta) vehiculo).getPma())));
			elementTipo.appendChild(elementPma);
		}
		
		//Creo un elemento para añadir la marca del vehiculo
		Element elementoMarca = dom.createElement(MARCA);
		
		//Añadimos atributos a la etiqueta Marca con el tipo de dato
		//El tipo de dato de la marca será el nombre de la clase que tiene la variable marca
		elementoMarca.appendChild(dom.createAttribute(TIPO_DATO)).setNodeValue(vehiculo.getMarca().getClass().getName());
		
		//A la etiqueta marca le tengo que añadir un hijo que es un nodo de texto
		//con la marca del vehiculo
		elementoMarca.appendChild(dom.createTextNode(vehiculo.getMarca()));
		
		//Creo un elemento para añadir el modelo del vehiculo
		Element elementModelo = dom.createElement(MODELO);
		
		//Añadimos atributos a la etiqueta Modelo con el tipo de dato
		//El tipo de dato del model será el nombre de la clase que tiene la variable modelo
		elementModelo.appendChild(dom.createAttribute(TIPO_DATO)).setNodeValue(vehiculo.getModelo().getClass().getName());
		
		//A la etiqueta modelo le tengo que añadir un hijo que es un nodo de texto
		//con el modelo del vehiculo
		elementModelo.appendChild(dom.createTextNode(vehiculo.getModelo()));

		//Al elemento principal Cliente le añado como hijo el nombre
		elementVehiculo.appendChild(elementoMarca);
		elementVehiculo.appendChild(elementModelo);
		elementVehiculo.appendChild(elementTipo);
		return elementVehiculo;
	}
	
	
	@Override
	public List<Vehiculo> get() {
		List<Vehiculo> copiaVehiculo = new ArrayList<>(coleccionVehiculos);
		
		return copiaVehiculo;
	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo.");
		}

		if (coleccionVehiculos.contains(vehiculo)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula.");
		}
		
		if (vehiculo instanceof Turismo) {
			coleccionVehiculos.add(new Turismo((Turismo) vehiculo));
		}

		if (vehiculo instanceof Autobus) {
			coleccionVehiculos.add(new Autobus((Autobus) vehiculo));
		}
		
		if (vehiculo instanceof Furgoneta) {
			coleccionVehiculos.add(new Furgoneta((Furgoneta) vehiculo));
		}
	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		Vehiculo vehiculoEncontrado;
		
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo.");
		}
		
		int indice = coleccionVehiculos.indexOf(vehiculo);
		if(coleccionVehiculos.contains(vehiculo.copiar(vehiculo))) {
			vehiculoEncontrado = coleccionVehiculos.get(indice);
		}else {
			vehiculoEncontrado = null;
		}
		
		return vehiculoEncontrado;
	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo.");
		}

		if (!coleccionVehiculos.contains(vehiculo)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula.");
		}
		
		coleccionVehiculos.remove(vehiculo);
	}
}
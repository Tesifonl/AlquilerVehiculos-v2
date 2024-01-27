package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades;

import java.io.File;
import java.io.IOException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.Clientes;

//Import para utilizar las clase Document para el manejo de XML
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

//Imporrt para utilizar la clase que crea archivos xml Java
import org.w3c.dom.DOMImplementation;

//Import para utilizar la clase DocumentBuilderFactory es la fabrica de xml
//que convierte un fichero de texto xml en un ojeto de Java
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Result;
//Import para la fuente de datos del documento, para transformar en fichero fisico xml
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class UtilidadesXML {
	
	//Patron Singleton: Solo puede haber una instancia, se indica del mismo tipo que
	//la propia clase y se indica como static para que siempre
	//sea la misma
	private static UtilidadesXML utilidadesXML = new UtilidadesXML();
		
	private UtilidadesXML() {
	}
	
	public static Document xmlToDom(String rutaXml) throws ParserConfigurationException, SAXException, IOException {
		//Cargamos en una variable de tipo File (la clase predefinida en Java para el manejo de ficheros)
		//el fichero que queremos leer
		File archivo = new File(rutaXml);
		
		//Creo una instancia de DocumentBuilderFactory, la fabrica del fichero XML
		//que utilizaré para convertir el fichero xml en objeto Java
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        // Creo un documentBuilder --> crear el documento XML en memoria
        //Este metodo lanza una excepción del tipo ParserConfigurationException en caso que
        //el fichero xml pueda ser erróneo.
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        
        //Cargar el archivo xml en un objeto de la clase Document para poder mostrar sus datos
        Document document = documentBuilder.parse(archivo);
        
        //Devuelvo el document xml cargado del fichero
        return document;
	}
	
	public static void domToXML(Document dom, String rutaXml) throws TransformerFactoryConfigurationError, TransformerException {
		
		// Creo una fuente de datos (origen de los datos del fichero xml) con el objeto documento
		// El tipo de fuente es DOMSource --> fuente de datos de tipo DOM que utiliza un fichero xml
		// el objeto documento contiene todos los datos a incluir en el fichero xml.
		Source source = new DOMSource(dom);

		// Creo el objeto resultado, indicado que fichero se va a crear en la ruta fisica
		Result fichero = new StreamResult(new File(rutaXml));
		
		// Creo un transformer, objeto que transformará o convertira la fuente de datos en fichero xml resultado
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		
		//Se crea el fichero XML a partir del objeto source (fuente de datos) anterior y el fichero resultado
		//mediante el objeto transformer
		transformer.transform(source, fichero);	
	}
	
	public static Document crearDomVacio(String raiz) throws ParserConfigurationException {
		
		//Creo una instancia de DocumentBuilderFactory, la fabrica del fichero XML
		//que utilizaré para convertir el fichero xml en objeto Java
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        // Creo un documentBuilder --> crear el documento XML en memoria
        //Este metodo lanza una excepción del tipo ParserConfigurationException en caso que
        //el fichero xml pueda ser erróneo.
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        
        //Crear un objeto DOMImplementation que crea una implementación de un objeto DOM
        //que es el tipo de documento que utiliza un fichero xml
        DOMImplementation implementacion = documentBuilder.getDOMImplementation();
        
        //Para crear el archivo xml genérico se utiliza unicamente el parametro central raiz
        //el primero sería si se utiliza un espacio de nombres específico para crear nuestras etiquetas
        //el tercer parametro sería si el xml es de un tipo específico
        Document document = implementacion.createDocument(null, raiz, null);
        
        //Devolvemos del documento con la raiz
        return document;		        
		
	}
	
}

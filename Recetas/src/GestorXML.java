import java.io.File;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GestorXML {
    private static final String FICHERO = "recetas.xml";

    public static void exportar(List<Receta> recetas) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element raiz = doc.createElement("recetas");
            doc.appendChild(raiz);

            for (Receta r : recetas) {
                Element receta = doc.createElement("receta");
                receta.setAttribute("id", String.valueOf(r.getId()));

                Element nombre = doc.createElement("nombre");
                nombre.setTextContent(r.getNombre());
                receta.appendChild(nombre);

                Element categoria = doc.createElement("categoria");
                categoria.setTextContent(r.getCategoria());
                receta.appendChild(categoria);

                Element tiempo = doc.createElement("tiempoPreparacion");
                tiempo.setTextContent(String.valueOf(r.getTiempoPreparacion()));
                receta.appendChild(tiempo);

                Element dificultad = doc.createElement("dificultad");
                dificultad.setTextContent(r.getDificultad());
                receta.appendChild(dificultad);

                raiz.appendChild(receta);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FICHERO)));

            System.out.println("Catalogo exportado a " + FICHERO);
        } catch (ParserConfigurationException e) {
            System.out.println("Error al crear el documento XML: " + e.getMessage());
        } catch (TransformerException e) {
            System.out.println("Error al escribir el documento XML: " + e.getMessage());
        }
    }
}
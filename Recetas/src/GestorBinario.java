import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GestorBinario {
    private static final String FICHERO = "recetas.dat";

    public static boolean existe() {
        File f = new File(FICHERO);
        return f.exists();
    }

    public static void crearCatalogoInicial() {
        List<Receta> recetas = new ArrayList<>();
        recetas.add(new Receta(1, "Tortilla de patatas", "Principal", 40, "Media"));
        recetas.add(new Receta(2, "Ensalada mixta", "Entrante", 10, "Facil"));
        recetas.add(new Receta(3, "Paella valenciana", "Principal", 90, "Dificil"));
        recetas.add(new Receta(4, "Flan de huevo", "Postre", 60, "Media"));
        recetas.add(new Receta(5, "Gazpacho", "Entrante", 15, "Facil"));
        guardar(recetas);
    }

    public static List<Receta> leer() {
        List<Receta> recetas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO))) {
            recetas = (List<Receta>) ois.readObject();
        } catch (EOFException e) {
            System.out.println("El fichero esta vacio.");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero binario: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error de clase al leer el fichero: " + e.getMessage());
        }
        return recetas;
    }

    public static void guardar(List<Receta> recetas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO))) {
            oos.writeObject(recetas);
        } catch (IOException e) {
            System.out.println("Error al guardar el fichero binario: " + e.getMessage());
        }
    }
}
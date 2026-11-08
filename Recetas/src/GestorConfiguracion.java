import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestorConfiguracion {
    private static final String FICHERO = "configuracion.json";

    public static Configuracion cargar() {
        File f = new File(FICHERO);
        if (!f.exists()) {
            return new Configuracion(0, 0);
        }
        try (FileReader fr = new FileReader(f)) {
            Gson gson = new Gson();
            Configuracion config = gson.fromJson(fr, Configuracion.class);
            if (config == null) {
                return new Configuracion(0, 0);
            }
            return config;
        } catch (IOException e) {
            System.out.println("Error al leer la configuracion: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.out.println("El JSON de configuracion no es valido: " + e.getMessage());
        }
        return new Configuracion(0, 0);
    }

    public static void guardar(Configuracion config) {
        try (FileWriter fw = new FileWriter(FICHERO)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(config, fw);
        } catch (IOException e) {
            System.out.println("Error al guardar la configuracion: " + e.getMessage());
        }
    }
}
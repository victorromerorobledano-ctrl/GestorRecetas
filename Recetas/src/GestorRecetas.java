import java.util.List;
import java.util.Scanner;

public class GestorRecetas {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Configuracion config = GestorConfiguracion.cargar();
        System.out.println("Ultima opcion usada: " + config.getUltimaOpcion());
        System.out.println("Total de recetas en la ultima ejecucion: " + config.getTotalRecetas());

        if (!GestorBinario.existe()) {
            System.out.println("No existe recetas.dat, creando catalogo inicial...");
            GestorBinario.crearCatalogoInicial();
        }

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opcion: ");

            switch (opcion) {
                case 1:
                    mostrarRecetas();
                    break;
                case 2:
                    buscarReceta();
                    break;
                case 3:
                    modificarTiempo();
                    break;
                case 4:
                    anadirReceta();
                    break;
                case 5:
                    GestorXML.exportar(GestorBinario.leer());
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

            if (opcion >= 1 && opcion <= 5) {
                config.setUltimaOpcion(opcion);
            }
        } while (opcion != 0);

        config.setTotalRecetas(GestorBinario.leer().size());
        GestorConfiguracion.guardar(config);
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== GESTOR DE RECETAS =====");
        System.out.println("1. Mostrar todas las recetas");
        System.out.println("2. Buscar receta por ID");
        System.out.println("3. Modificar tiempo de preparacion");
        System.out.println("4. Anadir receta");
        System.out.println("5. Exportar a XML");
        System.out.println("0. Salir");
    }

    private static void mostrarRecetas() {
        List<Receta> recetas = GestorBinario.leer();
        if (recetas.isEmpty()) {
            System.out.println("No hay recetas.");
            return;
        }
        for (Receta r : recetas) {
            System.out.println(r);
        }
    }

    private static void buscarReceta() {
        int id = leerEntero("ID de la receta: ");
        Receta r = buscarPorId(GestorBinario.leer(), id);
        if (r == null) {
            System.out.println("No existe ninguna receta con ese ID.");
        } else {
            System.out.println(r);
        }
    }

    private static void modificarTiempo() {
        List<Receta> recetas = GestorBinario.leer();
        int id = leerEntero("ID de la receta a modificar: ");
        Receta r = buscarPorId(recetas, id);
        if (r == null) {
            System.out.println("No existe ninguna receta con ese ID.");
            return;
        }
        int nuevoTiempo = leerEntero("Nuevo tiempo de preparacion (min): ");
        r.setTiempoPreparacion(nuevoTiempo);
        GestorBinario.guardar(recetas);
        System.out.println("Tiempo modificado correctamente.");
    }

    private static void anadirReceta() {
        List<Receta> recetas = GestorBinario.leer();
        int id = siguienteId(recetas);

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Categoria: ");
        String categoria = sc.nextLine();
        int tiempo = leerEntero("Tiempo de preparacion (min): ");
        System.out.print("Dificultad: ");
        String dificultad = sc.nextLine();

        recetas.add(new Receta(id, nombre, categoria, tiempo, dificultad));
        GestorBinario.guardar(recetas);
        System.out.println("Receta anadida con ID " + id);
    }

    private static Receta buscarPorId(List<Receta> recetas, int id) {
        for (Receta r : recetas) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    private static int siguienteId(List<Receta> recetas) {
        int max = 0;
        for (Receta r : recetas) {
            if (r.getId() > max) {
                max = r.getId();
            }
        }
        return max + 1;
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debes introducir un numero entero.");
            }
        }
    }
}
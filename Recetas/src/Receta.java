import java.io.Serializable;

public class Receta implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String categoria;
    private int tiempoPreparacion;
    private String dificultad;

    public Receta(int id, String nombre, String categoria, int tiempoPreparacion, String dificultad) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.tiempoPreparacion = tiempoPreparacion;
        this.dificultad = dificultad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getDificultad() {
        return dificultad;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " | " + categoria + " | " + tiempoPreparacion + " min | " + dificultad;
    }
}
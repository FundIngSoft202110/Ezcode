package EZCode.Entidades;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Meta implements Serializable {
    private String nombre;
    private String descripcion;
    private int prioridad;
    private int progreso;
    private String fechaInicio;

    public Meta(String nombre, String descripcion, int prioridad, int progreso, String fechaInicio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.progreso = progreso;
        this.fechaInicio = fechaInicio;
    }
    public Meta(String nombre, String descripcion, int prioridad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }
    public Meta(String nombre) {
        this.nombre = nombre;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meta)) return false;
        Meta meta = (Meta) o;
        return getPrioridad() == meta.getPrioridad() &&
                getProgreso() == meta.getProgreso() &&
                getNombre().equals(meta.getNombre()) &&
                getDescripcion().equals(meta.getDescripcion()) &&
                getFechaInicio().equals(meta.getFechaInicio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getDescripcion(), getPrioridad(), getProgreso(), getFechaInicio());
    }

    @Override
    public String toString() {
        return "Meta{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", prioridad=" + prioridad +
                ", progreso=" + progreso +
                ", fechaInicio=" + fechaInicio +
                '}';
    }

    public Meta() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}

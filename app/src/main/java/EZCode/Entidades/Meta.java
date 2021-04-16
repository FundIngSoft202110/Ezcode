package EZCode.Entidades;

import java.util.Date;

public class Meta {
    private String nombre;
    private String descripcion;
    private int prioridad;
    private int progreso;
    private Date fechaInicio;

    public Meta(String nombre, String descripcion, int prioridad, int progreso, Date fechaInicio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.progreso = progreso;
        this.fechaInicio = fechaInicio;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}

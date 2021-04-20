package EZCode.Entidades;

import java.util.Calendar;
import java.util.Date;

public class Actividad extends Evento{
    private String descripcion;

    public Actividad(Calendar horaInicial, Calendar horaFinal, String nombre, String descripcion) {
        super(horaInicial, horaFinal, nombre);
        this.descripcion = descripcion;
    }

    public Actividad() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

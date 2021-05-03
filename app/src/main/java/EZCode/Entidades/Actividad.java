package EZCode.Entidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Actividad extends Evento{
    private String descripcion;

    public Actividad(Calendar horaInicial, Calendar horaFinal, String nombre, String descripcion) {
        super(horaInicial, horaFinal, nombre);
        this.descripcion = descripcion;
    }

    @Override
    public List<String> getSubAtributos() {
        List<String> retorno = new ArrayList<>();
        retorno.add(descripcion);
        return retorno;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actividad)) return false;
        if (!super.equals(o)) return false;
        Actividad actividad = (Actividad) o;
        return super.equals(o) && Objects.equals(getDescripcion(), actividad.getDescripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDescripcion());
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

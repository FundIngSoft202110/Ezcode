package EZCode.Entidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public Actividad() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

package EZCode.Entidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Clase extends Evento{
    private String profesor;
    private String salon;

    public Clase(Calendar horaInicial, Calendar horaFinal, String nombre, String profesor, String salon) {
        super(horaInicial, horaFinal, nombre);
        this.profesor = profesor;
        this.salon = salon;
    }

    @Override
    public List<String> getSubAtributos() {
        List<String> retorno = new ArrayList<>();
        retorno.add(profesor);
        retorno.add(salon);
        return retorno;
    }

    public Clase() {
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }
}

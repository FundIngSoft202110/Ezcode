package EZCode.Entidades;

import java.util.Date;

public class Clase extends Evento{
    private String profesor;
    private String salon;

    public Clase(Date horaInicial, Date horaFinal, String nombre, String profesor, String salon) {
        super(horaInicial, horaFinal, nombre);
        this.profesor = profesor;
        this.salon = salon;
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

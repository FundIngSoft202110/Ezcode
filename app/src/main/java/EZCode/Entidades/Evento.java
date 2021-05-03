package EZCode.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class Evento implements Serializable {
    private Calendar horaInicial;
    private Calendar horaFinal;
    private String nombre;
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public abstract List<String> getSubAtributos();

    public Evento(Calendar horaInicial, Calendar horaFinal, String nombre) {
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if(o != null && o.getClass() == getClass()) {
            Evento evento = (Evento) o;
            return Objects.equals(getHoraInicial(), evento.getHoraInicial()) &&
                    Objects.equals(getHoraFinal(), evento.getHoraFinal()) &&
                    Objects.equals(getNombre(), evento.getNombre());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHoraInicial(), getHoraFinal(), getNombre());
    }

    public Evento() {
    }

    public Calendar getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Calendar horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Calendar getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Calendar horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

package EZCode.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class Evento implements Serializable {
    private Calendar horaInicial;
    private Calendar horaFinal;
    private String nombre;

    public abstract List<String> getSubAtributos();

    public Evento(Calendar horaInicial, Calendar horaFinal, String nombre) {
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.nombre = nombre;
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

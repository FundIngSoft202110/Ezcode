package EZCode.Entidades;

import java.util.Date;

public abstract class Evento {
    private Date horaInicial;
    private Date horaFinal;
    private String nombre;

    public Evento(Date horaInicial, Date horaFinal, String nombre) {
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.nombre = nombre;
    }

    public Evento() {
    }

    public Date getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Date horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

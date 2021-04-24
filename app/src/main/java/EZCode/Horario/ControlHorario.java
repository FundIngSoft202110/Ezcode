package EZCode.Horario;

import java.util.Calendar;

import EZCode.Entidades.*;

public class ControlHorario {

    public void agregarEvento(Evento evento){
        Estudiante.getInstance().getHorario().add(evento);
    }
    public Boolean verificarFecha(Calendar inicio, Calendar fin){
        if(!inicio.before(fin))
            return true;
        else
            return false;
    }

}

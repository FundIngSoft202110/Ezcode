package EZCode.Horario;

import java.util.Calendar;
import java.util.List;

import EZCode.Entidades.*;

public class ControlHorario {

    public void agregarRepeticiones(List<String> dias,int repeticiones){

    }
    public void agregarEvento(Evento evento){
        evento.setID(Estudiante.getInstance().getHorario().size());
        Estudiante.getInstance().getHorario().add(evento);
    }
    public Boolean verificarFecha(Calendar inicio, Calendar fin){
        /*
        Solo faltaria verificar que no exista un evento que se cruce con esas fechas
         */
        if(!inicio.before(fin))
            return true;
        else
            return false;
    }
    public void eliminarEvento(Evento evento){
        Estudiante.getInstance().getHorario().remove(evento.getID());
    }
    public void modificarEvento(Evento evento){
        Estudiante.getInstance().getHorario().remove(evento.getID());
        Estudiante.getInstance().getHorario().add(evento.getID(),evento);
    }

}

package EZCode.Horario;

import EZCode.Entidades.*;

public class ControlHorario {

    public void agregarEvento(Evento evento){
        Estudiante.getInstance().getHorario().add(evento);
    }

}

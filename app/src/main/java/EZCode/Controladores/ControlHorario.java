package EZCode.Horario;

import android.app.usage.UsageEvents;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventObject;
import java.util.List;

import EZCode.Entidades.*;

public class ControlHorario {

    public void agregarEvento(Evento evento, List<String> dias, int numRepeticiones){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        if(numRepeticiones == 0) {
            evento.setID(Estudiante.getInstance().getHorario().size());
            Estudiante.getInstance().getHorario().add(evento);
        }
        else{
            Log.d("Control", "Lista: " + Estudiante.getInstance().getHorario().toString());
            for (int i = 0; i < numRepeticiones; i++){
                for (String dia : dias) {
                    Calendar inicio = Calendar.getInstance();
                    Calendar fin = Calendar.getInstance();
                    inicio.set(evento.getHoraInicial().get(Calendar.YEAR),evento.getHoraInicial().get(Calendar.MONTH),
                            evento.getHoraInicial().get(Calendar.DATE),evento.getHoraInicial().get(Calendar.HOUR),
                            evento.getHoraInicial().get(Calendar.MINUTE));
                    fin.set(evento.getHoraFinal().get(Calendar.YEAR),evento.getHoraFinal().get(Calendar.MONTH),
                            evento.getHoraFinal().get(Calendar.DATE),evento.getHoraFinal().get(Calendar.HOUR),
                            evento.getHoraFinal().get(Calendar.MINUTE));
                    inicio.set(Calendar.DAY_OF_WEEK,convertirDia(dia));
                    inicio.add(Calendar.WEEK_OF_YEAR,i);
                    Log.d("Control", "Inicio: " + df.format(inicio.getTime()));
                    fin.set(Calendar.DAY_OF_WEEK,convertirDia(dia));
                    fin.add(Calendar.WEEK_OF_YEAR,i);
                    Log.d("Control", "Fin: " + df.format(fin.getTime()));
                    if(evento instanceof Clase){
                        Estudiante.getInstance().getHorario().add(new Clase(inicio,fin,evento.getNombre(),
                                ((Clase)evento).getProfesor(),((Clase)evento).getSalon()));
                    }
                    else{
                        Estudiante.getInstance().getHorario().add(new Actividad(inicio,fin,evento.getNombre(),
                                ((Actividad)evento).getDescripcion()));
                    }
                }
                Log.d("Control", "Lista: " + Estudiante.getInstance().getHorario().toString());
            }
        }
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
    private int convertirDia(String dia){
        if(dia == "domingo")
            return Calendar.SUNDAY;
        if(dia == "lunes")
            return Calendar.MONDAY;
        if(dia == "martes")
            return Calendar.TUESDAY;
        if(dia == "miercoles")
            return Calendar.WEDNESDAY;
        if(dia == "jueves")
            return Calendar.THURSDAY;
        if(dia == "viernes")
            return Calendar.FRIDAY;
        if(dia == "sabado")
            return Calendar.SATURDAY;
        return 0;
    }
}

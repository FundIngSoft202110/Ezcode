package EZCode.Controladores;

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
                                ((Clase)evento).getProfesor(),((Clase)evento).getSalon(),
                                Estudiante.getInstance().getHorario().size()));
                    }
                    else{
                        Estudiante.getInstance().getHorario().add(new Actividad(inicio,fin,
                                evento.getNombre(),((Actividad)evento).getDescripcion(),
                                Estudiante.getInstance().getHorario().size()));
                    }
                }
            }
        }
    }
    public String fechaValida(Calendar inicio, Calendar fin){
        if(inicio.after(fin))
            return "La fecha de inicio está despues de la fecha de fin";
        for (Evento evento: Estudiante.getInstance().getHorario()) {
            if(hayInterseccionEventos(evento,inicio,fin))
                return "Ya tiene un evento programado a esa hora";
        }
        return "";
    }
    public void eliminarEvento(Evento evento){
        Estudiante.getInstance().getHorario().remove(evento.getID());
    }
    public void modificarEvento(Evento evento){
        Estudiante.getInstance().getHorario().remove(evento.getID());
        Estudiante.getInstance().getHorario().add(evento.getID(),evento);
    }
    public Boolean hayInterseccionEventos(Evento evento, Calendar inicio, Calendar fin){
        evento.getHoraInicial().clear(Calendar.MILLISECOND);
        evento.getHoraFinal().clear(Calendar.MILLISECOND);
        inicio.clear(Calendar.MILLISECOND);
        fin.clear(Calendar.MILLISECOND);
        if (inicio.equals(evento.getHoraInicial()) && fin.equals(evento.getHoraFinal())) {
            return true;
        }
        if (inicio.before(evento.getHoraInicial()) && fin.after(evento.getHoraFinal())) {
            return true;
        }
        Boolean inicial = inicio.before(evento.getHoraInicial()) || inicio.after(evento.getHoraFinal());
        Boolean ffinal = fin.before(evento.getHoraInicial()) || fin.after(evento.getHoraFinal());
        if( inicial && ffinal) {
            return false;
        }
        return true;
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

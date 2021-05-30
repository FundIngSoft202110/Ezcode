package EZCode.Controladores;



import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import EZCode.Entidades.*;


import android.app.AlarmManager;

import static java.util.Calendar.*;


public class ControlMetas{




    public Evento metaEvento(Meta meta,Calendar horaInicial,Calendar horaFinal)
    {
        Actividad metaActividad = new Actividad(horaInicial,horaFinal,meta.getNombre(),meta.getNombre());
        return metaActividad;

    }
    public Calendar inicioSemana (Calendar cal)
    {
        //Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        //cal.getTime()
        return cal;
    }
    public boolean horaCondiciones(Calendar Inicio, Evento comparador)
    {
        Calendar auxSiguiente= Calendar.getInstance();
        auxSiguiente=Inicio;
        auxSiguiente.add(HOUR_OF_DAY,2);

            if(Inicio.before(comparador.getHoraInicial())&&auxSiguiente.before(comparador.getHoraInicial())||
               Inicio.after(comparador.getHoraFinal())&&auxSiguiente.after(comparador.getHoraFinal())
            )
            {
                return  true;

            }else {
                return false;
            }

    }
    public boolean horaDisponible(List<Evento>horario,Calendar Inicio)
    {
        Calendar auxSiguiente= Calendar.getInstance();
        auxSiguiente=Inicio;
        auxSiguiente.add(HOUR_OF_DAY,2);
        for(int indice= 0 ; indice< horario.size();indice++)
        {
            if(horaCondiciones(Inicio,horario.get(indice))==false)
            {
                return false;
            }
        }
        return true;
    }
    public Calendar busqueda(Calendar Inicio)
    {
        boolean encontrado = false;
        Calendar finalSemana= getInstance();
        Calendar aux= Inicio;
        finalSemana = Inicio;
        finalSemana.add(DAY_OF_WEEK,7);

        while(aux.before(finalSemana) && encontrado == false )
        {
            encontrado = horaDisponible(Estudiante.getInstance().getHorario(),aux);
            if(encontrado == true)
            {break;}else {
                aux.add(DAY_OF_WEEK, 1);
            }
        }
        if(encontrado == true)
        {
            return aux;
        }else
        {
            return null;
        }

    }

    public void listaMetasEventos()
    {
        int vecesxdia = 2 ;
        Calendar horaInicio = getInstance();
        horaInicio = inicioSemana(horaInicio);//supongo que esto esta bien
        List<Meta> auxMetas =  Estudiante.getInstance().getMetas();

        Calendar auxNuevo;
        Calendar auxFinal;
        for(int indice = 0;indice<auxMetas.size();indice++)
        {
            for(int i = 0 ;i<vecesxdia;i++)
            {
                auxNuevo = busqueda(horaInicio);

                if(auxNuevo== null)
                {
                    break;
                }else {
                    auxFinal  = auxNuevo;
                    auxFinal.add(HOUR_OF_DAY,2);
                    Estudiante.getInstance().getMetasEvento().add(metaEvento(auxMetas.get(indice), auxNuevo,auxFinal ));
                    auxFinal.add(DAY_OF_WEEK,1);

                }


            }

        }

    }




    public void modificarMeta(Meta meta, int i){
        Estudiante.getInstance().getMetas().remove(i);
        Estudiante.getInstance().getMetas().add(i,meta);
    }

    public void eliminarMeta(int i){
        Estudiante.getInstance().getMetas().remove(i);
    }

    public void agregarMeta(Meta meta){
        Estudiante.getInstance().getMetas().add(meta);
    }



    public ControlMetas() { }

}

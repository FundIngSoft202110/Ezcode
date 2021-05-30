package EZCode.Pantallas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.List;
import static java.util.Calendar.*;
import EZCode.Entidades.Actividad;
import EZCode.Entidades.Estudiante;
import EZCode.Entidades.Evento;
import EZCode.Entidades.Meta;
import EZCode.Controladores.ControlMetas;


public class AlarmaMetaEvento extends BroadcastReceiver {
    public Evento metaEvento(Meta meta,Calendar horaInicial,Calendar horaFinal)
    {
        Actividad metaActividad = new Actividad(horaInicial,horaFinal,meta.getNombre(),meta.getDescripcion());
        return metaActividad;

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
            if(encontrado == false)
            {
                aux.add(HOUR_OF_DAY,1);
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





    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar horaInicio = Calendar.getInstance();
        int vecesxdia = 2 ;
        List<Meta> auxMetas =  Estudiante.getInstance().getMetas();
        Calendar auxNuevo = horaInicio;
        Calendar auxFinal = auxNuevo;
        auxFinal.add(HOUR_OF_DAY,2);
        for(int indice = 0;indice<auxMetas.size();indice++)
        {

            for(int i = 0 ;i<vecesxdia;i++)
            {
                auxNuevo = busqueda(auxNuevo);
                if(auxNuevo== null)
                {
                    break;
                }else{

                    Estudiante.getInstance().getHorario().add(metaEvento(auxMetas.get(indice), auxNuevo,auxFinal ));
                    Estudiante.getInstance().getMetasEvento().add(metaEvento(auxMetas.get(indice), auxNuevo,auxFinal ));
                    auxNuevo.add(DAY_OF_WEEK,1);
                    auxFinal= auxNuevo;
                    auxFinal.add(HOUR_OF_DAY,2);

                }
            }
            auxNuevo = horaInicio;

        }

    }
}

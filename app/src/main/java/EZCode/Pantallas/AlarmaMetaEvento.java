package EZCode.Pantallas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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
        String message = intent.getStringExtra("message");
        Calendar horaInicio = Calendar.getInstance();
        Calendar horaFinal = Calendar.getInstance();
        horaFinal.add(HOUR_OF_DAY,2);
        Actividad aux = new Actividad(horaInicio,horaFinal,message,"metas");

        Toast.makeText(context, horaInicio.toString()+" Esta en el brodacast reciever  ", Toast.LENGTH_LONG).show();


        Estudiante.getInstance().getHorario().add(aux);
        Toast.makeText(context, "Agregue evento 1 ", Toast.LENGTH_LONG).show();

        horaInicio.add(DAY_OF_WEEK,2);
        horaFinal.add(DAY_OF_WEEK,2);


        Actividad aux2= new Actividad(horaInicio,horaFinal,message,"metas");

        Estudiante.getInstance().getHorario().add(aux2);

        Toast.makeText(context, " Agregue evento 2", Toast.LENGTH_LONG).show();


        /*int vecesxdia = 2 ;
        Calendar auxNuevo= (Calendar)horaInicio.clone();
        Calendar auxFinal = (Calendar) horaFinal.clone();

            for(int i = 0 ;i<vecesxdia;i++)
            {
                auxNuevo = busqueda(auxNuevo);
                if(auxNuevo== null)
                {
                    break;
                }else{

                    Estudiante.getInstance().getHorario().add(aux);
                    Estudiante.getInstance().getMetasEvento().add(aux);
                    auxNuevo.add(DAY_OF_WEEK,1);
                    auxFinal= auxNuevo;
                    auxFinal.add(HOUR_OF_DAY,2);

                }

      */


    }
}

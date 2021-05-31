package main.java.EZCode.Controladores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.java.EZCode.Entidades.*;

public class ControlHorario {
    FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference data = db.getReference();
    public void agregarEvento(Evento evento, List<String> dias, int numRepeticiones){
        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm");
        if(numRepeticiones == 0) {
            evento.setID(Estudiante.getInstance().getHorario().size());
            Estudiante.getInstance().getHorario().add(evento);
            DTOEvento dtoEvento = new DTOEvento(df.format(evento.getHoraInicial().getTime()),
                    df.format(evento.getHoraFinal().getTime()),evento.getNombre(),evento.getID());
            if(evento instanceof Clase) {
                dtoEvento.setTipo("Clase");
                dtoEvento.setProfesor(((Clase)evento).getProfesor());
                dtoEvento.setSalon(((Clase)evento).getSalon());
            }
            else {
                dtoEvento.setTipo("Actividad");
                dtoEvento.setDescripcion(((Actividad)evento).getDescripcion());
            }
            data.child("Eventos").child(usuario.getUid()).child(String.valueOf(dtoEvento.getID()))
                    .setValue(dtoEvento);
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
                    fin.set(Calendar.DAY_OF_WEEK,convertirDia(dia));
                    fin.add(Calendar.WEEK_OF_YEAR,i);
                    int ID = Estudiante.getInstance().getHorario().size();
                    if(evento instanceof Clase){
                        Estudiante.getInstance().getHorario().add(new Clase(inicio,fin,evento.getNombre(),
                                ((Clase)evento).getProfesor(),((Clase)evento).getSalon(),ID));
                        data.child("Eventos").child(usuario.getUid()).child(String.valueOf(ID))
                                .setValue(new DTOEvento(df.format(inicio.getTime()), df.format(fin.getTime())
                                        ,evento.getNombre(),ID, "Clase",((Clase)evento).getProfesor(),
                                        ((Clase) evento).getSalon()));
                    }
                    else{
                        Estudiante.getInstance().getHorario().add(new Actividad(inicio,fin,
                                evento.getNombre(),((Actividad)evento).getDescripcion(), ID));
                        data.child("Eventos").child(usuario.getUid()).child(String.valueOf(ID))
                                .setValue(new DTOEvento(df.format(inicio.getTime()),
                                        df.format(fin.getTime()), evento.getNombre(),ID,
                                        "Actividad",((Actividad)evento).getDescripcion()));
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
        int id = evento.getID();
        Log.d("Eliminando Evento","lista antes de borrar: "+Estudiante.getInstance().getHorario().toString());
        Estudiante.getInstance().getHorario().remove(id);

        data.child("Eventos").child(usuario.getUid()).child(String.valueOf(id)).removeValue();
        for (Evento e : Estudiante.getInstance().getHorario()) {
            int tempID = e.getID();
            if(tempID > id){
                e.setID(tempID-1);
                DTOEvento dto = convertirADTOEvento(e);
                data.child("Eventos").child(usuario.getUid()).child(String.valueOf(id)).setValue(dto);
            }
        }
        Log.d("Eliminando Evento","lista despues de borrar: "+Estudiante.getInstance().getHorario().toString());
        data.child("Eventos").child(usuario.getUid()).child(String.valueOf(Estudiante.getInstance().getHorario().size())).removeValue();
    }
    public void modificarEvento(Evento evento){
        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm");
        DTOEvento ev = new DTOEvento(df.format(evento.getHoraInicial().getTime()),
                df.format(evento.getHoraFinal().getTime()),evento.getNombre(),evento.getID());
        if(evento instanceof Clase){
            ev.setProfesor(((Clase)evento).getProfesor());
            ev.setSalon(((Clase)evento).getSalon());
            ev.setTipo("Clase");
        }
        else{
            ev.setDescripcion(((Actividad)evento).getDescripcion());
            ev.setTipo("Actividad");
        }

        data.child("Eventos").child(usuario.getUid()).child(String.valueOf(evento.getID())).setValue(ev)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful())
                    Log.d("Control", "Algo salio mal");
            }
        });
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
    public DTOEvento convertirADTOEvento(Evento evento){
        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm");
        DTOEvento dto = new DTOEvento(df.format(evento.getHoraInicial().getTime()),
                df.format(evento.getHoraFinal().getTime()),evento.getNombre(),evento.getID());
        if(evento instanceof Clase){
            dto.setTipo("Clase");
            dto.setProfesor(((Clase)evento).getProfesor());
            dto.setSalon(((Clase)evento).getSalon());
        }
        else{
            dto.setTipo("Actividad");
            dto.setDescripcion(((Actividad)evento).getDescripcion());
        }
        return dto;
    }
    public List<Evento> convertirAEvento(List<DTOEvento> eventosDB) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm");
        List<Evento> eventos = new ArrayList<>();
        for (DTOEvento ev : eventosDB) {
            Calendar inicio = Calendar.getInstance();
            Calendar fin = Calendar.getInstance();
            inicio.setTime(df.parse(ev.getHoraInicial()));
            fin.setTime(df.parse(ev.getHoraFinal()));
            Evento evento;
            if(ev.getTipo().equals("Clase")){
                evento = new Clase(inicio, fin, ev.getNombre(), ev.getProfesor(), ev.getSalon(), ev.getID());
            }
            else{
                evento = new Actividad(inicio, fin, ev.getNombre(), ev.getDescripcion(), ev.getID());
            }
            eventos.add(evento);
        }
        return eventos;
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

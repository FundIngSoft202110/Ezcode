package EZCode.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import EZCode.Entidades.Estudiante;
import EZCode.Entidades.Evento;
import EZCode.Horario.ControlHorario;

public class PantallaHorario extends AppCompatActivity {

    CalendarView calendario;
    Button botonCerrarSesion;
    Button botonAgregarEvento;
    Button botonMetas;
    ListView listaEventos;
    List<String> horario;
    List<Evento> eventosDia;
    ArrayAdapter adapter;
    ControlHorario controlHorario = new ControlHorario();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_horario);

        iniciarlizarAtributos();
        inicializarLista();

        listaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PantallaModificarEvento.class);
                intent.putExtra("Evento",eventosDia.get(position));
                startActivity(intent);
            }
        });

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                iniciarLista(year,month,dayOfMonth);
            }
        });

        botonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });
        botonAgregarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPantallaAgregarEvento();
            }
        });
        botonMetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPantallaMetas();
            }
        });

    }

    private void iniciarLista(int year, int month, int day){
        horario.clear();
        int i = 0;
        for (Evento e: Estudiante.getInstance().getHorario()){
            e.setID(i);
            if(e.getHoraInicial().get(Calendar.YEAR) == year &&
                    e.getHoraInicial().get(Calendar.MONTH) == month &&
                    e.getHoraInicial().get(Calendar.DAY_OF_MONTH) == day) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                horario.add(e.getNombre()+"--"+dateFormat.format(e.getHoraInicial().getTime()));
                eventosDia.add(e);
            }
            i++;
        }
        listaEventos.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, horario));
    }
    private void inicializarLista(){
        Calendar calendar = Calendar.getInstance();
        iniciarLista(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }
    /*
    Este metodo debería cerrar la sesión del usuario, lo cuál debería llamar un método del controlador
    que permita guardar todo en la BD, por ahora solo vuelve a la pantalla de autenticación
     */
    private void cerrarSesion(){
        Intent intent = new Intent(this, PantallaAutenticacion.class);
        startActivity(intent);
    }
    private void abrirPantallaAgregarEvento(){
        Intent intent = new Intent(this, PantallaAgregarEvento.class);
        startActivity(intent);
    }
    private void abrirPantallaMetas(){
        Intent intent = new Intent(this, PantallaMetas.class);
        startActivity(intent);
    }
    private void iniciarlizarAtributos(){
        calendario =  (CalendarView) findViewById(R.id.calendarView);
        botonAgregarEvento = (Button) findViewById(R.id.botonNuevoEvento);
        botonCerrarSesion = (Button) findViewById(R.id.botonCerrarSesion);
        botonMetas = (Button) findViewById(R.id.botonMetas);
        listaEventos = (ListView) findViewById(R.id.listaEventos);
        horario = new ArrayList<>();
        eventosDia = new ArrayList<>();
    }
}
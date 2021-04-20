package EZCode.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import EZCode.Entidades.Estudiante;
import EZCode.Entidades.Evento;
import EZCode.Entidades.Meta;

public class PantallaHorario extends AppCompatActivity {

    CalendarView calendario;
    Button botonVolverPantallaPrincipal;
    Button botonAgregarEvento;
    ListView listaEventos;
    ArrayList<String> horario;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_horario);

        iniciarlizarAtributos();
        inicializarLista();

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                iniciarLista(year,month,dayOfMonth);
            }
        });

        botonVolverPantallaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverPantallaPrincipal();
            }
        });
        botonAgregarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPantallaAgregarEvento();
            }
        });

    }
    private void iniciarlizarAtributos(){
        calendario =  (CalendarView) findViewById(R.id.calendarView);
        botonAgregarEvento = (Button) findViewById(R.id.botonNuevoEvento);
        botonVolverPantallaPrincipal = (Button) findViewById(R.id.botonVolverPPrincipal);
        listaEventos = (ListView) findViewById(R.id.listaEventos);
        horario = new ArrayList<>();
    }
    private void iniciarLista(int year, int month, int day){
        horario.clear();
        for (Evento e: Estudiante.getInstance().getHorario()){
            if(e.getHoraInicial().get(Calendar.YEAR) == year &&
                    e.getHoraInicial().get(Calendar.MONTH) == month &&
                    e.getHoraInicial().get(Calendar.DAY_OF_MONTH) == day) {
                horario.add(e.getNombre());
            }
        }
        listaEventos.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, horario));
    }
    private void inicializarLista(){
        Calendar calendar = Calendar.getInstance();
        iniciarLista(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }
    private void volverPantallaPrincipal(){
        Intent intent = new Intent(this, PantallaPrincipal.class);
        startActivity(intent);
    }
    private void abrirPantallaAgregarEvento(){
        Intent intent = new Intent(this, PantallaAgregarEvento.class);
        startActivity(intent);
    }
}
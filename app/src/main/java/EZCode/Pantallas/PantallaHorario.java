package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import java.util.ArrayList;

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
        iniciarLista();

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
    private void iniciarLista(){
        for (Evento e: Estudiante.getInstance().getHorario()) {
            horario.add(e.getNombre());
        }
        listaEventos.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, horario));
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
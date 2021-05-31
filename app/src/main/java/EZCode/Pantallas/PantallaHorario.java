package EZCode.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import EZCode.Controladores.ControlHorario;
import EZCode.Entidades.Actividad;
import EZCode.Entidades.Clase;
import EZCode.Entidades.DTOEvento;
import EZCode.Entidades.Estudiante;
import EZCode.Entidades.Evento;

public class PantallaHorario extends AppCompatActivity {

    CalendarView calendario;
    Button botonCerrarSesion;
    Button botonAgregarEvento;
    Button botonMetas;
    ListView listaEventos;
    TextView bienvenida;
    List<String> horario;
    List<Evento> eventosDia;
    FirebaseAuth autenticar;
    FirebaseUser usuario;
    FirebaseDatabase db;
    DatabaseReference refEventos;
    ControlHorario controlHorario;
    String nombre;

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
                autenticar.signOut();
                cerrarSesion();
                finish();
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

            public void onClick(View v) { abrirPantallaMetas(); }
        });

    }

    private void iniciarLista(int year, int month, int day){
        horario.clear();
        eventosDia.clear();
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
    private void inicializarLista() {
        List<DTOEvento> eventosDB = new ArrayList<>();
        refEventos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    DTOEvento ev = data.getValue(DTOEvento.class);
                    eventosDB.add(ev);
                }
                try {
                    Log.d("Eventos DB",eventosDB.toString());
                    Estudiante.getInstance().setHorario(controlHorario.convertirAEvento(eventosDB));
                    Calendar calendar = Calendar.getInstance();
                    iniciarLista(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
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
        bienvenida = (TextView) findViewById(R.id.textView7);
        horario = new ArrayList<>();
        eventosDia = new ArrayList<>();
        autenticar = FirebaseAuth.getInstance();
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        refEventos = db.getReference().child("Eventos").child(usuario.getUid());
        controlHorario = new ControlHorario();
        //Dar la bienvenida

        DatabaseReference ref = db.getReference();

        ref.child("Estudiantes").child(usuario.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               nombre =  snapshot.child("nombre").getValue().toString();
               bienvenida.setText("Horario de " + nombre);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }
}

package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import EZCode.Entidades.Actividad;
import EZCode.Entidades.Clase;
import EZCode.Entidades.Estudiante;
import EZCode.Entidades.Evento;
import EZCode.Horario.ControlHorario;

public class PantallaModificarEvento extends AppCompatActivity {

    Button botonModificarEvento;
    Button botonEliminarEvento;
    Calendar fechaInicio;
    Calendar fechaFin;
    TextView profesor;
    TextView errores;
    TextView descripcion;
    TextView salon;
    EditText campoNombre;
    EditText campoFechaInicio;
    EditText campoFechaFin;
    EditText campoProfesor;
    EditText campoSalon;
    EditText campoDescripcion;

    Evento evento;
    private ControlHorario controlHorario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_modificar_evento);

        iniciarAtributos();
        llenarCampos();

        botonEliminarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlHorario.eliminarEvento(evento);
                volverPantallaHorario();
            }
        });
        botonModificarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verificarCampos()){
                    errores.setText("Verifique que todos los campos estén llenos");
                    return;
                }
                if(fechaInicio == null && fechaFin == null){
                    fechaInicio = evento.getHoraInicial();
                    fechaFin = evento.getHoraFinal();
                }
                Evento event;
                if(evento instanceof Clase){
                    event = new Clase(fechaInicio, fechaFin, campoNombre.getText().toString(),
                            campoProfesor.getText().toString(), campoSalon.getText().toString());
                }
                else{
                    event = new Actividad(fechaInicio, fechaFin, campoNombre.getText().toString(),
                            campoDescripcion.getText().toString());
                }
                event.setID(evento.getID());
                controlHorario.modificarEvento(event);
                SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
                volverPantallaHorario();
            }
        });
        campoFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoFechaInicio();
            }
        });
        campoFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoFechaFin();
            }
        });
    }
    private void llenarCampos(){
        campoNombre.setText(evento.getNombre());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
        campoFechaInicio.setText(dateFormat.format(evento.getHoraInicial().getTime()));
        campoFechaFin.setText(dateFormat.format(evento.getHoraFinal().getTime()));
        if(evento instanceof Actividad){
            campoProfesor.setVisibility(View.INVISIBLE);
            campoSalon.setVisibility(View.INVISIBLE);
            profesor.setVisibility(View.INVISIBLE);
            salon.setVisibility(View.INVISIBLE);
            List<String> lista = evento.getSubAtributos();
            campoDescripcion.setText(lista.get(0));
        }
        else{
            campoDescripcion.setVisibility(View.INVISIBLE);
            descripcion.setVisibility(View.INVISIBLE);
            List<String> lista = evento.getSubAtributos();
            campoProfesor.setText(lista.get(0));
            campoSalon.setText(lista.get(1));
        }
    }
    private boolean verificarCampos(){
        if(campoNombre.getText().toString().equals("") || campoFechaInicio.getText().toString().equals("") || campoFechaFin.getText().toString().equals(""))
            return false;
        if(evento instanceof Clase && (campoProfesor.getText().toString().equals("") || campoSalon.getText().toString().equals("")))
            return false;
        if(evento instanceof Actividad && (campoDescripcion.getText().toString().equals("")))
            return false;
        return true;
    }
    private void mostrarDialogoFechaInicio(){
        fechaInicio = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaInicio.set(Calendar.YEAR,year);
                fechaInicio.set(Calendar.MONTH,month);
                fechaInicio.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        fechaInicio.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        fechaInicio.set(Calendar.MINUTE,minute);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                        campoFechaInicio.setText(dateFormat.format(fechaInicio.getTime()));
                    }
                };
                new TimePickerDialog(PantallaModificarEvento.this,timeSetListener,
                        fechaInicio.get(Calendar.HOUR_OF_DAY),fechaInicio.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(PantallaModificarEvento.this,dateSetListener,
                fechaInicio.get(Calendar.YEAR),fechaInicio.get(Calendar.MONTH),
                fechaInicio.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void mostrarDialogoFechaFin(){
        fechaFin = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaFin.set(Calendar.YEAR,year);
                fechaFin.set(Calendar.MONTH,month);
                fechaFin.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        fechaFin.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        fechaFin.set(Calendar.MINUTE,minute);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                        campoFechaFin.setText(dateFormat.format(fechaFin.getTime()));
                    }
                };
                new TimePickerDialog(PantallaModificarEvento.this,timeSetListener,
                        fechaFin.get(Calendar.HOUR_OF_DAY),fechaFin.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(PantallaModificarEvento.this,dateSetListener,
                fechaFin.get(Calendar.YEAR),fechaFin.get(Calendar.MONTH),
                fechaFin.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void volverPantallaHorario(){
        Intent intent = new Intent(this,PantallaHorario.class);
        startActivity(intent);
    }
    private void iniciarAtributos(){
        botonModificarEvento = (Button) findViewById(R.id.botonModificarEvento);
        botonEliminarEvento = (Button) findViewById(R.id.botonEliminarEvento);
        profesor = (TextView) findViewById(R.id.textView14);
        errores = (TextView) findViewById(R.id.textoErroresModificarEvento);
        descripcion = (TextView) findViewById(R.id.textView15);
        salon = (TextView) findViewById(R.id.textView16);
        campoNombre = (EditText) findViewById(R.id.campoModificarNombreEvento);
        campoFechaInicio = (EditText) findViewById(R.id.campoModificarFechaInicio);
        campoFechaFin = (EditText) findViewById(R.id.campoModificarFechaFin);
        campoProfesor = (EditText) findViewById(R.id.campoModificarProfesorEvento);
        campoSalon = (EditText) findViewById(R.id.campoMOdificarSalonEvento);
        campoDescripcion = (EditText) findViewById(R.id.campoModificarDescripcionEvento);
        evento = (Evento) getIntent().getSerializableExtra("Evento");
        controlHorario = new ControlHorario();
    }
}
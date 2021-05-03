package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.nio.file.ProviderNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import EZCode.Entidades.Actividad;
import EZCode.Entidades.Clase;
import EZCode.Entidades.Evento;
import EZCode.Horario.ControlHorario;

public class PantallaAgregarEvento extends AppCompatActivity {

    EditText campoNombre;
    EditText campoFechaInicial;
    EditText campoFechaFin;
    EditText campoDescripcion;
    EditText campoProfesor;
    EditText campoSalon;
    TextView error;
    Calendar fechaInicio;
    Calendar fechaFin;
    Switch esClase;
    Button botonAgregarEvento;
    ControlHorario controlHorario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_evento);

        iniciarAtributos();

        esClase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    campoDescripcion.setVisibility(View.INVISIBLE);
                    campoProfesor.setVisibility(View.VISIBLE);
                    campoSalon.setVisibility(View.VISIBLE);
                }
                else {
                    campoDescripcion.setVisibility(View.VISIBLE);
                    campoProfesor.setVisibility(View.INVISIBLE);
                    campoSalon.setVisibility(View.INVISIBLE);
                }
            }
        });

        campoFechaInicial.setOnClickListener(new View.OnClickListener() {
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
        botonAgregarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificarCampos()) {
                    error.setText("Verifique que todos los campos estén corrctamente llenos");
                    return;
                }
                String nombre = campoNombre.getText().toString();
                if(controlHorario.verificarFecha(fechaInicio,fechaFin)){
                    error.setText("Las fechas son incorrectas");
                    return;
                }
                if(esClase.isChecked()){
                    String profesor, salon;
                    profesor = campoProfesor.getText().toString();
                    salon = campoSalon.getText().toString();
                    Evento evento = new Clase(fechaInicio,fechaFin,nombre,profesor,salon);
                    controlHorario.agregarEvento(evento);
                }
                else{
                    String desc = campoDescripcion.getText().toString();
                    Evento evento = new Actividad(fechaInicio,fechaFin,nombre,desc);
                    controlHorario.agregarEvento(evento);
                }
                volverPantallaHorario();
            }
        });
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
                        campoFechaInicial.setText(dateFormat.format(fechaInicio.getTime()));
                    }
                };
                new TimePickerDialog(PantallaAgregarEvento.this,timeSetListener,
                        fechaInicio.get(Calendar.HOUR_OF_DAY),fechaInicio.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(PantallaAgregarEvento.this,dateSetListener,
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
                new TimePickerDialog(PantallaAgregarEvento.this,timeSetListener,
                        fechaFin.get(Calendar.HOUR_OF_DAY),fechaFin.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(PantallaAgregarEvento.this,dateSetListener,
                fechaFin.get(Calendar.YEAR),fechaFin.get(Calendar.MONTH),
                fechaFin.get(Calendar.DAY_OF_MONTH)).show();
    }
    private boolean verificarCampos(){
        if(campoNombre.getText().toString() == "" || campoFechaInicial.getText().toString() == "" || campoFechaFin.getText().toString() == "")
            return false;
        if(esClase.isChecked() && (campoProfesor.getText().toString() == "" || campoSalon.getText().toString() == ""))
            return false;
        if(!esClase.isChecked() && campoDescripcion.getText().toString() == "")
            return false;
        return true;
    }
    private void iniciarAtributos(){
        controlHorario = new ControlHorario();
        campoNombre = (EditText) findViewById(R.id.campoNombreEvento);
        campoFechaInicial = (EditText) findViewById(R.id.textoFechaInicio);
        campoFechaFin = (EditText) findViewById(R.id.textoFechaFin);
        campoDescripcion = (EditText) findViewById(R.id.campoDescripcionActividad);
        campoProfesor = (EditText) findViewById(R.id.campoProfesorClase);
        campoSalon = (EditText) findViewById(R.id.campoSalonClase);
        error = (TextView) findViewById(R.id.textoErrorEvento);
        esClase = (Switch) findViewById(R.id.switchTipoEvento);
        botonAgregarEvento = (Button) findViewById(R.id.botonAgregarEvento);
        campoProfesor.setVisibility(View.INVISIBLE);
        campoSalon.setVisibility(View.INVISIBLE);
    }
    private void volverPantallaHorario(){
        Intent intent = new Intent(this,PantallaHorario.class);
        startActivity(intent);
    }
}
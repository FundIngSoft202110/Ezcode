package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.nio.file.ProviderNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    TextView textoRepeticiones;
    TextView error;
    Calendar fechaInicio;
    Calendar fechaFin;
    Switch esClase;
    Button repeticionEvento;
    Button botonAgregarEvento;
    ControlHorario controlHorario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_evento);

        iniciarAtributos();

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
        repeticionEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoRepeticion();
            }
        });
    }

    private void mostrarDialogoRepeticion(){
        final Dialog dialogoRepeticion = new Dialog(this);
        dialogoRepeticion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoRepeticion.setCancelable(true);
        dialogoRepeticion.setContentView(R.layout.dialogo_repeticion_evento);

        String texto = "";
        EditText numRepeticiones = (EditText) dialogoRepeticion.findViewById(R.id.campoNumeroRepeticionesEvento);
        TextView errorRepeticion = (TextView) dialogoRepeticion.findViewById(R.id.textoErrorRepeticion);
        CheckBox lunes = (CheckBox) dialogoRepeticion.findViewById(R.id.checkLunes);
        CheckBox martes = (CheckBox) dialogoRepeticion.findViewById(R.id.checkMartes);
        CheckBox miercoles = (CheckBox) dialogoRepeticion.findViewById(R.id.checkMiercoles);
        CheckBox jueves = (CheckBox) dialogoRepeticion.findViewById(R.id.checkJueves);
        CheckBox viernes = (CheckBox) dialogoRepeticion.findViewById(R.id.cehckViernes);
        CheckBox sabado = (CheckBox) dialogoRepeticion.findViewById(R.id.checkSabado);
        CheckBox domingo = (CheckBox) dialogoRepeticion.findViewById(R.id.checkDomingo);
        Button confirmar = (Button) dialogoRepeticion.findViewById(R.id.botonConfirmarRepeticion);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numRepeticiones.getText().toString().equals(""))
                    errorRepeticion.setText("Seleccione el número de repeticiones del evento");
                else {
                    if(lunes.isChecked())
                    if (lunes.isChecked())
                        texto.concat("lunes");
                    if (martes.isChecked())
                        texto.concat("martes");
                    if (miercoles.isChecked())
                        texto.concat("miercoles");
                    if (jueves.isChecked())
                        texto.concat("jueves");
                    if (viernes.isChecked())
                        texto.concat("viernes");
                    if (sabado.isChecked())
                        texto.concat("sabado");
                    if (domingo.isChecked())
                        texto.concat("domingo");
                    Log.d("Dias", texto);
                    //mostrarRepeticiones(texto);
                    dialogoRepeticion.dismiss();
                }
            }
        });
        dialogoRepeticion.show();;
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
        repeticionEvento = (Button) findViewById(R.id.botonRepeticionEvento);
        textoRepeticiones = (TextView) findViewById(R.id.textoRepeticion);
    }
    private void volverPantallaHorario(){
        Intent intent = new Intent(this,PantallaHorario.class);
        startActivity(intent);
    }
    private void mostrarRepeticiones(String dias){
        error.setText(dias);
    }
}
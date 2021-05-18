package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import EZCode.Entidades.Actividad;
import EZCode.Entidades.Clase;
import EZCode.Entidades.Evento;
import EZCode.Controladores.ControlHorario;

public class PantallaAgregarEvento extends AppCompatActivity {

    EditText campoNombre;
    EditText campoFechaInicial;
    EditText campoFechaFin;
    EditText campoDescripcion;
    EditText campoProfesor;
    EditText campoSalon;
    TextView textoRepeticiones;
    Calendar fechaInicio;
    Calendar fechaFin;
    Switch esClase;
    Button repeticionEvento;
    Button botonAgregarEvento;
    ControlHorario controlHorario;
    List<String> repeticiones = new ArrayList<>();
    int cantRepeticiones = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_evento);

        iniciarAtributos();

        botonAgregarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verificarCampos()) {
                    Toast.makeText(getApplicationContext(),
                            "Verifique que todos los campos estén corrctamente llenos",Toast.LENGTH_SHORT).show();
                    return;
                }
                String nombre = campoNombre.getText().toString();
                if(controlHorario.verificarFecha(fechaInicio,fechaFin)){
                    Toast.makeText(getApplicationContext(),
                            "Las fechas son incorrectas",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(esClase.isChecked()){
                    String profesor, salon;
                    profesor = campoProfesor.getText().toString();
                    salon = campoSalon.getText().toString();
                    Evento evento = new Clase(fechaInicio,fechaFin,nombre,profesor,salon);
                    controlHorario.agregarEvento(evento, repeticiones,cantRepeticiones);
                }
                else{
                    String desc = campoDescripcion.getText().toString();
                    Evento evento = new Actividad(fechaInicio,fechaFin,nombre,desc);
                    controlHorario.agregarEvento(evento, repeticiones, cantRepeticiones);
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

        EditText numRepeticiones = (EditText) dialogoRepeticion.findViewById(R.id.campoNumeroRepeticionesEvento);
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
                    Toast.makeText(getApplicationContext(),
                            "Por favor ingrese la cantidad de veces que el evento se repite",Toast.LENGTH_SHORT).show();
                else {
                    if (lunes.isChecked())
                        repeticiones.add("lunes");
                    if (martes.isChecked())
                        repeticiones.add("martes");
                    if (miercoles.isChecked())
                        repeticiones.add("miercoles");
                    if (jueves.isChecked())
                        repeticiones.add("jueves");
                    if (viernes.isChecked())
                        repeticiones.add("viernes");
                    if (sabado.isChecked())
                        repeticiones.add("sabado");
                    if (domingo.isChecked())
                        repeticiones.add("domingo");
                    textoRepeticiones.setText("El evento se repetirá los días" + repeticiones.toString());
                    cantRepeticiones = Integer.parseInt(numRepeticiones.getText().toString());
                    dialogoRepeticion.dismiss();
                }
            }
        });
        dialogoRepeticion.show();
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
        if(campoNombre.getText().toString().equals("") || campoFechaInicial.getText().toString().equals("") || campoFechaFin.getText().toString().equals(""))
            return false;
        if(esClase.isChecked() && (campoProfesor.getText().toString().equals("") || campoSalon.getText().toString().equals("")))
            return false;
        if(!esClase.isChecked() && campoDescripcion.getText().toString().equals(""))
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
}
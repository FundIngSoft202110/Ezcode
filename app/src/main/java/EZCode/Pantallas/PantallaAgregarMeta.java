package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import EZCode.Entidades.Estudiante;
import EZCode.Entidades.Meta;
import EZCode.Controladores.ControlMetas;


public class PantallaAgregarMeta extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText campoNombre;
    EditText campoDescripcion;
    Spinner spinnerPrioridad;
    Button botonAgregarMeta;
    Button botonCancelar;
    Calendar fechaInicio;
    TextView errores;
    String Id = PantallaAutenticacion.autenticacion.getCurrentUser().getUid();

    private ControlMetas controlMetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_meta);

        inicializarAtributos();
        iniciarSpinner();

        //fechaInicio.set(Calendar.DAY_OF_WEEK,  Estudiante.getInstance().getInicio().getFirstDayOfWeek());









        botonAgregarMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verificarCampos()){
                    errores.setText("Verifique que todos los campos estén llenos");
                    return;
                }
                String nombre, descripcion;
                int prioridad = Integer.parseInt(spinnerPrioridad.getSelectedItem().toString());
                nombre = campoNombre.getText().toString();
                descripcion = campoDescripcion.getText().toString();

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String strDate = dateFormat.format(date).toString();

                Meta meta = new Meta(nombre, descripcion, prioridad,0,strDate);

                PantallaAutenticacion.data.child("Estudiantes").child(Id).child("Metas").child(nombre).setValue(meta);
                Toast.makeText(PantallaAgregarMeta.this, "Se agregó una meta", Toast.LENGTH_SHORT).show();





                fechaInicio = Calendar.getInstance();
                /*fechaInicio.set(Calendar.HOUR_OF_DAY, 0);
                fechaInicio.clear(Calendar.MINUTE);
                fechaInicio.clear(Calendar.SECOND);
                fechaInicio.clear(Calendar.MILLISECOND);
                fechaInicio.add(Calendar.DAY_OF_WEEK,2);*/
                fechaInicio.add(Calendar.MINUTE,1);

                Intent intent = new Intent(getApplicationContext(), AlarmaMetaEvento.class);
                intent.putExtra("message", nombre);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, fechaInicio.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pi);
                //alarmManager.set(AlarmManager.RTC_WAKEUP,fechaInicio.getTimeInMillis(),pi);
                Toast.makeText(PantallaAgregarMeta.this, fechaInicio.toString(), Toast.LENGTH_SHORT).show();



                volverPantallaMetas();

            }
        });
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverPantallaMetas();
            }
        });
    }
    private boolean verificarCampos(){
        if(campoNombre.getText().toString().matches("") || campoDescripcion.getText().toString().matches(""))
            return false;
        return true;
    }

    private void volverPantallaMetas(){
        Intent intent = new Intent(this, PantallaMetas.class);
        startActivity(intent);
    }
    private void iniciarSpinner(){
        spinnerPrioridad.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.prioridades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrioridad.setAdapter(adapter);
    }
    private void inicializarAtributos(){
        campoNombre = (EditText) findViewById(R.id.campoNombreMeta);
        campoDescripcion = (EditText) findViewById(R.id.campoDescripcionMeta);
        spinnerPrioridad = (Spinner) findViewById(R.id.spinnerPrioridad);
        botonAgregarMeta = (Button) findViewById(R.id.botonAgregarMeta);
        botonCancelar = (Button) findViewById(R.id.botonCancelarMetaNueva);
        errores = (TextView) findViewById(R.id.textoErrorMeta);
        controlMetas = new ControlMetas();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
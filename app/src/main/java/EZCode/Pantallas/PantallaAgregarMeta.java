package main.java.EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

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

import main.java.EZCode.Entidades.Meta;
import main.java.EZCode.Controladores.ControlMetas;


public class PantallaAgregarMeta extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText campoNombre;
    EditText campoDescripcion;
    Spinner spinnerPrioridad;
    Button botonAgregarMeta;
    Button botonCancelar;
    TextView errores;
    String Id = PantallaAutenticacion.autenticacion.getCurrentUser().getUid();

    private ControlMetas controlMetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_meta);

        inicializarAtributos();
        iniciarSpinner();

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
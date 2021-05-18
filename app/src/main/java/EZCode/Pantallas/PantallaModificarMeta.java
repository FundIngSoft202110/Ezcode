package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import EZCode.Entidades.Estudiante;
import EZCode.Entidades.Meta;
import EZCode.Controladores.ControlMetas;

public class PantallaModificarMeta extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Meta metaModificar;
    int indice;
    TextView errores;
    EditText nombre;
    EditText descripcion;
    Spinner prioridad;
    EditText progreso;
    Button botonConfirmarCambios;
    Button botonEliminarMeta;
    ProgressBar barraProgreso;
    private ControlMetas controlMetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_modificar_meta);

        iniciarAtributos();
        iniciarSpinner();

        botonConfirmarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verificarCampos()) {
                    errores.setText("Verifique que todos los campos estén llenos");
                    return;
                }
                String name,desc;
                int prio,prog;
                name = nombre.getText().toString();
                desc = descripcion.getText().toString();
                prio = Integer.parseInt(prioridad.getSelectedItem().toString());
                prog = Integer.parseInt(progreso.getText().toString());
                Meta m = new Meta(name,desc,prio,prog,metaModificar.getFechaInicio());
                Log.d("PantallaModificarMeta", Estudiante.getInstance().getMetas().toString());
                controlMetas.modificarMeta(m,indice);
                volverPantallaMetas();
            }
        });
        botonEliminarMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlMetas.eliminarMeta(indice);
                volverPantallaMetas();
            }
        });

    }
    private void volverPantallaMetas(){
        Intent intent = new Intent(this,PantallaMetas.class);
        startActivity(intent);
    }
    private void iniciarSpinner(){
        prioridad.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.prioridades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioridad.setAdapter(adapter);
    }
    private boolean verificarCampos(){
        if(nombre.getText().toString().matches("") || descripcion.getText().toString().matches(""))
            return false;
        return true;
    }
    private void iniciarAtributos(){
        metaModificar = (Meta) getIntent().getSerializableExtra("Meta");
        indice = (int) getIntent().getSerializableExtra("indice");
        errores = (TextView) findViewById(R.id.erroresModificarMeta);
        nombre = (EditText) findViewById(R.id.nuevoNombreMeta);
        nombre.setText(metaModificar.getNombre());
        descripcion = (EditText) findViewById(R.id.nuevaDescripcionMeta);
        descripcion.setText(metaModificar.getDescripcion());
        prioridad = (Spinner) findViewById(R.id.spinnerModificarPrioridad);
        progreso = (EditText) findViewById(R.id.nuevoProgresoMeta);
        progreso.setText(String.valueOf(metaModificar.getProgreso()));
        barraProgreso = (ProgressBar) findViewById(R.id.progresoMeta);
        barraProgreso.setProgress(metaModificar.getProgreso());
        botonConfirmarCambios = (Button) findViewById(R.id.botonConfirmarMeta);
        botonEliminarMeta = (Button) findViewById(R.id.botonBorrarMeta);
        controlMetas = new ControlMetas();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
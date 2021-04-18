package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;

import EZCode.Entidades.Estudiante;
import EZCode.Entidades.Meta;
import EZCode.Metas.ControlMetas;

public class PantallaModificarMeta extends AppCompatActivity {

    Meta metaModificar;
    int indice;
    EditText nombre;
    EditText descripcion;
    EditText prioridad;
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

        botonConfirmarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,desc;
                int prio,prog;
                name = nombre.getText().toString();
                desc = descripcion.getText().toString();
                prio = Integer.parseInt(prioridad.getText().toString());
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

    private void iniciarAtributos(){
        metaModificar = (Meta) getIntent().getSerializableExtra("Meta");
        indice = (int) getIntent().getSerializableExtra("indice");
        nombre = (EditText) findViewById(R.id.nuevoNombreMeta);
        nombre.setText(metaModificar.getNombre());
        descripcion = (EditText) findViewById(R.id.nuevaDescripcionMeta);
        descripcion.setText(metaModificar.getDescripcion());
        prioridad = (EditText) findViewById(R.id.nuevaPrioridadMeta);
        prioridad.setText(String.valueOf(metaModificar.getPrioridad()));
        progreso = (EditText) findViewById(R.id.nuevoProgresoMeta);
        progreso.setText(String.valueOf(metaModificar.getProgreso()));
        barraProgreso = (ProgressBar) findViewById(R.id.progresoMeta);
        barraProgreso.setProgress(metaModificar.getProgreso());
        botonConfirmarCambios = (Button) findViewById(R.id.botonConfirmarMeta);
        botonEliminarMeta = (Button) findViewById(R.id.botonBorrarMeta);
        controlMetas = new ControlMetas();
    }
}
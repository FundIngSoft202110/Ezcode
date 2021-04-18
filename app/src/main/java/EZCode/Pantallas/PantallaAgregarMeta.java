package EZCode.Pantallas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.controls.Control;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.Calendar;

import EZCode.Entidades.Estudiante;
import EZCode.Entidades.Meta;
import EZCode.Metas.ControlMetas;

public class PantallaAgregarMeta extends AppCompatActivity {

    EditText campoNombre;
    EditText campoDescripcion;
    EditText campoPrioridad;
    Button botonAgregarMeta;
    Button botonCancelar;
    TextView errores;
    private ControlMetas controlMetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_meta);

        inicializarAtributos();

        botonAgregarMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre, descripcion;
                int prioridad;
                nombre = campoNombre.getText().toString();
                descripcion = campoDescripcion.getText().toString();
                prioridad = Integer.parseInt(campoPrioridad.getText().toString());
                Meta meta = new Meta(nombre, descripcion, prioridad, 0, Calendar.getInstance().getTime());
                controlMetas.agregarMeta(meta);
                //mostrarMeta.setText(meta.toString());
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

    private void volverPantallaMetas(){
        Intent intent = new Intent(this, PantallaMetas.class);
        startActivity(intent);
    }
    private void inicializarAtributos(){
        campoNombre = (EditText) findViewById(R.id.campoNombreMeta);
        campoDescripcion = (EditText) findViewById(R.id.campoDescripcionMeta);
        campoPrioridad = (EditText) findViewById(R.id.campoPrioridadMeta);
        botonAgregarMeta = (Button) findViewById(R.id.botonAgregarMeta);
        botonCancelar = (Button) findViewById(R.id.botonCancelarMetaNueva);
        errores = (TextView) findViewById(R.id.textoErrorMeta);
        controlMetas = new ControlMetas();
    }
}
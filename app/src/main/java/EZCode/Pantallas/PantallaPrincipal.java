package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import EZCode.Entidades.Estudiante;
import EZCode.Horario.ControlHorario;
import EZCode.Metas.ControlMetas;

public class PantallaPrincipal extends AppCompatActivity {
    private Button botonMetas;
    private Button botonHorario;
    private Estudiante estudiante;
    private TextView nombre;
    private ControlHorario cHorario = new ControlHorario(estudiante);
    private ControlMetas cMetasM = new ControlMetas(estudiante);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        botonMetas = (Button) findViewById(R.id.botonMetas);
        botonHorario = (Button) findViewById(R.id.botonHorario);
        nombre = (TextView) findViewById(R.id.textoNombre);
        this.estudiante = (Estudiante) getIntent().getSerializableExtra("Estudiante");

        nombre.setText("Hola " + estudiante.getNombre());
        botonMetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPantallaMetas();
            }
        });
        botonHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPantallaHorario();
            }
        });
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    void abrirPantallaMetas(){
        Intent intent = new Intent(this, PantallaMetas.class);
        intent.putExtra("Estudiante", this.estudiante);
        startActivity(intent);
    }
    void abrirPantallaHorario(){
        Intent intent = new Intent(this, PantallaHorario.class);
        intent.putExtra("Estudiante", this.estudiante);
        startActivity(intent);
    }
}
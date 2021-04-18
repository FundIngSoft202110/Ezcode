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
    private TextView nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        botonMetas = (Button) findViewById(R.id.botonMetas);
        botonHorario = (Button) findViewById(R.id.botonHorario);
        nombre = (TextView) findViewById(R.id.textoNombre);

        nombre.setText("Hola " + Estudiante.getInstance().getNombre());
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


    void abrirPantallaMetas(){
        Intent intent = new Intent(this, PantallaMetas.class);
        startActivity(intent);
    }
    void abrirPantallaHorario(){
        Intent intent = new Intent(this, PantallaHorario.class);
        startActivity(intent);
    }
}
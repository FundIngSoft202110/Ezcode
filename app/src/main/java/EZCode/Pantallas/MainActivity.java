package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botonIniciarSesion;
    private Button botonRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonIniciarSesion = (Button) findViewById(R.id.botonIngresar);
        botonRegistrarse = (Button) findViewById(R.id.botonRegistrarse);

        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPantallaInicioSesion();
            }
        });
        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPantallaRegistro();
            }
        });

    }
    private void abrirPantallaInicioSesion(){
        Intent intent = new Intent(this,PantallaAutenticacion.class);
        startActivity(intent);
    }
    private void abrirPantallaRegistro(){
        Intent intent = new Intent(this,PantallaRegistro.class);
        startActivity(intent);
    }
}
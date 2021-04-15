package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PantallaAutenticacion extends AppCompatActivity {
    private Button botonInicio;
    private TextView error;
    private EditText usuario;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_autenticacion);

        botonInicio = (Button) findViewById(R.id.botonInicioSesion);
        usuario =  (EditText) findViewById(R.id.campoCorreo);
        password = (EditText) findViewById(R.id.campoPassword);
        error = (TextView) findViewById(R.id.Errores);
        botonInicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(autenticarUsuario())
                    abrirPantallaPrincipal();
                else
                    error.setText("El usuario y contraseña son incorrectos");
            }
        });
    }
    public boolean autenticarUsuario(){
        if(usuario.getText().toString().equals("hola@esto.com") &&
                password.getText().toString().equals("12345"))
            return true;
        return false;
    }
    public void abrirPantallaPrincipal(){
        Intent intent = new Intent(this, PantallaPrincipal.class);
        startActivity(intent);
    }
}
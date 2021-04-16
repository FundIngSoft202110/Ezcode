package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

import EZCode.Entidades.Estudiante;


public class PantallaAutenticacion extends AppCompatActivity {

    private Button botonInicio;
    private TextView error;
    private EditText usuario;
    private EditText password;
    private Estudiante estudiante;
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
                if(autenticarUsuario()) {
                    abrirPantallaPrincipal();
                }
                else
                    error.setText("El usuario y contraseña son incorrectos");
            }
        });
    }
    public boolean autenticarUsuario(){
        if(usuario.getText().toString().equals("hola@correo.com") &&
                password.getText().toString().equals("12345")) {
            this.estudiante = obtenerEstudiante("temp","temp");
            return true;
        }
        return false;
    }
    /*Este metodo deberia obtener TODA la informacion de el estudiante que inicio sesion
      desde la BD y crear una instancia de tipo Estudiante y retornarla.
     */
    public Estudiante obtenerEstudiante(String correo, String Password){
        return new Estudiante("Daniel","hola@correo.com","12345",1,0);
    }
    public void abrirPantallaPrincipal(){
        Intent intent = new Intent(this, PantallaPrincipal.class);
        intent.putExtra("Estudiante", this.estudiante);
        startActivity(intent);
    }
}
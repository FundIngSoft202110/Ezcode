package EZCode.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

import EZCode.Autenticacion.ControlAutenticacion;
import EZCode.Entidades.Estudiante;


public class PantallaAutenticacion extends AppCompatActivity {

    Button botonInicio;
    Button botonRegistro;
    TextView error;
    EditText usuario;
    EditText password;

    String email ="";
    String contraseña ="";
    FirebaseAuth autenticacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_autenticacion);

        botonInicio = (Button) findViewById(R.id.botonInicioSesion);
        botonRegistro = (Button) findViewById(R.id.botonRegistro);
        usuario =  (EditText) findViewById(R.id.campoCorreo);
        password = (EditText) findViewById(R.id.campoPassword);
        error = (TextView) findViewById(R.id.Errores);
        autenticacion=FirebaseAuth.getInstance();

        botonInicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                email=usuario.getText().toString();
                contraseña=password.getText().toString();

                if(!email.isEmpty() && !contraseña.isEmpty()){
                    loginUser();
                }
                else {
                    Toast.makeText(PantallaAutenticacion.this, "Por Favor complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { abrirPantallaRegistro(); }
        });
    }
    /*
    Esta autenticacion se debera realizar desde el controlador
     */
    public boolean autenticarUsuario(){
        if(usuario.getText().toString().equals("hola@correo.com") &&
                password.getText().toString().equals("12345")) {
            Estudiante.setInstance(obtenerEstudiante("temp","temp"));
            return true;
        }
        return false;
    }
    /*Este metodo deberia obtener TODA la informacion de el estudiante que inicio sesion
      desde la BD y crear una instancia de tipo Estudiante y retornarla.
     */
    public Estudiante obtenerEstudiante(String correo, String Password){
        return new Estudiante("Daniel","hola@correo.com","12345","",0);
    }
    public void abrirPantallaPrincipal(){
        Intent intent = new Intent(this, PantallaHorario.class);
        startActivity(intent);
    }
    public void abrirPantallaRegistro(){
        Intent intent = new Intent(this, PantallaRegistro.class);
        startActivity(intent);
    }

    private void loginUser(){
        autenticacion.signInWithEmailAndPassword(email,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirPantallaPrincipal();
                    //finish();
                }
                else {
                    Toast.makeText(PantallaAutenticacion.this,
                            "El usuario y contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(autenticacion.getCurrentUser() != null){
            abrirPantallaPrincipal();
            finish();
        }
    }

}
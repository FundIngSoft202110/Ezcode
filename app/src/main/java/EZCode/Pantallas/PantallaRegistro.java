package EZCode.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import EZCode.Entidades.Estudiante;


public class PantallaRegistro extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private Button registrar;

    String nombre = "";
    String correo="";
    String clave="";
    String EZpuntos="0";

    FirebaseAuth autenticacion;
    DatabaseReference data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro);
        autenticacion = FirebaseAuth.getInstance();
        data= FirebaseDatabase.getInstance().getReference();
        name= (EditText) findViewById(R.id.NombreRegistro);
        email= (EditText) findViewById(R.id.CorreoRegistro);
        password= (EditText) findViewById(R.id.ContraseñaRegistro);
        registrar=(Button) findViewById(R.id.BttRegistrame);
        registrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nombre= name.getText().toString();
                correo= email.getText().toString();
                clave= password.getText().toString();

                if(!nombre.isEmpty() && !correo.isEmpty() && !clave.isEmpty()){
                    if(clave.length()>=6){
                        registerUser();
                    }
                    else{
                        Toast.makeText(PantallaRegistro.this, "La contraseña debe contener minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(PantallaRegistro.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void inicio (){

        Intent intent = new Intent(this,PantallaHorario.class);
        startActivity(intent);
    }
    private void registerUser(){


        autenticacion.createUserWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String id = Objects.requireNonNull(autenticacion.getCurrentUser()).getUid();
                    Estudiante est= new Estudiante(nombre,correo,clave,0);

                    data.child("Estudiantes").child(id).setValue(est).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull  Task<Void> task2) {
                            if(task2.isSuccessful()){
                               inicio();
                               Toast.makeText(PantallaRegistro.this, "Registro Exitoso!", Toast.LENGTH_SHORT).show();
                               finish();
                            }
                            else{
                                Toast.makeText(PantallaRegistro.this,"Ocurrio un error en el registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                   Toast.makeText(PantallaRegistro.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}
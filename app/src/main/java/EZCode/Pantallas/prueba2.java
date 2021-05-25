package EZCode.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import EZCode.Controladores.ControlMetas;
import EZCode.Entidades.Meta;

public class prueba2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Meta meta;
    int indice;
    TextView errores;
    EditText nombre;
    EditText descripcion;
    Spinner prioridad;
    EditText progreso;
    Button botonConfirmarCambios;
    Button botonEliminarMeta;
    ProgressBar barraProgreso;
    FirebaseAuth autenticacion = FirebaseAuth.getInstance();
    String id;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba2);
        inicializar();
        cargardt();
        iniciarSpinner();


        botonConfirmarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               actualizar();
               volverPantallaMetas();



            }
        });

        botonEliminarMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
                volverPantallaMetas();


            }
        });

    }

    private void  cargardt(){
        meta= (Meta) getIntent().getExtras().getSerializable("item");
        PantallaAutenticacion.data.child("Estudiantes").child(id).child("Metas").child(meta.getNombre()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    nombre.setText(meta.getNombre());
                    descripcion.setText(snapshot.child("descripcion").getValue().toString());
                    progreso.setText(snapshot.child("progreso").getValue().toString());
                }
                else { volverPantallaMetas();}
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

    }
    private void volverPantallaMetas(){
        Intent intent = new Intent(getApplicationContext(), PantallaMetas.class);
        startActivity(intent);
    }

    private void iniciarSpinner(){
        prioridad.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.prioridades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioridad.setAdapter(adapter);
    }
    void inicializar(){
        id=autenticacion.getCurrentUser().getUid();
        errores = (TextView) findViewById(R.id.erroresModificarMeta);
        nombre = (EditText) findViewById(R.id.nuevoNombreMeta);
        descripcion = (EditText) findViewById(R.id.nuevaDescripcionMeta);
        prioridad = (Spinner) findViewById(R.id.spinnerModificarPrioridad);
        progreso = (EditText) findViewById(R.id.nuevoProgresoMeta);
        barraProgreso = (ProgressBar) findViewById(R.id.progresoMeta);
        botonConfirmarCambios = (Button) findViewById(R.id.botonConfirmarMeta);
        botonEliminarMeta = (Button) findViewById(R.id.botonBorrarMeta);

    }


    public void eliminar(){

        PantallaAutenticacion.data.child("Estudiantes").child(id).child("Metas").child(meta.getNombre()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(prueba2.this, "Se elimino correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void actualizar(){

        Meta act = new Meta();
        act.setNombre(nombre.getText().toString());
        act.setDescripcion(descripcion.getText().toString());
        act.setPrioridad(Integer.parseInt(prioridad.getSelectedItem().toString()));
        act.setProgreso(Integer.parseInt(progreso.getText().toString()));

        PantallaAutenticacion.data.child("Estudiantes").child(id).child("Metas").child(meta.getNombre()).setValue(act).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(prueba2.this, "Actualizacion exitosa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
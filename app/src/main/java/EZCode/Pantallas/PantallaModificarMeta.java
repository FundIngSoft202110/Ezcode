package main.java.EZCode.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import main.java.EZCode.Entidades.Meta;

public class PantallaModificarMeta extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Meta meta;
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
    Integer puntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_modificar_meta);

        inicializar();
        cargardt();
        iniciarSpinner();


        botonConfirmarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!verificarCampos()) {
                    Toast.makeText(PantallaModificarMeta.this, "Verifique que todos los campos esten llenos", Toast.LENGTH_SHORT).show();
                    return;
                }
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
        PantallaAutenticacion.data.child("Estudiantes").child(id).child("Metas").child(meta.getNombre()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    nombre.setText(meta.getNombre());
                    descripcion.setText(snapshot.child("descripcion").getValue().toString());
                    progreso.setText(snapshot.child("progreso").getValue().toString());
                    prioridad.setSelection(Integer.parseInt(snapshot.child("prioridad").getValue().toString())-1);
                    barraProgreso.setProgress(Integer.parseInt(snapshot.child("progreso").getValue().toString()));
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
        meta= (Meta) getIntent().getExtras().getSerializable("item");
        barraProgreso.setProgress(meta.getProgreso());
    }

    public void eliminar(){

        PantallaAutenticacion.data.child("Estudiantes").child(id).child("Metas").child(meta.getNombre()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(PantallaModificarMeta.this, "Se elimino correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void actualizar() {

        Meta act = new Meta();
        act.setNombre(nombre.getText().toString());
        act.setDescripcion(descripcion.getText().toString());
        act.setPrioridad(Integer.parseInt(prioridad.getSelectedItem().toString()));

        if (Integer.parseInt(progreso.getText().toString()) <= 99) {
            act.setProgreso(Integer.parseInt(progreso.getText().toString()));
            barraProgreso.setProgress(Integer.parseInt(String.valueOf(progreso.getText().toString())));

            PantallaAutenticacion.data.child("Estudiantes").child(id).child("Metas").child(meta.getNombre()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        PantallaAutenticacion.data.child("Estudiantes").child(id).child("Metas").child(act.getNombre()).setValue(act);
                        Toast.makeText(getApplicationContext(), "Actualizacion exitosa", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No se puede actualizar ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else if (Integer.parseInt(progreso.getText().toString()) ==100){
            finalizado();
        }
        else {
            Toast.makeText(this, "Progreso no valido", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private boolean verificarCampos(){
        if(nombre.getText().toString().matches("") || descripcion.getText().toString().matches(""))
            return false;
        return true;
    }
    public void finalizado() {

        PantallaAutenticacion.data.child("Estudiantes").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                puntos = snapshot.child("ezpuntos").getValue(Integer.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        PantallaAutenticacion.data.child("Estudiantes").child(id).child("ezpuntos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PantallaAutenticacion.data.child("Estudiantes").child(id).child("ezpuntos").setValue(puntos+10);
                Toast.makeText(getApplicationContext(), "Meta Finalizada!!", Toast.LENGTH_SHORT).show();
                eliminar();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
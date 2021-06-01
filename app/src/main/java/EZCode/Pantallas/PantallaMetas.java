package main.java.EZCode.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import main.java.EZCode.Entidades.Meta;

public class PantallaMetas extends AppCompatActivity {

    private ImageButton newMeta;
    private Button volver;
    private ListaAdapter Madapter;
    private RecyclerView Mrecycler;
    private ArrayList<Meta> Metas = new ArrayList<Meta>();
    TextView EZPuntos;
    String id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_metas);

        newMeta= (ImageButton) findViewById(R.id.botonNuevaMeta);
        volver = (Button) findViewById(R.id.botonVolverPantallaPrincipal);
        Mrecycler=(RecyclerView) findViewById(R.id.listmetas);
        EZPuntos = (TextView) findViewById(R.id.textoEZPuntos);

        Mrecycler.setLayoutManager(new LinearLayoutManager(this));

        newMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPantallaAgregarMeta();
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverPantallaPrincipal();
            }
        });
        getnombre();

    }
    private void abrirPantallaAgregarMeta(){
        Intent intent = new Intent(this, PantallaAgregarMeta.class);
        startActivity(intent);
    }

    private void volverPantallaPrincipal(){
        Intent intent = new Intent(this, PantallaHorario.class);
        startActivity(intent);
    }

    private void getnombre (){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        id=auth.getCurrentUser().getUid();

        PantallaAutenticacion.data.child("Estudiantes").child(auth.getCurrentUser().getUid()).child("Metas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Metas.clear();
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        String nombre= ds.child("nombre").getValue().toString();
                        Metas.add(new Meta(nombre));
                    }
                    Madapter = new ListaAdapter(Metas,R.layout.vista_metas);
                    Mrecycler.setAdapter(Madapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        PantallaAutenticacion.data.child("Estudiantes").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String p = snapshot.child("ezpuntos").getValue().toString();
                EZPuntos.setText("Tus EZPuntos son: " + p);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
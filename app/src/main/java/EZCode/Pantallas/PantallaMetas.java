package EZCode.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import EZCode.Entidades.Meta;

public class PantallaMetas extends AppCompatActivity {

    private Button newMeta;
    private Button volver;
    private ListaAdapter Madapter;
    private RecyclerView Mrecycler;
    private ArrayList<Meta> Metas = new ArrayList<Meta>();
    String id="";
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_metas);

        newMeta= (Button) findViewById(R.id.botonnuevameta);
        volver = (Button) findViewById(R.id.botonvolver);
        Mrecycler=(RecyclerView) findViewById(R.id.listmetas);

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
        //Metas.clear();
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
    }
}
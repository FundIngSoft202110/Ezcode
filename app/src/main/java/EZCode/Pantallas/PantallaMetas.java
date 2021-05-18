package EZCode.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import EZCode.Entidades.Estudiante;
import EZCode.Entidades.Meta;

public class PantallaMetas extends AppCompatActivity {

    Button botonNuevarMeta;
    ListView listaMetas;
    TextView errores;
    Button botonVolver;
    ArrayList<String> metas;
    ArrayAdapter arrayadapter;
    String meta;


    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_metas);

        inicializarAtributos();
        iniciarLista();

        database= FirebaseDatabase.getInstance().getReference();

        if(Estudiante.getInstance().getMetas().size() == 0)
            errores.setText("Parece que aun no tienes metas. Presiona el botón \"Agregar Meta\" para crear una meta nueva");
        else {

            errores.setText("Presione una meta para modificarla o borrarla");
        }

        listaMetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arrayadapter, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PantallaModificarMeta.class);
                intent.putExtra("Meta", Estudiante.getInstance().getMetas().get(position));
                intent.putExtra("indice",position);
                startActivity(intent);
            }
        });
        botonNuevarMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPantallaAgregarMeta();
            }
        });
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverPantallaPrincipal();
            }
        });

    }
    private void iniciarLista(){
        for (Meta m:Estudiante.getInstance().getMetas()) {
            metas.add(meta);
        }
        listaMetas.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, metas));
    }
    private void inicializarAtributos(){
        botonNuevarMeta = (Button) findViewById(R.id.botonNuevaMeta);
        listaMetas = (ListView) findViewById(R.id.listaMetas);
        errores = (TextView) findViewById(R.id.textoNoMetas);
        botonVolver = (Button) findViewById(R.id.botonVolverPantallaPrincipal);
        metas = new ArrayList<>();
    }

    private void abrirPantallaAgregarMeta(){
        Intent intent = new Intent(this, PantallaAgregarMeta.class);
        startActivity(intent);
    }
    private void volverPantallaPrincipal(){
        Intent intent = new Intent(this, PantallaHorario.class);
        startActivity(intent);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getUserInfo(){
        database.child("ezcode-e852f-default-rtdb").child(Estudiante.getInstance().getID()).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                     meta= snapshot.child("Nombre").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}
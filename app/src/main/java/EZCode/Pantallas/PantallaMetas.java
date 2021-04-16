package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaMetas extends AppCompatActivity {

    private Button botonAgregarMeta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_metas);

        botonAgregarMeta = (Button) findViewById(R.id.botonAgregarMeta);

        botonAgregarMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPantallaAgregarMeta();
            }
        });

    }
    private void abrirPantallaAgregarMeta(){
        Intent intent = new Intent(this, PantallaAgregarMeta.class);
        startActivity(intent);
    }
}
package main.java.EZCode.Pantallas;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PantallaBoost extends AppCompatActivity {

    Button boton_volver;
    WebView wb_memes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_boost);
        Random random = new Random();

        boton_volver = findViewById(R.id.botonVolverDesdeMemes);
        wb_memes = (WebView)findViewById(R.id.wbMemes);
        wb_memes.getSettings().setBuiltInZoomControls(true);
        wb_memes.getSettings().setJavaScriptEnabled(true);
        wb_memes.getSettings().setDomStorageEnabled(true);
        wb_memes.setWebViewClient(new WebViewClient());
        wb_memes.loadUrl("https://imgur.com/a/nwzEyTU");

        boton_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverPantallaPrincipal();
            }
        });

    }
    private void volverPantallaPrincipal(){
        finish();
    }
}

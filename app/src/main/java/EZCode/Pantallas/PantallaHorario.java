package EZCode.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;

public class PantallaHorario extends AppCompatActivity {
    CalendarView calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_horario);

        calendario = (CalendarView) findViewById(R.id.calendario);

    }
}
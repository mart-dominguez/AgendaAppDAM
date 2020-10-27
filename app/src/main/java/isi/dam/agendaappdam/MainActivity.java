package isi.dam.agendaappdam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Toolbar miToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        miToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(miToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuAddCat:
                Intent i1 = new Intent(this, CategoriaActivity.class);
                startActivity(i1);
                return true;
            case R.id.mnuAddEvt:
                Intent i2 = new Intent(this, EventoActivity.class);
                startActivity(i2);
                return true;
            case R.id.mnuLstEvt:
                Intent i3 = new Intent(this, ListaEventosActivity.class);
                startActivity(i3);
                return true;
            case R.id.mnuMapaEvento:
                Intent i4 = new Intent(this, MapsActivity.class);
                startActivity(i4);
                return true;
            default:
                Toast.makeText(this, ". . . . ", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

}
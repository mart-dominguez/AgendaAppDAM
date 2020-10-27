package isi.dam.agendaappdam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import isi.dam.agendaappdam.dao.AccionesDAO;
import isi.dam.agendaappdam.dao.CategoriaRepository;
import isi.dam.agendaappdam.dao.EventoRepository;
import isi.dam.agendaappdam.dao.room.CategoriaRepositoryRoom;
import isi.dam.agendaappdam.dao.room.EventoRepositoryRoom;
import isi.dam.agendaappdam.modelo.Categoria;
import isi.dam.agendaappdam.modelo.Evento;

public class EventoActivity extends AppCompatActivity {

    private final MyEventoHandler mHandler = new MyEventoHandler(this);
    List<Categoria> listaCategorias = new ArrayList<>();

    Spinner spCategorias;
    EditText edtEvtDesc, evtCosto;
    Button btnCrearCategoria;
    Button btnGuardarEvt;
    ArrayAdapter<Categoria> adapterCat;

    CategoriaRepository categoriaRepository;
    EventoRepository eventoRepository;

    private static class MyEventoHandler extends Handler {
        private final WeakReference<EventoActivity> mActivity;

        public MyEventoHandler(EventoActivity activity) {
            mActivity = new WeakReference<EventoActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            EventoActivity activity = mActivity.get();
            if (activity != null) {
                Bundle data = msg.getData();
                AccionesDAO evento = AccionesDAO.valueOf(data.getString("accion"));
                switch (evento){
                    case EVT_CREAR:
                        Intent i1 = new Intent(activity,ListaEventosActivity.class);
                        activity.startActivity(i1);
                        break;
                    case CAT_LISTAR:
                        ArrayList<Categoria> lasCategorias = data.getParcelableArrayList ("categorias");
                        Log.d("CATEGORIAS", "handleMessage: "+lasCategorias);
                        Toast.makeText(activity,"Categorias listadas",Toast.LENGTH_LONG).show();
                        if(lasCategorias!=null){
                            activity.listaCategorias.clear();
                            activity.listaCategorias.addAll(lasCategorias);
                            activity.adapterCat.notifyDataSetInvalidated();
                        }
                        break;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        categoriaRepository = new CategoriaRepositoryRoom(this);
        eventoRepository = new EventoRepositoryRoom(this);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        spCategorias = findViewById(R.id.evtCmbCategoria);
        edtEvtDesc = findViewById(R.id.evtDesc);
        evtCosto = findViewById(R.id.evtCosto);
        btnCrearCategoria = findViewById(R.id.btnAddNewCat);
        btnGuardarEvt = findViewById(R.id.btnGuardarEvt);

        this.actualizarListaCategorias();

        adapterCat = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listaCategorias);
        spCategorias.setAdapter(adapterCat);

        btnCrearCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(EventoActivity.this,CategoriaActivity.class);
                startActivityForResult(i1,999);
            }
        });

        btnGuardarEvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("EVENTOS_DAM", "onClick: guardar");
                Toast.makeText(EventoActivity.this," A VER :...... ",Toast.LENGTH_LONG).show();
                Evento e = new Evento();
                e.setCosto(Double.valueOf(evtCosto.getText().toString()));
                e.setDescripcion(edtEvtDesc.getText().toString());
                eventoRepository.crearEvento(e,mHandler);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==999){
            this.actualizarListaCategorias();
        }
    }

    private void actualizarListaCategorias(){
        categoriaRepository.listarCategoria(mHandler);
    }
}
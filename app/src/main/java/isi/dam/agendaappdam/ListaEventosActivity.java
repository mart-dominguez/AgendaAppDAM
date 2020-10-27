package isi.dam.agendaappdam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import isi.dam.agendaappdam.dao.AccionesDAO;
import isi.dam.agendaappdam.dao.EventoRepository;
import isi.dam.agendaappdam.dao.room.EventoRepositoryRoom;
import isi.dam.agendaappdam.modelo.Categoria;
import isi.dam.agendaappdam.modelo.Evento;
import isi.dam.agendaappdam.ui.EventoAdapter;

public class ListaEventosActivity extends AppCompatActivity {
    private final ListaEventosActivity.ListaEventosHandler mHandler = new ListaEventosActivity.ListaEventosHandler(this);

    private static class ListaEventosHandler extends Handler {
        private final WeakReference<ListaEventosActivity> mActivity;

        public ListaEventosHandler(ListaEventosActivity activity) {
            mActivity = new WeakReference<ListaEventosActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("EVENTOS_DAM", "handleMessage: EVT_LISTAR");
            ListaEventosActivity activity = mActivity.get();
            if (activity != null) {
                Bundle data = msg.getData();
                AccionesDAO evento = AccionesDAO.valueOf(data.getString("accion"));
                switch (evento){
                    case EVT_LISTAR:
                        Log.d("EVENTOS_DAM", "onClick: EVT_LISTAR");
                        List<Evento> lista = data.getParcelableArrayList("listaEventos");
                        Toast.makeText(activity,"Lista de eventos...",Toast.LENGTH_LONG).show();
                        Log.d("EVENTOS_DAM", "onClick: EVT_LISTAR is null"+lista);

                        if(lista!=null){
                            activity.listaEvt.clear();
                            activity.listaEvt.addAll(lista);
                            activity.mAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        }
    }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Evento> listaEvt;
    EventoRepository evtRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listaEvt = new ArrayList<Evento>();

        evtRepo = new EventoRepositoryRoom(this);

        recyclerView = (RecyclerView) findViewById(R.id.recListaEvt);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new EventoAdapter(listaEvt);
        recyclerView.setAdapter(mAdapter);
        evtRepo.listarEventos(mHandler);
    }
}
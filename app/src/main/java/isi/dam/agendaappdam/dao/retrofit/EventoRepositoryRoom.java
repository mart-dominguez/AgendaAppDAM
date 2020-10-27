package isi.dam.agendaappdam.dao.retrofit;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import isi.dam.agendaappdam.dao.AccionesDAO;
import isi.dam.agendaappdam.dao.EventoRepository;
import isi.dam.agendaappdam.dao.room.DatabaseBuilder;
import isi.dam.agendaappdam.dao.room.EventoDAORoom;
import isi.dam.agendaappdam.modelo.Evento;

public class EventoRepositoryRoom implements EventoRepository {

    private EventoDAORoom eventoDao;

    public EventoRepositoryRoom(Context ct){

        eventoDao= DatabaseBuilder.getInstance().getEventoDaoRoom(ct);
    }

    @Override
    public void crearEvento(final Evento evt, final Handler h) {
        final Message msg = h.obtainMessage();
        final Bundle datos = new Bundle();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("EVENTOS_DAM", "onClick: crearEvento");
                long idNuevo = eventoDao.crear(evt);
                evt.setId(idNuevo);
                List<Evento> listaEvt = eventoDao.lista();
                datos.putParcelableArrayList("listaEventos",new ArrayList<Evento>(listaEvt));
                datos.putString("accion", AccionesDAO.EVT_CREAR.toString());
                msg.setData(datos);
                h.sendMessage(msg);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    @Override
    public void listarEventos(final Handler  h) {

        final Message msg = h.obtainMessage();
        final Bundle datos = new Bundle();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("EVENTOS_DAM", "onClick: listarEventos");
                datos.putParcelableArrayList("listaEventos",(ArrayList<Evento>) eventoDao.lista());
                datos.putString("accion", AccionesDAO.EVT_LISTAR.toString());
                msg.setData(datos);
                h.sendMessage(msg);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

}

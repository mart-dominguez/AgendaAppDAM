package isi.dam.agendaappdam.dao;

import android.os.Handler;

import java.util.List;

import isi.dam.agendaappdam.modelo.Evento;

public interface EventoRepository {

    public void crearEvento(final Evento evt, final Handler h);

    public void listarEventos(final Handler h);
}

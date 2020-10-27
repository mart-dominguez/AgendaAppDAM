package isi.dam.agendaappdam.dao.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import isi.dam.agendaappdam.modelo.Categoria;
import isi.dam.agendaappdam.modelo.Evento;

@Dao
public interface EventoDAORoom {

    @Query("SELECT * FROM evento")
    public List<Evento> lista();

    @Insert
    public long crear(Evento c);


}

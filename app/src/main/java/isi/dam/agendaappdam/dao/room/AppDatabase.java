package isi.dam.agendaappdam.dao.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import isi.dam.agendaappdam.modelo.Categoria;
import isi.dam.agendaappdam.modelo.Evento;

@Database(entities = {Categoria.class, Evento.class}, version = 3)
@TypeConverters({Conversores.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoriaDAORoom categoriaDAO();
    public abstract EventoDAORoom eventoDAO();
}

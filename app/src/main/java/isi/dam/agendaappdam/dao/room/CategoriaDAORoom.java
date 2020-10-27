package isi.dam.agendaappdam.dao.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import isi.dam.agendaappdam.modelo.Categoria;

@Dao
public interface CategoriaDAORoom  {

    @Query("SELECT * FROM categoria")
    public List<Categoria> lista();

    @Insert
    public long crear(Categoria c);


}

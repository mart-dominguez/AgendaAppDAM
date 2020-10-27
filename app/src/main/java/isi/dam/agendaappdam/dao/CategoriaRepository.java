package isi.dam.agendaappdam.dao;

import android.os.Handler;

public interface CategoriaRepository {
    public void crearCategoria(final String desc, final Handler h);
    public void listarCategoria ( final Handler h);

}

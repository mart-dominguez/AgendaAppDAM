package isi.dam.agendaappdam.dao.retrofit;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import isi.dam.agendaappdam.dao.AccionesDAO;
import isi.dam.agendaappdam.dao.CategoriaRepository;
import isi.dam.agendaappdam.dao.room.CategoriaDAORoom;
import isi.dam.agendaappdam.dao.room.DatabaseBuilder;
import isi.dam.agendaappdam.modelo.Categoria;

public class CategoriaRepositoryRoom implements CategoriaRepository {

    CategoriaDAORoom categoriaDaoRoom;


    public CategoriaRepositoryRoom(Context ct){
        categoriaDaoRoom = DatabaseBuilder.getInstance().getCategoriaDaoRoom(ct);
    }

    public void crearCategoria(final String desc, final Handler h){
        final Message msg = h.obtainMessage();
        final Bundle datos = new Bundle();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Categoria catInsert = new Categoria(null,desc);
                long idNuevo = categoriaDaoRoom.crear(catInsert);
                catInsert.setId(idNuevo);
                datos.putParcelable("categoria",catInsert);
                datos.putString("accion", AccionesDAO.CAT_CREAR.toString());
                msg.setData(datos);
                h.sendMessage(msg);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void listarCategoria(final Handler h){
        final Message msg = h.obtainMessage();
        final Bundle datos = new Bundle();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                datos.putParcelableArrayList("categorias",( ArrayList<Categoria>) categoriaDaoRoom.lista());
                datos.putString("accion", AccionesDAO.CAT_LISTAR.toString());
                msg.setData(datos);
                h.sendMessage(msg);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }


}

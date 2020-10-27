package isi.dam.agendaappdam.dao.retrofit;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.Room;

import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import isi.dam.agendaappdam.dao.AccionesDAO;
import isi.dam.agendaappdam.dao.CategoriaRepository;
import isi.dam.agendaappdam.modelo.Categoria;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriaRepositoryRest implements CategoriaRepository {


    CategoriaDAORest categoriaRest;


    public CategoriaRepositoryRest(){
        categoriaRest = ApiBuilder.getInstance().getCategoriaRest();
    }


        public void crearCategoria(final String desc, final Handler h){
            Log.d("EVENTOS_DAM", "REST: crearCategoria");
            Categoria catInsert = new Categoria(null,desc);

            Call<Categoria> llamada = categoriaRest.crear(catInsert);
            llamada.enqueue(new Callback<Categoria>() {
                @Override
                public void onResponse(Call<Categoria> call, Response<Categoria> response) {
                    Log.d("EVENTOS_DAM", "REST respuesta: crearCategoria "+response.body());
                    final Message msg = h.obtainMessage();
                    final Bundle datos = new Bundle();
                    datos.putParcelable("categoria",response.body());
                    datos.putString("accion", AccionesDAO.CAT_CREAR.toString());
                    msg.setData(datos);
                    h.sendMessage(msg);
                }

                @Override
                public void onFailure(Call<Categoria> call, Throwable t) {
                    Log.d("EVENTOS_DAM", "REST EWRROR  "+t.toString());

                }
            });
        }

        public void listarCategoria(final Handler h){
            final Message msg = h.obtainMessage();
            final Bundle datos = new Bundle();
            Call<List<Categoria>> llamada = categoriaRest.listarTodos();
            llamada.enqueue(new Callback<List<Categoria>>() {
                @Override
                public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                    Log.d("EVENTOS_DAM", "REST: crearCategoria "+response.body());
                    final Message msg = h.obtainMessage();
                    final Bundle datos = new Bundle();
                    datos.putParcelableArrayList("categoria",(ArrayList<Categoria>) response.body());
                    datos.putString("accion", AccionesDAO.CAT_LISTAR.toString());
                    msg.setData(datos);
                    h.sendMessage(msg);
                }

                @Override
                public void onFailure(Call<List<Categoria>> call, Throwable t) {

                }
            });
        }

    }


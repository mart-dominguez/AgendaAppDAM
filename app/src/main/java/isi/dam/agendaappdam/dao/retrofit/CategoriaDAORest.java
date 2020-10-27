package isi.dam.agendaappdam.dao.retrofit;

import java.util.List;

import isi.dam.agendaappdam.modelo.Categoria;
import isi.dam.agendaappdam.modelo.Evento;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoriaDAORest {

    @GET("categorias")
    Call<List<Categoria>> listarTodos();

    @POST("categorias")
    Call<Categoria> crear(@Body Categoria e);
}

package isi.dam.agendaappdam.dao.retrofit;

import java.util.List;

import isi.dam.agendaappdam.modelo.Evento;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EventoDAORest {

    @GET("eventos")
    Call<List<Evento>> listarTodos();

    @POST("eventos")
    Call<Evento> crear(@Body Evento e);
}

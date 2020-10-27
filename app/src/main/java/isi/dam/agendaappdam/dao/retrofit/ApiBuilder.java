package isi.dam.agendaappdam.dao.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import isi.dam.agendaappdam.dao.room.DatabaseBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
    private CategoriaDAORest categoriaRest;
    private EventoDAORest eventoRest;
    private static ApiBuilder _INSTANCIA;

    private ApiBuilder(){

    }

    public CategoriaDAORest getCategoriaRest() {
        if(categoriaRest==null) this.iniciarRetrofit();
        return categoriaRest;
    }


    public EventoDAORest getEventoRest() {
        if(eventoRest==null) this.iniciarRetrofit();
        return eventoRest;
    }

    public static ApiBuilder getInstance(){
        if(_INSTANCIA == null) {
            _INSTANCIA = new ApiBuilder();
        }
        return _INSTANCIA;
    }

    private void iniciarRetrofit(){
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        categoriaRest =retrofit.create(CategoriaDAORest.class);
        eventoRest =retrofit.create(EventoDAORest.class);
    }


}

package isi.dam.agendaappdam.dao.room;

import android.content.Context;

import androidx.room.Room;

public class DatabaseBuilder {

    CategoriaDAORoom categoriaDaoRoom;
    EventoDAORoom eventoDaoRoom;
    private static DatabaseBuilder _INSTANCIA;
    private AppDatabase dbRoom;

    private void iniciarRoom(Context ct){
        dbRoom = Room.databaseBuilder(ct,
                AppDatabase.class, "agenda-dam")
                .fallbackToDestructiveMigration()
                .build();
        categoriaDaoRoom = dbRoom.categoriaDAO();
        eventoDaoRoom = dbRoom.eventoDAO();
    }

    public static DatabaseBuilder getInstance(){
        if(_INSTANCIA == null) {
            _INSTANCIA = new DatabaseBuilder();
        }
        return _INSTANCIA;
    }

    public CategoriaDAORoom getCategoriaDaoRoom(Context ct) {
        if(dbRoom==null){
            iniciarRoom(ct);
        }
        return categoriaDaoRoom;
    }

    public EventoDAORoom getEventoDaoRoom(Context ct) {
        if(dbRoom==null){
            iniciarRoom(ct);
        }
        return eventoDaoRoom;
    }

}

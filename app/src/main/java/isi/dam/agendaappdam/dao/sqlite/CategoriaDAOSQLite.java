package isi.dam.agendaappdam.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import isi.dam.agendaappdam.modelo.Categoria;

public class CategoriaDAOSQLite  {

    MyDatabaseHelper dbHelper;

    public CategoriaDAOSQLite(Context ctx){
        dbHelper = MyDatabaseHelper.getInstance(ctx);
    }

    public List<Categoria> lista() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT " + MyDatabaseHelper.CAT_ID + " , " + MyDatabaseHelper.CAT_DESCRIPCION + " FROM " + MyDatabaseHelper.TABLA_CATEGORIA, null);
        ArrayList<Categoria> listaCat = new ArrayList<>();

        // no es necesario para explotar los datos
        // lo necesario es recorrer el cursor
        // pero es util para obtener mas informacion.
        int cantidadColumnas = c.getColumnCount();
        List<String> nombresColumnas = new ArrayList<>();
        List<Integer> tiposColumnas = new ArrayList<>();
        for(int i =0;i<cantidadColumnas;i++){
            nombresColumnas.add(c.getColumnName(i));
            tiposColumnas.add(c.getType(i));
        }

        while (c.moveToNext()){
//             listaCat.add(new Categoria(c.getLong(0),c.getString(1)));
             Categoria cat = new Categoria();
             cat.setId(c.getLong(c.getColumnIndex(MyDatabaseHelper.CAT_ID)));
             cat.setDescripcion(c.getString(c.getColumnIndex(MyDatabaseHelper.CAT_DESCRIPCION)));
             listaCat.add(cat);

        }
        return listaCat;
    }

    public long insertar(Categoria c) {

        ContentValues cv=new ContentValues();
        cv.put(MyDatabaseHelper.CAT_DESCRIPCION, c.getDescripcion());
        long id = dbHelper.getWritableDatabase().insert(MyDatabaseHelper.TABLA_CATEGORIA, null, cv);
        return id;
    }
}

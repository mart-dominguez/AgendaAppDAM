package isi.dam.agendaappdam.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="db";
    public static final String TABLA_CATEGORIA = "dam_categoria";
    public static final String CAT_ID="_id";
    public static final String CAT_DESCRIPCION="descripcion";

    public static final String TABLA_EVENTO = "dam_evento";
    public static final String EVT_ID="_id";
    public static final String EVT_DESCRIPCION="descripcion";
    public static final String EVT_FECHA="fecha";
    public static final String EVT_COSTO="costo";
    public static final String EVT_NOTIFICAR="notificar";
    public static final String EVT_CATEGORIA="id_categoria";
    public static final String EVT_LAT="lat";
    public static final String EVT_LNG="lng";

    private static MyDatabaseHelper INSTANCIA_ = null;

    private MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static MyDatabaseHelper getInstance(Context ctx){
        if(INSTANCIA_==null){
            INSTANCIA_ = new MyDatabaseHelper(ctx);
        }
        return INSTANCIA_;
    }

    private static final String SQL_CREATE_TBL_EVENTO = "create table " + TABLA_EVENTO +" ("+
            EVT_ID+ " integer primary key autoincrement, "+
            EVT_DESCRIPCION+ " text, " +
            EVT_FECHA+ " text, " +
            EVT_COSTO+ " real, " +
            EVT_NOTIFICAR+ " integer, " +
            EVT_CATEGORIA+ " text, " +
            EVT_LAT+ " real, " +
            EVT_LNG+ " real, " +
            " FOREIGN KEY ("+ EVT_CATEGORIA + ") REFERENCES " +TABLA_CATEGORIA+"("+CAT_ID+");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLA_CATEGORIA +" ("+CAT_ID+ " integer primary key autoincrement, "+CAT_DESCRIPCION+ " text);");
        db.execSQL(SQL_CREATE_TBL_EVENTO);
        ContentValues cv=new ContentValues();
        cv.put(CAT_DESCRIPCION, "Categoria 1");
        db.insert(TABLA_CATEGORIA, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}

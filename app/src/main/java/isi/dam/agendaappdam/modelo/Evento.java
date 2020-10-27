package isi.dam.agendaappdam.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Categoria.class,
        parentColumns = "id",  // clave en la tabla padre
        childColumns = "id_categoria", // clave foranea en esta tabla
        onDelete = ForeignKey.CASCADE))
public class Evento implements Parcelable {


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

    public Evento(Parcel in) {
        this.id = in.readLong();
        this.descripcion = in.readString();
        this.costo = in.readDouble();
        this.idCategoria = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(descripcion);
        parcel.writeDouble(this.costo);
        parcel.writeLong(this.idCategoria);
    }

    public Evento(){
    }

    @Expose
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @Expose
    private String descripcion;

    @Expose
    private Date fecha;

    @Expose
    private Double costo;

    @Expose
    private Boolean notificar;

    @ColumnInfo(name = "id_categoria")
    private  Long idCategoria;

    @Expose // se usa en REST
    @Ignore // se ignora en ROOM
    private Categoria categoria;


    @Expose
    private Double lat;

    @Expose
    private Double lng;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Boolean getNotificar() {
        return notificar;
    }

    public void setNotificar(Boolean notificar) {
        this.notificar = notificar;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}

package isi.dam.agendaappdam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import isi.dam.agendaappdam.dao.AccionesDAO;
import isi.dam.agendaappdam.dao.CategoriaRepository;
import isi.dam.agendaappdam.dao.retrofit.CategoriaRepositoryRest;
import isi.dam.agendaappdam.dao.room.CategoriaRepositoryRoom;
import isi.dam.agendaappdam.modelo.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    private CategoriaRepository catRepo ;
    
    private static class MyHandler extends Handler {
        private final WeakReference<CategoriaActivity> mActivity;

        public MyHandler(CategoriaActivity activity) {
            mActivity = new WeakReference<CategoriaActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CategoriaActivity activity = mActivity.get();
            if (activity != null) {
                Bundle data = msg.getData();
                AccionesDAO evento = AccionesDAO.valueOf(data.getString("accion"));
                switch (evento){
                    case CAT_CREAR:
                        Categoria laCategoria = data.getParcelable("categoria");
                        Toast.makeText(activity,"Categoria creada",Toast.LENGTH_LONG).show();
                        activity.tvCatId.setText("ID : "+laCategoria.getId());
                        // getIntent().get
                        activity.finish();
                        break;
                }
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);

    Button btnGuardar;
    TextView tvCatId;
    EditText etCatDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        // persistir en SQLITE con ROOM
        // catRepo= new CategoriaRepositoryRoom(this);
        // persistir en un API REST con Retrofit
        catRepo= new CategoriaRepositoryRest();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvCatId = findViewById(R.id.catId);
        etCatDesc = findViewById(R.id.catDescripcion);
        btnGuardar = findViewById(R.id.btnGuardarCat);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catRepo.crearCategoria(etCatDesc.getText().toString(),mHandler);

            }
        });

    }

}
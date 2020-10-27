package isi.dam.agendaappdam.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import isi.dam.agendaappdam.R;
import isi.dam.agendaappdam.modelo.Evento;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {
    private List<Evento> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvDesc;
        public TextView tvCosto;
        public TextView tvCategoria;
        public Button btnProcesar;
        public EventoViewHolder(View v) {
            super(v);
            tvDesc = v.findViewById(R.id.rowEvtDescripcion);
            tvCosto = v.findViewById(R.id.rowEvtCosto);
            tvCategoria = v.findViewById(R.id.rowEvtCategoria);
            btnProcesar = v.findViewById(R.id.rowBtnProcesar);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventoAdapter(List<Evento> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public EventoAdapter.EventoViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v =  (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fila_evento, parent, false);
        EventoViewHolder vh = new EventoViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(EventoViewHolder holder, int position) {
        Evento evt = mDataset.get(position);
        holder.tvDesc.setText(evt.getDescripcion());
        holder.tvCosto.setText(evt.getCosto()+"");
        holder.tvCategoria.setText(evt.getIdCategoria()+"");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

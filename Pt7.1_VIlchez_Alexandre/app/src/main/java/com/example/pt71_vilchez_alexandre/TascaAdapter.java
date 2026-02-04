package com.example.pt71_vilchez_alexandre;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pt71_vilchez_alexandre.sampledata.Tag;
import com.example.pt71_vilchez_alexandre.sampledata.Tasca;
import com.example.pt71_vilchez_alexandre.sampledata.TascaAmbTag;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TascaAdapter extends RecyclerView.Adapter<TascaAdapter.TascaViewHolder> {

    // 1. lista de TascaAmbTag
    private List<TascaAmbTag> tasques;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public interface OnItemLongClickListener {
        void onItemLongClick(Tasca t);
    }

    private OnItemLongClickListener longClickListener;

    public TascaAdapter(List<TascaAmbTag> tasques, OnItemLongClickListener listener) {
        this.tasques = tasques;
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public TascaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tasca, parent, false);
        return new TascaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TascaViewHolder holder, int position) {
        // Coincide el tipo de la lista con el objeto
        TascaAmbTag item = tasques.get(position);
        Tasca t = item.tasca;

        holder.titol.setText(t.getTitol());
        holder.estat.setText(t.getEstat());
        holder.data.setText(dateFormat.format(t.getDataCreacio()));

        // Lógica para concatenar tags
        if (item.tags != null && !item.tags.isEmpty()) {
            StringBuilder sb = new StringBuilder("Tags: ");
            for (int i = 0; i < item.tags.size(); i++) {
                sb.append(item.tags.get(i).getNombre());
                if (i < item.tags.size() - 1) sb.append(", ");
            }
            holder.tags.setText(sb.toString());
        } else {
            holder.tags.setText("Tags: cap");
        }
    }

    @Override
    public int getItemCount() {
        return tasques.size();
    }

    // 2. Actualizar el tipo de datos que recibe el método
    public void updateData(List<TascaAmbTag> nuevasTasques) {
        this.tasques = nuevasTasques;
        notifyDataSetChanged();
    }

    // 3.
    static class TascaViewHolder extends RecyclerView.ViewHolder {
        TextView titol, estat, data, tags;
        public TascaViewHolder(@NonNull View itemView) {
            super(itemView);
            titol = itemView.findViewById(R.id.txtTitol);
            estat = itemView.findViewById(R.id.txtEstat);
            data = itemView.findViewById(R.id.txtData);
            tags = itemView.findViewById(R.id.txtTags);
        }
    }
}
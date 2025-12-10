package com.example.pt6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {

    private List<Equipo> listaEquipos;
    private String codigoLiga;
    private Context context;

    // Interfaz para gestionar el click
    public interface OnItemClickListener {
        void onItemClick(Equipo equipo);
    }
    private OnItemClickListener listener;

    public EquipoAdapter(List<Equipo> listaEquipos, String codigoLiga, OnItemClickListener listener) {
        this.listaEquipos = listaEquipos;
        this.codigoLiga = codigoLiga;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EquipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        // Inflamos el diseño de la fila (item_equipo.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.item_equipo, parent, false);
        return new EquipoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipoViewHolder holder, int position) {
        Equipo equipo = listaEquipos.get(position);

        // Asignamos textos
        holder.tvNombre.setText(equipo.getNombre());
        holder.tvAbrev.setText(equipo.getAbreviatura());

        // Cargamos imagen con Glide
        // Usamos el método getUrlEscudo que creamos en la clase Equipo
        String urlImagen = equipo.getUrlEscudo(codigoLiga);

        Glide.with(context)
                .load(urlImagen)
                .placeholder(R.mipmap.ic_launcher) // Imagen mientras carga
                .into(holder.ivEscudo);

        // Click en el elemento
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(equipo);
        });
    }

    @Override
    public int getItemCount() {
        return listaEquipos.size();
    }

    // Clase interna ViewHolder: Mantiene las referencias a los controles
    public static class EquipoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvAbrev;
        ImageView ivEscudo;

        public EquipoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvItemNombre);
            tvAbrev = itemView.findViewById(R.id.tvItemAbrev);
            ivEscudo = itemView.findViewById(R.id.ivItemEscudo);
        }
    }
}
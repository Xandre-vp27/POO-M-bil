package com.example.pt7_bbdd_locals_vilchez_alex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder> {

    private List<Vehiculo> lista;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Vehiculo v);
    }

    public VehiculoAdapter(List<Vehiculo> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VehiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehiculo, parent, false);
        return new VehiculoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiculoViewHolder holder, int position) {
        Vehiculo v = lista.get(position);
        holder.tvMatricula.setText(v.getMatricula());
        holder.tvPropietario.setText(v.getNombre() + " " + v.getApellidos() + " - " + v.getTelefono());
        holder.tvVehiculo.setText(v.getMarcaVehiculo() + " " + v.getModeloVehiculo());
        holder.itemView.setOnClickListener(view -> listener.onItemClick(v));
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public void updateData(List<Vehiculo> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    public static class VehiculoViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatricula, tvPropietario, tvVehiculo;
        public VehiculoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatricula = itemView.findViewById(R.id.tvRowMatricula);
            tvPropietario = itemView.findViewById(R.id.tvRowPropietario);
            tvVehiculo = itemView.findViewById(R.id.tvRowVehiculo);
        }
    }
}
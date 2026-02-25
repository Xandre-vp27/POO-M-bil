package com.example.pt8_vilchez_alexandre;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NotaViewHolder> {

    private List<Nota> notaList;
    private OnNotaListener mOnNotaListener;

    public interface OnNotaListener {
        void onNotaClick(Nota nota);
        void onNotaLongClick(Nota nota);
    }

    public NotaAdapter(List<Nota> notaList, OnNotaListener onNotaListener) {
        this.notaList = notaList;
        this.mOnNotaListener = onNotaListener;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(v, mOnNotaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        Nota n = notaList.get(position);
        holder.titol.setText(n.getTitol());
        holder.contingut.setText(n.getContingut());
    }

    @Override
    public int getItemCount() { return notaList.size(); }

    class NotaViewHolder extends RecyclerView.ViewHolder {
        TextView titol, contingut;
        public NotaViewHolder(@NonNull View itemView, OnNotaListener listener) {
            super(itemView);
            titol = itemView.findViewById(R.id.tvItemTitol);
            contingut = itemView.findViewById(R.id.tvItemContingut);

            itemView.setOnClickListener(v -> listener.onNotaClick(notaList.get(getAdapterPosition())));
            itemView.setOnLongClickListener(v -> {
                listener.onNotaLongClick(notaList.get(getAdapterPosition()));
                return true;
            });
        }
    }
}
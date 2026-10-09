package com.example.pedidosapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedidosapp.R;
import com.example.pedidosapp.models.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private List<Categoria> listaCategorias;

    public CategoriaAdapter(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categoria, parent, false);

        return new CategoriaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        Categoria categoria = listaCategorias.get(position);
        holder.tvNombreCategoria.setText(categoria.getNombre());
        holder.tvDescripcionCategoria.setText(categoria.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    public static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCategoria;
        TextView tvDescripcionCategoria;
        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCategoria = itemView.findViewById(R.id.tvNombreCategoria);
            tvDescripcionCategoria = itemView.findViewById(R.id.tvDescripcionCategoria);
        }
    }
}
package com.example.pedidosapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedidosapp.R;
import com.example.pedidosapp.models.Producto;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> listaProductos;

    public ProductoAdapter(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);
        holder.tvNombreProducto.setText(producto.getNombre());
        holder.tvPrecioProducto.setText("Precio: Q" + producto.getPrecio());
        holder.tvStockProducto.setText("Stock: " + producto.getStock());
        if (producto.getCategoria() != null) {
            holder.tvCategoriaProducto.setText(
                    "Categoría: " + producto.getCategoria().getNombre()
            );
        }
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreProducto;
        TextView tvPrecioProducto;
        TextView tvStockProducto;
        TextView tvCategoriaProducto;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecioProducto = itemView.findViewById(R.id.tvPrecioProducto);
            tvStockProducto = itemView.findViewById(R.id.tvStockProducto);
            tvCategoriaProducto = itemView.findViewById(R.id.tvCategoriaProducto);
        }
    }
}
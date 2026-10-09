package com.example.pedidosapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedidosapp.R;
import com.example.pedidosapp.models.Detalle_Pedido;

import java.util.List;

public class DetallePedidoAdapter extends RecyclerView.Adapter<DetallePedidoAdapter.DetallePedidoViewHolder> {

    private List<Detalle_Pedido> listaDetalles;
    public DetallePedidoAdapter(List<Detalle_Pedido> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    @NonNull
    @Override
    public DetallePedidoViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detalle_pedido, parent, false);
        return new DetallePedidoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(
            @NonNull DetallePedidoViewHolder holder,
            int position) {

        Detalle_Pedido detalle = listaDetalles.get(position);

        if (detalle.getPedido() != null) {
            holder.tvPedidoDetalle.setText("Pedido: #" + detalle.getPedido().getId());
        } else {
            holder.tvPedidoDetalle.setText("Pedido: Sin pedido");
        }

        if (detalle.getProducto() != null) {
            holder.tvProductoDetalle.setText("Producto: " + detalle.getProducto().getNombre()
            );
        } else {
            holder.tvProductoDetalle.setText("Producto: Sin producto");
        }
        holder.tvCantidadDetalle.setText("Cantidad: " + detalle.getCantidad());
        holder.tvPrecioDetalle.setText("Precio unitario: Q" + detalle.getPreciounitario());
    }

    @Override
    public int getItemCount() {
        return listaDetalles.size();
    }

    public static class DetallePedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvPedidoDetalle;
        TextView tvProductoDetalle;
        TextView tvCantidadDetalle;
        TextView tvPrecioDetalle;

        public DetallePedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPedidoDetalle =
                    itemView.findViewById(R.id.tvPedidoDetalle);
            tvProductoDetalle =
                    itemView.findViewById(R.id.tvProductoDetalle);
            tvCantidadDetalle =
                    itemView.findViewById(R.id.tvCantidadDetalle);
            tvPrecioDetalle =
                    itemView.findViewById(R.id.tvPrecioDetalle);
        }
    }
}
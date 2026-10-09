package com.example.pedidosapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedidosapp.R;
import com.example.pedidosapp.models.Pedido;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {
    private List<Pedido> listaPedidos;
    public PedidoAdapter(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pedidos, parent, false);
        return new PedidoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = listaPedidos.get(position);
        if (pedido.getCliente() != null) {
            holder.tvClientePedido.setText("Cliente: " + pedido.getCliente().getNombre()
            );
        } else {
            holder.tvClientePedido.setText("Cliente: Sin cliente");
        }
        holder.tvFechaPedido.setText("Fecha: " + pedido.getFecha()
        );

        holder.tvEstadoPedido.setText("Estado: " + pedido.getEstado()
        );
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvClientePedido;
        TextView tvFechaPedido;
        TextView tvEstadoPedido;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClientePedido = itemView.findViewById(R.id.tvClientePedido);
            tvFechaPedido = itemView.findViewById(R.id.tvFechaPedido);
            tvEstadoPedido = itemView.findViewById(R.id.tvEstadoPedido);
        }
    }
}
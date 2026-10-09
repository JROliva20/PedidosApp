package com.example.pedidosapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedidosapp.R;
import com.example.pedidosapp.models.Cliente;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {

    private List<Cliente> listaClientes;

    public ClienteAdapter(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false);

        return new ClienteViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {

        Cliente cliente = listaClientes.get(position);

        holder.tvNombreCliente.setText(cliente.getNombre());
        holder.tvEmailCliente.setText("Correo: " + cliente.getEmail());
        holder.tvTelefonoCliente.setText("Teléfono: " + cliente.getTelefono());
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCliente;
        TextView tvEmailCliente;
        TextView tvTelefonoCliente;
        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvEmailCliente = itemView.findViewById(R.id.tvEmailCliente);
            tvTelefonoCliente = itemView.findViewById(R.id.tvTelefonoCliente);
        }
    }
}
package com.example.pedidosapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedidosapp.R;
import com.example.pedidosapp.adapter.PedidoAdapter;
import com.example.pedidosapp.controller.ApiService;
import com.example.pedidosapp.models.Cliente;
import com.example.pedidosapp.models.Pedido;
import com.example.pedidosapp.utils.ApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pedidos extends AppCompatActivity {
    private TextInputEditText etClientePedido;
    private TextInputEditText etFechaPedido;
    private TextInputEditText etEstadoPedido;
    private MaterialButton btnCrearPedido;
    private RecyclerView recyclerPedidos;
    private PedidoAdapter pedidoAdapter;
    private List<Pedido> listaPedidos;
    private ApiService apiService;
    private MaterialButton btnVerPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pedidos);

        etClientePedido = findViewById(R.id.etClientePedido);
        etFechaPedido = findViewById(R.id.etFechaPedido);
        etEstadoPedido = findViewById(R.id.etEstadoPedido);
        btnCrearPedido = findViewById(R.id.btnCrearPedido);
        recyclerPedidos = findViewById(R.id.recyclerPedidos);
        listaPedidos = new ArrayList<>();
        pedidoAdapter = new PedidoAdapter(listaPedidos);
        recyclerPedidos.setLayoutManager(
                new LinearLayoutManager(this)
        );
        recyclerPedidos.setAdapter(pedidoAdapter);
        apiService = ApiClient.getClient().create(ApiService.class);
        btnCrearPedido.setOnClickListener(v -> crearPedido());

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        btnVerPedido = findViewById(R.id.btnVerPedido);
        btnVerPedido.setOnClickListener(v -> {
            Intent intent = new Intent(Pedidos.this, DetallePedido.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerPedidos();
    }
    private void obtenerPedidos() {

        apiService.obtenerPedidos().enqueue(
                new Callback<List<Pedido>>() {
                    @Override
                    public void onResponse(
                            Call<List<Pedido>> call,
                            Response<List<Pedido>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {
                            listaPedidos.clear();
                            listaPedidos.addAll(response.body());
                            pedidoAdapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(Pedidos.this, "No se pudieron obtener los pedidos",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<Pedido>> call,
                            Throwable t) {
                        Toast.makeText(Pedidos.this, "Error de conexión: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
    private void crearPedido() {

        String clienteTexto = etClientePedido.getText().toString().trim();
        String fecha = etFechaPedido.getText().toString().trim();
        String estado = etEstadoPedido.getText().toString().trim();

        if (clienteTexto.isEmpty()
                || fecha.isEmpty()
                || estado.isEmpty()) {

            Toast.makeText(this, "Completa todos los campos",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Long clienteId;

        try {
            clienteId = Long.parseLong(clienteTexto);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El ID del cliente debe ser numérico",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);

        Pedido pedido = new Pedido(
                fecha,
                estado,
                cliente
        );

        apiService.crearPedido(pedido).enqueue(
                new Callback<Pedido>() {
                    @Override
                    public void onResponse(
                            Call<Pedido> call,
                            Response<Pedido> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {
                            Toast.makeText(Pedidos.this, "Pedido creado correctamente",
                                    Toast.LENGTH_SHORT
                            ).show();

                            etClientePedido.setText("");
                            etFechaPedido.setText("");
                            etEstadoPedido.setText("");
                            obtenerPedidos();

                        } else {
                            Toast.makeText(Pedidos.this, "No se pudo crear el pedido",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<Pedido> call,
                            Throwable t) {
                        Toast.makeText(Pedidos.this, "Error de conexión: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
}
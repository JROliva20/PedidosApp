package com.example.pedidosapp.views;

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
import com.example.pedidosapp.adapter.DetallePedidoAdapter;
import com.example.pedidosapp.controller.ApiService;
import com.example.pedidosapp.models.Detalle_Pedido;
import com.example.pedidosapp.models.Pedido;
import com.example.pedidosapp.models.Producto;
import com.example.pedidosapp.utils.ApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePedido extends AppCompatActivity {

    private TextInputEditText etPedidoDetalle, etProductoDetalle, etCantidadDetalle, etPrecioDetalle;
    private MaterialButton btnAgregarDetalle;
    private RecyclerView recyclerDetalles;
    private DetallePedidoAdapter detalleAdapter;
    private List<Detalle_Pedido> listaDetalles;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_pedido);

        etPedidoDetalle = findViewById(R.id.etPedidoDetalle);
        etProductoDetalle = findViewById(R.id.etProductoDetalle);
        etCantidadDetalle = findViewById(R.id.etCantidadDetalle);
        etPrecioDetalle = findViewById(R.id.etPrecioDetalle);
        btnAgregarDetalle = findViewById(R.id.btnAgregarDetalle);
        recyclerDetalles = findViewById(R.id.recyclerDetalles);

        listaDetalles = new ArrayList<>();
        detalleAdapter = new DetallePedidoAdapter(listaDetalles);

        recyclerDetalles.setLayoutManager(new LinearLayoutManager(this));
        recyclerDetalles.setAdapter(detalleAdapter);

        apiService = ApiClient.getClient().create(ApiService.class);
        btnAgregarDetalle.setOnClickListener(v -> agregarDetalle());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerDetalles();
    }

    private void obtenerDetalles() {
        apiService.obtenerDetallesPedido().enqueue(new Callback<List<Detalle_Pedido>>() {

            @Override
            public void onResponse(Call<List<Detalle_Pedido>> call, Response<List<Detalle_Pedido>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaDetalles.clear();
                    listaDetalles.addAll(response.body());
                    detalleAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(DetallePedido.this, "No se pudieron obtener los detalles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Detalle_Pedido>> call, Throwable t) {
                Toast.makeText(DetallePedido.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void agregarDetalle() {
        String pedidoTexto = etPedidoDetalle.getText().toString().trim();
        String productoTexto = etProductoDetalle.getText().toString().trim();
        String cantidadTexto = etCantidadDetalle.getText().toString().trim();
        String precioTexto = etPrecioDetalle.getText().toString().trim();

        if (pedidoTexto.isEmpty() || productoTexto.isEmpty() || cantidadTexto.isEmpty() || precioTexto.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Long pedidoId = Long.parseLong(pedidoTexto);
            Long productoId = Long.parseLong(productoTexto);
            Integer cantidad = Integer.parseInt(cantidadTexto);
            Double precio = Double.parseDouble(precioTexto);

            Pedido pedido = new Pedido();
            pedido.setId(pedidoId);

            Producto producto = new Producto();
            producto.setId(productoId);

            Detalle_Pedido detalle = new Detalle_Pedido(
                    cantidad,
                    precio,
                    producto,
                    pedido
            );

            apiService.crearDetallePedido(detalle).enqueue(new Callback<Detalle_Pedido>() {

                @Override
                public void onResponse(Call<Detalle_Pedido> call, Response<Detalle_Pedido> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(DetallePedido.this, "Detalle agregado correctamente", Toast.LENGTH_SHORT).show();

                        etPedidoDetalle.setText("");
                        etProductoDetalle.setText("");
                        etCantidadDetalle.setText("");
                        etPrecioDetalle.setText("");

                        obtenerDetalles();
                    } else {
                        Toast.makeText(DetallePedido.this, "No se pudo agregar el detalle", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Detalle_Pedido> call, Throwable t) {
                    Toast.makeText(DetallePedido.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Revisa los valores numéricos", Toast.LENGTH_SHORT).show();
        }
    }
}
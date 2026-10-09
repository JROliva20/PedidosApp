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
import com.example.pedidosapp.adapter.ProductoAdapter;
import com.example.pedidosapp.controller.ApiService;
import com.example.pedidosapp.models.Producto;
import com.example.pedidosapp.utils.ApiClient;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Productos extends AppCompatActivity {
    private MaterialButton btnAgregarProducto;
    private RecyclerView recyclerProductos;
    private ProductoAdapter productoAdapter;
    private List<Producto> listaProductos;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_productos);

        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);
        btnAgregarProducto.setOnClickListener(v -> {
            Intent intent = new Intent(Productos.this, AgregarProducto.class);
            startActivity(intent);
        });
        recyclerProductos = findViewById(R.id.recyclerProductos);
        listaProductos = new ArrayList<>();
        productoAdapter = new ProductoAdapter(listaProductos);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this));
        recyclerProductos.setAdapter(productoAdapter);
        apiService = ApiClient.getClient().create(ApiService.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void obtenerProductos() {
        apiService.obtenerProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaProductos.clear();
                    listaProductos.addAll(response.body());
                    productoAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(Productos.this,
                            "No se pudieron obtener los productos",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(Productos.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerProductos();
    }
}
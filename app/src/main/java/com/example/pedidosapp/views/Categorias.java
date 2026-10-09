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
import com.example.pedidosapp.adapter.CategoriaAdapter;
import com.example.pedidosapp.controller.ApiService;
import com.example.pedidosapp.models.Categoria;
import com.example.pedidosapp.utils.ApiClient;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Categorias extends AppCompatActivity {
    private MaterialButton btnAgregarCategoria;
    private RecyclerView recyclerCategorias;
    private CategoriaAdapter categoriaAdapter;
    private List<Categoria> listaCategorias;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categorias);
        btnAgregarCategoria = findViewById(R.id.btnAgregarCategoria);
        recyclerCategorias = findViewById(R.id.recyclerCategorias);
        listaCategorias = new ArrayList<>();
        categoriaAdapter = new CategoriaAdapter(listaCategorias);
        recyclerCategorias.setLayoutManager(
                new LinearLayoutManager(this)
        );
        recyclerCategorias.setAdapter(categoriaAdapter);
        apiService = ApiClient.getClient().create(ApiService.class);
        btnAgregarCategoria.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Categorias.this,
                    AgregarCategoria.class
            );
            startActivity(intent);
        });
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerCategorias();
    }

    private void obtenerCategorias() {
        apiService.obtenerCategorias().enqueue(
                new Callback<List<Categoria>>() {

                    @Override
                    public void onResponse(
                            Call<List<Categoria>> call,
                            Response<List<Categoria>> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            listaCategorias.clear();
                            listaCategorias.addAll(response.body());
                            categoriaAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(
                                    Categorias.this, "No se pudieron obtener las categorías",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<Categoria>> call,
                            Throwable t) {
                        Toast.makeText(Categorias.this, "Error de conexión: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
}
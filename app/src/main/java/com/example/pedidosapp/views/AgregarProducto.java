package com.example.pedidosapp.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pedidosapp.R;
import com.example.pedidosapp.controller.ApiService;
import com.example.pedidosapp.models.Categoria;
import com.example.pedidosapp.models.Producto;
import com.example.pedidosapp.utils.ApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarProducto extends AppCompatActivity {

    private TextInputEditText etNombreProducto;
    private TextInputEditText etPrecioProducto;
    private TextInputEditText etStockProducto;
    private Spinner spinnerCategoriaProducto;
    private List<Categoria> listaCategorias;
    private ArrayAdapter<String> categoriaAdapter;
    private MaterialButton btnGuardarProducto;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_producto);

        etNombreProducto = findViewById(R.id.etNombreProducto);
        etPrecioProducto = findViewById(R.id.etPrecioProducto);
        etStockProducto = findViewById(R.id.etStockProducto);

        spinnerCategoriaProducto = findViewById(R.id.spinnerCategoriaProducto);
        listaCategorias = new ArrayList<>();
        categoriaAdapter = new ArrayAdapter<>(
                this,
                R.layout.item_spinner_categoria,
                new ArrayList<>()
        );
        categoriaAdapter.setDropDownViewResource(
                R.layout.item_spinner_categoria_dropdown
        );

        spinnerCategoriaProducto.setAdapter(categoriaAdapter);
        btnGuardarProducto = findViewById(R.id.btnGuardarProducto);
        apiService = ApiClient.getClient().create(ApiService.class);
        cargarCategorias();
        btnGuardarProducto.setOnClickListener(v -> guardarProducto());

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());
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
    private void cargarCategorias() {
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
                            List<String> nombresCategorias = new ArrayList<>();
                            for (Categoria categoria : listaCategorias) {
                                nombresCategorias.add(categoria.getNombre());
                            }
                            categoriaAdapter.clear();
                            categoriaAdapter.addAll(nombresCategorias);
                            categoriaAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(AgregarProducto.this, "No se pudieron cargar las categorías",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<Categoria>> call,
                            Throwable t) {

                        Toast.makeText(
                                AgregarProducto.this, "Error al cargar categorías: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }

    private void guardarProducto() {
        String nombre = etNombreProducto.getText().toString().trim();
        String precioTexto = etPrecioProducto.getText().toString().trim();
        String stockTexto = etStockProducto.getText().toString().trim();
        int posicionCategoria = spinnerCategoriaProducto.getSelectedItemPosition();

        if (nombre.isEmpty() ||
                precioTexto.isEmpty() ||
                stockTexto.isEmpty() ||
                listaCategorias.isEmpty()) {

            Toast.makeText(
                    this, "Completa todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Double precio = Double.parseDouble(precioTexto);
        Integer stock = Integer.parseInt(stockTexto);
        if (posicionCategoria < 0 || posicionCategoria >= listaCategorias.size()) {
            Toast.makeText(this, "Selecciona una categoría",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        Categoria categoria = listaCategorias.get(posicionCategoria);
        Producto producto = new Producto(
                nombre,
                precio,
                stock,
                categoria
        );

        apiService.crearProducto(producto).enqueue(new Callback<Producto>() {

            @Override
            public void onResponse(
                    Call<Producto> call,
                    Response<Producto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(
                            AgregarProducto.this, "Producto creado correctamente",
                            Toast.LENGTH_SHORT
                    ).show();
                    finish();
                } else {
                    Toast.makeText(
                            AgregarProducto.this, "No se pudo crear el producto",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<Producto> call, Throwable t) {
                Toast.makeText(
                        AgregarProducto.this, "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

}
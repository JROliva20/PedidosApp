package com.example.pedidosapp.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pedidosapp.R;
import com.example.pedidosapp.controller.ApiService;
import com.example.pedidosapp.models.Categoria;
import com.example.pedidosapp.utils.ApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarCategoria extends AppCompatActivity {

    private TextInputEditText etNombreCategoria;
    private TextInputEditText etDescripcionCategoria;
    private MaterialButton btnGuardarCategoria;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_categoria);
        etNombreCategoria = findViewById(R.id.etNombreCategoria);
        etDescripcionCategoria = findViewById(R.id.etDescripcionCategoria);
        btnGuardarCategoria = findViewById(R.id.btnGuardarCategoria);
        apiService = ApiClient.getClient().create(ApiService.class);
        btnGuardarCategoria.setOnClickListener(v -> guardarCategoria());

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

    private void guardarCategoria() {
        String nombre = etNombreCategoria.getText().toString().trim();
        String descripcion = etDescripcionCategoria.getText().toString().trim();
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(
                    this, "Completa todos los campos",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Categoria categoria = new Categoria(
                nombre,
                descripcion
        );

        apiService.crearCategoria(categoria).enqueue(
                new Callback<Categoria>() {

                    @Override
                    public void onResponse(
                            Call<Categoria> call,
                            Response<Categoria> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            Toast.makeText(
                                    AgregarCategoria.this, "Categoría creada correctamente",
                                    Toast.LENGTH_SHORT
                            ).show();
                            finish();
                        } else {
                            Toast.makeText(
                                    AgregarCategoria.this, "No se pudo crear la categoría",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<Categoria> call, Throwable t) {
                        Toast.makeText(
                                AgregarCategoria.this, "Error de conexión: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
}
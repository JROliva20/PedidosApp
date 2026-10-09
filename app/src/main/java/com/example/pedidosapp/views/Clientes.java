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
import com.example.pedidosapp.adapter.ClienteAdapter;
import com.example.pedidosapp.controller.ApiService;
import com.example.pedidosapp.models.Cliente;
import com.example.pedidosapp.utils.ApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Clientes extends AppCompatActivity {
    private TextInputEditText etNombreCliente;
    private TextInputEditText etEmailCliente;
    private TextInputEditText etTelefonoCliente;
    private MaterialButton btnAgregarCliente;
    private RecyclerView recyclerClientes;
    private ClienteAdapter clienteAdapter;
    private List<Cliente> listaClientes;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clientes);
        etNombreCliente = findViewById(R.id.etNombreCliente);
        etEmailCliente = findViewById(R.id.etEmailCliente);
        etTelefonoCliente = findViewById(R.id.etTelefonoCliente);
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente);
        recyclerClientes = findViewById(R.id.recyclerClientes);
        listaClientes = new ArrayList<>();
        clienteAdapter = new ClienteAdapter(listaClientes);
        recyclerClientes.setLayoutManager(
                new LinearLayoutManager(this)
        );
        recyclerClientes.setAdapter(clienteAdapter);
        apiService = ApiClient.getClient().create(ApiService.class);
        btnAgregarCliente.setOnClickListener(v -> agregarCliente());

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerClientes();
    }

    private void obtenerClientes() {

        apiService.obtenerClientes().enqueue(
                new Callback<List<Cliente>>() {

                    @Override
                    public void onResponse(
                            Call<List<Cliente>> call,
                            Response<List<Cliente>> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            listaClientes.clear();
                            listaClientes.addAll(response.body());
                            clienteAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(Clientes.this, "No se pudieron obtener los clientes",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<Cliente>> call,
                            Throwable t) {
                        Toast.makeText(Clientes.this, "Error de conexión: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
    private void agregarCliente() {

        String nombre = etNombreCliente
                .getText()
                .toString()
                .trim();

        String email = etEmailCliente
                .getText()
                .toString()
                .trim();

        String telefono = etTelefonoCliente
                .getText()
                .toString()
                .trim();

        if (nombre.isEmpty()
                || email.isEmpty()
                || telefono.isEmpty()) {

            Toast.makeText(this, "Completa todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Cliente cliente = new Cliente(
                nombre,
                email,
                telefono
        );

        apiService.crearCliente(cliente).enqueue(
                new Callback<Cliente>() {

                    @Override
                    public void onResponse(
                            Call<Cliente> call,
                            Response<Cliente> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {
                            Toast.makeText(Clientes.this, "Cliente agregado correctamente",
                                    Toast.LENGTH_SHORT
                            ).show();
                            etNombreCliente.setText("");
                            etEmailCliente.setText("");
                            etTelefonoCliente.setText("");
                            obtenerClientes();

                        } else {
                            Toast.makeText(Clientes.this, "No se pudo agregar el cliente",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<Cliente> call,
                            Throwable t) {
                        Toast.makeText(Clientes.this, "Error de conexión: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
}
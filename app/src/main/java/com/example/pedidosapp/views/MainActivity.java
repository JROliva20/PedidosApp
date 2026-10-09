package com.example.pedidosapp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pedidosapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnProductos;
    private MaterialButton btnClientes;
    private MaterialButton btnCategorias;
    private MaterialButton btnPedidos;

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnProductos = findViewById(R.id.btnProductos);
        btnClientes = findViewById(R.id.btnClientes);
        btnCategorias = findViewById(R.id.btnCategorias);
        btnPedidos = findViewById(R.id.btnPedidos);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        btnProductos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Productos.class);
            startActivity(intent);
        });

        btnClientes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Clientes.class);
            startActivity(intent);
        });

        btnCategorias.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Categorias.class);
            startActivity(intent);
        });

        btnPedidos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Pedidos.class);
            startActivity(intent);
        });

        bottomNavigation.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_inicio) {
                return true;

            } else if (id == R.id.nav_productos) {
                startActivity(new Intent(MainActivity.this, Productos.class));
                return true;

            } else if (id == R.id.nav_clientes) {
                startActivity(new Intent(MainActivity.this, Clientes.class));
                return true;

            } else if (id == R.id.nav_pedidos) {
                startActivity(new Intent(MainActivity.this, Pedidos.class));
                return true;
            }

            return false;
        });

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
}
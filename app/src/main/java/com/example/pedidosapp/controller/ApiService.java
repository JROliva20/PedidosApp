package com.example.pedidosapp.controller;

import com.example.pedidosapp.models.Categoria;
import com.example.pedidosapp.models.Cliente;
import com.example.pedidosapp.models.Detalle_Pedido;
import com.example.pedidosapp.models.Pedido;
import com.example.pedidosapp.models.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/productos")
    Call<List<Producto>> obtenerProductos();

    @POST("api/productos")
    Call<Producto> crearProducto(@Body Producto producto);

    @PUT("api/productos/{id}")
    Call<Producto> actualizarProducto(
            @Path("id") Long id,
            @Body Producto producto
    );

    @GET("api/categorias")
    Call<List<Categoria>> obtenerCategorias();
    @POST("api/categorias")
    Call<Categoria> crearCategoria(@Body Categoria categoria);
    @PUT("api/categorias/{id}")
    Call<Categoria> actualizarCategoria(
            @Path("id") Long id,
            @Body Categoria categoria
    );

    @GET("api/clientes")
    Call<List<Cliente>> obtenerClientes();
    @POST("api/clientes")
    Call<Cliente> crearCliente(@Body Cliente cliente);
    @PUT("api/clientes/{id}")
    Call<Cliente> actualizarCliente(
            @Path("id") Long id,
            @Body Cliente cliente
    );
    @GET("api/pedidos")
    Call<List<Pedido>> obtenerPedidos();
    @POST("api/pedidos")
    Call<Pedido> crearPedido(@Body Pedido pedido);
    @PUT("api/pedidos/{id}")
    Call<Pedido> actualizarPedido(
            @Path("id") Long id,
            @Body Pedido pedido
    );
    @GET("api/detallepedidos")
    Call<List<Detalle_Pedido>> obtenerDetallesPedido();
    @POST("api/detallepedidos")
    Call<Detalle_Pedido> crearDetallePedido(
            @Body Detalle_Pedido detalle
    );
    @PUT("api/detallepedidos/{id}")
    Call<Detalle_Pedido> actualizarDetallePedido(
            @Path("id") Long id,
            @Body Detalle_Pedido detalle
    );

}
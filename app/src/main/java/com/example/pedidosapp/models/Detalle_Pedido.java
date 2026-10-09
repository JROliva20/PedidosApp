package com.example.pedidosapp.models;

public class Detalle_Pedido {
    private Long id;
    private Integer cantidad;
    private Double preciounitario;
    private Pedido pedido;
    private Producto producto;

    public Detalle_Pedido() {
    }

    public Detalle_Pedido(Long id, Integer cantidad, Double preciounitario, Pedido pedido, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.preciounitario = preciounitario;
        this.pedido = pedido;
        this.producto = producto;
    }

    public Detalle_Pedido(Integer cantidad, Double preciounitario, Producto producto, Pedido pedido) {
        this.cantidad = cantidad;
        this.preciounitario = preciounitario;
        this.producto = producto;
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(Double preciounitario) {
        this.preciounitario = preciounitario;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}

package com.example.pedidosapp.models;

public class Pedido {

    private Long id;
    private String fecha;
    private String estado;
    private Cliente cliente;

    public Pedido() {
    }

    public Pedido(Long id, String fecha, String estado, Cliente cliente) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.cliente = cliente;
    }

    public Pedido(String fecha, String estado, Cliente cliente) {
        this.fecha = fecha;
        this.estado = estado;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
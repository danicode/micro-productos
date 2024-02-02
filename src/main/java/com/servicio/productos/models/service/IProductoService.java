package com.servicio.productos.models.service;

import com.servicio.productos.models.entity.Producto;

import java.util.List;

public interface IProductoService {

    public List<Producto> findAll();
    public Producto findById(Long id);

    public Producto save(Producto producto);

    public Producto update(Producto producto, Long id);

    public void deleteById(Long id);
}

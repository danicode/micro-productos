package com.servicio.productos.models.service.impl;

import com.servicio.productos.models.dao.ProductoDao;
import com.servicio.productos.models.entity.Producto;
import com.servicio.productos.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // estereo tipo componente clasificamos al bean del tipo service, de esta forma lo podemos inyectar en otro componentes por ej.> en el controlador
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoDao productoDao;
    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return (List<Producto>) productoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return productoDao.save(producto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productoDao.deleteById(id);
    }
}

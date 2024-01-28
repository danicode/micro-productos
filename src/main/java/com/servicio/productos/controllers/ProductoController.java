package com.servicio.productos.controllers;

import com.servicio.productos.models.entity.Producto;
import com.servicio.productos.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController // una de las cosas que hace esta anotacion es convertir a json lo q retorna el metodo handler
public class ProductoController {

    //@Autowired
    //private Environment env;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private IProductoService productoService;

    @GetMapping("/listar")
    public List<Producto> listar() {
        return productoService.findAll().stream().map(producto -> {
            //producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            producto.setPort(port);
            return producto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        //producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        producto.setPort(port);

        // simulando la falla para probar corto circuito de Hytrix
        /*boolean ok = false;
        if (!ok) {
            throw new RuntimeException("No se pudo cargar el Producto");
        }*/

        //simular un timeout con Thread.sleep
        /*try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        return producto;
    }
}

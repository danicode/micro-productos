package com.servicio.productos.controllers;

import com.servicio.commons.models.entity.Producto;
import com.servicio.productos.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController // una de las cosas que hace esta anotacion es convertir a json lo q retorna el metodo handler
public class ProductoController {

    @Autowired
    private Environment env;

    //@Value("${server.port}")
    //private Integer port;

    @Autowired
    private IProductoService productoService;

    @GetMapping("/listar")
    public List<Producto> listar() {
        return productoService.findAll().stream().map(producto -> {
            producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            //producto.setPort(port);
            return producto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id) throws InterruptedException {
        Producto producto = productoService.findById(id);
        producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        //producto.setPort(port);

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

        // Probar circuit breaker con Resilience4J
        if (id.equals(10L)) {
            throw new IllegalStateException("Producto no encontrado!");
        }

        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(5L);
        }

        return producto;
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
        return productoService.update(producto, id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        productoService.deleteById(id);
    }
}

package com.gestion.biblioteca.control_libros.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestion.biblioteca.control_libros.entidades.Libro;

public interface LibroService {

    public List<Libro> findAll();

    public Page<Libro> findAll(Pageable pageable);

    public void save(Libro libro);

    public Libro findOne(Long id);

    public void delete(Long id);

    
}
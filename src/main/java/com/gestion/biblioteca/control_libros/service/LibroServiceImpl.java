package com.gestion.biblioteca.control_libros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.biblioteca.control_libros.entidades.Libro;
import com.gestion.biblioteca.control_libros.repository.LibroRepository;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Libro> findAll() {
        return (List<Libro>) libroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Libro> findAll(Pageable pageable) {
        return libroRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Libro libro) {
        libroRepository.save(libro);
    }

    @Override
    @Transactional(readOnly = true)
    public Libro findOne(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        libroRepository.deleteById(id);
    }
}
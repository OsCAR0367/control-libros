package com.gestion.biblioteca.control_libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.biblioteca.control_libros.entidades.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
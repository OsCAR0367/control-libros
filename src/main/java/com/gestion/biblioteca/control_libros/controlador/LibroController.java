package com.gestion.biblioteca.control_libros.controlador;


import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.biblioteca.control_libros.entidades.Libro;
import com.gestion.biblioteca.control_libros.service.LibroService;
import com.gestion.biblioteca.control_libros.util.paginacion.PageRender;


@Controller
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/ver/{id}")
    public String verDetallesDelLibro(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Libro libro = libroService.findOne(id);
        if (libro == null) {
            flash.addFlashAttribute("error", "El libro no existe en la base de datos");
            return "redirect:/listar";
        }

        modelo.put("libro", libro);
        modelo.put("titulo", "Detalles del libro " + libro.getNombre());
        return "ver";
    }

    @GetMapping({"/", "/listar", ""})
    public String listarLibros(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo) {
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Libro> libros = libroService.findAll(pageRequest);
        PageRender<Libro> pageRender = new PageRender<>("/listar", libros);

        modelo.addAttribute("titulo", "Listado de libros");
        modelo.addAttribute("libros", libros);
        modelo.addAttribute("page", pageRender);

        return "listar";
    }

    @GetMapping("/form")
    public String mostrarFormularioDeRegistrarLibro(Map<String, Object> modelo) {
        Libro libro = new Libro();
        modelo.put("libro", libro);
        modelo.put("titulo", "Registro de libros");
        return "form";
    }

    @PostMapping("/form")
    public String guardarLibro(@Valid Libro libro, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            modelo.addAttribute("titulo", "Registro de libro");
            return "form";
        }

        String mensaje = (libro.getId() != null) ? "El libro ha sido editado con éxito" : "Libro registrado con éxito";

        libroService.save(libro);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listar";
    }

    @GetMapping("/form/{id}")
    public String editarLibro(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Libro libro = null;
        if (id > 0) {
            libro = libroService.findOne(id);
            if (libro == null) {
                flash.addFlashAttribute("error", "El ID del libro no existe en la base de datos");
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del libro no puede ser cero");
            return "redirect:/listar";
        }

        modelo.put("libro", libro);
        modelo.put("titulo", "Edición de libro");
        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            libroService.delete(id);
            flash.addFlashAttribute("success", "Libro eliminado con éxito");
        }
        return "redirect:/listar";
    }


}
package mx.ipn.upiicsa.smbd.sistemaescolar.controller;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Alumno;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Curso;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.AlumnosService;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.CursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profesor/cursos")
public class PrfCursosController {
    private final CursosService cursosService;
    private final AlumnosService alumnosService;

    @Autowired
    public PrfCursosController(CursosService cursosService, AlumnosService alumnosService) {
        this.cursosService = cursosService;
        this.alumnosService = alumnosService;
    }

    @GetMapping("/index")
    public String viewCursos(Authentication authentication, Model model) {
        Integer id = Integer.parseInt(authentication.getName());
        List<Curso> cursosByProfesor = cursosService.getCursosByProfesor(id);
        System.out.println(cursosByProfesor);
        model.addAttribute("cursos", cursosByProfesor);
        return "profesor/cursos/view_cursos";
    }

    @GetMapping("/{id_curso}/alumnos")
    public String viewCurso(@PathVariable("id_curso") Integer idCurso, Model model) {
        model.addAttribute("idCurso", idCurso);
        List<Alumno> alumnos = alumnosService.getAlumnosByCourse(idCurso);
        model.addAttribute("alumnosList", alumnos);
        System.out.println(alumnos);
        return "profesor/alumnos/view_alumnos";
    }

}
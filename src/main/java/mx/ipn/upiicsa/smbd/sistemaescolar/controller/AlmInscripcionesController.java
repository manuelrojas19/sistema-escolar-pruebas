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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/estudiante/inscripciones")
public class AlmInscripcionesController {
    private final CursosService cursosService;
    private final AlumnosService alumnosService;

    @Autowired
    public AlmInscripcionesController(CursosService cursosService, AlumnosService alumnosService) {
        this.cursosService = cursosService;
        this.alumnosService = alumnosService;
    }

    @GetMapping("/index")
    public String viewProfesores(Authentication authentication, Model model) {
        List<Curso> cursos;
        Integer boleta = Integer.parseInt(authentication.getName());
        cursos = cursosService.getEnrollableCoursesByAlumno(boleta);
        model.addAttribute("alumno", alumnosService.getAlumnoById(boleta));
        model.addAttribute("cursos", cursos);
        return ("alumno/inscripciones/view_inscripciones");
    }

    @GetMapping("/enroll/{idCurso}")
    public String inscribirCursos(@PathVariable Integer idCurso, Authentication authentication, RedirectAttributes redirectAttributes) {
        Integer boleta = Integer.parseInt(authentication.getName());
        Alumno alumno = alumnosService.getAlumnoById(boleta);
        Curso curso = cursosService.getCursoById(idCurso);
        cursosService.enrollAlumnoInCurso(curso, alumno);
        return "redirect:/estudiante/inscripciones/index";
    }

}

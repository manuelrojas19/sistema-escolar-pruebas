package mx.ipn.upiicsa.smbd.sistemaescolar.controller;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Curso;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.AsignaturasService;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.CursosService;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.ProfesoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/administrador/cursos")
public class AdmCursosController {
    private final CursosService cursosService;
    private final ProfesoresService profesoresService;
    private final AsignaturasService asignaturasService;

    @Autowired
    public AdmCursosController(CursosService cursosService, ProfesoresService profesoresService, AsignaturasService asignaturasService) {
        this.cursosService = cursosService;
        this.profesoresService = profesoresService;
        this.asignaturasService = asignaturasService;
    }

    @GetMapping("/index")
    public String vistaCursos(Model model) {
        List<Curso> cursoList = cursosService.getCursos();
        System.out.println(cursoList);
        model.addAttribute("cursoList", cursoList);
        return "administrador/cursos/view_cursos";
    }

    @GetMapping("/create")
    public String createCurso(Curso curso, Model model) {
        model.addAttribute("profesores", profesoresService.getProfesores());
        model.addAttribute("asignaturas", asignaturasService.getAsignaturas());
        return "administrador/cursos/form_curso";
    }

    @PostMapping("/save")
    public String save(Curso curso, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        cursosService.guardarCurso(curso);
        return "redirect:/administrador/cursos/index";
    }

    @GetMapping("/delete/{idCurso}")
    public String delete(@PathVariable("idCurso") Integer idCurso, RedirectAttributes redirectAttributes) {
        cursosService.deleteCurso(idCurso);
        redirectAttributes.addFlashAttribute("msg", "Registro eliminado");
        redirectAttributes.addFlashAttribute("text_msg", "El curso ha sido eliminado correctamente de la base de datos.");
        return "redirect:/administrador/cursos/index";
    }
}

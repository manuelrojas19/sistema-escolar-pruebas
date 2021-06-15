package mx.ipn.upiicsa.smbd.sistemaescolar.controller;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Curso;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Entrega;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Tarea;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.AlumnosService;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.CursosService;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.EntregaService;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.TareasServices;
import mx.ipn.upiicsa.smbd.sistemaescolar.util.FilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/estudiante/cursos")
public class AlmCursosController {
    private final CursosService cursosService;
    private final TareasServices tareasServices;
    private final AlumnosService alumnosService;
    private final EntregaService entregaService;

    @Autowired
    public AlmCursosController(CursosService cursosService, TareasServices tareasServices, AlumnosService alumnosService, EntregaService entregaService) {
        this.tareasServices = tareasServices;
        this.cursosService = cursosService;
        this.alumnosService = alumnosService;
        this.entregaService = entregaService;
    }

    @GetMapping("/index")
    public String viewCursos(Authentication authentication, Model model) {
        Integer boleta = Integer.parseInt(authentication.getName());
        List<Curso> cursosByAlumno = cursosService.getCursosByAlumno(boleta);
        System.out.println(cursosByAlumno);
        model.addAttribute("cursos", cursosByAlumno);
        return "alumno/cursos/view_cursos";
    }

    @GetMapping("/{id_curso}/tareas")
    public String viewTareas(@PathVariable("id_curso") Integer idCurso, Model model) {
        List<Tarea> tareasList;
        tareasList = tareasServices.getTareasByCurso(idCurso);
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("tareasList", tareasList);
        return "alumno/tareas/view_tareas";
    }

    @GetMapping("/{id_curso}/tareas/{id_tarea}")
    public String entregarTarea(Entrega entrega, Model model, @PathVariable("id_curso") Integer idCurso, @PathVariable Integer id_tarea) {
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("idTarea", id_tarea);
        Integer boleta = 2019600987;
        Entrega entrega1 = entregaService.getEntregaByAlumno(id_tarea, boleta, idCurso);
        System.out.println(entrega1);
        return "alumno/entregas/form_entrega";
    }

    @PostMapping("/{id_curso}/tareas/{id_tarea}/entregar")
    public String saveEntrega(Entrega entrega, Model model, @RequestParam("tareaFile") MultipartFile multipartFile,
                              @PathVariable("id_curso") Integer idCurso, @PathVariable Integer id_tarea, Authentication authentication) {
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("idTarea", id_tarea);

        entrega.setTarea(tareasServices.getTareaById(id_tarea));
        entrega.setAlumno(alumnosService.getAlumnoById(Integer.parseInt(authentication.getName())));
        entrega.setCurso(cursosService.getCursoById(idCurso));

        if (!multipartFile.isEmpty()) {
            String relativePath = "";
            String fileName = FilesUtil.uploadFile(multipartFile, relativePath);
            entrega.setArchivo(fileName);
        }

        if (entregaService.uploadEntrega(entrega)) {
            System.out.println(entrega);
        } else {
            System.out.println("Fallo metodo");
        }
        return "redirect:/estudiante/cursos/" + idCurso + "/tareas/" + id_tarea;
    }
}

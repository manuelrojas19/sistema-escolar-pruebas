package mx.ipn.upiicsa.smbd.sistemaescolar.controller;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Tarea;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.TareasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/profesor/cursos")
public class PrfTareasController {
    private final TareasServices tareasServices;

    @Autowired
    public PrfTareasController(TareasServices tareasServices) {
        this.tareasServices = tareasServices;
    }

    @GetMapping("/{id_curso}/tareas")
    public String viewCurso(@PathVariable("id_curso") Integer idCurso, Model model) {
        List<Tarea> tareasList;
        tareasList = tareasServices.getTareasByCurso(idCurso);
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("tareasList", tareasList);
        return "profesor/tareas/view_tareas";
    }

    @GetMapping("/{id_curso}/tareas/create")
    public String createTarea(@PathVariable("id_curso") Integer idCurso, Tarea tarea, Model model) {
        model.addAttribute("id_curso", idCurso);
        return "profesor/tareas/form_tareas";
    }

    @PostMapping("{id_curso}/tareas/save")
    public String save(@PathVariable("id_curso") Integer idCurso, Tarea tarea, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                System.out.println("Ocurrió un error: " + objectError.getDefaultMessage());
            }
            return "profesor/tareas/form_tareas";
        }
        tareasServices.addTareaByCourse(idCurso, tarea);
        return "redirect:/profesor/cursos/" + idCurso + "/tareas";
    }

    @GetMapping("{id_curso}/tareas/delete/{id_tarea}")
    public String delete(@PathVariable Integer id_curso, @PathVariable Integer id_tarea) {
        tareasServices.deleteTarea(id_tarea);
        return "redirect:/profesor/cursos/" + id_curso + "/tareas";
    }
}

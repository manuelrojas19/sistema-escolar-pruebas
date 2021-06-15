package mx.ipn.upiicsa.smbd.sistemaescolar.controller;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Profesor;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.AcademiasService;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.ProfesoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/administrador/profesores")
public class AdmProfesoresController {
    private final ProfesoresService profesoresService;
    private final AcademiasService academiasService;

    @Autowired
    public AdmProfesoresController(ProfesoresService profesoresService, AcademiasService academiasService) {
        this.profesoresService = profesoresService;
        this.academiasService = academiasService;
    }

    @GetMapping("/index")
    public String viewProfesores(Model model) {
        List<Profesor> profesorList;
        profesorList = profesoresService.getProfesores();
        System.out.println(profesorList);
        model.addAttribute("profesoresList", profesorList);
        return ("administrador/profesores/view_profesores");
    }

    @GetMapping("/create")
    @SuppressWarnings("unused")
    public String createProfesor(Profesor profesor, Model model) {
        model.addAttribute("academias", academiasService.getAcademias());
        return ("administrador/profesores/form_profesor");
    }

    @GetMapping("/edit/{idProfesor}")
    public String edit(@PathVariable("idProfesor") Integer idProfesor, Model model) {
        Profesor profesor = profesoresService.getProfesorById(idProfesor);
        System.out.println(profesor);
        model.addAttribute("profesor", profesor);
        model.addAttribute("academias", academiasService.getAcademias());
        model.addAttribute("isEdit", true);
        return "administrador/profesores/form_profesor";
    }

    @PostMapping("/save")
    public String save(Profesor profesor, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("id") Integer id) {
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                System.out.println("Ocurrio un error: " + objectError.getDefaultMessage());
            }
            return "administrador/profesores/form_profesor";
        }
        System.out.println(profesor);
        if (id != -1) {
            profesoresService.editarProfesor(profesor, id);
            redirectAttributes.addFlashAttribute("msg", "Registro editado");
            redirectAttributes.addFlashAttribute("text_msg", "Se han editado los datos del profesor en la base de datos.");
        } else {
            System.out.println("Entra en guardar");
            if (profesoresService.guardarProfesor(profesor) != null) {
                redirectAttributes.addFlashAttribute("msg", "Registro guardado");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Ocurrio un error al guardar el registro");
            }
        }
        return "redirect:/administrador/profesores/index";
    }

    @GetMapping("/delete/{idProfesor}")
    public String delete(@PathVariable("idProfesor") Integer idProfesor, RedirectAttributes redirectAttributes) {
        profesoresService.eliminarProfesor(idProfesor);
        redirectAttributes.addFlashAttribute("msg", "Registro eliminado");
        redirectAttributes.addFlashAttribute("text_msg", "El profesor ha sido eliminado correctamente de la base de datos.");
        return "redirect:/administrador/profesores/index";
    }
}

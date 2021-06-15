package mx.ipn.upiicsa.smbd.sistemaescolar.controller;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Asignatura;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.AcademiasService;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.AsignaturasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/administrador/asignaturas")
public class AdmAsignaturasController {
    private final AsignaturasService asignaturasService;
    private final AcademiasService academiasService;

    @Autowired
    public AdmAsignaturasController(AsignaturasService asignaturasService, AcademiasService academiasService) {
        this.asignaturasService = asignaturasService;
        this.academiasService = academiasService;
    }

    @GetMapping("/index")
    public String vistaAsignaturas(Model model) {
        List<Asignatura> asignaturaList;
        asignaturaList = asignaturasService.getAsignaturas();
        model.addAttribute("asignaturasList", asignaturaList);
        return "administrador/asignaturas/view_asignaturas";
    }

    @GetMapping("/create")
    public String createAsignatura(Asignatura asignatura, Model model) {
        model.addAttribute("academias", academiasService.getAcademias());
        return "administrador/asignaturas/form_asignatura";
    }

    @GetMapping("/edit/{idAsignatura}")
    public String edit(@PathVariable("idAsignatura") String idAsignatura, Model model) {
        Asignatura asignatura = asignaturasService.getAsignaturaById(idAsignatura);
        model.addAttribute("asignatura", asignatura);
        model.addAttribute("academias", academiasService.getAcademias());
        model.addAttribute("isEdit", true);
        return "administrador/asignaturas/form_asignatura";
    }

    @PostMapping("/save")
    public String save(Asignatura asignatura, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("id") Integer id) {
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                System.out.println("Ocurrio un error: " + objectError.getDefaultMessage());
            }
            return "administrador/asignaturas/form_asignatura";
        }
        if (id != -1) {

        } else {
            System.out.println("Entra en guardar");
            if (asignaturasService.addAsignatura(asignatura)) {
                redirectAttributes.addFlashAttribute("msg", "Registro guardado");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Ocurrió un error al guardar el registro");
            }
        }
        return "redirect:/administrador/asignaturas/index";
    }

    @GetMapping("/delete/{idAsignatura}")
    public String delete(@PathVariable String idAsignatura, RedirectAttributes redirectAttributes) {
        if (asignaturasService.deleteAsignatura(idAsignatura)) {
            redirectAttributes.addFlashAttribute("msg", "Registro eliminado");
            redirectAttributes.addFlashAttribute("text_msg", "La asignatura ha sido eliminada correctamente de la base de datos.");
        }
        return "redirect:/administrador/asignaturas/index";
    }

}

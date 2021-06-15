package mx.ipn.upiicsa.smbd.sistemaescolar.controller;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Alumno;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.AlumnosService;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.ProgramasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/administrador/alumnos")
public class AdmAlumnosController {
    private final AlumnosService alumnosService;
    private final ProgramasService programasService;

    @Autowired
    public AdmAlumnosController(AlumnosService alumnosService, ProgramasService programasService) {
        this.alumnosService = alumnosService;
        this.programasService = programasService;
    }

    @GetMapping("/index")
    public String vistaAlumnos(Model model) {
        List<Alumno> alumnos;
        alumnos = alumnosService.getAlumnos();
        System.out.println(alumnos);
        model.addAttribute("alumnosList", alumnos);
        return "administrador/alumnos/view_alumnos";
    }

    @GetMapping("/create")
    @SuppressWarnings("unused")
    public String createAlumno(Alumno alumno, Model model) {
        model.addAttribute("programas", programasService.getProgramas());
        return "administrador/alumnos/form_alumno";
    }

    @GetMapping("/edit/{boleta}")
    public String edit(@PathVariable("boleta") Integer boleta, Model model) {
        Alumno alumno = alumnosService.getAlumnoById(boleta);
        System.out.println(alumno);
        model.addAttribute("alumno", alumno);
        model.addAttribute("programas", programasService.getProgramas());
        model.addAttribute("isEdit", true);
        return "administrador/alumnos/form_alumno";
    }

    @PostMapping("/save")
    public String save(Alumno alumno, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("id") Integer id) {
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                System.out.println("Ocurrio un error: " + objectError.getDefaultMessage());
            }
            return "administrador/alumnos/form_alumno";
        }
        System.out.println(alumno);
        if (id != -1) {
            if (alumnosService.editAlumno(alumno, id)) {
                sendMessage("Registro editado", "Se han editado los datos del alumno en la base de datos.", 1, redirectAttributes);
            } else {
                sendMessage("Registro no guardado", "Ah ocurrido un error al editar al alumno.", 0, redirectAttributes);
            }
        } else {
            if (alumnosService.guardarAlumno(alumno)) {
                sendMessage("Registro guardado", "El alumno ha sido agregado correctamente a la base de datos.", 1, redirectAttributes);
            } else {
                sendMessage("Registro no guardado", "Ah ocurrido un error al agregar al alumno.", 0, redirectAttributes);
            }
        }
        return "redirect:/administrador/alumnos/index";
    }


    @GetMapping("/delete/{boleta}")
    public String delete(@PathVariable("boleta") Integer boleta, RedirectAttributes redirectAttributes) {
        System.out.println("Borrando el alumno con boleta: " + boleta);
        alumnosService.eliminarAlumno(boleta);
        redirectAttributes.addFlashAttribute("msg", "Registro eliminado");
        redirectAttributes.addFlashAttribute("text_msg", "El alumno ha sido eliminado correctamente de la base de datos.");
        return "redirect:/administrador/alumnos/index";
    }

    private void sendMessage(String title, String message, int type, RedirectAttributes redirectAttributes) {
        if (type == 1) {
            redirectAttributes.addFlashAttribute("msg", title);
        } else {
            redirectAttributes.addFlashAttribute("error_msg", title);
        }
        redirectAttributes.addFlashAttribute("text_msg", message);
    }
}

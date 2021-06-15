package mx.ipn.upiicsa.smbd.sistemaescolar.controller;


import mx.ipn.upiicsa.smbd.sistemaescolar.model.Entrega;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Tarea;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.AlumnosService;
import mx.ipn.upiicsa.smbd.sistemaescolar.service.EntregaService;
import mx.ipn.upiicsa.smbd.sistemaescolar.util.FilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profesor/cursos")
public class ProfEntregasController {
    private final EntregaService entregaService;
    private final AlumnosService alumnosService;

    @Autowired
    public ProfEntregasController(EntregaService entregaService, AlumnosService alumnosService) {
        this.entregaService = entregaService;
        this.alumnosService = alumnosService;
    }

    @GetMapping("/{id_curso}/entregas/{boleta}")
    public String viewEntregasByAlumno(@PathVariable Integer id_curso, @PathVariable Integer boleta, Model model) {
        model.addAttribute("idCurso", id_curso);
        List<Entrega> entregas = entregaService.getEntregasByAlumno(id_curso, boleta);
        System.out.println(entregas);
        model.addAttribute("entregas", entregas);
        return "profesor/alumnos/entregas_by_alumno";
    }

    @GetMapping("/{id_curso}/entregas/{boleta}/{id_tarea}/download")
    public ResponseEntity<Resource> descargarTarea(@PathVariable Integer boleta, @PathVariable Integer id_curso, @PathVariable Integer id_tarea) {
        Entrega entrega = entregaService.getEntregaByAlumno(id_tarea, boleta, id_curso);
        System.out.println(entrega);
        return FilesUtil.loadFile(entrega.getArchivo());
    }

}

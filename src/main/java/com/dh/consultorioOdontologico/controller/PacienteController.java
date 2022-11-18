package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.service.PacienteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    PacienteService pacienteService = new PacienteService();

}

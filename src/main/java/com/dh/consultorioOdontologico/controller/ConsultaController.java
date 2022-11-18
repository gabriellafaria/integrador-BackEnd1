package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.service.ConsultaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    ConsultaService consultaService = new ConsultaService();

}

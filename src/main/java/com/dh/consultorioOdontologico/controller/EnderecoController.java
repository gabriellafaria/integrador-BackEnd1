package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.service.EnderecoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    EnderecoService enderecoService = new EnderecoService();

}

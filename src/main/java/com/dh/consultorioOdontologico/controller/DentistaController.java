package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.service.DentistaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dentista")
public class DentistaController {
    DentistaService dentistaService = new DentistaService();

}

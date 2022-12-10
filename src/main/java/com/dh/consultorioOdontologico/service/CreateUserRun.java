package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Usuario;
import com.dh.consultorioOdontologico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class CreateUserRun implements ApplicationRunner {

    @Autowired
    UsuarioRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

        Usuario usuario = new Usuario();
        usuario.setPassword(bCrypt.encode("123456"));
        usuario.setUsername("Sabrina");

        repository.save(usuario);
    }
}

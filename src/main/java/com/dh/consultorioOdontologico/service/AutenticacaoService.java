package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Usuario;
import com.dh.consultorioOdontologico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> user = repository.findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Usuário não foi encontrado");
        }
        return user.get();
    }
}
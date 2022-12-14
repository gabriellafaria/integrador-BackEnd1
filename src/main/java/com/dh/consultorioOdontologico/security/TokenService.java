package com.dh.consultorioOdontologico.security;

import com.dh.consultorioOdontologico.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class TokenService {

    @Value("${consultorioOdontologico.jwt.expiration}")
    private String expiracao;

    @Value("${consultorioOdontologico.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Usuario userLogado = (Usuario) authentication.getPrincipal();
        Date dataHoje = new Date();
        Date dataExpiracao = new Date(dataHoje.getTime() + Long.parseLong(this.expiracao));
        LocalDate localDateHoje = LocalDate.now();
        LocalDate localDateExpiracao = localDateHoje.plusDays(1);

        String token = Jwts.builder()
                .setIssuer("Api DH Consultório Odontológico")
                .setSubject(userLogado.getUsername())
                .setIssuedAt(dataHoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();

        return token;
    }

    public boolean verificaToken(String token) {
        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;

        }catch (Exception e){
            return false;
        }
    }

    public String getUsernameUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        return username;
    }
}

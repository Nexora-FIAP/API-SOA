package com.br.fiap.nexora.service;

import com.br.fiap.nexora.model.Cliente;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String secret = "segredo-super-seguro";

    public String gerarToken(Cliente cliente) {
        return Jwts.builder()
                .setSubject(cliente.getCpf())
                .claim("nome", cliente.getNome())
                .claim("perfil", cliente.getPerfilInvestidor().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String validarToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}


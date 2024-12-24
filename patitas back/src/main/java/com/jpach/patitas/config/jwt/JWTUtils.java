package com.jpach.patitas.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JWTUtils {
  @Value("${jwt.secret.key}")
  private String secretKey;
  @Value("${jwt.time.expiration}")
  private String timeExpiration;

  // Generamos el token
  public String generateToken(String username) {

    // builder() genera
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de creacion del token
        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
        .signWith(getKey(), SignatureAlgorithm.HS256) // Firma del metodo
        .compact();
  }

  // Obtenemos la firma del token
  public Key getKey() {

    // Decodificamos la clave y la volvemos a encriptar en bytes
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    // Generamos la clave en HMAC para firmar el token
    return Keys.hmacShaKeyFor(keyBytes);
  }

  // Validamos el token
  public boolean isTokenValid(String token) {

    try {

      // parseBuilder() verifica
      Jwts.parserBuilder()
          .setSigningKey(getKey())
          .build()
          .parseClaimsJws(token)
          .getBody();

      return true;

    } catch (Exception e) {
      log.error(token, e.getMessage());

      return false;
    }

  }

  // Obtenemos todos los claims que comforman el token
  public Claims getClaimsToken(String token) {

    return Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody();

  }
  
  // Obtenemos un claims en especifico
  public <T> T getClaim(String token, Function<Claims, T> claimsFuntion) {
    Claims claims = getClaimsToken(token);
    return claimsFuntion.apply(claims);
  }

  // conseguimos el username del claim  con las funciones getClaimsToken y getClaim
  public String ClaimUsernameFromToken(String token) {
    return getClaim(token, Claims::getSubject);
  }

}

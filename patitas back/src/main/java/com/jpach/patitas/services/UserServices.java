package com.jpach.patitas.services;

import com.jpach.patitas.interfaces.ICRUD;
import com.jpach.patitas.models.Counts;
import com.jpach.patitas.models.Movements;
import com.jpach.patitas.models.Services;
import com.jpach.patitas.models.Users;
import com.jpach.patitas.repositories.CountRepository;
import com.jpach.patitas.repositories.MovementRepository;
import com.jpach.patitas.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
public class UserServices implements ICRUD<Users, Long>, UserDetailsService {

  // Atributo para indicar si hay bono de bienvenida
  private Boolean bonus = true;

  // Inyectamos las clases repository
  @Autowired
  private UserRepository UR;
  @Autowired
  private CountRepository CR;
  @Autowired
  private MovementRepository MR;

  // Metodo sobreescribido de la clase ICRUD
  @Override
  public ResponseEntity<?> save(Users data) {

    // Valor del bonO
    Integer valueBonus = 0;

    // Si hay bono se cargan 1000 en la variable valueBonus
    if (this.bonus) {
      valueBonus = 1000;
    }

    // Funcion que la que hereda la clase repository UserRepository de JpaRepository
    UR.save(data);

    Counts countUser = new Counts(0L, BigDecimal.valueOf(valueBonus), data);

    // Funcion que la que hereda la clase repository CountRepository de
    // JpaRepository
    CR.save(countUser);

    if (this.bonus) {
      // Si el bono de bienvenida esta habilitado se crea el objeto bonusMovemt para
      // realizar la persintencia en la base de datos
      Movements bonusMovement = new Movements(
          null, BigDecimal.valueOf(valueBonus), true,
          new Services(1L, null, null), data);

      // Funcion que la que hereda la clase repository MovementRepository de
      // JpaRepository
      MR.save(bonusMovement);
    }

    return new ResponseEntity<>(data, HttpStatus.CREATED);

  }

  

  // Indicaremos a Spring que los usuarios para la autenticacion van a estar en la
  // base de datos
  @Override
  public UserDetails loadUserByUsername(String email) {
    log.info("Aqui estamos !!!!");

    try {

      Users loadUser = UR.findUserByEmail(email)
          .orElseThrow(() -> new UsernameNotFoundException("Email introducido no existe en al base de datos.."));

      

      /*
       * Crea una colección inmutable que contiene un único objeto
       * SimpleGrantedAuthority se obtiene el nombre del rol y se concatena a "ROLE_"
       */
      Collection<GrantedAuthority> authorities = Collections.singleton(
          new SimpleGrantedAuthority("ROLE_".concat(loadUser.getRole().getName())));
      return new User(loadUser.getEmail(), loadUser.getPassword(), true, true, true, true, authorities);
    } catch (Exception e) {

      log.info(
          "65498644564341654061224945646554465567465+4+654654654654654274324234234234+654657465762762765765756746574627465745746274267426465745674657465745274327456746274627426746274657465746574657456746247264567465796795797");
      return null;
    }

  }

}

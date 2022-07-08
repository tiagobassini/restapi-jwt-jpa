package org.apirest.example.domain.repository;

import org.apirest.example.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}

package com.BACKTP6.BACKTP6.repositories;

import com.BACKTP6.BACKTP6.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}

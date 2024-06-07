package com.BACKTP6.BACKTP6.services;

import com.BACKTP6.BACKTP6.entities.Pedido;
import com.BACKTP6.BACKTP6.entities.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface UsuarioService {


    public List<Usuario> findAll() throws Exception;
    public Usuario findById(Long id) throws Exception;
    public Usuario create(Usuario usuario) throws Exception;
    public Usuario update(Long id, Usuario usuario) throws Exception;
    public String delete(Long id) throws Exception;
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);


}

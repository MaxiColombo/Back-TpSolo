package com.BACKTP6.BACKTP6.services;

import com.BACKTP6.BACKTP6.entities.Pedido;
import com.BACKTP6.BACKTP6.entities.Usuario;
import com.BACKTP6.BACKTP6.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioServiceImplementation implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public List<Usuario> findAll() throws Exception {
        try{

            List<Usuario> entities = usuarioRepository.findAll();
            return entities;

        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Usuario findById(Long id) throws Exception {
        try{

            Optional<Usuario> entityOptional = usuarioRepository.findById(id);
            return entityOptional.get();

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Usuario create(Usuario usuario) throws Exception {
        try{
            Usuario usuarioSaved;
            usuarioSaved = usuarioRepository.save(usuario);
            return usuarioSaved;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Usuario update(Long id, Usuario usuario) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();
            usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());
            if (!usuario.getClave().equals(usuarioExistente.getClave())) {
                usuarioExistente.setClave(usuario.encriptarClave(usuario.getClave()));
            }
            usuarioExistente.setRol(usuario.getRol());
            return usuarioRepository.save(usuarioExistente);
        } else {
            throw new Exception("Usuario no encontrado");
        }
    }

    @Override
    @Transactional
    public String delete(Long id) throws Exception {
        try{
            usuarioRepository.deleteById(id);
            return "Usuario eliminado";
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Optional<Usuario> findByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
}

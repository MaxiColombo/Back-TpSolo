package com.BACKTP6.BACKTP6.controllers;

import com.BACKTP6.BACKTP6.entities.Usuario;
import com.BACKTP6.BACKTP6.repositories.UsuarioRepository;

import com.BACKTP6.BACKTP6.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {



    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        System.out.println("ESTOY ACA");
        Optional<Usuario> usuarioDB = usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario());
        if (usuarioDB.isPresent() && usuarioDB.get().getClave().equals(usuario.encriptarClave(usuario.getClave()))) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("usuario", usuarioDB.get());
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Usuario y/o Clave incorrectos, vuelva a intentar");
            return ResponseEntity.ok(response);
        }
    }


    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }


    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.create(usuario);
            return ResponseEntity.status(201).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al crear el usuario");
        }
    }
}

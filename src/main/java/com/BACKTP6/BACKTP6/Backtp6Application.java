package com.BACKTP6.BACKTP6;

import com.BACKTP6.BACKTP6.entities.CategoriaInstrumento;
import com.BACKTP6.BACKTP6.entities.Usuario;
import com.BACKTP6.BACKTP6.repositories.CategoriaInstrumentoRepository;
import com.BACKTP6.BACKTP6.repositories.InstrumentoRespository;
import com.BACKTP6.BACKTP6.repositories.PedidoRepository;
import com.BACKTP6.BACKTP6.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Backtp6Application {


    @Autowired
    private UsuarioRepository usuarioRepository;

    public static void main(String[] args) {
        SpringApplication.run(Backtp6Application.class, args);
        System.out.println("Funciono");
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:5173/").allowedMethods("*").allowedHeaders("*");
            }
        };
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {

            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setClave("admin123");
            admin.setRol("Admin");
            usuarioRepository.save(admin);

            Usuario operador = new Usuario();
            operador.setNombreUsuario("operador");
            operador.setClave("operador123");
            operador.setRol("Operador");
            usuarioRepository.save(operador);

            Usuario visor = new Usuario();
            visor.setNombreUsuario("visor");
            visor.setClave("visor123");
            visor.setRol("Visor");
            usuarioRepository.save(visor);
        };
    }
}

package com.BACKTP6.BACKTP6.services;


import com.BACKTP6.BACKTP6.entities.CategoriaInstrumento;
import com.BACKTP6.BACKTP6.repositories.CategoriaInstrumentoRepository;


import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaInstrumentoServiceImplementation implements CategoriaInstrumentoService{

    @Autowired
    private CategoriaInstrumentoRepository categoriaRepository;


    @PostConstruct
    public void init() {
        if (categoriaRepository.count() == 0) {
            crearCategorias();
        }
    }

    private void crearCategorias() {
        List<String> nombres = Arrays.asList("Cuerda", "Viento", "Percusion", "Teclado", "Electronico");
        nombres.forEach(nombre -> {
            CategoriaInstrumento categoria = new CategoriaInstrumento();
            categoria.setDenominacion(nombre);
            categoriaRepository.save(categoria);
        });
    }

    @Override
    @Transactional
    public List<CategoriaInstrumento> findAll() throws Exception {

        try{

            List<CategoriaInstrumento> entities = categoriaRepository.findAll();
            return entities;

        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CategoriaInstrumento findById(Long id) throws Exception {
        try{

            Optional<CategoriaInstrumento> entityOptional = categoriaRepository.findById(id);
            return entityOptional.get();

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    @Transactional
    public CategoriaInstrumento create(CategoriaInstrumento categoriaInstrumento) throws Exception {
        try{
            CategoriaInstrumento categoriaSaved;
            categoriaSaved = categoriaRepository.save(categoriaInstrumento);
            return categoriaSaved;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    @Transactional
    public CategoriaInstrumento update(Long id, CategoriaInstrumento categoriaInstrumento) throws Exception{
        try{
            return categoriaRepository.findById(id)
                    .map(category -> {
                        category.setDenominacion(categoriaInstrumento.getDenominacion());
                        return categoriaRepository.save(category);
                    }).orElseThrow(()->new Exception("Categoria no encontrado"));
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }


    }

    @Override
    @Transactional
    public String delete(Long id) throws Exception {
        try{
            categoriaRepository.deleteById(id);
            return "Categoria eliminado";
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    // metodo extra - guardar muchas categorias
    @Transactional
    public List<CategoriaInstrumento> guardarCategorias(List<CategoriaInstrumento> categorias) {
        return categoriaRepository.saveAll(categorias);
    }

}

package com.BACKTP6.BACKTP6.services;

import com.BACKTP6.BACKTP6.entities.CategoriaInstrumento;
import com.BACKTP6.BACKTP6.repositories.CategoriaInstrumentoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriaInstrumentoService {

    public List<CategoriaInstrumento> findAll() throws Exception;


    public CategoriaInstrumento findById(Long id) throws Exception;


    public CategoriaInstrumento create(CategoriaInstrumento categoriaInstrumento) throws Exception;


    public CategoriaInstrumento update(Long id, CategoriaInstrumento categoriaInstrumento) throws Exception;


    public String delete(Long id) throws Exception;
}

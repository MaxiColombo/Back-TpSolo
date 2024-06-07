package com.BACKTP6.BACKTP6.repositories;

import com.BACKTP6.BACKTP6.entities.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InstrumentoRespository extends JpaRepository<Instrumento,Long> {
    List<Instrumento> findByCategoriaId(Long categoriaId);

}

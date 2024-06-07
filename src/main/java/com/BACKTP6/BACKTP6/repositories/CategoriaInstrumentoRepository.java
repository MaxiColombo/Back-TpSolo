package com.BACKTP6.BACKTP6.repositories;

import com.BACKTP6.BACKTP6.entities.CategoriaInstrumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaInstrumentoRepository extends JpaRepository<CategoriaInstrumento,Long> {
}

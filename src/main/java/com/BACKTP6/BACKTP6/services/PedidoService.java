package com.BACKTP6.BACKTP6.services;

import com.BACKTP6.BACKTP6.entities.Instrumento;
import com.BACKTP6.BACKTP6.entities.Pedido;
import com.BACKTP6.BACKTP6.repositories.InstrumentoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
@Service
public interface PedidoService {


    public List<Pedido> findAll() throws Exception;


    public Pedido findById(Long id) throws Exception;


    public Pedido create(Pedido pedido) throws Exception;


    public Pedido update(Long id, Pedido pedido) throws Exception;


    public String delete(Long id) throws Exception;

    ByteArrayInputStream exportAllInstrumentos() throws Exception;

}

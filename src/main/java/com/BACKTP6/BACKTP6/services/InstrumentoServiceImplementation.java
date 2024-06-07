package com.BACKTP6.BACKTP6.services;

import com.BACKTP6.BACKTP6.entities.Instrumento;
import com.BACKTP6.BACKTP6.repositories.CategoriaInstrumentoRepository;
import com.BACKTP6.BACKTP6.repositories.InstrumentoRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InstrumentoServiceImplementation implements InstrumentoService{

    @Autowired
    private InstrumentoRespository instrumentoRepository;
    @Override
    @Transactional
    public List<Instrumento> findAll() throws Exception {

        try{

            List<Instrumento> entities = instrumentoRepository.findAll();
            return entities;

        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Instrumento findById(Long id) throws Exception {
        try{

            Optional<Instrumento> entityOptional = instrumentoRepository.findById(id);
            return entityOptional.get();

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    @Transactional
    public Instrumento create(Instrumento instrumento) throws Exception {
        try{
            Instrumento instrumentoSaved;
            instrumentoSaved = instrumentoRepository.save(instrumento);
            return instrumentoSaved;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    @Transactional
    public Instrumento update(Long id,Instrumento instrumento) throws Exception{
        try{
            return instrumentoRepository.findById(id)
                    .map(inst -> {
                        inst.setInstrumento(instrumento.getInstrumento());
                        inst.setMarca(instrumento.getMarca());
                        inst.setModelo(instrumento.getModelo());
                        inst.setImagen(instrumento.getImagen());
                        inst.setPrecio(instrumento.getPrecio());
                        inst.setCostoEnvio(instrumento.getCostoEnvio());
                        inst.setCantidadVendida(instrumento.getCantidadVendida());
                        inst.setDescripcion(instrumento.getDescripcion());
                        inst.setCategoria(instrumento.getCategoria());
                        return instrumentoRepository.save(inst);
                    }).orElseThrow(() -> new Exception("Instrumento no encontrado"));
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public String delete(Long id) throws Exception {
        try{
            instrumentoRepository.deleteById(id);
            return "Instrumento eliminado";
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Instrumento> findByCategoria(Long categoriaId) {
        return instrumentoRepository.findByCategoriaId(categoriaId);
    }



}

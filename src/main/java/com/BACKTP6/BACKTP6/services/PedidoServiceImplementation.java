package com.BACKTP6.BACKTP6.services;

import com.BACKTP6.BACKTP6.entities.Instrumento;
import com.BACKTP6.BACKTP6.entities.Pedido;
import com.BACKTP6.BACKTP6.entities.PedidoDetalle;
import com.BACKTP6.BACKTP6.repositories.InstrumentoRespository;
import com.BACKTP6.BACKTP6.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Repository
public class PedidoServiceImplementation implements PedidoService{
    @Autowired
    private PedidoRepository pedidoRepository;
    @Override
    @Transactional
    public List<Pedido> findAll() throws Exception {
        try{

            List<Pedido> entities = pedidoRepository.findAll();
            return entities;

        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Pedido findById(Long id) throws Exception {
        try{

            Optional<Pedido> entityOptional = pedidoRepository.findById(id);
            return entityOptional.get();

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Pedido create(Pedido pedido) throws Exception {
        try{
            Pedido pedidoSaved;
            pedidoSaved = pedidoRepository.save(pedido);
            return pedidoSaved;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Pedido update(Long id, Pedido pedido) throws Exception {
        try {
            return pedidoRepository.findById(id)
                    .map(existingPedido -> {
                        existingPedido.setFechaPedido(pedido.getFechaPedido());
                        existingPedido.setTotalPedido(pedido.getTotalPedido());
                        return pedidoRepository.save(existingPedido);
                    }).orElseThrow(() -> new Exception("Pedido not found"));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public String delete(Long id) throws Exception {
        try{
            pedidoRepository.deleteById(id);
            return "Pedido eliminado";
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ByteArrayInputStream exportAllInstrumentos() throws Exception {
        String[] columns = {"Fecha Pedido","Instrumento","Marca","Modelo","Cantidad","Precio","Subtotal"};

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Pedidos");
        Row row = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<Pedido> pedidos = this.findAll();
        int initRow = 1;
        for (Pedido pedido : pedidos) {
            for (PedidoDetalle detalle : pedido.getPedidosDetalle()) {
                row = sheet.createRow(initRow);
                row.createCell(0).setCellValue(pedido.getFechaPedido());
                row.createCell(1).setCellValue(detalle.getInstrumento().getInstrumento());
                row.createCell(2).setCellValue(detalle.getInstrumento().getMarca());
                row.createCell(3).setCellValue(detalle.getInstrumento().getModelo());
                row.createCell(4).setCellValue(detalle.getCantidad());
                row.createCell(5).setCellValue(detalle.getInstrumento().getPrecio());
                row.createCell(6).setCellValue(detalle.getCantidad() * detalle.getInstrumento().getPrecio());
                initRow++;
            }
        }

        workbook.write(stream);

        return new ByteArrayInputStream(stream.toByteArray());
    }
}

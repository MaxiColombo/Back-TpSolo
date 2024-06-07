package com.BACKTP6.BACKTP6.controllers;

import com.BACKTP6.BACKTP6.entities.Instrumento;
import com.BACKTP6.BACKTP6.entities.Pedido;
import com.BACKTP6.BACKTP6.entities.PedidoDetalle;
import com.BACKTP6.BACKTP6.repositories.InstrumentoRespository;
import com.BACKTP6.BACKTP6.repositories.PedidoDetalleRepository;
import com.BACKTP6.BACKTP6.repositories.PedidoRepository;
import com.BACKTP6.BACKTP6.services.PedidoService;
import com.BACKTP6.BACKTP6.services.PedidoServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    @Autowired
    private InstrumentoRespository instrumentoRepository;


    @PostMapping("/pedidos")
    public ResponseEntity<Map<String, Object>> crearPedido(@RequestBody List<PedidoDetalle> detalles) {
        double total = 0;
        Pedido pedido = new Pedido();
        pedido.setFechaPedido(new Date());

        for (PedidoDetalle detalle : detalles) {
            Instrumento instrumento = instrumentoRepository.findById(detalle.getInstrumento().getId()).orElse(null);
            if (instrumento == null) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Instrumento no encontrado"));
            }
            detalle.setInstrumento(instrumento);
            detalle.setPedido(pedido);
            total += instrumento.getPrecio() * detalle.getCantidad();
        }

        pedido.setTotalPedido(total);
        pedido.setPedidosDetalle(detalles);
        pedidoRepository.save(pedido);

        Map<String, Object> response = new HashMap<>();
        response.put("id", pedido.getId());
        response.put("message", "El pedido se guardó correctamente");

        return ResponseEntity.ok(response);
    }

   @GetMapping("/all")
   public ResponseEntity<?> getAllPedidos() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error retrieving pedidos");
        }
    }
    @GetMapping("/exportAll")
    public ResponseEntity<InputStreamResource> exportAllInstrumentos() throws Exception{
        ByteArrayInputStream stream = pedidoService.exportAllInstrumentos();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=pedidos.xls");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }


//    @GetMapping("/get/{id}")
//    public ResponseEntity<?> getPedidoById(@PathVariable Long id) {
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.findById(id));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido not found");
//        }
//    }



//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updatePedido(@PathVariable Long id, @RequestBody Pedido pedido) {
//        try {
//            pedido.setId(id);
//            return ResponseEntity.ok(pedidoService.update(id, pedido));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido not found");
//        }
//    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deletePedido(@PathVariable Long id) {
//        try {
//            pedidoService.delete(id);
//            return ResponseEntity.ok().body("Pedido deleted successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido not found");
//        }
//    }

    // Additional filter method if needed for Pedido
    // @GetMapping("/all/filter")
    // public ResponseEntity<?> filterPedido(@RequestParam Long someParameter) {
    //     try {
    //         // Add your filtering logic here
    //         // return ResponseEntity.status(HttpStatus.OK).body(pedidoService.findBySomeParameter(someParameter));
    //         return ResponseEntity.status(HttpStatus.OK).body(pedidoService.findAll());
    //     } catch(Exception e){
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido not found");
    //     }
    // }
}

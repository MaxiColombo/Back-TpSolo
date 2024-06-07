package com.BACKTP6.BACKTP6.controllers;




import com.BACKTP6.BACKTP6.entities.Pedido;
import com.BACKTP6.BACKTP6.entities.PreferenceMP;
import com.BACKTP6.BACKTP6.services.MercadoPagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/mercadopago")
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping("/create-preference")
    public PreferenceMP createPreference(@RequestBody Pedido pedido) {
        return mercadoPagoService.createPreference(pedido);
    }
}
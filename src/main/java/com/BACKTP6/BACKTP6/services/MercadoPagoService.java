package com.BACKTP6.BACKTP6.services;

import com.BACKTP6.BACKTP6.entities.Pedido;
import com.BACKTP6.BACKTP6.entities.PreferenceMP;
import com.BACKTP6.BACKTP6.repositories.PreferenceMPRepository;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {
    @Autowired
    private PreferenceMPRepository preferenceMPRepository;

    public PreferenceMP createPreference(Pedido pedido) {


        try {
            MercadoPagoConfig.setAccessToken("TEST-7629241533934885-052919-7a6440fe4edd105c19359e015a04ef96-464528256");

            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id("1234")
                    .title("Pedido Nombre")
                    .description("Pedido realizado desde el carrito")
                    .pictureUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRooqX_tEjlq63fL2GQIpdJ50tuKZ7qT-qJ8A&s")
                    .quantity(1)
                    .currencyId("ARG")
                    .unitPrice(new BigDecimal(23232))
                    .build();

            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backURL = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:3000/success")
                    .pending("http://localhost:3000/mppending")
                    .failure("http://localhost:3000/mpfailure")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backURL)
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            PreferenceMP mpPreference = new PreferenceMP();
            mpPreference.setStatusCode(preference.getResponse().getStatusCode());
            mpPreference.setId(preference.getId());

            // Guardar en la base de datos
            preferenceMPRepository.save(mpPreference);


            return mpPreference;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.BACKTP6.BACKTP6.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "pedido")
public class Pedido extends Base{

    private Date fechaPedido;

    private Double totalPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoDetalle> pedidosDetalle = new ArrayList<PedidoDetalle>();


}

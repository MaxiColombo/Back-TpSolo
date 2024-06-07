package com.BACKTP6.BACKTP6.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "categorias_instrumentos")
public class CategoriaInstrumento extends Base{

    private String denominacion;


}

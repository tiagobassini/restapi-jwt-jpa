package org.apirest.example.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;



@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco_unitario", precision = 20, scale = 2)
    private BigDecimal preco;


}

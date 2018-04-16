package com.comexport.lancamentocontabil.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = ContaContabil.COLLECTION)
public class ContaContabil {

  public static final String COLLECTION = "conta-contabil";

  @Id
  private String id;

  @Indexed(name = "numero_uk", unique = true)
  private Long numero;

  private String descricao;
}

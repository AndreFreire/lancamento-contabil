package com.comexport.lancamentocontabil.entity;

import java.time.LocalDate;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = LancamentoContabil.COLLECTION)
public class LancamentoContabil {

  public static final String COLLECTION = "lancamento-contabil";

  @Id
  private String id;

  @Indexed
  private Long contaContabil;

  private LocalDate data;
  private Double valor;
}
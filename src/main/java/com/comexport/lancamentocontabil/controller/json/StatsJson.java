package com.comexport.lancamentocontabil.controller.json;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatsJson implements Serializable {

  private static final long serialVersionUID = 3228577496861184751L;

  private double soma;
  private double min;
  private double max;
  private double media;
  private int qtde;
}

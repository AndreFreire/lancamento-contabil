package com.comexport.lancamentocontabil.controller.json;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaContabilJson implements Serializable {

  private static final long serialVersionUID = 5445042080597953803L;

  @NotNull
  private Long numero;

  @NotEmpty
  private String descricao;
}

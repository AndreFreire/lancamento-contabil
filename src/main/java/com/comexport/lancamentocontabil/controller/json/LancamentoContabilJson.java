package com.comexport.lancamentocontabil.controller.json;

import com.comexport.lancamentocontabil.conf.json.LocalDateDeserializer;
import com.comexport.lancamentocontabil.conf.json.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoContabilJson implements Serializable {

  private static final long serialVersionUID = -7506807239840127802L;

  @NotNull
  private Long contaContabil;

  @NotNull
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate data;

  @NotNull
  private Double valor;
}
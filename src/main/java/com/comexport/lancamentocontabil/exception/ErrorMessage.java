package com.comexport.lancamentocontabil.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class ErrorMessage {

  private List<String> errors;
}
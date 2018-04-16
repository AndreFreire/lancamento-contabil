package com.comexport.lancamentocontabil.exception;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class MessageBody implements Serializable {

  private static final long serialVersionUID = 6368671874550318077L;

  private String message;
}

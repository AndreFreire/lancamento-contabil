package com.comexport.lancamentocontabil.exception;

import static java.lang.String.format;

public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = -5563989047853574507L;

  public NotFoundException(final String entity) {
    super(format("Entidade não encontrada: %s", entity));
  }
}
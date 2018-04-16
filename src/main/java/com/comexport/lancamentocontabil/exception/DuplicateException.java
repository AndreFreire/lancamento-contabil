package com.comexport.lancamentocontabil.exception;

public class DuplicateException extends RuntimeException {

  private static final long serialVersionUID = 4844344703027766254L;

  public DuplicateException(final String entity) {
    super(String.format("Entidade já cadastrada: %s", entity));
  }
}

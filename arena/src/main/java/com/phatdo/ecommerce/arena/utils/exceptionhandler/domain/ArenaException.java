package com.phatdo.ecommerce.arena.utils.exceptionhandler.domain;

import lombok.Getter;

@Getter
public class ArenaException extends Exception {
  private final ArenaError error;

  public ArenaException(ArenaError error) {
    this.error = error;
  }
}

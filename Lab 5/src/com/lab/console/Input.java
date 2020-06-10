package com.lab.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** Input class */
public final class Input {
  private BufferedReader reader;

  public Input() {
    reader = new BufferedReader(new InputStreamReader(System.in));
  }

  public void setReader(BufferedReader reader) {
    this.reader = reader;
  }

  /**
   * Reads and processes a line
   *
   * @return Line
   */
  public String readLine() {
    try {
      return reader.readLine().trim().replaceAll(" +", " ");
    } catch (IOException | NullPointerException ex) {
      return null;
    }
  }
}

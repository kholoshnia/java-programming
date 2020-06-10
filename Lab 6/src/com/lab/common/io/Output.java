package com.lab.common.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/** Output class */
public class Output {
  private BufferedWriter writer;

  /** Default Writer constructor. Takes System.out */
  public Output() {
    writer = new BufferedWriter(new OutputStreamWriter(System.out));
  }

  /**
   * Writer constructor
   *
   * @param writer writer to write to
   */
  public Output(BufferedWriter writer) {
    this.writer = writer;
  }

  /**
   * Prints message with specified color and decorator
   *
   * @param message message to print
   * @param color color of the message
   * @param decorator decorator for the message
   */
  public void write(String message, Colors color, Decorators decorator) throws OutputException {
    try {
      writer.write(
          color.getColorCode()
              + decorator.getDecoratorCode()
              + message
              + Colors.RESET.getColorCode());
      writer.flush();
    } catch (IOException e) {
      throw new OutputException("Ошибка вывода", e);
    }
  }

  /**
   * Prints message
   *
   * @param message message to print
   */
  public void write(String message) throws OutputException {
    write(message, Colors.NO);
  }

  /**
   * Prints message with specified color
   *
   * @param message message to print
   * @param color color of the message
   */
  public void write(String message, Colors color) throws OutputException {
    write(message, color, Decorators.NO);
  }

  /**
   * Prints message with specified color and decorator
   *
   * @param message message to print
   * @param decorator decorator for the message
   */
  public void write(String message, Decorators decorator) throws OutputException {
    write(message, Colors.NO, decorator);
  }

  /** Prints line separator */
  public void writeln() throws OutputException {
    write(System.lineSeparator());
  }

  /**
   * Prints message with specified color
   *
   * @param message message to print
   */
  public void writeln(String message) throws OutputException {
    writeln(message, Colors.NO, Decorators.NO);
  }

  /**
   * Prints message with line separator symbol with specified color
   *
   * @param message message to print
   * @param color color of the message
   */
  public void writeln(String message, Colors color) throws OutputException {
    writeln(message, color, Decorators.NO);
  }

  /**
   * Prints message with line separator symbol with specified color and specified decorator
   *
   * @param message message to print
   * @param color color of the message
   * @param decorator decorator for the message
   */
  public void writeln(String message, Colors color, Decorators decorator) throws OutputException {
    try {
      writer.write(
          color.getColorCode()
              + decorator.getDecoratorCode()
              + message
              + Colors.RESET.getColorCode());
      writer.newLine();
      writer.flush();
    } catch (IOException e) {
      throw new OutputException("Ошибка вывода", e);
    }
  }

  /**
   * Prints message with line separator symbol with specified decorator
   *
   * @param message message to print
   * @param decorator decorator for the message
   */
  public void writeln(String message, Decorators decorator) throws OutputException {
    writeln(message, Colors.NO, decorator);
  }

  /** Closes buffer */
  public void close() throws OutputException {
    try {
      writer.close();
    } catch (IOException e) {
      throw new OutputException("Ошибка закрытия потока вывода", e);
    }
  }

  /** Available colors for console output */
  public enum Colors {
    NO(""),
    RED("\u001b[31m"),
    CYAN("\u001b[36m"),
    RESET("\u001b[0m"),
    BLUE("\u001b[34m"),
    BLACK("\u001b[30m"),
    GREEN("\u001b[32m"),
    WHITE("\u001b[37m"),
    YELLOW("\u001b[33m"),
    MAGENTA("\u001b[35m");

    private String colorCode;

    /**
     * Colors enum constructor
     *
     * @param colorCode color code for console
     */
    Colors(String colorCode) {
      this.colorCode = colorCode;
    }

    /**
     * Returns color code for console
     *
     * @return Color code for console
     */
    public String getColorCode() {
      return colorCode;
    }
  }

  /** Available decorators for console output */
  public enum Decorators {
    NO(""),
    BOLD("\u001b[1m"),
    RESET("\u001b[0m"),
    RESERVED("\u001b[7m"),
    UNDERLINE("\u001b[4m");

    private String decoratorCode;

    /**
     * Decorators enum constructor
     *
     * @param decoratorCode decorator code for console
     */
    Decorators(String decoratorCode) {
      this.decoratorCode = decoratorCode;
    }

    /**
     * Returns decorator code for console
     *
     * @return Decorator code for console
     */
    public String getDecoratorCode() {
      return decoratorCode;
    }
  }
}

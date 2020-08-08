package ru.lab.console;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/** Output class */
public final class Output {
  private BufferedWriter writer;

  public Output() {
    writer = new BufferedWriter(new OutputStreamWriter(System.out));
  }

  public void setWriter(BufferedWriter writer) {
    this.writer = writer;
  }

  /**
   * Prints message
   *
   * @param message message to print
   */
  public void print(String message) {
    print(message, Colors.RESET);
  }

  /**
   * Prints message with specified color
   *
   * @param message message to print
   * @param color color of the message
   */
  public void print(String message, Colors color) {
    try {
      writer.write(color.getColorCode() + message + Colors.RESET.getColorCode());
      writer.flush();
    } catch (IOException ignored) {
    }
  }

  /**
   * Prints message with specified color and decorator
   *
   * @param message message to print
   * @param color color of the message
   * @param decorator decorator for the message
   */
  public void print(String message, Colors color, Decorators decorator) {
    try {
      writer.write(
          color.getColorCode()
              + decorator.getDecoratorCode()
              + message
              + Colors.RESET.getColorCode());
      writer.flush();
    } catch (IOException ignored) {
    }
  }

  /**
   * Prints message with specified color and decorator
   *
   * @param message message to print
   * @param decorator decorator for the message
   */
  public void print(String message, Decorators decorator) {
    try {
      writer.write(decorator.getDecoratorCode() + message + Decorators.RESET.getDecoratorCode());
      writer.flush();
    } catch (IOException ignored) {
    }
  }

  /**
   * Prints message with specified color
   *
   * @param message message to print
   */
  public void println(String message) {
    println(message, Colors.RESET);
  }

  /**
   * Prints message with line separator symbol with specified color
   *
   * @param message message to print
   * @param color color of the message
   */
  public void println(String message, Colors color) {
    try {
      writer.write(
          color.getColorCode() + message + Colors.RESET.getColorCode() + System.lineSeparator());
      writer.flush();
    } catch (IOException ignored) {
    }
  }

  /**
   * Prints message with line separator symbol with specified color and specified decorator
   *
   * @param message message to print
   * @param color color of the message
   * @param decorator decorator for the message
   */
  public void println(String message, Colors color, Decorators decorator) {
    try {
      writer.write(
          color.getColorCode()
              + decorator.getDecoratorCode()
              + message
              + Colors.RESET.getColorCode()
              + System.lineSeparator());
      writer.flush();
    } catch (IOException ignored) {
    }
  }

  /**
   * Prints message with line separator symbol with specified decorator
   *
   * @param message message to print
   * @param decorator decorator for the message
   */
  public void println(String message, Decorators decorator) {
    try {
      writer.write(
          decorator.getDecoratorCode()
              + message
              + Decorators.RESET.getDecoratorCode()
              + System.lineSeparator());
      writer.flush();
    } catch (IOException ignored) {
    }
  }

  /** Available colors for console output */
  public enum Colors {
    BLACK("\u001b[30m"),
    BLUE("\u001b[34m"),
    CYAN("\u001b[36m"),
    GREEN("\u001b[32m"),
    MAGENTA("\u001b[35m"),
    RED("\u001b[31m"),
    RESET("\u001b[0m"),
    WHITE("\u001b[37m"),
    YELLOW("\u001b[33m");

    private String colorCode;

    Colors(String colorCode) {
      this.colorCode = colorCode;
    }

    public String getColorCode() {
      return colorCode;
    }
  }

  /** Available decorators for console output */
  public enum Decorators {
    BOLD("\u001b[1m"),
    UNDERLINE("\u001b[4m"),
    RESERVED("\u001b[7m"),
    RESET("\u001b[0m");

    private String decoratorCode;

    Decorators(String decoratorCode) {
      this.decoratorCode = decoratorCode;
    }

    public String getDecoratorCode() {
      return decoratorCode;
    }
  }
}

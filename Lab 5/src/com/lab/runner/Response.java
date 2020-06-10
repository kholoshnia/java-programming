package com.lab.runner;

import com.lab.console.Output;

import java.util.List;

/** Response class contains response value and correctness value */
public final class Response {
  private Types type;
  private boolean correct;
  private List<String> response;

  public Response(boolean correct, List<String> response, Types type) {
    this.type = type;
    this.correct = correct;
    this.response = response;
  }

  /**
   * Returns response value
   *
   * @return Response value
   */
  public List<String> getResponse() {
    return response;
  }

  /**
   * Returns correctness value
   *
   * @return Correctness value
   */
  public boolean isCorrect() {
    return correct;
  }

  /**
   * Returns type of response
   *
   * @return Type of response
   */
  public Types getType() {
    return type;
  }

  /** Types of response */
  public enum Types {
    UNUSUAL(Output.Colors.BLUE),
    UNNECESSARY(Output.Colors.CYAN),
    CORRECT(Output.Colors.GREEN),
    FAIL(Output.Colors.MAGENTA),
    ERROR(Output.Colors.RED),
    TEXT(Output.Colors.RESET),
    MISSING(Output.Colors.YELLOW);

    private Output.Colors color;

    Types(Output.Colors color) {
      this.color = color;
    }

    public Output.Colors getColor() {
      return color;
    }
  }
}

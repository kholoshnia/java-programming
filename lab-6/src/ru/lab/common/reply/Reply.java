package ru.lab.common.reply;

import ru.lab.common.io.Output.Colors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/** Reply class contains reply value and correctness value */
public final class Reply implements Serializable {
  private Types type;
  private boolean correct;
  private List<String> reply;

  /**
   * Reply constructor
   *
   * @param correct correctness of reply
   * @param reply reply in form of string list
   * @param type type of reply
   */
  public Reply(boolean correct, List<String> reply, Types type) {
    this.type = type;
    this.correct = correct;
    this.reply = reply;
  }

  /**
   * Reply constructor
   *
   * @param reply reply in form of string list
   * @param type type of reply
   */
  public Reply(List<String> reply, Types type) {
    this.type = type;
    this.correct = true;
    this.reply = reply;
  }

  /**
   * Reply constructor
   *
   * @param correct correctness of reply
   * @param reply reply in form of string
   * @param type type of reply
   */
  public Reply(boolean correct, String reply, Types type) {
    this.type = type;
    this.correct = correct;
    this.reply = new ArrayList<>();
    this.reply.add(reply);
  }

  /**
   * Reply constructor
   *
   * @param reply reply in form of string
   * @param type type of reply
   */
  public Reply(String reply, Types type) {
    this.type = type;
    this.correct = true;
    this.reply = new ArrayList<>();
    this.reply.add(reply);
  }

  /**
   * Reply constructor
   *
   * @param correct correctness of reply
   * @param type type of reply
   */
  public Reply(boolean correct, Types type) {
    this.type = type;
    this.correct = correct;
    this.reply = new ArrayList<>();
    this.reply.add("");
  }

  /**
   * Reply constructor
   *
   * @param type type of reply
   */
  public Reply(Types type) {
    this.type = type;
    this.correct = true;
    this.reply = new ArrayList<>();
    this.reply.add("");
  }

  /**
   * Returns reply value
   *
   * @return reply value
   */
  public List<String> getReply() {
    return reply;
  }

  /**
   * Returns string list as string
   *
   * @return String list as string
   */
  public String getString() {
    return String.join("\n", reply);
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
   * Returns type of reply
   *
   * @return Type of reply
   */
  public Types getType() {
    return type;
  }

  /** Types of reply */
  public enum Types {
    FINE(Colors.BLUE, Level.FINE),
    INFO(Colors.RESET, Level.INFO),
    FINER(Colors.CYAN, Level.FINER),
    SEVERE(Colors.RED, Level.SEVERE),
    FINEST(Colors.GREEN, Level.FINEST),
    CONFIG(Colors.YELLOW, Level.CONFIG),
    WARNING(Colors.MAGENTA, Level.WARNING);

    private Level level;
    private Colors color;

    Types(Colors color, Level level) {
      this.color = color;
      this.level = level;
    }

    public Level getLevel() {
      return level;
    }

    public Colors getColor() {
      return color;
    }
  }
}

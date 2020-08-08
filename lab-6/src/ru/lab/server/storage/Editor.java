package ru.lab.server.storage;

import ru.lab.common.element.Worker;
import ru.lab.common.generators.Creator;
import ru.lab.common.generators.CreatorException;
import ru.lab.common.io.Input;
import ru.lab.common.io.InputException;
import ru.lab.common.io.Output;
import ru.lab.common.io.OutputException;
import ru.lab.server.commands.CommandsHistory;
import ru.lab.server.storage.collection.Collection;

import java.util.ArrayList;
import java.util.List;

/** Editor class contains data to be passed to command */
public final class Editor {
  private Input in;
  private Output out;
  private String value;
  private Worker worker;
  private boolean running;
  private boolean fromFile;
  private String dataFilePath;
  private Collection collection;
  private List<Integer> filesHashes;
  private CommandsHistory commandsHistory;

  public Editor() {
    value = null;
    running = true;
    fromFile = false;
    in = new Input();
    out = new Output();
    collection = new Collection();
    filesHashes = new ArrayList<>();
    commandsHistory = new CommandsHistory();
  }

  public String getDataFilePath() {
    return dataFilePath;
  }

  /**
   * Sets data file path
   *
   * @param dataFilePath data file path
   */
  public void setDataFilePath(String dataFilePath) {
    this.dataFilePath = dataFilePath;
  }

  public Worker getWorker() throws CreatorException, OutputException, InputException {
    if (fromFile) {
      worker = new Creator(in, out).create(); // TODO
    }
    worker.generateId();
    worker.generateCreationDate();
    return worker;
  }

  public void setWorker(Worker worker) {
    this.worker = worker;
  }

  /**
   * Returns true if read is from file
   *
   * @return True if read is from file
   */
  public boolean getFromFile() {
    return fromFile;
  }

  /**
   * Sets if read is from file
   *
   * @param fromFile true if from file
   */
  public void setFromFile(boolean fromFile) {
    this.fromFile = fromFile;
  }

  /**
   * Returns entered value
   *
   * @return Entered value
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value entered
   *
   * @param value new entered value
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Returns files hashes to catch recursion
   *
   * @return Files hashes
   */
  public List<Integer> getFilesHashes() {
    return filesHashes;
  }

  /**
   * Returns input
   *
   * @return Input
   */
  public Input getIn() {
    return in;
  }

  /**
   * Sets input
   *
   * @param in new input
   */
  public void setIn(Input in) {
    this.in = in;
  }

  /**
   * Returns output
   *
   * @return Output
   */
  public Output getOut() {
    return out;
  }

  /**
   * Sets output
   *
   * @param out new output
   */
  public void setOut(Output out) {
    this.out = out;
  }

  /** Clears files hashes list */
  public void clearFilesHashes() {
    filesHashes.clear();
  }

  /**
   * Returns running parameter Program stops if running parameter become false
   *
   * @return Running parameter
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Sets running parameter
   *
   * @param running new running parameter value
   */
  public void setRunning(boolean running) {
    this.running = running;
  }

  /**
   * Returns collection to work with
   *
   * @return Collection to work with
   */
  public Collection getCollection() {
    return collection;
  }

  public void setCollection(Collection collection) {
    this.collection = collection;
  }

  /**
   * Returns history of correctly executed commands
   *
   * @return History of correctly executed commands
   */
  public CommandsHistory getCommandHistory() {
    return commandsHistory;
  }
}

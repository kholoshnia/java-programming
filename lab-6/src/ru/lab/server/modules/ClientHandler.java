package ru.lab.server.modules;

import ru.lab.common.element.Worker;
import ru.lab.common.exchange.Request;
import ru.lab.common.io.Output;
import ru.lab.common.io.OutputException;
import ru.lab.common.reply.Reply;
import ru.lab.common.transceiver.Receiver;
import ru.lab.common.transceiver.ReceiverException;
import ru.lab.common.transceiver.Sender;
import ru.lab.common.transceiver.SenderException;
import ru.lab.server.commands.AvailableCommands;
import ru.lab.server.commands.Command;
import ru.lab.server.executor.Executor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/** ClientHandler class */
public class ClientHandler implements Runnable {
  private Output out;
  private Executor executor;
  private SocketChannel client;
  private Sender sender;
  private Receiver receiver;
  private Logger logger;

  public ClientHandler(SocketChannel client, Executor executor) {
    out = new Output();
    this.client = client;
    this.executor = executor;
    new Setup().logger();
    logger = Logger.getLogger(Server.class.getName());
  }

  /** Setups connection */
  public void setup() {
    try {
      sender = new Sender(new ObjectOutputStream(client.socket().getOutputStream()));
    } catch (IOException e) {
      logger.severe("Ошибка создания потока вывода");
    }

    try {
      receiver = new Receiver(new ObjectInputStream(client.socket().getInputStream()));
    } catch (IOException e) {
      logger.severe("Ошибка создания потока ввода");
    }
  }

  /** Closes connection */
  public void close() {
    try {
      sender.close();
    } catch (SenderException e) {
      logger.log(Level.SEVERE, "Ошибка закрытия потока вывода", e);
    }
    try {
      receiver.close();
    } catch (ReceiverException e) {
      logger.log(Level.SEVERE, "Ошибка закрытия потока ввода", e);
    }
    try {
      client.close();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Ошибка закрытия соединения", e);
    }
  }

  /**
   * Checks if command requires element
   *
   * @param request request by client
   */
  public void requireElement(Request request) {
    Command com = new AvailableCommands().getCommand(request.getCommandName());
    if (com != null) {
      String parameters = com.getParameters();
      if (parameters != null) {
        if (parameters.contains("{element}")) {
          try {
            sender.writeBoolean(true);
          } catch (SenderException e) {
            logger.log(Level.SEVERE, "Ошибка отправки сообщения типа boolean", e);
          }
          try {
            executor.getEditor().setWorker((Worker) receiver.readObject());
          } catch (ReceiverException e) {
            logger.log(Level.SEVERE, "Ошибка получения данных", e);
            close();
          }
          return;
        }
      }
    }
    try {
      sender.writeBoolean(false);
    } catch (SenderException e) {
      logger.log(Level.SEVERE, "Ошибка отправки сообщения типа boolean", e);
    }
  }

  /**
   * Returns true if command is "exit"
   *
   * @param request request by client
   * @return True if command is "exit"
   */
  public boolean exit(Request request) {
    if (request.getCommandName().equals("exit")) {
      try {
        sender.writeBoolean(true);
      } catch (SenderException e) {
        logger.log(Level.SEVERE, "Ошибка отправки сообщения типа boolean", e);
      }
      return true;
    } else {
      try {
        sender.writeBoolean(false);
      } catch (SenderException e) {
        logger.log(Level.SEVERE, "Ошибка отправки сообщения типа boolean", e);
      }
      return false;
    }
  }

  /** One request */
  public void get() {
    try {
      sender.writeUTF("Введите команду: ");
    } catch (SenderException e) {
      logger.log(Level.SEVERE, "Ошибка отправки сообщения", e);
    }
    try {
      out.writeln("Запрос на получение команды отправлен клиенту");
    } catch (OutputException e) {
      logger.log(Level.SEVERE, "Ошибка записи", e);
    }
    logger.fine("Запрос на получение команды отправлен клиенту");
    Request request;
    try {
      request = (Request) receiver.readObject();
    } catch (ReceiverException e) {
      logger.severe("Ошибка при получении данных клиента. Закрытие соединения");
      close();
      return;
    }
    if (request == null) {
      logger.severe("Ошибка при получении данных клиента. Закрытие соединения");
      close();
      return;
    }
    logger.info(
        "Данные клиента: " + request.getCommandName() + " " + request.getCommandParameter());
    if (exit(request)) {
      close();
      logger.finest("Клиент успешно отключен");
      return;
    }
    requireElement(request);
    Reply reply = executor.executeCommand(request);
    try {
      sender.writeObject(reply);
    } catch (SenderException e) {
      logger.severe("Ошибка при отправке данных клиента. Закрытие соединения");
      close();
      return;
    }
    logger.log(reply.getType().getLevel(), reply.getString());
  }

  /** Runs client handler */
  @Override
  public void run() {
    try {
      out.writeln(
          "Подклчен клиент: "
              + client.getLocalAddress().toString().substring(1)
              + client.getRemoteAddress());
      logger.finest(
          "Подклчен клиент: "
              + client.getLocalAddress().toString().substring(1)
              + client.getRemoteAddress());
    } catch (IOException e) {
      logger.severe("Ошибка получения адреса клиента");
    } catch (OutputException e) {
      e.printStackTrace(); // TODO
    }
    setup();
    try {
      out.writeln("Настойка произведена успешно", Output.Colors.GREEN);
    } catch (OutputException e) {
      logger.log(Level.SEVERE, "Ошибка записи", e);
    }
    logger.config("Настойка произведена успешно");
    while (!client.socket().isClosed()) {
      get();
    }
    close();
  }
}

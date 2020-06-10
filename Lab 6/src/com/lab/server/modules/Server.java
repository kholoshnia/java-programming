package com.lab.server.modules;

import com.lab.common.io.Output;
import com.lab.common.io.OutputException;
import com.lab.server.executor.Executor;
import com.lab.server.storage.transfer.Loader;
import com.lab.server.storage.transfer.LoaderException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Server class */
public class Server {
  private final int port;
  private Output out;
  private Logger logger;
  private Executor executor;
  private ServerSocketChannel server;
  private ExecutorService threadPool;

  public Server(int port) {
    out = new Output();
    this.port = port;
    executor = new Executor();
    this.threadPool = Executors.newFixedThreadPool(1);
    new Setup().logger();
    logger = Logger.getLogger(Server.class.getName());
  }

  /**
   * Returns runner
   *
   * @return Runner
   */
  public Executor getExecutor() {
    return executor;
  }

  /** Connects client */
  public void connect() throws OutputException {
    while (server != null) {
      try {
        SocketChannel client = server.accept();
        threadPool.execute(new ClientHandler(client, executor));
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Ошибка подключения", e);
      }
      out.writeln("Соедениение установлено: ", Output.Colors.GREEN);
      logger.finest("Соедениение установлено");
    }
  }

  /**
   * Starts server
   *
   * @return Boolean
   */
  public boolean start() throws OutputException {
    try {
      server = ServerSocketChannel.open();
      server.bind(new InetSocketAddress(port));
      out.writeln("Сервер успешно запущен", Output.Colors.GREEN);
      out.writeln("Ожидание нового подключения...");
      logger.finest("Сервер успешно запущен");
      return true;
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Ошибка запуска сервера: ", e);
    }
    return false;
  }

  /** Closes server */
  public void close() throws OutputException {
    try {
      server.close();
      threadPool.shutdown();
      out.writeln("Завершение работы сервера успешно", Output.Colors.GREEN);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Ошибка завершения работы сервера", e);
    }
  }

  public void load() {
    try {
      executor.getEditor().setCollection(new Loader().load(executor.getEditor().getDataFilePath()));
    } catch (LoaderException e) {
      logger.log(Level.SEVERE, "Ошибка загрузки данных из файла", e);
    }
  }
}

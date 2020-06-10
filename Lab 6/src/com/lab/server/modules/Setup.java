package com.lab.server.modules;

import com.lab.common.tools.Files;
import com.lab.common.tools.FilesException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Setup {
  private Logger logger;

  public Setup() {
    logger = Logger.getLogger(Setup.class.getName());
  }

  public void logger() {
    String path = "res" + File.separator + "logging.properties";
    try {
      new Files().setupDirectory("logs");
      new Files().checkFile(path);
      LogManager.getLogManager().readConfiguration(new FileInputStream(path));
      logger.finest("Логгер успешно настроен");
    } catch (FilesException e) {
      logger.log(Level.SEVERE, "Ошибка чтения файла конфигурации логгера", e);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Ошибка при загрузке конфигурации логгера", e);
    } catch (NullPointerException e) {
      logger.log(Level.SEVERE, "Ошибка при чтении конфигурации логгера", e);
    }
  }
}

package ru.lab.common.tools;

import java.io.File;

/** Files class. */
public class Files {
  /**
   * Setups directory by given name
   *
   * @param directoryName directory name
   */
  public void setupDirectory(String directoryName) throws FilesException {
    File directory = new File(directoryName);
    if (directory.exists()) {
      if (!directory.isDirectory()) {
        throw new FilesException("Обнаружен путь к файлу, а не к директории");
      }
      return;
    }

    if (!directory.mkdir()) {
      if (!directory.mkdirs()) {
        throw new FilesException("Ошибка создания директории");
      }
      throw new FilesException("Ошибка создания директории");
    }
  }

  /**
   * Returns true if file path is correct
   *
   * @param fileName file name
   */
  public void checkFile(String fileName) throws FilesException {
    File file = new File(fileName);
    if (file.isDirectory()) {
      throw new FilesException("Обнаружен путь к директории, а не к файлу");
    }
    if (!file.exists()) {
      throw new FilesException("Файл не найден");
    }
    if (!file.canRead()) {
      throw new FilesException("Ошибка доступа на чтение");
    }
    if (!file.canWrite()) {
      throw new FilesException("Ошибка доступа на чтение");
    }
  }
}

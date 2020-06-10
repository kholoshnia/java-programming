package com.lab.server.storage.transfer;

import com.lab.common.element.Worker;
import com.lab.common.tools.Files;
import com.lab.common.tools.FilesException;
import com.lab.server.storage.collection.Collection;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/** Saver class */
public class Saver { // TODO: JSON, CSV, PostgreSQL
  /** Saves collection to the file */
  public void saveXML(Collection collection, String dataFilePath) throws SaverException {
    try {
      new Files().checkFile(dataFilePath);
      BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(dataFilePath));
      writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
      writer.write("<workers>\n".getBytes());
      for (Map.Entry<Integer, Worker> el : collection.getEntrySet()) {
        writer.write("\t<element>\n".getBytes());
        writer.write(("\t\t<key>" + el.getKey() + "</key>\n").getBytes());
        writer.write(("\t\t<id>" + el.getValue().getId() + "</id>\n").getBytes());
        writer.write(("\t\t<name>" + el.getValue().getName() + "</name>\n").getBytes());
        writer.write(
            ("\t\t<creationDate>"
                    + el.getValue()
                        .getCreationDate()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    + "</creationDate>\n")
                .getBytes());
        writer.write("\t\t<coordinates>\n".getBytes());
        writer.write(("\t\t\t<x>" + el.getValue().getCoordinates().getX() + "</x>\n").getBytes());
        writer.write(("\t\t\t<y>" + el.getValue().getCoordinates().getY() + "</y>\n").getBytes());
        writer.write("\t\t</coordinates>\n".getBytes());
        if (el.getValue().getSalary() != 0.0) {
          writer.write(("\t\t<salary>" + el.getValue().getSalary() + "</salary>\n").getBytes());
        } else {
          writer.write(("\t\t<salary>null</salary>\n").getBytes());
        }
        writer.write(
            ("\t\t<startDate>"
                    + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                        .format(el.getValue().getStartDate())
                    + "</startDate>\n")
                .getBytes());
        if (el.getValue().getEndDate() != null) {
          writer.write(
              ("\t\t<endDate>"
                      + el.getValue()
                          .getEndDate()
                          .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                      + "</endDate>\n")
                  .getBytes());
        } else {
          writer.write(("\t\t<endDate>null</endDate>\n").getBytes());
        }
        writer.write(("\t\t<status>" + el.getValue().getStatus() + "</status>\n").getBytes());
        writer.write("\t\t<person>\n".getBytes());
        writer.write(
            ("\t\t\t<eyeColor>" + el.getValue().getPerson().getEyeColor() + "</eyeColor>\n")
                .getBytes());
        writer.write(
            ("\t\t\t<hairColor>" + el.getValue().getPerson().getHairColor() + "</hairColor>\n")
                .getBytes());
        writer.write("\t\t\t<location>\n".getBytes());
        writer.write(
            ("\t\t\t\t<x>" + el.getValue().getPerson().getLocation().getX() + "</x>\n").getBytes());
        writer.write(
            ("\t\t\t\t<y>" + el.getValue().getPerson().getLocation().getY() + "</y>\n").getBytes());
        writer.write(
            ("\t\t\t\t<z>" + el.getValue().getPerson().getLocation().getZ() + "</z>\n").getBytes());
        writer.write(
            ("\t\t\t\t<name>" + el.getValue().getPerson().getLocation().getName() + "</name>\n")
                .getBytes());
        writer.write("\t\t\t</location>\n".getBytes());
        writer.write(
            ("\t\t\t<passportID>" + el.getValue().getPerson().getPassportID() + "</passportID>\n")
                .getBytes());
        writer.write("\t\t</person>\n".getBytes());
        writer.write("\t</element>\n".getBytes());
      }
      writer.write("</workers>\n".getBytes());
      writer.close();
    } catch (FilesException e) {
      throw new SaverException("Ошибка при проверке файла", e);
    } catch (IOException e) {
      throw new SaverException("Ошибка записи в файл", e);
    }
  }
}

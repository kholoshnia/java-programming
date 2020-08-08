package ru.lab.client.controller.responseHandler.responseHandlers;

import ru.lab.client.controller.responseHandler.ResponseHandler;
import ru.lab.client.controller.responseHandler.formatter.Formatter;
import ru.lab.common.transfer.response.Status;

public final class CreatedResponseHandler extends ResponseHandler {
  private final Formatter stringFormatter;

  public CreatedResponseHandler(Formatter stringFormatter) {
    this.stringFormatter = stringFormatter;
  }

  @Override
  protected String process() {
    if (!status.equals(Status.CREATED)) {
      return null;
    }

    return stringFormatter.makeGreen(answer);
  }
}

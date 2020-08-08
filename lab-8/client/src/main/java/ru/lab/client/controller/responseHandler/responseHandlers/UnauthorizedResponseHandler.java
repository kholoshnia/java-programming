package ru.lab.client.controller.responseHandler.responseHandlers;

import ru.lab.client.controller.responseHandler.MessageMediator;
import ru.lab.client.controller.responseHandler.ResponseHandler;
import ru.lab.client.controller.responseHandler.formatter.Formatter;
import ru.lab.common.transfer.response.Status;

public final class UnauthorizedResponseHandler extends ResponseHandler {
  private final Formatter stringFormatter;
  private final MessageMediator messageMediator;

  public UnauthorizedResponseHandler(Formatter stringFormatter, MessageMediator messageMediator) {
    this.stringFormatter = stringFormatter;
    this.messageMediator = messageMediator;
  }

  @Override
  protected String process() {
    if (!status.equals(Status.UNAUTHORIZED)) {
      return null;
    }

    return String.format(
            "%s (%s):",
            stringFormatter.makeYellow(status.toString()), messageMediator.getUnauthorizedMessage())
        + System.lineSeparator()
        + answer;
  }
}

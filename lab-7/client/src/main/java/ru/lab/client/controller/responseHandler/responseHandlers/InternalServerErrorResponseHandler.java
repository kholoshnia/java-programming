package ru.lab.client.controller.responseHandler.responseHandlers;

import ru.lab.client.controller.responseHandler.MessageMediator;
import ru.lab.client.controller.responseHandler.ResponseHandler;
import ru.lab.client.controller.responseHandler.formatter.Formatter;
import ru.lab.common.transfer.response.Status;

public final class InternalServerErrorResponseHandler extends ResponseHandler {
  private final Formatter stringFormatter;
  private final MessageMediator messageMediator;

  public InternalServerErrorResponseHandler(
      Formatter stringFormatter, MessageMediator messageMediator) {
    this.stringFormatter = stringFormatter;
    this.messageMediator = messageMediator;
  }

  @Override
  protected String process() {
    if (!status.equals(Status.INTERNAL_SERVER_ERROR)) {
      return null;
    }

    return String.format(
            "%s (%s):",
            stringFormatter.makeRed(status.toString()),
            messageMediator.getInternalServerErrorMessage())
        + System.lineSeparator()
        + stringFormatter.makeRed(answer);
  }
}

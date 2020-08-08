package ru.lab.client.controller.argumentFormer;

import ru.lab.client.controller.validator.exceptions.ValidationException;

public interface ArgumentValidator {
  /**
   * Checks user input.
   *
   * @param input user input
   * @throws ValidationException - in case of validation errors.
   */
  void check(String input) throws ValidationException;
}

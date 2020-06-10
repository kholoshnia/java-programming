package com.lab;

import com.lab.runner.Runner;

public class Main {
  public static void main(String[] args) {
    Runner runner = new Runner();
    if (runner.setArgs(args)) {
      runner.load();
      runner.run();
    }
  }
}

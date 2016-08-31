package eu.nerro.tus.server.service;

public interface Service extends Runnable {
  void run();

  void stop();
}

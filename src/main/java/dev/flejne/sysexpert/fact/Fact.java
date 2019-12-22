package dev.flejne.sysexpert.fact;

public interface Fact<T> {

  int level();

  String name();

  boolean hasQuestion();

  String questionMe();

  T value();

  Fact<T> handleLevel(int n);
}

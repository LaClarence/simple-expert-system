package dev.flejne.ia.expert.genericexpertsystem;

public interface Fait<T> {

  Integer getNiveau();

  String getNom();

  boolean hasQuestion();
  
  String getQuestion();

  T getValeur();

  Fait<T> avecNiveau(int n);
}

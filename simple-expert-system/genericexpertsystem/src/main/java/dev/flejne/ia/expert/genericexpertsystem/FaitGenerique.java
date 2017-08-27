package dev.flejne.ia.expert.genericexpertsystem;

public interface FaitGenerique<T> {

  Integer getNiveau();

  String getNom();

  boolean hasQuestion();
  
  String getQuestion();

  T getValeur();

  FaitGenerique<T> avecNiveau(int n);
}

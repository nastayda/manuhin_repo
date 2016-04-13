package ru.stqa.pft.rzd.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by manuhin on 22.03.2016.
 */
public class Trains extends ForwardingSet<Train> {
  Set<Train> delegate;

  public Trains(Trains trains) {
    this.delegate = new HashSet<Train>(trains.delegate());
  }

  public Trains() {
    this.delegate = new HashSet<Train>();
  }

  public Trains(Collection<Train> contacts) {
    this.delegate = new HashSet<Train>(contacts);
  }

  @Override
  protected Set<Train> delegate() {
    return delegate;
  }

  public Trains withAdded(Train train) {
    Trains trains = new Trains(this);
    trains.add(train);
    return trains;
  }

  public Trains without(Train train) {
    Trains trains = new Trains(this);
    trains.remove(train);
    return trains;
  }
}

package ru.lab.server.storage.collection;

import ru.lab.common.element.Worker;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/** Implementation of CollectionInterface for Hashtable */
public final class Collection implements CollectionInterface {
  private Date initDate;
  private Hashtable<Integer, Worker> collection;

  /** Collection constructor */
  public Collection() {
    initDate = new Date();
    collection = new Hashtable<>();
  }

  @Override
  public void clear() {
    collection.clear();
  }

  @Override
  public int getSize() {
    return collection.size();
  }

  @Override
  public java.util.Collection<Worker> values() {
    return collection.values();
  }

  @Override
  public Date getInitTime() {
    return initDate;
  }

  @Override
  public Set<Integer> keySet() {
    return collection.keySet();
  }

  @Override
  public Set<Map.Entry<Integer, Worker>> getEntrySet() {
    return collection.entrySet();
  }

  @Override
  public void remove(int key) {
    collection.remove(key);
  }

  @Override
  public Worker get(int key) {
    return collection.get(key);
  }

  @Override
  public String getCollectionType() {
    return collection.getClass().getName();
  }

  @Override
  public boolean containsKey(int key) {
    return collection.containsKey(key);
  }

  @Override
  public void put(int key, Object value) {
    collection.put(key, (Worker) value);
  }

  @Override
  public void replace(int key, Object value) {
    collection.replace(key, (Worker) value);
  }
}

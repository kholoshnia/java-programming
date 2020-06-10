package com.lab.server.storage.collection;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/** Collection shell interface */
public interface CollectionInterface {
  /** Clears collection */
  void clear();

  /**
   * Returns size of the collection
   *
   * @return Size of collection
   */
  int getSize();

  /**
   * Returns values of the collection
   *
   * @return Values of collection
   */
  Collection<?> values();

  /**
   * Returns initialization time of the collection
   *
   * @return Initialization time of the collection
   */
  Date getInitTime();

  /**
   * Returns key set
   *
   * @return Key set
   */
  Set<?> keySet();

  /**
   * Returns entry set of the collection
   *
   * @return Entry set of the collection
   */
  Set<?> getEntrySet();

  /**
   * Remove element from collection by key
   *
   * @param key key for element to remove
   */
  void remove(int key);
  /**
   * Returns element by key
   *
   * @param key key for element to be returned
   * @return Element by key
   */
  Object get(int key);

  /**
   * Returns collection type
   *
   * @return Collection type
   */
  String getCollectionType();

  /**
   * Returns true if collection contains key
   *
   * @param key key to be checked
   * @return True if collection contains key
   */
  boolean containsKey(int key);

  /**
   * Puts value with key
   *
   * @param key key for value
   * @param value value with key
   */
  void put(int key, Object value);

  /**
   * Replaces value with key
   *
   * @param key key for value
   * @param value value with key
   */
  void replace(int key, Object value);
}

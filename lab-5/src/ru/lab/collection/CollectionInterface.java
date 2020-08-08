package ru.lab.collection;

import java.util.Collection;
import java.util.Date;

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
  Collection<?> keySet();

  /**
   * Returns entry set of the collection
   *
   * @return Entry set of the collection
   */
  Collection<?> getEntrySet();

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
  Object get(Integer key);

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

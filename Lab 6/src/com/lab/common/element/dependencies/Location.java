package com.lab.common.element.dependencies;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/** Coordinates class for worker class */
public class Location implements Serializable, Comparable<Location> {
  private long x;
  private long y;
  private double z;
  private String name;

  /** Empty constructor */
  public Location() {}

  /**
   * Location constructor
   *
   * @param x x coordinate
   * @param y y coordinate
   * @param z z coordinate
   * @param name name of the location
   * @throws LocationException is thrown if value is wrong
   */
  public Location(long x, long y, double z, String name) throws LocationException {
    setX(x);
    setY(y);
    setZ(z);
    setName(name);
  }

  /**
   * Returns x coordinate
   *
   * @return X coordinate
   */
  public long getX() {
    return x;
  }

  /**
   * Sets x coordinate
   *
   * @param x new x coordinate
   * @throws LocationException is thrown if the value is wrong (value should not be null (0L))
   */
  public void setX(long x) throws LocationException {
    if (x == 0L) {
      throw new LocationException("Значение y не может быть null (0L)");
    } else {
      this.x = x;
    }
  }

  /**
   * Returns y coordinate
   *
   * @return Y coordinate
   */
  public long getY() {
    return y;
  }

  /**
   * Sets y coordinate
   *
   * @param y new y coordinate
   */
  public void setY(long y) {
    this.y = y;
  }

  /**
   * Returns z coordinate
   *
   * @return Z coordinate
   */
  public double getZ() {
    return z;
  }

  /**
   * Sets z coordinate
   *
   * @param z new z coordinate
   * @throws LocationException is thrown if the value is wrong (value should not be null (0.0))
   */
  public void setZ(double z) throws LocationException {
    if (z == 0.0) {
      throw new LocationException("Значение y не может быть null (0.0)");
    } else {
      this.z = z;
    }
  }

  /**
   * Returns name of the location
   *
   * @return Name of the location
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name of the location
   *
   * @param name Name of the location
   * @throws LocationException is thrown if the value is wrong (string length should be less than
   *     461 and should not be null)
   */
  public void setName(String name) throws LocationException {
    if (name == null) {
      throw new LocationException("Строка имени не может быть null");
    } else if (name.length() > 461) {
      throw new LocationException("Длина строки имени не должна быть больше 461");
    } else {
      this.name = name;
    }
  }

  /**
   * Returns a string representation of the object. In general, the {@code toString} method returns
   * a string that "textually represents" this object. The result should be a concise but
   * informative representation that is easy for a person to read. It is recommended that all
   * subclasses override this method.
   *
   * <p>The {@code toString} method for class {@code Object} returns a string consisting of the name
   * of the class of which the object is an instance, the at-sign character `{@code @}', and the
   * unsigned hexadecimal representation of the hash code of the object. In other words, this method
   * returns a string equal to the value of:
   *
   * <blockquote>
   *
   * <pre>
   * getClass().getName() + '@' + Integer.toHexString(hashCode())
   * </pre>
   *
   * </blockquote>
   *
   * @return a string representation of the object { x: value, y: value, z: value, name: 'value' }.
   */
  @Override
  public String toString() {
    return "{"
        + System.lineSeparator()
        + "\tx: "
        + x
        + ','
        + System.lineSeparator()
        + "\ty: "
        + y
        + ','
        + System.lineSeparator()
        + "\tz: "
        + z
        + ','
        + System.lineSeparator()
        + "\tname: '"
        + name
        + '\''
        + System.lineSeparator()
        + "}";
  }

  /**
   * Return true if objects are equal
   *
   * @param o other object
   * @return True if object are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Location location = (Location) o;
    return x == location.x
        && y == location.y
        && Double.compare(location.z, z) == 0
        && Objects.equals(name, location.name);
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   *
   * <p>The general contract of {@code hashCode} is:
   *
   * <ul>
   *   <li>Whenever it is invoked on the same object more than once during an execution of a Java
   *       application, the {@code hashCode} method must consistently return the same integer,
   *       provided no information used in {@code equals} comparisons on the object is modified.
   *       This integer need not remain consistent from one execution of an application to another
   *       execution of the same application.
   *   <li>If two objects are equal according to the {@code equals(Object)} method, then calling the
   *       {@code hashCode} method on each of the two objects must produce the same integer result.
   *   <li>It is <em>not</em> required that if two objects are unequal according to the {@link
   *       Object#equals(Object)} method, then calling the {@code hashCode} method on each of the
   *       two objects must produce distinct integer results. However, the programmer should be
   *       aware that producing distinct integer results for unequal objects may improve the
   *       performance of hash tables.
   * </ul>
   *
   * <p>As much as is reasonably practical, the hashCode method defined by class {@code Object} does
   * return distinct integers for distinct objects. (This is typically implemented by converting the
   * internal address of the object into an integer, but this implementation technique is not
   * required by the Java&trade; programming language.)
   *
   * @return a hash code value for this object.
   * @see Object#equals(Object)
   * @see System#identityHashCode
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y, z, name);
  }

  /**
   * Compares this object with the specified object for order (compares locations by name). Returns
   * a negative integer, zero, or a positive integer as this object is less than, equal to, or
   * greater than the specified object.
   *
   * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) == -sgn(y.compareTo(x))</tt> for all
   * <tt>x</tt> and <tt>y</tt>. (This implies that <tt>x.compareTo(y)</tt> must throw an exception
   * iff <tt>y.compareTo(x)</tt> throws an exception.)
   *
   * <p>The implementor must also ensure that the relation is transitive: <tt>(x.compareTo(y)&gt;0
   * &amp;&amp; y.compareTo(z)&gt;0)</tt> implies <tt>x.compareTo(z)&gt;0</tt>.
   *
   * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt> implies that
   * <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for all <tt>z</tt>.
   *
   * <p>It is strongly recommended, but <i>not</i> strictly required that <tt>(x.compareTo(y)==0) ==
   * (x.equals(y))</tt>. Generally speaking, any class that implements the <tt>Comparable</tt>
   * interface and violates this condition should clearly indicate this fact. The recommended
   * language is "Note: this class has a natural ordering that is inconsistent with equals."
   *
   * <p>In the foregoing description, the notation <tt>sgn(</tt><i>expression</i><tt>)</tt>
   * designates the mathematical <i>signum</i> function, which is defined to return one of
   * <tt>-1</tt>, <tt>0</tt>, or <tt>1</tt> according to whether the value of <i>expression</i> is
   * negative, zero or positive.
   *
   * @param o the object to be compared.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
   *     or greater than the specified object.
   * @throws NullPointerException if the specified object is null
   * @throws ClassCastException if the specified object's type prevents it from being compared to
   *     this object.
   */
  @Override
  public int compareTo(Location o) {
    return o.name.compareTo(name);
  }
}

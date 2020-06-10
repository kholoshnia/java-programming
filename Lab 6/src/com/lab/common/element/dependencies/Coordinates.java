package com.lab.common.element.dependencies;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/** Coordinates class for Worker class */
public class Coordinates implements Serializable, Comparable<Coordinates> {
  private double x;
  private double y;

  /** Empty constructor */
  public Coordinates() {}

  /**
   * Constructor constructor
   *
   * @param x x coordinate
   * @param y y coordinate
   * @throws CoordinatesException is thrown if the value is wrong
   */
  public Coordinates(double x, double y) throws CoordinatesException {
    setX(x);
    setY(y);
  }

  /**
   * Returns x coordinate
   *
   * @return X coordinate
   */
  public double getX() {
    return x;
  }

  /**
   * Sets x coordinate if value is correct
   *
   * @param x new x coordinate
   * @throws CoordinatesException is thrown if the value is wrong (value should be greater than
   *     -433)
   */
  public void setX(double x) throws CoordinatesException {
    if (x <= -433) {
      throw new CoordinatesException("Значение x должно быть больше -433");
    } else {
      this.x = x;
    }
  }

  /**
   * Returns y coordinate
   *
   * @return Y coordinate
   */
  public double getY() {
    return y;
  }

  /**
   * Sets y coordinate if value is correct
   *
   * @param y new y coordinate
   * @throws CoordinatesException is thrown if the value is wrong (value should be greater than -501
   *     and should not be null (0.0))
   */
  public void setY(double y) throws CoordinatesException {
    if (y == 0.0) {
      throw new CoordinatesException("Значение y не может быть null (0.0)");
    } else if (y <= -501) {
      throw new CoordinatesException("Значение y должно быть больше -501");
    } else {
      this.y = y;
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
   * @return a string representation of the object { x: value, y: value }.
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
        + System.lineSeparator()
        + "}";
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * <p>The {@code equals} method implements an equivalence relation on non-null object references:
   *
   * <ul>
   *   <li>It is <i>reflexive</i>: for any non-null reference value {@code x}, {@code x.equals(x)}
   *       should return {@code true}.
   *   <li>It is <i>symmetric</i>: for any non-null reference values {@code x} and {@code y}, {@code
   *       x.equals(y)} should return {@code true} if and only if {@code y.equals(x)} returns {@code
   *       true}.
   *   <li>It is <i>transitive</i>: for any non-null reference values {@code x}, {@code y}, and
   *       {@code z}, if {@code x.equals(y)} returns {@code true} and {@code y.equals(z)} returns
   *       {@code true}, then {@code x.equals(z)} should return {@code true}.
   *   <li>It is <i>consistent</i>: for any non-null reference values {@code x} and {@code y},
   *       multiple invocations of {@code x.equals(y)} consistently return {@code true} or
   *       consistently return {@code false}, provided no information used in {@code equals}
   *       comparisons on the objects is modified.
   *   <li>For any non-null reference value {@code x}, {@code x.equals(null)} should return {@code
   *       false}.
   * </ul>
   *
   * <p>The {@code equals} method for class {@code Object} implements the most discriminating
   * possible equivalence relation on objects; that is, for any non-null reference values {@code x}
   * and {@code y}, this method returns {@code true} if and only if {@code x} and {@code y} refer to
   * the same object ({@code x == y} has the value {@code true}).
   *
   * <p>Note that it is generally necessary to override the {@code hashCode} method whenever this
   * method is overridden, so as to maintain the general contract for the {@code hashCode} method,
   * which states that equal objects must have equal hash codes.
   *
   * @param o the reference object with which to compare.
   * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
   * @see #hashCode()
   * @see HashMap
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coordinates that = (Coordinates) o;
    return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
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
    return Objects.hash(x, y);
  }

  /**
   * Compares this object with the specified object for order (compares coordinates by x). Returns a
   * negative integer, zero, or a positive integer as this object is less than, equal to, or greater
   * than the specified object.
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
  public int compareTo(Coordinates o) {
    return Double.compare(x, o.x);
  }
}

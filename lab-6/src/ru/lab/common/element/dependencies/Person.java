package ru.lab.common.element.dependencies;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

/** Person class for Worker class */
public class Person implements Serializable, Comparable<Person> {
  private Color eyeColor;
  private Color hairColor;
  private Location location;
  private String passportID;
  /** Empty constructor */
  public Person() {}

  /**
   * Person constructor
   *
   * @param eyeColor eye color
   * @param hairColor hair color
   * @param location location
   * @param passportID passport ID
   * @throws PersonException is thrown if the value is wrong
   */
  public Person(Color eyeColor, Color hairColor, Location location, String passportID)
      throws PersonException {
    setEyeColor(eyeColor);
    setHairColor(hairColor);
    setLocation(location);
    setPassportID(passportID);
  }

  /**
   * Returns eye color
   *
   * @return Eye color
   */
  public Color getEyeColor() {
    return eyeColor;
  }

  /**
   * Sets eye color
   *
   * @param eyeColor new eye color
   */
  public void setEyeColor(Color eyeColor) {
    this.eyeColor = eyeColor;
  }

  /**
   * Returns hair color
   *
   * @return Hair color
   */
  public Color getHairColor() {
    return hairColor;
  }

  /**
   * Sets hair color
   *
   * @param hairColor new hair color
   * @throws PersonException is thrown if the value is wrong (value should not be null)
   */
  public void setHairColor(Color hairColor) throws PersonException {
    if (hairColor == null) {
      throw new PersonException("Значение цвета волос быть null");
    } else {
      this.hairColor = hairColor;
    }
  }

  /**
   * Returns location
   *
   * @return Location
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Sets location
   *
   * @param location new location
   * @throws PersonException is thrown if the value is wrong (value should not be null)
   */
  public void setLocation(Location location) throws PersonException {
    if (location == null) {
      throw new PersonException("Значение поля location не может быть null");
    } else {
      this.location = location;
    }
  }

  /**
   * Returns passport ID
   *
   * @return Passport ID
   */
  public String getPassportID() {
    return passportID;
  }

  /**
   * Sets passport ID
   *
   * @param passportID passport ID
   * @throws PersonException is thrown if value is wrong (ID length should be greater than 10, less
   *     then 44, should not be empty string, should not be null)
   */
  public void setPassportID(String passportID) throws PersonException {
    if (passportID == null) {
      throw new PersonException("Значение ID паспорта не может null");
    } else if (passportID.equals("")) {
      throw new PersonException("ID паспорта не может быть пустой строкой");
    } else if (passportID.length() > 44) {
      throw new PersonException("Неверный ID паспорта (больше 44)");
    } else if (passportID.length() < 10) {
      throw new PersonException("Неверный ID паспорта (меньше 10)");
    } else {
      this.passportID = passportID;
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
   * @return a string representation of the object { eyeColor: value, hairColor: value, location:
   *     value, passportID: 'value' }.
   */
  @Override
  public String toString() {
    String locationString = "null";
    if (location != null) {
      locationString =
          Arrays.stream(location.toString().split(System.lineSeparator()))
              .skip(0)
              .collect(Collectors.joining(System.lineSeparator() + "\t"));
    }
    return "{"
        + System.lineSeparator()
        + "\teyeColor: "
        + eyeColor
        + ','
        + System.lineSeparator()
        + "\thairColor: "
        + hairColor
        + ','
        + System.lineSeparator()
        + "\tlocation: "
        + locationString
        + ','
        + System.lineSeparator()
        + "\tpassportID: '"
        + passportID
        + '\''
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
    Person person = (Person) o;
    return eyeColor == person.eyeColor
        && hairColor == person.hairColor
        && Objects.equals(location, person.location)
        && Objects.equals(passportID, person.passportID);
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
    return Objects.hash(eyeColor, hairColor, location, passportID);
  }

  /**
   * Compares this object with the specified object for order (compares persons by location).
   * Returns a negative integer, zero, or a positive integer as this object is less than, equal to,
   * or greater than the specified object.
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
  public int compareTo(Person o) {
    return o.location.compareTo(location);
  }

  /** Enum for eye and hair colors */
  public enum Color {
    BLUE,
    WHITE,
    BROWN,
    BLACK,
    YELLOW,
    ORANGE
  }
}

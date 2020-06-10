package com.lab.common.transceiver;

import java.io.*;

public class Sender { // TODO: All exceptions
  private ObjectOutputStream sender;

  public Sender(ObjectOutputStream sender) {
    this.sender = sender;
  }

  /**
   * Specify stream protocol version to use when writing the stream.
   *
   * <p>This routine provides a hook to enable the current version of Serialization to write in a
   * format that is backwards compatible to a previous version of the stream format.
   *
   * <p>Every effort will be made to avoid introducing additional backwards incompatibilities;
   * however, sometimes there is no other alternative.
   *
   * @param version use ProtocolVersion from java.io.ObjectStreamConstants.
   * @throws IllegalStateException if called after any objects have been serialized.
   * @throws IllegalArgumentException if invalid version is passed in.
   * @throws SenderException if I/O errors occur
   * @see ObjectStreamConstants#PROTOCOL_VERSION_1
   * @see ObjectStreamConstants#PROTOCOL_VERSION_2
   * @since 1.2
   */
  public void useProtocolVersion(int version) throws SenderException {
    try {
      sender.useProtocolVersion(version);
    } catch (IOException e) {
      throw new SenderException("Ошибка установки версии протокола", e);
    }
  }

  /**
   * Write the specified object to the ObjectOutputStream. The class of the object, the signature of
   * the class, and the values of the non-transient and non-static fields of the class and all of
   * its supertypes are written. Default serialization for a class can be overridden using the
   * writeObject and the readObject methods. Objects referenced by this object are written
   * transitively so that a complete equivalent graph of objects can be reconstructed by an
   * ObjectInputStream.
   *
   * <p>Exceptions are thrown for problems with the OutputStream and for classes that should not be
   * serialized. All exceptions are fatal to the OutputStream, which is left in an indeterminate
   * state, and it is up to the caller to ignore or recover the stream state.
   *
   * @throws InvalidClassException Something is wrong with a class used by serialization.
   * @throws NotSerializableException Some object to be serialized does not implement the
   *     java.io.Serializable interface.
   * @throws SenderException Any exception thrown by the underlying OutputStream.
   * @param obj
   */
  public void writeObject(Object obj) throws SenderException {
    try {
      sender.writeObject(obj);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes an "unshared" object to the ObjectOutputStream. This method is identical to writeObject,
   * except that it always writes the given object as a new, unique object in the stream (as opposed
   * to a back-reference pointing to a previously serialized instance). Specifically:
   *
   * <ul>
   *   <li>An object written via writeUnshared is always serialized in the same manner as a newly
   *       appearing object (an object that has not been written to the stream yet), regardless of
   *       whether or not the object has been written previously.
   *   <li>If writeObject is used to write an object that has been previously written with
   *       writeUnshared, the previous writeUnshared operation is treated as if it were a write of a
   *       separate object. In other words, ObjectOutputStream will never generate back-references
   *       to object data written by calls to writeUnshared.
   * </ul>
   *
   * While writing an object via writeUnshared does not in itself guarantee a unique reference to
   * the object when it is deserialized, it allows a single object to be defined multiple times in a
   * stream, so that multiple calls to readUnshared by the receiver will not conflict. Note that the
   * rules described above only apply to the base-level object written with writeUnshared, and not
   * to any transitively referenced sub-objects in the object graph to be serialized.
   *
   * <p>ObjectOutputStream subclasses which override this method can only be constructed in security
   * contexts possessing the "enableSubclassImplementation" SerializablePermission; any attempt to
   * instantiate such a subclass without this permission will cause a SecurityException to be
   * thrown.
   *
   * @param obj object to write to stream
   * @throws NotSerializableException if an object in the graph to be serialized does not implement
   *     the Serializable interface
   * @throws InvalidClassException if a problem exists with the class of an object to be serialized
   * @throws SenderException if an I/O error occurs during serialization
   * @since 1.4
   */
  public void writeUnshared(Object obj) throws SenderException {
    try {
      sender.writeUnshared(obj);
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Write the non-static and non-transient fields of the current class to this stream. This may
   * only be called from the writeObject method of the class being serialized. It will throw the
   * NotActiveException if it is called otherwise.
   *
   * @throws SenderException if I/O errors occur while writing to the underlying <code>OutputStream
   *     </code>
   */
  public void defaultWriteObject() throws SenderException {
    try {
      sender.defaultWriteObject();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Retrieve the object used to buffer persistent fields to be written to the stream. The fields
   * will be written to the stream when writeFields method is called.
   *
   * @return an instance of the class Putfield that holds the serializable fields
   * @throws SenderException if I/O errors occur
   * @since 1.2
   */
  public ObjectOutputStream.PutField putFields() throws SenderException {
    try {
      return sender.putFields();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Write the buffered fields to the stream.
   *
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   * @throws NotActiveException Called when a classes writeObject method was not called to write the
   *     state of the object.
   * @since 1.2
   */
  public void writeFields() throws SenderException {
    try {
      sender.writeFields();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Reset will disregard the state of any objects already written to the stream. The state is reset
   * to be the same as a new ObjectOutputStream. The current point in the stream is marked as reset
   * so the corresponding ObjectInputStream will be reset at the same point. Objects previously
   * written to the stream will not be referred to as already being in the stream. They will be
   * written to the stream again.
   *
   * @throws SenderException if reset() is invoked while serializing an object.
   */
  public void reset() throws SenderException {
    try {
      sender.reset();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes a byte. This method will block until the byte is actually written.
   *
   * @param val the byte to be written to the stream
   * @throws SenderException If an I/O error has occurred.
   */
  public void write(int val) throws SenderException {
    try {
      sender.write(val);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes an array of bytes. This method will block until the bytes are actually written.
   *
   * @param buf the data to be written
   * @throws SenderException If an I/O error has occurred.
   */
  public void write(byte[] buf) throws SenderException {
    try {
      sender.write(buf);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes a sub array of bytes.
   *
   * @param buf the data to be written
   * @param off the start offset in the data
   * @param len the number of bytes that are written
   * @throws SenderException If an I/O error has occurred.
   */
  public void write(byte[] buf, int off, int len) throws SenderException {
    try {
      sender.write(buf, off, len);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Flushes the stream. This will write any buffered output bytes and flush through to the
   * underlying stream.
   *
   * @throws SenderException If an I/O error has occurred.
   */
  public void flush() throws SenderException {
    try {
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Closes the stream. This method must be called to release any resources associated with the
   * stream.
   *
   * @throws SenderException If an I/O error has occurred.
   */
  public void close() throws SenderException {
    try {
      sender.close();
    } catch (IOException e) {
      throw new SenderException("Ошибка закрытия потока", e);
    }
  }

  /**
   * Writes a boolean.
   *
   * @param val the boolean to be written
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   */
  public void writeBoolean(boolean val) throws SenderException {
    try {
      sender.writeBoolean(val);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes an 8 bit byte.
   *
   * @param val the byte value to be written
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   */
  public void writeByte(int val) throws SenderException {
    try {
      sender.writeByte(val);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes a 16 bit short.
   *
   * @param val the short value to be written
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   */
  public void writeShort(int val) throws SenderException {
    try {
      sender.writeShort(val);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes a 16 bit char.
   *
   * @param val the char value to be written
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   */
  public void writeChar(int val) throws SenderException {
    try {
      sender.writeChar(val);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes a 32 bit int.
   *
   * @param val the integer value to be written
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   */
  public void writeInt(int val) throws SenderException {
    try {
      sender.writeInt(val);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes a 64 bit long.
   *
   * @param val the long value to be written
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   */
  public void writeLong(long val) throws SenderException {
    try {
      sender.writeLong(val);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes a 32 bit float.
   *
   * @param val the float value to be written
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   */
  public void writeFloat(float val) throws SenderException {
    try {
      sender.writeFloat(val);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes a 64 bit double.
   *
   * @param val the double value to be written
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   */
  public void writeDouble(double val) throws SenderException {
    try {
      sender.writeDouble(val);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes a String as a sequence of bytes.
   *
   * @param str the String of bytes to be written
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   */
  public void writeBytes(String str) throws SenderException {
    try {
      sender.writeBytes(str);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Writes a String as a sequence of chars.
   *
   * @param str the String of chars to be written
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   */
  public void writeChars(String str) throws SenderException {
    try {
      sender.writeChars(str);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }

  /**
   * Primitive data write of this String in <a href="DataInput.html#modified-utf-8">modified
   * UTF-8</a> format. Note that there is a significant difference between writing a String into the
   * stream as primitive data or as an Object. A String instance written by writeObject is written
   * into the stream as a String initially. Future writeObject() calls write references to the
   * string into the stream.
   *
   * @param str the String to be written
   * @throws SenderException if I/O errors occur while writing to the underlying stream
   */
  public void writeUTF(String str) throws SenderException {
    try {
      sender.writeUTF(str);
      sender.flush();
    } catch (IOException e) {
      throw new SenderException("Ошибка отправки объекта", e);
    }
  }
}

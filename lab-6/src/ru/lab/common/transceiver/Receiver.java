package ru.lab.common.transceiver;

import java.io.*;

public class Receiver {
  private ObjectInputStream receiver;

  public Receiver(ObjectInputStream receiver) { // TODO: All exceptions
    this.receiver = receiver;
  }

  /**
   * Read an object from the ObjectInputStream. The class of the object, the signature of the class,
   * and the values of the non-transient and non-static fields of the class and all of its
   * supertypes are read. Default deserializing for a class can be overridden using the writeObject
   * and readObject methods. Objects referenced by this object are read transitively so that a
   * complete equivalent graph of objects is reconstructed by readObject.
   *
   * <p>The root object is completely restored when all of its fields and the objects it references
   * are completely restored. At this point the object validation callbacks are executed in order
   * based on their registered priorities. The callbacks are registered by objects (in the
   * readObject special methods) as they are individually restored.
   *
   * <p>Exceptions are thrown for problems with the InputStream and for classes that should not be
   * deserialized. All exceptions are fatal to the InputStream and leave it in an indeterminate
   * state; it is up to the caller to ignore or recover the stream state.
   *
   * <ul>
   *   <li>{@code ClassNotFoundException} Class of a serialized object cannot be found.
   *   <li>{@code InvalidClassException} Something is wrong with a class used by serialization.
   *   <li>{@code StreamCorruptedException} Control information in the stream is inconsistent.
   *   <li>{@code OptionalDataException} Primitive data was found in the stream instead of objects.
   *   <li>{@code IOException} Any of the usual Input/Output related exceptions. *
   * </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public Object readObject() throws ReceiverException {
    try {
      return receiver.readObject();
    } catch (ClassNotFoundException e) {
      throw new ReceiverException("Класс не найден", e);
    } catch (InvalidClassException e) {
      throw new ReceiverException("Неисправность в классе, использованом сериализацией", e);
    } catch (StreamCorruptedException e) {
      throw new ReceiverException("Информация в потоке повреждена", e);
    } catch (OptionalDataException e) {
      throw new ReceiverException("Получен примитив", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads an "unshared" object from the ObjectInputStream. This method is identical to readObject,
   * except that it prevents subsequent calls to readObject and readUnshared from returning
   * additional references to the deserialized instance obtained via this call. Specifically:
   *
   * <ul>
   *   <li>If readUnshared is called to deserialize a back-reference (the stream representation of
   *       an object which has been written previously to the stream), an ObjectStreamException will
   *       be thrown.
   *   <li>If readUnshared returns successfully, then any subsequent attempts to deserialize
   *       back-references to the stream handle deserialized by readUnshared will cause an
   *       ObjectStreamException to be thrown.
   * </ul>
   *
   * Deserializing an object via readUnshared invalidates the stream handle associated with the
   * returned object. Note that this in itself does not always guarantee that the reference returned
   * by readUnshared is unique; the deserialized object may define a readResolve method which
   * returns an object visible to other parties, or readUnshared may return a Class object or enum
   * constant obtainable elsewhere in the stream or through external means. If the deserialized
   * object defines a readResolve method and the invocation of that method returns an array, then
   * readUnshared returns a shallow clone of that array; this guarantees that the returned array
   * object is unique and cannot be obtained a second time from an invocation of readObject or
   * readUnshared on the ObjectInputStream, even if the underlying data stream has been manipulated.
   *
   * <p>ObjectInputStream subclasses which override this method can only be constructed in security
   * contexts possessing the "enableSubclassImplementation" SerializablePermission; any attempt to
   * instantiate such a subclass without this permission will cause a SecurityException to be
   * thrown.
   *
   * @return reference to deserialized object
   *     <ul>
   *       <li>{@code ClassNotFoundException} if class of an object to deserialize cannot be found
   *       <li>{@code StreamCorruptedException} if control information in the stream is inconsistent
   *       <li>{@code ObjectStreamException} if object to deserialize has already appeared in stream
   *       <li>{@code OptionalDataException} if primitive data is next in stream
   *       <li>{@code IOException} if an I/O error occurs during deserialization
   *       <li>{@code ReceiverException} combines all receiver exceptions
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   * @since 1.4
   */
  public Object readUnshared() throws ReceiverException {
    try {
      return receiver.readUnshared();
    } catch (ClassNotFoundException e) {
      throw new ReceiverException("Класс не найден", e);
    } catch (StreamCorruptedException e) {
      throw new ReceiverException("Информация в потоке повреждена", e);
    } catch (OptionalDataException e) {
      throw new ReceiverException("Получен примитив", e);
    } catch (ObjectStreamException e) {
      throw new ReceiverException("Объект уже был десериализован", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Read the non-static and non-transient fields of the current class from this stream. This may
   * only be called from the readObject method of the class being deserialized. It will throw the
   * NotActiveException if it is called otherwise.
   *
   * <ul>
   *   <li>{@code ClassNotFoundException} if the class of a serialized object could not be found.
   *   <li>{@code IOException} if an I/O error occurs.
   *   <li>{@code NotActiveException} if the stream is not currently reading objects.
   * </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public void defaultReadObject() throws ReceiverException {
    try {
      receiver.defaultReadObject();
    } catch (ClassNotFoundException e) {
      throw new ReceiverException("Класс не найден", e);
    } catch (NotActiveException e) {
      throw new ReceiverException("Поток не читает объекты", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads the persistent fields from the stream and makes them available by name.
   *
   * @return the {@code GetField} object representing the persistent fields of the object being
   *     deserialized
   *     <ul>
   *       <li>{@code ClassNotFoundException} if the class of a serialized object could not be
   *           found.
   *       <li>{@code IOException} if an I/O error occurs.
   *       <li>{@code NotActiveException} if the stream is not currently reading objects.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   * @since 1.2
   */
  public ObjectInputStream.GetField readFields() throws ReceiverException {
    try {
      return receiver.readFields();
    } catch (ClassNotFoundException e) {
      throw new ReceiverException("Класс не найден", e);
    } catch (NotActiveException e) {
      throw new ReceiverException("Поток в настоящее время не читает объекты", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Register an object to be validated before the graph is returned. While similar to resolveObject
   * these validations are called after the entire graph has been reconstituted. Typically, a
   * readObject method will register the object with the stream so that when all of the objects are
   * restored a final set of validations can be performed.
   *
   * @param obj the object to receive the validation callback.
   * @param prio controls the order of callbacks;zero is a good default. Use higher numbers to be
   *     called back earlier, lower numbers for later callbacks. Within a priority, callbacks are
   *     processed in no particular order.
   *     <ul>
   *       <li>{@code NotActiveException} The stream is not currently reading objects so it is
   *           invalid to register a callback.
   *       <li>{@code InvalidObjectException} The validation object is null.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public void registerValidation(ObjectInputValidation obj, int prio) throws ReceiverException {
    try {
      receiver.registerValidation(obj, prio);
    } catch (NotActiveException e) {
      throw new ReceiverException("Поток в настоящее время не читает объекты", e);
    } catch (InvalidObjectException e) {
      throw new ReceiverException("Объект валидации - null", e);
    }
  }

  /**
   * Reads a byte of data. This method will block if no input is available.
   *
   * @return the byte read, or -1 if the end of the stream is reached.
   *     <p>{@code IOException If an I/O error has occurred.}
   * @throws ReceiverException combines all receiver exceptions
   */
  public int read() throws ReceiverException {
    try {
      return receiver.read();
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads into an array of bytes. This method will block until some input is available. Consider
   * using java.io.DataInputStream.readFully to read exactly 'length' bytes.
   *
   * @param buf the buffer into which the data is read
   * @param off the start offset of the data
   * @param len the maximum number of bytes read
   * @return the actual number of bytes read, -1 is returned when the end of the stream is reached.
   *     <p>{@code IOException If an I/O error has occurred.}
   * @throws ReceiverException combines all receiver exceptions
   * @see DataInputStream#readFully(byte[], int, int)
   */
  public int read(byte[] buf, int off, int len) throws ReceiverException {
    try {
      return receiver.read(buf, off, len);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Returns the number of bytes that can be read without blocking.
   *
   * @return the number of available bytes.
   *     <p>{@code IOException} if there are I/O errors while reading from the underlying {@code
   *     InputStream}
   * @throws ReceiverException combines all receiver exceptions
   */
  public int available() throws ReceiverException {
    try {
      return receiver.available();
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Closes the input stream. Must be called to release any resources associated with the stream.
   *
   * <p>{@code IOException} If an I/O error has occurred.
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public void close() throws ReceiverException {
    try {
      receiver.close();
    } catch (IOException e) {
      throw new ReceiverException("Ошибка закрытия потока получения", e);
    }
  }

  /**
   * Reads in a boolean.
   *
   * @return the boolean read.
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public boolean readBoolean() throws ReceiverException {
    try {
      return receiver.readBoolean();
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads an 8 bit byte.
   *
   * @return the 8 bit byte read.
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public byte readByte() throws ReceiverException {
    try {
      return receiver.readByte();
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads an unsigned 8 bit byte.
   *
   * @return the 8 bit byte read.
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public int readUnsignedByte() throws ReceiverException {
    try {
      return receiver.readUnsignedByte();
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads a 16 bit char.
   *
   * @return the 16 bit char read.
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public char readChar() throws ReceiverException {
    try {
      return receiver.readChar();
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads a 16 bit short.
   *
   * @return the 16 bit short read.
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public short readShort() throws ReceiverException {
    try {
      return receiver.readShort();
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads an unsigned 16 bit short.
   *
   * @return the 16 bit short read.
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public int readUnsignedShort() throws ReceiverException {
    try {
      return receiver.readUnsignedShort();
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads a 32 bit int.
   *
   * @return the 32 bit integer read.
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public int readInt() throws ReceiverException {
    try {
      return receiver.readInt();
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads a 64 bit long.
   *
   * @return the read 64 bit long.
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public long readLong() throws ReceiverException {
    try {
      return receiver.readLong();
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads a 32 bit float.
   *
   * @return the 32 bit float read.
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public float readFloat() throws ReceiverException {
    try {
      return receiver.readFloat();
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads a 64 bit double.
   *
   * @return the 64 bit double read.
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public double readDouble() throws ReceiverException {
    try {
      return receiver.readDouble();
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads bytes, blocking until all bytes are read.
   *
   * @param buf the buffer into which the data is read
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public void readFully(byte[] buf) throws ReceiverException {
    try {
      receiver.readFully(buf);
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads bytes, blocking until all bytes are read.
   *
   * @param buf the buffer into which the data is read
   * @param off the start offset of the data
   * @param len the maximum number of bytes to read
   *     <ul>
   *       <li>{@code EOFException} If end of file is reached.
   *       <li>{@code IOException} If other I/O error has occurred.
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public void readFully(byte[] buf, int off, int len) throws ReceiverException {
    try {
      receiver.readFully(buf, off, len);
    } catch (EOFException e) {
      throw new ReceiverException("Достигнут конец файла", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Skips bytes.
   *
   * @param len the number of bytes to be skipped
   * @return the actual number of bytes skipped.
   *     <p>{@code IOException If an I/O error has occurred.}
   * @throws ReceiverException combines all receiver exceptions
   */
  public int skipBytes(int len) throws ReceiverException {
    try {
      return receiver.skipBytes(len);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads in a line that has been terminated by a \n, \r, \r\n or EOF.
   *
   * @return a String copy of the line.
   *     <p>{@code IOException} if there are I/O errors while reading from the underlying {@code
   *     InputStream}
   * @throws ReceiverException combines all receiver exceptions
   * @deprecated This method does not properly convert bytes to characters. see DataInputStream for
   *     the details and alternatives.
   */
  @Deprecated
  public String readLine() throws ReceiverException {
    try {
      return receiver.readLine();
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads a String in <a href="DataInput.html#modified-utf-8">modified UTF-8</a> format.
   *
   * @return the String.
   *     <ul>
   *       <li>{@code IOException} if there are I/O errors while reading from the underlying {@code
   *           InputStream}
   *       <li>{@code UTFDataFormatException} if read bytes do not represent a valid modified UTF-8
   *           encoding of a string
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   */
  public String readUTF() throws ReceiverException {
    try {
      return receiver.readUTF();
    } catch (UTFDataFormatException e) {
      throw new ReceiverException("Ошибка представления байто в UTF формате", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Reads some number of bytes from the input stream and stores them into the buffer array {@code b
   * }. The number of bytes actually read is returned as an integer. This method blocks until input
   * data is available, end of file is detected, or an exception is thrown.
   *
   * <p>If the length of {@code b} is zero, then no bytes are read and {@code 0} is returned;
   * otherwise, there is an attempt to read at least one byte. If no byte is available because the
   * stream is at the end of the file, the value {@code -1} is returned; otherwise, at least one
   * byte is read and stored into {@code b}.
   *
   * <p>The first byte read is stored into element {@code b[0]}, the next one into {@code b[1] },
   * and so on. The number of bytes read is, at most, equal to the length of {@code b} . Let
   * <i>k</i> be the number of bytes actually read; these bytes will be stored in elements {@code
   * b[0]} through {@code b[}<i>k</i>{@code -1]}, leaving elements {@code b[ }<i>k</i>{@code ]}
   * through {@code b[b.length-1]} unaffected.
   *
   * <p>The {@code read(b)} method for class {@code InputStream} has the same effect as:
   *
   * <pre>{@code  read(b, 0, b.length) }</pre>
   *
   * @param b the buffer into which the data is read.
   * @return the total number of bytes read into the buffer, or {@code -1} if there is no more data
   *     because the end of the stream has been reached.
   *     <ul>
   *       <li>{@code IOException} If the first byte cannot be read for any reason other than the
   *           end of the file, if the input stream has been closed, or if some other I/O error
   *           occurs
   *       <li>{@code NullPointerException} if {@code b} is {@code null}
   *     </ul>
   *
   * @throws ReceiverException combines all receiver exceptions
   * @see InputStream#read(byte[], int, int)
   */
  public int read(byte[] b) throws ReceiverException {
    try {
      return receiver.read(b);
    } catch (NullPointerException e) {
      throw new ReceiverException("Переданный параметр - null", e);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Skips over and discards {@code n} bytes of data from this input stream. The {@code skip }
   * method may, for a variety of reasons, end up skipping over some smaller number of bytes,
   * possibly {@code 0}. This may result from any of a number of conditions; reaching end of file
   * before {@code n} bytes have been skipped is only one possibility. The actual number of bytes
   * skipped is returned. If {@code n} is negative, the {@code skip} method for class {@code
   * InputStream} always returns 0, and no bytes are skipped. Subclasses may handle the negative
   * value differently.
   *
   * <p>The {@code skip} method of this class creates a byte array and then repeatedly reads into it
   * until {@code n} bytes have been read or the end of the stream has been reached. Subclasses are
   * encouraged to provide a more efficient implementation of this method. For instance, the
   * implementation may depend on the ability to seek.
   *
   * @param n the number of bytes to be skipped.
   * @return the actual number of bytes skipped.
   *     <p>{@code IOException} if the stream does not support seek, or if some other I/O error
   *     occurs
   * @throws ReceiverException combines all receiver exceptions
   */
  public long skip(long n) throws ReceiverException {
    try {
      return receiver.skip(n);
    } catch (IOException e) {
      throw new ReceiverException("Ошибка получения объекта", e);
    }
  }

  /**
   * Marks the current position in this input stream. A subsequent call to the {@code reset} method
   * repositions this stream at the last marked position so that subsequent reads re-read the same
   * bytes.
   *
   * <p>The {@code readlimit} arguments tells this input stream to allow that many bytes to be read
   * before the mark position gets invalidated.
   *
   * <p>The general contract of {@code mark} is that, if the method {@code markSupported} returns
   * {@code true}, the stream somehow remembers all the bytes read after the call to {@code mark}
   * and stands ready to supply those same bytes again if and whenever the method {@code reset} is
   * called. However, the stream is not required to remember any data at all if more than {@code
   * readlimit} bytes are read from the stream before {@code reset} is called.
   *
   * <p>Marking a closed stream should not have any effect on the stream.
   *
   * <p>The {@code mark} method of {@code InputStream} does nothing.
   *
   * @param readlimit the maximum limit of bytes that can be read before the mark position becomes
   *     invalid.
   * @see InputStream#reset()
   */
  public void mark(int readlimit) {
    receiver.mark(readlimit);
  }

  /**
   * Repositions this stream to the position at the time the {@code mark} method was last called on
   * this input stream.
   *
   * <p>The general contract of {@code reset} is:
   *
   * <ul>
   *   <li>If the method {@code markSupported} returns {@code true}, then:
   *       <ul>
   *         <li>If the method {@code mark} has not been called since the stream was created, or the
   *             number of bytes read from the stream since {@code mark} was last called is larger
   *             than the argument to {@code mark} at that last call, then an {@code IOException}
   *             might be thrown.
   *         <li>If such an {@code IOException} is not thrown, then the stream is reset to a state
   *             such that all the bytes read since the most recent call to {@code mark} (or since
   *             the start of the file, if {@code mark} has not been called) will be resupplied to
   *             subsequent callers of the {@code read} method, followed by any bytes that otherwise
   *             would have been the next input data as of the time of the call to {@code reset}.
   *       </ul>
   *   <li>If the method {@code markSupported} returns {@code false}, then:
   *       <ul>
   *         <li>The call to {@code reset} may throw an {@code IOException}.
   *         <li>If an {@code IOException} is not thrown, then the stream is reset to a fixed state
   *             that depends on the particular type of the input stream and how it was created. The
   *             bytes that will be supplied to subsequent callers of the {@code read } method
   *             depend on the particular type of the input stream.
   *       </ul>
   * </ul>
   *
   * <p>The method {@code reset} for class {@code InputStream} does nothing except throw an {@code
   * IOException}.
   *
   * <p>{@code IOException} if this stream has not been marked or if the mark has been invalidated.
   *
   * @throws ReceiverException combines all receiver exceptions
   * @see InputStream#mark(int)
   * @see IOException
   */
  public void reset() throws ReceiverException {
    try {
      receiver.reset();
    } catch (IOException e) {
      throw new ReceiverException("Ошибка сброса отметок", e);
    }
  }

  /**
   * Tests if this input stream supports the {@code mark} and {@code reset} methods. Whether or not
   * {@code mark} and {@code reset} are supported is an invariant property of a particular input
   * stream instance. The {@code markSupported} method of {@code InputStream} returns {@code false}.
   *
   * @return {@code true} if this stream instance supports the mark and reset methods; {@code false}
   *     otherwise.
   * @see InputStream#mark(int)
   * @see InputStream#reset()
   */
  public boolean markSupported() {
    return receiver.markSupported();
  }
}

package ru.lab.common.io;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.file.Files;
import java.util.stream.Stream;

/** Input class */
public class Input {
  private BufferedReader reader;

  /** Default Reader constructor. Takes System.in */
  public Input() {
    reader = new BufferedReader(new InputStreamReader(System.in));
  }

  /**
   * Reader constructor
   *
   * @param reader reader to read from
   */
  public Input(BufferedReader reader) {
    this.reader = reader;
  }

  /**
   * Reads a single character.
   *
   * @return The character read, as an integer in the range 0 to 65535 (<tt>0x00-0xffff</tt>), or -1
   *     if the end of the stream has been reached
   * @exception InputException If an I/O error occurs
   */
  public int read() throws InputException {
    try {
      return reader.read();
    } catch (IOException e) {
      throw new InputException("Ошибка чтения", e);
    }
  }

  /**
   * Reads characters into a portion of an array.
   *
   * <p>This method implements the general contract of the corresponding <code>
   * {@link Reader#read(char[], int, int) read}</code> method of the <code>{@link Reader}</code>
   * class. As an additional convenience, it attempts to read as many characters as possible by
   * repeatedly invoking the <code>read</code> method of the underlying stream. This iterated <code>
   * read</code> continues until one of the following conditions becomes true:
   *
   * <ul>
   *   <li>The specified number of characters have been read,
   *   <li>The <code>read</code> method of the underlying stream returns <code>-1</code>, indicating
   *       end-of-file, or
   *   <li>The <code>ready</code> method of the underlying stream returns <code>false</code>,
   *       indicating that further input requests would block.
   * </ul>
   *
   * If the first <code>read</code> on the underlying stream returns <code>-1</code> to indicate
   * end-of-file then this method returns <code>-1</code>. Otherwise this method returns the number
   * of characters actually read.
   *
   * <p>Subclasses of this class are encouraged, but not required, to attempt to read as many
   * characters as possible in the same fashion.
   *
   * <p>Ordinarily this method takes characters from this stream's character buffer, filling it from
   * the underlying stream as necessary. If, however, the buffer is empty, the mark is not valid,
   * and the requested length is at least as large as the buffer, then this method will read
   * characters directly from the underlying stream into the given array. Thus redundant <code>
   * BufferedReader</code>s will not copy data unnecessarily.
   *
   * @param cbuf Destination buffer
   * @param off Offset at which to start storing characters
   * @param len Maximum number of characters to read
   * @return The number of characters read, or -1 if the end of the stream has been reached
   * @exception InputException If an I/O error occurs
   */
  public int read(char[] cbuf, int off, int len) throws InputException {
    try {
      return reader.read(cbuf, off, len);
    } catch (IOException e) {
      throw new InputException("Ошибка пропуска символов", e);
    }
  }

  /**
   * Reads a line of text. A line is considered to be terminated by any one of a line feed ('\n'), a
   * carriage return ('\r'), or a carriage return followed immediately by a linefeed.
   *
   * @return A String containing the contents of the line, not including any line-termination
   *     characters, or null if the end of the stream has been reached
   * @exception InputException If an I/O error occurs
   * @see Files#readAllLines
   */
  public String readLine() throws InputException {
    try {
      return reader.readLine();
    } catch (IOException e) {
      throw new InputException("Ошибка пропуска символов", e);
    }
  }

  /**
   * Skips characters.
   *
   * @param n The number of characters to skip
   * @return The number of characters actually skipped
   * @exception IllegalArgumentException If {@code n < 0}.
   * @exception InputException If an I/O error occurs
   */
  public long skip(long n) throws InputException {
    try {
      return reader.skip(n);
    } catch (IllegalArgumentException e) {
      throw new InputException("Количетсво элементов не может быть отрицательным", e);
    } catch (IOException e) {
      throw new InputException("Ошибка пропуска символов", e);
    }
  }

  /**
   * Tells whether this stream is ready to be read. A buffered character stream is ready if the
   * buffer is not empty, or if the underlying character stream is ready.
   *
   * @exception InputException If an I/O error occurs
   */
  public boolean ready() throws InputException {
    try {
      return reader.ready();
    } catch (IOException e) {
      throw new InputException("Ошибка проверки готовности", e);
    }
  }

  /** Tells whether this stream supports the mark() operation, which it does. */
  public boolean markSupported() {
    return reader.markSupported();
  }

  /**
   * Marks the present position in the stream. Subsequent calls to reset() will attempt to
   * reposition the stream to this point.
   *
   * @param readAheadLimit Limit on the number of characters that may be read while still preserving
   *     the mark. An attempt to reset the stream after reading characters up to this limit or
   *     beyond may fail. A limit value larger than the size of the input buffer will cause a new
   *     buffer to be allocated whose size is no smaller than limit. Therefore large values should
   *     be used with care.
   * @exception IllegalArgumentException If {@code readAheadLimit < 0}
   * @exception InputException If an I/O error occurs
   */
  public void mark(int readAheadLimit) throws InputException {
    try {
      reader.mark(readAheadLimit);
    } catch (IllegalArgumentException e) {
      throw new InputException("Лимит чтения не может быть отрицательным", e);
    } catch (IOException e) {
      throw new InputException("Ошибка установки отметки", e);
    }
  }

  /**
   * Resets the stream to the most recent mark.
   *
   * @exception InputException If the stream has never been marked, or if the mark has been
   *     invalidated
   */
  public void reset() throws InputException {
    try {
      reader.reset();
    } catch (IOException e) {
      throw new InputException("Ошибка сброса отметки", e);
    }
  }

  public void close() throws InputException {
    try {
      reader.close();
    } catch (IOException e) {
      throw new InputException("Ошибка чтения", e);
    }
  }

  /**
   * Returns a {@code Stream}, the elements of which are lines read from this {@code
   * BufferedReader}. The {@link Stream} is lazily populated, i.e., read only occurs during the <a
   * href="../util/stream/package-summary.html#StreamOps">terminal stream operation</a>.
   *
   * <p>The reader must not be operated on during the execution of the terminal stream operation.
   * Otherwise, the result of the terminal stream operation is undefined.
   *
   * <p>After execution of the terminal stream operation there are no guarantees that the reader
   * will be at a specific position from which to read the next character or line.
   *
   * <p>If an {@link InputException} is thrown when accessing the underlying {@code BufferedReader},
   * it is wrapped in an {@link UncheckedIOException} which will be thrown from the {@code Stream}
   * method that caused the read to take place. This method will return a Stream if invoked on a
   * BufferedReader that is closed. Any operation on that stream that requires reading from the
   * BufferedReader after it is closed, will cause an UncheckedIOException to be thrown.
   *
   * @return a {@code Stream<String>} providing the lines of text described by this {@code
   *     BufferedReader}
   * @since 1.8
   */
  public Stream<String> lines() {
    return reader.lines();
  }

  /**
   * Attempts to read characters into the specified character buffer. The buffer is used as a
   * repository of characters as-is: the only changes made are the results of a put operation. No
   * flipping or rewinding of the buffer is performed.
   *
   * @param target the buffer to read characters into
   * @return The number of characters added to the buffer, or -1 if this source of characters is at
   *     its end
   * @throws InputException if an I/O error occurs
   * @throws NullPointerException if target is null
   * @throws ReadOnlyBufferException if target is a read only buffer
   * @since 1.5
   */
  public int read(CharBuffer target) throws InputException {
    try {
      return reader.read(target);
    } catch (NullPointerException e) {
      throw new InputException("Цель - null", e);
    } catch (ReadOnlyBufferException e) {
      throw new InputException("Цель доступна только для чтения", e);
    } catch (IOException e) {
      throw new InputException("Ошибка чтения", e);
    }
  }

  /**
   * Reads characters into an array. This method will block until some input is available, an I/O
   * error occurs, or the end of the stream is reached.
   *
   * @param cbuf Destination buffer
   * @return The number of characters read, or -1 if the end of the stream has been reached
   * @exception InputException If an I/O error occurs
   */
  public int read(char[] cbuf) throws InputException {
    try {
      return reader.read(cbuf);
    } catch (IOException e) {
      throw new InputException("Ошибка чтения", e);
    }
  }
}

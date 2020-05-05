package nano_id;

import java.security.SecureRandom;
import clojure.lang.IFn;

public final class NanoID {
  private NanoID() {}
  
  public static final int MASK = 0x3f;
  
  public static final SecureRandom secureRandom = new SecureRandom();
  
  public static final char[] alphabet =
    "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
      
  public static String nanoID() {
    return nanoID(21);
  }
  
  public static String nanoID(int size) {
    final StringBuilder id = new StringBuilder();
    final byte[] bytes = new byte[size];
    secureRandom.nextBytes(bytes);
    for (int i = 0; i < size; ++i) {
      id.append(alphabet[bytes[i] & MASK]);
    };
    return id.toString();
  }
  
  public static String custom(char[] alphabet, int size, IFn random, int step, int mask) {
    final StringBuilder id = new StringBuilder();
    while (true) {
      final byte[] bytes = (byte[]) random.invoke(step);
      for (int i = 0; i < step; ++i) {
      final int index = bytes[i] & mask;
      if (index < alphabet.length) {
          id.append(alphabet[index]);
          if (id.length() == size) {
            return id.toString();
          }
        }
      }
    }
  }
}

package nano_id;

import java.util.Random;
import clojure.lang.IFn;

public final class NanoID {
  private NanoID() {}
  
  public static final int MASK = 0x3f;
  
  //public static SecureRandom secureRandom = new SecureRandom();
  
  public static final char[] alphabet =
    "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
      
  public static String nanoID(Random random) {
    return nanoID(random, 21);
  }
  
  public static String nanoID(Random random, int size) {
    final StringBuilder id = new StringBuilder();
    final byte[] bytes = new byte[size];
    random.nextBytes(bytes);
    for (int i = 0; i < size; ++i) {
      id.append(alphabet[bytes[i] & MASK]);
    };
    return id.toString();
  }
  
  public static String custom(IFn random, int size, char[] alphabet, int step, int mask) {
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

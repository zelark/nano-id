package nano_id;

import java.security.SecureRandom;
import clojure.lang.IFn;

class NanoIdUtils {
  public NanoIdUtils() { this.secureRandom = new SecureRandom(); }

  public SecureRandom secureRandom;
  
  public final int MASK = 0x3f;
  
  public final char[] alphabet = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
}

public class NanoID {
  public NanoID() { this.utils = new NanoIdUtils(); }

  public SecureRandom getSecureRandom() { return this.utils.secureRandom; }

  public NanoIdUtils utils;

  public String newId() { return newId(21); }
  public String newId(int size) {
    final StringBuilder id = new StringBuilder();
    final byte[] bytes = new byte[size];
    utils.secureRandom.nextBytes(bytes);
    for (int i = 0; i < size; ++i) {
      id.append(utils.alphabet[bytes[i] & utils.MASK]);
    };
    return id.toString();
  }

  public String custom(char[] alphabet, int size, IFn random, int step, int mask) {
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

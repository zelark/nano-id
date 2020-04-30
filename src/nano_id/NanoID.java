package nano_id;

import java.security.SecureRandom;

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
}

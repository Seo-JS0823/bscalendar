package com.bscalendar.jwt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaTest {
    public static String encSHA512(String str) {
      MessageDigest md = null;
      try {md = MessageDigest.getInstance("SHA-512");md.update(str.getBytes());} catch (NoSuchAlgorithmException e) {e.printStackTrace();}
      return md == null ? null : bytesToHex(md.digest());
   } // End of encSHA256()

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    } // End of bytesToHex()
}

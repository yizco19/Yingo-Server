package cm.zy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    public static String encodePassword(String s) {
        try {
            // Crear un objeto MessageDigest para MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Aplicar el hash a la entrada
            byte[] messageDigest = md.digest(s.getBytes());

            // Convertir el hash de bytes a formato hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejar la excepción si no se encuentra el algoritmo de hash
            e.printStackTrace();
            return null;
        }
    }

    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        return encodePassword(rawPassword).equals(encodedPassword);
    }

}

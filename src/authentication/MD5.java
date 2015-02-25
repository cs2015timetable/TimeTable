package authentication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
/**
 * This is a class that contains a static method for converting a string to its hash value. e.g dog => 06D80EB0C50B49A509B49F2424E8C805
 * @author http://www.asjava.com/core-java/java-md5-example/
 * This class has been manipulated by adding in a try catch and removing unwanted variables.
 */
public class MD5 {
    /**
     *  This is a static method which takes in a string and returns a string representation of a hash value.
     * @param input The string value of the string to be converted to md5 hash code.
     * @return hashtext A string which is a representation of the hash code.
     */
    public static String getMD5(String input) {
        try {
            /**
             * MessageDigest provides a secure one way hash function using the algorithm for md5.
             */
            MessageDigest md = MessageDigest.getInstance("MD5");
            /**
             * This is a byte array for the resulting hash value.
             */
            byte[] messageDigest = md.digest(input.getBytes());
            /**
             * Translates the sign-magnitude representation of a BigInteger into a BigInteger. // 1 is positive. 0 is zero and -1 is negative.
             */
            BigInteger number = new BigInteger(1, messageDigest);
            // A string variable to hold the toString of the number variable which is a bigInteger.
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            //Returns the string variable which holds the hashText.
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            //Throws a Run Time Exception when a no such algorithm exception occurs.
            //This occurs when the md cannot be initialized.
            throw new RuntimeException(e);
        }
    } 
}

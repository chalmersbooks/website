package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public class RandomStringGenerator {

    private RandomStringGenerator() {
        // Nothing here
    }

    public static String generateRandomString(int length) {
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789").toCharArray();
        return RandomStringUtils.random(
                length,
                0,
                possibleCharacters.length - 1,
                false,
                false,
                possibleCharacters,
                new SecureRandom());
    }

}

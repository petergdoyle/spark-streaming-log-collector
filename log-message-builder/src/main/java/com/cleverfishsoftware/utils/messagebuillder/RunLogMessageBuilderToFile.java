/*
 */
package com.cleverfishsoftware.utils.messagebuillder;

import org.apache.logging.log4j.LogManager;
import static com.cleverfishsoftware.utils.messagebuillder.LogMessage.Level.info;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 */
public class RunLogMessageBuilderToFile {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        int limit = 0;
        boolean error = false;
        if (args == null || args.length == 0) {
            error = true;
        } else {
            try {
                limit = Integer.parseInt(args[0]);
            } catch (Exception ex) {
                error = true;
            }
        }
        if (error) {
            System.err.println("Usage RunLogMessageBuilder <size>\n"
                    + "size - the number of messages to write to file \n\n");
            System.exit(1);
        }
        // each class must declare it's own logger and pass it to the LogBuilder or else we lose class level log scope
        org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(RunLogMessageBuilderToFile.class.getName());
        Lorem lorem = LoremIpsum.getInstance();
        // create instance of Random class 
        Random rand = new Random();
        SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
        //generate a random number
        String randomNum = Integer.toString(prng.nextInt());
        //get its digest
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        Random random = new Random();
        for (int i = 0; i < limit; i++) {
            // build the message with random data
            byte[] result = sha.digest(randomNum.getBytes());
            String trackingId = hexEncode(result);
            String body = lorem.getWords(5, 10);
            LogMessage.Level randomLevel = LogMessage.Level.getRandomLevel(random);
            new LogMessage.Builder(LOGGER, info, body)
                    .addTag("trackId", trackingId)
                    .addTag("identifier", randomLevel.toString())
                    .log();
        }

    }

    static private String hexEncode(byte[] input) {
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < input.length; ++idx) {
            byte b = input[idx];
            result.append(digits[(b & 0xf0) >> 4]);
            result.append(digits[b & 0x0f]);
        }
        return result.toString();
    }

}

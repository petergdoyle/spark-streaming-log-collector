/*
 */
package com.cleverfishsoftware.utils.messagebuillder;

import org.apache.logging.log4j.LogManager;
import static com.cleverfishsoftware.utils.messagebuillder.LogMessage.Level.info;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

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
        System.out.println("ready to start...");
        // each class must declare it's own logger and pass it to the LogBuilder or else we lose class level log scope
        org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(RunLogMessageBuilderToFile.class.getName());
        Lorem lorem = LoremIpsum.getInstance();
        Random random = new Random();
        int min = 2;
        int max = 6;
        System.out.println("about to start...");
        for (int i = 0; i < limit; i++) {
            
            LogMessage.Level randomLevel = LogMessage.Level.getRandomLevel(random);
            String trackingId = UUID.randomUUID().toString();
            
            if (randomLevel.equals(LogMessage.Level.error) || randomLevel.equals(LogMessage.Level.fatal)) {
                int r = random.nextInt((max - min) + 1) + min;
                System.out.println("trackingId=" + trackingId + "r=" + r);
                for (int j = 0; j < r; j++) {
                    String body = lorem.getWords(5, 10);
                    new LogMessage.Builder(LOGGER, randomLevel, body)
                            .addTag("trackId", trackingId)
                            .addTag("identifier", randomLevel.toString())
                            .log();
                }
            } else {
                String body = lorem.getWords(5, 10);
                new LogMessage.Builder(LOGGER, randomLevel, body)
                        .addTag("trackId", trackingId)
                        .addTag("identifier", randomLevel.toString())
                        .log();

            }
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

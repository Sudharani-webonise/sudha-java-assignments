package com.netmagic.spectrum.utils;

import java.util.UUID;

/**
 * This class generates a random UUID
 * 
 * @author webonise
 *
 */
public class RandomString {

    private static final String MNP = "mnp-";

    public static synchronized String generateCloudToken() {
        return UUID.randomUUID().toString() + System.currentTimeMillis();
    }

    public static synchronized String generateSofToken() {
        return UUID.randomUUID().toString() + System.currentTimeMillis();
    }

    public static synchronized String generateMnpToken() {
        return MNP + UUID.randomUUID().toString();
    }
}

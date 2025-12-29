package com.example.oemunlock;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

public class SystemSecurityUtils {
    private static final String TAG = "SystemSecurityUtils";

    /**
     * Reads a system property using Reflection.
     * Equivalent to: SystemProperties.get(key, defaultValue)
     */
    public static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method getMethod = systemPropertiesClass.getMethod("get", String.class, String.class);
            return (String) getMethod.invoke(null, key, defaultValue);
        } catch (Exception e) {
            Log.e(TAG, "Failed to read property: " + key, e);
            return defaultValue;
        }
    }

    /**
     * Attempts to enable OEM Unlock via PersistentDataBlockManager.
     * Requires: App to be signed with System Keys or Root.
     */
    public static void setOemUnlockEnabled(Context context, boolean enabled) {
        try {
            // 1. Get the Service using the string name since the constant might be hidden
            Object manager = context.getSystemService("persistent_data_block");

            if (manager != null) {
                // 2. Find the hidden method via reflection
                Method setMethod = manager.getClass().getMethod("setOemUnlockEnabled", boolean.class);

                // 3. Invoke the method
                setMethod.invoke(manager, enabled);
                Log.d(TAG, "Successfully invoked setOemUnlockEnabled(" + enabled + ")");
            } else {
                Log.e(TAG, "PersistentDataBlockManager is null. Device may not support PDB.");
            }
        } catch (Exception e) {
            // This will likely catch a SecurityException if your UID is not 1000 (System)
            Log.e(TAG, "Critical failure attempting to set OEM Unlock: " + e.getCause());
        }
    }

    /**
     * Helper to check the bootloader lock status directly
     */
    public static boolean isBootloaderLocked() {
        String status = getSystemProperty("ro.boot.flash.locked", "1");
        return "1".equals(status);
    }
}

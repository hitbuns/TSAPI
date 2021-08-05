package com.PacketAnal;

import com.sun.istack.internal.NotNull;
import org.bukkit.ChatColor;

public class Utils {

    public static String color(@NotNull String s) {
        return (s != null) ? ChatColor
                .translateAlternateColorCodes('&',s):null;
    }

}

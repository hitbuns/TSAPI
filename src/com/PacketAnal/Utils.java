package com.PacketAnal;

import com.sun.istack.internal.NotNull;
import org.bukkit.ChatColor;

public class Utils {

    public static String color(@NotNull String s) {
        return ChatColor
                .translateAlternateColorCodes('&',s);
    }

}

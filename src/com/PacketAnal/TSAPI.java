package com.PacketAnal;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

public class TSAPI extends JavaPlugin {

    public static void sendActionbar(String message,Player... players) {
        if (message == null) return;
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(new ChatComponentText(Utils
        .color(message)), (byte) 2);
        if (players == null || players.length == 0) {
            send(Bukkit.getOnlinePlayers(),packetPlayOutChat);
            return;
        }
        send(Arrays.asList(players),packetPlayOutChat);
    }

    public static void sendTitle(String title, String subTitle, int fadeInTicks, int stayInTicks, int fadeOutTicks, Player... players) {
        if (title == null && subTitle == null) return;
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, title != null?
                new ChatComponentText(Utils.color(title)):null, fadeInTicks, stayInTicks, fadeOutTicks);
        PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitle != null?
                new ChatComponentText(Utils.color(subTitle)):null, fadeInTicks, stayInTicks, fadeOutTicks);
        if (players == null || players.length == 0) {
            send(Bukkit.getOnlinePlayers(),titlePacket,subTitlePacket);
            return;
        }
        send(Arrays.asList(players),titlePacket,subTitlePacket);
    }

    public static void updateTabHeader(String header,String footer,Player... players) {
        PacketPlayOutPlayerListHeaderFooter packetPlayOutPlayerListHeaderFooter =
                new PacketPlayOutPlayerListHeaderFooter((header !=null)?new ChatComponentText(Utils.color(header)):null);
        if (footer != null) {
            try {
                Field field = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("b");
                field.setAccessible(true);
                field.set(packetPlayOutPlayerListHeaderFooter, new ChatComponentText(Utils.color(footer)));
                field.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (players == null || players.length == 0) {
            send(Bukkit.getOnlinePlayers(),packetPlayOutPlayerListHeaderFooter);
            return;
        }
        send(Arrays.asList(players),packetPlayOutPlayerListHeaderFooter);
    }

    private static void send(Collection<? extends Player> players,Packet<PacketListenerPlayOut>... packets) {
        for (Player player : players) {
            PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
            for (Packet<PacketListenerPlayOut> packet : packets) playerConnection.sendPacket(packet);
        }
    }

    //


}

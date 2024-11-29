package com.davishi.copychat;



import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Mouse;

@Mod(modid = CopyChat.MODID, version = CopyChat.VERSION, name = CopyChat.MODNAME, clientSideOnly = true)
public class CopyChat
{
    public static final String MODNAME = "Copy-Chat";
    public static final String MODID = "copychat";
    public static final String VERSION = "0.1";


    @EventHandler
    public void init(FMLInitializationEvent event) {MinecraftForge.EVENT_BUS.register(this);}

    /*@SubscribeEvent
    public void preDrawScreen(GuiNewChat g) {
        ChatLine chat = event;
        if(g.getChatOpen())
            hovered = g.getChatComponent(Mouse.getX(), Mouse.getY());
        else hovered = null;
    }*/

    @SubscribeEvent
    public void copy(GuiNewChat g) {
        if (GuiScreen.isCtrlKeyDown() && g.getChatOpen()){
            int button = Mouse.getEventButton();
            if (g.getChatComponent(Mouse.getX(), Mouse.getY()) != null && button == 0)
                GuiScreen.setClipboardString(g.getChatComponent(Mouse.getX(), Mouse.getY()).getUnformattedText());
        }
    }

}

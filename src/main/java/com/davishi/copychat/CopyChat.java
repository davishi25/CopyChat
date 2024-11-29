package com.davishi.copychat;



import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.MouseEvent;
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

    private ChatComponentText hovered = null;

    @SubscribeEvent
    public void preDrawScreen(GuiScreenEvent.DrawScreenEvent.Pre event) {
        GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        if(chat.getChatOpen())
            hovered = new ChatComponentText(chat.getChatComponent(Mouse.getX(),Mouse.getY()).getUnformattedText());
        else hovered = null;
    }
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void copy(GuiScreenEvent.MouseInputEvent.Pre e) {
        if(!(e.gui instanceof GuiChat)) return;
        GuiNewChat gui = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        if (GuiScreen.isCtrlKeyDown() && gui.getChatOpen()){
            int button = Mouse.getEventButton();
            if (button != 0) return;
            if (gui.getChatComponent(Mouse.getX(), Mouse.getY()) != null) {
                ChatComponentText component = hovered;
                GuiScreen.setClipboardString(StringUtils.stripControlCodes(component.getUnformattedText()));
            }
        }
    }

}

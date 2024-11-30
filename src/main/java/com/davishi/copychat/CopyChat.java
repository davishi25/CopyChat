package com.davishi.copychat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.*;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = CopyChat.MODID, version = CopyChat.VERSION, name = CopyChat.MODNAME, clientSideOnly = true)
public class CopyChat
{
    public static final String MODNAME = "Copy-Chat";
    public static final String MODID = "copychat";
    public static final String VERSION = "0.1";

    //initializes the command
    @Mod.Instance
    public static CopyChat instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new copy());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {MinecraftForge.EVENT_BUS.register(this);}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceived(ClientChatReceivedEvent e) {
        if (e.type == 2) return;
        e.message.setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "//copy " + e.message.getUnformattedText())));
    }

    //copy the argument
    public static class copy extends CommandBase {
        @Override
        public String getCommandName() { return "/copy"; }

        @Override
        public String getCommandUsage(ICommandSender sender) { return "//copy <arg>"; }

        @Override
        public void processCommand(ICommandSender sender, String[] args) throws CommandException {
            if(GuiScreen.isCtrlKeyDown()) {
                String out = "";
                for(String s : args)
                    out += s + " ";
                out = out.substring(0,out.length() - 1);
                GuiScreen.setClipboardString(StringUtils.stripControlCodes(out));
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Copied Message!"));
            }
        }
        @Override
        public boolean canCommandSenderUseCommand(ICommandSender sender) { return true; }
    }
}

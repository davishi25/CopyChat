package copychat;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "copychat", version = "1.0", name = "CopyChat", clientSideOnly = true)
public class CopyChat {


    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onChatReceived(ClientChatReceivedEvent e) {
        if (e.type == 2) return; // Ignore actionbar messages

        String unformattedText = StringUtils.stripControlCodes(e.message.getUnformattedText());

        if (!unformattedText.replace(" ", "").isEmpty()) {
            ChatComponentText copyText = new ChatComponentText(EnumChatFormatting.DARK_GRAY + Character.toString((char) Integer.parseInt("270D", 16)));
            ChatStyle style = new ChatStyle()
                    .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.GRAY + "Copy message")))
                    .setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, unformattedText));
            copyText.setChatStyle(style);

            e.message.appendSibling(new ChatComponentText(EnumChatFormatting.RESET + " "));
            e.message.appendSibling(copyText);
        }
    }
}

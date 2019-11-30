package com.jredfox.upnp.client.gui;

import com.jredfox.upnp.ConfigPortforward;
import com.jredfox.upnp.PortMappings;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

public class GuiLan extends GuiScreen
{
    private final GuiScreen parent;
    private GuiButton allowCmds;
    private GuiButton gameMode;
    private String gameType = "survival";
    private boolean allowCheats;

    public GuiLan(GuiScreen parent)
    {
        this.parent = parent;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("lanServer.start", new Object[0])));
        this.buttonList.add(new GuiButton(102, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(this.gameMode = new GuiButton(104, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
        this.buttonList.add(this.allowCmds = new GuiButton(103, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
        this.refreshButtons();
    }

    private void refreshButtons()
    {
        this.gameMode.displayString = I18n.format("selectWorld.gameMode", new Object[0]) + " " + I18n.format("selectWorld.gameMode." + this.gameType, new Object[0]);
        this.allowCmds.displayString = I18n.format("selectWorld.allowCommands", new Object[0]) + " ";

        if (this.allowCheats)
        {
            this.allowCmds.displayString = this.allowCmds.displayString + I18n.format("options.on", new Object[0]);
        }
        else
        {
            this.allowCmds.displayString = this.allowCmds.displayString + I18n.format("options.off", new Object[0]);
        }
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 102)
        {
            this.mc.displayGuiScreen(this.parent);
        }
        else if (button.id == 104)
        {
            if (this.gameType.equals("survival"))
            {
                this.gameType = "creative";
            }
            else if (this.gameType.equals("creative"))
            {
                this.gameType = "adventure";
            }
            else
            {
                this.gameType = "survival";
            }

            this.refreshButtons();
        }
        else if (button.id == 103)
        {
            this.allowCheats = !this.allowCheats;
            this.refreshButtons();
        }
        else if (button.id == 101)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
            int port = PortMappings.getRndPort();
            boolean openToLan = PortMappings.openLan(port, WorldSettings.GameType.getByName(this.gameType), this.allowCheats);
            IChatComponent itextcomponent = openToLan ? new ChatComponentTranslation("lan.open", new Object[] {PortMappings.getPublicIp() + ":" + port}) : new ChatComponentTranslation("lan.closed", new Object[]{"" + port});
     	    this.mc.ingameGUI.getChatGUI().printChatMessage(itextcomponent);
            
            if(openToLan && ConfigPortforward.openToInternet)
            {
            	PortMappings.addScheduledMapping(port);
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("lanServer.title", new Object[0]), this.width / 2, 50, 16777215);
        this.drawCenteredString(this.fontRendererObj, I18n.format("lanServer.otherPlayers", new Object[0]), this.width / 2, 82, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}

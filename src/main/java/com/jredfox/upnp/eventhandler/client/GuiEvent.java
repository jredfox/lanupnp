package com.jredfox.upnp.eventhandler.client;

import com.jredfox.upnp.client.gui.GuiNet;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraftforge.client.event.GuiOpenEvent;

public class GuiEvent {
	
	@SubscribeEvent
	public void replaceGui(GuiOpenEvent event)
	{
		GuiScreen gui = event.gui;
		if(gui instanceof GuiShareToLan)
		{
			event.gui = new GuiNet(Minecraft.getMinecraft().currentScreen);
		}
	}

}

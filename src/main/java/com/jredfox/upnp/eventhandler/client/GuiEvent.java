package com.jredfox.upnp.eventhandler.client;

import com.jredfox.upnp.ConfigPortforward;
import com.jredfox.upnp.client.gui.GuiLan;
import com.jredfox.upnp.client.gui.GuiNet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiEvent {
	
	@SubscribeEvent
	public void replaceGui(GuiOpenEvent event)
	{
		GuiScreen gui = event.getGui();
		if(gui instanceof GuiShareToLan)
		{
			event.setGui( ConfigPortforward.randomPorts ? new GuiLan(Minecraft.getMinecraft().currentScreen) : new GuiNet(Minecraft.getMinecraft().currentScreen));
		}
	}

}

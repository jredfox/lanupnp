package com.jredfox.upnp.proxy;

import com.jredfox.upnp.PortMappings;
import com.jredfox.upnp.eventhandler.client.GuiEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends ServerProxy{
	
	
	@Override
	public void serverStopping()
	{
		System.out.println("Closing Ports....");
		PortMappings.closePorts();
	}
	
	@Override
	public void serverStarting()
	{
		
	}
	
	@Override
	public void init()
	{
		this.isClient = true;
	  	MinecraftForge.EVENT_BUS.register(new GuiEvent());
	}
	
	@Override
	public MinecraftServer getServer()
	{
		return Minecraft.getMinecraft().getIntegratedServer();
	}

}

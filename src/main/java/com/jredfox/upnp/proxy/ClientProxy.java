package com.jredfox.upnp.proxy;

import com.jredfox.upnp.PortMappings;
import com.jredfox.upnp.eventhandler.client.GuiEvent;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends ServerProxy{
	
	@Override
	public void serverStarting(MinecraftServer server)
	{
		
	}
	
	@Override
	public void serverStopping()
	{
		System.out.println("Closing Ports....");
		PortMappings.closePorts();
	}
	
	@Override
	public void init()
	{
	  	MinecraftForge.EVENT_BUS.register(new GuiEvent());
	}

}

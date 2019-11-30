package com.jredfox.upnp.proxy;

import com.jredfox.upnp.PortMappings;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;

public class ServerProxy {
	
	public boolean isClient;

	public void serverStarting()
	{
    	int port = DedicatedServer.getServer().getPort();
    	boolean opened = PortMappings.addMapping(port);
    	System.out.println((opened ? "opened" : "failed to open") + " port on:" + PortMappings.getPublicIp() + ":" + port);
	}

	public void serverStopping()
	{
    	int port = DedicatedServer.getServer().getPort();
    	System.out.println("closed:" + PortMappings.removeMapping(port) + "\t port:" + port);
	}
	
	public void preinit()
	{
		
	}

	public void init() 
	{
		
	}

	public MinecraftServer getServer() 
	{
		return DedicatedServer.getServer();
	}
	
}

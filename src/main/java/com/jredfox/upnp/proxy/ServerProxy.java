package com.jredfox.upnp.proxy;

import com.jredfox.upnp.PortMappings;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;

public class ServerProxy {
	
	public boolean isClient;

	public void serverStarting()
	{
    	int port = DedicatedServer.getServer().getPort();
    	System.out.println("opened:" + PortMappings.addMapping(port) + "\t port:" + port);
	}

	public void serverStopping()
	{
    	int port = DedicatedServer.getServer().getPort();
    	System.out.println("closed:" + PortMappings.removeMapping(port) + "\t port:" + port);
	}

	public void init() 
	{
		
	}

	public MinecraftServer getServer() 
	{
		return DedicatedServer.getServer();
	}
	
}

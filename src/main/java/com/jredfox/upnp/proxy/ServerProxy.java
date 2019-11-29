package com.jredfox.upnp.proxy;

import com.jredfox.upnp.PortMappings;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;

public class ServerProxy {
	
	public static MinecraftServer lastServer;
	public void serverStarting(MinecraftServer server)
	{
		lastServer = server;
    	int port = server.getServerPort();
    	System.out.println("opened:" + PortMappings.addMapping(port) + "\t port:" + port);
	}

	public void serverStopping()
	{
		int port = lastServer.getServerPort();
    	System.out.println("closed:" + PortMappings.removeMapping(port) + "\t port:" + port);
	}

	public void init() 
	{
		
	}
}

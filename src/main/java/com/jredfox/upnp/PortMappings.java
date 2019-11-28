package com.jredfox.upnp;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.dosse.upnp.UPnP;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.WorldSettings;

public class PortMappings {
	
	public static final List<Integer> ports = new ArrayList();
	
	public static boolean addMapping(int port)
	{
		boolean opened = UPnP.openPortTCP(port);
		if(opened)
			ports.add((Integer)port);
		return opened;
	}
	
	public static boolean removeMapping(int port)
	{
		boolean closed = UPnP.closePortTCP(port);
		if(closed)
			ports.remove((Integer)port);
		return closed;
	}
	
	public static void closePorts()
	{
		for(int i : ports)
		{
			UPnP.closePortTCP((Integer)i);
			System.out.println("closed port:" + i);
		}
		ports.clear();
	}
	
    /**
     * On dedicated does nothing. On integrated, sets commandsAllowedForAll, gameType and allows external connections.
     */
    public static boolean openLan(int port, WorldSettings.GameType type, boolean allowCheats)
    {
        try
        {
        	IntegratedServer server = Minecraft.getMinecraft().getIntegratedServer();
            server.func_147137_ag().addLanEndpoint((InetAddress)null, port);
            server.logger.info("Started on " + port);
            server.isPublic = true;
            server.lanServerPing = new ThreadLanServerPing(server.getMOTD(), port + "");
            server.lanServerPing.start();
            server.getConfigurationManager().func_152604_a(type);
            server.getConfigurationManager().setCommandsAllowedForAll(allowCheats);
            return true;
        }
        catch (IOException ioexception1)
        {
            return false;
        }
    }
    
	public static int getRndPort()
	{
        int i = 25564;

        try
        {
            i = HttpUtil.func_76181_a();
        }
        catch (IOException ioexception)
        {
            ;
        }
        return i;
	}

}

package com.jredfox.upnp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.dosse.upnp.UPnP;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.MinecraftForge;

public class PortMappings {
	
	public static final List<Integer> ports = new ArrayList();
	public static final String portDisplay = "Minecraft-" + MinecraftForge.MC_VERSION;
	
	public static boolean addMapping(int port)
	{
		boolean opened = UPnP.openPortTCP(port, portDisplay);
		if(opened)
		{
			ports.add((Integer)port);
		}
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
		}
		ports.clear();
	}
	
	public static void addScheduledMapping(final int port)
	{
		Thread thread = new Thread(
		new Runnable() { 
		@Override
		public void run() 
		{ 
		   long time = System.currentTimeMillis();
		   boolean opened = addMapping(port);
		   if(Minecraft.getMinecraft().ingameGUI != null)
			   Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText( (opened ? EnumChatFormatting.AQUA : EnumChatFormatting.RED) + (opened ? "port opened" : "port failed to open")));
		 }
		});
		
		thread.start();
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
        int port = 25564;

        try
        {
            port = HttpUtil.func_76181_a();
        }
        catch (IOException ioexception)
        {
            ;
        }
        return port;
	}

	public static void closeLan(IntegratedServer server)
	{
		List<EntityPlayerMP> players = server.serverConfigManager.playerEntityList;
		for(EntityPlayerMP p : players)
			if(!isPlayerOwner(p))
				disconnectPlayer(p);
		
	    closePorts();
        if (server.lanServerPing != null)
        {
            if (server.lanServerPing != null)
            {
                server.lanServerPing.interrupt();
                server.lanServerPing = null;
            }
        	if(server.func_147137_ag() != null)
        	{
        		server.func_147137_ag().terminateEndpoints();
        	}
        }
        server.isPublic = false;
	}
	
	public static void disconnectPlayer(EntityPlayerMP p) 
	{
		if(isPlayerOwner(p))
		{
			p.mcServer.initiateShutdown();
		}
		else
		{
			p.playerNetServerHandler.onDisconnect(new ChatComponentText("Resetting Lan World...."));
		}
	}

	public static boolean isPlayerOwner(EntityPlayerMP player)
	{
		return player.getCommandSenderName().equals(player.mcServer.getServerOwner());
	}
	
	/**
	 * your current computer adress's ip
	 */
	public static String getIpv4() throws UnknownHostException 
	{
        InetAddress inetAddress = InetAddress.getLocalHost();
		return inetAddress.getHostAddress();
	}
	
	/**
	 * dynamically get your current public ip adress I recommend cacheing it somewhere so it doesn't go throw a huge process each time
	 */
	public static String getPublicIp() throws IOException 
	{
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		String ip = in.readLine(); //you get the IP as a String
		return ip.trim();
	}

}

package com.jredfox.upnp;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.jredfox.upnp.command.CmdCloseLan;
import com.jredfox.upnp.proxy.ServerProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandManager;
import net.minecraft.server.MinecraftServer;

@Mod(modid = PortForwardMod.MODID, version = PortForwardMod.VERSION, name = PortForwardMod.NAME)
public class PortForwardMod
{
    public static final String MODID = "lanupnp";
    public static final String NAME = "Lan UPNP";
    public static final String VERSION = "1.0";
   
    @SidedProxy(serverSide = "com.jredfox.upnp.proxy.ServerProxy", clientSide = "com.jredfox.upnp.proxy.ClientProxy")
    public static ServerProxy proxy;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	ConfigPortforward.load(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init();
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
    	if(ConfigPortforward.openToInternet)
    		proxy.serverStarting();
    	
    	removePublish(event.getServer());
    	if(proxy.isClient)
    		event.registerServerCommand(new CmdCloseLan());
    }
    
    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event)
    {
    	if(ConfigPortforward.openToInternet)
    		proxy.serverStopping();
    }
    
	public static void removePublish(MinecraftServer server)
	{
    	//removes half of it
		String publish = "publish";
    	ICommandManager manager = server.getCommandManager();
    	Map<String,ICommand> map = manager.getCommands();
    	map.remove(publish);
    	
    	Set<ICommand> cmds = ((CommandHandler)manager).commandSet;
    	Iterator<ICommand> it = cmds.iterator();
    	while(it.hasNext())
    	{
    		ICommand cmd = it.next();
    		String s = cmd.getCommandName();
    		if(publish.equals(s))
    		{
    			it.remove();
    		}
    	}
	}
}

package com.jredfox.upnp;

import com.jredfox.upnp.proxy.ServerProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = PortForwardMod.MODID, version = PortForwardMod.VERSION, name = PortForwardMod.NAME)
public class PortForwardMod
{
    public static final String MODID = "lanupnp";
    public static final String NAME = "Lan UPNP";
    public static final String VERSION = "0.8";
   
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
    }
    
    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event)
    {
    	if(ConfigPortforward.openToInternet)
    		proxy.serverStopping();
    }
}

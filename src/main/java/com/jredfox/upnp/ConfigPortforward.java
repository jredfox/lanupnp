package com.jredfox.upnp;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigPortforward {
	
	public static boolean openToInternet;
	public static boolean randomPorts;
	
	public static void load(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		cfg.load();
		openToInternet = cfg.get("general", "openToInternet", true).getBoolean();
		randomPorts = cfg.get("general", "randomPorts", false).getBoolean();//client gui only
		cfg.save();
	}

}

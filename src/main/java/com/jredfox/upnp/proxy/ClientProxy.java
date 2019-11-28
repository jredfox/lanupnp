package com.jredfox.upnp.proxy;

import com.jredfox.upnp.PortMappings;

public class ClientProxy extends ServerProxy{
	
	@Override
	public void serverStopping()
	{
		PortMappings.closePorts();
	}

}

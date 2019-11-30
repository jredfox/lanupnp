package com.jredfox.upnp.command;

import com.jredfox.upnp.PortForwardMod;
import com.jredfox.upnp.PortMappings;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;

public class CmdCloseLan extends CommandBase{

	@Override
	public String getCommandName() 
	{
		return "closeLan";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		return "/" + this.getCommandName();
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException 
	{
		PortMappings.closeLan((IntegratedServer)PortForwardMod.proxy.getServer());
	}
	
	@Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
		if(!(sender instanceof EntityPlayerMP))
			return false;
		EntityPlayerMP player = (EntityPlayerMP)sender;
		return PortMappings.isPlayerOwner(player) || super.canCommandSenderUseCommand(sender);
	}

}

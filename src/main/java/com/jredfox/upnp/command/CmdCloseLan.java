package com.jredfox.upnp.command;

import com.jredfox.upnp.PortforwardMod;
import com.jredfox.upnp.PortMappings;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

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
		PortMappings.closeLan((IntegratedServer)PortforwardMod.proxy.getServer());
		sender.addChatMessage(new ChatComponentTranslation("commands.closelan.name"));
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

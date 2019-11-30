package com.jredfox.upnp.client.gui;

import java.io.IOException;

import com.jredfox.upnp.ConfigPortforward;
import com.jredfox.upnp.PortforwardMod;
import com.jredfox.upnp.PortMappings;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings.GameType;

public class GuiNet extends GuiShareToLan {
   public GuiTextField txtPort;
   public int port;
   public boolean openToNet = ConfigPortforward.openToInternet;

   public GuiNet(GuiScreen parent) 
   {
      super(parent);
   }
   
   @Override
   public void initGui() 
   {
      super.initGui();
      this.txtPort = new GuiTextField(this.fontRendererObj, this.width / 2 - 155, 150, 150, 20);
      this.txtPort.setFocused(true);
      this.txtPort.setText("25565");
      this.buttonList.add(new GuiButton(105, this.width / 2 + 5, 150, 150, 20, this.openToNet ? "Open To Net" : "Open To Lan"));
      port = 25565;
   }
   
   @Override
   public void updateScreen()
   {
      this.txtPort.updateCursorCounter();
   }
   
   @Override
   public void drawScreen(int par1, int par2, float par3)
   {
      super.drawScreen(par1, par2, par3);
      this.txtPort.drawTextBox();
      this.fontRendererObj.drawString("Open on port: (10000 - 65535)", this.width / 2 - 155, 135, 16777215, false);
   }
   
   @Override
   protected void keyTyped(char par1, int par2) 
   {
      if(this.txtPort.isFocused()) 
      {
         this.txtPort.textboxKeyTyped(par1, par2);
         int port = 0;

         try 
         {
            port = Integer.valueOf(this.txtPort.getText()).intValue();
         } 
         catch (NumberFormatException var5) 
         {
        	 
         }
         this.port = port;
      }

      if(par2 == 28 || par2 == 156) 
      {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }
      
      ((GuiButton)this.buttonList.get(0)).enabled = port >= 10000 && port <= '\uffff';
      super.keyTyped(par1, par2);
   }
   
   @Override
   protected void actionPerformed(GuiButton button)
   {
	   if(button.id == 101)
	   {
           this.mc.displayGuiScreen((GuiScreen)null);
           boolean opened = PortMappings.openLan(this.port, GameType.getByName(getGameMode()), getCheats());
           IChatComponent msg = opened ? new ChatComponentTranslation("commands.publish.started", new Object[] {"" + PortMappings.getPublicIp() + ":" + this.port}) : new ChatComponentTranslation("commands.publish.failed", new Object[]{"" + this.port});
           System.out.println(msg.getUnformattedText());
           this.mc.ingameGUI.getChatGUI().printChatMessage(msg);
           if(opened && this.openToNet)
           {
        	   PortMappings.addScheduledMapping(this.port);
           }
	   }
	   else if(button.id == 105)
	   {
		   this.openToNet = !this.openToNet;
		   button.displayString = this.openToNet ? "Open To Net" : "Open To Lan";
	   }
	   else
	   {
		   super.actionPerformed(button);
	   }
   }
   
   public boolean getCheats()
   {
	   return this.field_146600_i;
   }

   public String getGameMode() 
   {
	   return this.field_146599_h;
   }

}

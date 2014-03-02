package net.ixios.advancedthaumaturgy.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigResearch;
import thaumcraft.common.lib.research.ResearchManager;
import net.ixios.advancedthaumaturgy.AdvThaum;
import net.ixios.advancedthaumaturgy.tileentities.TileNodeModifier.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class ATServerCommand implements ICommand
{
	
	@Override
	public int compareTo(Object arg0)
	{
		return 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] list)
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add("research");
		return options;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		return ((EntityPlayer)sender).capabilities.isCreativeMode || Utilities.isOp(sender.getCommandSenderName());
	}

	@Override
	public List getCommandAliases()
	{
		return null;
	}

	@Override
	public String getCommandName()
	{
		return "at";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/at <option> <parameters>";
	}

	@Override
	public boolean isUsernameIndex(String[] arg0, int arg1)
	{
		return false;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params)
	{
		if (!(sender instanceof EntityPlayer))
		{
			sender.sendChatToPlayer(new ChatMessageComponent().addText("This command is only available to players."));
			return;
		}
		
		EntityPlayer player = (EntityPlayer)sender;
		
		String cmd = params[0].toLowerCase();
		
		if (cmd.equals("debug"))
		{
			AdvThaum.debug = !AdvThaum.debug;
			player.addChatMessage("Debug mode is now: " + AdvThaum.debug);
		}
		else if (cmd.equals("test"))
		{
			for (String key : ConfigResearch.recipes.keySet())
				if (key.startsWith("WAND_"))
					player.addChatMessage(key);
		        
		}
		else if (cmd.equals("research"))
		{
			if (params.length < 2)
			{
				showHelp(player);
				return;
			}

			String option = params[1].toLowerCase();
			String which = params[2].toLowerCase();
			
			if (option.equals("add"))
			{
				for (String categorykey : ResearchCategories.researchCategories.keySet())
                {
                    ResearchCategoryList cat = ResearchCategories.researchCategories.get(categorykey);
                    player.addChatMessage("Searching: " + categorykey);
                    
                    for (ResearchItem ri : cat.research.values())
                    {       
                    	if (!ri.key.equalsIgnoreCase(which))
                    		continue;
                    	
	                    if(!ResearchManager.isResearchComplete(player.username, ri.key))
	                    {
	                        Thaumcraft.proxy.getResearchManager().completeResearch(player, ri.key);
	                        player.addChatMessage("Added research: " + ri.getName());
	                    }
	                    else
	                        player.addChatMessage("Research already complete: " + ri.key);
                    }
                }
	                
			}
			else if (option.equals("remove"))
			{
				ArrayList<String> list = (ArrayList<String>) ResearchManager.getResearchForPlayer(player.username);
				for (Iterator<String>it = list.iterator(); it.hasNext();)
				{
					String research = (String)it.next();
					
					if (research.equalsIgnoreCase(option))
					{
						player.addChatMessage("Removing research: " + research);
						it.remove();
					}
				}
				player.addChatMessage("Research removal complete.");
			}
			else
			{
				showHelp(player);
			}
		}
	}

	private static void showHelp(EntityPlayer plr)
	{
		plr.addChatMessage("Usage:  /at <command> <option> <parameters>");
		plr.addChatMessage("     :  /at research add|remove ResearchKey");
	}
}

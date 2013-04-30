package cbp.double0negative.xServer.client;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import cbp.double0negative.xServer.XServer;
import cbp.double0negative.xServer.util.LogManager;

public class ChatListener implements Listener
{

	Client c;
	LogManager log;
	XServer plugin;
	
	public void setClient(Client c, XServer instance)
	{
		plugin = instance;
		this.c = c;
	}

   @EventHandler(priority=EventPriority.NORMAL)
   public void onPlayerChat(AsyncPlayerChatEvent chat)
   {
     Player sender = chat.getPlayer();
     if (chat.isCancelled())
     {
       return;
     }
     if (XServer.userAttached.containsKey(chat.getPlayer()))
     {
       String to = (String)XServer.userAttached.get(sender);
       String msg = "/fsay " + to + " " + chat.getMessage();
       sender.chat(msg);
       chat.setCancelled(true);
     }
     else if (XServer.overRide == 1)
     {
       XServer.overRide = 0;
     }
     else
     {
    	 Player p = chat.getPlayer();
    	 String message = chat.getMessage();
    	 if (XServer.pluginEnabled.containsKey(p))
    	 {
    		if ((XServer.checkPerm(p, "xserver.ac.chat")) || (p.isOp()))
			{
				c.sendMessage(message, p.getDisplayName());
				chat.setCancelled(true);
				Player[] players = Bukkit.getOnlinePlayers();
				for (Player op : players)
				{
					if ((XServer.checkPerm(op, "xserver.ac.chat")) || (XServer.checkPerm(op, "xserver.ac.see")) || (op.isOp()))
					{
						op.sendMessage(ChatColor.AQUA+"[Local]"+p.getDisplayName()+": "+message);
					}
				}
			}
			else
			{
				p.sendMessage(ChatColor.RED+"You don't have permission to do that!");
			}
    	 }
     }
   }
 }

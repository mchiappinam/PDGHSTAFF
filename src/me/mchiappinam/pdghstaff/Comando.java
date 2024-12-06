/**
 * Copyright PDGH Minecraft Servers & HostLoad © 2013-XXXX
 * Todos os direitos reservados
 * Uso apenas para a PDGH.com.br e https://HostLoad.com.br
 * Caso você tenha acesso a esse sistema, você é privilegiado!
*/

package me.mchiappinam.pdghstaff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Comando implements CommandExecutor {
	private Main plugin;

	public Comando(Main main) {
		plugin = main;
	}
	
  	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("staff")) {
			if(!sender.hasPermission("pdgh.coordenador")) {
				sender.sendMessage("§cSem permissões");
				return true;
			}
			if(args.length==0) {
				if(sender==plugin.getServer().getConsoleSender()) {
					plugin.helpConsole(plugin.getServer().getConsoleSender());
					return true;
				}
				plugin.help((Player)sender);
				return true;
        	}
			if(args[0].equalsIgnoreCase("promote")) {
				if(!sender.hasPermission("pdgh.op")) {
					sender.sendMessage("§4Sem permissões");
					return true;
				}
				if((args.length==1)||(args.length==2)) {
					if(sender==plugin.getServer().getConsoleSender()) {
						plugin.helpConsole(plugin.getServer().getConsoleSender());
						return true;
					}
					plugin.help((Player)sender);
					return true;
	        	}
				if(args[2].equalsIgnoreCase("youtuber")) {
					if(args.length>=4) {
						if(sender==plugin.getServer().getConsoleSender()) {				//YouTuber
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					plugin.demoteAll(args[1]);
					plugin.youtuber(args[1]);
					return true;
				}else if(args[2].equalsIgnoreCase("construcao")) {				//Construção
					if(args.length>=4) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					plugin.demoteAll(args[1]);
					plugin.construcao(args[1]);
					return true;
				}else if(args[2].equalsIgnoreCase("coordenacao")) {				//Coordenação
					if(args.length>=4) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					plugin.demoteAll(args[1]);
					plugin.coordenacao(args[1]);
					return true;
				}else if(args[2].equalsIgnoreCase("moderacao")) {				//Moderação
					if(args.length>=4) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					plugin.demoteAll(args[1]);
					plugin.moderacao(args[1]);
					return true;
				}else if(args[2].equalsIgnoreCase("administracao")) {				//Administração
					if(args.length>=4) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					plugin.demoteAll(args[1]);
					plugin.administracao(args[1]);
					return true;
				}else if(args[2].equalsIgnoreCase("subdirecao")) {				//Sub-Direção
					if(args.length>=4) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					plugin.demoteAll(args[1]);
					plugin.subdirecao(args[1]);
					return true;
					}else if(args[2].equalsIgnoreCase("direcao")) {				//Direção
					if(args.length>=4) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					plugin.demoteAll(args[1]);
					plugin.direcao(args[1]);
					return true;
				}else if(args[2].equalsIgnoreCase("vicepresidencia")) {				//Vice-Presidência
					if(args.length>=4) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					plugin.demoteAll(args[1]);
					plugin.vicepresidencia(args[1]);
					return true;
				}else if(args[2].equalsIgnoreCase("presidencia")) {				//Presidência
					if(args.length>=4) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					plugin.demoteAll(args[1]);
					plugin.presidencia(args[1]);
					return true;
				}
			}else if(args[0].equalsIgnoreCase("demote")) {
				if(!sender.hasPermission("pdgh.op")) {
					sender.sendMessage("§4Sem permissões");
					return true;
				}
					if((args.length>=3)||(args.length<=1)) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					plugin.demoteAll(args[1]);
					sender.sendMessage("§3[PDGHSTAFF] §2Sucesso ao demover.");
					return true;
			}else if(args[0].equalsIgnoreCase("bdemote")) {
				if(!sender.hasPermission("pdgh.op")) {
					sender.sendMessage("§4Sem permissões");
					return true;
				}
				if((args.length>=3)||(args.length<=1)) {
					if(sender==plugin.getServer().getConsoleSender()) {
						plugin.helpConsole(plugin.getServer().getConsoleSender());
						return true;
					}
					plugin.help((Player)sender);
					return true;
	        	}
				plugin.demoteAll(args[1]);
				sender.sendMessage("§3[PDGHSTAFF] §2Sucesso ao demover.");
				
				plugin.getServer().getOfflinePlayer(args[1]).setBanned(true);
				
				if(plugin.getServer().getPlayerExact(args[1])!=null)
					plugin.getServer().getPlayerExact(args[1]).kickPlayer("Negada!");
				sender.sendMessage("§3[PDGHSTAFF] §2Sucesso ao banir 00.");
				return true;
			}else if(args[0].equalsIgnoreCase("brdemote")) {
				if(!sender.hasPermission("pdgh.op")) {
					sender.sendMessage("§4Sem permissões");
					return true;
				}
				if((args.length>=3)||(args.length<=1)) {
					if(sender==plugin.getServer().getConsoleSender()) {
						plugin.helpConsole(plugin.getServer().getConsoleSender());
						return true;
					}
					plugin.help((Player)sender);
					return true;
	        	}
				plugin.demoteAll(args[1]);
				sender.sendMessage("§3[PDGHSTAFF] §2Sucesso ao demover.");

				plugin.getServer().getOfflinePlayer(args[1]).setBanned(true);

				if(plugin.getServer().getPlayerExact(args[1])!=null)
					plugin.getServer().getPlayerExact(args[1]).kickPlayer("Negada!");
				
				sender.sendMessage("§3[PDGHSTAFF] §2Sucesso ao banir 00.");

				try {
					if(plugin.vault) {
						double quantia=0.0;
						quantia=Main.econ.getBalance(args[1]);
						Main.econ.withdrawPlayer(args[1], quantia);
						sender.sendMessage("§3[PDGHSTAFF] §c$"+quantia+" §2removidos.");
					}
					for(String cmd1 : plugin.getConfig().getStringList("comandos.STAFFBRDemote"))
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd1);
				}catch(Exception ex) {}
				return true;
			}/**else if(args[0].equalsIgnoreCase("v")) {
				if(!sender.hasPermission("pdgh.admin")) {
					sender.sendMessage("§4Sem permissões");
					return true;
				}
					if((args.length>=3)||(args.length<=1)) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					//plugin.demoteAll(args[1]);
					sender.sendMessage("§3[PDGHSTAFF] §2Jogador invisível com sucesso.");
					sender.sendMessage("§3[PDGHSTAFF] §2Lista de atividades:");
					sender.sendMessage("§3[PDGHSTAFF] §a- Permissão de Invisibilidade (/v) concedida.");
					sender.sendMessage("§3[PDGHSTAFF] §a- Permissão de SoftBan (/sban) concedida.");
					sender.sendMessage("§3[PDGHSTAFF] §a- Permissão de God (/god) concedida.");
					sender.sendMessage("§3[PDGHSTAFF] §a- Permissão de Fly (/fly) concedida.");
					sender.sendMessage("§3[PDGHSTAFF] §a- Permissão de teleportar (/tp <nick>) concedida.");
					sender.sendMessage("§3[PDGHSTAFF] §2Nota: Ao deslogar e/ou digitar o comando novamente, o jogador perderá essas permissões.");
					sender.sendMessage("§3[PDGHSTAFF] §2Nota: Esses comandos são enviados automaticamente para a central.");
					return true;
			}*/else if(args[0].equalsIgnoreCase("crash")) {
				if(!sender.hasPermission("pdgh.admin")) {
					sender.sendMessage("§4Sem permissões");
					return true;
				}
					if((args.length>=3)||(args.length<=1)) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					if(plugin.getServer().getPlayer(args[1])==null) {
						sender.sendMessage("§3[PDGHSTAFF] §cJogador offline!");
						return true;
					}
					Inventory inv = plugin.getServer().createInventory(plugin.getServer().getPlayer(args[1]), 666);
					plugin.getServer().getPlayer(args[1]).openInventory(inv);
					sender.sendMessage("§3[PDGHSTAFF] §cThe gay(§d"+plugin.getServer().getPlayer(args[1]).getName()+"§c) has been crashed :p");
					return true;
			}else if(args[0].equalsIgnoreCase("msg")) {
				if(sender==plugin.getServer().getConsoleSender()) {
	            	if(args.length < 2) {
	            		sender.sendMessage("§cUse /staff msg <mensagem>");
	  					return true;
	            	}
	                StringBuilder sb = new StringBuilder();
	                sb.append(args[1]);
	                for (int i = 2; i < args.length; i++) {
	                  sb.append(" ");
	                  sb.append(args[i]);
	                }
		  			for(Player pp : plugin.getServer().getOnlinePlayers()) {
			  			if(pp.hasPermission("pdgh.moderador")) {
			  				pp.sendMessage(("§3[PDGHSTAFF] §a"+sb).replaceAll("&", "§"));
			  			}
		  			}
  					return true;
				}
		        final Player p = (Player) sender;
  				if(!p.hasPermission("pdgh.moderador")) {
  					p.sendMessage("§cSem permissões");
  					return true;
  				}
            	if(args.length < 2) {
            		sender.sendMessage("§cUse /staff msg <mensagem>");
  					return true;
            	}
                StringBuilder sb = new StringBuilder();
                sb.append(args[1]);
                for (int i = 2; i < args.length; i++) {
                  sb.append(" ");
                  sb.append(args[i]);
                }
	  			for(Player pp : plugin.getServer().getOnlinePlayers()) {
		  			if(pp.hasPermission("pdgh.moderador")) {
		  				pp.sendMessage(("§3["+p.getName()+"] §a"+sb).replaceAll("&", "§"));
		  			}
	  			}
				return true;
			}else if(args[0].equalsIgnoreCase("mod")) {
				if(args.length>=2) {
					if(sender==plugin.getServer().getConsoleSender()) {
						plugin.helpConsole(plugin.getServer().getConsoleSender());
						return true;
					}
					plugin.help((Player)sender);
					return true;
	        	}
				plugin.mod((Player)sender);
				return true;
			}else if(args[0].equalsIgnoreCase("admin")) {
				if(args.length>=2) {
					if(sender==plugin.getServer().getConsoleSender()) {
						plugin.helpConsole(plugin.getServer().getConsoleSender());
						return true;
					}
					plugin.help((Player)sender);
					return true;
	        	}
				plugin.admin((Player)sender);
				return true;
			}else if(args[0].equalsIgnoreCase("att")) {
				if(!sender.hasPermission("pdgh.coordenador")) {
					sender.sendMessage("§cSem permissões");
					return true;
				}
				if(args.length>=2) {
					if(sender==plugin.getServer().getConsoleSender()) {
						plugin.helpConsole(plugin.getServer().getConsoleSender());
						return true;
					}
					plugin.help((Player)sender);
					return true;
	        	}
				if(plugin.check.contains(sender.getName().toLowerCase())) {
					sender.sendMessage("§3[PDGHSTAFF] §cVocê já atualizou suas permissões!");
					return true;
				}
				if((sender.hasPermission("pdgh.youtuber"))&&(!sender.hasPermission("pdgh.construtor"))&&(!sender.hasPermission("pdgh.coordenador"))) {
					plugin.demoteAll(sender.getName());
					plugin.youtuber(sender.getName());
	    		}else if((sender.hasPermission("pdgh.construtor"))&&(!sender.hasPermission("pdgh.youtuber"))&&(!sender.hasPermission("pdgh.coordenador"))) {
	    			plugin.demoteAll(sender.getName());
					plugin.construcao(sender.getName());
	    		}else if((sender.hasPermission("pdgh.coordenador"))&&(!sender.hasPermission("pdgh.moderador"))) {
	    			plugin.demoteAll(sender.getName());
					plugin.coordenacao(sender.getName());
	    		}else if((sender.hasPermission("pdgh.moderador"))&&(!sender.hasPermission("pdgh.admin"))) {
	    			plugin.demoteAll(sender.getName());
					plugin.moderacao(sender.getName());
	    		}else if((sender.hasPermission("pdgh.admin"))&&(!sender.hasPermission("pdgh.subdiretor"))) {
	    			plugin.demoteAll(sender.getName());
					plugin.administracao(sender.getName());
	    		}else if((sender.hasPermission("pdgh.subdiretor"))&&(!sender.hasPermission("pdgh.diretor"))) {
	    			plugin.demoteAll(sender.getName());
					plugin.subdirecao(sender.getName());
	    		}else if((sender.hasPermission("pdgh.diretor"))&&(!sender.hasPermission("pdgh.vicepresidente"))) {
	    			plugin.demoteAll(sender.getName());
					plugin.direcao(sender.getName());
	    		}else if((sender.hasPermission("pdgh.vicepresidente"))&&(!sender.hasPermission("pdgh.presidente"))) {
	    			plugin.demoteAll(sender.getName());
					plugin.vicepresidencia(sender.getName());
	    		}else if(sender.hasPermission("pdgh.presidente")) {
	    			plugin.demoteAll(sender.getName());
					plugin.presidencia(sender.getName());
	    		}
				plugin.check.add(sender.getName().toLowerCase());
				sender.sendMessage("§3[PDGHSTAFF] §2Permissões atualizadas.");
				return true;
			}
			if(args.length>=0) {
				if(sender==plugin.getServer().getConsoleSender()) {
					plugin.helpConsole(plugin.getServer().getConsoleSender());
					return true;
				}
				plugin.help((Player)sender);
				return true;
        	}
			return true;
        }
        if(cmd.getName().equalsIgnoreCase("kill")) {
        	if(args.length>0) {
        		sender.sendMessage("§cUse /kill");
        		return true;
        	}
        	((Player)sender).setHealth(0);
        	sender.sendMessage(" ");
        	sender.sendMessage("§cVocê arregou!");
        	sender.sendMessage(" ");
        	return true;
        }
        if(cmd.getName().equalsIgnoreCase("ativarwhitelist")) {
        	if(sender!=plugin.getServer().getConsoleSender()) {
        		sender.sendMessage("§cSem permissões");
        		return true;
        	}
    		plugin.kickAll=true;
    		plugin.bloqueado=true;
	    	plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "plugin unload PDGHAutoShutdown");
        	return true;
        }
        if(cmd.getName().equalsIgnoreCase("desativarwhitelist")) {
        	if(sender!=plugin.getServer().getConsoleSender()) {
        		sender.sendMessage("§cSem permissões");
        		return true;
        	}
    		plugin.bloqueado=false;
	    	plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "plugin reload PDGHAutoShutdown");
    		plugin.getServer().broadcastMessage("§3§l[STAFF] §aWhitelist desativada!");
        	return true;
        }
		return false;
    }
  	
}
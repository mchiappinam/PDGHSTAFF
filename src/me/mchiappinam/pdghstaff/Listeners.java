/**
 * Copyright PDGH Minecraft Servers & HostLoad © 2013-XXXX
 * Todos os direitos reservados
 * Uso apenas para a PDGH.com.br e https://HostLoad.com.br
 * Caso você tenha acesso a esse sistema, você é privilegiado!
*/

package me.mchiappinam.pdghstaff;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Listeners implements Listener {
	
	private Main plugin;
	public Listeners(Main main) {
		plugin=main;
	}

	/**@EventHandler
	public void antiDupe(PlayerDropItemEvent e) {
		if((e.getPlayer().getInventory().getType()==InventoryType.PLAYER)&&(e.getItemDrop().getItemStack().getAmount()==e.getItemDrop().getItemStack().getMaxStackSize()))
			e.setCancelled(true);
	}*/
/**
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(plugin.checkhitplayer.contains(((Player)e.getWhoClicked()).getName().toLowerCase())) {
	    	e.setCancelled(true);
	    	((Player)e.getWhoClicked()).closeInventory();
	    	((Player)e.getWhoClicked()).sendMessage("§f§l[PDGHSTAFF] §cVocê tem que desativar seu sistema de verificar se o jogador te hita.");
		}
		if(((Player)e.getWhoClicked()).hasPermission("pdgh.admin"))
		    if (e.getInventory().getName().equals(Main.menu.getName())) {
		    	e.setCancelled(true);
		    	if ((e.getCurrentItem() != null)&&(!e.getCurrentItem().getType().equals(Material.AIR))) {
			    	if (e.getCurrentItem().getType() == Material.DIAMOND_BLOCK) { //Creativo
				    	((Player)e.getWhoClicked()).closeInventory();
				    	plugin.sendCreativo(((Player)e.getWhoClicked()));
					    return;
				    }else if (e.getCurrentItem().getType() == Material.IRON_AXE) { //DayZ2
				    	((Player)e.getWhoClicked()).closeInventory();
				    	plugin.sendDayZ2(((Player)e.getWhoClicked()));
					    return;
				    }else if (e.getCurrentItem().getType() == Material.DIAMOND_AXE) { //DayZ3
				    	((Player)e.getWhoClicked()).closeInventory();
				    	((Player)e.getWhoClicked()).sendMessage(" ");
				    	((Player)e.getWhoClicked()).sendMessage("§b§lServidor apenas para Minecraft original!");
				    	((Player)e.getWhoClicked()).sendMessage("§b§lIP do servidor: §a§ldayz3.pdgh.com.br");
				    	((Player)e.getWhoClicked()).sendMessage(" ");
					    return;
				    }else if (e.getCurrentItem().getType() == Material.FIRE) { //DayZHardcore
				    	((Player)e.getWhoClicked()).closeInventory();
				    	plugin.sendDayZHardcore(((Player)e.getWhoClicked()));
					    return;
				    }else if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) { //Full PvP
				    	((Player)e.getWhoClicked()).closeInventory();
				    	plugin.sendFullPvP(((Player)e.getWhoClicked()));
					    return;
				    }else if (e.getCurrentItem().getType() == Material.MUSHROOM_SOUP) { //Hunger Games1
				    	((Player)e.getWhoClicked()).closeInventory();
				    	plugin.sendHungerGames1(((Player)e.getWhoClicked()));
					    return;
				    }else if (e.getCurrentItem().getType() == Material.BOWL) { //Hunger Games2
				    	((Player)e.getWhoClicked()).closeInventory();
				    	plugin.sendHungerGames2(((Player)e.getWhoClicked()));
					    return;
				    }else if (e.getCurrentItem().getType() == Material.DIAMOND_PICKAXE) { //PvP
				    	((Player)e.getWhoClicked()).closeInventory();
				    	plugin.sendPvP(((Player)e.getWhoClicked()));
					    return;
				    }
		    	}
			    return;
		    }
	}*/
    
    @EventHandler
    private void onPick(PlayerPickupItemEvent e) {
		if(plugin.checkhitplayer.contains(e.getPlayer().getName().toLowerCase())) {
	    	e.setCancelled(true);
	    	e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §cVocê tem que desativar seu sistema de verificar se o jogador te hita.");
		}
		if((e.getItem().getItemStack().getType()==Material.COMPASS)||(e.getItem().getItemStack().getType()==Material.STICK)||(e.getItem().getItemStack().getType()==Material.NETHER_STAR)||(e.getItem().getItemStack().getType()==Material.GLOWSTONE_DUST)||(e.getItem().getItemStack().getType()==Material.CLAY_BRICK)||(e.getItem().getItemStack().getType()==Material.WATCH)||(e.getItem().getItemStack().getType()==Material.SLIME_BALL)) {
	    	if((!e.getPlayer().hasPermission("pdgh.admin"))&&(e.getItem().getItemStack().getItemMeta().hasDisplayName())) {
    			e.setCancelled(true);
    			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §cVocê não pode pegar esse item.");
    			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §cO item foi removido.");
    			e.getItem().remove();
    		}
    	}
    }

	@EventHandler
	private void onDeath(PlayerDeathEvent e) {
		if((e.getEntity().getPlayer().hasPermission("pdgh.admin"))&&(!plugin.getConfig().getBoolean("STAFFDropItensOnDeath")))
			e.getDrops().clear();
	}
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
    	if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
	    	if((((Player)e.getEntity()).hasPermission("pdgh.admin"))&&(plugin.checkhitplayer.contains(((Player)e.getEntity()).getName().toLowerCase()))) {
	    		e.setCancelled(true);
    			plugin.checkhitplayer.remove(((Player)e.getEntity()).getName().toLowerCase());
    			((Player)e.getEntity()).sendMessage("§f§l[PDGHSTAFF] §a"+((Player)e.getDamager()).getName()+" §cte hitou.");
	    		((Player)e.getEntity()).setHealth(20);
		    	/**((Player)e.getEntity()).getInventory().setHelmet(null);
		    	((Player)e.getEntity()).getInventory().setChestplate(null);
		    	((Player)e.getEntity()).getInventory().setLeggings(null);
		    	((Player)e.getEntity()).getInventory().setBoots(null);*/
    			plugin.getServer().dispatchCommand(((Player)e.getEntity()), "god");
    			plugin.getServer().dispatchCommand(((Player)e.getEntity()), "von");
    			((Player)e.getEntity()).sendMessage("§f§l[PDGHSTAFF] §a"+((Player)e.getDamager()).getName()+" §cte hitou.");
    			((Player)e.getEntity()).sendMessage("§f§l[PDGHSTAFF] §aGod & invisibilidade ON. Info de hit OFF.");
	    	}
	    }
    }
    
    @EventHandler(priority=EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e) {
    	/**if((e.getPlayer().getItemInHand().getType()==Material.NETHER_STAR)&&(e.getAction().equals(Action.LEFT_CLICK_AIR)||e.getAction().equals(Action.LEFT_CLICK_BLOCK)||e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) 
    		if(e.getPlayer().hasPermission("pdgh.admin")) {
        		e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §cVocê deve clicar em algum jogador.");
        		return;
    		}*/
    	/**if((e.getPlayer().getItemInHand().getType()==Material.WATCH)&&(e.getAction().equals(Action.LEFT_CLICK_AIR)||e.getAction().equals(Action.LEFT_CLICK_BLOCK)||e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if(e.getPlayer().hasPermission("pdgh.admin")) {
                e.setCancelled(true);
                plugin.tags();
                e.getPlayer().openInventory(Main.menu);
                return;
            }
    	}*/
    	if((e.getPlayer().getItemInHand().getType()==Material.GLOWSTONE_DUST)&&(e.getAction().equals(Action.LEFT_CLICK_AIR)||e.getAction().equals(Action.LEFT_CLICK_BLOCK)||e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if(e.getPlayer().hasPermission("pdgh.admin")) {
                e.setCancelled(true);
                if(plugin.checkhitplayer.contains(e.getPlayer().getName().toLowerCase())) {
        			plugin.checkhitplayer.remove(e.getPlayer().getName().toLowerCase());
        			e.getPlayer().setHealth(20);
        			/**e.getPlayer().getInventory().setHelmet(null);
    		    	e.getPlayer().getInventory().setChestplate(null);
    		    	e.getPlayer().getInventory().setLeggings(null);
    		    	e.getPlayer().getInventory().setBoots(null);*/
					for(String cmd1 : plugin.getConfig().getStringList("infoDeHit.off"))
						plugin.getServer().dispatchCommand(e.getPlayer(), cmd1);
        			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §aGod & invisibilidade ON. Info de hit §f§lOFF§a.");
                    return;
                }else if(!plugin.checkhitplayer.contains(e.getPlayer().getName().toLowerCase())) {
        			plugin.checkhitplayer.add(e.getPlayer().getName().toLowerCase());
        			e.getPlayer().setHealth(20);
        			/**ItemStack elmo = new ItemStack(Material.DIAMOND_HELMET, 1);
    				ItemStack peito = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
    				ItemStack calca = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
    		    	ItemStack bota = new ItemStack(Material.DIAMOND_BOOTS, 1);
    		    	e.getPlayer().getInventory().setHelmet(elmo);
    		    	e.getPlayer().getInventory().setChestplate(peito);
    		    	e.getPlayer().getInventory().setLeggings(calca);
    		    	e.getPlayer().getInventory().setBoots(bota);*/
					for(String cmd1 : plugin.getConfig().getStringList("infoDeHit.on"))
						plugin.getServer().dispatchCommand(e.getPlayer(), cmd1);
        			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §cGod & invisibilidade OFF. Info de hit §f§lON§c.");
                }
                return;
            }
    	}

    	if((e.getPlayer().getItemInHand().getType()==Material.SLIME_BALL)&&(e.getAction().equals(Action.LEFT_CLICK_AIR)||e.getAction().equals(Action.LEFT_CLICK_BLOCK)||e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if(e.getPlayer().hasPermission("pdgh.admin")) {
                e.setCancelled(true);
				plugin.getServer().dispatchCommand(e.getPlayer(), plugin.getConfig().getString("comandos.spawn"));
			}
    	}
    	
    	if(e.getPlayer().getItemInHand().getType()==Material.CLAY_BRICK)
    		if(e.getPlayer().hasPermission("pdgh.admin")) {
    			e.setCancelled(true);
    			for(String mundo : plugin.getConfig().getStringList("removedorDeBlocos.mundos")) {
    			    if((e.getPlayer().getWorld()==plugin.getServer().getWorld(mundo))) {
			    		if(e.getAction() == Action.RIGHT_CLICK_AIR) {
			    			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §cVocê deve clicar em algum bloco.");
			    			return;
			    		}
			    		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
		                    for(Integer blocos : plugin.getConfig().getIntegerList("removedorDeBlocos.blocos")) {
					    		if((e.getClickedBlock().getType() == Material.getMaterial(blocos))&&((plugin.getConfig().getBoolean("removedorDeBlocos.remocaoInteligenteJogadorInativo.ativado"))&&((blocos!=68)||((blocos!=63))))) {
					    			e.getClickedBlock().setType(Material.AIR);
					    			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §2Bloco removido com sucesso.");
					    			break;
					    		}else if((e.getClickedBlock().getType() == Material.getMaterial(blocos))&&((blocos==68)||(blocos==63))&&(plugin.getConfig().getBoolean("removedorDeBlocos.remocaoInteligenteJogadorInativo.ativado"))) {
					    			Sign sign = (Sign)e.getClickedBlock().getState();
					    			//String[] lines = sign.getLines();
					    			//String line1 = sign.getLine(0);
					    			String line2 = sign.getLine(plugin.getConfig().getInt("removedorDeBlocos.remocaoInteligenteJogadorInativo.linha"));
					    			try {
						  				if (plugin.version == 2) {
						  					if(plugin.getClanPlayerManager().getClanPlayer(line2)==null) {
								    			e.getClickedBlock().setType(Material.AIR);
								    			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §2Bloco removido com sucesso - "+plugin.getClanPlayerManager().getClanPlayer(line2));
								    			break;
						  					}
						  					if(plugin.getClanPlayerManager().getClanPlayer(line2).getInactiveDays()>=plugin.getConfig().getInt("removedorDeBlocos.remocaoInteligenteJogadorInativo.dias")) {
								    			e.getClickedBlock().setType(Material.AIR);
							  					e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §2JOGADOR EXISTENTE (Clan) - "+plugin.getClanPlayerManager().getClanPlayer(line2));
								    			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §2Bloco removido com sucesso - "+plugin.getClanPlayerManager().getClanPlayer(line2)+" com "+plugin.getClanPlayerManager().getClanPlayer(line2).getInactiveDays()+" dias inativos.");
								    			break;
						  					}
						  					e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §2JOGADOR EXISTENTE (Clan) - "+plugin.getClanPlayerManager().getClanPlayer(line2));
						  					break;
						  				}else{
						  					if(plugin.core2.getClanManager().getClanPlayer(line2)==null) {
								    			e.getClickedBlock().setType(Material.AIR);
								    			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §2Bloco removido com sucesso - "+plugin.core2.getClanManager().getClanPlayer(line2));
								    			break;
						  					}
						  					if(plugin.core2.getClanManager().getClanPlayer(line2).getInactiveDays()>=plugin.getConfig().getInt("removedorDeBlocos.remocaoInteligenteJogadorInativo.dias")) {
								    			e.getClickedBlock().setType(Material.AIR);
							  					e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §2JOGADOR EXISTENTE (Clan) - "+plugin.core2.getClanManager().getClanPlayer(line2));
								    			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §2Bloco removido com sucesso - "+plugin.core2.getClanManager().getClanPlayer(line2)+" com "+plugin.core2.getClanManager().getClanPlayer(line2).getInactiveDays()+" dias inativos.");
									    		break;
						  					}
						  					e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §2JOGADOR EXISTENTE (Clan) - "+plugin.core2.getClanManager().getClanPlayer(line2));
								    		break;
						  				}
					    			}catch (Exception ex) {
						    			e.getClickedBlock().setType(Material.AIR);
						    			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §2Bloco removido com sucesso. (catch exception)");
							    		break;
						  			}	
					    		}
		                    }
			    			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §cBloco não permitido.");
				    		return;
			    		}
			    		break;
    				}
    			}
    		}
    }
    
    @EventHandler
    public void onClickPlayer(PlayerInteractEntityEvent e) {
    	if(e.getPlayer().getItemInHand().getType()==Material.NETHER_STAR)
    		if(e.getPlayer().hasPermission("pdgh.admin")) {
    			if(e.getRightClicked() instanceof Player) {
        			plugin.getServer().dispatchCommand(e.getPlayer(), "oi "+((Player)e.getRightClicked()).getName());
        		}else{
        			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §cVocê deve clicar em algum jogador.");
        		}
    		}

    	if(e.getPlayer().getItemInHand().getType()==Material.STICK)
    		if(e.getPlayer().hasPermission("pdgh.admin"))
    			if(e.getRightClicked() instanceof Player) {
        			((Player)e.getRightClicked()).setVelocity(((Player)e.getRightClicked()).getLocation().getDirection().multiply(-3));
        			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §a"+((Player)e.getRightClicked()).getName()+" recebeu knockback.");
        		}else{
        			e.getPlayer().sendMessage("§f§l[PDGHSTAFF] §cVocê deve clicar em algum jogador.");
        		}
    }
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onQuit(final PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onKick(final PlayerKickEvent e) {
		e.setLeaveMessage(null);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onJoin(final PlayerJoinEvent e) {
		e.setJoinMessage(null);
		if((plugin.bloqueado)&&(!e.getPlayer().hasPermission("pdgh.moderador")))
			e.getPlayer().kickPlayer("§3§l-- PDGH --\n\n§cO servidor está temporariamente fechado\n§fTente novamente dentro de 5 minutos\n\n§3§l-- PDGH --");
		/**if((e.getPlayer().getAddress().getAddress().getHostAddress().replaceAll("/", "").contains("201.13.83.106"))||
			(e.getPlayer().getAddress().getAddress().getHostAddress().replaceAll("/", "").contains("187.180.40.206"))||
			(e.getPlayer().getAddress().getAddress().getHostAddress().replaceAll("/", "").contains("177.53.5.145"))) {
			e.getPlayer().sendMessage("§c§lVocê é baitola que eu sei =)");
		}*/
		if(e.getPlayer().isOp()==true) {
			e.getPlayer().setOp(false);
			e.getPlayer().setBanned(true);
			e.getPlayer().kickPlayer("§cVocê é OP e foi banido!");
			plugin.getServer().broadcastMessage(" ");
			plugin.getServer().broadcastMessage("§f§l[PDGHSTAFF] §c"+e.getPlayer().getName()+" era OP e foi banido 00 (Sem volta de VIP)");
			plugin.getServer().broadcastMessage(" ");
			return;
		}else if((e.getPlayer().hasPermission("pdgh.youtuber"))&&(!e.getPlayer().hasPermission("pdgh.construtor"))&&(!e.getPlayer().hasPermission("pdgh.coordenador"))) {
			plugin.joinMessage(e.getPlayer(), "YouTuber");
		}else if((e.getPlayer().hasPermission("pdgh.construtor"))&&(!e.getPlayer().hasPermission("pdgh.youtuber"))&&(!e.getPlayer().hasPermission("pdgh.coordenador"))) {
			plugin.joinMessage(e.getPlayer(), "Construtor(a)");
		}else if((e.getPlayer().hasPermission("pdgh.coordenador"))&&(!e.getPlayer().hasPermission("pdgh.moderador"))) {
			plugin.joinMessage(e.getPlayer(), "Coordenador(a)");
		}else if((e.getPlayer().hasPermission("pdgh.moderador"))&&(!e.getPlayer().hasPermission("pdgh.admin"))) {
			plugin.joinMessage(e.getPlayer(), "Moderador(a)");
		}else if((e.getPlayer().hasPermission("pdgh.admin"))&&(!e.getPlayer().hasPermission("pdgh.subdiretor"))) {
			plugin.joinMessage(e.getPlayer(), "Administrador(a)");
		}else if((e.getPlayer().hasPermission("pdgh.subdiretor"))&&(!e.getPlayer().hasPermission("pdgh.diretor"))) {
			plugin.joinMessage(e.getPlayer(), "Sub-Diretor(a)");
		}else if((e.getPlayer().hasPermission("pdgh.diretor"))&&(!e.getPlayer().hasPermission("pdgh.vicepresidente"))) {
			plugin.joinMessage(e.getPlayer(), "Diretor(a)");
		}else if((e.getPlayer().hasPermission("pdgh.vicepresidente"))&&(!e.getPlayer().hasPermission("pdgh.presidente"))) {
			plugin.joinMessage(e.getPlayer(), "Vide-Presidênte(a)");
		}else if(e.getPlayer().hasPermission("pdgh.presidente")) {
			plugin.joinMessage(e.getPlayer(), "Presidente(a)");
		}
		    for(PotionEffect effect : e.getPlayer().getActivePotionEffects()) {
		    	if(effect.getAmplifier()>60) {
				    for(PotionEffect pot : e.getPlayer().getActivePotionEffects()) {
				    	e.getPlayer().removePotionEffect(pot.getType());
				    }
			        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "ban "+e.getPlayer().getName()+" 1200 Uso indevido de poções");
		    		e.getPlayer().getInventory().setHelmet(null);
		    		e.getPlayer().getInventory().setChestplate(null);
	  				e.getPlayer().getInventory().setLeggings(null);
	  				e.getPlayer().getInventory().setBoots(null);
	  				e.getPlayer().getInventory().clear();
	  				plugin.getServer().broadcastMessage(" ");
	  				plugin.getServer().broadcastMessage("§f§l[PDGHSTAFF] §c"+e.getPlayer().getName()+" estava com poções indevidas e foi banido por 1200 minutos.");
	  				plugin.getServer().broadcastMessage(" ");
	  				return;
		    	}
		    }
	}
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	private void onPCmd(PlayerCommandPreprocessEvent e) {
	    if((e.getMessage().toLowerCase().startsWith("/login"))||(e.getMessage().toLowerCase().startsWith("/logar"))) {
	    	if((e.getPlayer().hasPermission("pdgh.admin"))&&(!plugin.lobby)) {
	    		plugin.clearInv(e.getPlayer());
	    	}
	    }
	    for(String cmd : plugin.getConfig().getStringList("comandos.bloqueados"))
		    if(e.getMessage().toLowerCase().startsWith(cmd)) {
		    	e.setCancelled(true);
		    	if(!e.getPlayer().hasPermission("pdgh.admin")) {
		    		return;
		    	}
		    	e.getPlayer().sendMessage("§cSem permissões");
		    	return;
		    }
    }
	
	

    
    @EventHandler(priority=EventPriority.MONITOR)
    public void onInventoryOpenEvent(InventoryOpenEvent e) {
		Player p = (Player)e.getPlayer();
		if(p != null)
			if((e.getInventory().getType()==InventoryType.HOPPER)||(e.getInventory().getType()==InventoryType.BREWING)) {
	    		e.setCancelled(true);
	    		e.getPlayer().closeInventory();
	    		p.sendMessage("§3§l[PDGHSTAFF] §cVocê não pode abrir isso.");
			}
    }
    
    
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerConsume(final PlayerItemConsumeEvent e) {
    	try {
    	    if(e.getItem().getType().equals(Material.GOLDEN_APPLE))
    	    	if(e.getItem().getDurability()==0) {
    	    		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run(){
    	    	    		e.getPlayer().removePotionEffect(PotionEffectType.ABSORPTION);
                        }
                    }, 1L);
    	    	}
    	}catch(Exception ex) {}
    }
}
/**
 * Copyright PDGH Minecraft Servers & HostLoad © 2013-XXXX
 * Todos os direitos reservados
 * Uso apenas para a PDGH.com.br e https://HostLoad.com.br
 * Caso você tenha acesso a esse sistema, você é privilegiado!
*/
		
package me.mchiappinam.pdghstaff;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.p000ison.dev.simpleclans2.api.SCCore;
import com.p000ison.dev.simpleclans2.api.clanplayer.ClanPlayerManager;

import net.milkbowl.vault.economy.Economy;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Main extends JavaPlugin {
	//public static Inventory menu = Bukkit.createInventory(null, 45, "§e» §9§lServidores PDGH §e«");
	public String retorno = null;
	public boolean vault = false;
	public boolean permissionsEx = false;
	public List<String> check=new ArrayList<String>();
	public List<String> checkhitplayer=new ArrayList<String>();
	protected SCCore core;
	protected SimpleClans core2;
	protected int version = 0;
    public boolean bloqueado=false;
    public boolean kickAll=false;
    public boolean lobby=false;
    //public SendMailSSL mail = null;

	protected static Economy econ = null;


    protected boolean ativado=true;
    protected String key=null;
	protected int tentativa1 = 0;
	protected int tentativa2 = 0;
	protected int tentativa3 = 0;
	protected int tentativa4 = 0;
	protected int tentativa5 = 0;
	protected int tentativa6 = 0;
	
	int verificacao=0;

	public void onEnable() {
		primeiros15min();
		resetTentativaAfter1hour();
	    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2ativando... - Plugin by: mchiappinam");
	    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2verificando config... - Plugin by: mchiappinam");
		File file = new File(getDataFolder(),"config.yml");
		if(!file.exists()) {
			try {
			    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2salvando config pela primeira vez... - Plugin by: mchiappinam");
				saveResource("config_template.yml",false);
				File file2 = new File(getDataFolder(),"config_template.yml");
				file2.renameTo(new File(getDataFolder(),"config.yml"));
			    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2config salva... - Plugin by: mchiappinam");
			}catch(Exception e) {}
		}
		lobby=getConfig().getBoolean("lobby");
	    getServer().getPluginCommand("staff").setExecutor(new Comando(this));
	    getServer().getPluginCommand("kill").setExecutor(new Comando(this));
	    getServer().getPluginCommand("ativarwhitelist").setExecutor(new Comando(this));
	    getServer().getPluginCommand("desativarwhitelist").setExecutor(new Comando(this));
	    //getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    //mail = new SendMailSSL(this);
	    if(!setupEconomy()) {
	    	getLogger().warning("ERRO: Vault (Economia) nao encontrado!");
	    	vault = false;
	    }else{
	    	getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2Sucesso: Vault (Economia) encontrado.");
	    	vault = true;
	    }
	    permissionsEx=getConfig().getBoolean("permissionsEx");
	    if(getServer().getPluginManager().getPlugin("PermissionsEx") == null) {
	    	getLogger().warning("ERRO: PermissionsEx (Permissoes) nao encontrado!");
	    	getLogger().warning("Forçando o uso de comandos para add/remover as permissões...");
	    }else{
	    	//mail.main("PermissionsEx");
	    	getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2Sucesso: PermissionsEx (Permissoes) encontrado.");
	    }

		if (hookSimpleClans()) {
			getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2Sucesso: SimpleClans2 (Removedor de Itens) encontrado.");
			version = 2;
		}else if (getServer().getPluginManager().getPlugin("SimpleClans") != null) {
			getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2Sucesso: SimpleClans (Removedor de Itens) encontrado.");
			core2 = ((SimpleClans)getServer().getPluginManager().getPlugin("SimpleClans"));
			version = 1;
		}else{
			version = 0;
	    	getLogger().warning("ERRO: SimpleClans ou SimpleClans2 (Removedor de Itens) nao encontrado!");
		}
	    
	    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2Ligando scheduler checkOp... - Plugin by: mchiappinam");
	    checkOp();
	    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2Scheduler checkOp ligado - Plugin by: mchiappinam");

	    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2Ligando scheduler checkKickAll... - Plugin by: mchiappinam");
	    checkKickAll();
	    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2Scheduler checkKickAll ligado - Plugin by: mchiappinam");
	    forceCheck();
	    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2ativado - Plugin by: mchiappinam");
	    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2Acesse: http://pdgh.com.br/");
	}

	public void onDisable() {
	    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2desativado - Plugin by: mchiappinam");
	    getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF] §2Acesse: http://pdgh.com.br/");
	}
	
	private boolean setupEconomy() {
      if (getServer().getPluginManager().getPlugin("Vault") == null) {
          return false;
      }
      RegisteredServiceProvider<Economy> rsp=getServer().getServicesManager().getRegistration(Economy.class);
      if (rsp == null) {
          return false;
      }
      econ=rsp.getProvider();
      return econ != null;
	}

	private boolean hookSimpleClans() {
		try {
			for(Plugin plugin : getServer().getPluginManager().getPlugins()) {
				if ((plugin instanceof SCCore)) {
					core = ((SCCore)plugin);
					return true;
				}
			}
		}catch (NoClassDefFoundError e) {
			return false;
		}
		return false;
	}

	public ClanPlayerManager getClanPlayerManager() {
		return core.getClanPlayerManager();
	}
	
	private void checkKickAll() {
	  	getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	  		public void run() {
	  			if(kickAll) {
	  				kickAll=false;
	  	    		getServer().dispatchCommand(getServer().getConsoleSender(), "save-all");
	  	    		getServer().dispatchCommand(getServer().getConsoleSender(), "plugin reload SimpleNoRelog");
	  	    		getServer().dispatchCommand(getServer().getConsoleSender(), "plugin reload PDGHX1");
	  	    		getServer().dispatchCommand(getServer().getConsoleSender(), "plugin reload PDGHX1C");
	  	    		getServer().dispatchCommand(getServer().getConsoleSender(), "plugin reload PDGHCreativoArenas");
	  	    		getServer().dispatchCommand(getServer().getConsoleSender(), "plugin reload PDGHFullPvPArenas");
	  	    		getServer().dispatchCommand(getServer().getConsoleSender(), "plugin reload PDGHEventos");
	  	    		for(Player p : getServer().getOnlinePlayers())
	  	    			if(!p.hasPermission("pdgh.moderador"))
	  	    				p.kickPlayer("§3§l-- PDGH --\n\n§cO servidor está temporariamente fechado\n§fTente novamente dentro de 5 minutos\n\n§3§l-- PDGH --");
	  	    		getServer().broadcastMessage("§3§l[STAFF] §aWhitelist ativada!");
	  			}
	  		}
	  	}, 0, 5*20);
	}
	
	private void checkOp() {
	  	getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	  		public void run() {
	  			for(Player p : getServer().getOnlinePlayers()) {
	  				if(verificacao==9) {
		  				if(p.hasPermission("pdgh.coordenador")) {
		  					p.sendMessage("§3[PDGHSTAFF] §aIniciando verificação...");
		  				}
		  				verificacao=0;
	  				}else
	  					verificacao++;
		  			if(p.isOp()==true) {
		  				p.setOp(false);
		  				p.setBanned(true);
		  				p.kickPlayer("§cVocê é OP e foi banido!");
		  				getServer().broadcastMessage(" ");
		  				getServer().broadcastMessage("§f§l[PDGHSTAFF] §c"+p.getName()+" era OP e foi banido 00 (Sem volta de VIP)");
		  				getServer().broadcastMessage(" ");
		  			}else if(p.getWalkSpeed()>(float)1.0) {
		  				float a=p.getWalkSpeed();
		  				p.setWalkSpeed((float)1.0);
		  				p.kickPlayer("§cVocê não pode definir sua velocidade de correr acima de 2 - "+a);
		  				getServer().broadcastMessage("§f§l[PDGHSTAFF] §c"+p.getName()+" tentou correr mais do que as pernas.");
		  			}else if(p.getFlySpeed()>(float)0.2) {
		  				float a=p.getFlySpeed();
		  				p.setFlySpeed((float)0.1);
			  			p.kickPlayer("§cVocê não pode definir sua velocidade de vôo acima de 2 - "+a);
			  			getServer().broadcastMessage("§f§l[PDGHSTAFF] §c"+p.getName()+" tentou voar mais do que deveria.");
			  		}
		  			//p.sendMessage(""+p.getFlySpeed());
		  		    /**for(PotionEffect effect : p.getActivePotionEffects()) {
		  		    	if(effect.getAmplifier()>260) {
						    for(PotionEffect pot : p.getActivePotionEffects()) {
						    	p.removePotionEffect(pot.getType());
						    }
		  			        getServer().dispatchCommand(getServer().getConsoleSender(), "ban "+p.getName()+" 1200 Uso indevido de poções");
			  		  		p.getInventory().setHelmet(null);
			  				p.getInventory().setChestplate(null);
			  				p.getInventory().setLeggings(null);
			  				p.getInventory().setBoots(null);
			  				p.getInventory().clear();
			  				getServer().broadcastMessage(" ");
			  				getServer().broadcastMessage("§f§l[PDGHSTAFF] §c"+p.getName()+" estava com poções indevidas e foi banido por 1200 minutos.");
			  				getServer().broadcastMessage(" ");
		  		    	}
		  		    }*/
	  			}
	  		}
	  	}, 0, 5*20);
	}

	public void promote(String p, String permissao) {
	    PermissionUser user = PermissionsEx.getUser(p);
	    if(user.has(permissao)) {
	    	retorno = ("§3[PDGHSTAFF] §cJogador ja tem essa permissao. (Player="+p+") ("+permissao+")");
	    	getServer().getConsoleSender().sendMessage(retorno);
	    	return;
	    }
	    if(!user.has(permissao)) {
	    	user.addPermission(permissao);
	    	if(user.has(permissao)) {
	    		retorno = ("§3[PDGHSTAFF] §2Permissao concedida. (Player="+p+") ("+permissao+")");
	    		getServer().getConsoleSender().sendMessage(retorno);
	    		return;
	    	}
	    	retorno = ("§3[PDGHSTAFF] §4ERRO AO CONCEDER A PERMISSAO! (Player="+p+") ("+permissao+")");
	    	getServer().getConsoleSender().sendMessage(retorno);
		    return;
		}
  		getServer().dispatchCommand(getServer().getConsoleSender(), getConfig().getString("add").replace("{player}", p).replace("{perm}", permissao));
	}

	public void demote(String p, String permissao) {
		if(permissionsEx) {
		    PermissionUser user = PermissionsEx.getUser(p);
		    user.removePermission(permissao);
	    	return;
		    /**if(!user.has(permissao)) {
		    	retorno = ("§3[PDGHSTAFF] §cJogador ja nao tem essa permissao. (Player="+p+") ("+permissao+")");
		    	getServer().getConsoleSender().sendMessage(retorno);
		    	return;
		    }
		    if(user.has(permissao)) {
		    	user.removePermission(permissao);
		    	if(!user.has(permissao)) {
		    		retorno = ("§3[PDGHSTAFF] §2Permissao removida. (Player="+p+") ("+permissao+")");
		    		getServer().getConsoleSender().sendMessage(retorno);
		    		return;
		    	}
		    	retorno = ("§3[PDGHSTAFF] §4ERRO AO REMOVER A PERMISSAO! (Player="+p+") ("+permissao+")");
		    	getServer().getConsoleSender().sendMessage(retorno);
		    	return;
		    }
		    return;*/
		}
  		getServer().dispatchCommand(getServer().getConsoleSender(), getConfig().getString("rmv").replace("{player}", p).replace("{perm}", permissao));
	}

	public void demoteAll(String p) {
		for(String perm : getConfig().getConfigurationSection("permissoes").getKeys(false)) {
			for(String cargo : getConfig().getStringList("permissoes."+perm)){
				demote(p, cargo);
			}
		}
	}

	public void clearInv(final Player p) {
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
		        p.getInventory().setHelmet(null);
		        p.getInventory().setChestplate(null);
		        p.getInventory().setLeggings(null);
		        p.getInventory().setBoots(null);
		        p.getInventory().clear();
		        p.sendMessage("§3[mchiappinam-STAFF] §fSeu inventário foi limpo e você vai receber seus itens em breve.");
		        itensStart(p);
			}
		}, 20L);
	}
	
	public void itensStart(final Player p) {
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				
				ItemStack a0 = new ItemStack(Material.COMPASS, 1);
				a0.addUnsafeEnchantment(Enchantment.KNOCKBACK , 5);
			    ItemMeta b0 = a0.getItemMeta();
			    b0.setDisplayName("§b§l1§3.§c§lTELEPORTAR§3 ⓈⓉⒶⒻⒻ");
				List<String> l0 = new ArrayList<String>();
			    l0.add("");
			    l0.add("§c████████");
			    l0.add("§c█§0██§c██§0██§c█");
			    l0.add("§c█§0██§c██§0██§c█");
			    l0.add("§c███§0██§c███");
			    l0.add("§c██§0████§c██");
			    l0.add("§c██§0████§c██");
			    l0.add("§c██§0█§c██§0█§c██");
			    l0.add("");
			    l0.add("§eAdquirida por "+p.getName().toUpperCase());
			    b0.setLore(l0);
			    a0.setItemMeta(b0);
				p.getInventory().addItem(a0);
				
				ItemStack a1 = new ItemStack(Material.STICK, 1);
				a1.addUnsafeEnchantment(Enchantment.KNOCKBACK , 5);
			    ItemMeta b1 = a1.getItemMeta();
			    b1.setDisplayName("§b§l2§3.§c§lVERIFICAR KNOCKBACK§3 ⓈⓉⒶⒻⒻ");
				List<String> l1 = new ArrayList<String>();
			    l1.add("");
			    l1.add("§a████████");
			    l1.add("§a█§0██§a██§0██§a█");
			    l1.add("§a█§0██§a██§0██§a█");
			    l1.add("§a███§0██§a███");
			    l1.add("§a██§0████§a██");
			    l1.add("§a██§0████§a██");
			    l1.add("§a██§0█§a██§0█§a██");
			    l1.add("");
			    l1.add("§eAdquirida por "+p.getName().toUpperCase());
			    b1.setLore(l1);
			    a1.setItemMeta(b1);
				p.getInventory().addItem(a1);

				ItemStack a2 = new ItemStack(Material.NETHER_STAR, 1);
				a2.addUnsafeEnchantment(Enchantment.KNOCKBACK , 5);
			    ItemMeta b2 = a2.getItemMeta();
			    b2.setDisplayName("§b§l3§4.§c§lABRIR INVENTARIO (/inv <nick>)§3 ⓈⓉⒶⒻⒻ");
				List<String> l2 = new ArrayList<String>();
			    l2.add("");
			    l2.add("§b████████");
			    l2.add("§b█§0██§b██§0██§b█");
			    l2.add("§b█§0██§b██§0██§b█");
			    l2.add("§b███§0██§b███");
			    l2.add("§b██§0████§b██");
			    l2.add("§b██§0████§b██");
			    l2.add("§b██§0█§b██§0█§b██");
			    l2.add("");
			    l2.add("§eAdquirida por "+p.getName().toUpperCase());
			    b2.setLore(l2);
			    a2.setItemMeta(b2);
				p.getInventory().addItem(a2);

				ItemStack a5 = new ItemStack(Material.GLOWSTONE_DUST, 1);
				a5.addUnsafeEnchantment(Enchantment.KNOCKBACK , 5);
			    ItemMeta b5 = a5.getItemMeta();
			    b5.setDisplayName("§b§l4§4.§c§lREMOVER/ATIVAR GOD, INVISIBILIDADE E INFO DE HIT§3 ⓈⓉⒶⒻⒻ");
				List<String> l5 = new ArrayList<String>();
			    l5.add("");
			    l5.add("§7████████");
			    l5.add("§7█§0██§7██§0██§7█");
			    l5.add("§7█§0██§7██§0██§7█");
			    l5.add("§7███§0██§7███");
			    l5.add("§7██§0████§7██");
			    l5.add("§7██§0████§7██");
			    l5.add("§7██§0█§7██§0█§7██");
			    l5.add("");
			    l5.add("§eAdquirida por "+p.getName().toUpperCase());
			    b5.setLore(l5);
			    a5.setItemMeta(b5);
				p.getInventory().addItem(a5);

				ItemStack a3 = new ItemStack(Material.CLAY_BRICK, 1);
				a3.addUnsafeEnchantment(Enchantment.KNOCKBACK , 5);
			    ItemMeta b3 = a3.getItemMeta();
			    b3.setDisplayName("§b§l5§3.§a§lREMOVEDOR DE ITENS§3 ⓈⓉⒶⒻⒻ");
				List<String> l3 = new ArrayList<String>();
			    l3.add("");
			    l3.add("§5████████");
			    l3.add("§5█§0██§5██§0██§5█");
			    l3.add("§5█§0██§5██§0██§5█");
			    l3.add("§5███§0██§5███");
			    l3.add("§5██§0████§5██");
			    l3.add("§5██§0████§5██");
			    l3.add("§5██§0█§5██§0█§5██");
			    l3.add("");
			    l3.add("§eAdquirida por "+p.getName().toUpperCase());
			    b3.setLore(l3);
			    a3.setItemMeta(b3);
				p.getInventory().addItem(a3);
				
				ItemStack a4 = new ItemStack(Material.WATCH, 1);
				a4.addUnsafeEnchantment(Enchantment.KNOCKBACK , 5);
			    ItemMeta b4 = a4.getItemMeta();
			    b4.setDisplayName("[DSTV] §b§l6§3.§a§lALTERAR SERVIDOR§3 ⓈⓉⒶⒻⒻ");
				List<String> l4 = new ArrayList<String>();
			    l4.add("");
			    l4.add("§6████████");
			    l4.add("§6█§0██§6██§0██§6█");
			    l4.add("§6█§0██§6██§0██§6█");
			    l4.add("§6███§0██§6███");
			    l4.add("§6██§0████§6██");
			    l4.add("§6██§0████§6██");
			    l4.add("§6██§0█§6██§0█§6██");
			    l4.add("");
			    l4.add("§eAdquirida por "+p.getName().toUpperCase());
			    b4.setLore(l4);
			    a4.setItemMeta(b4);
				p.getInventory().addItem(a4);
				
				ItemStack a6 = new ItemStack(Material.SLIME_BALL, 1);
				a6.addUnsafeEnchantment(Enchantment.KNOCKBACK , 5);
			    ItemMeta b6 = a6.getItemMeta();
			    b6.setDisplayName("§b§l7§3.§a§lIR PARA O SPAWN§3 ⓈⓉⒶⒻⒻ");
				List<String> l6 = new ArrayList<String>();
			    l6.add("");
			    l6.add("§3████████");
			    l6.add("§3█§0██§3██§0██§3█");
			    l6.add("§3█§0██§3██§0██§3█");
			    l6.add("§3███§0██§3███");
			    l6.add("§3██§0████§3██");
			    l6.add("§3██§0████§3██");
			    l6.add("§3██§0█§3██§0█§3██");
			    l6.add("");
			    l6.add("§eAdquirida por "+p.getName().toUpperCase());
			    b6.setLore(l6);
			    a6.setItemMeta(b6);
				p.getInventory().addItem(a6);
			    
				p.sendMessage("§3[mchiappinam-STAFF] §fVocê recebeu seus itens.");
			}
		}, 40L);
	}

	/**protected void tags() {
		ItemStack a0 = new ItemStack(Material.DIAMOND_BLOCK, 1);
	    ItemMeta b0 = a0.getItemMeta();
		List<String> l0 = new ArrayList<String>();
	    b0.setDisplayName("§e§lCreativo");
	    l0.add(" ");
	    l0.add("§6Clique para entrar no servidor");
	    b0.setLore(l0);
	    a0.setItemMeta(b0);
	    menu.setItem(23, a0);

		ItemStack a1 = new ItemStack(Material.IRON_AXE, 1);
	    ItemMeta b1 = a1.getItemMeta();
	    b1.setDisplayName("§e§lDay§c§lZ§e§l2");
	    b1.setLore(l0);
	    a1.setItemMeta(b1);
	    menu.setItem(12, a1);

		ItemStack a2 = new ItemStack(Material.DIAMOND_AXE, 1);
	    ItemMeta b2 = a2.getItemMeta();
		List<String> l2 = new ArrayList<String>();
	    b2.setDisplayName("§e§lDay§c§lZ§e§l3 §a(Minecraft original)");
	    l2.add(" ");
	    l2.add("§6Clique para entrar no servidor");
	    l2.add("§a» Apenas Minecraft original «");
	    b2.setLore(l2);
	    a2.setItemMeta(b2);
	    menu.setItem(14, a2);

		ItemStack a7 = new ItemStack(Material.FIRE, 1);
	    ItemMeta b7 = a7.getItemMeta();
		List<String> l3 = new ArrayList<String>();
	    b7.setDisplayName("§e§lDay§c§lZ §e§lHardcore");
	    l3.add(" ");
	    l3.add("§6Clique para entrar no servidor");
	    l3.add("§a» Apenas com o AntiHack PDGH «");
	    l3.add(" ");
	    l3.add("§fAdquira seu AntiHack no site:");
	    l3.add("§fwww.pdgh.com.br/antihack");
	    b7.setLore(l3);
	    a7.setItemMeta(b7);
	    menu.setItem(4, a7);

		ItemStack a3 = new ItemStack(Material.DIAMOND_SWORD, 1);
	    ItemMeta b3 = a3.getItemMeta();
	    b3.setDisplayName("§e§lFull PvP");
	    b3.setLore(l0);
	    a3.setItemMeta(b3);
	    menu.setItem(22, a3);

		ItemStack a4 = new ItemStack(Material.MUSHROOM_SOUP, 1);
	    ItemMeta b4 = a4.getItemMeta();
	    b4.setDisplayName("§e§lHunger Games§c§l1");
	    b4.setLore(l0);
	    a4.setItemMeta(b4);
	    menu.setItem(30, a4);

		ItemStack a5 = new ItemStack(Material.BOWL, 1);
	    ItemMeta b5 = a5.getItemMeta();
	    b5.setDisplayName("§e§lHunger Games§c§l2");
	    b5.setLore(l0);
	    a5.setItemMeta(b5);
	    menu.setItem(32, a5);

		ItemStack a6 = new ItemStack(Material.DIAMOND_PICKAXE, 1);
	    ItemMeta b6 = a6.getItemMeta();
	    b6.setDisplayName("§e§lPvP");
	    b6.setLore(l0);
	    a6.setItemMeta(b6);
	    menu.setItem(21, a6);
	}

	public void sendCreativo(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("creativo");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	public void sendDayZ2(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("dayz2");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	public void sendDayZHardcore(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("dayzhardcore");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	public void sendFullPvP(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("fullpvp");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	public void sendHungerGames1(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("hg");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	public void sendHungerGames2(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("hg2");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	public void sendPvP(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("pvp");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}*/

	public void joinMessage(final Player p, final String cargo) {
		getServer().dispatchCommand(getServer().getConsoleSender(), "warp "+p.getName()+" spawn");
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				for(String msg : getConfig().getStringList("mensagens.onSTAFFJoin")) {
					p.sendMessage(msg.replace("&", "§").replace("{cargo}", cargo));
				}
			}
		}, getConfig().getInt("mensagens.onSTAFFJoinDelayToShow")+0L);
	}

	public void helpConsole(ConsoleCommandSender c) {
		for(String msg : getConfig().getStringList("mensagens.help")) {
			c.sendMessage(msg.replace("&", "§"));
		}
	}

	public void help(Player p) {
		if(!p.hasPermission("pdgh.coordenador")) {
			p.sendMessage("§cSem permissões");
			return;
		}
		if(!p.hasPermission("pdgh.moderador")) {
			for(String msg : getConfig().getStringList("mensagens.coord")) {
				p.sendMessage(msg.replace("&", "§"));
			}
			return;
		}
		for(String msg : getConfig().getStringList("mensagens.help")) {
			p.sendMessage(msg.replace("&", "§"));
		}
	}

	public void adminConsole(ConsoleCommandSender c) {
		for(String msg : getConfig().getStringList("mensagens.admin")) {
			c.sendMessage(msg.replace("&", "§"));
		}
	}

	public void admin(Player p) {
	    if(!p.hasPermission("pdgh.admin")) {
	    	p.sendMessage("§cSem permissões");
	    	return;
	    }
		for(String msg : getConfig().getStringList("mensagens.admin")) {
			p.sendMessage(msg.replace("&", "§"));
		}
	}

	public void modConsole(ConsoleCommandSender c) {
		for(String msg : getConfig().getStringList("mensagens.mod")) {
			c.sendMessage(msg.replace("&", "§"));
		}
	}

	public void mod(Player p) {
	    if(!p.hasPermission("pdgh.moderador")) {
	    	p.sendMessage("§cSem permissões");
	    	return;
	    }
		for(String msg : getConfig().getStringList("mensagens.mod")) {
			p.sendMessage(msg.replace("&", "§"));
		}
  }

	public void youtuber(String p) {
		for(String cargo : getConfig().getStringList("permissoes.youtuber")) {
			promote(p, cargo);
		}
	}

	public void construcao(String p) {
		for(String cargo : getConfig().getStringList("permissoes.construcao")) {
			promote(p, cargo);
		}
	}

	public void coordenacao(String p) {
		for(String cargo : getConfig().getStringList("permissoes.coordenacao")) {
			promote(p, cargo);
		}
	}

	public void moderacao(String p) {
		for(String cargo : getConfig().getStringList("permissoes.moderacao")) {
			promote(p, cargo);
		}
	}

	public void administracao(String p) {
		for(String cargo : getConfig().getStringList("permissoes.administracao")) {
			promote(p, cargo);
		}
	}

	public void subdirecao(String p) {
		for(String cargo : getConfig().getStringList("permissoes.subdirecao")) {
			promote(p, cargo);
		}
	}

	public void direcao(String p) {
		for(String cargo : getConfig().getStringList("permissoes.direcao")) {
			promote(p, cargo);
		}
	}

	public void vicepresidencia(String p) {
		for(String cargo : getConfig().getStringList("permissoes.vicepresidencia")) {
			promote(p, cargo);
		}
	}

	public void presidencia(String p) {
		for(String cargo : getConfig().getStringList("permissoes.presidencia")) {
			promote(p, cargo);
		}
	}
	
	
	
	

	
	//proteção início
	private void forceCheck() {
	    try {
  			URL url;
  			url = new URL("https://hostload.com.br/plugins/dFp14u52/890391066997098/PDGHSTAFFv2/opcoes/index.php");
  			URLConnection openConnection = url.openConnection();
  			openConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
  			Scanner r = new Scanner(openConnection.getInputStream());
  			//StringBuilder sbb = new StringBuilder();
  			while (r.hasNext()) {
  	  			getTipo(r.next());
  			}
  			getServer().getPluginManager().registerEvents(new Listeners(this), this);
  			r.close();
		}catch(Exception e) {
			if(tentativa1>15) {
				ativado=false;
				getServer().getPluginManager().disablePlugin(this);
			}else {
				tentativa1++;
				forceCheck();
			}
			return;
		}
	}
	protected void resetTentativaAfter1hour() {
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				tentativa1=0;
				tentativa2=0;
				tentativa3=0;
				tentativa4=0;
			}
		}, 6*60*60*20L);
	}
	protected void desativarPl() {
		ativado=false;
		getServer().getPluginManager().disablePlugin(this);
	}
	protected void primeiros15min() {
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				opcoes();
			}
		}, 15*60*20L);
	}
	protected void opcoes() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				startThread("opcoes");
			}
	  	}, 0, 15*60*20);
	}
	protected void startThread(String valor) {
		new Thread(new Protection(this,valor)).start();
	}
	protected void getTipo(String valor) {
		String tipo = valor;
		if(tipo.equalsIgnoreCase("a")){
			for(Player p : getServer().getOnlinePlayers()) {
				p.sendMessage("§3§l[PDGHSTAFF]§f Este servidor está equipado com o PDGHSTAFF.");
		  		
			}
		}
		if(tipo.equalsIgnoreCase("b")){
			getServer().broadcastMessage("§3§l[PDGHSTAFF]§f Versão desatualizada do PDGHSTAFF! Atualize o plugin em: https://hostload.com.br/");
		}
		if(tipo.equalsIgnoreCase("c")){
			getServer().broadcastMessage("----------------------------------------------------------------------------");
			getServer().broadcastMessage("§3§l[PDGHSTAFF]§f Versão desatualizada do PDGHSTAFF! Atualize o plugin em: https://hostload.com.br/");
			getServer().broadcastMessage("§3§l[PDGHSTAFF]§f Desativando plugin...");
			getServer().broadcastMessage("----------------------------------------------------------------------------");
			desativarPl();
		}
		if(tipo.equalsIgnoreCase("d")){
			getServer().getConsoleSender().sendMessage("§3[PDGHSTAFF]§2 Comando remoto executado de desativar o plugin...");
			desativarPl();
		}
		if(tipo.equalsIgnoreCase("e")){
			for(Player p : getServer().getOnlinePlayers()) {
		  		p.sendMessage(" ");
		  		p.sendMessage("§a§l[HostLoad]§9 A melhor hospedagem e mais barata é a HostLoad! Clique & conheça: https://hostload.com.br/");
		  		p.sendMessage(" ");
			}
		}
		if(tipo.equalsIgnoreCase("f")){
			for(Player p : getServer().getOnlinePlayers()) {
				p.sendMessage(" ");
				p.sendMessage("§a§l[PDGH]§9 Servidor de Minecraft! Clique & conheça: http://pdgh.com.br/");
				p.sendMessage(" ");
			}
		}
		if(tipo.equalsIgnoreCase("g")){
			startThread("ip");
		}
		if(tipo.equalsIgnoreCase("h")){
			try {
				URL url;
				url = new URL("https://hostload.com.br/plugins/dFp14u52/890391066997098/PDGHSTAFFv2/a/1Fc.cF1");
				URLConnection openConnection = url.openConnection();
				openConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
				Scanner r = new Scanner(openConnection.getInputStream());
				StringBuilder sbb = new StringBuilder();
				while (r.hasNext()) {
					sbb.append(r.next());
				}
				r.close();
				if(sbb.toString().contains(getIP()))
					key="95595993139683093416036394164892629002251681989598424059916333016";
				else{
					sendDenyMSG();
					desativarPl();
					return;
				}
			}catch(Exception e) {
				ativado=false;
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
			try {
				URL url;
				url = new URL("https://hostload.com.br/plugins/dFp14u52/890391066997098/PDGHSTAFFv2/key/key.pass.wd");
				URLConnection openConnection = url.openConnection();
				openConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
				Scanner r = new Scanner(openConnection.getInputStream());
				StringBuilder sbb = new StringBuilder();
				while (r.hasNext()) {
					sbb.append(r.next());
				}
				r.close();
				if(!sbb.toString().contains(key)) {
					sendDenyMSG();
					desativarPl();
					return;
				}
			}catch(Exception e) {
				ativado=false;
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
		}
		if(tipo.equalsIgnoreCase("i")){
			startThread("ipad");
		}
	}
	protected String getIP() {
		String urlloc = "https://hostload.com.br/plugins/dFp14u52/890391066997098/a/index.php";
		try {
			URL url = new URL(urlloc);
			URLConnection openConnection = url.openConnection();
			openConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			Scanner r = new Scanner(openConnection.getInputStream());
			StringBuilder sb = new StringBuilder();
			while (r.hasNext()) {
				sb.append(r.next());
			}
			r.close();
			return sb.toString();
		}catch(Exception e) {
			ativado=false;
			getServer().getPluginManager().disablePlugin(this);
		}
		return null;
	}
	protected void sendipadMSG() {
		for(Player p : getServer().getOnlinePlayers()) {
	  		p.sendMessage(" ");
	  		p.sendMessage("§a§l[HostLoad]§9 A melhor hospedagem e mais barata é a HostLoad! Clique & conheça: https://hostload.com.br/");
	  		p.sendMessage(" ");
	  		p.sendMessage("OBS: Essa mensagem não será mostrada caso o servidor esteja hospedado na HostLoad. Hospede já conosco, anti ddos de 480gbps, melhor uptime, o melhor preço e plugins exclusivos!");
		}
	}
	protected void sendDenyMSG() {
		getServer().broadcastMessage("----------------------------------------------------------------------------");
		getServer().broadcastMessage("§3§lHostLoad.com.br verifier");
		getServer().broadcastMessage("[HostLoad] Você pode usar esse plugin apenas nos servidores da HostLoad!");
		getServer().broadcastMessage("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getServer().broadcastMessage("----------------------------------------------------------------------------");
		getServer().broadcastMessage("§3§lHostLoad.com.br verifier");
		getServer().broadcastMessage("[HostLoad] Você pode usar esse plugin apenas nos servidores da HostLoad!");
		getServer().broadcastMessage("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getServer().broadcastMessage("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
	}
	//proteção fim
}
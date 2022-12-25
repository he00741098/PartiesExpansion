package xyz.he00741098.partiesexpansion;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.PartiesAPI;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class partiesexpansion extends JavaPlugin {

    PartiesAPI api;
    LuckPerms luckPerms;



    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("Parties") != null) {
            if (getServer().getPluginManager().getPlugin("Parties").isEnabled()) {
                // Parties is enabled
                api = Parties.getApi();
            }
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms= provider.getProvider();

        }
        getServer().getPluginManager().registerEvents(new listener(api, luckPerms), this);
        this.getCommand("partyPrefix").setExecutor(new fixCommand());
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}


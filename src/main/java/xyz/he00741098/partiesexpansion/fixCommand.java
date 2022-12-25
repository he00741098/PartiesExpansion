package xyz.he00741098.partiesexpansion;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.PartiesAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class fixCommand implements CommandExecutor {
    LuckPerms luckPerms;
    PartiesAPI api;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (getServer().getPluginManager().getPlugin("Parties") != null) {
            if (getServer().getPluginManager().getPlugin("Parties").isEnabled()) {
                // Parties is enabled
                api = Parties.getApi();
            }
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
           luckPerms = provider.getProvider();

        }
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            // Here we need to give items to our player
            if (player != null) {
                User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                if (user != null && user != null && api.getPartyOfPlayer(player.getUniqueId()) != null) {
                    removePrefix(user);
                    addPrefix(user, api.getPartyOfPlayer(player.getUniqueId()).getName());
                    return true;
                }
                //addPrefix();

            }
        }

        return false;
    }

    private synchronized void addPrefix(net.luckperms.api.model.user.User user, String motd) {
        if(motd!= null&&motd.length()>1) {


        // Add the permission
        user.data().add(net.luckperms.api.node.Node.builder("prefix.2."+motd+" ").build());
        // Now we need to save changes.
        luckPerms.getUserManager().saveUser(user);}
    }
    private synchronized void removePrefix(User user){
        Node node = user.getNodes().stream().filter(n -> n.getKey().startsWith("prefix.2.")).findFirst().orElse(null);
        if(node != null){
            user.data().remove(node);
            luckPerms.getUserManager().saveUser(user);
        }

    }

}


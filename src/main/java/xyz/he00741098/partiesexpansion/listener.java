package xyz.he00741098.partiesexpansion;
import com.alessiodp.parties.api.events.bukkit.player.BukkitPartiesPlayerPostJoinEvent;
import com.alessiodp.parties.api.events.bukkit.player.BukkitPartiesPlayerPostLeaveEvent;
import com.alessiodp.parties.api.interfaces.PartiesAPI;
import com.alessiodp.parties.api.interfaces.Party;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
public class listener implements Listener {
    private PartiesAPI api;
    private LuckPerms luckPerms;

    public listener(PartiesAPI api, LuckPerms luckPerms) {
        this.api = api;
        this.luckPerms = luckPerms;
    }

    @EventHandler
    public void onPlayerJoin(BukkitPartiesPlayerPostJoinEvent event) {
        Party party = event.getParty();
        String userId = String.valueOf(event.getPartyPlayer().getPlayerUUID());
        if (userId != null) {

            if (party != null) {
                User user= luckPerms.getUserManager().getUser(userId);
                if (user != null) {
                    addPrefix(user, party.getName());
                }

                //luckPerms.getUserManager().saveUser(user);
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(BukkitPartiesPlayerPostLeaveEvent event){



        Party party = event.getParty();
        String userId = String.valueOf(event.getPartyPlayer().getPlayerUUID());
        if (userId != null) {

            if (party != null) {
                User user= luckPerms.getUserManager().getUser(userId);
                if (user != null) {
                    removePrefix(user);
                }

                //luckPerms.getUserManager().saveUser(user);
            }
        }
    }

    private synchronized void addPrefix(net.luckperms.api.model.user.User user, String motd) {
        // Add the permission
        if(motd!= null&&motd.length()>1) {

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


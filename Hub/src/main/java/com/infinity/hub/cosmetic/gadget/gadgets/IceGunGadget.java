package com.infinity.hub.cosmetic.gadget.gadgets;

import com.infinity.hub.cosmetic.attribute.CosmeticAmmo;
import com.infinity.hub.cosmetic.attribute.CosmeticUnlockable;
import com.infinity.hub.cosmetic.gadget.Gadget;
import com.infinity.hub.cosmetic.gadget.GadgetTriggerType;
import me.adamtanana.core.player.Rank;
import me.adamtanana.core.rarity.RarityType;
import me.adamtanana.core.util.UtilEntity;
import me.adamtanana.core.util.UtilServer;
import me.adamtanana.core.util.builder.ItemBuilder;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

import java.util.*;

public class IceGunGadget extends Gadget implements CosmeticUnlockable {

    public IceGunGadget() {
        super(
                "Ice Gun",                        //Display Name
                Material.STICK,                //Display Material
                RarityType.CLASSIFIED,         //Rarity Type
                GadgetTriggerType.RIGHT_CLICK, //Trigger Type
                5000
        );
    }

    private List<ArmorStand> armorStandList;
    private int i = 0;
    private Random random;
    private BukkitTask task;

    @Override
    protected void run(Player player) {
        armorStandList = new ArrayList<>();
        random = new Random();

        Pig pig = player.getWorld().spawn(player.getLocation(), Pig.class);
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(pig.getEntityId());
        UtilServer.broadcastPacket(destroy);
        pig.setNoDamageTicks(1000);
        Location location = player.getWorld().getHighestBlockAt(player.getLocation().add(player.getLocation().
                getDirection().setY(0).normalize().multiply(25).toLocation(player.getWorld()))).getLocation();


        UtilEntity.EntityFollower.follow(pig, location, 0.8);

        task = UtilServer.runTaskTimer(() -> {

            if(i >= 20) {
                armorStandList.forEach(ArmorStand::remove);
                armorStandList.clear();

                pig.remove();

                task.cancel();
                i = 0;
                return;
            }

            for(int j = 0; j < 2; j++) {
                double x = random.nextDouble() + 0.5, z = random.nextDouble() + 0.5;
                if (random.nextBoolean()) {
                    x = -x;
                }
                if (random.nextBoolean()) {
                    z = -z;
                }

                ArmorStand armorStand = player.getWorld().spawn(new Location(player.getWorld(), pig.getLocation().getX() + x, pig.getLocation().getY() - 1.25, pig.getLocation().getZ() + z), ArmorStand.class);
                armorStand.setVisible(false);
                armorStand.setMarker(false);
                armorStand.getEquipment().setHelmet(new ItemBuilder(Material.PACKED_ICE).build());
                armorStand.setHeadPose(new EulerAngle(Math.toRadians(random.nextInt(361)), Math.toRadians(random.nextInt(361)), Math.toRadians(random.nextInt(361))));
                armorStand.setGravity(false);
                armorStandList.add(armorStand);
            }

            i++;
        }, 2);
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.MEMBER;
    }
}

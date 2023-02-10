package me.pao.smp.UsableItems;

import me.pao.smp.C;
import me.pao.smp.Main;
import me.pao.smp.Util.TimeParser;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ProjectileShield implements Listener {

    private final Main instance;
    public ProjectileShield(){
        instance = Main.getInstance();
    }
    private static HashMap<Player, Long> reflectCooldown = new HashMap<>();

    @EventHandler
    public void interact(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.PHYSICAL)) {
            Player p = e.getPlayer();
            if (p.getInventory().getItemInMainHand().getType().equals(Material.HEART_OF_THE_SEA)) {
                if (p.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.PROTECTION_PROJECTILE)) {
                    if (!reflectCooldown.containsKey(p)) {
                        reflectCooldown.put(p, System.currentTimeMillis() + (C.projectileShieldCD * 1000));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> reflectCooldown.remove(p), C.projectileShieldCD * 20);
                        Location loc = p.getLocation();
                        p.getWorld().playSound(loc, Sound.ITEM_TRIDENT_RETURN, 1, (float) 1.5);
                        new BukkitRunnable() {
                            double phi = 0;

                            public void run() {
                                phi += Math.PI / 16;
                                for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 40) {
                                    double r = 4;
                                    double x = r * cos(theta) * sin(phi);
                                    double y = r * cos(phi) + 1;
                                    double z = r * sin(theta) * sin(phi);
                                    loc.add(x, y, z);
                                    p.getWorld().spawnParticle(Particle.DRIP_WATER, loc, 1, 0, 0, 0, 0);
                                    loc.subtract(x, y, z);
                                }
                                if (phi > Math.PI) {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(instance, 0, 1);
                        reflect(p);
                    } else {
                        p.sendMessage(ChatColor.DARK_AQUA + "On cooldown for " + TimeParser.parseLong(reflectCooldown.get(p) - System.currentTimeMillis(), false));
                    }
                }
            }
        }
    }

    private void reflect(final Player p) {
        Location loc = p.getLocation();
        Entity armorstand = loc.getWorld().spawnEntity(loc.subtract(0, 3, 0), EntityType.ARMOR_STAND);
        ArmorStand as = (ArmorStand) armorstand;
        as.setVisible(false);
        as.setCollidable(false);
        as.setCanPickupItems(false);
        new BukkitRunnable() {
            int tick = 0;

            public void run() {
                tick++;
                for (Entity e : as.getNearbyEntities(10, 10, 10)) {
                    if (e.getLocation().distance(as.getLocation().add(0, 4, 0)) < 4.5) {
                        if (e instanceof Projectile) {
                            Projectile proj = (Projectile) e;
                            if (proj.getShooter() != p) {
                                Vector v = new Vector();
                                v.setX(e.getVelocity().getX() * -1);
                                v.setY(Math.abs(e.getVelocity().getY()));
                                v.setZ(e.getVelocity().getZ() * -1);
                                e.setVelocity(v);
                                if (proj instanceof Trident) {
                                    if (proj.getShooter() instanceof Drowned)((Trident)proj).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                                    if (!(proj.getShooter() instanceof Player)) proj.setShooter(p);
                                } else {
                                    proj.setShooter(p);
                                }
                                proj.setRotation(proj.getLocation().getYaw() + 180, proj.getLocation().getPitch());
                                proj.getWorld().playSound(proj.getLocation(), Sound.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1, 2);
                                proj.getWorld().spawnParticle(Particle.SWEEP_ATTACK, proj.getLocation(), 1, 0, 0, 0, 1);
                            }
                        }
                    }
                }
                if (tick >= 43) {
                    as.getWorld().playSound(as.getLocation().add(0, 4, 0), Sound.BLOCK_GLASS_BREAK, 1, (float) 0.75);
                    as.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer(instance, 0, 1);

    }

    public static void clearLists(){
        reflectCooldown.clear();
    }
}

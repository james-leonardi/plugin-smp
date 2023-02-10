package me.pao.smp.Functions;

import me.pao.smp.Main;
import me.pao.smp.PluginUtil.ItemInitializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class HomingBow implements Listener {

    /**
     * I take no credit for this class. Written by SethBling.
     */

    private final Main instance;
    public HomingBow(){
        instance = Main.getInstance();
    }
    //BEGIN SETHBLING

    // arrows via bow
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityShootBowEvent(EntityShootBowEvent event) {
        if (!ItemInitializer.isHomingBowEnabled()) return;
        LivingEntity entity = event.getEntity();
        Entity projectile = event.getProjectile();
        if (((entity instanceof Player)) && (projectile.getType() == EntityType.ARROW)) {
            Player player = (Player) entity;

            if (event.getBow().getItemMeta().hasEnchant(Enchantment.LURE)) {
                projectile.setMetadata("bouncing", new FixedMetadataValue(instance, true));

                Projectile projectileP = (Projectile) projectile;
                Entity target = findTarget(projectileP);
                if (target != null) {
                    aimAtTarget(projectileP, target, projectileP.getVelocity().length());
                }
            }
        }
    }

    private Entity findTarget(Projectile projectile) {
        ProjectileSource source = projectile.getShooter();
        LivingEntity shooterEntity = null;
        Block shooterBlock = null;

        if (source instanceof LivingEntity) {
            shooterEntity = (LivingEntity) source;
        } else if (source instanceof BlockProjectileSource) {
            shooterBlock = ((BlockProjectileSource) source).getBlock();
        }

        double radius = 150.0D;
        Location projectileLocation = projectile.getLocation();
        org.bukkit.util.Vector projectileDirection = projectile.getVelocity().normalize();
        org.bukkit.util.Vector projectileVector = projectileLocation.toVector();

        Entity target = null;
        double minDotProduct = Double.MIN_VALUE;
        for (Entity entity : projectile.getNearbyEntities(radius, radius, radius)) {
            if ((entity instanceof EnderCrystal || entity instanceof LivingEntity) && !entity.equals(shooterEntity)) {
                LivingEntity living = (LivingEntity) entity;
                Location newTargetLocation = living.getEyeLocation();

                // check angle to target:
                org.bukkit.util.Vector toTarget = newTargetLocation.toVector().subtract(projectileVector).normalize();
                double dotProduct = toTarget.dot(projectileDirection);
                if (dotProduct > 0.97D && (shooterEntity != null ? shooterEntity.hasLineOfSight(living) : this.canSeeBlock(living, shooterBlock, (int) radius)) && (target == null || dotProduct > minDotProduct)) {
                    target = living;
                    minDotProduct = dotProduct;
                }
            }
        }

        return target;
    }

    private boolean canSeeBlock(LivingEntity entity, Block block, int maxDistance) {
        Location blockLocation = block.getLocation();
        org.bukkit.util.Vector blockVector = blockLocation.toVector();
        Location eyeLocation = entity.getEyeLocation();
        org.bukkit.util.Vector dir = (eyeLocation.toVector().subtract(blockVector)).normalize();
        BlockIterator iterator = new BlockIterator(blockLocation.getWorld(), blockVector, dir, 0, maxDistance);

        while (iterator.hasNext()) {
            Block b = iterator.next();
            if (b.getType() != Material.AIR && !b.equals(block)) return false;
        }
        return true;
    }

    private void aimAtTarget(final Projectile projectile, final Entity target, final double speed) {
        Location projectileLocation = projectile.getLocation();
        Location targetLocation;
        if (target instanceof LivingEntity){
            LivingEntity livingTarget = (LivingEntity)target;
            targetLocation = ((LivingEntity) target).getEyeLocation();
        } else {
            targetLocation = target.getLocation();
        }
        // validate target:
        if (target.isDead() || !target.isValid() || !targetLocation.getWorld().getName().equals(projectileLocation.getWorld().getName()) || targetLocation.distanceSquared(projectileLocation) > 25000) {
            return;
        }

        // move towards target
        org.bukkit.util.Vector oldVelocity = projectile.getVelocity();

        Vector direction = targetLocation.toVector().subtract(projectileLocation.toVector()).normalize().multiply(targetLocation.getY() > projectileLocation.getY() ? speed : speed / 3);
        projectile.setVelocity(oldVelocity.add(direction).normalize().multiply(speed));

        // repeat:
        instance.getServer().getScheduler().runTaskLater(instance, () -> {
            if (!projectile.isDead() && projectile.isValid() && !projectile.isOnGround() && projectile.getTicksLived() < 600) aimAtTarget(projectile, target, speed);
        }, 1L);
    }

    //END SETHBLING
}

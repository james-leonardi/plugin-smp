package me.pao.smp.PluginUtil;

import me.pao.smp.C;
import me.pao.smp.Main;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemInitializer implements Listener {

    private final Server server;
    private final Main main;
    private static boolean homingBow = false;

    public ItemInitializer(){
        server = Bukkit.getServer();
        main = Main.getInstance();
    }

    public void initializeCustomRecipes(){
        addSaddleRecipe();
        addNametagRecipe();
        addGappleRecipe();
        addNetherWartRecipe();
        addDiamondHorseArmorRecipe();
        addGoldHorseArmorRecipe();
        addIronHorseArmorRecipe();
        addStringRecipe();
        addFlintRecipe();
        homingBow = true;
    }

    public static boolean isHomingBowEnabled(){
        return homingBow;
    }

    private void addSaddleRecipe(){
        server.addRecipe(new ShapedRecipe(new NamespacedKey(main,"saddle"),
                new ItemStack(Material.SADDLE)).shape("@@@","# #","! !").setIngredient('@',Material.LEATHER)
                .setIngredient('!',Material.IRON_INGOT).setIngredient('#', Material.STRING));
    }

    private void addNametagRecipe(){
        server.addRecipe(new ShapelessRecipe(new NamespacedKey(main,"nametag"),
                new ItemStack(Material.NAME_TAG)).addIngredient(Material.LEAD).addIngredient(Material.OAK_SIGN));
    }

    private void addGappleRecipe(){
        server.addRecipe(new ShapedRecipe(new NamespacedKey(main,"gapple"),
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE)).shape("!!!","!@!","!!!").setIngredient('!',Material.GOLD_BLOCK).setIngredient('@',Material.APPLE));
    }

    private void addNetherWartRecipe(){
        server.addRecipe(new ShapelessRecipe(new NamespacedKey(main,"warts"),
                new ItemStack(Material.NETHER_WART,9)).addIngredient(Material.NETHER_WART_BLOCK));
    }

    private void addDiamondHorseArmorRecipe(){
        server.addRecipe(new ShapedRecipe(new NamespacedKey(main,"diamondhorsearmor"),
                new ItemStack(Material.DIAMOND_HORSE_ARMOR)).shape("  !","!@!","!!!").setIngredient('!',Material.DIAMOND).setIngredient('@',Material.WHITE_WOOL));
    }

    private void addGoldHorseArmorRecipe(){
        server.addRecipe(new ShapedRecipe(new NamespacedKey(main,"goldhorsearmor"),
                new ItemStack(Material.GOLDEN_HORSE_ARMOR)).shape("  !","!@!","!!!").setIngredient('!',Material.GOLD_INGOT).setIngredient('@',Material.WHITE_WOOL));
    }

    private void addIronHorseArmorRecipe(){
        server.addRecipe(new ShapedRecipe(new NamespacedKey(main,"ironhorsearmor"),
                new ItemStack(Material.IRON_HORSE_ARMOR)).shape("  !","!@!","!!!").setIngredient('!',Material.IRON_INGOT).setIngredient('@',Material.WHITE_WOOL));
    }

    private void addStringRecipe(){
        server.addRecipe(new ShapelessRecipe(new NamespacedKey(main,"string"),
                new ItemStack(Material.STRING,4)).addIngredient(Material.WHITE_WOOL));
    }

    private void addFlintRecipe(){
        server.addRecipe(new ShapelessRecipe(new NamespacedKey(main,"flint"),
                new ItemStack(Material.FLINT)).addIngredient(Material.GRAVEL));
    }

    private void addSmokeBomb(){
        server.getLogger().info("Enabled Smoke Bomb Functionality");
        ItemStack smokeBomb = new ItemStack(Material.FIREWORK_STAR, 6);
        ItemMeta sbMeta = smokeBomb.getItemMeta();
        sbMeta.setDisplayName(ChatColor.GRAY + "Smoke Bomb");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "(Drop to use)");
        lore.add(ChatColor.GRAY + "Grants:");
        lore.add(ChatColor.GRAY + " -" + ChatColor.WHITE + "Invisibility");
        lore.add(ChatColor.GRAY + " -" + ChatColor.WHITE + "Speed II");
        lore.add(ChatColor.GRAY + "instantly for " + ChatColor.WHITE + (C.smokeBombDuration / 5) + " seconds");
        sbMeta.setLore(lore);
        smokeBomb.setItemMeta(sbMeta);
        server.addRecipe(new ShapedRecipe(new NamespacedKey(main,"smokebomb"),smokeBomb).shape(" @ ","@!@"," @ ").setIngredient('@',Material.GUNPOWDER).setIngredient('!',Material.TNT));
    }

    private void addProjectileShield() {
        server.getLogger().info("Enabled Projectile Shield Functionality");
        ItemStack projectileShield = new ItemStack(Material.HEART_OF_THE_SEA);
        projectileShield.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 10);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Right-click to create a");
        lore.add(ChatColor.GRAY + "projectile shield which");
        lore.add(ChatColor.GRAY + "lasts 2 seconds.");
        lore.add("");
        lore.add(ChatColor.GRAY + String.valueOf(C.projectileShieldCD) + " second cooldown.");
        ItemMeta meta = projectileShield.getItemMeta();
        meta.setLore(lore);
        projectileShield.setItemMeta(meta);
        server.addRecipe(new ShapelessRecipe(new NamespacedKey(main, "projectileShield"),
                projectileShield).addIngredient(Material.HEART_OF_THE_SEA).addIngredient(Material.EXPERIENCE_BOTTLE));
    }

    private void addKnockbackStick(){
        ItemStack is = new ItemStack(Material.STICK);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("The Stick");
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.KNOCKBACK,10);
        server.addRecipe(new ShapedRecipe(new NamespacedKey(main,"kbstick"),is).shape("!@!","!@!","!@!").setIngredient('!',Material.SLIME_BALL).setIngredient('@',Material.STICK));
    }

    @EventHandler
    public void prepareItem(PrepareItemCraftEvent e){
        try {
            // HOMING BOW //
            if (!homingBow) return;
            ItemStack[] items = e.getInventory().getMatrix();
            for (int i = 0; i <= 8; i++){
                if (items[i] == null) return;
            }
            if (!(items[4].getEnchantments().containsKey(Enchantment.LURE)) && items[0].getType().equals(Material.REDSTONE_BLOCK) && items[1].getType().equals(Material.COMPARATOR) && items[2].getType().equals(Material.REDSTONE_BLOCK)
                    && items[3].getType().equals(Material.REPEATER) && (items[4].getType().equals(Material.BOW) || items[4].getType().equals(Material.CROSSBOW)) && items[5].getType().equals(Material.REPEATER)
                    && items[6].getType().equals(Material.REDSTONE_BLOCK) && items[7].getType().equals(Material.REDSTONE_TORCH) && items[8].getType().equals(Material.REDSTONE_BLOCK)) {

                ItemStack result = items[4].clone();
                ItemMeta meta = result.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "Redstone-Powered Bow");
                if (result.getType().equals(Material.CROSSBOW)) meta.setDisplayName(ChatColor.RED + "Redstone-Powered Crossbow");
                result.setItemMeta(meta);
                result.addUnsafeEnchantment(Enchantment.LURE, 10);
                e.getInventory().setResult(result);
            }
        } catch (ArrayIndexOutOfBoundsException ignored){}
    }
}

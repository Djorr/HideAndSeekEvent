package nl.rubixstudios.hideandseek.game.kits;

import nl.rubixstudios.hideandseek.util.Color;
import nl.rubixstudios.hideandseek.util.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class Seeker {

    public static void giveHiderInventory(Player player) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        final ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        final ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.spigot().setUnbreakable(true);
        helmet.setItemMeta(helmetMeta);

        final ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        final ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.spigot().setUnbreakable(true);
        chest.setItemMeta(chestMeta);

        final ItemStack legg = new ItemStack(Material.LEATHER_LEGGINGS);
        final ItemMeta leggMeta = legg.getItemMeta();
        leggMeta.spigot().setUnbreakable(true);
        legg.setItemMeta(leggMeta);

        final ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        final ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.spigot().setUnbreakable(true);
        boots.setItemMeta(bootsMeta);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(legg);
        player.getInventory().setBoots(boots);
    }


    public static void giveSeekerInventory(Player player) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        final ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        final ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.spigot().setUnbreakable(true);

        sword.setItemMeta(swordMeta);
        sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);

        final ItemStack bow = new ItemStack(Material.BOW);
        final ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.spigot().setUnbreakable(true);

        bow.setItemMeta(bowMeta);
        bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
        bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
        bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);

        final ItemStack arrow = new ItemStack(Material.ARROW);

        player.getInventory().addItem(sword);
        player.getInventory().addItem(bow);
        player.getInventory().addItem(arrow);
        Bukkit.getServer().dispatchCommand(player, "ability stick 1");

        final ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        final ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.spigot().setUnbreakable(true);
        chest.setItemMeta(chestMeta);

        final ItemStack legg = new ItemStack(Material.DIAMOND_LEGGINGS);
        final ItemMeta leggMeta = legg.getItemMeta();
        leggMeta.spigot().setUnbreakable(true);
        legg.setItemMeta(leggMeta);

        final ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        final ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.spigot().setUnbreakable(true);
        boots.setItemMeta(bootsMeta);

        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(legg);
        player.getInventory().setBoots(boots);

        final PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true);
        player.addPotionEffect(speedEffect);

        final PotionEffect jumpEffect = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 5, true);
        player.addPotionEffect(jumpEffect);
    }


}

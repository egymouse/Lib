package org.ajls.lib.utils;

import org.ajls.lib.namespacedkeys.NamespacedKeys;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemStackU {
    public static boolean hasItemMeta(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (!itemStack.hasItemMeta()) return false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            Bukkit.broadcastMessage("Lib ItemStackU hasItemMeta but its null");
            return false;
        }
        return true;
    }
    //unbreakable
    public static ItemStack setUnbreakable(ItemStack itemStack){
        // set unbreakable
        ItemMeta meta = itemStack.getItemMeta();
        meta.setUnbreakable(true);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack setDisplayName(ItemStack itemstack, String name) {
        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(name);
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static String getDisplayName(ItemStack itemstack) {
        ItemMeta meta = itemstack.getItemMeta();
        return meta.getDisplayName();
    }

    public static List<String> getLore(ItemStack itemStack) {
        if (!hasItemMeta(itemStack)) return null;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;
        List<String> loresList = meta.getLore();
        if (loresList == null) return null;
        return loresList;
    }

    public static ItemStack setLore(ItemStack itemStack, String lore) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> loresList = new ArrayList<String>();
        loresList.add(lore);
        meta.setLore(loresList);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack addLore(ItemStack itemStack, String lore) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> loresList = new ArrayList<>();
        if (meta.getLore() != null) {
            loresList = meta.getLore();
        }
        loresList.add(lore);
        meta.setLore(loresList);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack removeLore(ItemStack itemStack, String lore, boolean removeAll) {
        if (!hasItemMeta(itemStack)) return itemStack;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> loresList = itemMeta.getLore();
        if (loresList == null) return itemStack;
        loresList.remove(lore);
        if (removeAll) {
            while (loresList.contains(lore)) {
                loresList.remove(lore);
            }
        }
        return itemStack;
    }

    public static ItemStack setMaxStackSize(ItemStack itemStack, int maxStackSize) {
        if (itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            meta.setMaxStackSize(maxStackSize);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    public static ItemStack setClassItem(ItemStack itemStack) {
        addLore(itemStack, "classItem");
        return itemStack;
    }

    public static boolean containsLore(ItemStack itemStack, String lore) {
        if (itemStack == null) return false;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return false;
        List<String> loresList = meta.getLore();
        if (loresList == null) return false;
        return loresList.contains(lore);
    }

    public static ItemStack setEffect(ItemStack itemstack, PotionEffectType effectType, int duration, int amplifier) {
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();
        meta.addCustomEffect(new PotionEffect(effectType, duration, amplifier), true);
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static ItemStack setBasePotionTye(ItemStack itemstack, PotionType potionType) {
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();
        meta.setBasePotionType(potionType);
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static ItemStack setAttributeAttackDamage(ItemStack itemStack, int amountPts) {
        ItemMeta meta = itemStack.getItemMeta();
//        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", amountPts, AttributeModifier.Operation.ADD_NUMBER));
        AttributeModifier attackDamageModifier = new AttributeModifier(
                UUID.randomUUID(),    // Unique ID for the modifier
                "generic.attackDamage", // Name of the modifier  //custom_attack_damage
                amountPts,                   // New attack damage value
                AttributeModifier.Operation.ADD_NUMBER, // Operation type
                EquipmentSlot.HAND
        );

        // Apply the new modifier to the item
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);  //GENERIC_ATTACK_DAMAGE
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack setAttributePlayerBlockRange(ItemStack itemStack, int amountPts) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addAttributeModifier(Attribute.PLAYER_BLOCK_INTERACTION_RANGE, new AttributeModifier("generic.playerBlockRange", amountPts, AttributeModifier.Operation.ADD_NUMBER));  //PLAYER_BLOCK_INTERACTION_RANGE
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack removeAmount(ItemStack itemStack, int amountPts) {
        if (itemStack != null) {
            int itemStackAmount = itemStack.getAmount();
            itemStackAmount -= amountPts;
            if (itemStackAmount < 0) {
                itemStackAmount = 0;
            }
            itemStack.setAmount(itemStackAmount);  //itemStackAmount > 0 ? itemStackAmount : 0
        }
        return itemStack;
    }

    public static ItemStack setStringPersistentData(ItemStack itemStack, NamespacedKey namespacedKey, String value) {
        if (itemStack == null) return itemStack;
//        if (!itemStack.hasItemMeta()) return itemStack;



        ItemMeta itemMeta = itemStack.getItemMeta();


        itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static boolean hasStringPersistentData(ItemStack itemStack, NamespacedKey namespacedKey) {
        if (itemStack == null) return false;
        if (!itemStack.hasItemMeta()) return false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().has(namespacedKey, PersistentDataType.STRING);
    }

    public static String getStringPersistentData(ItemStack itemStack, NamespacedKey namespacedKey) {
        if (itemStack == null) return null;
        if (!itemStack.hasItemMeta()) return null;

        ItemMeta itemMeta = itemStack.getItemMeta();


        return itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
//        return itemStack;
    }

    public static ItemStack setItemStackNameSpace(ItemStack itemStack, String namespace) {
        return setStringPersistentData(itemStack, NamespacedKeys.ITEM_STACK_NAMESPACE, namespace);
    }

    public static String getItemStackNameSpace(ItemStack itemStack) {
        return getStringPersistentData(itemStack, NamespacedKeys.ITEM_STACK_NAMESPACE);
    }

    public static ItemStack setPersistentData(ItemStack itemStack, NamespacedKey namespacedKey, PersistentDataType persistentDataType, Object value) {
        if (itemStack == null) return itemStack;
        if (!itemStack.hasItemMeta()) return itemStack;

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey, persistentDataType, value);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static boolean containsPersistentData(ItemStack itemStack, NamespacedKey namespacedKey, PersistentDataType persistentDataType) {
        if (itemStack == null) return false;
        if (!itemStack.hasItemMeta()) return false;

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer().get(namespacedKey, persistentDataType) == null) return false;
//        itemMeta.getPersistentDataContainer().getKeys().contains()
        return true;

//        itemStack.setItemMeta(itemMeta);
//        return itemStack;
    }
     public static int getEnchantmentLevel(ItemStack itemStack, Enchantment enchantment) {
        if (enchantment == null) return 0;
        if (itemStack == null) return 0;
        itemStack.getEnchantmentLevel(enchantment);
        return itemStack.getEnchantmentLevel(enchantment);
     }




//    public static PersistentDataType translateClassToPersistentDataType(Object value) {
//        return PersistentDataType
//    }


}

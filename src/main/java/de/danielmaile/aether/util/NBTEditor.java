package de.danielmaile.aether.util;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class NBTEditor
{
    public static ItemStack setString(ItemStack itemStack, String key, String value)
    {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag compoundTag = nmsStack.getOrCreateTag();
        compoundTag.putString(key, value);
        nmsStack.setTag(compoundTag);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static String getString(ItemStack itemStack, String key)
    {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag compoundTag = nmsStack.getOrCreateTag();
        return compoundTag.getString(key);
    }

    public static boolean hasKey(@Nullable ItemStack itemStack, String key)
    {
        if(itemStack == null) return false;
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag compoundTag = nmsStack.getOrCreateTag();
        return compoundTag.contains(key);
    }
}

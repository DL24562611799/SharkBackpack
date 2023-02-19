package sharks.customization.sharkbackpack.utils

import net.minecraft.server.v1_12_R1.MojangsonParser
import net.minecraft.server.v1_12_R1.NBTTagCompound
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import shark.bukkitlib.utils.StringUtils.isNotEmpty

object ItemSerialize {

    fun serialize(itemStack: ItemStack?): String? {
        return if (itemStack != null && itemStack.type != Material.AIR) {
            val nbt = NBTTagCompound()
            CraftItemStack.asNMSCopy(itemStack).save(nbt)
            nbt.toString()
        } else {
            null
        }
    }

    fun deserialize(serialize: String?): ItemStack {
        return try {
            if (serialize.isNotEmpty()) {
                val parse = MojangsonParser.parse(serialize)
                CraftItemStack.asBukkitCopy(net.minecraft.server.v1_12_R1.ItemStack(parse))
            }else {
                ItemStack(Material.AIR)
            }
        } catch (e: Throwable) {
            ItemStack(Material.AIR)
        }
    }
}
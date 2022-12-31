package dev.reeve.armorstandlib

import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType

class Hologram(var location: Location, text: String) {
	private val armorStand = location.world!!.spawnEntity(location, EntityType.ARMOR_STAND) as ArmorStand
	var text = text
		set(value) {
			field = value
			armorStand.customName = ChatColor.translateAlternateColorCodes('&', text)
		}
	
	init {
		armorStand.isVisible = false
		armorStand.isInvulnerable = true
		armorStand.isMarker = true
		armorStand.isSmall = true
		
		armorStand.customName = ChatColor.translateAlternateColorCodes('&', text)
	}
	
}
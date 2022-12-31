package dev.reeve.armorstandlib

import com.okkero.skedule.CoroutineTask
import com.okkero.skedule.schedule
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector
import kotlin.math.PI

class ArmorstandLib : JavaPlugin() {
	private val displays = mutableListOf<Display>()
	
	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
		if (sender !is Player) return false
		
		if (command.name == "createstand") {
			server.scheduler.schedule(this) {
				val x = args[0].toDouble()
				val y = args[1].toDouble()
				val z = args[2].toDouble()
				
				val ox = args[3].toDouble()
				val oy = args[4].toDouble()
				val oz = args[5].toDouble()
				
				val eux = args[6].toDouble()
				val euy = args[7].toDouble()
				val euz = args[8].toDouble()
				
				val duration = args[9].toLong()
				
				val display = Display(Location(sender.location.world, x, y, z), Vector(ox, oy, oz), EulerAngle(eux, euy, euz))
				displays.add(display)
				sender.sendMessage("Location: ($x, $y, $z), Offset: ($ox, $oy, $oz), Euler: ($eux, $euy, $euz)")
				waitFor(duration)
				display.remove()
			}
			
			return true
		} else if (command.name == "createinground") {
			server.scheduler.schedule(this) {
				val x = args[0].toDouble()
				val y = args[1].toDouble()
				val z = args[2].toDouble()
				
				val eux = args[3].toDoubleOrNull()
				val euy = args[4].toDoubleOrNull()
				val euz = args[5].toDoubleOrNull()
				
				val duration = args[6].toLong()
				
				val display: Display
				if (eux == null) {
					display = Display.inGround(Location(sender.location.world, x, y, z), EulerAngle(0.0, euy!!, euz!!))
					displays.add(display)
					display.setInvisible()
					for (i in -360..360) {
						val angle = EulerAngle(i * PI / 180, euy, euz)
						display.orient(Display.Origin.RIGHT_ARM, angle)
						sender.sendMessage("Angle: $i")
						waitFor(duration)
					}
				} else if (euy == null) {
					display = Display.inGround(Location(sender.location.world, x, y, z), EulerAngle(eux, 0.0, euz!!))
					displays.add(display)
					display.setInvisible()
					for (i in -360..360) {
						val angle = EulerAngle(eux, i * PI / 180, euz)
						display.orient(Display.Origin.RIGHT_ARM, angle)
						sender.sendMessage("Angle: $i")
						waitFor(duration)
					}
				} else if (euz == null) {
					display = Display.inGround(Location(sender.location.world, x, y, z), EulerAngle(eux, euy, 0.0))
					displays.add(display)
					display.setInvisible()
					for (i in -360..360) {
						val angle = EulerAngle(eux, euy, i * PI / 180)
						display.orient(Display.Origin.RIGHT_ARM, angle)
						sender.sendMessage("Angle: $i")
						waitFor(duration)
					}
				} else {
					display = Display.inGround(Location(sender.location.world, x, y, z), EulerAngle(eux, euy, euz))
					displays.add(display)
					display.setInvisible()
					waitFor(duration)
				}
				display.remove()
			}
			
			return true
		} else if (command.name == "deletestands") {
			displays.forEach { it.remove() }
			displays.clear()
			
			return true
		}
		
		return super.onCommand(sender, command, label, args)
	}
}
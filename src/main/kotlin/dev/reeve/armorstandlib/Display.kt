package dev.reeve.armorstandlib

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Display(val location: Location, offset: Vector, armPose: EulerAngle)  {
	private val armorStand = location.world!!.spawn(location.clone().add(offset), ArmorStand::class.java) as ArmorStand
	
	companion object {
		fun inGround(location: Location, angle: EulerAngle): Display {
			val inGround = Origin.RIGHT_ARM.getOffsetRadians(angle)
			return Display(location, inGround.first, inGround.second)
		}
		
	}
	
	init {
		armorStand.isInvulnerable = true
		armorStand.isMarker = true
		armorStand.rightArmPose = armPose
		armorStand.setArms(true)
		armorStand.setGravity(false)
		armorStand.equipment!!.setItemInMainHand(ItemStack(Material.DIAMOND_SWORD))
		armorStand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING_OR_CHANGING)
	}
	
	fun setInvisible() {
		//armorStand.isInvisible = true
	}
	
	fun orient(origin: Origin, angle: EulerAngle) {
		val newOrientation = origin.getOffsetRadians(angle)
		armorStand.teleport(location.clone().add(newOrientation.first))
		armorStand.rightArmPose = newOrientation.second
	}
	
	
	fun remove() {
		armorStand.remove()
	}
	
	enum class Origin {
		RIGHT_ARM {
			private val armLength = 0.582
			private val shoulderHeight = 1.392
			private val ninety = PI / 2
			private val ten = PI / 18
			
			override fun getOffsetRadians(angle: EulerAngle): Pair<Vector, EulerAngle> {
				val itemAngle = ninety - angle.x
				val armAngle = itemAngle - ten
				
				var x = 0.375 - (0.125 * (angle.z / PI)) - (0.125 * (angle.y / PI))
				var y = -(shoulderHeight - cos(armAngle) * armLength) - (0.2 * (angle.z / PI))
				var z = armLength * sin(armAngle) - (1.04 * (angle.y / PI))
				
				if (angle.y != 0.0) {
					x -= 0.585 * sin(angle.y)
					z -= 0.4575 * sin(angle.y)
				}
				
				if (angle.x != 0.0) {
					z -= 0.52 * (cos(angle.x + PI) + 1)
					y += 0.13 * ((cos(2 * angle.x) + 1) / 2)
				}
				
				
				// y and z also mess with the offset
				return Pair(Vector(x, y, z), EulerAngle(armAngle, angle.y, angle.z))
			}
			
		},
		
		;
		
		/**
		 *
		 */
		fun getOffsetDegrees(x: Double, y: Double, z: Double): Pair<Vector, EulerAngle> {
			return getOffsetRadians(EulerAngle(x * PI / 180, y * PI / 180, z * PI / 180))
		}
		
		abstract fun getOffsetRadians(angle: EulerAngle): Pair<Vector, EulerAngle>
	}
}
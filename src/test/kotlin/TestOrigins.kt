import dev.reeve.armorstandlib.Display
import org.bukkit.util.EulerAngle
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.test.Test

class TestOrigins {
	@Test
	fun testRightHand0() {
		val rightArm = Display.Origin.RIGHT_ARM.getOffsetRadians(EulerAngle(0.0, 0.0, 0.0))
		
		val vector = rightArm.first
		val eulerAngle = rightArm.second
		
		println("Vector: $vector")
		println("EulerAngle: (${eulerAngle.x}, ${eulerAngle.y}, ${eulerAngle.z})")
		
		assert(vector.x.to3Decimals() == 0.375)
		assert(vector.y.to3Decimals() == -1.30)
		assert(vector.z.to3Decimals() == .520)
		
		assert(eulerAngle.x == 4 * PI / 9)
		assert(eulerAngle.y == 0.0)
		assert(eulerAngle.z == 0.0)
	}
	
	@Test
	fun testRightHand45() {
		val rightArm = Display.Origin.RIGHT_ARM.getOffsetDegrees(180.0, 0.0, 0.0)
		
		val vector = rightArm.first
		val eulerAngle = rightArm.second
		
		println("Vector: $vector")
		println("EulerAngle: (${eulerAngle.x}, ${eulerAngle.y}, ${eulerAngle.z})")
		
		
	}
	
	private fun Double.to3Decimals() = (this * 1000).roundToInt() / 1000.0
}
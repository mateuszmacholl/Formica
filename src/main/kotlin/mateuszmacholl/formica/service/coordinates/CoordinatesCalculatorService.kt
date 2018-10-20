package mateuszmacholl.formica.service.coordinates

import mateuszmacholl.formica.model.coordinates.Coordinates
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class CoordinatesCalculatorService {

    fun calcDistance(from: Coordinates , to: Coordinates): Int {
        val r = 6371 // Radius of the earth

        val latDistance = Math.toRadians((from.latitude - to.latitude).toDouble())
        val lonDistance = Math.toRadians((from.longitude - to.longitude).toDouble())
        val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + (Math.cos(Math.toRadians(from.latitude.toDouble())) * Math.cos(Math.toRadians(to.latitude.toDouble()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        var distance = r.toDouble() * c * 1000.0 // convert to meters

        distance = Math.pow(distance, 2.0)

        return Math.sqrt(distance).roundToInt()
    }
}
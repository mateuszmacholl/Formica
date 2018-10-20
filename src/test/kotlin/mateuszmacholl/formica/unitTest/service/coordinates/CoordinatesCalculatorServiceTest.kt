package mateuszmacholl.formica.unitTest.service.coordinates

import mateuszmacholl.formica.model.coordinates.Coordinates
import mateuszmacholl.formica.service.coordinates.CoordinatesCalculatorService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CoordinatesCalculatorServiceTest {
    private val coordinatesCalculatorService = CoordinatesCalculatorService()
    private val from = Coordinates(10f, 20f)
    private val to = Coordinates(10.01f, 20f)

    @BeforeEach
    fun init() {

    }

    @Test
    fun calcDistance(){
        val distance = coordinatesCalculatorService.calcDistance(from, to)
        assertTrue(distance == 1112)
    }
}
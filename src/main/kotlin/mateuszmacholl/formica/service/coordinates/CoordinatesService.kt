package mateuszmacholl.formica.service.coordinates

import mateuszmacholl.formica.model.coordinates.Coordinates
import mateuszmacholl.formica.repo.CoordinatesRepo
import org.springframework.stereotype.Service

@Service
class CoordinatesService
constructor(private val coordinatesRepo: CoordinatesRepo){
    fun add(coordinates: Coordinates): Coordinates {
        return coordinatesRepo.save(coordinates)
    }
}
package mateuszmacholl.formica.repo

import mateuszmacholl.formica.model.coordinates.Coordinates
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CoordinatesRepo : JpaRepository<Coordinates, Int>
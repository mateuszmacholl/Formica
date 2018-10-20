package mateuszmacholl.formica.model.coordinates

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Coordinates(
        val latitude: Float,
        val longitude: Float
){
    @Id
    @GeneratedValue
    var id: Int? = null
}
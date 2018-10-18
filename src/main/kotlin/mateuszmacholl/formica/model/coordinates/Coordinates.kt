package mateuszmacholl.formica.model.coordinates

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Coordinates(
        val latitude: Float? = null,
        val longitude: Float? = null
){
    @Id
    @GeneratedValue
    var id: Int? = null
}
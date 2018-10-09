package mateuszmacholl.formica.model.token

import mateuszmacholl.formica.model.user.User
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class Token(
        var token: String? = null,

        @OneToOne
        @PrimaryKeyJoinColumn
        var user: User? = null
) {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = [Parameter(name = "property", value = "user")])
    var id: Int? = null

    var expirationDate = setDefaultExpirationDate()

    private fun setDefaultExpirationDate(): Date {
        val defaultExpirationDate = Calendar.getInstance()
        defaultExpirationDate.add(Calendar.MINUTE, 180)
        return defaultExpirationDate.time
    }

    fun hasExpired(): Boolean {
        return Date().after(this.expirationDate)
    }
}
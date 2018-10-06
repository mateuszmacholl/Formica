package mateuszmacholl.formica.model.user

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import java.util.*
import javax.persistence.*


@Entity
class PasswordResetToken {
    @Id
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters= [Parameter(name = "property", value = "user")])
    var id: Int? = null

    @Column(name = "token", unique = true)
    var token: String? = null

    @Column(name = "expirationDate")
    var expirationDate = setDefaultExpirationDate()

    @OneToOne
    @PrimaryKeyJoinColumn
    var user: User? = null

    constructor() {}

    private fun setDefaultExpirationDate(): Date {
        val defaultExpirationDate = Calendar.getInstance()
        defaultExpirationDate.add(Calendar.MINUTE, 180)
        return defaultExpirationDate.time
    }

    fun hasExpired(): Boolean {
        return Date().after(this.expirationDate)
    }

    constructor(token: String, user: User) {
        this.token = token
        this.user = user
    }
}

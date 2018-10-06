package mateuszmacholl.formica.model.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
data class User(
        var username: String? = null,

        var email: String? = null,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        var password: String? = null,

        @DateTimeFormat
        var creationDate: Calendar? = null,

        var enabled: Boolean? = null,

        @ElementCollection(fetch = FetchType.EAGER)
        var roles: MutableList<String> = mutableListOf(),

        @JsonIgnore
        @OneToOne(mappedBy = "user")
        var passwordResetToken: PasswordResetToken? = null,

        @JsonIgnore
        @OneToOne(mappedBy = "user")
        var verificationToken: VerificationToken? = null


) {
    @Id
    @GeneratedValue
    var id: Int? = null

    init {
        roles.add("user")
        enabled = false
        creationDate = Calendar.getInstance()
    }
}
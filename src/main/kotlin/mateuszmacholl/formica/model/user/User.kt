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
        var password: String? = null
) {
    @Id
    @GeneratedValue
    var id: Int? = null

    var enabled: Boolean = false

    @DateTimeFormat
    var creationDate: Calendar = Calendar.getInstance()

    @ElementCollection(fetch = FetchType.EAGER)
    var roles: MutableList<String> = mutableListOf()

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    var passwordResetToken: PasswordResetToken? = null

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    var verificationToken: VerificationToken? = null

    init {
        roles.add("user")
    }
}
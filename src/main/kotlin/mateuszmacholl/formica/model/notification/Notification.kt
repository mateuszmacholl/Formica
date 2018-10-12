package mateuszmacholl.formica.model.notification

import mateuszmacholl.formica.model.user.User
import java.util.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
abstract class Notification(
        @ManyToOne(fetch = FetchType.EAGER, targetEntity = User::class)
        @JoinColumn(name = "user_id")
        var notifiedUser: User? = null,
        val type: String
) {
    @Id
    @GeneratedValue
    var id: Int? = null
    var seen: Boolean = false
    var creationDate: Calendar = Calendar.getInstance()
}
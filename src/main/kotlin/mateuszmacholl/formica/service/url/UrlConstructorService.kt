package mateuszmacholl.formica.service.url

import org.springframework.stereotype.Service

@Service
class UrlConstructorService {
    fun constructWithToken(clientUrl: String, token: String): String {
        return "$clientUrl?token=$token"
    }
}

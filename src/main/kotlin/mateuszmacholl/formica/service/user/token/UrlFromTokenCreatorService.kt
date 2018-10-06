package mateuszmacholl.formica.service.user.token

import org.springframework.stereotype.Service

@Service
class UrlFromTokenCreatorService {
    fun create(clientUrl: String?, token: String?): String {
        if (clientUrl == null || token == null) {
            throw IllegalArgumentException()
        }
        return "$clientUrl?token=$token"
    }
}

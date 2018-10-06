package mateuszmacholl.formica.integrationTest.helper

import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class JwtHelper {
    static ResponseEntity performLogin(restTemplate) {
        def username = 'd_enabled_user'
        def password = 'root'
        Map body = [
                username: username,
                password: password
        ]
        return restTemplate.postForEntity('/auth/login', body, String.class)
    }

    static MultiValueMap<String, String> getAuthHeader(ResponseEntity response){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>()
        headers.add("Content-Type", "application/json")
        headers.add("Authorization", response.headers.get("Authorization")[0])
        return headers
    }
}

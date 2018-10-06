package mateuszmacholl.formica.config.cors

import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletResponse

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
class CORS2Filter : Filter {

    override fun init(filterConfig: FilterConfig) {

    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val response = servletResponse as HttpServletResponse
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200")
        response.setHeader("Access-Control-Expose-Headers", "Authorization")
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT")
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization")
        filterChain.doFilter(servletRequest, servletResponse)
    }

    override fun destroy() {

    }

}

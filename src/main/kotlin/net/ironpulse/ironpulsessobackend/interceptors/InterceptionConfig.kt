package net.ironpulse.ironpulsessobackend.interceptors

import net.ironpulse.ironpulsessobackend.services.IUserService
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorConfig(
    private val userService: IUserService,
    private val cacheManager: CacheManager
): WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AuthorizationInterceptor(userService, cacheManager))
    }
}
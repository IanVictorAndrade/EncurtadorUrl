package tjro.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import tjro.entidade.Usuario

class UserDetail(
    private val usuario: Usuario
): UserDetails {


    override fun getAuthorities() = null

    override fun getPassword() = usuario.senha

    override fun getUsername() = usuario.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
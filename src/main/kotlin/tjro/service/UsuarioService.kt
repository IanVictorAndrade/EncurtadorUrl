package tjro.service


import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import tjro.dto.AtualizaUsuario
import tjro.dto.DadosUsuarios
import tjro.dto.UsuarioView
import tjro.entidade.Usuario
import tjro.mapper.UsuarioMapper
import tjro.repositorio.UsuarioRepository
import java.util.*
import java.util.stream.Collectors
import java.util.UUID
import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Service
class UsuarioService (
    private val usuarioRepository: UsuarioRepository,
    private val usuarioMapper: UsuarioMapper,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    @Autowired
    private val javaMailSender: JavaMailSender? = null

    @Value("\${spring.mail.username}")
    private val sender: String? = null


    fun listar(): List<UsuarioView> {
        return usuarioRepository.findAll().stream().map { t -> usuarioMapper.map(t) }.collect(Collectors.toList())
    }

    fun buscarPorId(id: Long): Optional<Usuario> {
        return usuarioRepository.findById(id)
    }

    fun edita(edit: AtualizaUsuario) {
        val usuario = usuarioRepository.findById(edit.id).orElseThrow { NotFoundException() }
        usuario.email = edit.email
        val senhaCriptografada = passwordEncoder.encode(edit.senha)
        usuario.senha = senhaCriptografada
    }

    fun deletar(id: Long) {
        return usuarioRepository.deleteById(id)
    }

    fun cadastrar(dados: DadosUsuarios) {

        var usuario = Usuario(0, "", "", "")


        if (dados.id != 0.toLong()) {
            throw RuntimeException("campo id tem que ser 0")
        }



        usuario.id = dados.id
        usuario.email = dados.email
        val senhaCriptografada = passwordEncoder.encode(dados.senha)
        usuario.senha = senhaCriptografada


        val existeUsuario = usuarioRepository.encontreEmail(dados.email)
        if (existeUsuario == null) {
            usuarioRepository.save(usuario)
        }
    }

    fun redefinirSenha(token: String, novaSenha: String): String {
        val usuario: Usuario = usuarioRepository.findByToken(token)
        // Verificar se o token é válido e está associado ao email fornecido
        if (usuario.token != token) {
            throw RuntimeException("Token inválido!")
        }
        val senhaCriptografada = passwordEncoder.encode(novaSenha)
        usuario.senha = senhaCriptografada
        usuarioRepository.save(usuario)
        val objectMapper = ObjectMapper()
        val jsonResponse = objectMapper.writeValueAsString("Senha Alterada com Sucesso!")
        return jsonResponse
    }

    fun enviandoEmailDeRecuperacao(email: String): String {
        val emailConvertido = ObjectMapper().readTree(email)["email"].asText()

        val usuario = usuarioRepository.findByEmail(emailConvertido)

        if (usuario != null) {
            val token = UUID.randomUUID().toString()
            usuario.token = token
            usuarioRepository.save(usuario)


            return try {
                val mailMessage = SimpleMailMessage()

                mailMessage.from = sender
                mailMessage.setTo(emailConvertido)
                mailMessage.subject = "Recuperação de Senha"
                mailMessage.text = "Seu token de recuperação é: $token"

                javaMailSender!!.send(mailMessage)
                val objectMapper = ObjectMapper()
                val jsonResponse = objectMapper.writeValueAsString("E-mail enviado com Sucesso!")
                jsonResponse

            } catch (e: Exception) {
                "Erro ao enviar o e-mail"
            }
        } else {
            throw RuntimeException("Usuário não existe")
        }


    }


}
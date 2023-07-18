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
import org.springframework.mail.javamail.JavaMailSender


@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val usuarioMapper: UsuarioMapper,
    private val mailSender: JavaMailSender
) {


    fun listar(): List<UsuarioView> {
        return usuarioRepository.findAll().stream().map { t -> usuarioMapper.map(t) }.collect(Collectors.toList())
    }

    fun buscarPorId(id: Long): Optional<Usuario> {
        return usuarioRepository.findById(id)
    }

    fun edita(edit: AtualizaUsuario) {
        val usuario = usuarioRepository.findById(edit.id).orElseThrow { NotFoundException() }
        usuario.email = edit.email
        usuario.senha = edit.senha
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
        usuario.senha = dados.senha


        val existeUsuario = usuarioRepository.encontreEmail(dados.email)
        if (existeUsuario == null) {
            usuarioRepository.save(usuario)
        }
    }

    fun gerarTokenRecuperacao(email: String): String {
        var usuario: Usuario? = usuarioRepository.findByEmail(email)

        var token = UUID.randomUUID().toString()

        if (usuario != null) {
            usuario.token = token
            usuarioRepository.save(usuario)

            // Enviar e-mail com o token de recuperação
            enviarEmailRecuperacao(email, token)
        } else {
            throw RuntimeException("Usuário não existe")
        }

        return token
    }

    fun redefinirSenha(token: String, novaSenha: String): String {
        val usuario: Usuario = usuarioRepository.findByToken(token)
        // Verificar se o token é válido e está associado ao email fornecido
        if (usuario.token != token) {
            throw RuntimeException("Token inválido!")
        }

        usuario.senha = novaSenha
        usuarioRepository.save(usuario)

        return "Senha Alterada com Sucesso!"
    }

    private fun enviarEmailRecuperacao(email: String, token: String) {

        val message:MimeMessage = mailSender.createMimeMessage()
        message.setFrom(InternetAddress("contaApiRestTest@gmail.com")) // remetente
        message.setRecipient(Message.RecipientType.TO, InternetAddress(email))
        message.subject = "Recuperação de Senha"
        message.setText("Seu token de recuperação é: $token")

        Transport.send(message)
    }



}
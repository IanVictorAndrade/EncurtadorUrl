//package tjro.service
//
//// Importing required classes
//import jakarta.mail.MessagingException
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.core.io.FileSystemResource
//import org.springframework.mail.SimpleMailMessage
//import org.springframework.mail.javamail.JavaMailSender
//import org.springframework.mail.javamail.MimeMessageHelper
//import org.springframework.stereotype.Service
//import tjro.requests.GerarTokenRequest
//import java.io.File
//
//// Annotation
//@Service
//// Class
//// Implementing EmailService interface
//class EmailServiceParteDois : EmailService {
//    @Autowired
//    private val javaMailSender: JavaMailSender? = null
//
//    @Value("\${spring.mail.username}")
//    private val sender: String? = null
//
//    // Method 1
//    // To send a simple email
//    override fun sendSimpleMail(email: GerarTokenRequest): String {
//        // Try block to check for exceptions
//        return try {
//            // Creating a simple mail message
//            val mailMessage = SimpleMailMessage()
//
//            // Setting up necessary details
//            mailMessage.from = sender
//            mailMessage.setTo(email.email)
//            mailMessage.subject = "Recuperação de Senha"
//            mailMessage.setText("Seu token de recuperação é: $token")
//
//            // Sending the mail
//            javaMailSender!!.send(mailMessage)
//            "Mail Sent Successfully..."
//        } catch (e: Exception) {
//            "Error while Sending Mail"
//        }
//    }
//
//
//}

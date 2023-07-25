//package tjro.controller
//// Importing required classes
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RestController
//import tjro.service.EmailDetails
//import tjro.service.EmailService
//
//// Annotation
//@RestController // Class
//
//class EmailController {
//    @Autowired
//    private val emailService: EmailService? = null
//
//    // Sending a simple Email
//    @PostMapping("/sendMail")
//    fun sendMail(@RequestBody details: EmailDetails?): String? {
//        return emailService!!.sendSimpleMail(details)
//    }
//
//    // Sending email with attachment
//    @PostMapping("/sendMailWithAttachment")
//    fun sendMailWithAttachment(
//        @RequestBody details: EmailDetails?
//    ): String? {
//        return emailService!!.sendMailWithAttachment(details!!)
//    }
//}
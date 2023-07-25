package tjro.service
// Importing required classes
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import javax.xml.crypto.Data

// Annotations
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor // Class

class EmailDetails {
    // Class data members
    val recipient: String? = null
    val msgBody: String? = null
    val subject: String = "Simple Email Message"
    val attachment: String? = null
}
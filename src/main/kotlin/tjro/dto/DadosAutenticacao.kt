package tjro.dto


data class DadosAutenticacao(
    val email: String,
    val senha: String
) {
    // Construtor padrão vazio
    constructor() : this("", "")
}
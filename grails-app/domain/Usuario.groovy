import grails.plugin.bcrypt.BCrypt

class Usuario {
    Date dateCreated
    Date lastUpdated

    String senha
    String confirmacaoDeSenha
    String nome
    String email
    Boolean admin = false

    static transients = ["confirmacaoDeSenha"]

    static hasMany = [papeis: Papel]
    static hasOne = [esqueciMinhaSenha: EsqueciMinhaSenha]

    static constraints = {
        nome()
        email email: true
        senha password: true
        confirmacaoDeSenha bindable: true
        senha validator: { val, obj ->
            if (!obj.id && val != obj.confirmacaoDeSenha) return ['senhaNaoConfere']
        }
        admin bindable: false
        esqueciMinhaSenha nullable: true
    }

    def beforeInsert() {
        encripteSenha()
    }

    String toString() {
        email
    }

    boolean podeAcessar(String funcionalidade) {
        funcionalidade && (admin || papeis.any { it.podeAcessar(funcionalidade) })
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Usuario usuario = (Usuario) o

        if (email != usuario.email) return false

        return true
    }

    int hashCode() {
        return email.hashCode()
    }

    boolean alterarSenha(String senhaAtual, String novaSenha, String confirmacaoNovaSenha) {
        if (BCrypt.checkpw(senhaAtual, senha) && novaSenha == confirmacaoNovaSenha) {
            senha = novaSenha
            encripteSenha()
        } else {
            false
        }
    }

    private encripteSenha() {
        confirmacaoDeSenha = senha = BCrypt.hashpw(senha, BCrypt.gensalt())
    }

    void gerarEsqueciMinhaSenha() {
        esqueciMinhaSenha =  EsqueciMinhaSenha.build()
        save()
    }
}

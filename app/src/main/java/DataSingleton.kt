object DataSingleton {
    lateinit var login: String
        private set
    fun SetLogin(l: String) {
        login = l
    }
    fun GetLogin() : String {
        return login
    }

    lateinit var password: String
        private set
    fun SetPassword(p: String) {
        password = p
    }
    fun GetPassword() : String {
        return password
    }
}
package space.cloud4b.ctw.model

class User(userName : String) {
    var userName = userName



    fun getUserNamen() : String {
        return userName.trim()
    }
}
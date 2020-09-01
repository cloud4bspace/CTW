package space.cloud4b.ctw.model

/**
 * Ein object in Kotlin entspricht der Implementierung eines Singleton und ist Thread-safe
 *
 * Dieses Objekt wird verwendet bei der Erfassung eines neuen Termins/Anlasses und sammelt
 * die User-Eingaben über die 4 Eingabe-Fragments hinweg, bevor diese gespeichert werden.
 *
 * @author Serge Kaulitz & Bernhard Kämpf
 */
object NewCakeboardEntry {
    var tac: String = ""
    var userEmail: String = ""
    var date: String = ""
    var time: String = ""
    var reason: String = ""
    var infos: String = ""
    var beverages: String = ""
}
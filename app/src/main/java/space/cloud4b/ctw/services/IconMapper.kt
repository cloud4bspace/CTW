package space.cloud4b.ctw.services

/**
 * Die Klasse dient der Zuordnung von Icons zu den übergebenen Suchstrings
 *
 * @author Serge Kaulitz & Bernhard Kämpf
 */
class IconMapper {

    /**
     * Gibt zum übergebenen String die Bezeichnung des Icons zurück.
     * @param string Suchstring
     * @return string Bezeichnung des Icons
     */
    fun getIcnName(string : String) : String {
        when (string) {
            "Wein" -> return "icn_wine"
            "Limonade" -> return "icn_food"
            "Bier" -> return "icn_beers"
            "Cocktails" -> return "icn_cocktail"
            "Champagner" -> return "icn_champagne"
            "Tee" -> return "icn_bubbletea"
            "Kaffee" -> return "icn_coffeecup"
            "Mineral" -> return "icn_mineralwater"
            "Gipfeli" -> return "icn_croissant"
            "Kuchen" -> return "icn_tart"
            "Chips" -> return "icn_chips"
            "Sandwich" -> return "icn_sandwich"
            "Gemüse" -> return "icn_vegetable"
            "Sushi" -> return "icn_sushi"
            "Früchte" -> return "icn_fruits"
            "Burger" -> return "icn_burger"
            "Ferien" -> return "icn_beach"
            "Geburtstag" -> return "icn_birthdaycake"
            "Geheimnis" -> return "icn_secret"
            "Abschied" -> return "icn_goodbye"
            "Nachwuchs" -> return "icn_schnuller"
            "Abschluss" -> return "icn_graduation"
            "Hochzeit" -> return "icn_wedding"
            "Auszeichnung" -> return "icn_medal"
            "Beförderung" -> return "icn_motivation"
            "Pensionierung" -> return "icn_retirement"
            "Lottogewinn" -> return "icn_jackpot"
            "einfach so" -> return "icn_dinosaur"
            "1" -> return "icn_am"
            "2" -> return "icn_pm"
            "3" -> return "icn_afternoon"
            "male" -> return "icn_male"
            "female" -> return "icn_female"
            else -> return "icn_secret"
        }
    }
    fun getText(string : String) : String {
        when (string) {
            "1" -> return "Znüni (Vormittag)"
            "2" -> return "Zvieri (Nachmittag)"
            "3" -> return "Apéro"
            else -> return "???"
        }
    }
}
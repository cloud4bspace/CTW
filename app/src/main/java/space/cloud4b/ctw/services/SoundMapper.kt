package space.cloud4b.ctw.services

class SoundMapper {

    fun getSoundName(string : String) : String {
        when (string) {
            "Ferien" -> return "icn_beach"
            "Geburtstag" -> return "birthday.mp3"
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
            "1" -> return "icn_am"
            "2" -> return "icn_pm"
            "3" -> return "icn_afternoon"
            "male" -> return "icn_male"
            "female" -> return "icn_female"
            else -> return "birthday.mp3"
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
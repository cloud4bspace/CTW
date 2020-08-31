package space.cloud4b.ctw.model

import java.time.LocalDate

/**
 * Ein Objekt des Typs CakeboardEntry entspricht grundsätzlich einem Datensatz
 * der MySQL-Tabelle usr_web116_5.ctwList und stellt einen Termin dar für einen angemeldeten
 * Event (Znüni, Apéro). Die deklarierten Properties entsprechen exakt den Feldnamen
 * der zugrundeliegenden Tabelle (die Properties beginnen aus diesem Grund mit einem
 * Grossbuchstaben, was sonst nicht üblich wäre).
 *
 * Die Objekte dieses Typs werden in der Klasse Cakeboard in eine Liste eingetragen und
 * werden vom CakeboardAdapter genutzt, um entsprechende JSON-Einträge für die
 * ListView aufzubereiten.
 *
 * @author Serge Kaulitz & Bernhard Kämpf
 */
class CakeboardEntry(var ListId : String, var MemberEmail: String, var ListDate : String,
                     var ListDaytime : String, var MemberName : String, var MemberAlias : String,
                     var MemberAvatar : String, var ListReason : String,
                     var ListFoodAndBev : String, var ListDescription : String,
                     var ListImage : String, var ListAngemeldet : String, var ListAbgemeldet : String,
                     var ListUnentschlossen : String, var MemberID : String, var MemberTeamId : String) {
    public var entryId = ListId.toInt()
    public var entryDate : LocalDate = LocalDate.parse(ListDate)
    var memberId = MemberID.toInt()
    var memberTeamId = MemberTeamId.toInt()
}
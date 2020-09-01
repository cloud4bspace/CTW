package space.cloud4b.ctw.model

/**
 * Ein Objekt des Typs TeamMember entspricht grundsätzlich einem Datensatz
 * der MySQL-Tabelle usr_web116_5.ctwMember und stellt ein Teammitglied dar.
 * Die deklarierten Properties entsprechen exakt den Feldnamen der zugrundeliegenden Tabelle
 * (die Properties beginnen aus diesem Grund mit einem Grossbuchstaben, was sonst nicht
 * üblich wäre).
 *
 * Die Objekte dieses Typs werden in der Klasse Team in eine Liste eingetragen und
 * werden vom TeamlistAdapter genutzt, um entsprechende JSON-Einträge für die
 * ListView aufzubereiten.
 *
 * @author Serge Kaulitz & Bernhard Kämpf
 */
class TeamMember(var MemberID : String, var MemberTeamId : String, var MemberEmail : String,
                 var MemberName : String, var MemberAlias : String, var MemberAvatar : String,
                 var MemberAdmin : String, var TeamBezeichnung : String, var TeamOrt : String,
                 var CompBezeichnung : String, var CompSitz : String) {
}
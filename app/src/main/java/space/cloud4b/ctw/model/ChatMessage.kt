package space.cloud4b.ctw.model

/**
 * Ein Objekt des Typs ChatMessage entspricht grundsätzlich einem Datensatz
 * der MySQL-Tabelle usr_web116_5.ctwChat und stellt eine Nachricht im Teamchat. Die deklarierten
 * Properties entsprechen exakt den Feldnamen der zugrundeliegenden Tabelle (die Properties
 * beginnen aus diesem Grund mit einem Grossbuchstaben, was sonst nicht üblich wäre.
 *
 * Die Objekte dieses Typs werden in der Klasse Chat in eine Liste eingetragen und
 * werden vom CChatlistAdapter genutzt, um entsprechende JSON-Einträge für die
 * ListView aufzubereiten.
 *
 * @author Serge Kaulitz & Bernhard Kämpf
 */
class ChatMessage(var ChatID : String, var MemberEmail : String, var MemberName : String,
                  var MemberAlias : String, var MemberAvatar : String, var ChatMessage : String,
                  var ChatTimestamp : String, var TeamBezeichnung : String) {

}
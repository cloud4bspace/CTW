package space.cloud4b.ctw.model

import java.time.LocalDate

/**
 * Ein Objekt des Typs DoodleResponse entspricht grundsätzlich einem Datensatz
 * der MySQL-Tabelle usr_web116_5.ctwRegistration und stellt eine An- oder Abmeldung dar für einen
 * Event (Znüni, Apéro). Die deklarierten Properties entsprechen exakt den Feldnamen
 * der zugrundeliegenden Tabelle (die Properties beginnen aus diesem Grund mit einem
 * Grossbuchstaben, was sonst nicht üblich wäre).
 *
 * Die Objekte dieses Typs werden in der Klasse Doodle in eine Liste eingetragen und
 * werden vom DoodleAdapter genutzt, um entsprechende JSON-Einträge für die
 * ListView aufzubereiten.
 *
 * @author Serge Kaulitz & Bernhard Kämpf
 */
class DoodleResponse(var MemberName : String, var MemberAlias : String, var MemberAvatar : String,
                     var RegistrationAngemeldet : String, var RegistrationAbgemeldet : String) {

}
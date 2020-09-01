package space.cloud4b.ctw.model

/**
 * Für den aktuellen Monat wird aus der Datenbank eine Auswertung generiert mit der Anzahl
 * Anlässe (Znüni, Apéro etc.) pro Grund (Geburtstag, Jubiläum etc.).
 * Ein Objekt des Typs MonthlyReason entspricht einer Zeile dieser Auswertung.
 * Die deklarierten Properties entsprechen exakt den Feldnamen der zugrundeliegenden Tabelle
 * (die Properties beginnen aus diesem Grund mit einem Grossbuchstaben,
 * was sonst nicht üblich wäre).
 *
 * Die Objekte dieses Typs werden in der Klasse Monthlyboard in eine Liste eingetragen und
 * werden vom MonthlyboardAdapter genutzt, um entsprechende JSON-Einträge für die
 * ListView aufzubereiten (wird im Dashboard angezeigt).
 *
 * @author Serge Kaulitz & Bernhard Kämpf
 */
class MonthlyReason(var ListReason : String, var Anzahl : String) {

}
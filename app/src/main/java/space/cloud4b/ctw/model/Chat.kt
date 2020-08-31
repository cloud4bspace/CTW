package space.cloud4b.ctw.model

/**
 * Die Klasse beinhaltet eine Liste von Objekten des Typs Chat
 * und wird für den ChatlistAdapter genutzt, um entsprechende JSON-Einträge für die
 * ListView aufzubereiten.
 * @author Serge Kaulitz & Bernhard Kämpf
 */
class Chat(val chatmessages : MutableList<ChatMessage>) {
}
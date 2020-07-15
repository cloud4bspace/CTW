package space.cloud4b.ctw.model

import java.time.LocalDate

class CakeboardEntry(var ListId : String, var MemberEmail: String, var ListDate : String,
                     var MemberName : String, var ListReason : String, var ListDescription : String,
                     var ListImage : String, var MemberID : String, var MemberTeamId : String) {
    public var entryId = ListId.toInt()
    public var entryDate : LocalDate = LocalDate.parse(ListDate)
    var memberId = MemberID.toInt()
    var memberTeamId = MemberTeamId.toInt()


    fun irgendwas() : String {
        return "hallo"
    }






}
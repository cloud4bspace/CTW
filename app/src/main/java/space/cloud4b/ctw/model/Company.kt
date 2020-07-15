package space.cloud4b.ctw.model

import java.time.LocalDate

class Company(var CompId : String, var CompBezeichnung : String, var CompSitz : String,
              CompLogo : String) {
    var compId = CompId.toInt()

}
package com.adpratap11.dmrcp1

class model {

    var Image_url : String? = null
    var Location : String?= null
    var Timedate : String?=null
    var Remarks : String? = null
    var Name :String? = null

    constructor()

    constructor(Image_url: String?, Location: String?, Timedate: String?, Remarks: String?, Name: String?) {
        this.Image_url = Image_url
        this.Location = Location
        this.Timedate = Timedate
        this.Remarks = Remarks
        this.Name = Name
    }

}
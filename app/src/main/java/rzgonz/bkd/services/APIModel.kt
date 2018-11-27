package kodigo.rzgonz.id.rzkotlin.API

import com.google.gson.annotations.Expose

/**
 * Created by rzgonz on 8/2/17.
 */

class APIModel {
    var status: String = ""
    var message: String = ""
    @Expose
    var data : Any = null!!
    override fun toString(): String {
        return "APIModel(status='$status', message='$message', data='$data')"
    }


}
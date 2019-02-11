package rzgonz.bkd.modules.profile

import rzgonz.bkd.models.profile.UserProfileResponse
import rzgonz.bkd.models.provinsi.ProvinsiResponse
import rzgonz.core.kotlin.contract.DIBaseContract
/**
 * Created by rzgonz on 7/10/17.
 */

object EditProfileContract  {

    interface View : DIBaseContract.View {
            fun returnProfile(status : Boolean,responde:UserProfileResponse?,message:String)
            fun returnEditAkun(status : Boolean,responde:String?,message:String)
            fun returnProvinsi(status: Boolean,responde: ProvinsiResponse?,message: String)

    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getProfile()
        fun getProvinsi()
        fun postEditAkun(fullname:String,email:String,tlp:String,nomer_rekening:String,nama_bank:String)
        fun postEditAlamat(member_id:String,alamat:String,kota:String,provinsi:String,kodepos:String)
        fun postEditPassword(member_id:String,old_pass:String,pass:String,conf:String)

    }
}
package rzgonz.bkd.modules.daftar.kilat.datadiri

import rzgonz.core.kotlin.contract.DIBaseContract

object DaftarKilatDataDiriContract  {

    interface View : DIBaseContract.View {
        fun returnSendDataDiri(status : Boolean, responde: String?, message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {
        fun sendDataDiri(pendidikan:String,nama_perusahaan:String,telp_perusahaan:String,status_karyawan:String,lama_bekerja:String,nama_atasan:String,referensi_1:String,referensi_2:String)

    }
}
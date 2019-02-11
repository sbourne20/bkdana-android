package rzgonz.bkd.modules.koran

import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.models.koran.KoranListResponse
import rzgonz.core.kotlin.contract.DIBaseContract
/**
 * Created by rzgonz on 7/10/17.
 */

object KoranContract  {

    interface View : DIBaseContract.View {
            fun returnKoranList(status : Boolean,responde:KoranListResponse?,message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getKoranList()
    }
}
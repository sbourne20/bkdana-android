package rzgonz.bkd.injector.redem

import dagger.Component
import rzgonz.bkd.injector.AppsComponent
import rzgonz.bkd.injector.topup.TopupModule
import rzgonz.bkd.modules.redem.RedemFragment
import rzgonz.bkd.modules.transaksi.TransaksiActivity
import rzgonz.bkd.modules.transaksi.TransaksiFragment
import rzgonz.bkd.modules.transaksi.detail.DetailTransaksiActivity

@Component(dependencies = [AppsComponent::class],modules = [RedemModule::class])
interface RedemComponent {
    fun inject(activity: RedemFragment)

}
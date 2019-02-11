package rzgonz.bkd.injector.topup

import dagger.Component
import rzgonz.bkd.injector.AppsComponent
import rzgonz.bkd.modules.topup.TopupFragment
import rzgonz.bkd.modules.transaksi.TransaksiActivity
import rzgonz.bkd.modules.transaksi.TransaksiFragment
import rzgonz.bkd.modules.transaksi.detail.DetailTransaksiActivity

@Component(dependencies = [AppsComponent::class],modules = [TopupModule::class])
interface TopupComponent {

    fun inject(activity: TopupFragment)
}
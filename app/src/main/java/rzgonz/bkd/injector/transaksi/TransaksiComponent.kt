package rzgonz.bkd.injector.transaksi

import dagger.Component
import rzgonz.bkd.injector.AppsComponent
import rzgonz.bkd.modules.transaksi.TransaksiActivity
import rzgonz.bkd.modules.transaksi.TransaksiFragment
import rzgonz.bkd.modules.transaksi.detail.DetailTransaksiActivity

@Component(dependencies = [AppsComponent::class],modules = [TransaksiModule::class])
interface TransaksiComponent {

    fun inject(activity: TransaksiActivity)
    fun inject(activity: TransaksiFragment)
    fun inject(activity: DetailTransaksiActivity)
}
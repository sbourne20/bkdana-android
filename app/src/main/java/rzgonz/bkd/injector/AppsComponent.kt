package rzgonz.bkd.injector

import android.content.Context
import dagger.Component

@Component(modules = [AppsModule::class])
interface AppsComponent {

    fun context(): Context
}
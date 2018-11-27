package rzgonz.bkd.injector

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppsModule constructor(val context: Context) {

    @Provides //scope is not necessary for parameters stored within the module
    fun context(): Context {
        return context
    }

}
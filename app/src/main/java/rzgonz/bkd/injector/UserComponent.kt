package rzgonz.bkd.injector

import dagger.Component
import rzgonz.bkd.modules.MainActivity

@Component(dependencies = [AppsComponent::class],modules = [UserModule::class])
interface UserComponent {

    fun inject(activity: MainActivity)
}
package rzgonz.bkd.injector

import dagger.Component
import rzgonz.bkd.modules.Login.LoginActivity
import rzgonz.bkd.modules.MainActivity
import rzgonz.bkd.modules.splashscreen.HomeActivity

@Component(dependencies = [AppsComponent::class],modules = [UserModule::class])
interface UserComponent {

    fun inject(activity: LoginActivity)
    fun inject(activity: MainActivity)
}
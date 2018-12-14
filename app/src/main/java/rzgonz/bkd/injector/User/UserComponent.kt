package rzgonz.bkd.injector.User

import dagger.Component
import rzgonz.bkd.injector.AppsComponent
import rzgonz.bkd.modules.Login.LoginActivity
import rzgonz.bkd.modules.MainActivity
import rzgonz.bkd.modules.register.RegisterActivity

@Component(dependencies = [AppsComponent::class],modules = [UserModule::class])
interface UserComponent {

    fun inject(activity: LoginActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: RegisterActivity)
}
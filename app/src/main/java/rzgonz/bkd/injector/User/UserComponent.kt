package rzgonz.bkd.injector.User

import dagger.Component
import rzgonz.bkd.injector.AppsComponent
import rzgonz.bkd.modules.Login.LoginActivity
import rzgonz.bkd.modules.MainActivity
import rzgonz.bkd.modules.daftar.agri.agri1.DaftarAgriActivity
import rzgonz.bkd.modules.daftar.kilat.datadiri.DaftarKilatDataDiriActivity
import rzgonz.bkd.modules.daftar.kilat.pribadi.DaftarKilatActivity
import rzgonz.bkd.modules.daftar.kilat.upload.DaftarKilatUploadActivity
import rzgonz.bkd.modules.daftar.mikro.upload.DaftarMikroUploadActivity
import rzgonz.bkd.modules.daftar.mikro.usaha.DaftarMirkoUsahaActivity
import rzgonz.bkd.modules.daftar.mikro.pribadi.DaftarMikroActivity
import rzgonz.bkd.modules.password.ConfimPasswordActivity
import rzgonz.bkd.modules.register.RegisterActivity

@Component(dependencies = [AppsComponent::class],modules = [UserModule::class])
interface UserComponent {

    fun inject(activity: LoginActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: RegisterActivity)
    fun inject(activity: DaftarKilatActivity)
    fun inject(activity: DaftarKilatDataDiriActivity)
    fun inject(activity: DaftarKilatUploadActivity)
    fun inject(activity: DaftarMikroActivity)
    fun inject(activity: DaftarMirkoUsahaActivity)
    fun inject(activity: DaftarMikroUploadActivity)
    fun inject(activity: ConfimPasswordActivity)

    fun inject(activity: DaftarAgriActivity)



}
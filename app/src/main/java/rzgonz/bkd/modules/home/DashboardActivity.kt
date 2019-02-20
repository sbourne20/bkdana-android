package rzgonz.bkd.modules.home

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.bottomnavigation.LabelVisibilityMode
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.content_darsboard.*
import rzgonz.bkd.R
import rzgonz.bkd.constant.BKD
import rzgonz.bkd.models.user.UserContent
import rzgonz.bkd.modules.koran.KoranActivity
import rzgonz.bkd.modules.peminjam.PeminjamActivity
import rzgonz.bkd.modules.profile.EditProfileActivity
import rzgonz.bkd.modules.redem.RedemFragment
import rzgonz.bkd.modules.topup.TopupFragment
import rzgonz.bkd.modules.transaksi.TransaksiFragment
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.helper.SharedPreferenceService
import org.greenrobot.eventbus.EventBus
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.modules.QRCodeActivity
import android.widget.TextView
import rzgonz.bkd.models.dashboard.MySaldoResponse
import rzgonz.bkd.modules.Login.LoginActivity


class DashboardActivity : DIBaseActivity(), NavigationView.OnNavigationItemSelectedListener,DashboardContract.View {
    var username= ""
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, DashboardFragment.newInstance(username,"")).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_transaksi -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, TransaksiFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_topup -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, TopupFragment.newInstance("Toup")).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_menu -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return@OnNavigationItemSelectedListener false
            }
            R.id.navigation_tarik -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, RedemFragment.newInstance("Redem")).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            finishAffinity()
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item?.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }

    }

    val mPresenter = DashboardPresenter()

    override fun inject() {

    }

    override fun onAttachView() {
        mPresenter.attachView(this)
    }


    override fun onDetachView() {
        mPresenter.detachView()
    }

    override fun initLayout(): Int {
        if(SharedPreferenceService(applicationContext).getInt(BKD.LOGINTYPE,1) == 1){
            return R.layout.activity_dashboard_peminjam
        }else{
            return R.layout.activity_dashboard
        }

    }

    lateinit var navUsername: TextView
    lateinit var navSaldo: TextView

    override fun initUI(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        navigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId =  R.id.navigation_home


        val headerView = nav_view.getHeaderView(0)
        navUsername = headerView.findViewById<TextView>(R.id.tvName)
        navSaldo = headerView.findViewById<TextView>(R.id.tvSalod)

    }

    override fun onResume() {
        super.onResume()
        mPresenter.getMyData()
        mPresenter.getMySaldo()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.itemProfile -> {
               startActivity(Intent(baseContext,EditProfileActivity::class.java))
            }
            R.id.itemPeminjam -> {
                if(SharedPreferenceService(baseContext).getInt(BKD.LOGINTYPE,0)==2) {
                    startActivity(Intent(baseContext, PeminjamActivity::class.java))
                }else{
                    showMessage("Anda Login Sebagai Peminjam")
                }
            }
            R.id.itemKoran -> {
                startActivity(Intent(baseContext,KoranActivity::class.java))
            }
            R.id.itemQR -> {
                startActivity(Intent(baseContext,QRCodeActivity::class.java))
            }
            R.id.itemLogout -> {
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().clear().apply()
                finish()
                startActivity(Intent(baseContext,LoginActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun returnUser(status: Boolean, responde: UserContent?, message: String) {
        if(status){
            navUsername.setText(responde?.namaPengguna)
            username = responde?.namaPengguna!!
            EventBus.getDefault().post(responde)
            SharedPreferenceService(applicationContext).saveString("MEMBER_ID",responde?.member_id!!)
        }
    }

    override fun returnMySaldo(status: Boolean, responde: MySaldoResponse?, message: String) {
        if(status){
            SharedPreferenceService(applicationContext).saveString("SALDO",responde?.content?.Amount)
            navSaldo.setText(responde?.content?.Amount)
        }
    }


}

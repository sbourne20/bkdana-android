package rzgonz.bkd.modules.splashscreen

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer
import kotlinx.android.synthetic.main.activity_worktour.*
import rzgonz.bkd.R
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.view.CustomeViewPager

class WorktourActivity : DIBaseActivity(),CustomeViewPager.PagerListener,WorktourFragemnt.OnFragmentInteractionListener {

    override fun inject() {

    }

    override fun onAttachView() {

    }

    override fun onDetachView() {

    }

    override fun initLayout(): Int {
    return R.layout.activity_worktour
    }

    override fun initUI(savedInstanceState: Bundle?) {
        cvWorktour.listener = this
        cvWorktour.setAdapter(this)
        indicator.setViewPager(cvWorktour);
        cvWorktour.setPageTransformer(true,  ZoomOutSlideTransformer())

// optional
// .registerDataSetObserver(indicator.getDataSetObserver());
    }

    override fun initViewPager(): Int {
        return R.id.cvWorktour
    }

    override fun initViewTabLayout() {

    }

    override fun addFragment(): ArrayList<Fragment> {
        val items: ArrayList<Fragment> = ArrayList()
        items.add(WorktourFragemnt.newInstance("1","3"))
        items.add(WorktourFragemnt.newInstance("2","4"))
        items.add(WorktourFragemnt.newInstance("3","5"))
        return items
      }

    override fun onFragmentInteraction(uri: Uri) {
        Log.d("data","${uri}")
    }
}

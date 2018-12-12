package rzgonz.bkd.modules.peminjam.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rzgonz.bkd.R
import rzgonz.bkd.modules.peminjam.detial.DetailPinjamanActivity
import rzgonz.core.kotlin.adapter.BaseRVAdapter
import rzgonz.core.kotlin.holder.BaseItemHolder
import rzgonz.core.kotlin.model.RvPropertise
import java.util.ArrayList

class PeminjamAdapter(c: Context, items: ArrayList<Any>) : BaseRVAdapter(c, items) {

    override fun setRv(): RvPropertise {
        rvPropertise.hasFooter = false
        rvPropertise.hasHeadear = false
        rvPropertise.colomCount = 1
        rvPropertise.reverseLayout = false
        rvPropertise.hasRefresh = true
        rvPropertise.hasLoadmore = true
        return rvPropertise
    }


    override fun onBindViewHolderItem(holder: BaseItemHolder?, position: Int, positionData: Int) {
        if (holder is Item){
            holder.sentData(getItem(positionData) as String)
        }

    }

    override fun onCreateViewHolderItem(viewGroup: ViewGroup, viewType: Int): Item {
        var v = LayoutInflater.from(c).inflate(R.layout.cell_peminjam, viewGroup, false)
        return Item(v)
    }



    class Item(itemView: View) : BaseItemHolder(itemView) {


        val tvName =itemView.findViewById<TextView>(R.id.tvName)

        init {


        }

        fun sentData(s: String) {
            tvName.rootView.setOnClickListener {
                tvName.context.startActivity(Intent(tvName.context,DetailPinjamanActivity::class.java))
            }
        }


    }


}
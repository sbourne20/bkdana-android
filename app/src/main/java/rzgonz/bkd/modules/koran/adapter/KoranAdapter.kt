package rzgonz.bkd.modules.koran.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rzgonz.bkd.R
import rzgonz.bkd.models.koran.KorantItem
import rzgonz.core.kotlin.adapter.BaseRVAdapter
import rzgonz.core.kotlin.holder.BaseItemHolder
import rzgonz.core.kotlin.model.RvPropertise
import java.util.ArrayList

class KoranAdapter(c: Context, items: ArrayList<Any>) : BaseRVAdapter(c, items) {

    override fun setRv(): RvPropertise {
        rvPropertise.hasFooter = false
        rvPropertise.hasHeadear = false
        rvPropertise.colomCount = 1
        rvPropertise.reverseLayout = false
        rvPropertise.hasRefresh = true
        rvPropertise.hasLoadmore = false
        return rvPropertise
    }


    override fun onBindViewHolderItem(holder: BaseItemHolder?, position: Int, positionData: Int) {
        if (holder is Item){
            holder.sentData(getItem(positionData) as KorantItem)
        }

    }

    override fun onCreateViewHolderItem(viewGroup: ViewGroup, viewType: Int): Item {
        var v = LayoutInflater.from(c).inflate(R.layout.cell_koran, viewGroup, false)
        return Item(v)
    }



    class Item(itemView: View) : BaseItemHolder(itemView) {

        val tvJenis =itemView.findViewById<TextView>(R.id.tvJenis)
        val tvNo =itemView.findViewById<TextView>(R.id.tvNo)
        val tvTile =itemView.findViewById<TextView>(R.id.tvTitle)
        val tvTolal =itemView.findViewById<TextView>(R.id.tvTotal)
        val tvDate =itemView.findViewById<TextView>(R.id.tvDate)
        val tvLender =itemView.findViewById<TextView>(R.id.tvLender)

        fun sentData(korantItem: KorantItem) {
            if(korantItem.tipeDana.equals("1")){
                tvJenis.setText("Debit")
            }else{
                tvJenis.setText("Kredit")
            }
            tvNo.setText("No ${korantItem.id}")
            tvTile.setText(korantItem.notes)
            tvTolal.setText("${korantItem.amountDetail} IDR")
            tvDate.setText(korantItem.dateTransaction)


        }


    }


}
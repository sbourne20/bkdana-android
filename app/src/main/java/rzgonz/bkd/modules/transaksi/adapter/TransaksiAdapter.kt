package rzgonz.bkd.modules.transaksi.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder
import rzgonz.bkd.R
import rzgonz.bkd.models.transaksi.ListTransaksiItem
import rzgonz.bkd.modules.transaksi.TransaksiActivity
import rzgonz.bkd.modules.transaksi.detail.DetailTransaksiActivity
import rzgonz.core.kotlin.adapter.BaseRVAdapter
import rzgonz.core.kotlin.holder.BaseItemHolder
import rzgonz.core.kotlin.model.RvPropertise
import java.util.ArrayList

class TransaksiAdapter(c: Context, items: ArrayList<Any>) : BaseRVAdapter(c, items) {

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
            holder.sentData(getItem(positionData) as ListTransaksiItem)
        }

    }

    override fun onCreateViewHolderItem(viewGroup: ViewGroup, viewType: Int): Item {
        val v = LayoutInflater.from(c).inflate(R.layout.cell_transaksi, viewGroup, false)
        return Item(v)
    }



    class Item(itemView: View) : BaseItemHolder(itemView) {

        var tvNoTransaksi:TextView
         var tvJenis:TextView
         var tvNamaTransaksi:TextView
         var tvJumlah:TextView
         var tvTotal:TextView
         var tvStatus:TextView

        init {
            tvNoTransaksi =itemView.findViewById(R.id.tvNoTransaksi)
            tvJenis =itemView.findViewById(R.id.tvJenis)
            tvNamaTransaksi =itemView.findViewById(R.id.tvNamaTransaksi)
            tvJumlah =itemView.findViewById(R.id.tvJumlah)
            tvTotal =itemView.findViewById(R.id.tvTotal)
            tvStatus =itemView.findViewById(R.id.tvStatus)

        }
        fun sentData(listTransaksiItem: ListTransaksiItem) {
            tvNoTransaksi.setText(listTransaksiItem.transaksiId)
            tvNamaTransaksi.setText(listTransaksiItem.productTitle)
            tvJenis.setText(listTransaksiItem.typeBusinessName)
            tvJumlah.setText(listTransaksiItem.totalrp)
            tvTotal.setText(listTransaksiItem.totalApprove)
            tvStatus.setText(listTransaksiItem.transaksiStatus)

            tvStatus.rootView.setOnClickListener {
                it.context.startActivity(Intent(it.context,DetailTransaksiActivity::class.java).putExtra(DETIAL,listTransaksiItem))
            }
        }

    }
    companion object {
        val DETIAL = "DETIAL"
    }
}
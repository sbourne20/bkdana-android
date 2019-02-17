package rzgonz.bkd.modules.peminjam.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import rzgonz.bkd.R
import rzgonz.bkd.models.peminjam.ListPeminjamItem
import rzgonz.bkd.modules.peminjam.detial.DetailPinjamanActivity
import rzgonz.core.kotlin.adapter.BaseRVAdapter
import rzgonz.core.kotlin.holder.BaseItemHolder
import rzgonz.core.kotlin.model.RvPropertise
import java.util.ArrayList
import kotlin.math.roundToInt

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
            holder.sentData(getItem(positionData) as ListPeminjamItem)
        }

    }

    override fun onCreateViewHolderItem(viewGroup: ViewGroup, viewType: Int): Item {
        val v = LayoutInflater.from(c).inflate(R.layout.cell_peminjam, viewGroup, false)
        return Item(v)
    }



    class Item(itemView: View) : BaseItemHolder(itemView) {


        val tvName =itemView.findViewById<TextView>(R.id.tvName)
        val tvTenor =itemView.findViewById<TextView>(R.id.tvTenor)
        val tvNoTransaksi =itemView.findViewById<TextView>(R.id.tvNoTransaksi)
        val tvTotalPinjaman =itemView.findViewById<TextView>(R.id.tvTotalPinjaman)
        val tvTotalDana =itemView.findViewById<TextView>(R.id.tvTotalDana)
        val tvGrade =itemView.findViewById<TextView>(R.id.tvGrade)
        val tvLender =itemView.findViewById<TextView>(R.id.tvLender)
        val progressBar =itemView.findViewById<ProgressBar>(R.id.progressBar)
        val tvProgress =itemView.findViewById<TextView>(R.id.tvProgress)
        val btnDetail =itemView.findViewById<Button>(R.id.btnDetail)

        fun sentData(s: ListPeminjamItem) {
            tvName.setText(s.namaPeminjam)
            tvTenor.setText(s.loanTerm)
            tvNoTransaksi.setText(s.transaksiId)
            tvTotalPinjaman.setText(s.totalPinjam)
            tvTotalDana.setText(s.totalApprove)
            tvGrade.setText(s.peringkatPengguna)
            tvLender.setText(s.totalLender)

            val prog = (s.totalApprove?.toDouble()!!.times(100).div(s.totalPinjam?.toDouble()!!)).roundToInt()

            tvProgress.setText("${prog}%")
            progressBar.progress = (prog)
            btnDetail.setOnClickListener {
                tvName.context.startActivity(Intent(tvName.context,DetailPinjamanActivity::class.java).putExtra("ID",s))
            }
        }

    }


}
package rzgonz.bkd.modules.transaksi.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import rzgonz.bkd.R
import rzgonz.bkd.models.transaksi.detail.LogPinjaman
import rzgonz.bkd.models.transaksi.detail.RepaymentListItem

/**
 * Code taken from https://developer.android.com/training/material/lists-cards.html and modified
 * according to need.
 */
class TransaksiDetailAdapter(val mDataset: ArrayList<RepaymentListItem>) : RecyclerView.Adapter<TransaksiDetailAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var imgIcon = v.findViewById<ImageView>(R.id.imgIcon)
        var tvStatus = v.findViewById<TextView>(R.id.tvStatus)
        var tvTgl = v.findViewById<TextView>(R.id.tvTgl)
        var tvCicilan = v.findViewById<TextView>(R.id.tvCicilan)
        var tvSetoran = v.findViewById<TextView>(R.id.tvSetoran)

        fun bindData(position: Int,data: RepaymentListItem) {
            tvTgl.setText(data.jatuhTempo)
            tvCicilan.setText("Angsuran Ke ${position+1}")
            tvSetoran.setText(data.nominalCicilan)
            tvStatus.setText("Status : ${data.status}")
            if(data.status.equals("lunas",true)){
                imgIcon.setImageResource(R.drawable.ic_check)
                imgIcon.setBackgroundResource(R.drawable.ellipse_enable)
            }else{
                imgIcon.setImageResource(R.drawable.ic_clipboard)
                imgIcon.setBackgroundResource(R.drawable.ellipse_disable)
            }

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TransaksiDetailAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_riwayat, parent, false)
        // set the view's size, margins, paddings and layout parameters
        //
        return ViewHolder(v)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       // holder.mTextView.text = mDataset[position]
        holder.bindData(position,mDataset.get(position))
//        if (position % 5 == 0) {
//            // just add some visual variation after some items
//            holder.mTextView.setTypeface(null, Typeface.BOLD)
//            holder.mTextView.setTextColor(Color.BLUE)
//        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return mDataset.size
    }
}
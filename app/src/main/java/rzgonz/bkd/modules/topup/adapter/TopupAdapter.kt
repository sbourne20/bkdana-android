package rzgonz.bkd.modules.topup.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rzgonz.bkd.R
import rzgonz.bkd.models.topup.ContentItem

/**
 * Code taken from https://developer.android.com/training/material/lists-cards.html and modified
 * according to need.
 */
class TopupAdapter(val mDataset: ArrayList<ContentItem?> = ArrayList()) : RecyclerView.Adapter<TopupAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var tvNoTransaksi = v.findViewById<TextView>(R.id.tvNoTransaksi)
        var tvDate = v.findViewById<TextView>(R.id.tvDate)
        var tvStatus = v.findViewById<TextView>(R.id.tvStatus)
        var tvTotal = v.findViewById<TextView>(R.id.tvTotal)
        var card = v.findViewById<CardView>(R.id.card)

        fun bindData(data: ContentItem?) {
            tvNoTransaksi.setText(data?.kodeTopUp)
            tvDate.setText(data?.tglTopUp)
            tvTotal.setText(data?.jmlTopUp)
            tvStatus.setText(data?.statusTopUp)
            if(data?.statusTopUp.equals("pending")){
                card.setCardBackgroundColor(card.context.resources.getColor(R.color.ivory))
            }else{
                card.setCardBackgroundColor(card.context.resources.getColor(android.R.color.white))
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_topup, parent, false)
        // set the view's size, margins, paddings and layout parameters
        //
        return ViewHolder(v)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       // holder.mTextView.text = mDataset[position]
        holder.bindData(mDataset.get(position))
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

    fun addAll(data:List<ContentItem?>){
        val old = mDataset.size
        mDataset.addAll(data)
        notifyItemRangeInserted(old,mDataset.size)
    }
}
package rzgonz.bkd.modules.topup.adapter

import android.widget.TextView
import android.view.ViewGroup
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import rzgonz.bkd.R
import rzgonz.bkd.models.pinjaman.PinjamanItem
import rzgonz.bkd.models.pinjaman.ProductsItem
import android.R.attr.name
import rzgonz.bkd.models.bank.BankItem


class BankAdapter(context: Context, banks: List<BankItem?>) : ArrayAdapter<BankItem?>(context, R.layout.row_spinner, banks) {
    // View lookup cache
    private class ViewHolder {
        internal var name: TextView? = null
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // Get the data item for this position
        val bank = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        val viewHolder: ViewHolder // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.row_spinner, parent, false)
            viewHolder.name = convertView!!.findViewById<TextView>(R.id.text)

            // Cache the viewHolder object inside the fresh view
            convertView.tag = viewHolder
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = convertView.tag as ViewHolder
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name?.setText("${bank?.bankName}")
        // Return the completed view to render on screen
        return convertView
    }

    override fun getItem(position: Int): BankItem? {
        return super.getItem(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // Get the data item for this position
        val bank = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        val viewHolder: ViewHolder // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.row_spinner, parent, false)
            viewHolder.name = convertView!!.findViewById<TextView>(R.id.text)

            // Cache the viewHolder object inside the fresh view
            convertView.tag = viewHolder
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = convertView.tag as ViewHolder
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name?.setText("${bank?.bankName}")
        // Return the completed view to render on screen
        return convertView
    }
}
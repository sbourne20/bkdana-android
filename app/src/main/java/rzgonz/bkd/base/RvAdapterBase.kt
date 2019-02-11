package id.rzgonz.demokrat.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rzgonz.demokrat.base.custome.ViewLoading
import rzgonz.bkd.R
import rzgonz.bkd.base.RvCallBack
import rzgonz.core.kotlin.holder.BaseItemHolder

abstract class  RvAdapterBase(val c:Context, var mDataset:ArrayList<Any>) : RecyclerView.Adapter<BaseItemHolder>() {
    var ISEND = false
    val VIEW_TYPE_LOADING = -1
    var footerView : ViewLoading? = null
    lateinit var  listener : RvCallBack

    fun setTryAgain(){
        footerView?.setTryAgian()
    }
    fun stopLoading() {
        footerView?.stopLoading()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseItemHolder {
        lateinit var v: View
        when(viewType){
            VIEW_TYPE_LOADING -> {
                val v = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.row_loading, viewGroup, false)
                footerView = ViewLoading(v)
                footerView?.setListener(listener)
                return footerView as ViewLoading
            }
            else -> {
                return onCreateViewHolderItem(viewGroup, viewType)
            }
        }
    }

    protected abstract fun onCreateViewHolderItem(viewGroup: ViewGroup, viewType: Int): BaseItemHolder
    protected abstract fun onBindViewHolderItem(holder: BaseItemHolder?, position: Int, positionData : Int)

    override fun onBindViewHolder(holder: BaseItemHolder, position: Int) {
       if(holder is ViewLoading){
            holder.initData(ISEND)
        }else{
           onBindViewHolderItem(holder, position, position )
       }
    }

    override fun getItemViewType(position: Int): Int {
        if(position+1==mDataset.size&&!ISEND){
            return VIEW_TYPE_LOADING
        }else{
            return  position
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return mDataset.size
    }

    fun addAll(data:ArrayList<Any>){
        val old = mDataset.size
        mDataset.addAll(data)
        notifyItemRangeInserted(old, mDataset.size)
    }

    fun addAll(data:List<Any>){
            val old = mDataset.size
            mDataset.addAll(data)
            notifyItemRangeInserted(old, mDataset.size)
    }

    fun addAll(data: Array<String>){
        val old = mDataset.size
        mDataset.addAll(data)
        notifyItemRangeInserted(old,mDataset.size)
    }

    fun add(data:String){
        mDataset.add(data)
    }

    fun clear() {
        mDataset.clear()
        notifyDataSetChanged()
    }

    fun removeItem(postionn: Int) {
        mDataset.removeAt(postionn)
        notifyItemRemoved(postionn)
    }


}
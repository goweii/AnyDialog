package pers.goweii.anydialog

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class VAdapter : RecyclerView.Adapter<VAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ImageView(parent.context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 240).apply {
                leftMargin = 24
                rightMargin = 24
                topMargin = 24
                bottomMargin = 24
            }
        })
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.iv.setImageResource(R.mipmap.ic_launcher)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv = itemView as ImageView
    }
}

class HAdapter : RecyclerView.Adapter<HAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ImageView(parent.context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            layoutParams = ViewGroup.MarginLayoutParams(240, 240).apply {
                leftMargin = 24
                rightMargin = 24
                topMargin = 24
                bottomMargin = 24
            }
        })
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.iv.setImageResource(R.mipmap.ic_launcher)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv = itemView as ImageView
    }
}
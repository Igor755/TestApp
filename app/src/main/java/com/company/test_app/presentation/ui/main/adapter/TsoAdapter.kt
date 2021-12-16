package com.company.test_app.presentation.ui.main.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.company.test_app.R
import com.company.test_app.data.entity.presentation.Tso
import kotlinx.android.synthetic.main.item_tso.view.*

class TsoAdapter : BaseQuickAdapter<Tso, BaseViewHolder>(R.layout.item_tso) {

    var lastSelectedPosition: Int = -1
    var onItemClickListener: ((item: Tso) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: Tso) {
        item.let { tso ->
            Glide.with(helper.itemView.context)
                .load(R.drawable.im_tso)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(helper.itemView.ivTso)
            helper.itemView.tvFullAddressRu.text = tso.fullAddressRu
            helper.itemView.tvPlaceRu.text = tso.placeRu

            helper.itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
                lastSelectedPosition = helper.adapterPosition
            }
        }
    }
}
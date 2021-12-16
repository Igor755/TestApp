package com.company.test_app.presentation.ui.main.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.company.test_app.R
import com.company.test_app.data.entity.presentation.Department
import kotlinx.android.synthetic.main.item_department.view.*

class DepartmentAdapter : BaseQuickAdapter<Department, BaseViewHolder>(R.layout.item_department) {

    var lastSelectedPosition: Int = -1
    var onItemClickListener: ((item: Department) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: Department) {
        item.let { department ->
            Glide.with(helper.itemView.context)
                .load(R.drawable.im_privat_main)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(helper.itemView.ivPrivat)
            helper.itemView.tvPhone.text = department.phone
            helper.itemView.tvEmail.text = department.email
            helper.itemView.tvAddress.text = department.address

            helper.itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
                lastSelectedPosition = helper.adapterPosition
            }
        }
    }
}
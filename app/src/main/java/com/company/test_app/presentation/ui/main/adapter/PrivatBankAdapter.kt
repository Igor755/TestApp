package com.company.test_app.presentation.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.company.test_app.R
import com.company.test_app.data.entity.presentation.Currency
import kotlinx.android.synthetic.main.item_privat_bank.view.*

class PrivatBankAdapter : BaseQuickAdapter<Currency, BaseViewHolder>(R.layout.item_privat_bank) {

    var lastSelectedPosition: Int = -1
    var onItemClickListener: ((item: Currency) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: Currency) {
        item.let { currency ->
            helper.itemView.tvCurrency.text = currency.currency
            helper.itemView.tvSale.text = currency.saleRate.toString()
            helper.itemView.tvPurchase.text = currency.purchaseRate.toString()
            helper.itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
                lastSelectedPosition = helper.adapterPosition
            }
        }
    }
}
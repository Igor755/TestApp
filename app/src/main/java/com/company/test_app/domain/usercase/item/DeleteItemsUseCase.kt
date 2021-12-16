package com.company.test_app.domain.usercase.item

import com.company.test_app.data.entity.mapper.DbModelMapper
import com.company.test_app.domain.repository.IItemsRepository
import com.company.test_app.domain.usercase.BaseCoroutinesUseCase
import javax.inject.Inject

class DeleteItemsUseCase @Inject constructor(
    private val itemsRepository: IItemsRepository
) {/*: BaseCoroutinesUseCase<Unit>() {

    private var model: ItemUIModel? = null

    override suspend fun executeOnBackground() {
        model?.let { itemsRepository.delete(DbModelMapper.map(it)) }
    }

    fun setData(model: ItemUIModel) {
        this.model = model
    }*/

}
package com.company.test_app.domain.usercase.item

import com.company.test_app.data.entity.mapper.UiModelMapper
import com.company.test_app.domain.repository.IItemsRepository
import com.company.test_app.domain.usercase.BaseCoroutinesUseCase
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val itemsRepository: IItemsRepository
){/* : BaseCoroutinesUseCase<List<ItemUIModel>>() {

    override suspend fun executeOnBackground(): List<ItemUIModel> =
        itemsRepository.get().map { model -> UiModelMapper.map(model) }*/
}
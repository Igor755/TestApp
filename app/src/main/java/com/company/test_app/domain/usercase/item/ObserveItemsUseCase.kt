package com.company.test_app.domain.usercase.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.company.test_app.data.entity.mapper.UiModelMapper
import com.company.test_app.domain.repository.IItemsRepository
import com.company.test_app.domain.usercase.BaseObserveUseCase
import javax.inject.Inject

class ObserveItemsUseCase @Inject constructor(
    private val itemsRepository: IItemsRepository
){/* : BaseObserveUseCase<List<ItemUIModel>>() {

    override fun observe(): LiveData<List<ItemUIModel>> =
        Transformations.map(itemsRepository.observe()) { list -> list.map { UiModelMapper.map(it) } }*/

}
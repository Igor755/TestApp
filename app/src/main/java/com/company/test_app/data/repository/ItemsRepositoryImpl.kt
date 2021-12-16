package com.company.test_app.data.repository

import androidx.lifecycle.LiveData
import com.company.test_app.data.entity.db.ItemDBModel
import com.company.test_app.data.entity.mapper.DbModelMapper
import com.company.test_app.data.network.services.ApiServices
import com.company.test_app.data.storage.database.dao.IItemsDao
import com.company.test_app.domain.repository.IItemsRepository
import javax.inject.Inject

class ItemsRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices,
    private val itemsDao: IItemsDao
): IItemsRepository {

    override suspend fun get(): List<ItemDBModel> {
       // val listItems = apiServices.getListAsync().await()
      //  itemsDao.insert(listItems.map { model -> DbModelMapper.map(model) })
        return itemsDao.select()
    }

    override suspend fun delete(model: ItemDBModel) {
        itemsDao.delete(model)
    }

    override suspend fun clear() {
        itemsDao.clear()
    }

    override fun observe(): LiveData<List<ItemDBModel>> = itemsDao.observe()
}
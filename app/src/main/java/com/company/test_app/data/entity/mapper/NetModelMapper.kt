package com.company.test_app.data.entity.mapper

import com.company.test_app.data.entity.network.*
import com.company.test_app.data.entity.presentation.*

object NetModelMapper {
    fun PrivatBankNet.map() = PrivatBank(
        date,
        bank,
        baseCurrency,
        baseCurrencyLit,
        exchangeRate.mapCurrency()
    )

    fun PrivatBank.map() = PrivatBankNet(
        date,
        bank,
        baseCurrency,
        baseCurrencyLit,
        exchangeRate.mapCurrencyNet()
    )

    fun List<CurrencyNet>.mapCurrency() = this.mapTo(mutableListOf(), {
        it.map()
    })

    fun List<Currency>.mapCurrencyNet() = this.mapTo(mutableListOf(), {
        it.map()
    })

    fun CurrencyNet.map() = Currency(
        baseCurrency,
        currency,
        saleRateNB,
        purchaseRateNB,
        saleRate,
        purchaseRate
    )

    fun Currency.map() = CurrencyNet(
        baseCurrency,
        currency,
        saleRateNB,
        purchaseRateNB,
        saleRate,
        purchaseRate
    )

    fun Department.map() = DepartmentNet(
        country,
        state,
        city,
        index,
        address,
        phone,
        email,
        name
    )

    fun DepartmentNet.map() = Department(
        country,
        state,
        city,
        index,
        address,
        phone,
        email,
        name
    )

    fun List<DepartmentNet>.mapDepartmentNet() = this.mapTo(mutableListOf(), {
        it.map()
    })

    fun List<Department>.mapDepartment() = this.mapTo(mutableListOf(), {
        it.map()
    })

    fun List<TsoNet>.mapTsoNet() = this.mapTo(mutableListOf(), {
        it.map()
    })

    fun List<Tso>.mapTso() = this.mapTo(mutableListOf(), {
        it.map()
    })

    fun Tso.map() = TsoNet(
        type,cityRU,cityUA,cityEN, fullAddressRu, fullAddressUa, fullAddressEn, placeRu, placeUa, latitude, longitude, week.map())


    fun TsoNet.map() = Tso(
        type,cityRU,cityUA,cityEN, fullAddressRu, fullAddressUa, fullAddressEn, placeRu, placeUa, latitude, longitude, week.map())

    fun ResponseTso.map() = ResponseTsoNet(
        city,address, devices?.mapTso()
    )

    fun ResponseTsoNet.map() = ResponseTso(
        city,address, devices?.mapTsoNet()
    )

    fun WeekNet.map() = Week(
        mon, tue, wed, thu, fri, sat, sun, hol
    )

    fun Week.map() = WeekNet(
        mon, tue, wed, thu, fri, sat, sun, hol
    )

}
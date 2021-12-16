package com.company.test_app.domain.usercase.block

import com.company.test_app.domain.usercase.BaseCoroutinesUseCase

typealias CompletionBlock<T> = BaseCoroutinesUseCase.Request<T>.() -> Unit
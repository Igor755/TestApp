package com.company.test_app.domain.exception

class NetworkErrorException(override val message: String = "network error occurred") :
    Exception(message)
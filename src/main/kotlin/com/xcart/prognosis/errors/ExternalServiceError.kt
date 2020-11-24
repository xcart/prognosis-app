package com.xcart.prognosis.errors

//@ResponseStatus(value=HttpStatus.BAD_REQUEST)
class ExternalServiceError : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
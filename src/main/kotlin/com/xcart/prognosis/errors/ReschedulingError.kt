package com.xcart.prognosis.errors

class ReschedulingError : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
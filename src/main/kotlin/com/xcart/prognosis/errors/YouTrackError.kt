package com.xcart.prognosis.errors

//@ResponseStatus(value=HttpStatus.BAD_REQUEST)
class YouTrackError : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
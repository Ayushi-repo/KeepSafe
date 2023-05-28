package com.example.keepsafe

class Note {
    var id: String? = null
    var message: String? = null
    var date: String? = null

    constructor() {}
    constructor(id: String?, message: String?, date: String?) {
        this.id = id
        this.message = message
        this.date = date
    }
}
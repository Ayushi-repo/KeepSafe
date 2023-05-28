package com.example.keepsafe

class Password {
    var pId: String? = null
    var account: String? = null
    var pass: String? = null

    constructor() {}
    constructor(pId: String?, account: String?, pass: String?) {
        this.pId = pId
        this.account = account
        this.pass = pass
    }
}
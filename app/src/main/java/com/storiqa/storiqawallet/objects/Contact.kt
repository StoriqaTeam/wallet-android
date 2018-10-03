package com.storiqa.storiqawallet.objects

data class Contact(val id : String, val name : String, val phone : String, val imageIri : String, var wallet : String = "") {
    fun getShortenName(): String {
        var nameShorten = ""
        if (name.split(" ").isEmpty()) {
            nameShorten += name[0].toUpperCase()
        } else {
            for (word in name.split(" ")) {
                nameShorten += word[0].toUpperCase()
            }
        }
        return nameShorten
    }
}
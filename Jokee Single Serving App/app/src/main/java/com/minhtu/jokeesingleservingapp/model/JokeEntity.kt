package com.minhtu.jokeesingleservingapp.model

class JokeEntity {
    private var name : String
    private var content : String

    constructor(name: String, content: String) {
        this.name = name
        this.content = content
    }

    fun getName() : String {
        return this.name
    }

    fun getContent() : String {
        return this.content
    }

    fun setName(name : String) {
        this.name = name
    }

    fun setContent(content: String) {
        this.content = content
    }
}
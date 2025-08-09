package ru.semura.aicoder.services.client

interface AiClient {
    fun generate(prompt: String)
}
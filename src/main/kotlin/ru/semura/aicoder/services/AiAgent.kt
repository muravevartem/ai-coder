package ru.semura.aicoder.services

enum class AiAgent(val displayName: String) {
    OLLAMA("Ollama", ),
    CUSTOM("Custom"),
    ;

    override fun toString(): String = displayName
}
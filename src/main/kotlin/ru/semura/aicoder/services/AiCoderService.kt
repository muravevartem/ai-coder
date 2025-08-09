package ru.semura.aicoder.services

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(
    name = "AiCoderServiceData",
    storages = [Storage("aiCoderServiceData.xml")]
)
class AiCoderService : PersistentStateComponent<AiCoderService.State> {
    companion object {
        val instance: AiCoderService
            get() = ApplicationManager.getApplication().getService(AiCoderService::class.java)
    }

    private var state: State = State()

    override fun getState(): AiCoderService.State {
        return this.state
    }

    override fun loadState(state: AiCoderService.State) {
        this.state = state
    }


    class State {
        var url: String = "http://localhost:11434"
        var model: String = "qwen2.5-coder:7b"
        var token: String = ""
        var agent: AiAgent? = null
    }
}
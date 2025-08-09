package ru.semura.aicoder.ui

import com.intellij.icons.AllIcons
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.BoundConfigurable
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.Row
import com.intellij.ui.dsl.builder.actionButton
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import ru.semura.aicoder.notifiers.Notifier
import ru.semura.aicoder.services.AiAgent
import ru.semura.aicoder.services.AiCoderService
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URI
import java.net.URL

class AiCoderConfigurable : BoundConfigurable("Ai Coder") {
    override fun createPanel(): DialogPanel = createDialogPanel()
}

fun createDialogPanel(): DialogPanel {
    val instance = AiCoderService.instance

    return panel {
        lateinit var ollamaConfig: Row

        row {
            comboBox(AiAgent.entries)
                .onChanged { ollamaConfig.visible(AiAgent.OLLAMA == it.item) }
        }

        ollamaConfig = group("Ollama Configuration") {
            row {
                textField().bindText(instance.state::url)
                    .label("Url", LabelPosition.TOP).bold()
                    .validationOnInput {
                        if (it.text.asUrl() == null) {
                            return@validationOnInput error("Invalid url")
                        }
                        return@validationOnInput null
                    }
                actionButton(object : DumbAwareAction("Test connect", null, AllIcons.Actions.Refresh) {
                    override fun actionPerformed(e: AnActionEvent) {
                        val url = instance.state.url.asUrl() ?: return
                        print(url)
                        if (url.isOpenPort()) {
                            Notifier.notify(e.project, "Success connect", NotificationType.INFORMATION)
                            return
                        }
                        Notifier.notify(e.project, "Failed connect", NotificationType.ERROR)
                    }
                })
            }
            row {
                textField().bindText(instance.state::model)
                    .label("Model", LabelPosition.TOP).bold()
            }
            collapsibleGroup("Security") {
                row {
                    passwordField().bindText(instance.state::token)
                        .label("Token", LabelPosition.TOP).bold()
                }
            }
        }

    }
}

fun String.asUrl(): URL? {
    return try {
        URI.create(this).toURL()
    } catch (e: Exception) {
        null
    }
}

fun URL.isOpenPort(): Boolean {
    try {
        Socket().use { socket ->
            socket.connect(InetSocketAddress(this.host, this.port))
            return true
        }
    } catch (e: Exception) {
        return false
    }
}
package ru.semura.aicoder.action

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import ru.semura.aicoder.services.AiCoderService

class GenerateAiJavadocAction : AnAction() {
    private val aiJavadocService: AiCoderService = AiCoderService.instance

    override fun actionPerformed(e: AnActionEvent) {

    }
}

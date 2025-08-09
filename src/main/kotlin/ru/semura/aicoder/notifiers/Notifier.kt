package ru.semura.aicoder.notifiers

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object Notifier {
    fun notify(project: Project?, message: String, type: NotificationType) {
        if (project == null) return

        NotificationGroupManager.getInstance()
            .getNotificationGroup("AiCoder.NotificationGroup")
            .createNotification(message, type)
            .notify(project)
    }
}
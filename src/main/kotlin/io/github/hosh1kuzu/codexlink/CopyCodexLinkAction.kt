package io.github.hosh1kuzu.codexlink

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.popup.util.PopupUtil
import com.intellij.openapi.vfs.VirtualFile
import java.awt.datatransfer.StringSelection

class CopyCodexLinkAction : DumbAwareAction(
    "Copy Codex Link",
    "Copy a Codex markdown link for the current selection",
    CodexIcons.Action,
) {
    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun update(event: AnActionEvent) {
        val presentation = event.presentation
        val selection = event.resolveSelection()
        presentation.isEnabledAndVisible = selection != null
    }

    override fun actionPerformed(event: AnActionEvent) {
        val selection = event.resolveSelection() ?: return
        CopyPasteManager.getInstance().setContents(StringSelection(selection.toMarkdownLink()))
        showCopiedHint(event.getData(CommonDataKeys.EDITOR))
    }

    private fun showCopiedHint(editor: Editor?) {
        if (editor == null || PopupUtil.getOwner(editor.contentComponent) == null) {
            return
        }

        HintManager.getInstance().showInformationHint(editor, "Copied Codex link")
    }

    private fun AnActionEvent.resolveSelection(): CodexLinkSelection? {
        val editor = getData(CommonDataKeys.EDITOR) ?: return null
        val virtualFile = resolveVirtualFile(editor) ?: return null
        return CodexLinkSelection.from(editor, virtualFile)
    }

    private fun AnActionEvent.resolveVirtualFile(editor: Editor): VirtualFile? {
        return getData(CommonDataKeys.VIRTUAL_FILE) ?: FileDocumentManager.getInstance().getFile(editor.document)
    }
}

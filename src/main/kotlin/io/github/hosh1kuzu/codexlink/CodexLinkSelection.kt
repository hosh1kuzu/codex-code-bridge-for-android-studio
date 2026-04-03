package io.github.hosh1kuzu.codexlink

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.vfs.VirtualFile
import java.nio.file.InvalidPathException
import java.nio.file.Path

data class CodexLinkSelection(
    val fileName: String,
    val absolutePath: String,
    val startLine: Int,
    val endLine: Int,
) {
    fun toMarkdownLink(): String = CodexLinkFormatter.format(fileName, absolutePath, startLine, endLine)

    companion object {
        fun from(editor: Editor, virtualFile: VirtualFile): CodexLinkSelection? {
            if (!virtualFile.isInLocalFileSystem) {
                return null
            }

            val selectionModel = editor.selectionModel
            if (!selectionModel.hasSelection()) {
                return null
            }

            val selectionStart = selectionModel.selectionStart
            val selectionEnd = selectionModel.selectionEnd
            if (selectionEnd <= selectionStart) {
                return null
            }

            val absolutePath = try {
                Path.of(virtualFile.path).toAbsolutePath().normalize().toString()
            } catch (_: InvalidPathException) {
                return null
            }

            val document = editor.document
            val startLine = document.getLineNumber(selectionStart) + 1
            val inclusiveEndOffset = (selectionEnd - 1).coerceAtLeast(selectionStart)
            val endLine = document.getLineNumber(inclusiveEndOffset) + 1

            return CodexLinkSelection(
                fileName = virtualFile.name,
                absolutePath = absolutePath,
                startLine = startLine,
                endLine = endLine,
            )
        }
    }
}

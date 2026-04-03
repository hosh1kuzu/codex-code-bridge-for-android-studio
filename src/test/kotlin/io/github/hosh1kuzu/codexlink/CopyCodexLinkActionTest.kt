package io.github.hosh1kuzu.codexlink

import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.actionSystem.impl.SimpleDataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.awt.datatransfer.DataFlavor

class CopyCodexLinkActionTest : BasePlatformTestCase() {
    private val action = CopyCodexLinkAction()

    fun `test action is visible for a local file selection`() {
        val file = myFixture.tempDirFixture.createFile("src/Example.kt", "first line\nsecond line\n")
        myFixture.openFileInEditor(file)
        val editor = myFixture.editor
        editor.selectionModel.setSelection(
            editor.document.getLineStartOffset(0),
            editor.document.getLineEndOffset(0),
        )

        val event = createEvent(editor, file)
        action.update(event)

        assertTrue(event.presentation.isEnabledAndVisible)
    }

    fun `test action is hidden without a selection`() {
        val file = myFixture.tempDirFixture.createFile("src/Example.kt", "first line\nsecond line\n")
        myFixture.openFileInEditor(file)
        val editor = myFixture.editor
        editor.selectionModel.removeSelection()

        val event = createEvent(editor, file)
        action.update(event)

        assertFalse(event.presentation.isEnabledAndVisible)
    }

    fun `test action copies the markdown link for the selected range`() {
        val file = myFixture.tempDirFixture.createFile("src/Example.kt", "first line\nsecond line\nthird line\n")
        myFixture.openFileInEditor(file)
        val editor = myFixture.editor
        editor.selectionModel.setSelection(
            editor.document.getLineStartOffset(0),
            editor.document.getLineStartOffset(2),
        )

        val event = createEvent(editor, file)
        action.actionPerformed(event)

        val clipboardText = CopyPasteManager.getInstance().contents
            ?.getTransferData(DataFlavor.stringFlavor) as? String

        assertEquals("[Example.kt:L1-L2](${file.path}#L1)", clipboardText)
    }

    private fun createEvent(editor: Editor, file: VirtualFile): AnActionEvent {
        val dataContext = SimpleDataContext.builder()
            .add(CommonDataKeys.EDITOR, editor)
            .add(CommonDataKeys.VIRTUAL_FILE, file)
            .add(CommonDataKeys.PROJECT, project)
            .build()

        return AnActionEvent.createFromDataContext(ActionPlaces.EDITOR_POPUP, Presentation(), dataContext)
    }
}

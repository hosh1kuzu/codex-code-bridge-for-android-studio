package io.github.hosh1kuzu.codexlink

import org.junit.Assert.assertEquals
import org.junit.Test

class CodexLinkFormatterTest {
    @Test
    fun `formats a single-line selection`() {
        val formatted = CodexLinkFormatter.format(
            fileName = "Example.kt",
            absolutePath = "/tmp/project/Example.kt",
            startLine = 120,
            startColumn = 20,
            endLine = 120,
            endColumn = 20,
        )

        assertEquals("[Example.kt:L120C20](/tmp/project/Example.kt#L120C20)", formatted)
    }

    @Test
    fun `formats a ranged selection with columns`() {
        val formatted = CodexLinkFormatter.format(
            fileName = "Example.kt",
            absolutePath = "/tmp/project/Example.kt",
            startLine = 120,
            startColumn = 20,
            endLine = 168,
            endColumn = 80,
        )

        assertEquals("[Example.kt:L120C20-L168C80](/tmp/project/Example.kt#L120C20)", formatted)
    }

    @Test
    fun `keeps spaces and chinese characters in the path`() {
        val formatted = CodexLinkFormatter.format(
            fileName = "示例 文件.kt",
            absolutePath = "/tmp/示例 工程/示例 文件.kt",
            startLine = 10,
            startColumn = 3,
            endLine = 12,
            endColumn = 18,
        )

        assertEquals("[示例 文件.kt:L10C3-L12C18](/tmp/示例 工程/示例 文件.kt#L10C3)", formatted)
    }

    @Test
    fun `formats a single-line range with both columns`() {
        val formatted = CodexLinkFormatter.format(
            fileName = "Example.kt",
            absolutePath = "/tmp/project/Example.kt",
            startLine = 100,
            startColumn = 20,
            endLine = 100,
            endColumn = 48,
        )

        assertEquals("[Example.kt:L100C20-L100C48](/tmp/project/Example.kt#L100C20)", formatted)
    }
}

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
            endLine = 120,
        )

        assertEquals("[Example.kt:L120](/tmp/project/Example.kt#L120)", formatted)
    }

    @Test
    fun `formats a multi-line selection`() {
        val formatted = CodexLinkFormatter.format(
            fileName = "Example.kt",
            absolutePath = "/tmp/project/Example.kt",
            startLine = 120,
            endLine = 168,
        )

        assertEquals("[Example.kt:L120-L168](/tmp/project/Example.kt#L120)", formatted)
    }

    @Test
    fun `keeps spaces and chinese characters in the path`() {
        val formatted = CodexLinkFormatter.format(
            fileName = "示例 文件.kt",
            absolutePath = "/tmp/示例 工程/示例 文件.kt",
            startLine = 10,
            endLine = 12,
        )

        assertEquals("[示例 文件.kt:L10-L12](/tmp/示例 工程/示例 文件.kt#L10)", formatted)
    }
}

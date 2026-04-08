package io.github.hosh1kuzu.codexlink

object CodexLinkFormatter {
    fun format(
        fileName: String,
        absolutePath: String,
        startLine: Int,
        startColumn: Int,
        endLine: Int,
        endColumn: Int,
    ): String {
        require(fileName.isNotBlank()) { "fileName must not be blank" }
        require(absolutePath.isNotBlank()) { "absolutePath must not be blank" }
        require(startLine > 0) { "startLine must be positive" }
        require(startColumn > 0) { "startColumn must be positive" }
        require(endLine >= startLine) { "endLine must be >= startLine" }
        require(endColumn > 0) { "endColumn must be positive" }
        require(endLine > startLine || endColumn >= startColumn) {
            "endColumn must be >= startColumn when the selection stays on one line"
        }

        val label = if (startLine == endLine && startColumn == endColumn) {
            "$fileName:L${startLine}C$startColumn"
        } else {
            "$fileName:L${startLine}C$startColumn-L${endLine}C$endColumn"
        }

        return "[$label]($absolutePath#L${startLine}C$startColumn)"
    }
}

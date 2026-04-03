package io.github.hosh1kuzu.codexlink

object CodexLinkFormatter {
    fun format(fileName: String, absolutePath: String, startLine: Int, endLine: Int): String {
        require(fileName.isNotBlank()) { "fileName must not be blank" }
        require(absolutePath.isNotBlank()) { "absolutePath must not be blank" }
        require(startLine > 0) { "startLine must be positive" }
        require(endLine >= startLine) { "endLine must be >= startLine" }

        val label = if (startLine == endLine) {
            "$fileName:L$startLine"
        } else {
            "$fileName:L$startLine-L$endLine"
        }

        return "[$label]($absolutePath#L$startLine)"
    }
}

package com.ozg.noted.core

object LinkExtractor {
    private val LINK_PATTERN = "\\[\\[([^\\]]+)\\]\\]".toRegex()
    
    fun extractLinks(content: String): List<String> {
        return LINK_PATTERN.findAll(content)
            .map { it.groupValues[1] }
            .toList()
    }
}

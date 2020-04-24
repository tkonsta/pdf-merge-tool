package de.tkonsta.pdfmergetool

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MergeToolTest {

    @Test
    fun mergePdfDocuments_noPdf() {
        val mergeTool = MergeTool()
        val mergedPdfDocument = mergeTool.mergePdfDocuments(listOf())
        assertThat(mergedPdfDocument).hasSizeLessThanOrEqualTo(0)
    }

    @Test
    fun mergePdfDocuments_onePdf() {
        val mergeTool = MergeTool()
        val mergedPdfDocument = mergeTool.mergePdfDocuments(listOf(this::class.java.getResourceAsStream("/Test.pdf")))
        assertThat(mergedPdfDocument).hasSizeGreaterThan(0)
    }

    @Test
    fun mergePdfDocuments_twoPdfs() {
        val mergeTool = MergeTool()
        val mergedPdfDocument = mergeTool.mergePdfDocuments(
            listOf(
                this::class.java.getResourceAsStream("/Test.pdf"),
                this::class.java.getResourceAsStream("/Test.pdf")
            )
        )
        assertThat(mergedPdfDocument).hasSizeGreaterThan(0)
    }

}

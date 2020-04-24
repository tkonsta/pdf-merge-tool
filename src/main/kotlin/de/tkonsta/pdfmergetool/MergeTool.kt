package de.tkonsta.pdfmergetool

import org.apache.pdfbox.io.MemoryUsageSetting
import org.apache.pdfbox.multipdf.PDFMergerUtility
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MergeTool {

    fun mergePdfDocuments(pdfFiles: List<InputStream>): ByteArray {
        println("Merging the given PDF documents")
        val result = ByteArrayOutputStream()
        val pdfMerger = PDFMergerUtility()
        pdfMerger.addSources(pdfFiles)
        pdfMerger.destinationStream = result
        pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly())
        return result.toByteArray()
    }

}

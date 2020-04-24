package de.tkonsta.pdfmergetool

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

class Main {
 companion object {
     @JvmStatic fun main(args: Array<String>) {
         val inputs: MutableList<InputStream> = mutableListOf()
         for (pdfFileName in args) {
             val fis =
                 try {
                     println("looking up " + pdfFileName)
                     FileInputStream(pdfFileName)
                 } catch (e: Exception) {
                     println(pdfFileName + " not found!")
                     null
                 }
             if (fis != null) {
                 inputs.add(fis)
             }
         }

         val mergedPdfDocument = MergeTool().mergePdfDocuments(inputs)
         val fos = FileOutputStream("merged-pdf-file.pdf")
         fos.write(mergedPdfDocument)
         fos.close()
     }
 }
}

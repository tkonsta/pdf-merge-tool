package de.tkonsta.pdfmergetool

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream


class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val argumentHandler = ArgumentHandler(args)

            if (argumentHandler.inputs.size == 0) {
                println("No input files given or given input files cannot be found")
                return
            }

            val inputs: MutableList<InputStream> = argumentHandler.inputs

            val mergedPdfDocument = MergeTool().mergePdfDocuments(inputs)

            println("Writing merged PDF document to ${argumentHandler.outputPath}")
            val fileOutputStream = FileOutputStream(argumentHandler.outputPath)
            fileOutputStream.write(mergedPdfDocument)
            fileOutputStream.close()
        }
    }
}

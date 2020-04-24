package de.tkonsta.pdfmergetool

import org.apache.commons.io.IOUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Path

internal class ArgumentHandlerTest {

    companion object {

        var tmpDir: String = ""

        @BeforeAll
        @JvmStatic
        fun beforeAll(@TempDir tempDir: Path) {
            copyTestResourcesToTempDir(tempDir)
            tmpDir = tempDir.toAbsolutePath().toString()
            println("tmpDir is " + tmpDir)
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            println("Temp directory is deleted. If errors occur, InputStreams were not closed properly in the test")
        }

        private fun copyTestResourcesToTempDir(tempDir: Path) {
            val inputStream = this::class.java.getResourceAsStream("/Test.pdf")
            val file = File(tempDir.toFile(), "Test.pdf")
            val file2 = File(tempDir.toFile(), "Test2.pdf")
            IOUtils.copy(inputStream, FileOutputStream(file))
            IOUtils.copy(inputStream, FileOutputStream(file2))
        }
    }

    @Test
    fun construct_withParamListAndDefaultOutput() {

        val testPdfPath = File(tmpDir, "Test.pdf").absolutePath

        val argumentHandler = ArgumentHandler(arrayOf("-i", testPdfPath, testPdfPath))

        assertThat(argumentHandler.inputs).hasSize(2)
        assertThat(argumentHandler.outputPath).isEqualTo(ArgumentHandler.DEFAULT_OUTPUT_PATH)

        argumentHandler.inputs.forEach { inputStream ->
            inputStream.close()
        }
    }

    @Test
    fun construct_withParamListAndDefinedOutput() {

        val testPdfPath = File(tmpDir, "Test.pdf").absolutePath
        val outputPdfPath = File(tmpDir, "Output.pdf").absolutePath

        val argumentHandler = ArgumentHandler(arrayOf("-i", testPdfPath, testPdfPath, "-o", outputPdfPath))

        assertThat(argumentHandler.inputs).hasSize(2)
        assertThat(argumentHandler.outputPath).isEqualTo(outputPdfPath)

        argumentHandler.inputs.forEach { inputStream ->
            inputStream.close()
        }
    }

    @Test
    fun construct_withListFileAndDefinedOutput() {

        val testPdfPath = File(tmpDir, "Test2.pdf").absolutePath
        val outputPdfPath = File(tmpDir, "Output.pdf").absolutePath

        val inputFilePath = File(tmpDir, "inputListFile.txt").absolutePath
        createListFileInTempDir(inputFilePath, arrayOf(testPdfPath, testPdfPath, testPdfPath))

        val argumentHandler = ArgumentHandler(arrayOf("-l", inputFilePath, "-o", outputPdfPath))

        assertThat(argumentHandler.inputs).hasSize(3)
        assertThat(argumentHandler.outputPath).isEqualTo(outputPdfPath)

        argumentHandler.inputs.forEach { inputStream ->
            inputStream.close()
        }
    }

    private fun createListFileInTempDir(inputFilePath: String, testPdfPaths: Array<String>) {
        val fos = FileOutputStream(inputFilePath)
        val bufferedWriter = fos.bufferedWriter()
        testPdfPaths.forEach { testPdfPath ->
            bufferedWriter.write(testPdfPath)
            bufferedWriter.newLine()
        }
        bufferedWriter.close()
        fos.close()
    }

}

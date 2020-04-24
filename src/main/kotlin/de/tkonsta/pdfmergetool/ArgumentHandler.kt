package de.tkonsta.pdfmergetool

import org.apache.commons.cli.*
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.Files


class ArgumentHandler(args: Array<String>) {

    companion object {
        const val DEFAULT_OUTPUT_PATH = "merged-pdf-file.pdf"
    }

    var inputs: MutableList<InputStream> = mutableListOf()
    var outputPath = DEFAULT_OUTPUT_PATH

    init {
        parseArguments(args)
    }

    private fun parseArguments(args: Array<String>) {
        val options = Options()
        options.addOption(
            "l",
            "inputList",
            true,
            "Use list in given input file instead of params. One file path per line."
        )
        options.addOption("i", true, "List of input files separated by spaces (' ')")
        options.addOption("o", "outputFile", true, "Use given output file instead of default output file name")


        options.getOption("i").valueSeparator = ' '
        options.getOption("i").args = Option.UNLIMITED_VALUES

        val parser: CommandLineParser = DefaultParser()
        val cmd = parser.parse(options, args)

        if (cmd.hasOption("i")) {
            val optionValues = cmd.getOptionValues("i")
            optionValues.forEach { inputString ->
                addInputIfFileExists(inputString)
            }
        }

        if (cmd.hasOption("l")) {
            val listFile = cmd.getOptionValue("l")
            val fileReader = Files.newBufferedReader(File(listFile).toPath(), Charset.forName("UTF-8"))
            fileReader.use {
                fileReader.lines().forEach { inputString ->
                    addInputIfFileExists(inputString)
                }
            }
        }

        if (!cmd.hasOption("i") && !cmd.hasOption("l")) {
            val formatter = HelpFormatter()
            formatter.printHelp("pdf-merge-tool", options)
        }

        if (cmd.hasOption("o")) {
            outputPath = cmd.getOptionValue("o")
        }
    }

    private fun addInputIfFileExists(inputString: String) {
        try {
            println("looking up $inputString")
            val fis = FileInputStream(inputString)
            inputs.add(fis)
        } catch (e: Exception) {
            println("$inputString not found!")
        }
    }

}

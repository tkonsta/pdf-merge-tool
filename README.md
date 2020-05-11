# pdf-merge-tool
A small command line tool to merge PDF files written in Kotlin.

It uses Apache PDFBox for the merging and Apache commons-cli for parsing the command line arguments.

Usage:

    java -jar pdf-merge-tool-1.0-SNAPSHOT-jar-with-dependencies.jar -i <pdf-files> [-o outputFile]
    
    java -jar pdf-merge-tool-1.0-SNAPSHOT-jar-with-dependencies.jar -l <text-file-list>  [-o outputFile]

    

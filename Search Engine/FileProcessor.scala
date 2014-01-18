package search2

object FileProcessor {
    def getFileText(file : String) : String = {
        val source = scala.io.Source.fromFile(file)
        val ret = source.mkString
        source.close()
        ret
    }
}

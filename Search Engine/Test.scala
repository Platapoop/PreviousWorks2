package search2
object Test {
  def main(args:Array[String]) {
val writer = new SearchWriter("file")
writer.write("Hello World!")
writer.close
  }}

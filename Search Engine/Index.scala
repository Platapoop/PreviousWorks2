package search2
import scala.xml.Node
import scala.collection.mutable.HashMap
import scala.io.Source
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.MutableList

class StupidityException extends Exception{}

class Index(filename : String){
  
  val node: Node = xml.XML.loadFile(filename)
  val ignoreWord = List("or", "and", "", "a", "an", "the", "for", "at", "with", "that", "www")
  //pull pages
  val children : Seq[Node] = node.child.filter(x => x.label.contains("page"))
  //hashmap of all ids and titles
  val IDtoTitle = new HashMap[String, String]
  //hashmap of all the word frequencies of documents
  val wordHash = new HashMap[String, MutableList[(String, Double)]]
  //hashmap of document title and pagerank score
  val pageRank = new HashMap[String, Double]
  
  for (n <- 0 to children.length - 1){
    //create variables per children
    //frequency of the words in this particular document
    val dWFreq = new HashMap[String, Int]
    
    val nodeID = (children(n) \\ "id").text
    val nodeTitle = (children(n) \\ "title").text
    
    //add title and node to the ID-Title hashmap
    IDtoTitle += ((nodeID, nodeTitle))
    
    //split node body text at all non-alphanumeric characters except for when they're inside brackets
    val nodeText = ((children(n) \\ "text")).text.toLowerCase.split("[^a-z0-9]+(?![^\\[]*\\])")
    for (w <- nodeText){
      if (!ignoreWord.contains(w)){
        if (!w.contains('[')){
          //if the word is not a link
          val potWord = PorterStemmer.stem(w)
          dWFreq += ((potWord, dWFreq.get(potWord).getOrElse(0) + 1))
        } else {
          //if the word is a link, split at pipe, take out non-alphanumeric characters
          val link = w.split('|')
	      val title = link(link.length-1).replaceAll("[^A-Za-z0-9\\s]", "")
	      //add to page rank hashmap
	      if (!title.startsWith("http")) pageRank += ((title, (pageRank.get(title).getOrElse(0.0) + 1.0)))
	      val linkWords = PorterStemmer.stemArray(link(0).split("[^a-z0-9]"))
	      //get the text from the link and add it to the document frequency
	      for (l <- linkWords){
	        if (!ignoreWord.contains(l)) dWFreq += ((l, dWFreq.get(l).getOrElse(0) + 1))
	      }
        }
      }
    }
    //tabulate euclidean
    var euScore = 0.0
    for (pair <- dWFreq){
       euScore += (pair._2)^2
    }
    //add words and scores to index
    for (pair <- dWFreq) {
      wordHash += ((pair._1, wordHash.get(pair._1).getOrElse(new MutableList[(String, Double)]) += ((nodeID, pair._2 / Math.sqrt(euScore)))))
    }
  }
  
  writeWordIndex
  writeTitleIndex
  writePageRankIndex
  
  def writeWordIndex() = {
  //write to file
	  val writer = new SearchWriter("WordIndex")
	  for (pair <- wordHash){
	    writer.write((pair._1 + "!").replace("\n", ""))
	    for(f <- pair._2){
	      writer.write((f._1 + "," + f._2 + "!").replace("\n", ""))
	    }
	    writer.writeLine("")
	  }
	  writer.close()
  }
  
  def writeTitleIndex() = {
	  val writer2 = new SearchWriter("TitleIndex")
	  for (pair <- IDtoTitle){
	    writer2.write((pair._1 + "!").replace("\n", ""))
	    writer2.writeLine((pair._2).replace("\n", ""))
	  }
	  writer2.close()
  }
  
  def writePageRankIndex() = {
	  val writer3 = new SearchWriter("PageRankIndex")
	  for (pair <- pageRank){
	    writer3.write(pair._1 + "~")
	    writer3.writeLine(pair._2.toString())
	  }
	  writer3.close()
  }
}

object Index {
  
 def main(args: Array[String]) {
   try {
	   val indexer = new Index(args(0))
	   println("DING!")
   } catch {
     case ex : Exception => println(ex.getMessage() + "Please input wiki location.")
   }
   
 }
 }
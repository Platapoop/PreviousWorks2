package search2
import scala.xml.Node
import scala.collection.mutable.HashMap
import scala.io.Source
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.MutableList
import scala.collection.mutable.PriorityQueue

class Query(wordIndex: String, titleIndex: String, pageRankIndex: String) {
   
   //contains the words and the list of ids and frequency of documents that contain them. 
   val wordHash = new HashMap[String, MutableList[(Double, String)]]
   //hashmap of all the ids to titles
   val IDToTitle = new HashMap[String, String]
   //hashmap of all titles and page rank scores
   val pageRank = new HashMap[String, Double]
   //pull text from files
   val wordIndexer = FileProcessor.getFileText(wordIndex)
   val titleIndexer = FileProcessor.getFileText(titleIndex)
   val pageRanker = FileProcessor.getFileText(pageRankIndex)
   //split each file by line
   val readyToHashTitle = titleIndexer.split("\n")
   val readyToHash = wordIndexer.split("\n")
   val readyToHashPageRank = pageRanker.split("\n")
   
   //put words into the word frequency hash table
   for (lines <- readyToHash){
     var elements: Array[String] = lines.split("!")
     //hasP is the list of the documents and their scores
     val hashP : MutableList[(Double, String)] = new MutableList
     for (i <- 1 to elements.length-1){
       val titleScore = elements(i).split(',')
       hashP += ((titleScore(1).toDouble, titleScore(0)))
     }
     wordHash +=((elements(0), hashP))
   }
  
  //put documents and IDs into hash table
  for (lines <- readyToHashTitle) {
    var elements = lines.split("!")
    IDToTitle += ((elements(0), elements(1)))
  }
  
  //put titles and pagerank scores into hash map
  for (lines <- readyToHashPageRank) {
    var elements = lines.split("~")
    try {
      pageRank += ((elements(0), elements(1).toDouble))
    } catch {
      case ex : ArrayIndexOutOfBoundsException => 
    }
  }
  
  
  def calcScore(input : Array[String]) : HashMap[String, Double] = {
    //calculate the scores based on frequency
    val score = new HashMap[String,Double]
      for (i <- 0 until input.length) {
        try {
	        val titleScoreList = wordHash(input(i))
	    	val inf : Double = math.log(IDToTitle.size.toDouble/(1 + titleScoreList.size))
	        for (elem <- titleScoreList) {
	          score += ((elem._2, (score.get(elem._2).getOrElse(0.0)*inf + elem._1)))
	        }
        } catch {
        	case ex : NoSuchElementException =>  
        }
    }
      return score
  }


  def query (input : Array[String])= {
	//calculate score based on frequency
	val score = calcScore(input)
    //copy to array and sort
	var sorted = Array.ofDim[(String, Double)](score.size);
    score.copyToArray(sorted);
    sorted = sorted.sortWith((e1, e2) => e1._2 > e2._2);
    
    //print out top 10 results
    for (i <- 0 to math.min(9, sorted.length-1)) {
      println((i+1) + ". " + IDToTitle(sorted(i)._1))
    }
  }
  
  def smartQuery(input : Array[String]){
    val score = calcScore(input)
    //copy to array
    var sorted = Array.ofDim[(String, Double)](score.size);
	score.copyToArray(sorted);
	//for each document that contains the word, factor in the title and the page rank
    for (i <- 0 until sorted.length){
       //get the title from ID
	   val title = IDToTitle.get(sorted(i)._1).get.toLowerCase().replaceAll("[^A-Za-z0-9\\s]", "")
	   //weight title
	   val titleScore : Double = compare(input, title)
	   var pageRankScore = 0.0
      try {
        //get pageRank score, normalized 
        pageRankScore = math.log10(pageRank.get(title).get + 10)
	  } catch {
	    case ex : NoSuchElementException  => 
	  }
	  //sum pageRank score, frequency score, and titleScore
	  sorted(i) = ((sorted(i)._1, pageRankScore*.5 + sorted(i)._2*8 + titleScore))
	}
    //sort
    sorted = sorted.sortWith((e1, e2) => e1._2 > e2._2);
    //print top 10 results
    for (i <- 0 to math.min(9, sorted.length - 1)) {
      println((i+1) + ". " + IDToTitle(sorted(i)._1))
    }
  }
  
  def compare(in : Array[String], title : String) : Double = {
    //tabulates title score
    //stem title and input
    val input = PorterStemmer.stemArray(in)
    val t = PorterStemmer.stem(title.toLowerCase.replace("[^a-z0-9\\s]", ""))
    var score = 0.0; 
    for (i <- input) { 
      if (t.contains(i)) { 
        score += 1.0
      }
    }
    return math.pow(score, 2)
  }

}

object Query {
  def main(args: Array[String]) {
    val querier = new Query (args(0), args(1), args(2))
    var searching = true
    while (searching){ //check for word that isn't there
      print("search> ")
       val input = PorterStemmer.stemArray(readLine().toLowerCase().split(" "))
       if (input(0) == ":quit") {
         searching = false; 
       } else if (input(0) == "--smart") {
         querier.smartQuery(input.drop(1))
       } else {
    	 querier.query(input)
       }
    }
    
  }
}
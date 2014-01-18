/*package olympians2

import scala.collection.mutable.ArrayBuffer;

class Game3 {
	val summonMap = collection.mutable.Map[String, String]();
	
	var artifactList = List[String]("Magic Herbs", "Miracle Potion", "The Elemental Potions", "Urn of Fortitude", "Amulet of Power", 
	    "Holy Patronage", "Summon");
	
	// Use name and affinity as key, in case there are same names 
	// for different affinity.
	def buildSummonRule = {
		// Water
		summonMap.put("Water Worshiper", "Water Titan");
		summonMap.put("Water Titan", "Water Poeidon");
		summonMap.put("Holy Bard", "Dionysus");

		// Air
		summonMap.put("Air Worshiper", "Air Titan");
		summonMap.put("Air Titan", "Zeus");
		summonMap.put("Holy Messenger", "Athena");
		summonMap.put("Holy Bard", "Athena");

		// More ...
	}
	
	def summonNextForm(name : String) : Olympians = {
		var o = null;
		val nextName = summonMap.get(name)
		nextName match {
			case None => null;
			case Some(x) => createOlympianFromName(x);
		}
	}
	
	def createOlympianFromName(name : String) : Olympians = {
		val o = new Olympians(name);

		if(name=="Water Worshipe"||name=="Water Titan"||name=="Poseidon"||name=="Holy Bard"||name=="Dionysus") {
				o.affinity = "WATER";
				o.weakAgainst = "AIR";
				o.strongAgainst = "FIRE";
				if(name=="Water Worshipe"){
					o.attackDmg = 20;
					o.attackName = "Water Blast";
					o.maxHP = 60;
				}
				else if(name=="Water Titan"){
					o.attackDmg = 20;
					o.attackName = "Flood";
					o.maxHP = 100;
				}
				else if(name=="Poseidon"){
					o.attackDmg = 60;
					o.attackName = "Tidal Wave";
					o.maxHP = 120;
				}
				else if(name=="Holy Bard"){
					o.attackDmg = 0;
					o.attackName = "Stumble";
					o.maxHP = 100;
				}
				else if(name=="Dionysus"){
					o.attackDmg = 50;
					o.attackName = "Partaaay";
					o.maxHP = 150;
				}
		}
		else if(name=="Air Worshiper"||name=="Air Titan"||name=="Zeus"||name=="Holy Messenge"||name=="Athena"){
				o.affinity = "AIR";
				o.weakAgainst = "FIRE";
				o.strongAgainst = "WATER";
				if(name=="Air Worshiper"){
					o.attackDmg = 20;
					o.attackName = "Gust";
					o.maxHP = 60;
				}
				else if(name=="Air Titan"){
					o.attackDmg = 30;
					o.attackName = "Hurricane";
					o.maxHP = 100;
				}
				else if(name=="Zeus"){
					o.attackDmg = 50;
					o.attackName = "Thunderbolt";
					o.maxHP = 140;
				}
				else if(name=="Holy Messenge"){
					o.attackDmg = 30;
					o.attackName = "Quick Strike";
					o.maxHP = 40;
				}
				else if(name=="Athena"){
					o.attackDmg = 30;
					o.attackName = "Trickery";
					o.maxHP = 70;
				}
		}
		else if(name=="Fire Worshiper"||name=="Fire Titan"||name=="Ares"||name=="Holy Blacksmith"||name=="Hephaestus"){
				o.affinity = "FIRE";
				o.weakAgainst = "WATER";
				o.strongAgainst = "AIR";
				if(name=="Fire Worshiper"){
					o.attackDmg = 20;
					o.attackName = "Enkindle";
					o.maxHP = 60;
				}
				else if(name=="Fire Titan"){
					o.attackDmg = 30;
					o.attackName = "Fire Blast";
					o.maxHP = 90;
				}
				else if(name=="Ares"){
					o.attackDmg = 60;
					o.attackName = "Firestorm";
					o.maxHP = 120;
				}
				else if(name=="Holy Blacksmith"){
					o.attackDmg = 20;
					o.attackName = "Smelt";
					o.maxHP = 80;
				}
				else if(name=="Hephaestus"){
					o.attackDmg = 40;
					o.attackName = "Incinerate";
					o.maxHP = 100;
				}
		}
		else if(name=="Earth Worshiper"||name=="Earth Titan"||name=="Hades"||name=="Holy Farmer"||name=="Demeter"){
				o.affinity = "FIRE";
				o.weakAgainst = "WATER";
				o.strongAgainst = "AIR";
				if(name=="Fire Worshiper"){
					o.attackDmg = 20;
					o.attackName = "Enkindle";
					o.maxHP = 60;
				}
				else if(name=="Fire Titan"){
					o.attackDmg = 30;
					o.attackName = "Fire Blast";
					o.maxHP = 90;
				}
				else if(name=="Ares"){
					o.attackDmg = 60;
					o.attackName = "Firestorm";
					o.maxHP = 120;
				}
				else if(name=="Holy Blacksmith"){
					o.attackDmg = 20;
					o.attackName = "Smelt";
					o.maxHP = 80;
				}
				else if(name=="Hephaestus"){
					o.attackDmg = 40;
					o.attackName = "Incinerate";
					o.maxHP = 100;
				}
		}

		return o;
	}
}

trait Cards {
	def getName : String;
	def getType : String;
}

class Olympians(var name : String) extends Cards {
	def getName = name;
	def getType = "OLYMPIAN";
	var affinity = "";
	var weakAgainst = "";
	var strongAgainst = "";
	var attackName : String = null;
	var attackDmg : Int = 0;
	var maxHP : Int = 0;
	var currentHP : Int = 0;
	var eons : Int = 0;
	
	override def toString() : String = {
	  //output all the stats of this Olympian
	  return "";
	}
	def getAttackName = attackName;
	def setName(name : String) = {
		this.name = name;
	}
	def setAttackName(attackName : String) = {
		this.attackName = attackName;
	}
	//...
}

class Artifacts(var name : String) extends Cards {
  def getName = name;
  def getType = "ARTIFACT";
//  def effect()
//  def toString() : String
}

/*
class MagicHerbs extends Artifacts  {
  val name = "Magic Herbs"
  def effect() {
    println("Choose an Olympian to be healed")
    val a = readLine()
//    P1.field(a)
  }
}
*/

class Player(name : String, deck: ArrayBuffer[String], game : Game3) {

//	var hand : List[Cards] = null;
	var hand : ArrayBuffer[Cards] = new ArrayBuffer[Cards]();

    var arena : Olympians = null;
//	var deck : Array[String] = null;
	var field : Array[Olympians] = null;
	
	for(i <- 0 to 4)
		moveCardFromDeckToHand()
	
	def intCheck(low : Int,high : Int,a : String) : Int = {
	  var input : Int = -1000
	  try{
	    input = a.toInt
	    if (input <= low && input >= high){
	     println("Please input an integer between " + low + " and " + high)
	    }
	  } catch {
	    case ex : Exception => println("Please input an integer between " + low + " and " + high)
	  }
	  return input
	}
  
	def moveCardFromDeckToHand() = {
		if(deck.isEmpty) println("Empty deck");
		else {
			var name = deck(0);
			if(game.artifactList.contains(name))
				hand.append(new Artifacts(name));
			else
			{
				hand.append(game.createOlympianFromName(deck(0)));
			}
			deck.remove(0);
		}
	}
	
	def printField () = {
	  println( "" + "\n \n" )
       println(name +"'s ")
       println("0. Olympian in the Arena: " + arena)
       println("The Olympians in the Sactuary are:")
       for(i <- 0 to 4){
         println((i + 1).toString + ". " + field(i))
       }
       
       println("")
	}
	
	def printHand() = {
	  println("Your hand contains" + "\n")
	  var counter = 0
	  for(handCards <- hand){
	    println(counter + ". " + handCards)
	    counter+= 1
	  }
	}
	
	def switch(input : Int) = {
	  println("Choose an Olympian to switch by inputting an integer")
	  if(0 < input && input <= 5){
		   	val arenaOlympian = arena
		   	arena = field(input)
		   	field(input) = arenaOlympian
	    } else println("please enter an integer 1 through 5")
	}
	
	def getLastHandIdx() : Int = {
	  return 2;
	}
	
	def play1(cardIdx : Int)
	{
	  	hand(cardIdx) match  {
	  	  case o : Olympians => {
	  		  if(arena == null) arena = o;
	  		  else field(getLastHandIdx+1) = o;
	  	  }
	      case a : Artifacts => applyArtifact(a);
	  	}
	}
	  	
	def play(input : Int) = {   
	  var counter = 0
	  for(handcards <- hand){
	    if(counter == Int){
	      handcards match {
	        case o : Olympians => if(arena == null){
	          arena = o
	        }else{
	          var counter = 0
	          while(field(counter) != null && counter < field.length){
	            counter+= 1
	            if(counter + 1 == field.length){
	              println("Please choose an Olympian position to replace")
	              val a = readLine()
	              val numbah = intCheck(0,4,a)
	              field(numbah) = o
	            }
	          }
	          field(counter) = o
	        }
//	        case a : Artifacts => a.effect()
	        case _ => println("error: attempted to play card not of type Olympains or Artifacts")
	      }
	    }
	  }
	} 
	
	// Choose an Olympian from field
	def chooseOlympian(n : Int) : Olympians = {
	  // which one in the field to choose?
	  return new Olympians("Water Worshiper"); // testing only
	}
	
	def applyArtifact(artifact : Cards) = {
	  if(artifact.getType!="ARTIFACT") {
		  println("This is not an artifact card");
	  }
	  else {
		  var o : Olympians = null;
	      if(artifact.getName!="Holy Patronage") {
	    	  // input which olympian to apply.
	    	  println("Please choose an Olympian to apply the artifact");
	    	  val input = readLine();
	    	  o = arena;
	    	  if(input.toInt>0&&input.toInt<6)
	    		  o = field(input.toInt);
	      }
		  
		  //switch
		  artifact.getName match {
		    case "Magic Herbs" => {
		      o.currentHP += 20;
		      if(o.currentHP>o.maxHP) o.currentHP=o.maxHP;
		    }
		    case "Miracle Potion" => {
		      o.currentHP = o.maxHP;
		    }
		    case "Water Potion" => {
		      if (o.affinity == "WATER")		        
		      o.currentHP += 30;
		      else
		        o.currentHP +=10;
		      if(o.currentHP>o.maxHP) o.currentHP=o.maxHP;
		    }
		    case "Fire Potion" => {
		      if (o.affinity == "FIRE")		        
		      o.currentHP += 30;
		      else
		        o.currentHP +=10;
		      if(o.currentHP>o.maxHP) o.currentHP=o.maxHP;
		    }
		   case "Air Potion" => {
		      if (o.affinity == "AIR")		        
		      o.currentHP += 30;
		      else
		        o.currentHP +=10;
		      if(o.currentHP>o.maxHP) o.currentHP=o.maxHP;
		    }
		    case "Earth Potion" => {
		      if (o.affinity == "EARTH")		        
		      o.currentHP += 30;
		      else
		        o.currentHP +=10;
		      if(o.currentHP>o.maxHP) o.currentHP=o.maxHP;
		    }
		    case "Water Potion" => {
		      if (o.affinity == "WATER")		        
		      o.currentHP += 30;
		      else
		        o.currentHP +=10;
		      if(o.currentHP>o.maxHP) o.currentHP=o.maxHP;
		    }
		    case "Urn of Fortitude" => {
		      o.currentHP += 20;
		      o.maxHP +=o.maxHP;
		    }
		    case "Amulet of Power" => {
		      o.attackDmg += 10;
		      if(o.currentHP>o.maxHP) o.currentHP=o.maxHP;
		    }
		    case "Holy Patronage" => {
		    	moveCardFromDeckToHand;
		    	moveCardFromDeckToHand;
		    	moveCardFromDeckToHand;
		    }
		    case "Summon" => evolve(o)
		  }
	  }
	  
	}

	def evolve(anOlympian : Olympians) = {
		// check other rules to see if the olympian can evolve, such as eons.
		if(anOlympian.eons>=3) {
			val o = game.summonNextForm(anOlympian.getName);
			if(o!=null)
			{
				anOlympian.setName(o.getName)
				anOlympian.setAttackName(o.getAttackName)
				anOlympian.currentHP = o.maxHP - (anOlympian.maxHP-anOlympian.currentHP)
				anOlympian.maxHP = o.maxHP;
				anOlympian.eons = 0;
			}
		}
	}
	
	def attack(opponent : Player) {
		// if opponent.arena=null, you win and set game.isGameOver = true;
		// else do damage to opponent
		// if(opponent.HP<=0) opponent.arena=null
	  opponent.arena.currentHP = 2;
	}
}

object Game {
	
  	def main(args: Array[String])
	{	 
  		val game = new Game3(); //wat
  		game.buildSummonRule;
  		var isGameOver = false;
  		// Need to create Cards array.
  		val  deck1 = new ArrayBuffer[String]();
  		deck1.appendAll(List("Fire Worshiper", "Holy Patronage", "Magic Herbs", "Amulet of Power"));
  		val  deck2 = new ArrayBuffer[String]();
  		deck2.appendAll(List("a", "2", "3"));
  		println("Player 1, type in your name")
  		var name1= readLine()
  		println("Player 2, type in your name")
  		var name2= readLine()
  		
  		val p1 = new Player(name1, deck1, game)
  		val p2 = new Player(name2, deck2, game)
  		var currentPlayer = p1
  		var otherPlayer = p2
  		
  		def getCurrentPlayer = {
  		  currentPlayer
  		}
  		
  		while(!isGameOver){
  		  val input = readLine()
	      val parsedInput = input.split(" ")
	      parsedInput match {
	        case Array("print", "field") => {
	          otherPlayer.printField
	          currentPlayer.printField
	        }
	        case Array("attack") => {currentPlayer.attack(otherPlayer); pass};
	        case Array("print" , "hand") => currentPlayer.printHand
	        case Array("pass") => pass
	        case Array("switch" , a) => currentPlayer.switch(currentPlayer.intCheck(1,5,a))
	        case Array("play", a) => currentPlayer.play(currentPlayer.intCheck(1,currentPlayer.hand.length,a))
  		  
  		  // player.printField()
  		  // switch statement.
  		  // 1. Move olympian to arena
  		  // 2. S 1 -Switch arena to field. (i = index of hand)
  		  // 3. P 2 - move olympian from hand to field.
  		  // 4. A 1 2 - Apply artifact (i, j) i = index of artifact in hand, j = which olympian to apply.
  		  
  		  // 5. F. attack
  		  
//  			if(isGameOver)
 // 			  break;
  		  
  		  }
  		}
  		def pass() {
  		  val tmp = currentPlayer
  		  currentPlayer = otherPlayer
  		  otherPlayer = tmp
  		  
  		}

	}
}*/

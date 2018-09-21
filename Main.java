import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Processing process = new Processing();/*for all operations*/
		
		People[] person= new People[100];/*People , food , sport object created.*/
		Food[] food = new Food[100];
		Sport[] sport = new Sport[100];
		
		 PrintWriter writer = null;
	        writer = new PrintWriter(new FileOutputStream("monitoring.txt"));
		 File people_file = new File("people.txt");/*people.txt*/
          BufferedReader people_reader = new BufferedReader(new FileReader(people_file));
        	process.file_read(people_reader, people_file,person,food,sport,writer);
        	
         File food_file = new File("food.txt");/*food.txt*/
 	      BufferedReader food_reader = new BufferedReader(new FileReader(food_file));
 	     	process.file_read(food_reader,food_file,person,food,sport,writer);
 	     	
 	     File sport_file = new File("sport.txt");/*sport.txt*/
	      BufferedReader sport_reader = new BufferedReader(new FileReader(sport_file));
	        process.file_read(sport_reader,sport_file,person,food,sport,writer);
	        
	     File command_file = new File(args[0]); /*command.txt*/
            BufferedReader command_reader = new BufferedReader(new FileReader(command_file));
             process.file_read(command_reader,command_file,person,food,sport,writer);
         
         writer.close();
         
	}
}
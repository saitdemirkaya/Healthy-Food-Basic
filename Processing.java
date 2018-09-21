import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processing {

public static void file_read(BufferedReader reader,File file,People[] person,Food[] food, Sport[] sport, PrintWriter writer) throws IOException {
	String file_name = file.getName();/*Control file name*/
	
	if(file_name.equals("people.txt")) {
		int i=0;
		String line = reader.readLine();
        while (line!=null) {  	
        	String[] tokens = line.split("\t");
        	int personId = Integer.parseInt(tokens[0]);/*id,weight,height,date of birth convert to integer and adding person array*/
        	int weight = Integer.parseInt(tokens[3]);
        	int height = Integer.parseInt(tokens[4]);
        	int dateBirth = Integer.parseInt(tokens[5]);
        	person [i]= new People(personId,tokens[1],tokens[2],weight,height,dateBirth);
        	person[i].age = 2018-person[i].dateBirth;/*Calculate age*/
            line = reader.readLine();
            i++;
        }
		
	}else if(file_name.equals("food.txt")) {
        String food_line = reader.readLine();
        int i=0;
        while(food_line!=null) {
        	String[] tokens = food_line.split("\t");
        	int food_ID= Integer.parseInt(tokens[0]);/*id and cal convert integer and adding food array*/
        	int food_cal = Integer.parseInt(tokens[2]);
        	food[i] = new Food(food_ID,tokens[1],food_cal);
        	i++;
        	food_line = reader.readLine();
        }
	}else if(file_name.equals("sport.txt")) {
		
        String sport_line = reader.readLine();
        int i=0;
        while(sport_line!=null) {
        	String[] tokens = sport_line.split("\t");/*id and cal convert integer and adding sport array*/
        	int sport_ID= Integer.parseInt(tokens[0]);
	        int sport_cal = Integer.parseInt(tokens[2]);
	        sport[i] = new Sport(sport_ID,tokens[1],sport_cal);
	        i++;     
        	sport_line = reader.readLine();
        }
		
	}else if(file_name.equals("command.txt")) {
		String commands_line=reader.readLine();
        while (commands_line!=null) {
        	if(commands_line.equals("printList")) {/*first control. Command is a printList ?*/
        		for(int i=0;i<person.length;i++) {
        			if(person[i]!=null) {/*empty line control*/
        				if(person[i].operation==1) {/*the operation of the transaction persons is 1.*/
        					 if(person[i].gender.equals("male")) {/* male daily calorie needs calculate*/
               			    	person[i].dailyCalorieNeeds = (int) Math.round((66 + (13.75*person[i].weight)+ (5*person[i].height)-(6.8*person[i].age)));
               			    }else {/* female daily calorie needs calculate */
               			    	person[i].dailyCalorieNeeds = (int) Math.round((665 + (9.6*person[i].weight)+ (1.7*person[i].height)-(4.7*person[i].age)));
               			    }
               			  int result=(person[i].total_take_cal-person[i].total_burn_cal)-person[i].dailyCalorieNeeds;/*Calculate result calorie*/
               
               			  writer.println(person[i].personName+"\t"+person[i].age+"\t"+person[i].dailyCalorieNeeds+"\t"+person[i].total_take_cal+"kcal"+"\t"+ person[i].total_burn_cal+"kcal"+"\t"+result+"kcal");

        				}
        			}
        		}
                writer.println("***************");
        		
      		 }else if(commands_line.startsWith("print")) {/*print(personID)*/
      			Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(commands_line);/*to get the personID in brackets.*/
      			int find_personID;
      			while(m.find()) {
      			    find_personID = Integer.parseInt(m.group(1));/*The value found and assign to find_personID.*/
      			    find_personID=find_person(find_personID,person);
      			    
      			    if(person[find_personID].gender.equals("male")) {/* male*/
      			    	person[find_personID].dailyCalorieNeeds = (int) Math.round((66 + (13.75*person[find_personID].weight)+ (5*person[find_personID].height)-(6.8*person[find_personID].age)));
      			    }else {/*female*/
      			    	person[find_personID].dailyCalorieNeeds = (int) Math.round((665 + (9.6*person[find_personID].weight)+ (1.7*person[find_personID].height)-(4.7*person[find_personID].age)));
      			    }
      			    /*result calorie*/
      			    int result=(person[find_personID].total_take_cal-person[find_personID].total_burn_cal)-person[find_personID].dailyCalorieNeeds;
      writer.println(person[find_personID].personName+"\t"+person[find_personID].age+"\t"+person[find_personID].dailyCalorieNeeds+"\t"+person[find_personID].total_take_cal+"kcal"+"\t"+ person[find_personID].total_burn_cal+"kcal"+"\t"+result+"kcal");
      writer.println("***************");
      			}
      			 
      		 }else {/*operations on person.*/
      			String[] tokens = commands_line.split("\t");
      			 int findID = Integer.parseInt(tokens[0]);/*first element of the line in the script.It is personID*/
      			 int personID = find_person(findID,person);/*finding personID*/
      			 person[personID].operation=1;/*person operation 1.For the printList.*/
      			 int findFoodOrSportID = Integer.parseInt(tokens[1]);/*Second element sport or food*/
      			 int piece = Integer.parseInt(tokens[2]);
      			 int foodID = find_foodID(findFoodOrSportID,food);/*firsly goes to foodID.If don't find, it is return -1 and goes to sportID*/
      			
      			 	if(foodID==-1) {
	      				 int sportID = find_sportID(findFoodOrSportID,sport);
	      				int calori = piece*sport[sportID].sport_calorie/60;/*calculate burning calorie*/
	      				person[personID].total_burn_cal=person[personID].total_burn_cal+calori;/*adding person total burn.*/
	      				writer.println(person[personID].personID+"\t"+"has burned"+"\t"+calori+"kcal thanks to"+"\t"+sport[sportID].nameSport);
	      				writer.println("***************");
      			 	}else {
	      				int calori = piece*food[foodID].food_calorie;/*calculate taken calorie*/
	      				person[personID].total_take_cal=person[personID].total_take_cal+calori;/*adding person total taken*/
	      				 writer.println(person[personID].personID+"\t"+"has taken"+"\t"+calori+"kcal from "+"\t"+food[foodID].nameFood);
	      				writer.println("***************");
      			 	}
      		 }
        	 commands_line=reader.readLine();
        }
	}else {
		System.out.println("unknown file name!");
	}
	
	
}
public static int find_foodID(int ID,Food[] food) {/*finding foodID*/
	for(int i=0;i<food.length;i++) {
		if(food[i]!=null) {			/*be must not empty line*/
			if(ID==food[i].foodID) {
				return i;/*return foodID*/
			}
		}
	}
	return -1;/*if don't find, return -1*/
}
public static int find_sportID(int ID,Sport[] sport) {
	for(int i=0;i<sport.length;i++) {
		if(sport[i]!=null) {
			if(ID==sport[i].sportID) {
				return i;/*return sportID*/
			}
		}			
	}
	return -1;
}
public static int find_person(int personID,People[] person) {/*finding personID*/
	for(int i=0;i<person.length;i++) {
		if(person[i]!=null) {
			if(personID==person[i].personID) {
				return i;
			}
		}
		
	}	
	return -1;
}	
}
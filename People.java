
public class People {
	
	public People(int personId2, String person_name, String person_gender, int weight, int height, int dateBirth) {
		this.personID=personId2;
		this.personName = person_name;
		this.gender=person_gender;
		this.weight=weight;
		this.height=height;
		this.dateBirth=dateBirth;
	}
	int personID;
	String personName;
	String gender;
	int weight;
	int height;
	int dateBirth;
	int operation = 0;/*person operations.*/
	int total_take_cal = 0;
	int total_burn_cal = 0;
	int dailyCalorieNeeds;
	int age;
}

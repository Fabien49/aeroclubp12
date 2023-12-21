package com.fabienit.flyingclub;

import com.fabienit.flyingclub.model.beans.Aircraft;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AeroclubApiApplication implements CommandLineRunner{





	public static void main(String[] args) {



		SpringApplication.run(AeroclubApiApplication.class, args);
		 {
			 List<Aircraft> aircraftList = new ArrayList<>();
/*			 aircraftList.add(new Aircraft(1, "du", "dd", "do", "de", 4, 4, "ecole",159, true  ));
			 aircraftList.add(new Aircraft(2, "mu", "md", "mo", "me", 2, 5, "voyage",1549, false  ));
			 aircraftList.add(new Aircraft(3, "tu", "td", "to", "te", 4, 4, "voyage",7899, true  ));
			 aircraftList.add(new Aircraft(4, "su", "sd", "so", "se", 2, 4, "ecole",2019, false  ));
			 aircraftList.add(new Aircraft(5, "xu", "xd", "xo", "xe", 4, 5, "ecole",358, true  ));*/

			 List<Aircraft> aircraftAvailableList = new ArrayList<>();
			 System.out.println("La liste des avions disponibles est la suivante : " );
			aircraftList.stream().filter(aircraft -> aircraft.getAvailable().equals(true));

			 System.out.println(aircraftList);
			 }

		}
	public void run(String... args) throws Exception{
		}

}

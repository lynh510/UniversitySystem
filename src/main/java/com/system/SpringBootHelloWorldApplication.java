package com.system;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.system.models.AcademicYearManagement;

@SpringBootApplication
public class SpringBootHelloWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHelloWorldApplication.class, args);
		Timer timer = new Timer();
		TimerTask hourlyTask = new TimerTask() {
			@Override
			public void run() {
				System.out.println("Checking academic year: " + new Date());
				AcademicYearManagement aym = new AcademicYearManagement();
				aym.check_close_date();
				aym.check_final_close_date();
			}
		};
		timer.schedule(hourlyTask, 24, 1000 * 60 * 60);
	}
}

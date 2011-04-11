package builtin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import base.EvolverState;
import framework.EvolverStateProcess;

public class RecordBestScoreToFile implements EvolverStateProcess {

	String filename;
	FileWriter fw;
	Calendar calendar;
	SimpleDateFormat sdf;
	BufferedWriter br;
	
	public void Initialize(String parameters) {
		try {
			filename = parameters;
			fw = new FileWriter(filename,false);
			br = new BufferedWriter(fw);
			calendar = Calendar.getInstance();
			sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Process(EvolverState es) {
		try {
			String toWrite = sdf.format(calendar.getTime()); 
			toWrite += "," + es.getGenerationNumber();
			toWrite += "," + es.getPopulation().get(0).getFitnessScore();
			toWrite += "\n";
			br.write(toWrite);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Terminate() {
		try {
			br.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
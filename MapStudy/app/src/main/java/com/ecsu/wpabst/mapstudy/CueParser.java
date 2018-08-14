import java.io.BufferedReader;

public class CueParser {
	public static void cueParse() {
		
		String[] cueType;
		String[] cueCommand;
		Float[] cueDistance;
		Float[] cueElevation;
			
		BufferedReader in = null;
		String thisLine = null;
	      
		try {
			BufferedReader br = new BufferedReader("Norwich_Colchester_Loop_CueSheet.csv");
			int i = 0;
			while ((thisLine = br.readLine()) != null) {
				System.out.println(thisLine);
				String[] parts = thisLine.split(",");
				cueType[i] = parts[0];
				cueCommand[i] = parts[1];
				cueDistance[i] = Float.valueOf(parts[2]);
				cueElevation[i] = Float.valueOf(parts[3]);
			}       
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		//Synthesize Audio
		try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
		    // Set the text input to be synthesized
		    SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

		    // Build the voice request
		    VoiceSelectionParams voice =
		        VoiceSelectionParams.newBuilder()
		            .setLanguageCode("en-US") // languageCode = "en_us"
		            .setSsmlGender(SsmlVoiceGender.FEMALE) // ssmlVoiceGender = SsmlVoiceGender.FEMALE
		            .build();

		    // Select the type of audio file you want returned
		    AudioConfig audioConfig =
		        AudioConfig.newBuilder()
		            .setAudioEncoding(AudioEncoding.MP3) // MP3 audio.
		            .build();

		    // Perform the text-to-speech request
		    SynthesizeSpeechResponse response =
		        textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

		    // Get the audio contents from the response
		    ByteString audioContents = response.getAudioContent();

		    // Write the response to the output file.
		    try (OutputStream out = new FileOutputStream("output.mp3")) {
		      out.write(audioContents.toByteArray());
		      System.out.println("Audio content written to file \"output.mp3\"");
		    }
		    
		    // Playback output file 
		    MediaPlayer mp = MediaPlayer.create(MapActivity.this, R.raw.output);
		    mp.start();
		    
		    // Delete output file
		    try
	        {
	            Files.deleteIfExists(Paths.get("output.mp3"));
	        }
	        catch(NoSuchFileException e)
	        {
	            System.out.println("No such file/directory exists");
	        }
	        catch(DirectoryNotEmptyException e)
	        {
	            System.out.println("Directory is not empty.");
	        }
	        catch(IOException e)
	        {
	            System.out.println("Invalid permissions.");
	        }
	         
	        System.out.println("Deletion successful.");
		
		}
	}
}

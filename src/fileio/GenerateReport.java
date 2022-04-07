package fileio;

import java.io.*;

public class GenerateReport
{
    public static void main(String[] args)
    {
        //variables for arguments from main
        String sourcePath;
        int numberOfRecords;

        //variables for reading source file
        String record;
        String state;
        int population;
        int childPopulation;
        int childPovertyPopulation;

        //variables for calculating report totals
        String[] stateCodes = {"01", "02", "04", "05", "06", "08", "09", "10", "11", "12", "13", "15", "16", "17", "18",
                "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35",
                "36", "37", "38", "39", "40", "41", "42", "44", "45", "46", "47", "48", "49", "50", "51", "53", "54",
                "55", "56"};
        int totalPopulation;
        int totalChildPopulation;
        int totalChildPovertyPopulation;
        float percentChildPoverty;

        try
        {
            if ( args.length < 2 ) throw new IllegalArgumentException( "Two arguments are required. " +
                    "source file path, number of records." );

            //set arg inputs
            sourcePath = args[ 0 ];
            numberOfRecords = Integer.parseInt( args[ 1 ] );

            //create file objects with source and destination inputs
            File fIn = new File( sourcePath );

            //source file check
            if ( ! fIn.exists( ) )
            {
                System.out.println("Source file not found." );
                System.exit( 1 );
            }
            if(!fIn.canRead())
            {
                System.out.println("Cannot read source file." );
                System.exit( 1 );
            }

            //print header to System.out
            System.out.printf("%nFile: " + sourcePath + "%n");
            System.out.printf("%n%5s %10s %16s %24s %15s%n", "State", "Population", "Child Population", "Child Poverty Population", "% Child Poverty");
            System.out.printf("----- ---------- ---------------- ------------------------ ---------------%n");

            for(int j = 0; j < stateCodes.length; j++)
            {
                try( BufferedReader input = new BufferedReader( new FileReader( fIn ) ) )
                {
                    //reset totals for next state
                    totalPopulation = 0;
                    totalChildPopulation = 0;
                    totalChildPovertyPopulation = 0;
                    percentChildPoverty = 0;

                    for(int i = 0; i < numberOfRecords; i++)
                    {
                        record = input.readLine();
                        state = record.substring(0,5).trim();

                        if(state.equals(stateCodes[j]))
                        {
                            //read in values for each line
                            population = Integer.parseInt(record.substring( 6, 16 ).trim());
                            childPopulation = Integer.parseInt(record.substring( 17, 33 ).trim());
                            childPovertyPopulation = Integer.parseInt(record.substring( 34, 58 ).trim());

                            //accumulate values into totals
                            totalPopulation += population;
                            totalChildPopulation += childPopulation;
                            totalChildPovertyPopulation += childPovertyPopulation;
                        }
                    }
                    percentChildPoverty = ((float)totalChildPovertyPopulation / (float)totalChildPopulation)*100;
                    System.out.printf("%5s %,10d %,16d %,24d %,15.2f%n", stateCodes[j], totalPopulation, totalChildPopulation, totalChildPovertyPopulation, percentChildPoverty);
                }
                catch(IOException e1)
                {
                    e1.printStackTrace();
                    System.exit( 1 );
                }
            }
        }
        catch ( IllegalArgumentException e0 )
        {
            e0.printStackTrace();
            System.exit( 1 );
        }
    }
}

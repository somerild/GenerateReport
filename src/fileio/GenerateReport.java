package fileio;

import java.io.*;

public class GenerateReport
{
    public static void main(String[] args)
    {
        String sourcePath; // data source file path
        int numberOfRecords; //number of records in the data source

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

            try( BufferedReader input = new BufferedReader( new FileReader( fIn ) ) )
            {
                String record;
                int state;
                int population;
                int childPopulation;
                int childPovertyPopulation;

                //print header to System.out
                System.out.printf("%nFile: " + sourcePath + "%n");
                System.out.printf("%n%5s %10s %16s %24s %15s%n", "State", "Population", "Child Population", "Child Poverty Population", "% Child Poverty");
                System.out.printf("----- ---------- ---------------- ------------------------ ---------------%n");

                //loop through records
                for(int i = 0; i < numberOfRecords; i++)
                {
                    record = input.readLine();
                    state = Integer.parseInt(record.substring(0,5).trim());
                    population = Integer.parseInt(record.substring( 6, 16 ).trim());
                    childPopulation = Integer.parseInt(record.substring( 17, 33 ).trim());
                    childPovertyPopulation = Integer.parseInt(record.substring( 34, 58 ).trim());

                }
            }
            catch(IOException e1)
            {
                e1.printStackTrace();
                System.exit( 1 );
            }
        }
        catch ( IllegalArgumentException e0 )
        {
            e0.printStackTrace();
            System.exit( 1 );
        }
    }
}

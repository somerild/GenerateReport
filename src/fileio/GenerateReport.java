package fileio;

import java.io.*;

public class GenerateReport
{
    public static void main(String[] args)
    {
        String sourcePath = null; // data source file path
        int numberOfRecords = 0; //number of records in the data source
        File in = null; //source file
        String[] stateCodes = { "01", "02", "04", "05", "06", "08", "09", "10", "11", "12", "13", "15", "16", "17", "18",
                "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35",
                "36", "37", "38", "39", "40", "41", "42", "44", "45", "46", "47", "48", "49", "50", "51", "53", "54",
                "55", "56" };
        String stateCode;
        int population;
        int childPopulation;
        int childPovertyPopulation;

        //verify the right number of arguments and set
        try
        {
            sourcePath = args[ 0 ];
            numberOfRecords = Integer.parseInt( args[ 1 ] );
            if ( numberOfRecords == 0 ) throw new IllegalArgumentException( "Number of records is required." );
            if ( numberOfRecords <= 0 ) throw new IllegalArgumentException( "Invalid number of records." );
        } catch ( Exception e )
        {
            System.out.println( "Missing or invalid parameters." );
            e.printStackTrace( );
            System.exit( 1 );
        }

        //create file objects, verify
        try
        {
            in = new File( sourcePath );
            if ( ! in.exists( ) ) throw new FileNotFoundException( "File does not exist." );
            if ( ! in.canRead( ) ) throw new IOException( "Cannot read file." );
        } catch ( Exception e1 )
        {
            e1.printStackTrace( );
            System.exit( 1 );
        }

        //print header to terminal
        System.out.printf( "%nFile: " + sourcePath + "%n" );
        System.out.printf( "%n%5s\t%10s\t%16s\t%24s\t%15s%n", "State", "Population", "Child Population", "Child Poverty Population", "% Child Poverty" );
        System.out.printf( "-----\t----------\t----------------\t------------------------\t---------------%n" );

        for ( String code : stateCodes )
        {
            try ( BufferedReader input = new BufferedReader( new FileReader( in ) ) )
            {
                int totalPopulation = 0;
                int totalChildPopulation = 0;
                int totalChildPovertyPopulation = 0;
                float percentChildPoverty;

                input.readLine( );

                for ( int j = 0; j < numberOfRecords; j++ )
                {
                    String record = input.readLine( );
                    stateCode = record.substring( 0, 6 ).trim( );

                    if ( stateCode.equals( code ) )
                    {
                        population = Integer.parseInt( record.substring( 6, 17 ).trim( ) );
                        childPopulation = Integer.parseInt( record.substring( 17, 34 ).trim( ) );
                        childPovertyPopulation = Integer.parseInt( record.substring( 34, 58 ).trim( ) );

                        totalPopulation += population;
                        totalChildPopulation += childPopulation;
                        totalChildPovertyPopulation += childPovertyPopulation;
                    }
                }
                percentChildPoverty = ( ( float ) totalChildPovertyPopulation / ( float ) totalChildPopulation ) * 100;
                System.out.printf( "%5s\t%,10d\t%,16d\t%,24d\t%,15.2f%n", code, totalPopulation, totalChildPopulation, totalChildPovertyPopulation, percentChildPoverty );
            } catch ( Exception e2 )
            {
                e2.printStackTrace( );
            }
        }
    }
}

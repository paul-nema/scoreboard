/**
 * Created by PaulN on 3/13/2017.
 */
import java.util.Scanner;
import java.io.*;
import java.util.StringJoiner;

public class ScoreBoard {

    public static void Commands() {
        System.out.println( "To add a score enter: TeamName scored" );
        System.out.println( "To see all scores enter: scores" );
        System.out.println( "To exit enter: Game Over" );
        System.out.println( "For help enter: help" );
        System.out.println( "List teams in game enter: teams" );
        System.out.println( "NOTE: All source is maintained with git" );
    }

    public static void Usage() {
        System.out.println( "Usage:" );
        System.out.println( "When the program starts you will be prompted for:" );
        System.out.println( "\tNumber of teams (2 through 4)" );
        System.out.println( "\tEach team name\n" );
        System.out.println( "Then the game will begin you will be prompted for commands" );
        System.out.println( "NOTE, all input is case insensitive\n" );
        Commands();
    }

    public static int NumberOfTeams ( Scanner scanner ) {
        int teams = 0;  // return value

        boolean validNumber = false;

        while ( validNumber == false) {
            System.out.print( "\nHow many teams are in this event? " );   // prompt user

            String numOfTeams = scanner.nextLine().trim();  // Get input

            try {
                teams = Integer.parseInt( numOfTeams ); // convert to int

                if ( teams > 4 ) {  // only allow up to four teams at the moment
                    System.out.println( "You can only have up to 4 teams\n" );
                } else if ( teams < 2 ) {   // need at least 2 teams
                    System.out.println( "You need at least two teams\n" );
                } else {
                    validNumber = true; // success
                }
            } catch (NumberFormatException e) {
                System.out.println( "You did not enter a valid number\n" );
            }
        }

        return( teams );
    }

    public static void TeamNames ( Team[] teams, Scanner scanner ) {
        int teamNumber = 1; // use for user prompt

        String teamName = "";

        for ( int i=0; i < teams.length; i++ ) {
            System.out.println( "\nEnter the name for team " + teamNumber++ );

            teamName = scanner.nextLine().toUpperCase();  // read in name

            if ( i == 0 ) { // no checking for repeats on the first team name
                teams[ i ] = new Team( teamName );
            } else {
                boolean repeat = false;

                for ( int x=0; x < i; x++ ) {   // do not allow repeat names

                    if ( teamName.equals( teams[ x ].getName() ) ) {
                        repeat = true;

                        System.out.println( "That name is used aready!");
                    }
                }

                if ( ! repeat ) {
                    teams[ i ] = new Team( teamName );  // No repeats found
                } else {
                    i--;    // name repeated. Reduce counter for a do over
                    teamNumber--;
                }
            }
        }
    }

    public static void main( String [] args ) {
        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        String cmd = "";

        int count = 0;
        int numberOfTeams = NumberOfTeams( scanner );

        Team[] teams = new Team[ numberOfTeams ];

        String[] cmdTokens;

        TeamNames( teams, scanner );

        System.out.println("\nGame On!\n");
        Commands();
        System.out.println();

        while ( true ) {
            System.out.print( "cmd = " );   // prompt user
            cmd = scanner.nextLine().trim().toUpperCase();    // Get user input

            cmdTokens = cmd.split(" "); // split input on space

            switch( cmdTokens[ 0 ] ) {
                case "TEAMS":
                    for ( int i=0; i < teams.length; i++ ) {
                        System.out.print( "Teams in this game are: " );
                        System.out.println( teams[ i ].getName() );
                    }

                    break;
                case "GAME":
                    if ( cmdTokens[ 1 ].equals( "OVER" ) ) {
                        System.out.println( "Thanks you for using ScoreBoard" );
                        System.exit( 0 );
                    }

                    System.out.println( "Unknown command: " + cmd );
                    Commands();

                    break;
                case "SCORES":
                    for ( int i=0; i < teams.length; i++ ) {
                        System.out.println( "Team " + teams[ i ].getName() + " score is " + teams[ i ].getScore() );
                    }

                    break;
                case "HELP":
                    Usage();

                    break;
                default:
                    // See if the first token is a team name
                    boolean found = false;

                    for ( int i=0; i < teams.length; i++ ) {

                        if ( cmdTokens[ 0 ].equals( teams[ i ].getName() ) ) {

                            if ( cmdTokens[ 1 ].equals( "SCORED" ) ) {

                                teams[ i ].scored( 1 );   // increment score

                                found = true;

                                i = teams.length;   // break out of the loop
                            }
                        }
                    }

                    if ( ! found ) {
                        System.out.println( "Either the team name is incorrect or the second word wasn't \"scored\"" );
                        System.out.println( "Or the command is unknown\n" );

                        Commands();

                        System.out.println();
                    }

                    break;
            }   // switch
        } // while
    }
}

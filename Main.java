import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.Random; 


public class Main{
    
    public static void main(String[] args) {
        
        ArrayList<Ship> ships = new ArrayList<Ship>();
        ArrayList<Ship> compShips = new ArrayList<Ship>();
        Scanner gameOption = new Scanner(System.in);
        int option;

        System.out.println("1 - player vs player/ 2 - player vs computer/  3 - simulation");
        option = gameOption.nextInt();

        if (option == 2){
            Ocean ocean = new Ocean();
            Ocean compOcean = new Ocean();
            ocean.createOcean();
            compOcean.createOcean();
            shipCreation(ocean,ships);
            autoShipCreation(compOcean, compShips);
     
            while (compShips.size() != 0 || ships.size() != 0){
                compOcean.printCompOcean();
                ocean.printOcean();
                shoot(compOcean, compShips);
                System.out.println("Computer shooting!");
                autoFire(ocean, ships);
            }

            if (compShips.size() == 0){System.out.println("CONGRATULATIONS");}
        }

        else if (option == 3){
            Ocean player1ocean = new Ocean();
            Ocean player2ocean = new Ocean();
            player1ocean.createOcean();
            player2ocean.createOcean();
            autoShipCreation(player1ocean, ships);
            autoShipCreation(player2ocean, compShips);
     
            while (compShips.size() != 0 || ships.size() != 0){
                player1ocean.printOcean();
                player2ocean.printOcean();

                System.out.println("Player 1 shooting!");
                autoFire(player2ocean, compShips);
                sleep(2);
                System.out.println("Player 2 shooting!");
                sleep(2);
                autoFire(player1ocean, ships);
            }

            if (compShips.size() == 0){System.out.println("CONGRATULATIONS");}
        }
        
       
    }


    public static int[] getAutoFireCoordinates(Ocean ocean){
        
                int[] fireCoordinates = new int[2];
                boolean cond = true;
        
                while(cond){
                    fireCoordinates[0] = generateRandomInt(9, 0);
                    fireCoordinates[1] = generateRandomInt(9, 0);
                    if (ocean.ocean[fireCoordinates[0]][fireCoordinates[1]].sign.equals("m")){
                        cond = true;
                    }
                    else if (ocean.ocean[fireCoordinates[0]][fireCoordinates[1]].sign.equals("O")){
                        cond = true;
                    }
                    else{cond = false;}


                }
                return fireCoordinates;
            }


    public static void autoFire(Ocean ocean, ArrayList<Ship> ships){

        int[] fireCoordinates = getAutoFireCoordinates(ocean);
        int xShoot = fireCoordinates[0];
        int yShoot = fireCoordinates[1];


        if (ocean.ocean[yShoot][xShoot].sign == "."){
            ocean.ocean[yShoot][xShoot].missed();
            System.out.println("MISS!");
        }
        else if (ocean.ocean[yShoot][xShoot].sign == "x"){
            ocean.ocean[yShoot][xShoot].shipHit();
            System.out.println("YOU SUFFER HIT");
            checkHitPlayerShip(ships, fireCoordinates);
        }

        else{
            System.out.print("SHOOTING ERROR");
        }
        System.out.println();
    }


    public static void checkHitPlayerShip(ArrayList<Ship> ships, int[] fireCoordinates){
        for (int i =0; i < ships.size(); i++){
            
            for (int j =0; j< ships.get(i).occupiedCoordinates.length; j++){
            
                if (fireCoordinates[0] == (ships.get(i).occupiedCoordinates[j][0]) && fireCoordinates[1] == ships.get(i).occupiedCoordinates[j][1]){
                    System.out.println("Computer hits your " + ships.get(i).name);
                    ships.get(i).lenght --;
                    if (ships.get(i).lenght == 0){
                        System.out.println("YOU LOST " + ships.get(i).name);
                    }
                }
            }                               
        }
    }



    public static void autoShipCreation(Ocean compOcean, ArrayList<Ship> compShips){
        boolean properPosition = false;
        
                while (properPosition == false){
                    properPosition = autoCreateShip(5, compOcean, "CARIER", compShips);
                }
        
                properPosition = false;
        
                while(properPosition == false){
                    properPosition = autoCreateShip(4, compOcean, "CRUISER", compShips);
                }
        
                properPosition = false;
        
                while(properPosition == false){
                    properPosition = autoCreateShip(2, compOcean, "DESTROYER", compShips);
                }
    }


    public static boolean autoCreateShip(int length, Ocean compOcean, String name, ArrayList<Ship> compShips){

        int xPosition, yPosition;
        String positionMode;
        String[] possiblePositions = {"hor","ver"};
        int[][] occupiedCoordinates;

       
        xPosition = generateRandomInt(9 - length,0);
        yPosition = generateRandomInt(9 - length,0);

        positionMode = possiblePositions[generateRandomInt(1, 0)];
        occupiedCoordinates = getOccupiedCoordinates(length,positionMode,xPosition,yPosition);
     

        for (int i =0; i < occupiedCoordinates.length; i++){ // checking if there is a free space for the ship
            if (compOcean.ocean[occupiedCoordinates[i][0]][occupiedCoordinates[i][1]].sign.equals("x")){
                return false;
            }   
        }

        compOcean.placeShip(length, xPosition, yPosition, positionMode);
        Ship ship = new Ship(length, occupiedCoordinates, name);
        compShips.add(ship);
        return true;
    }


    public static int generateRandomInt(int max, int min){

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }





    public static void checkHitCompShip(ArrayList<Ship> compShips, int[] fireCoordinates){
        for (int i =0; i < compShips.size(); i++){
            
            for (int j =0; j< compShips.get(i).occupiedCoordinates.length; j++){
            
                if (fireCoordinates[0] == (compShips.get(i).occupiedCoordinates[j][0]) && fireCoordinates[1] == compShips.get(i).occupiedCoordinates[j][1]){
                    System.out.println("You hit " + compShips.get(i).name);
                    compShips.get(i).lenght --;
                    if (compShips.get(i).lenght == 0){
                        System.out.println("Hit and sunk " + compShips.get(i).name);
                        compShips.remove(i);
                        System.out.println("Ships left: " + compShips.size());
                    }
                }
            }                               
        }
    }


    public static boolean shoot(Ocean compOcean, ArrayList<Ship> compShips){

        int[] fireCoordinates = getFireCoordinates();
        int xShoot = fireCoordinates[0];
        int yShoot = fireCoordinates[1];
        boolean hit;


        if (compOcean.ocean[yShoot][xShoot].sign == "."){
            compOcean.ocean[yShoot][xShoot].compShipMiss();
            System.out.println("You missed!");
            hit = false;
        }
        else if (compOcean.ocean[yShoot][xShoot].sign == "x"){
            compOcean.ocean[yShoot][xShoot].compShipHit();
            System.out.print("Successful hit!");
            checkHitCompShip(compShips, fireCoordinates);
            hit = true;
        }

        else{
            System.out.print("You have already shooted at this position!");
            hit = false;
        }
        System.out.println();
        return hit;


    }


    public static void shipCreation(Ocean ocean, ArrayList<Ship> ships){
        boolean properPosition = false;

        while (properPosition == false){
            ocean.printOcean();
            System.out.println("Place your ship:");
            properPosition = createShip(5, ocean, "CARIER", ships);
        }

        properPosition = false;

        while(properPosition == false){
            ocean.printOcean();
            System.out.println("Place your ship:");
            properPosition = createShip(4, ocean, "CRUISER", ships);
        }

        properPosition = false;

        while(properPosition == false){
            ocean.printOcean();
            System.out.println("Place your ship:");
            properPosition = createShip(2, ocean, "DESTROYER", ships);
        }
        
        
    }


    public static int[] getFireCoordinates(){

        int[] fireCoordinates = new int[2];
        int xFireCoordinate, yFireCoordinate;
        Scanner inputCoordinates = new Scanner(System.in);
        boolean cond = true;

        while(cond){
            System.out.println("X coordinate:");
            xFireCoordinate = inputCoordinates.nextInt();
            fireCoordinates[0] = xFireCoordinate;
    
            System.out.println("Y coordinate");
            yFireCoordinate = inputCoordinates.nextInt();
            fireCoordinates[1] = yFireCoordinate;
    
            if (xFireCoordinate <10 && yFireCoordinate <10){
                cond = false;
            }
            else{System.out.println("Coordinates must be numbers between 0 and 9");}
        }
        return fireCoordinates;
    }

    

    public static int[][] getOccupiedCoordinates(int length, String positionMode, int xPosition, int yPosition){

        int[][] occupiedCoordinates = new int[length][2];

        if (positionMode.equals("hor") && xPosition +length <10){

            for (int i = 0; i < length; i++){
                occupiedCoordinates[i][0] = xPosition;
                occupiedCoordinates[i][1] = yPosition;
                xPosition++;
            }
        }
            
        else if (positionMode.equals("ver") && yPosition + length <10){

            for (int i = 0; i < length; i++){
                occupiedCoordinates[i][0] = xPosition;
                occupiedCoordinates[i][1] = yPosition;
                yPosition++;
            }
        }
            
        else{;
            System.out.println("DUPA");
            }

        return occupiedCoordinates;
    }


    public static boolean createShip(int length, Ocean ocean, String name, ArrayList<Ship> ships){

        Scanner input = new Scanner(System.in);
        int xPosition, yPosition;
        String positionMode;
        int[][] occupiedCoordinates = new int[length][2];

        System.out.println("X Position:");
        xPosition = input.nextInt();
        System.out.println("Y Position:");
        yPosition = input.nextInt();
        System.out.println("Position mode:");
        positionMode = input.next();

        
        occupiedCoordinates = getOccupiedCoordinates(length,positionMode,xPosition,yPosition);

        for (int i =0; i < occupiedCoordinates.length; i++){ // checking if there is a free space for the ship
            if (ocean.ocean[occupiedCoordinates[i][0]][occupiedCoordinates[i][1]].sign.equals("x")){
                System.out.println("You cannot place your ship here!");
                return false;
            }   
        }

        ocean.placeShip(length, xPosition, yPosition, positionMode);
        Ship ship = new Ship(length, occupiedCoordinates, name);
        ships.add(ship);
        return true;
    }

    private static void sleep(int seconds){
        try{
            TimeUnit.SECONDS.sleep(2);
        }
        catch(InterruptedException e){};
    }

     
}
public class Ocean{

    Square[][] ocean;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    

    public void createOcean(){

        ocean = new Square[10][10];
        
                // OCEAN [Y][X]
        

        for (int i =0 ; i< ocean.length; i++){
            for (int j =0; j< ocean.length; j++){
                ocean[i][j]= new Square();
            }
        }
    }

    public void placeShip(int shipLength, int xPosition, int yPosition, String positionMode){
        try{

            if (positionMode.equals("hor")&& xPosition + shipLength < 10){

                for(int a = 0; a < shipLength; a++){

                    ocean[yPosition][xPosition].shipPlaced();
                    xPosition++;
                } 
            
            }
            else if (positionMode.equals("ver")&& yPosition + shipLength < 10){
                
                for(int a = 0; a < shipLength; a++){
                
                    ocean[yPosition][xPosition].shipPlaced();
                    yPosition++;
                } 
            }
        }
        catch (ArrayIndexOutOfBoundsException e){System.out.println("Index out of range");}
        
    }


    public void printOcean(){
        int[] numbers = {0,1,2,3,4,5,6,7,8,9};

        System.out.print(" ");

        for (int i =0; i< numbers.length; i++) {System.out.print(numbers[i]);}

        System.out.println();

        for (int a =0; a <ocean.length; a++){
            System.out.print(a);

            for (int b =0; b <ocean.length; b++){
                String sign = ocean[a][b].returnSign();

                if (sign.equals(".")){System.out.print(ANSI_BLUE + sign + ANSI_RESET);}
                else if (sign.equals("x")){System.out.print(ANSI_YELLOW + sign + ANSI_RESET);}
                else if (sign.equals("m")){System.out.print(ANSI_WHITE + sign + ANSI_RESET);}
                else if (sign.equals("O"))System.out.print(ANSI_RED + sign + ANSI_RESET);
                
            }
            System.out.println();
        }
        //System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
    }

    public void printCompOcean(){
        int[] numbers = {0,1,2,3,4,5,6,7,8,9};
        
                System.out.print(" ");
        
                for (int i =0; i< numbers.length; i++) {System.out.print(numbers[i]);}
        
                System.out.println();
        
                for (int a =0; a <ocean.length; a++){
                    System.out.print(a);
        
                    for (int b =0; b <ocean.length; b++){
                        String sign = ocean[a][b].returnHiddenSign();
        
                        if (sign.equals(".")){System.out.print(ANSI_BLUE + sign + ANSI_RESET);}
                        else if (sign.equals("m")){System.out.print(ANSI_WHITE + sign + ANSI_RESET);}
                        else if (sign.equals("O"))System.out.print(ANSI_RED + sign + ANSI_RESET);
                        
                    }
                    System.out.println();
                }
    }


}
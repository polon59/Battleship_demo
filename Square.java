public class Square{
    String sign = ".";
    String hiddenSign = ".";
    
    
    public void shipPlaced(){
        sign = "x";
    }


    public void shipHit(){
        sign = "O";
    }


    public void missed(){
        sign = "m";
    }


    public String returnSign(){
        return sign;
    }

    public void compShipHit(){
        hiddenSign = "O";
    }

    public void compShipMiss(){
        hiddenSign = "m";
    }

    public String returnHiddenSign(){
        return hiddenSign;
    }


}
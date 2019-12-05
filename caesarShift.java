public class caesarShift {

    final int asciiMin = 32;
    final int asciiMax =126;

    private char [] unusableChars;
    private char shiftC;
    private String cipherText = "";
    private String omissions;

    // cConstructor given a string and shift number

    public caesarShift(String plaintext, int shiftNo){

        shiftNo = shiftNo % (asciiMax-asciiMin);

        if(shiftNo == 0)
            shiftNo = 1;

        for(int i = 0; i < plaintext.length(); i++){
            shiftC = plaintext.charAt(i); // Set shiftC to current index
            shift(shiftNo); // Shift the indexed character by the appropriate amount of times.
            cipherText += shiftC;
        }
    }

    // Constructor with max length

    public caesarShift(String plaintext, int shiftNo, int maxLength){

        shiftNo = shiftNo % (asciiMax-asciiMin);

        if(shiftNo == 0){
            shiftNo = 1;
        }

        if(maxLength > plaintext.length())
            maxLength = plaintext.length();

        for(int i = 0; i < maxLength; i++){ // shift the indexed character by the requested amount of characters
            shiftC = plaintext.charAt(i); // Set shiftC to current index
            shift(shiftNo);
            cipherText += shiftC;
        }
    }

    // Constructor with omissions

    public caesarShift(String plaintext, int shiftNo, String oms){

        omissions = oms;
        shiftNo = shiftNo % (asciiMax-asciiMin);

        if(shiftNo == 0)
            shiftNo = 1;

        for(int i = 0; i < plaintext.length(); i++){

            //add shiftNo to the ascii value of each character, shifting it by that amount
            //determine whether the ascii value exceeds 126 and loop back to space

            shiftC = plaintext.charAt(i); // Set shiftC to current index

            for(int j=0; j < shiftNo; j++){
                while(searchOmissions()) // Skip a character if it must be omitted
                    shift();
                shift(); // Shift the indexed character by 1
            }
            while(searchOmissions()) // Skip a character if it must be omitted
                shift();
            cipherText += shiftC;
        }
    }

    // Constructor with omissions *AND* max length

    public caesarShift(String plaintext, int shiftNo, String oms, int maxLength){

        omissions = oms;
        shiftNo = shiftNo % (asciiMax-asciiMin);

        if(shiftNo == 0){
            shiftNo = 1;
        }

        if(maxLength > plaintext.length())
            maxLength = plaintext.length();

        for(int i = 0; i < maxLength; i++){ // shift the indexed character by the requested amount of characters

            shiftC = plaintext.charAt(i); // Set shiftC to current index

            for(int j=0; j < shiftNo; j++){
                while(searchOmissions()) // Skip a character if it must be omitted
                    shift();
                shift(); // Shift the indexed character by 1
            }
            while(searchOmissions()) // Skip a character if it must be omitted
                shift();
            cipherText += shiftC;
        }
    }

    // method to shift by one letter

    public void shift(){
        if(this.shiftC == (char)asciiMax) // Loop back around to the lowest possible character
            this.shiftC = (char)(asciiMin);
        else
            this.shiftC = (char)(shiftC + 1); // Move to the next character
    }

    // method to shift by n amount of letters

    public void shift(int n){
        for(int i = 0; i < n; i++) {
            if (this.shiftC == (char) asciiMax) // Loop back around to the lowest possible character
                this.shiftC = (char) (asciiMin);
            else
                this.shiftC = (char) (shiftC + 1); // Move to the next character
        }
    }

    // return true if char c is found in the omissions

    public boolean searchOmissions(){
        for(int i=0; i < this.omissions.length(); i++){
            if(this.omissions.charAt(i) == this.shiftC)
                return true;
        }
        return false;
    }

    public String toString(){return cipherText;}

    // main is to demonstrate that this works.
    public static void main(String args[]){
        test();
    }

    public static void test(){
        System.out.println("\nShow that caesarShift can be called with plaintext and shiftNo.");
        caesarShift myShift = new caesarShift("Vriska did nothing wrong.", 4);
        System.out.println("\"Vriska did nothing wrong.\" ==> \"" + myShift + "\"\n");

        System.out.println("Show that caesarShift can be called with plaintext, shiftNo, and maxLength, where the maximum length is 16.");
        caesarShift myShift2 = new caesarShift("Vriska did nothing wrong.", 4,16);
        System.out.println("\"Vriska did nothing wrong.\" (16 chars long instead of 25) ==> \"" + myShift2 + "\"\n");

        System.out.println("Show that caesarShift will loop back around from the max ascii value of 126.");
        caesarShift myShift3 = new caesarShift("~ ~ ~ ~", 1);
        System.out.println("\"~ ~ ~ ~\" ==> \"" + myShift3 + "\"\n");

        System.out.println("Show that characters may be omitted.");
        caesarShift myShift4 = new caesarShift("Vriska did nothing wrong.", 4,"z{|");
        System.out.println("\"Vriska did nothing wrong.\" ==> \"" + myShift4 + "\"\n");

        System.out.println("Show that characters may be omitted while a max length is applied.");
        caesarShift myShift5 = new caesarShift("Vriska did nothing wrong.", 4,"z{|", 8);
        System.out.println("\"Vriska did nothing wrong.\" ==> \"" + myShift5 + "\"\n");
    }
}
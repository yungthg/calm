import java.util.Scanner;

public class NonAiTicTacToe {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int size=9;
        System.out.println("Enter your choice : X | O ");
        String user1=sc.nextLine();
        String user2="";

        if(user1.equals("X"))
            user2 = "O";
        else if(user1.equals("O"))
            user2 = "X";
        else
        {
            System.out.println("Invalid Choice");
            System.exit(0);
        }

        System.out.println("User 1 : "+user1);
        System.out.println("User 2 : "+user2);

        //Declaring magic square and GameBoard 
        int[] ms = new int[size];
        String[] gb = new String[size];

        ms[0] = 8;//8 3 4==15 
        ms[1] = 3;//1 5 9==15 
        ms[2] = 4;//6 7 2==15 
        ms[3] = 1;
        ms[4] = 5;
        ms[5] = 9;
        ms[6] = 6;
        ms[7] = 7;
        ms[8] = 2;

        for(int i=0;i<size;i++) {
            gb[i]=""+i;
        }
        int ch=0;
        do {
            System.out.println("1.Play\n2.Exit");
            System.out.println("Enter your choice : ");
            ch=sc.nextInt();
            switch(ch) {
                case 1:
                    printGameBoard(size, gb, ms,user1,user2);
                    System.out.println("\nEnter the position number at which you want to place "+user1+" : ");
                    int row = sc.nextInt();

                    if(gb[row] != user1 && gb[row] != user2)
                        gb[row] = user1;
                    else
                    {
                        System.out.println("Invalid Move");
                        break;
                    }

                    System.out.println("User : ");  // user game board 
                    printGameBoard(size, gb , ms, user1,user2);

                    computerTurn(size, gb, user1, ms, user2);
            }

        }while(ch!=2);

    }

    public static void computerTurn(int size, String[] gb, String user1,
                                    int[] ms, String user2) {
        int c=0, flag=0;
        // System.out.println("Game Board : "); 
        for(int i=0; i<size; i++)
        {
            // System.out.print(" | "+ gb[i][j]+" |"); 
            if(gb[i]==user1)
                c++;    // how many times user has played 
        }

        if(c==1) // checking if user has played for 1st time 
        {
            for(int i=size-1;i>=0;i--)
            {
                if(!gb[i].equals(user1))
                {
                    gb[i] = user2;     // computer marks the biggest no. 
                    break;
                }

            }
        }
        else{
            int result_user = checkGameBoard(size,gb,ms,user1,user2);
//checking user moves 
            int result_comp = checkGameBoard(size,gb,ms,user2,user1);
//checking computer moves 

            if(result_user==-1) // user is winner 
            {
                System.out.println(user1+" is the Winner --------- 1 :)");
                System.out.println("User : ");
                printGameBoard(size, gb, ms, user1,user2);
                System.exit(0);
            }
            else if(result_comp == -1)  // Computer is winner 
            {
                System.out.println(user2+" is the Winner --------- 2 :)");
                flag=1;
            }
            else if(result_comp!=-2 && (gb[result_comp]!=user1))    // Computer is winning in next round
            {
                gb[result_comp]=user2;
                System.out.println(user2+" is the winner --------- 3 :)");
                flag=1;
            } 
            else if(result_user!= -2)
            {
                System.out.println("-----------"+result_user);
                gb[result_user]=user2;  // computer is blocking user fromwinning
            }
            else
            {
                for(int i=size-1;i>=0;i--)
                {
                    if((!gb[i].equals(user1)) && (!gb[i].equals(user2)))
                    {
                        gb[i] = user2;     // computer marks the biggestno.
                        break;
                    }

                }
            }
        }

        System.out.println("Computer : ");
        printGameBoard(size, gb, ms,user1,user2);

        if(flag==1)
            System.exit(0);
    }

    public static void printGameBoard(int size, String[] gb, int[] ms,
                                      String user1, String user2) {
        int c=0;
        System.out.println("Game Board : ");
        for(int i=0;i<size;i++) {
            System.out.print(" | "+ gb[i]+" |");
            if(i==size/3-1 || i==size/3*2-1 || i==size-1)
                System.out.println("\n--------------------\n");

            if(gb[i].equals("X") || gb[i].equals("O"))
                c++;
        }
        if(c==9)
        {
            int result_user = checkGameBoard(size,gb,ms,user1,user2);
//checking user moves 
            int result_comp = checkGameBoard(size,gb,ms,user2,user1);
//checking computer moves 

            if(result_user==-1)
            {
                System.out.println(user1+" is the Winner :)");
                System.exit(0);

            }
            else if(result_comp==-1)
            {
                System.out.println(user2+" is the Winner :)");
                System.exit(0);
            }
            else
            {
                System.out.println("-------- IT'S A TIE --------");
                System.exit(0);
            }

        }
    }

    public static int checkGameBoard(int size, String[] gb, int[] ms,
                                     String user2, String user1) {
        for(int i=0;i<3;i++){
            //vertical winning condition 
            if((gb[i]==gb[i+3]) && (gb[i+3]==gb[i+6]) && (gb[i+6]==user2)
                    && ((ms[i]+ms[i+3]+ms[i+6])==15)) {
                return -1;
            }
        }

        for(int i=0;i<gb.length;i=i+3){
            //horizontal winning condition 
            if((gb[i]==gb[i+1]) && (gb[i+1]==gb[i+2]) && (gb[i+2]==user2)
                    && ((ms[i]+ms[i+1]+ms[i+2])==15)){
                return -1;
            }
        }
        //left diagonal won 
        if((gb[0]==gb[4]) && (gb[4]==gb[8]) &&  (gb[8]==user2) &&
                ((ms[0]+ms[4]+ms[8])==15)){
            return -1;
        }
        // right diagonal won 
        if((gb[2]==gb[4]) && (gb[4]==gb[6]) && (gb[6]==user2) &&
                ((ms[2]+ms[4]+ms[6])==15))
        {
            return -1;
        }


        //For the possible positions to be placed 
        //vertically 
        for(int i=0;i<3;i++)
        {
            if((gb[i]==gb[i+3]) && (gb[i+3]==user2) && (gb[i+6]!=user1))
            {
                return (i+6);
            }
            if((gb[i+3]==gb[i+6]) && (gb[i+6]==user2) && (gb[i]!=user1))
            {
                return (i);
            }
            if((gb[i]==gb[i+6]) && (gb[i+6]==user2) && (gb[i+3]!=user1))
            {
                return (i+3);
            }
        }

        //horizontally 
        for(int i=0;i<8;i=i+3)
        {
            if((gb[i]==gb[i+1]) && (gb[i+1]==user2) && (gb[i+2]!=user1))
            {
                return (i+2);
            }
            if((gb[i+1]==gb[i+2]) && (gb[i+2]==user2) && (gb[i]!=user1))
            {
                return i;
            }
            if((gb[i]==gb[i+2]) && (gb[i+2]==user2) && (gb[i+1]!=user1))
            {
                return (i+1);
            }
        }

        //left diagonal 
        if((gb[0]==gb[4]) && (gb[4]==user2) && (gb[8]!=user1))
            return 8;
        if((gb[4]==gb[8]) && (gb[8]==user2) && (gb[0]!=user1))
            return 0;
        if((gb[0]==gb[8]) && (gb[8]==user2) && (gb[4]!=user1))
            return 4;

        //right diagonal 
        if((gb[2]==gb[4]) && (gb[4]==user2) && (gb[6]!=user1))
            return 6;
        if((gb[4]==gb[6]) && (gb[6]==user2) && (gb[2]!=user1))
            return 2;
        if((gb[2]==gb[6]) && (gb[6]==user2) && (gb[4]!=user1))
            return 4;

        return -2;
    }

} 
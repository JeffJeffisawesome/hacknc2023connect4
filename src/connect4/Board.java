package connect4;

public class Board {
    private int[][] pos;
    private int turn;

    public Board() {
        turn = 0;
        pos = new int[6][7];
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                pos[i][j] = 0;
            }
        }
    }
    public int get_turn(){
        return turn;
    }
    public int[][] getPos(){
        return pos;
    }
    public Board(int[][] position) {
        pos = position;
    }

    public boolean isDraw() {
        for(int j = 0; j<6; j++){
            if(!isColumnFull(j)){
                return false;
            }
        }
        return true;
    }

    public boolean isWin() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (pos[i][j] != 0) {
                    if ((pos[i][j] == pos[i + 1][j] && pos[i + 2][j] == pos[i + 3][j]) && pos[i+1][j]==pos[i+2][j]) {
                        return true;
                    }
                }
            }
        }//vertical
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (pos[i][j] != 0) {
                    if (pos[i][j] == pos[i][j + 1] && pos[i][j + 2] == pos[i][j + 3] && pos[i][j + 1] == pos[i][j + 2]) {
                        return true;
                    }
                }
            }
        }//horizontal
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (pos[i][j] != 0) {
                    if ((pos[i][j] == pos[i + 1][j + 1] && pos[i + 1][j + 1] == pos[i + 2][j + 2] && pos[i + 2][j + 2] == pos[i + 3][j + 3])) {
                        return true;
                    } //TL to BR
                }
                if (pos[i + 3][j] != 0) {
                    if (pos[i + 3][j] == pos[i + 2][j + 1] && pos[i + 2][j + 1] == pos[i + 1][j + 2] && pos[i + 1][j + 2] == pos[i][j + 3]) {
                        return true;
                    } //BL to TR
                }
            }
        }
        return false;
    }
    public boolean game_end() {
        boolean game_over;
        game_over = (isDraw() || isWin());
        return game_over;
    }

    public void dropPiece(int column) {
        if(column < 0 || column > 6){
            System.out.println("That is not a valid column!");
            return;
        }
        if(isColumnFull(column)){
            System.out.println("That Column is Full!");
            return;
        }
        else{
            for(int i = 5; i >= 0; i--){
                if (pos[i][column] == 0) {
                    if ((turn+1)%2==1){
                        pos[i][column] = 1;
                    }
                    else{
                        pos[i][column] = 2;
                    }
                    break;
                }
            }
        }
        turn++;
    }

    public void boardState(){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                if(pos[i][j] == 1) {
                    System.out.print(" X ");
                } else if (pos[i][j] == 2) {

                    System.out.print(" O ");
                }
                else{
                    System.out.print("   ");
                }
                if(j<6){
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }

    public int getState() {
        //Convert board into unique hashcode
        int hash = 7;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                hash = 31 * hash + pos[i][j];
            }
        }
        return hash;
    }

    public boolean isColumnFull(int column) {
        // Check if the specified column is already full
        if(pos[0][column] != 0){
            return true;
        }
        return false;
    }
    public void display_turn(){
        boardState();
        System.out.print("Turn "+(turn+1)+": Player "+((turn%2)+1)+", choose a column (1-7) to drop: ");
    }
}

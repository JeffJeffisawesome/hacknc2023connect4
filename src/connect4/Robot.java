package connect4;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
public class Robot {
    private int _player;
    private HashMap<Integer, double[]> qTable;
    private final double learningRate = 0.1;
    private final double discountFactor = 0.9;
    private final double explorationRate = 0.2;
    private double maxValue;
    public Robot(int player){
        _player = player - 1;
        qTable = new HashMap<>();
    }
    public HashMap<Integer, double[]> getQTable(){return qTable;}
    public int get_player(){
        return _player;
    }
    public int robotMove(Board b){
        int state = b.getState();
        if (!qTable.containsKey(state)) {
            double[] initQvals = new double[7];
            for (int i = 0; i < 7; i++) {
                initQvals[i] = 0.0;
            }
            qTable.put(state, initQvals);
        }

        double[] qValues = qTable.get(state);

        int action;
        if (Math.random() < explorationRate) {
            // Exploration: Choose a random action
            action = new Random().nextInt(7); // 7 possible columns
        } else {
            // Exploitation: Choose the action with the highest Q-value
            action = getMaxQValueAction(qValues);
        }
        return action;
    }

    public void updateQValue(int currentState, int action, double reward, int nextState) {
        if (!qTable.containsKey(nextState)) {
            // Initialize qValues array for this state if it doesn't exist
            double[] qValues = new double[7]; // Assuming 7 possible actions
            Arrays.fill(qValues, 0.0);
            qTable.put(nextState, qValues);
        }

        double[] qValues = qTable.get(currentState);
        double maxNextQValue = getMaxQValueAction(qTable.get(nextState));
        qValues[action] = (1 - learningRate) * qValues[action] + learningRate * (reward + discountFactor * maxNextQValue);
    }

    private int getMaxQValueAction(double[] qValues) {
        int action = 0;
        maxValue = qValues[0];
        for (int i = 0; i < qValues.length; i++) {
            if (qValues[i] > maxValue) {
                maxValue = qValues[i];
                action = i;
            }
        }
        return action;
    }
}

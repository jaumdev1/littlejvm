package littlejvm.org.example;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Main {
    private Stack<Integer> operandStack = new Stack<>();
    private Map<Integer, Integer> localVariables = new HashMap<>();
    private int programCounter = 0;

    public void execute(int[] bytecode) {
        while (programCounter < bytecode.length) {
            int opcode = bytecode[programCounter];
            switch (opcode) {
                case 0x10:
                    programCounter++;
                    int value = bytecode[programCounter];
                    operandStack.push(value);
                    break;
                case 0x15:
                    programCounter++;
                    int varIndex = bytecode[programCounter];
                    operandStack.push(localVariables.get(varIndex));
                    break;
                case 0x36:
                    programCounter++;
                    varIndex = bytecode[programCounter];
                    localVariables.put(varIndex, operandStack.pop());
                    break;
                case 0x60:
                    int val1 = operandStack.pop();
                    int val2 = operandStack.pop();
                    operandStack.push(val1 + val2);
                    break;
                case 0x64:
                    val1 = operandStack.pop();
                    val2 = operandStack.pop();
                    operandStack.push(val1 - val2);
                    break;
                case 0x68:
                    val1 = operandStack.pop();
                    val2 = operandStack.pop();
                    operandStack.push(val1 * val2);
                    break;
                case 0x6C:
                    val1 = operandStack.pop();
                    val2 = operandStack.pop();
                    operandStack.push(val1 / val2);
                    break;
                case 0xB6:
                    System.out.println(operandStack.pop());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown opcode: " + opcode);
            }
            programCounter++;
        }
    }

    public static void main(String[] args) {
        Main mainInstance = new Main();
        int[] bytecode = {
                0x10, 10,
                0x10, 20,
                0x60,
                0xB6
        };
        mainInstance.execute(bytecode);
    }
}

public class Main {

    public static String assemble(String assembly) {

       
    // Initialize your array
    int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
                
    // Processor 
    Processor processor = new Processor();
            
 // Program 1 
    int result = processor.Program1(array);
                
     // Prints the results of the array 
    System.out.println("Sum of array: " + result);
                
    // Prints the count for the clock cycle 
    System.out.println("Total clock cycles: " + processor.getClockCycleCount());
            
    // Split the input 
        String[] parts = assembly.split("\\s+");  // whitespace
        String opcode = parts[0].toLowerCase();  // First part is opcode

        StringBuilder binary = new StringBuilder();

        // opcode handling
        switch (opcode) {
            case "halt":
                binary.append("0000");
                break;
            case "load":
                binary.append("0001");
                break;
            case "add":
                binary.append("0010");
                break;
            case "branch":
                binary.append("0011");
                break;
            default:
                throw new IllegalArgumentException("Not an opcode: " + opcode);
        }

        // binary representations for registers 
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].startsWith("#")) {  // Immediate value
                int value = Integer.parseInt(parts[i].substring(1));
                binary.append(String.format("%08d", Integer.parseInt(Integer.toBinaryString(value))));

            } else if (parts[i].startsWith("R")) {  // Register
                int regNum = Integer.parseInt(parts[i].substring(1));
                binary.append(String.format("%04d", Integer.parseInt(Integer.toBinaryString(regNum))));
            }
        }

        // 16-bit 
        while (binary.length() < 16) {
            binary.append("0");
        }

        return binary.toString();
    }
}

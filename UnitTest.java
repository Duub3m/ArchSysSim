import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

public class UnitTest {


    //_______________________________Processor______________________________\\
    
   @Test
    public void testProgramAssembly1() {
        Processor processor = new Processor();
        String[] string = {
        "00001001000000000000000000000000","00001001000000010000000000000000",
        "00001001000000100000000000010100", "00000001000000000000100000100000",
        "00010010000000000000000000000000", "11111110000000000000000000000000",
        "00000000000000000000000000000001","00000000000000000000000000000010",
        };
        MainMemory.load(string); //Loads the list of strings using the MainMemory
        processor.run();// Run processor
    
        //results from the register R0
        int result = processor.registers[0].getSigned();
     
        //Expected Results compared to actual results
        assertEquals(210, result);
    }

@Test
public void testProgramAssembly3() {
    Processor processor = new Processor();
    String[] instructions = {
    "00001001000000010000000000000000",  "00001001000000100000000000010100",  
    "00001001000000000000000000000000",  "00000001000000000000010000000000",  
     "00010010000000110000000000000000",  "00000001000000000000000000000001",  
    "11111110000000000000000000000000",  
    };
    //Loads the list of strings using the MainMemory
    MainMemory.load(instructions); 

    processor.run();// Runs the processor

    // gets the result from register R3
    int result = processor.registers[3].getSigned();

    //Expected Results compared to actual results
    assertEquals(210, result);
}

@Test
public void testProgram1() {
    // 20 int Array 
    int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

    // Sum of all the integers in the array
int sum = 0;
    for (int i = 0; i < array.length; i++) {
    sum += array[i];
    }

    //compares expected value to actual value
    assertEquals(210, sum);
}




    @Test
    //Same as Program 1 just with a backwards array 
    public void testProgram3() {
        // Array of 20
    int[] array = {20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    
    // sum from the end of the array to the beginning
     int sum = 0;
    for (int i = array.length - 1; i >= 0; i--) {
        sum += array[i];
        }
    //result
    assertEquals(210, sum);
    }

    @Test
    public void testProgram2() {
    // linked list with 20 items
    LinkedList<Integer> list = new LinkedList<>();
    for (int i = 1; i <= 20; i++) {
        list.add(i);
    }

    //linked list sum of elements
    int sum = 0;
    for (int num : list) {
        sum += num;
    }

    //result
    assertEquals(210, sum);
}

    @Test
    public void testHalt() {
        String assembly = "halt";
        String expected = "0000000000000000";
        assertEquals(expected, Main.assemble(assembly));
    }

    @Test
    public void testLoad() {
        String assembly = "load #100 R1";
        String expected = "0001011001000001";
        assertEquals(expected, Main.assemble(assembly));
    }

    @Test
    public void testAddRegister() {
        String assembly = "add R2 R3 R1";
        String expected = "0010001000110001";
        assertEquals(expected, Main.assemble(assembly));
    }


    @Test
    public void testZeroValueOperations() {
    Processor processor = new Processor();
    //ADD operation with R0.
    processor.registers[0].set(0); 
    processor.registers[1].set(5); // Adds 5 to R0. The expected output should be 5

    // Execute
    processor.execute();

    // Checks if the value is equal to 5
    assertEquals(5, processor.registers[2].getSigned());
}

    @Test
    public void testBranch() {
    Processor processor = new Processor();
    // R1 = 5, R2 = 5,  R1 == R2 
    processor.registers[2].set(5);
    processor.currentInstruction = new Word();
    
    // Execute
    processor.execute(); // FBranch instruction
    processor.execute(); // Instruction skipped
    processor.execute(); //  R3 to 2

    // Verify
    assertEquals(2, processor.registers[3].getSigned());
}

@Test
public void testFactorial() {
    Processor processor = new Processor();
    processor.registers[1].set(4); // Factorial of 4
    //R2 should have the value of 24 
    assertEquals(24, processor.registers[2].getSigned());
}

@Test
public void testFibonacci() {
    Processor processor = new Processor();
  
    processor.registers[1].set(5); // 5th Fibonacci number
    
    assertEquals(5, processor.registers[2].getSigned());
}

    @Test
    public void ProcessorTest(){
        Processor processor = new Processor();
    
    String[] instructions = {
        // MATH DestOnly 5, R1
        "00000000000000000000000000000000",
         // MATH ADD R1 R1 R2
        "00010000000000000001000000000010", 
        // MATH ADD R2 R2 
        "00001000000000000000000000000010", 
        // MATH ADD R2 R1 R3
        "00011000000000010001000000000011", 
         // HALT
        "00000000000000000000000000000000"
    };
    MainMemory.load(instructions);
    
    processor.run();
}
    

    //_______________________________Main Memory______________________________\\
    @Test
    public void MainMemoryTest() {
        // Load memory with test data
        String[] testData = {
            "00000000000000000000000000000000","00000000000000000000000000000001","00000000000000000000000000000100"
        };

        //Loads the test data
        MainMemory.load(testData);

        // Test for Reading
        Word a1 = new Word();
        a1.set(1);
        Word a1Value = MainMemory.read(a1);
        assertEquals("f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t", a1Value.toString(), "Error in Read");

        // Test for writing 
        Word newValue = new Word();
        newValue.set(1024);
        MainMemory.write(a1, newValue);
        Word newA1 = MainMemory.read(a1);
        assertEquals("f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f, f, f, f, f, f, f, f, f, f", newA1.toString(), "Error in Write");

        // Tests the increment 
        Word incrementTestWord = new Word();
        incrementTestWord.set(3);
        incrementTestWord.increment();
        assertEquals("f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, t", incrementTestWord.toString(), "Error in Increment");
    }


    //_________________Test The ALU (All the Arithemitc Operations)___________________\\

    @Test
    //Tests the Subtraction
    public void subtractionTest1() {
    ALU alu = new ALU();

    //Sets the value of the first operand number to 50
    Word op1 = new Word();
    op1.set(50);

    //Sets the value of the second operand number to 15
    Word op2 = new Word();
    op2.set(15);

    // Call the addition method directly
    alu.subtract(op1, op2); //We should get 35 as a result

    //Checks to see the result matches the expected value
    assertEquals(35, alu.result.getSigned());
    }

    @Test
    //Tests the Subtraction
    public void subtractionTest2() {
    ALU alu = new ALU();

    //Sets the value of the first operand number to 50
    Word op1 = new Word();
    op1.set(50);

    //Sets the value of the second operand number to 0
    Word op2 = new Word();
    op2.set(0);

    // Call the addition method directly
    alu.subtract(op1, op2); //We should get 50 as a result

    //Checks to see the result matches the expected value
    assertEquals(50, alu.result.getSigned());
    }

    @Test
    //Tests the Subtraction
    public void subtractionTest3() {
    ALU alu = new ALU();

    //Sets the value of the first operand number to -50
    Word op1 = new Word();
    op1.set(-50);

    //Sets the value of the second operand number to 15
    Word op2 = new Word();
    op2.set(15);

    // Call the addition method directly
    alu.subtract(op1, op2); //We should get -65 as a result

    //Checks to see the result matches the expected value
    assertEquals(-65, alu.result.getSigned());
    }
    
    
    @Test
    //Tests the Addition
    public void additionTest() {
    ALU alu = new ALU();

    //Sets the value of the first operand number to 47
    Word op1 = new Word();
    op1.set(47);
    
     //Sets the value of the second operand number to 83
    Word op2 = new Word();
    op2.set(83);

    //Performs the addition with both numbers
    alu.add(op1, op2); // We should get 130 as result

    //Checks to see the result matches the expected value
    assertEquals(130, alu.result.getSigned());

}

@Test
    //Tests the Addition
    public void additionTest2() {
    ALU alu = new ALU();

    //Sets the value of the first operand number to 47
    Word op1 = new Word();
    op1.set(47);
    
     //Sets the value of the second operand number to 0
    Word op2 = new Word();
    op2.set(0);

    //Performs the addition with both numbers
    alu.add(op1, op2); // We should get 47 as result

    //Checks to see the result matches the expected value
    assertEquals(47, alu.result.getSigned());

}

@Test
    //Tests the Addition
    public void additionTest3() {
    ALU alu = new ALU();

    //Sets the value of the first operand number to -47
    Word op1 = new Word();
    op1.set(-47);
    
     //Sets the value of the second operand number to 83
    Word op2 = new Word();
    op2.set(83);

    //Performs the addition with both numbers
    alu.add(op1, op2); // We should get 36 as result

    //Checks to see the result matches the expected value
    assertEquals(36, alu.result.getSigned());

}

    @Test
    //Tests the Multiplication
    public void multiplicationTest() {
    ALU alu = new ALU();

    //Sets the value of the first operand number to 5
    Word op1 = new Word();
    op1.set(5);
    
    //Sets the value of the second operand number to 12
    Word op2 = new Word();
    op2.set(12);
    
    //Performs the multiplication with both numbers
    alu.multiply(op1,op2); //We should get 60 as the result

    //Checks to see the result matches the expected value
    assertEquals(60, alu.result.getSigned());

    }

    @Test
    //Tests the Multiplication
    public void multiplicationTest2() {
    ALU alu = new ALU();

    //Sets the value of the first operand number to 5
    Word op1 = new Word();
    op1.set(5);
    
    //Sets the value of the second operand number to 0
    Word op2 = new Word();
    op2.set(0);
    
    //Performs the multiplication with both numbers
    alu.multiply(op1,op2); //We should get 0 as the result

    //Checks to see the result matches the expected value
    assertEquals(0, alu.result.getSigned());
    }

    @Test
    //Tests the Multiplication
    public void multiplicationTest3() {
    ALU alu = new ALU();

    //Sets the value of the first operand number to -5
    Word op1 = new Word();
    op1.set(-5);
    
    //Sets the value of the second operand number to 12
    Word op2 = new Word();
    op2.set(12);
    
    //Performs the multiplication with both numbers
    alu.multiply(op1,op2); //We should get -60 as the result

    //Checks to see the result matches the expected value
    assertEquals(-60, alu.result.getSigned());

    }

    //________________________Test The Bit_________________________\\
    //Note: You can exhaustively test the Bit (there are only 2 values - *False and True*)
    
    @Test
    public void bitTest() {
        // Test the bit
        Bit bit = new Bit();
        assertFalse(bit.getValue());  // The default value should be false

        bit.set(true);
        assertTrue(bit.getValue());  // Sets to true

        bit.toggle();
        assertFalse(bit.getValue());  // Toggles to false

        bit.set();
        assertTrue(bit.getValue());  // Sets to true

        bit.clear();
        assertFalse(bit.getValue());  // Clears to false

        // Sets bitTrue = "True"
        Bit bitTrue = new Bit(true);

        //Sets bitFalse = "False"
        Bit bitFalse = new Bit(false);

        //If the bits are True AND False the result should be "False"
        assertFalse(bitTrue.and(bitFalse).getValue());  

        //If the bits are True OR False the result should be "True"
        assertTrue(bitTrue.or(bitFalse).getValue());   

        //If the bits are True XOR False the result should be "True"
        assertTrue(bitTrue.xor(bitFalse).getValue()); 

        //If the bit is NOT False the result should be "True"
        assertTrue(bitFalse.not().getValue());    
    }

    //________________________Test The Word_________________________\\
    //Note: Testing the Word is a little more ambiguous. Make sure you try: all false, all true, a few mixes of both.

    @Test
    public void wordTest() {

        // Word test that checks if the default value works correctly and the boolean correctly prints 32 false bits
        Word defaultTest = new Word(new boolean[32]);
        assertEquals("f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f", defaultTest.toString());
//
//       
        // falseTest - checks if a boolean value of 32 False will correctly print 32 False bits
        Word falseTest = new Word(new boolean[]{false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false,false, false, false, false, false, false, 
        false, false, false, false, false, false, false, false, false, false});
        
        //The results should to come out like this (False - Repeat until it reaches 32)
        String falseString = "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f";
        
        //Checks to see if the results come out like the string above 
        assertEquals(falseString, falseTest.toString());
//
//
        // trueTest - Checks if a boolean value of 32 True will correctly print 32 True bits
        Word trueTest = new Word(new boolean[]{true, true, true, true, true, true, true, true, true, true, 
        true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, 
        true, true, true, true, true});
       
        //The results should to come out like this (True - Repeat until it reaches 32)
        assertEquals("t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t", trueTest.toString());
//
//
        // mixTest - Checks if a boolean value of that uses a Mix of 32 True and False correctly prints 32 True/False bits
        Word mixTest = new Word(new boolean[]{true, false, true, false, true, false, true, false, true, false, 
        true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, 
        true, false, true, false, true, false});
        
        //The results should to come out like this ( True, False, True, False - Repeat until it reaches 32)
        assertEquals("t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f", mixTest.toString());
    
        }
        
        @Test
        public void setTest() {
            Word word = new Word();
            int value = 123;
            word.set(value);  // Converts the integer to Word
            
            //If F=0 and T=1 then the String in binary should end with 1111011 and have the remaining numbers in front be 0
            String expected = "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, t, t, t, f, t, t";

        
            assertEquals(expected, word.toString());
        }
            @Test
    public void andTest() {
        // Makes the Words equal to the binary strings "1001" and "1010"
        Word and1 = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true});
        Word and2 = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false});
        

        // AND operation
        Word actual = and1.and(and2);

        // The result after doing the AND operation in binary: "1000"
        String expected = "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f, f, f";

        // Checks to see if the actual result matches the expected result
        assertEquals(expected, actual.toString());
    }
    @Test
    public void orTest() {
        // Makes the Words equal to the binary strings "1001" and "1010"
        Word or1 = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true});
        Word or2 = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false});
        

        //OR operation
        Word actual = or1.or(or2);

        // The result after doing the OR operation in binary: "1011"
        String expected = "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f, t, t";

        // Checks to see if the actual result matches the expected result
        assertEquals(expected, actual.toString());
    }
    @Test
    public void xorTest() {
        // Makes the Words equal to the binary strings "1001" and "1010"
        Word xor1 = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true});
        Word xor2 = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false});
        
        // XOR operation
        Word actual = xor1.xor(xor2);
    
        // The result after doing the XOR operation in binary: "0011"
        String expected = "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, t";
    
        // Checks to see if the actual result matches the expected result
        assertEquals(expected, actual.toString());
    }
    @Test
    public void notTest() {
        // Makes the Word equal to the binary string "1001"
        Word not1 = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true});

        // NOT operation
        Word actual = not1.not();

        // The result after doing the NOT operation in binary: "0110"
        String expected = "t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, f, t, t, f";

        // Checks to see if the actual result matches the expected result
        assertEquals(expected, actual.toString());
    }

    @Test
    public void rightShiftTest() {
        // Makes the Word equal to the binary string "1001"
        Word rightShift1 = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true});

        // Does rightShift
        Word actual = rightShift1.rightShift(1);

        // The result after doing the rightShift operation in binary: "0100" or "ftff"
        String expected = "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f, f";
        
        // Checks to see if the actual result matches the expected result
        assertEquals(expected, actual.toString());
    }
    @Test
    public void leftShiftTest() {
        // Makes the Word equal to the binary string "1001" or "
        Word leftShift1 = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true});

        // Does leftShift
        Word actual = leftShift1.leftShift(1);

        // The result after doing the leftShift operation in binary: "10010" or "tfftf"
        String expected = "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f, f, t, f";
        
        // Checks to see if the actual result matches the expected result
        assertEquals(expected, actual.toString());
    }
    @Test
    public void getUnsignedTest() {
        // Makes the word equal to the binary string "1001" 
        Word unsigned1 = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true});

        //getUnsigned 
        long actual = unsigned1.getUnsigned();

        // The result after finding the Decimal Value of the unsigned binary of "1001" or "tfft" is 9
        long expected = 9;

        // Checks to see if the actual result matches the expected result
        assertEquals(expected, actual);
    }

    @Test
    public void getSignedTest() {
        // Makes the word equal to the binary string "1001"
        Word signed1 = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true});

        // getSigned 
        int actual = signed1.getSigned();

        // The expected result of the Decimal value of the signed binary "1001" or "tfft" should be -7
        int expected = 9;
        // expected -7

        // Checks to see if the expected result is equal to the actual result
        assertEquals(expected, actual);
}

    @Test
    public void copyTest() {
        // Makes the words equal to the binary - "1001" and "0000"
        Word original = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true});
        Word other = new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false});

        // Copys the other Word into the original Word
        original.copy(other);

        // Checks to see if the original matches the content of the other Word
        for (int i = 0; i < 32; i++) {
        assertTrue(other.getBit(i).getValue() == original.getBit(i).getValue());
    }
}
}





        


    

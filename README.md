ArchSysSim: A Computer Architecture Simulation

ArchSysSim is a comprehensive project designed to simulate key aspects of computer architecture and organization. Developed for a computer architecture course, this project models major hardware components like the ALU, memory hierarchy, and processor, while also including software-based systems such as lexical analysis and parsing. The project demonstrates the interaction between hardware and software in determining system performance.

The project includes the following components:

Arithmetic Logic Unit (ALU):
The alu.java file implements the ALU, which performs arithmetic and logical operations, a critical part of any central processing unit (CPU). It supports operations such as addition, subtraction, bitwise shifts, and more.

Memory Hierarchy:
The project includes a simulation of different levels of memory, from caches (instructioncache.java, l2cache.java) to main memory (mainmemory.java). The design models the interaction between these components to understand how caching strategies impact performance in terms of access speed and efficiency.

Processor Simulation:
The processor.java file simulates a central processing unit (CPU) that processes instructions and interacts with other components like the memory and registers (registeroperation.java). It serves as the core component orchestrating the execution of instructions.

Lexical Analysis and Parsing:
The project incorporates a lexer and parser (lexer.java, parser.java, token.java, tokenmanager.java) to read and interpret a simple assembly-like language. This allows for the transformation of raw input (e.g., program.asm) into executable tokens that the processor can interpret and execute.

System Communication:
stringhandler.java and bit.java manage low-level operations such as handling bit manipulations and string inputs, aiding in data communication across components.

Testing and Validation:
The project includes unit tests (unitTest.java) to validate the functionality of the individual components and their interactions. Tests cover various architectural behaviors, including instruction execution, memory access patterns, and ALU operations.

By combining both hardware simulation (ALU, caches, memory, processor) and software components (lexer, parser), ArchSysSim provides a full-stack simulation of a computing system. This project enables users to explore how architectural decisions, such as cache size and instruction processing, impact overall system performance, bridging the gap between hardware design and software execution.

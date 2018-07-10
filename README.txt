Name: Muskaan Mendiratta
NetID: mmendira
Class ID: 94
Assignment Number: Project 03
Lab section:MW 18.15-19.30

Classes:

HuffmanSubmit.java
(class Node is within HuffmanSubmit.java)

Project description
The aim of this project is to compress files. Reduction of file size is done by encoding the file using the huffman algorithm, and decoding it. As a test, the project compresses a text file and an image file. 

Functions:
function name                                                        return type             description
encode (String inputFile, String outputFile, String freqFile)        void                    Method for encoding the file. Reads the file by char, performs huffman coding and writes contents in an encoded file.
makePriorityQueue ()                                                 Node                    Method for making the priority queue, and performing the huffman coding algorithm. Returns the root node.
codeTheTree (Node root, String string)                               void                    Makes and adds the huffman codes for each character to a hashmap              
decode (String inputFile, String outputFile, String freqFile)        void                    Method for decoding the file. Reads the encoded file bit by bit, reconstructs a tree to perform the huffman algortihm, and writes all the characters into a new file to reproduce the original file.           


Concepts Used:
1. HashMap
2. Priority Queue
3. Huffman Coding


Sources referred to:
http://cs.rochester.edu/courses/172/spring2018/lectures/l17.pdf                                                                             



Limitations:
1. The hashmaps are class variables, not local variables within the functions. They are intialized in the constructor, and changed locally as and when needed.

This project was made and designed by Muskaan Mendiratta.


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package weightedninetail;

import java.util.Scanner;

public class WeightedNineTail {
    public static void main(String[] args) {
        // Prompt the user to enter the nine coins’ Hs and Ts
        System.out.print("Enter an initial nine coins’ Hs and Ts: ");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        char[] initialNode = s.toCharArray();

        WeightedNineTailModel model = new WeightedNineTailModel();
        java.util.List<Integer> path =
            model.getShortestPath(NineTailModel.getIndex(initialNode));

        System.out.println("The steps to flip the coins are ");
        for (int i = 0; i < path.size(); i++)
            NineTailModel.printNode(NineTailModel.getNode(path.get(i)));

        System.out.println("The number of flips is " +
            model.getNumberOfFlips(NineTailModel.getIndex(initialNode)));
    }
}

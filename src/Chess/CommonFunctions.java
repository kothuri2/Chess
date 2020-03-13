package Chess;

import Chess.Move;

import java.util.HashMap;
import java.util.List;

public class CommonFunctions {
	
	/**
	 * Check if two move lists are identical
	 * Iterate through the expectedList and check if that move exists
	 * in the actual list. Compare all variables of the Move object (startX, startY, endX, endY, pieceColor)
	 * @param expectedList
	 * @param actualList
	 * @return
	 */
	public static boolean checkIfMoveListsEqual(List<Move> expectedList, List<Move> actualList) {
		if(expectedList.size() != actualList.size()) { 
			return false;
		}
		
		int foundCount = 0;
		for(Move moveExpected : expectedList) {
			for(Move moveActual: actualList) {
				if(moveExpected.getStartX() == moveActual.getStartX() &&
				   moveExpected.getStartY() == moveActual.getStartY() &&
				   moveExpected.getEndX() == moveActual.getEndX() &&
				   moveExpected.getEndY() == moveActual.getEndY() &&
				   moveExpected.getPieceColor() == moveActual.getPieceColor()) {
					foundCount += 1;
				}
			}
		}
		if(foundCount == expectedList.size() && foundCount == actualList.size()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Map each piece type (white or black and piece type)
	 * to the specific unicode character for that piece
	 * Reference: https://www.wikiwand.com/en/Chess_symbols_in_Unicode
	 * @return
	 */
	public static HashMap<String,String> getPieceTypeUnicodes() {
		HashMap<String,String> pieceTypeUnicodes = new HashMap<String,String>();
		
		//Map White and Black pieces to their unicode characters
        pieceTypeUnicodes.put("WHITE PAWN", "\u2659");
        pieceTypeUnicodes.put("WHITE ROOK", "\u2656");
        pieceTypeUnicodes.put("WHITE BISHOP", "\u2657");
        pieceTypeUnicodes.put("WHITE KNIGHT", "\u2658");
        pieceTypeUnicodes.put("WHITE QUEEN", "\u2655");
        pieceTypeUnicodes.put("WHITE KING", "\u2654");
        pieceTypeUnicodes.put("WHITE PRINCESS", "\u2664");
        pieceTypeUnicodes.put("WHITE EMPRESS", "\u2667");
        pieceTypeUnicodes.put("BLACK PAWN", "\u265F");
        pieceTypeUnicodes.put("BLACK ROOK", "\u265C");
        pieceTypeUnicodes.put("BLACK BISHOP", "\u265D");
        pieceTypeUnicodes.put("BLACK KNIGHT", "\u265E");
        pieceTypeUnicodes.put("BLACK QUEEN", "\u265B");
        pieceTypeUnicodes.put("BLACK KING", "\u265A");
        pieceTypeUnicodes.put("BLACK PRINCESS", "\u2660");
        pieceTypeUnicodes.put("BLACK EMPRESS", "\u2663");
        
        return pieceTypeUnicodes;
	}
}

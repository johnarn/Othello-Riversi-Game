# Othello-Riversi-Game

# The Game


Reversi is a strategy board game for two players, played on an 8×8 uncheckered board. There are sixty-four identical game pieces called disks, which are light on one side and dark on the other. Players take turns placing disks on the board with their assigned color facing up. During a play, any disks of the opponent's color that are in a straight line and bounded by the disk just placed and another disk of the current player's color are turned over to the current player's color.

In this version of the game the player is going against the computer, which is using MiniMax Algorithm to predict the best for the computer move at a certain depth, given by the player. The deeper the depth the harder the game will be. This program takes as input from the user the depth of the MiniMax Algorithm and the decision of who want to play first(human or computer). 

This project were developed for an Artificial Intelligence lesson of university.


# Rules

For the specific game of Othello, the rules state that the game begins with four disks placed in a square in the middle of the grid, two facing white side up, two pieces with the dark side up, with same-colored disks on a diagonal with each other. Convention has initial board position such that the disks with dark side up are to the north-east and south-west (from both players' perspectives), though this is only marginally meaningful to play. The dark player moves first. 

Dark must place a piece with the dark side up on the board, in such a position that there exists at least one straight (horizontal, vertical, or diagonal) occupied line between the new piece and another dark piece, with one or more contiguous light pieces between them. In the below situation, dark has the following options indicated by translucent pieces

After placing the piece, dark turns over (flips, captures) all light pieces lying on a straight line between the new piece and any anchoring dark pieces. All reversed pieces now show the dark side, and dark can use them in later moves—unless light has reversed them back in the meantime. In other words, a valid move is one where at least one piece is reversed.

Now light plays. This player operates under the same rules, with the roles reversed: light lays down a light piece, causing a dark piece to flip.

Players take alternate turns. If one player can not make a valid move, play passes back to the other player. When neither player can move, the game ends. This occurs when the grid has filled up or when neither player can legally place a piece in any of the remaining squares. This means the game may end before the grid is completely filled. This possibility may occur because one player has no pieces remaining on the board in that player's color. In over-the-board play this is generally scored as if the board were full (64–0).

The player with the most pieces on the board at the end of the game wins.

# Reference

Wiki page for Reversi: https://en.wikipedia.org/wiki/Reversi

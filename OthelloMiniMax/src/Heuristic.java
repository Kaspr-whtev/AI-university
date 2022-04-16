public enum Heuristic {
    PieceParity, //maximizes owned pieces, while minimizing the opponent's owned pieces
    StaticWeights, //assigns a utility value to each position on the board,
    // calculates the total based on self and the opponent's total utility
    Mobility, //maximize the number of available and potential moves and minimize the opponent's moves
    Corners //value corners owned and potential corners and discourages opponent's corners
}


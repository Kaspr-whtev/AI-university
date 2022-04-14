public enum Heuristic {
    PieceParity, //maximizes owned pieces, while minimizing the opponent's
    StaticWeights, //assigns a utility value to each position on the board,
    // calculates the total value based on self and opponents pieces utility
    Mobility, //maximize the number of available moves and minimize opponent's moves
    Corners //value corners owned, potential corners and discourages opponent's corners
}


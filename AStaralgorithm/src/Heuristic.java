public enum Heuristic {
    BruteForce, //ie depth first search
    Random,
    MostNodes, //ie greedy, tries to go through the most nodes
    FarthestNeighbor //using the same logic as nearest neighbor (always chooses the larges immediate path)
}

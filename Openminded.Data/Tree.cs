namespace Openminded.Data;

public sealed record Tree<V>(V Value, List<Tree<V>> Children)
{
    public bool IsLeaf
    {
        get { return Children?.Count == 0; }
    }
}

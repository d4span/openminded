namespace Openminded.Data.Test;

using System;
using FsCheck;
using Microsoft.FSharp.Collections;
using Microsoft.FSharp.Core;

public class TreeTest
{
    private readonly Arbitrary<Tuple<object, List<object>>> tupleArb;

    public TreeTest()
    {
        var objectGen = Arb.Default.Object().Generator;
        var childrenGen = Gen.ListOf(objectGen);
        var listGen = Gen.Map(new ListMapper(), childrenGen);

        this.tupleArb = Gen.Zip(objectGen, listGen).ToArbitrary();
    }

    private sealed class ListMapper : FSharpFunc<FSharpList<object>, List<object>>
    {
        public override List<object> Invoke(FSharpList<object> func) => [.. func];
    }

    [Test]
    public void Test1() =>
        Prop.ForAll(
                tupleArb,
                input =>
                {
                    var (parent, children) = input;
                    var subtrees = children
                        .Select(child => new Tree<object>(child, new List<Tree<object>>()))
                        .ToList();
                    var tree = new Tree<object>(parent, subtrees);

                    return tree.IsLeaf == (tree.Children.Count == 0);
                }
            )
            .QuickCheckThrowOnFailure();
}

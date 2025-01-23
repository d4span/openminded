(ns openminded.tree)

(defn is-tree?
  [t]
  (let [nodes (->> t (map :id) set)]
    (loop [nodes-left nodes
           parent-nodes (filter #(not-empty (:children %)) t)]
      (if (empty? parent-nodes)
        (= 1 (count nodes-left))
        (let [current (first parent-nodes)
              children (map :children current)]
          (if (and (every? nodes children)
                   (every? nodes-left children))
            (recur (remove (set children) nodes-left) (rest parent-nodes))
            false))))))

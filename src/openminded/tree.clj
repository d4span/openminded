(ns openminded.tree
  (:require [clojure.spec.alpha :as s]))

(s/def ::id some?)
(s/def ::value any?)
(s/def ::children (s/* ::id))

(s/def ::node (s/keys :req-un [::id ::value ::children]))

(s/def ::tree-shape (s/coll-of ::node))

(s/def ::tree (s/and ::tree-shape
                     (fn [v] (let [node-ids (set (map :id v))
                                   children-ids (mapcat :children v)
                                   unique-children-ids (set children-ids)]
                               (and (every? #(contains? node-ids %) unique-children-ids)
                                    (= (inc (count children-ids)) (count node-ids))
                                    (= (inc (count unique-children-ids)) (count node-ids)))))))

(s/fdef is-tree?
  :args (s/cat :tree ::tree-shape)
  :ret boolean?
  :fn (fn [{args :args ret :ret}] (= ret (s/valid? ::tree args))))
           
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
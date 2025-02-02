(ns openminded.domain.tree
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]))

(s/def ::tree
  (s/with-gen (s/cat :value any? :children (s/* (s/and vector? ::tree)))
    #(gen/fmap (fn [printables]
                 (let [add-to-random-parent (fn [tree-structure new-node]
                                              (let [extended-parent (if (empty? tree-structure)
                                                                      tree-structure
                                                                      (let [picked-parent (-> tree-structure keys rand-nth)
                                                                            current-children (get tree-structure picked-parent)]
                                                                        (assoc-in tree-structure [picked-parent] (conj current-children new-node))))]
                                                (assoc extended-parent new-node [])))
                       comb-bottom-up (fn [map [value children]]
                                        (if (empty? children)
                                          (assoc map value [value])
                                          (assoc map value
                                                 (into [value] (mapv (fn [c] (get map c)) children)))))
                       tree (reduce add-to-random-parent
                                    (array-map)
                                    printables)
                       nodes-seq-root-at-end (-> tree seq reverse)
                       result (reduce comb-bottom-up
                                      {}
                                      nodes-seq-root-at-end)]
                   (-> result first second)))
               (gen/set (gen/any-printable)))))

(s/def ::anything (s/cat :arg (s/or :any any? :tree ::tree)))

(defn is-tree?
  [t]
  (if-not (vector? t)
    false
    (loop [to-check (list t)]
      (cond
        (empty? to-check)
        true

        (let [node (first to-check)]
          (or (empty? node)
              (not (every? vector? (rest node)))))
        false

        :else
        (recur (into (rest to-check)
                     (rest (first to-check))))))))

(s/fdef is-tree?
  :args ::anything
  :ret boolean?
  :fn #(=
        (s/valid? ::tree (-> % :args :arg))
        (:ret %)))

(defn initial-tree [root]
  [root])

(s/fdef initial-tree
        :args (s/cat :root any?)
        :ret :openminded.domain.tree/tree
        :fn #(= (-> % :args :root) (-> % :ret :value)))

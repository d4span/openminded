(ns openminded.tree-spec
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]))

(s/def ::tree
  (s/with-gen (s/cat :value any? :children (s/* (s/and vector? ::tree)))
              #(gen/fmap (fn [printables]
                          (let [tree (reduce (fn [map elem]
                                               (let [extended-parent (if (not (empty? map))
                                                                     (let [parent (rand-nth (keys map))]
                                                                       (assoc-in map [parent] (conj (get map parent) elem))))]
                                                 (assoc extended-parent elem [])))
                                             (array-map)
                                             printables)
                                nodes (-> tree seq reverse)
                                result (reduce (fn [map [value children]]
                                                 (if (empty? children)
                                                   (assoc map value [value])
                                                   (assoc map value (into [value] (mapv (fn [c] (get map c)) children)))))
                                               {}
                                               nodes)]
                            (-> result first second)))
                         (gen/set (gen/any-printable)))))

(ns openminded.tree.spec
  (:require [clojure.spec.alpha :as s]
            [openminded.tree :as tree]))

(s/def ::id some?)
(s/def ::value any?)
(s/def ::children (s/* ::id))

(s/def ::node (s/keys :req-un [::id ::value ::children]))

(s/def ::tree-shape (s/map-of ::id ::node))

(s/fdef tree/is-tree?
  :args (s/cat :tree ::tree-shape)
  :ret boolean?)
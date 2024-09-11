(ns openminded.tree)

(defn is-tree? [x]
  (and (map? x)
       (every? (fn [[k v]] (and (integer? k) (is-tree? v))) x)))

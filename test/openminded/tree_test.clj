(ns openminded.tree-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]
            [clojure.spec.gen.alpha :as gen]
            [clojure.test :refer :all]
            [openminded.tree :as tree]
            [openminded.test-util :refer [test-syms]]))

(def ^:private node-id (gen/simple-type-printable))
(def ^:private node-content (gen/any))
(def ^:private root-node (gen/tuple node-id node-content))

(def ^:private descendants (gen/tuple (gen/int) node-id node-content))

(def tree-gen
  (gen/fmap
   (fn [[root children]]
     (let [[root-id root-value] root]
       (loop [node-map {root-id {:id root-id :value root-value :children []}}
              children-left children]
         (if (empty? children-left)
           (vals node-map)
           (let [[parent-idx child-id child-val] (first children-left)]
             (if (node-map child-id)
               (recur node-map (rest children-left))
               (let [parent-id (nth (keys node-map)
                                    (mod parent-idx (count node-map)))
                     parent (node-map parent-id)
                     parent-updated (assoc parent
                                           :children (conj (:children parent) child-id))
                     child {:id child-id :value child-val :children []}]
                 (recur (assoc node-map
                               parent-id parent-updated
                               child-id child)
                        (rest children-left)))))))))
   (gen/tuple root-node
              (gen/not-empty (gen/vector descendants)))))

(deftest namespace-specs
  (testing "if specs are satisfied"
    (is (empty?
         (test-syms (stest/enumerate-namespace `openminded.tree)
                    {:clojure.spec.test.check/opts {:num-tests 24}})))))
